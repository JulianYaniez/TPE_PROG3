public class Camion {
    private int id;
    private String patente;
    private int esta_refrigerado;
    private int capacidad;
    private double carga;


    public Camion(int id, String patente, int esta_refrigerado, int capacidad, double carga) {
        this.id = id;
        this.patente = patente;
        this.esta_refrigerado = esta_refrigerado;
        this.capacidad = capacidad;
        this.carga = carga;
    }


    public int getId() {
        return id;
    }


    public String getPatente() {
        return patente;
    }


    public int getEsta_refrigerado() {
        return esta_refrigerado;
    }


    public int getCapacidad() {
        return capacidad;
    }


    public double getCarga() {
        return carga;
    }


    public void setCarga(double carga) {
        this.carga = carga;
    }
    
}
