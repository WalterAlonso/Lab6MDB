/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.entities;

import java.util.Date;

/**
 *
 * @author WALTER
 */
public class Promocion {
    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------
    
    /**
     * Fecha de inicio de la promocion
     */
    private Date fechaInicio;
    
    /**
     * Fecha de fin de la promocion
     */
    private Date fechaFin;
    
    /**
     * Descripcion de la promocion
     */
    private String descripcion;
    
    /**
     * Tipo de mueble a la cual aplica la promocion.
     */
    private TipoMueble tipo;
        

    //-----------------------------------------------------------
    // Constructores
    //-----------------------------------------------------------

    /**
     * Constructor de la clase (sin argumentos)
     */
    public Promocion()
    {

    }

    /**
     * Constructor de la clase (con argumentos)
     * @param nombre Nombre del país
     * @param ciudades Lista con las ciudades del país
     */
    public Promocion(String descripcion, TipoMueble tipoMueble, Date fechaInicio, Date fechaFin)
    {
        this.descripcion = descripcion;
        this.tipo = tipoMueble;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    //-----------------------------------------------------------
    // Getters y setters
    //-----------------------------------------------------------

    /**
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the tipo
     */
    public TipoMueble getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(TipoMueble tipo) {
        this.tipo = tipo;
    }

  
}
