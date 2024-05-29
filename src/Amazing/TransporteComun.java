package Amazing;

public class TransporteComun extends Transporte {
    private int cantMaximaPaquetes;

    public TransporteComun(String identificador, int volumenMaximoDeCarga, double valorPorViaje, int cantMaximaPaquetes) {
        super(identificador, volumenMaximoDeCarga, valorPorViaje);
        if (cantMaximaPaquetes <= 0) {
            throw new IllegalArgumentException("La cantidad maxima de paquetes debe ser mayor que cero.");
        }
        this.cantMaximaPaquetes = cantMaximaPaquetes;
    }

    @Override
    public boolean puedeCargarPaquete(Paquete paquete) {
        if (paquete instanceof PaqueteOrdinario && paquete.obtenerVolumen() < 2000) {
            if (obtenerVolumenCarga() + paquete.obtenerVolumen() <= obtenerVolumenMaximoDeCarga()) {
                if (obtenerCantidadDePaquetesEnCarga() < cantMaximaPaquetes) {                  
                    return true; 
                }
            }
        }
        return false; 
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TransporteComun [");
        sb.append(super.toString()).append(", ");
        sb.append("Cantidad Maxima de Paquetes: ").append(cantMaximaPaquetes);
        sb.append("]");
        return sb.toString();
    }
    
}
