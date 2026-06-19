import java.util.*;

public class Greedy {
    
    private HashMap<Integer, ArrayList<Paquete>> solucionBest;
    private double pesoNoAsignado;
    private int cantCandidatos;

    /*
        Greedy: Ordena los camiones por capacidad descendente y asigna cada paquete 
        al primer camión que tenga espacio disponible y cumpla con la restricción de 
        cadena de frío. Si un paquete no cabe en ningún camión, queda sin asignar. 
        Es rápido pero no garantiza minimizar el peso sin asignar.
    */
    public String asignarPaquetesGreedy(List<Camion> camiones, ArrayList<Paquete> paquetes) {

        this.solucionBest = new HashMap<>();
        this.cantCandidatos = 0;

        for (Camion c : camiones) {
            this.solucionBest.put(c.getId(), new ArrayList<>());
            c.setCarga(0.0);
        }

        camiones.sort((p1, p2) -> Double.compare(p2.getCapacidad(), p1.getCapacidad())); //Ordena de mayor a menor
        paquetes.sort((p1,p2)->Double.compare(p2.getPeso(), p1.getPeso()));
        // No existe cambio real entre ordenar los paquetes de mayor a menor o los camiones 
        // (Los dos tienen la misma cantidad de candidados y los mismos KG de peso no asignado)

        this.pesoNoAsignado = pesoSinAsigActual(paquetes);

        for(Paquete p : paquetes) {
            cargarCamiones(camiones, p);
        }
        return "Solucion obtenida " + solucionBest + "\n Peso no asignado "  + pesoNoAsignado + "Kg" + "\n cantidad de candidatos " + cantCandidatos;
    }

    private void cargarCamiones(List<Camion> camiones, Paquete p) {
        for(Camion c : camiones) {
                cantCandidatos++;

                double cargaActual = c.getCarga();

                if ((cargaActual + p.getPeso()) < c.getCapacidad() && p.getContiene_alimentos() == c.getEsta_refrigerado()) {
                    solucionBest.get(c.getId()).add(p);
                    pesoNoAsignado -= p.getPeso();
                    c.setCarga(cargaActual + p.getPeso());
                    if(c.getCapacidad() == c.getCarga()) {
                        camiones.remove(c);
                    }
                    //sacar camion y modulation
                    break;
                }
            }
    }

    private double pesoSinAsigActual(ArrayList<Paquete> arr) {
        double peso = 0;
        for(Paquete p : arr) {
            peso += p.getPeso();
        }
        return peso;
    }
}