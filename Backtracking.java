import java.util.*;

public class Backtracking {
    private HashMap<Camion, ArrayList<Paquete>> solucionBest;
    private double pesoMin;
    private int estadosGenerados;


    /*
        Aca deberia de ir una breve explicacion de esta estrategia...
    */
    public String asignarPaquetesBacktracking(List<Camion> camiones, ArrayList<Paquete> paquetes){
        this.solucionBest = new HashMap<>();
        this.estadosGenerados = 0;
        
        HashMap<Camion, ArrayList<Paquete>> asignadoActual = new HashMap<>();
    
        // Inicializamos la estructura de la solución óptima vacía
        for (Camion c : camiones) {
            this.solucionBest.put(c, new ArrayList<>());
            asignadoActual.put(c, new ArrayList<>());
            c.setCarga(0.0);
        }
        
        // Inicialmente, el "mejor peor escenario" es que todos los paquetes se queden abajo
        this.pesoMin = pesoSinAsigActual(paquetes.iterator());
        backtracking(camiones, asignadoActual, paquetes, 0, this.pesoMin);
        
        return "Solucion obtenida " + solucionBest + "\n Peso no asignado " + pesoMin + "\n Cantidad de estados " + estadosGenerados;
    }

    private void backtracking(List<Camion> camiones, HashMap<Camion, ArrayList<Paquete>>asignado, ArrayList<Paquete> pack, int index, double pesoActSinAsig) {
        estadosGenerados++; // Contabilizamos cada estado/llamada del árbol

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
        double cargaCamion = camion.getCarga();

        // Validar restricciones: Capacidad y Cadena de frío
        if ((cargaCamion + paquete.getPeso()) <= camion.getCapacidad() && 
            paquete.getContiene_alimentos() == camion.getEsta_refrigerado()) {
            
            // PASO RECURSIVO (Avanzar)
            camion.setCarga(cargaCamion + paquete.getPeso());
            asignado.get(camion).add(paquete);

            backtracking(camiones, asignado, pack, index + 1, pesoActSinAsig - paquete.getPeso());

            // BACKTRACKING (Deshacer el cambio)
            asignado.get(camion).remove(asignado.get(camion).size() - 1);
            camion.setCarga(cargaCamion);
        }
    }

    // Opción 2: Dejar el paquete sin asignar y seguir con el siguiente paquete.
    //backtracking(camiones, asignado, pack, index + 1, pesoActSinAsig);
    }

    private double pesoSinAsigActual(Iterator<Paquete> paquetes) {
        double peso = 0;
        while(paquetes.hasNext()) {
            Paquete p = paquetes.next();
            peso += p.getPeso();
        }
        return peso;
    }
}