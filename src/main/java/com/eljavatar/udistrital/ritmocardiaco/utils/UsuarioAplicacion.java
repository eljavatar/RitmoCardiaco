package com.eljavatar.udistrital.ritmocardiaco.utils;

import java.io.Serializable;

public class UsuarioAplicacion implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -3057946615752572775L;

    private Integer id;
    private String username;
    private String password;
    private String nombres;
    private String apellidos;
    private String nombreCompleto;

    public UsuarioAplicacion(Integer id, String username, String password, String nombres, String apellidos, String nombreCompleto) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nombreCompleto = nombreCompleto;
    }

    public UsuarioAplicacion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

}
