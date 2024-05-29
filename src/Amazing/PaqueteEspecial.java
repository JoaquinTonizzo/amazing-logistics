package Amazing;

public class PaqueteEspecial extends Paquete {
    private double porcentaje;
    private double adicional;

    public PaqueteEspecial(int identificador, int volumen, int precio, int porcentaje, int adicional) {
        super(identificador, volumen, precio);
        if (porcentaje <= 0 || adicional <= 0) {
            throw new IllegalArgumentException("El porcentaje y el adicional deben ser mayores que cero.");
        }
        this.porcentaje= porcentaje;
        this.adicional = adicional;
    }

    @Override
    public double obtenerCostoTotal() {
    	double costoTotal = obtenerPrecio() + (obtenerPrecio() * (porcentaje / 100));
        if (obtenerVolumen() > 5000) {
            costoTotal += adicional*2;
        }
        else {
        	if (obtenerVolumen() >= 3000) {
                costoTotal += adicional;
            }
        }
        return costoTotal;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PaqueteEspecial otroPaquete = (PaqueteEspecial) obj;
        return super.equals(obj) 
        		&& otroPaquete.porcentaje == porcentaje
        		&& otroPaquete.adicional == adicional;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PaqueteEspecial [");
        sb.append(super.toString()).append(", ");
        sb.append("Porcentaje: ").append(porcentaje).append(", ");
        sb.append("Adicional: ").append(adicional);
        sb.append("]");
        return sb.toString();
    }
       
}
