package Amazing;

public class PaqueteOrdinario extends Paquete {
    private int costoDeEnvio;

    public PaqueteOrdinario(int identificador, int volumen, int precio, int costoDeEnvio) {
        super(identificador, volumen, precio);
        if (costoDeEnvio <= 0) {
            throw new IllegalArgumentException("El costo de envio debe ser mayor que cero.");
        }
        this.costoDeEnvio = costoDeEnvio;
    }

    @Override
    public double obtenerCostoTotal() {
        return obtenerPrecio() + costoDeEnvio;
    }

    @Override
    public boolean equals(Object obj) {
    	if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PaqueteOrdinario otroPaquete = (PaqueteOrdinario) obj;
        return super.equals(obj) 		
        		&& otroPaquete.costoDeEnvio == costoDeEnvio;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaqueteOrdinario [");
        sb.append(super.toString()).append(", ");
        sb.append("Costo de Envio: ").append(costoDeEnvio);
        sb.append("]");
        return sb.toString();
    }

}