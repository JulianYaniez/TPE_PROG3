import java.util.*;

public class Backtracking {
    private HashMap<Integer, ArrayList<Paquete>> solucionBest;
    private double pesoMin;
    private int estadosGenerados;
    //peso total de paquetes como atributo


    /*
        Backtracking: Intenta asignar cada paquete a todos los camiones disponibles,
        respetando restricciones de capacidad y cadena de frío. Explora todas las 
        combinaciones posibles y retrocede cuando no puede continuar, encontrando 
        la asignación que minimiza el peso total sin asignar. Garantiza la solución 
        óptima pero explora muchos estados.
    */

        
    public String asignarPaquetesBacktracking(List<Camion> camiones, ArrayList<Paquete> paquetes){
        this.solucionBest = new HashMap<>();
        this.estadosGenerados = 0;
        
        HashMap<Camion, ArrayList<Paquete>> asignadoActual = new HashMap<>();
        double pesoActSinAsig = 0.0;
    
        // Inicializamos la estructura de la solución óptima vacía
        for (Camion c : camiones) {
            this.solucionBest.put(c.getId(), new ArrayList<>());
            asignadoActual.put(c, new ArrayList<>());
            c.setCarga(0.0);
        }
        
        // Inicialmente, el "mejor peor escenario" es que todos los paquetes se queden abajo
        pesoActSinAsig = pesoSinAsigActual(paquetes.iterator());
        this.pesoMin = pesoActSinAsig;
        backtracking(camiones, asignadoActual, paquetes, 0, pesoActSinAsig);
        
        return "Solucion obtenida " + solucionBest + "\n Peso no asignado " + pesoMin + "\n Cantidad de estados " + estadosGenerados;
    }

    private void backtracking(List<Camion> camiones, HashMap<Camion, ArrayList<Paquete>>asignado, ArrayList<Paquete> pack, int index, double pesoActSinAsig) {
        estadosGenerados++; // Contabilizamos cada estado del árbol

        // CASO BASE: Ya evaluamos todos los paquetes
        if (index == pack.size()) {
            if (pesoActSinAsig < this.pesoMin) {
                this.pesoMin = pesoActSinAsig;

                // Clonamos la solución actual a la definitiva
                for (Camion c : camiones) {
                    this.solucionBest.put(c.getId(), new ArrayList<>(asignado.get(c)));
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