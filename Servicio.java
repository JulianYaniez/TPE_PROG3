import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Servicio {
    private Map<Boolean, Map<String, Paquete>> paquetes;
    private List<Camion> camiones;
/*
* Expresar la complejidad temporal del constructor.
    Complejidad = O(n^2)
*/
    public Servicio(String pathCamion, String pathPaquete){
        this.paquetes = new HashMap<>();
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

        return p;
    }

    /*
    * Expresar la complejidad temporal del servicio 2.
        Complejidad = O(1)
    */
    public List<Paquete> servicio2(boolean contieneAlimentos) {
        return new LinkedList<Paquete>(this.paquetes.get(contieneAlimentos).values());
    }


    
    /*
    * Expresar la complejidad temporal del servicio 3.
        Complejidad = O(n)
    */
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {
        List<Paquete> res = new LinkedList<>();
        Iterator<Paquete> paquetesTrue = this.paquetes.get(true).values().iterator();
        Iterator<Paquete> paquetesFalse = this.paquetes.get(false).values().iterator();
        
        while(paquetesTrue.hasNext() || paquetesFalse.hasNext()) {
            if(paquetesTrue.hasNext()) res.add(paquetesTrue.next());
            if(paquetesFalse.hasNext()) res.add(paquetesFalse.next());
        }
        return res;
    }


    public void addPaquetesCamiones() {

    }
}