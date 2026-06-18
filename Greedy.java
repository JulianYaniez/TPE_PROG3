import java.util.*;

public class Greedy {
    
    private HashMap<Camion, ArrayList<Paquete>> solucionBest;
    private double pesoNoAsignado;
    private int cantCandidatos;

    /*
        Aca deberia de ir una breve explicacion de esta estrategia...
    */
    public String asignarPaquetesGreedy(List<Camion> camiones, ArrayList<Paquete> paquetes) {

        this.solucionBest = new HashMap<>();
        this.cantCandidatos = 0;

        for (Camion c : camiones) {
            this.solucionBest.put(c, new ArrayList<>());
            c.setCarga(0.0);
        }
        paquetes.sort((p1, p2) -> Double.compare(p2.getPeso(), p1.getPeso()));
        this.pesoNoAsignado = pesoSinAsigActual(paquetes);

        for(Paquete p : paquetes) {

            for(Camion c : camiones) {
                cantCandidatos++;

                double cargaActual = c.getCarga();

                if ((cargaActual + p.getPeso()) < c.getCapacidad() && p.getContiene_alimentos() == c.getEsta_refrigerado()) {
                    solucionBest.get(c).add(p);
                    c.setCarga(cargaActual + p.getPeso());
                    break;
                }
            }
        }
        return "Solucion obtenida " + solucionBest + "\n Peso no asignado "  + pesoNoAsignado + "\n cantidad de candidatos " + cantCandidatos;
    }

    private double pesoSinAsigActual(ArrayList<Paquete> arr) {
        double peso = 0;
        for(Paquete p : arr) {
            peso += p.getPeso();
        }
        return peso;
    }
}