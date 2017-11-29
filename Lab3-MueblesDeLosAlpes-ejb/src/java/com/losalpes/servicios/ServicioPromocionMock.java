/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.servicios;

import com.losalpes.entities.Mueble;
import com.losalpes.entities.Promocion;
import com.losalpes.excepciones.OperacionInvalidaException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author WALTER
 */
@Stateless
public class ServicioPromocionMock implements IServicioPromocionMockRemote,IServicioPromocionMockLocal {
    
     //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------

    /**
     * Interface con referencia al servicio de persistencia en el sistema
     */
    @EJB
    private IServicioPersistenciaMockLocal persistencia;

    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------

    /**
     * Constructor sin argumentos de la clase
     */
    public ServicioPromocionMock()
    {
    }

    //-----------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------

    /**
     * Agrega una promocion al sistema
     * @param promocion Nueva promocion
     */
    @Override
    public void agregarPromocion(Promocion promocion)
    {
        try
        {
            persistencia.create(promocion);
        }
        catch (OperacionInvalidaException ex)
        {
            Logger.getLogger(ServicioPromocionMock.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    /**
     * Elimina una promocion del sistema
     * @param id Identificador único de la promocion a eliminar
     */
    @Override    
    public void eliminarPromocion(long id)
    {
        Promocion m=(Promocion) persistencia.findById(Promocion.class, id);
        try
        {
            persistencia.delete(m);
        }
        catch (OperacionInvalidaException ex)
        {
            Logger.getLogger(ServicioPromocionMock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Devuelve todas las promociones del sistema
     * @return promociones Lista de promociones
     */
    @Override
    public List<Promocion> darPromociones()
    {
        return persistencia.findAll(Promocion.class);
    }
}