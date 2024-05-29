package Amazing;

import java.util.ArrayList;
import java.util.List;

public abstract class Transporte {
	private String patente;
	private int volumenMaximoDeCarga;
	private double valorPorViaje;
	private List<Paquete> carga;

	public Transporte(String patente, int volumenMaximoDeCarga, double valorPorViaje) {
		if (patente == null || patente.isEmpty()) {
			throw new IllegalArgumentException("La patente no puede ser nula o vacia.");
		}
		if (volumenMaximoDeCarga <= 0) {
			throw new IllegalArgumentException("El volumen maximo de carga debe ser mayor que cero.");
		}
		if (valorPorViaje <= 0) {
			throw new IllegalArgumentException("El valor por viaje debe ser mayor que cero.");
		}
		this.patente = patente;
		this.volumenMaximoDeCarga = volumenMaximoDeCarga;
		this.valorPorViaje = valorPorViaje;
		this.carga = new ArrayList<>();
	}

	public String obtenerIdentificador() {
		return patente;
	}

	public int obtenerVolumenMaximoDeCarga() {
		return volumenMaximoDeCarga;
	}

	public double obtenerCostoViaje() {
		if (obtenerCantidadDePaquetesEnCarga() == 0) {
			throw new RuntimeException("Transporte no cargado");
		}
		return valorPorViaje;
	}

	public int obtenerVolumenCarga() {
		int volumenCarga = 0;
		for (Paquete paquete : carga) {
			volumenCarga += paquete.obtenerVolumen();
		}
		return volumenCarga;
	}

	public boolean cargarPaquete(Paquete paquete) {
	    if (puedeCargarPaquete(paquete)) {
	        carga.add(paquete);
	        return true;
	    }
	    return false;
	}

	public abstract boolean puedeCargarPaquete(Paquete paquete);

	public int obtenerCantidadDePaquetesEnCarga() {
		return carga.size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Patente: ").append(patente).append(", ");
		sb.append("Volumen Maximo de Carga: ").append(volumenMaximoDeCarga).append(", ");
		sb.append("Valor por Viaje: ").append(valorPorViaje).append(", ");
		sb.append("Carga: ").append(carga);
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
		Transporte otroTransporte = (Transporte) obj;
		return obtenerCantidadDePaquetesEnCarga() > 0
				&& tieneLaMismaCarga(otroTransporte);

	}

	private boolean tieneLaMismaCarga(Transporte otro) {
		if (carga.size() != otro.carga.size()) {
			return false;
		}
		for (Paquete paquete : carga) {
			if (!otro.carga.contains(paquete)) {
				return false;
			}
		}
		return true;
	}

}
