package Amazing;

class Camion extends Transporte {
    private double valorAdicional;

    public Camion(String identificador, int volumenMaximoDeCarga, double valorPorViaje, double valorAdicional) {
        super(identificador, volumenMaximoDeCarga, valorPorViaje);
        if (valorAdicional <= 0) {
            throw new IllegalArgumentException("El valor adicional no puede ser negativo ni cero.");
        }
        this.valorAdicional = valorAdicional;
    }

    @Override
    public boolean puedeCargarPaquete(Paquete paquete) {
        if (paquete instanceof PaqueteEspecial && paquete.obtenerVolumen() > 2000) {
            if (obtenerVolumenCarga() + paquete.obtenerVolumen() <= obtenerVolumenMaximoDeCarga()) {
                return true; 
            }
        }
        return false;
    }

    @Override
    public double obtenerCostoViaje() {
        return super.obtenerCostoViaje() + valorAdicional*obtenerCantidadDePaquetesEnCarga();
    }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
	    sb.append("Camion [");
	    sb.append(super.toString()).append(", ");
	    sb.append("Valor Adicional por Paquete: ").append(valorAdicional);
	    sb.append("]");
	    return sb.toString();
	}
	
}
