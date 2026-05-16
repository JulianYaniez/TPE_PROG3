public class Paquete {
    private int id;
    private String codigo;
    private double peso;
    private int contiene_alimentos;
    private int lvl_urgencia;
    
    public Paquete(int id, String codigo, double peso, int contiene_alimentos, int lvl_urgencia) {
        this.id = id;
        this.codigo = codigo;
        this.peso = peso;
        this.contiene_alimentos = contiene_alimentos;
        this.lvl_urgencia = lvl_urgencia;
    }

    public int getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public double getPeso() {
        return peso;
    }

    public int getContiene_alimentos() {
        return contiene_alimentos;
    }

    public int getLvl_urgencia() {
        return lvl_urgencia;
    }

    @Override
    public String toString() {
        return " Paquete [ getId =" + getId() + ", getCodigo =" + getCodigo() + ", getPeso =" + getPeso()
                + ", getContiene_alimentos =" + getContiene_alimentos() + ", getLvl_urgencia =" + getLvl_urgencia()
                + " ]";
    }
}