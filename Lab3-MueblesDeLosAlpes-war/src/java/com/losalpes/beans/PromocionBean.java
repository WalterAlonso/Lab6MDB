/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.beans;

import com.losalpes.entities.Promocion;
import com.losalpes.servicios.IServicioPromocionMockLocal;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author WALTER
 */
public class PromocionBean implements Serializable {
 //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------

    /**
     * Representa una nueva promocion a ingresar
     */
    private Promocion promocion;

    /**
     * Relación con la interfaz que provee los servicios necesarios del catálogo.
     */
    @EJB
    private IServicioPromocionMockLocal promociones;

    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------

    /**
     * Constructor de la clase principal
     */
    public PromocionBean()
    {
        promocion=new Promocion();
    }

    //-----------------------------------------------------------
    // Getters y setters
    //-----------------------------------------------------------

    /**
     * Devuelve el objeto promocion
     * @return promocion Objeto promocion
     */
    public Promocion getPromocion()
    {
        return promocion;
    }

    /**
     * Modifica el objeto promocion
     * @param promocion Nuevo promocion
     */
    public void setPromocion(Promocion promocion)
    {
        this.promocion = promocion;
    }

    /**
     * Devuelve una lista con todas las promociones del sistema
     * @return promociones Promociones del sistema
     */
    public List<Promocion> getPromociones()
    {
        return promociones.darPromociones();
    }

    //-----------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------

    /**
     * Agrega un nuevo mueble al sistema
     */
    public void agregarPromocion()
    {
        promociones.agregarPromocion(promocion);
        promocion=new Promocion();
    }

    /**
     * Elimina un mueble del sistema
     * @param evento Evento que tiene como parámetro el ID del mueble
     */
    public void eliminarPromocion()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        long promocionId = Long.parseLong((String) map.get("promocionId"));

        promociones.eliminarPromocion(promocionId);
    }
     
    
    /**
     * Elimina la información del mueble
     */
    public void limpiar()
    {
        promocion=new Promocion();
    }
    
}
