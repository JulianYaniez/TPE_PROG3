import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

public class Servicio {
    private Map<Boolean, Map<String, Paquete>> paquetes;

    // private Map<String, Paquete> paquetes;
    // private LinkedList<Paquete> conAlimentos;
    // private LinkedList<Paquete> sinAlimentos;
    //
    private List<Camion> camiones;
/*
* Expresar la complejidad temporal del constructor.
    Complejidad = O(n^2)
*/
    public Servicio(String pathCamion, String pathPaquete){
        this.paquetes = new HashMap<>();
        //this.sinAlimentos = new LinkedList<>();
        //this.conAlimentos = new LinkedList<>();
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
                    Camion c = new Camion(Integer.parseInt(datos[0]), datos[1], Boolean.parseBoolean(datos[2]), Integer.parseInt(datos[3]));
                    this.camiones.add(c);
                } else {
                    Paquete p = new Paquete(Integer.parseInt(datos[0]), datos[1], Double.parseDouble(datos[2]), Integer.parseInt(datos[3]), Integer.parseInt(datos[4]));
                    this.paquetes.get((Integer.parseInt(datos[3]) == 1? true: false)).put( datos[1], p);
                    /*
                    this.paquetes.put(datos[1], p);
                    if(Integer.parseInt(datos[3]) == 1){
                        this.sinAlimentos.add(p);
                    }else{
                        this.conAlimentos.add(p);
                    } 
                    */
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
        Paquete p = null;
        p = paquetes.get(true).get(codigoPaquete);
        if (p==null) {
            p = paquetes.get(false).get(codigoPaquete);
        }
        // return paquetes.get(codigoPaquete);

        return p;}



    /*
    * Expresar la complejidad temporal del servicio 2.
        Complejidad = O(1)
    */
    public List<Paquete> servicio2(boolean contieneAlimentos) {
        return new LinkedList<Paquete>(this.paquetes.get(contieneAlimentos).values());
        /*if(contieneAlimentos){
            return new LinkedList<Paquete>(this.conAlimentos());
        }
        return new LinkedList<Paquete>(this.sinAlimentos());
        
        */
    }



    /*
    * Expresar la complejidad temporal del servicio 3.
        Complejidad = O(n)
    */
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {
        List<Paquete> res = new LinkedList<>();
        // Iterator<Paquete> paquetes2 = this.paquetes.values().iterator();
        Iterator<Paquete> it1 = this.paquetes. 
        while(paquetes2.hasNext()) {
            Paquete p = paquetes2.next(); 
            if(urgenciaMinima <= p.getLvl_urgencia() && p.getLvl_urgencia() <= urgenciaMaxima) {
                res.add(p);
            }
        }
        return res;
    }
    private HashMap<Camion, ArrayList<Paquete>> solucion;


    private double minimoPesoSinAsignar; 
    
    public int asignarPaquetes(){
        HashMap<Camion, ArrayList<Paquete>>asignado = new HashMap<>(); // paquetes asiganos
        HashMap<Camion, Double> carga = new HashMap<>(); // peso actual de cada camion

        asignarClaves(asignado); 

        backtraking(asignado, carga ,new ArrayList<Paquete>(paquetes.values()), 0, pesoSinAsigActual());
        
        return 0;
    }

    private double pesoSinAsigActual() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pesoSinAsigActual'");
    }

    private void asignarClaves(HashMap<Camion, ArrayList<Paquete>> asignado) {
        Iterator<Camion> it = camiones.iterator();
        while (it.hasNext()) {
           asignado.put(it.next(), new ArrayList<>());//o linkedList
        } 
    }


    private void backtraking(HashMap<Camion, ArrayList<Paquete>>asignado,  
        HashMap<Camion, Double> carga,ArrayList<Paquete> pack, int index, double pesoActSinAsig) {
            
        if (pack.size()-1 == index && (this.minimoPesoSinAsignar>pesoActSinAsig) || pesoActSinAsig == 0) {

            Iterator<Camion> it =asignado.keySet().iterator();
            while (it.hasNext()) {
                Camion camion=it.next();
                solucion.put(camion, new ArrayList<>(asignado.get(camion)));
            }
            return;

        }


        Iterator<Camion> it =asignado.keySet().iterator();

        Paquete paquete = pack.get(index);

        while (it.hasNext()) {
            Camion camion = it.next();
            double cargaCamion = carga.get(camion);

            if(camion.getCapacidad()>(cargaCamion + paquete.getPeso()) && 
            (paquete.getContiene_alimentos() == 1? true:false) == camion.getEsta_refrigerado() ){
                Double cargaNueva = paquete.getPeso();//obtiene el peso actual del paquete

                carga.put(camion, cargaCamion+cargaNueva);//asigana el peso actual 
               asignado.get(camion).add(paquete);//asigna el paquete a un camion
                pesoActSinAsig-=paquete.getPeso();

                backtraking(asignado, carga, pack, index+1, pesoActSinAsig);

                pesoActSinAsig+=paquete.getPeso();
               asignado.get(camion).remove(paquete);// desasigan del paquete
                carga.put(camion, cargaCamion); // elimina el peso 
            }
        
        }
    }


}