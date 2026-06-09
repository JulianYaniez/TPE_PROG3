import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Servicio {
    private Map<String, Paquete> paquetes;
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
                    if(Integer.parseInt(datos[3]) == 1) {
                    Paquete p = new Paquete(Integer.parseInt(datos[0]), datos[1], Double.parseDouble(datos[2]), Integer.parseInt(datos[3]), Integer.parseInt(datos[4]));
                    this.paquetes.put(datos[1], p);
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
        return paquetes.get(codigoPaquete);
    }

    /*
    * Expresar la complejidad temporal del servicio 2.
        Complejidad = O(1)
    */
    public List<Paquete> servicio2(boolean contieneAlimentos) {
        List<Paquete> res = new LinkedList<>();
        Iterator<Paquete> paquetes2 = this.paquetes.values().iterator();
        while(paquetes2.hasNext()) {
            Paquete p = paquetes2.next();
            if(contieneAlimentos == (p.getContiene_alimentos() == 1 ? true : false) ) {
                res.add(p);
            }
        }
        return res;
    }



    /*
    * Expresar la complejidad temporal del servicio 3.
        Complejidad = O(n)
    */
    public List<Paquete> servicio3(int urgenciaMinima, int urgenciaMaxima) {
        List<Paquete> res = new LinkedList<>();Iterator<Paquete> paquetes2 = this.paquetes.values().iterator();
        while(paquetes2.hasNext()) {
            Paquete p = paquetes2.next(); 
            if(urgenciaMinima <= p.getLvl_urgencia() && p.getLvl_urgencia() <= urgenciaMaxima) {
                res.add(p);
            }
        }
        return res;
    }
}