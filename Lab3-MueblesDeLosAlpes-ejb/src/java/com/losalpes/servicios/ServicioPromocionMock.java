/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.servicios;

import com.losalpes.entities.Mueble;
import com.losalpes.entities.Promocion;
import com.losalpes.entities.TipoMueble;
import com.losalpes.excepciones.OperacionInvalidaException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jms.JMSException;

/**
 *
 * @author WALTER
 */
@Stateless
public class ServicioPromocionMock implements IServicioPromocionMockRemote, IServicioPromocionMockLocal {

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------
    /**
     * Interface con referencia al servicio de persistencia en el sistema
     */
    @EJB
    private IServicioPersistenciaMockLocal persistencia;

    @EJB
    private IServicioVentasMockLocal ventas;

    @EJB
    private IServicioMercadeoMockLocal mercadeo;

    @EJB
    private IServicioCallCenterMockLocal callCenter;

    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------
    /**
     * Constructor sin argumentos de la clase
     */
    public ServicioPromocionMock() {
    }

    //-----------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------
    /**
     * Agrega una promocion al sistema
     *
     * @param promocion Nueva promocion
     */
    @Override
    public void agregarPromocion(Promocion promocion) {
        try {
            persistencia.create(promocion);

        } catch (OperacionInvalidaException ex) {
            Logger.getLogger(ServicioPromocionMock.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ventas.notificarPromocion(promocion);
        } catch (JMSException ex) {
            Logger.getLogger(ServicioPromocionMock.class.getName()).log(Level.SEVERE, "Error "
                    + "enviando la notificación de creación de un a promocion a Ventas", ex);
        }
        try {
            mercadeo.notificarPromocion(promocion);
        } catch (JMSException ex) {
            Logger.getLogger(ServicioPromocionMock.class.getName()).log(Level.SEVERE, "Error "
                    + "enviando la notificación de creación de un a promocion a Mercadeo", ex);
        }
        try {
            callCenter.notificarPromocion(promocion);
        } catch (JMSException ex) {
            Logger.getLogger(ServicioPromocionMock.class.getName()).log(Level.SEVERE, "Error "
                    + "enviando la notificación de creación de un a promocion a Call center", ex);
        }

    }

    /**
     * Elimina una promocion del sistema
     *
     * @param id Identificador único de la promocion a eliminar
     */
    @Override
    public void eliminarPromocion(long id) {
        Promocion m = (Promocion) persistencia.findById(Promocion.class, id);
        try {
            persistencia.delete(m);
        } catch (OperacionInvalidaException ex) {
            Logger.getLogger(ServicioPromocionMock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Devuelve todas las promociones del sistema
     *
     * @return promociones Lista de promociones
     */
    @Override
    public List<Promocion> darPromociones() {
        return persistencia.findAll(Promocion.class);
    }
    
    /**
     * Obtiene las p`romociones por tipo de mueble
     * @param tipoMueble tipo de mueble
     * @return las promociones del mueble.
     */
    @Override
    public List<Promocion> darPromocionesPorTipoMueble(TipoMueble tipoMueble){        
        List<Promocion> promociones =  persistencia.findAll(Promocion.class);
        List<Promocion> filteredPromociones = new ArrayList<>();
        for (Promocion promocion : promociones) {
            if(promocion.getTipo() == tipoMueble){
                filteredPromociones.add(promocion);
            }
        }
        return filteredPromociones;
    }
}
