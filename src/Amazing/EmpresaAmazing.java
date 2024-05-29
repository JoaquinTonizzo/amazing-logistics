package Amazing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class EmpresaAmazing implements IEmpresa{
	private String CUIT;
	private Map<String, Transporte> transportes;
    private Map<Integer, Pedido> pedidos;
	private int pedidoIdContador;
	private int paqueteIdContador;
	private double facturacionTotal;
	
	public EmpresaAmazing(String cuit) {
		if (cuit == null || cuit.isEmpty()) {
            throw new IllegalArgumentException("El CUIT no puede ser nulo o vacio.");
        }
		this.CUIT=cuit;
		this.transportes = new HashMap<>();
		this.pedidos = new HashMap<>();
        this.pedidoIdContador = 1; 
        this.paqueteIdContador = 1;
        this.facturacionTotal = 0;
    }
	
	@Override
	public void registrarAutomovil(String patente, int volMax, int valorViaje, int maxPaq) {
		validarPatenteUnica(patente);
        Transporte transporte = new TransporteComun(patente, volMax, valorViaje, maxPaq);
        transportes.put(patente, transporte);
    }

	@Override
	public void registrarUtilitario(String patente, int volMax, int valorViaje, int valorExtra) {
		validarPatenteUnica(patente);
        Transporte transporte = new TransporteUtilitario(patente, volMax, valorViaje, valorExtra);
        transportes.put(patente, transporte);
    }

	@Override
	public void registrarCamion(String patente, int volMax, int valorViaje, int adicXPaq) {
		validarPatenteUnica(patente);
        Transporte transporte = new Camion(patente, volMax, valorViaje, adicXPaq);
        transportes.put(patente, transporte);
    }

	@Override
	public int registrarPedido(String cliente, String direccion, int dni) {
		verificarDniClienteUnico(dni, cliente);
		int numeroPedido = pedidoIdContador;
		pedidoIdContador++;
        Pedido pedido = new Pedido(numeroPedido, cliente, direccion, dni);
        pedidos.put(numeroPedido, pedido);
        return numeroPedido;
    }

	@Override
	public int agregarPaquete(int codPedido, int volumen, int precio, int costoEnvio) {
        Pedido pedido = buscarPedidoPorCodigo(codPedido);
        int codigoPaquete = paqueteIdContador;
        paqueteIdContador++;
        Paquete paquete = new PaqueteOrdinario(codigoPaquete, volumen, precio, costoEnvio);
        pedido.agregarPaquete(paquete);
        return codigoPaquete;
	}

	@Override
	public int agregarPaquete(int codPedido, int volumen, int precio, int porcentaje, int adicional) {
		Pedido pedido = buscarPedidoPorCodigo(codPedido);
		int codigoPaquete = paqueteIdContador;
        paqueteIdContador++;
        Paquete paquete = new PaqueteEspecial(codigoPaquete, volumen, precio, porcentaje, adicional);
        pedido.agregarPaquete(paquete);
        return codigoPaquete;
    }

	@Override
	public boolean quitarPaquete(int codPaquete) {
		if (codPaquete >= paqueteIdContador || codPaquete<0) {
			throw new RuntimeException("Paquete no encontrado.");
		}
		for (Pedido pedido : pedidos.values()) {
            if (pedido.eliminarPaquete(codPaquete)) {
                return true; 
            }
        }
		return false;
    }

	@Override
	public double cerrarPedido(int codPedido) {
        Pedido pedido = buscarPedidoPorCodigo(codPedido);
        pedido.cerrar();
        facturacionTotal += pedido.obtenerCosto();
        return pedido.obtenerCosto();
    }

	@Override
	public String cargarTransporte(String patente) {
		Transporte transporte = buscarTransportePorPatente(patente);
	    List<Pedido> pedidosCerrados = obtenerPedidosCerrados();
	    StringBuilder listaCarga = new StringBuilder();	    
	    for (Pedido pedido : pedidosCerrados) {
	    	listaCarga.append(pedido.cargarPaquetesEnTransporte(transporte));
	    }
	    return listaCarga.toString();
	}

	@Override
	public double costoEntrega(String patente) {
		Transporte transporte = buscarTransportePorPatente(patente);
        return transporte.obtenerCostoViaje();
    }

	@Override
	public double facturacionTotalPedidosCerrados() {
		return facturacionTotal;
    }

	@Override
	public Map<Integer, String> pedidosNoEntregados() {
		Map<Integer, String> pedidosNoEntregados = new HashMap<>();
		List<Pedido> pedidosCerrados = obtenerPedidosCerrados();
		for (Pedido pedido : pedidosCerrados) {
            if (!pedido.estanTodosLosPaquetesCargados()) {
                pedidosNoEntregados.put(pedido.obtenerNumero(), pedido.obtenerNombreCliente());
            }
        }
        return pedidosNoEntregados;
    }

	@Override
	public boolean hayTransportesIdenticos() {
	    List<Transporte> listaTransportes = new ArrayList<>(transportes.values());	    
	    for (int i = 0; i < listaTransportes.size(); i++) {
	        for (int j = i + 1; j < listaTransportes.size(); j++) {
	            Transporte transporte1 = listaTransportes.get(i);
	            Transporte transporte2 = listaTransportes.get(j);        
	            if (transporte1.equals(transporte2)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}
	
	private void validarPatenteUnica(String patente) {
        if (transportes.containsKey(patente)) {
            throw new RuntimeException("La patente ya esta registrada en el sistema.");
        }
    }

	private Transporte buscarTransportePorPatente(String patente) {
	    if (!transportes.containsKey(patente)) {
	        throw new RuntimeException("La patente no esta registrada en el sistema.");
	    }
	    return transportes.get(patente);
	}

	private Pedido buscarPedidoPorCodigo(int codigo) {
	    if (!pedidos.containsKey(codigo)) {
	        throw new RuntimeException("El codigo de pedido no esta registrado en el sistema.");
	    }
	    return pedidos.get(codigo);
	}
	
	private List<Pedido> obtenerPedidosCerrados() {
	    List<Pedido> pedidosCerrados = new ArrayList<>();  
	    Iterator<Pedido> iterator = pedidos.values().iterator();
	    while (iterator.hasNext()) {
	        Pedido pedido = iterator.next();
	        if (pedido.estaCerrado()) {
	            pedidosCerrados.add(pedido);
	        }
	    }
	    return pedidosCerrados;
	}
	
	private void verificarDniClienteUnico(int dni, String nombre) {
		for (Pedido pedido: pedidos.values()) {
			if (pedido.obtenerDniCliente()==dni && !pedido.obtenerNombreCliente().equals(nombre)) {
				throw new RuntimeException("Ya se registro un cliente con otro nombre y el mismo DNI.");
			}
		}
	}

	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Amazing [\n");
	    sb.append("CUIT: ").append(CUIT).append("\n");
	    sb.append("Cantidad de Transportes registrados: ").append(transportes.size()).append("\n");
	    sb.append("Cantidad de Pedidos registrados: ").append(pedidos.size()).append("\n");
	    sb.append("]");
	    return sb.toString();
	}
	
}
