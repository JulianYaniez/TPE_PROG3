public class Camion {
    private int id;
    private String patente;
    private boolean esta_refrigerado;
    private int capacidad;


    public Camion(int id, String patente, boolean esta_refrigerado, int capacidad) {
        this.id = id;
        this.patente = patente;
        this.esta_refrigerado = esta_refrigerado;
        this.capacidad = capacidad;
    }


    public int getId() {
        return id;
    }


    public String getPatente() {
        return patente;
    }


    public boolean getEsta_refrigerado() {
        return esta_refrigerado;
    }


    public int getCapacidad() {
        return capacidad;
    }
}
