package Amazing;

class TransporteUtilitario extends Transporte {
    private double valorExtra;

    public TransporteUtilitario(String identificador, int volumenMaximoDeCarga, double valorPorViaje, double valorExtra) {
        super(identificador, volumenMaximoDeCarga, valorPorViaje);
        if (valorExtra <= 0) {
            throw new IllegalArgumentException("El valor extra no puede ser negativo ni cero.");
        }
        this.valorExtra = valorExtra;
    }

    @Override
    public boolean puedeCargarPaquete(Paquete paquete) {
        if (obtenerVolumenCarga() + paquete.obtenerVolumen() <= obtenerVolumenMaximoDeCarga()) {
            return true; 
        }
        return false; 
    }

    @Override
    public double obtenerCostoViaje() {
    	if (obtenerCantidadDePaquetesEnCarga() > 3) {
    		return super.obtenerCostoViaje() + valorExtra;
    	}
        return super.obtenerCostoViaje();
    }

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
	    sb.append("TransporteUtilitario [");
	    sb.append(super.toString()).append(", ");
	    sb.append("Valor Extra: ").append(valorExtra);
	    sb.append("]");
	    return sb.toString();
	}
	
}