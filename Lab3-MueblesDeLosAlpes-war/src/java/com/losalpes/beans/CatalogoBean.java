/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$ CatalogoBean.java
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 * Licenciado bajo el esquema Academic Free License version 3.0
 *
 * Ejercicio: Muebles de los Alpes
 * 
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */

package com.losalpes.beans;

import com.losalpes.entities.Mueble;
import com.losalpes.entities.Promocion;
import com.losalpes.entities.TipoMueble;
import com.losalpes.servicios.IServicioCatalogoMockLocal;
import com.losalpes.servicios.IServicioPromocionMockLocal;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;


/**
 * Managed bean encargado del catálogo de muebles en el sistema
 * 
 */
public class CatalogoBean implements Serializable
{

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------

    /**
     * Representa un nuevo mueble a ingresar
     */
    private Mueble mueble;

    public Promocion promocion;
    
    public List<Promocion> promociones;
    
    /**
     * Relación con la interfaz que provee los servicios necesarios del catálogo.
     */
    @EJB
    private IServicioCatalogoMockLocal catalogo;
    
    @EJB
    private IServicioPromocionMockLocal servicioPromocion;

    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------

    /**
     * Constructor de la clase principal
     */
    public CatalogoBean()
    {
        mueble=new Mueble();
    }

    //-----------------------------------------------------------
    // Getters y setters
    //-----------------------------------------------------------

    /**
     * Devuelve el objeto mueble
     * @return mueble Objeto mueble
     */
    public Mueble getMueble()
    {
        return mueble;
    }

    /**
     * Modifica el objeto mueble
     * @param mueble Nuevo mueble
     */
    public void setMueble(Mueble mueble)
    {
        this.mueble = mueble;
    }

    /**
     * Devuelve una lista con todos los muebles del sistema
     * @return muebles Muebles del sistema
     */
    public List<Mueble> getMuebles()
    {

        return catalogo.darMuebles();
    }

    /**
     * @return the promocion
     */
    public Promocion getPromocion() {
        return promocion;
    }

    /**
     * @param promocion the promocion to set
     */
    public void setPromocion(Promocion promocion) {
        this.promocion = promocion;
    }

    /**
     * @return the promociones
     */
    public List<Promocion> getPromociones() {
        return promociones;
    }

    /**
     * @param promociones the promociones to set
     */
    public void setPromociones(List<Promocion> promociones) {
        this.promociones = promociones;
    }

    //-----------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------

    /**
     * Agrega un nuevo mueble al sistema
     */
    public void agregarMueble()
    {
        if (promocion != null){
            catalogo.agregarPromocionAMueble(promocion, mueble);
        }
        catalogo.agregarMueble(mueble);
        mueble=new Mueble();
    }

    /**
     * Elimina un mueble del sistema
     * @param evento Evento que tiene como parámetro el ID del mueble
     */
    public void eliminarMueble(ActionEvent evento)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        Map map = context.getExternalContext().getRequestParameterMap();
        long inventoryId = Long.parseLong((String) map.get("muebleId"));

        catalogo.eliminarMueble(inventoryId);
    }
    
    /**
     * Devuelve los tipos de muebles
     * @return sitems Tipos de muebles en el sistema
     */
    public SelectItem[] getTiposMuebles()
    {
        TipoMueble[] tipos=  TipoMueble.values();
        SelectItem[] sitems = new SelectItem[tipos.length];
        
        for (int i = 0; i < sitems.length; i++)
        {
             sitems[i] = new SelectItem(tipos[i]);
        }
        
        //mueble.setTipo(tipos[0]);
        //changePromocion(null);
        return sitems;
    }
    
    /**
     * Elimina la información del mueble
     */
    public void limpiar()
    {
        mueble=new Mueble();
    }
    
    public void changePromocion(AjaxBehaviorEvent e){
        if (mueble.getTipo() != null) {           
            setPromociones(servicioPromocion.darPromocionesPorTipoMueble(mueble.getTipo()));
        }
    }
    /*catalogoBean.changePromocion
catalogoBean.promocion
catalogoBean.promociones*/
}
