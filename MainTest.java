public class MainTest {
    public static void main (String[] args) {
        String pathCamion = "camion.csv";
        String pathPaquete = "paquete.csv";

        Servicio servicio = new Servicio(pathCamion, pathPaquete);

        System.out.println("--- Servicio 1: buscar paquete P002 ---");
        Paquete p = servicio.servicio1("P002");
        System.out.println(p != null ? p : "Paquete no encontrado");

        System.out.println("\n--- Servicio 2: paquetes que contienen alimentos ---");
        for (Paquete paquete : servicio.servicio2(true)) {
            System.out.println(paquete);
        }

        System.out.println("\n--- Servicio 3: urgencia entre 10 y 90 ---");
        for (Paquete paquete : servicio.servicio3(10, 90)) {
            System.out.println(paquete);
        }
    }
}