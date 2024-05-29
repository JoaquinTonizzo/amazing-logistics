package Amazing;

import java.util.HashMap;
import java.util.Map;

public class Pedido implements Cerrable {
    private String nombreCliente;
    private String direccionCliente;
    private int dniCliente;
    private int numeroPedido;
    private Map<Integer ,Paquete> carrito;
    private boolean cerrado;

    public Pedido(int numeroPedido, String nombreCliente, String direccionCliente, int dniCliente) {
        if (nombreCliente == null || nombreCliente.isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacio.");
        }
        if (direccionCliente == null || direccionCliente.isEmpty()) {
            throw new IllegalArgumentException("La direccion del cliente no puede estar vacia.");
        }
        if (dniCliente <= 0) {
            throw new IllegalArgumentException("El DNI del cliente debe ser mayor que cero.");
        }
        this.numeroPedido = numeroPedido;
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        this.dniCliente = dniCliente;
        this.carrito = new HashMap<>();
    }
    
    public void agregarPaquete(Paquete paquete) {
    	if (cerrado) {
        	throw new RuntimeException("Pedido Cerrado.");
    	}
        carrito.put(paquete.obtenerIdentificador(), paquete);
    }

    public boolean eliminarPaquete(int codPaquete) {
    	if (cerrado) {
    		return false;
    	}
    	return carrito.remove(codPaquete) != null;
    }

    public double obtenerCosto() {
        double costoTotal = 0;
        for (Paquete paquete : carrito.values()) {
            costoTotal += paquete.obtenerCostoTotal();
        }
        return costoTotal;
    }
    
    public String obtenerNombreCliente() {
    	return nombreCliente;
    }
    
    public int obtenerDniCliente() {
    	return dniCliente;
    }

    public int obtenerNumero() {
        return numeroPedido;
    }
    
    public void cerrar() {
    	if (cerrado) {
            throw new RuntimeException("El pedido ya esta cerrado.");
        }
        cerrado = true;
    }

    public boolean estaCerrado() {
        return cerrado;
    }
    
    public boolean estanTodosLosPaquetesCargados() {
        for (Paquete paquete : carrito.values()) {
            if (!paquete.estaCargado()) {
                return false;
            }
        }
        return true; 
    }

	public String cargarPaquetesEnTransporte(Transporte transporte) {
		StringBuilder listaCarga = new StringBuilder();
	    for (Paquete paquete : carrito.values()) {
	        if (!paquete.estaCargado() && transporte.cargarPaquete(paquete)) {
	            paquete.cargar();
	            listaCarga.append(" + [ ").append(numeroPedido).append(" - ").append(paquete.obtenerIdentificador()).append(" ] ").append(direccionCliente).append("\n");
	        }
	    }
	    return listaCarga.toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
	    sb.append("Pedido [");
	    sb.append("Numero de Pedido: ").append(numeroPedido).append(", ");
	    sb.append("Cliente: ").append(nombreCliente).append(", ");
	    sb.append("Direccion: ").append(direccionCliente).append(", ");
	    sb.append("DNI: ").append(dniCliente).append(", ");
	    sb.append("Carrito: ").append(carrito.values()).append(", ");
	    sb.append("Cerrado? ").append(cerrado);
	    sb.append("]");
	    return sb.toString();
	}	
	
}
