import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Servicio {
    private Map<String, Paquete> paquetes;
    private LinkedList<Paquete> conAlimentos;
    private LinkedList<Paquete> sinAlimentos;
    private List<Camion> camiones;

    // private Map<Boolean, Map<String, Paquete>> paquetes;


    
/*
* Expresar la complejidad temporal del constructor.
    Complejidad = O(n^2)
*/
    public Servicio(String pathCamion, String pathPaquete){
        this.paquetes = new HashMap<>();
        this.conAlimentos = new LinkedList<>();
        this.sinAlimentos = new LinkedList<>();
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
                    Camion c = new Camion(Integer.parseInt(datos[0]), datos[1], Integer.parseInt(datos[2]), Integer.parseInt(datos[3]), 0);
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
            if(p.getLvl_urgencia() >= urgenciaMinima && p.getLvl_urgencia() <= urgenciaMaxima) {
                res.add(p);
            }
        }
        return res;
    }


    public String asignarPaquetesBacktracking() {
        return new Backtracking().asignarPaquetesBacktracking(camiones, new ArrayList<>(paquetes.values()));
    }

    public String asignarPaquetesGreedy() {
        return new Greedy().asignarPaquetesGreedy(camiones, new ArrayList<>(paquetes.values()));
    }
}