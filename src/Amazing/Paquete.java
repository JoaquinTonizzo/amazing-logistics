package Amazing;

public abstract class Paquete implements Cargable {
    private int identificador;
    private int volumen;
    private int precio;
    private boolean cargado;

    public Paquete(int identificador, int volumen, int precio) {
        if (volumen <= 0) {
            throw new IllegalArgumentException("El volumen del paquete debe ser mayor que cero.");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El precio del paquete debe ser mayor que cero.");
        }
        this.identificador = identificador;
        this.volumen = volumen;
        this.precio = precio;
        this.cargado = false;
    }

    public int obtenerIdentificador() {
        return identificador;
    }

    public int obtenerVolumen() {
        return volumen;
    }
    
    public int obtenerPrecio() {
        return precio;
    }

    public abstract double obtenerCostoTotal();

    public boolean estaCargado() {
        return cargado;
    }

    public void cargar() {
    	if (cargado) {
            throw new RuntimeException("El paquete ya esta cargado.");
        }
        cargado = true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Identificador: ").append(identificador).append(", ");
        sb.append("Volumen: ").append(volumen).append(", ");
        sb.append("Cargado? ").append(cargado).append(", ");
        sb.append("Precio: ").append(precio);
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
    	if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Paquete otroPaquete = (Paquete) obj;
        return volumen == otroPaquete.volumen 
        		&& precio == otroPaquete.precio;
    }
    
}