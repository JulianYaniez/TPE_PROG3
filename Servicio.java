import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Servicio {
    private Map<String, Paquete> paquetes;
    private LinkedList<Paquete> conAlimentos;
    private LinkedList<Paquete> sinAlimentos;

    // private Map<Boolean, Map<String, Paquete>> paquetes;
    private List<Camion> camiones;

    private HashMap<Camion, ArrayList<Paquete>> solucionBest;
    private double pesoMin;
    private int estadoVisitado;

    
/*
* Expresar la complejidad temporal del constructor.
    Complejidad = O(n^2)
*/
    public Servicio(String pathCamion, String pathPaquete){
        this.paquetes = new HashMap<>();
        this.conAlimentos= new LinkedList<>();
        this.conAlimentos= new LinkedList<>();
        this.camiones = new LinkedList<>();
       createPath(pathCamion, "camion");
       createPath(pathPaquete, "paquete");
    }



    private void createPath(String path, String tipo) {
        String rutaArchivo = path; // Usar la ruta recibida por parámetro
        String linea;
        String separador = ";"; // Cambia por ";" si tu archivo usa punto y coma

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            int cant =  Integer.parseInt(br.readLine());

            for(int i = 0; i < cant; i++) {
                linea = br.readLine();

                String[] datos = linea.split(separador);
                if ("camion".equals(tipo)) {
                    Camion c = new Camion(Integer.parseInt(datos[0]), datos[1], Integer.parseInt(datos[2]), Integer.parseInt(datos[3]));
                    this.camiones.add(c);
                } else {
                    Paquete p = new Paquete(Integer.parseInt(datos[0]), datos[1], Double.parseDouble(datos[2]), Integer.parseInt(datos[3]), Integer.parseInt(datos[4]));
                    this.paquetes.put(datos[1], p);
                    if (Integer.parseInt(datos[3])==1) {
                        this.conAlimentos.addLast(p);
                    }else{
                        this.sinAlimentos.addLast(p);
                    }
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }



    /*
    * Expresar la complejidad temporal del servicio 1.
        Complejidad = O(1)
    */
    public Paquete servicio1(String codigoPaquete) { 
        return this.paquetes.get(codigoPaquete);
    }



    /*
    * Expresar la complejidad temporal del servicio 2.
        Complejidad = O(1)
    */
    public List<Paquete> servicio2(boolean contieneAlimentos) {
       if (contieneAlimentos) {
            return new LinkedList<>(this.conAlimentos);
       }
       return new LinkedList<>(this.sinAlimentos);
    }

    
    /*
    * Expresar la complejidad temporal del servicio 3.
        Complejidad = O(n)
    */
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {
        List<Paquete> res = new LinkedList<>();
        Iterator<Paquete> it = this.paquetes.values().iterator();
        while (it.hasNext()) {
            Paquete p = it.next();
            if(p.getLvl_urgencia() >= urgenciaMinima && p.getLvl_urgencia() >= urgenciaMaxima) 
                res.add(p);
        }
        return res;
    }

    
    
    public int asignarPaquetesBacktracking(){
        this.solucionBest = new HashMap<>();
        this.estadoVisitado = 0;
        HashMap<Camion, ArrayList<Paquete>> asignadoActual = new HashMap<>();
        HashMap<Camion, Double> cargaActual = new HashMap<>();
    
        // Inicializamos la estructura de la solución óptima vacía
        for (Camion c : camiones) {
            this.solucionBest.put(c, new ArrayList<>());
            asignadoActual.put(c, new ArrayList<>());
            cargaActual.put(c, 0.0);
        }

        ArrayList<Paquete> listaPaquetes = new ArrayList<>(paquetes.get(true).values());
        listaPaquetes.addAll(paquetes.get(false).values());

        // Inicialmente, el "mejor peor escenario" es que todos los paquetes se queden abajo
        this.pesoMin = pesoSinAsigActual(listaPaquetes);
        backtracking(asignadoActual, cargaActual, listaPaquetes, 0, this.pesoMin);
        
        return estadoVisitado;
    }

    private double pesoSinAsigActual(ArrayList<Paquete> arr) {
        double peso = 0;
        for(Paquete p : arr) {
            peso += p.getPeso();
        }
        return peso;
    }

    private void backtracking(HashMap<Camion, ArrayList<Paquete>>asignado, HashMap<Camion, Double> carga, ArrayList<Paquete> pack, int index, double pesoActSinAsig) {
        estadoVisitado++; // Contabilizamos cada estado/llamada del árbol

    // CASO BASE: Ya evaluamos todos los paquetes
    if (index == pack.size()) {
        if (pesoActSinAsig < this.pesoMin) {
            this.pesoMin = pesoActSinAsig;
            // Clonamos la solución actual a la definitiva
            for (Camion c : camiones) {
                this.solucionBest.put(c, new ArrayList<>(asignado.get(c)));
            }
        }
        return;
    }

    Paquete paquete = pack.get(index);

    // Opción 1: Intentar meter el paquete en algún camión válido
    for (Camion camion : camiones) {
        double cargaCamion = carga.get(camion);

        // Validar restricciones: Capacidad y Cadena de frío
        if ((cargaCamion + paquete.getPeso()) <= camion.getCapacidad() && 
            paquete.getContiene_alimentos() == camion.getEsta_refrigerado()) {
            
            // PASO RECURSIVO (Avanzar)
            carga.put(camion, cargaCamion + paquete.getPeso());
            asignado.get(camion).add(paquete);

            backtracking(asignado, carga, pack, index + 1, pesoActSinAsig - paquete.getPeso());

            // BACKTRACKING (Deshacer el cambio)
            asignado.get(camion).remove(asignado.get(camion).size() - 1);
            carga.put(camion, cargaCamion);
        }
    }

    // Opción 2: Poda/Decisión alternativa -> Dejar el paquete en el suelo y seguir con el próximo
    // Esto es vital porque puede que un paquete quepa en un camión, pero convenga dejarlo abajo para meter otros más pesados.
    backtracking(asignado, carga, pack, index + 1, pesoActSinAsig);
    }

    public int asignarPaquetesGreedy() {
        HashMap<Camion, Double> carga = new HashMap<>();
        this.solucionBest = new HashMap<>();
        this.estadoVisitado = 0;

        for (Camion c : camiones) {
            this.solucionBest.put(c, new ArrayList<>());
            carga.put(c, 0.0);
        }

        ArrayList<Paquete> listaPaquetes = new ArrayList<>(paquetes.get(true).values());
        listaPaquetes.addAll(paquetes.get(false).values());
        listaPaquetes.sort((p1, p2) -> Double.compare(p2.getPeso(), p1.getPeso()));
        this.pesoMin = pesoSinAsigActual(listaPaquetes);
        

        for(Paquete p : listaPaquetes) {

            for(Camion c : camiones) {
                estadoVisitado++;

                double cargaActual = carga.get(c);

                if ((cargaActual + p.getPeso()) < c.getCapacidad() && p.getContiene_alimentos() == c.getEsta_refrigerado()) {
                    solucionBest.get(c).add(p);
                    carga.put(c, cargaActual + p.getPeso());
                    break;
                }
            }
        }
        return estadoVisitado;
    }
}