package com.eljavatar.udistrital.ritmocardiaco.model;

public enum SexoEnum {

	M("Masculino"),
	F("Femenino");
	
	private final String descripcion;
	
	private SexoEnum(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

}
