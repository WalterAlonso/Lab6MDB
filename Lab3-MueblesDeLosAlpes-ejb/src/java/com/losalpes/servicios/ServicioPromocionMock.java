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
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

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

    private Promocion promocion;

    @Resource(mappedName = "jms/promocionCreadaTopicFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/promocionCreadaTopic")
    private Topic topic;

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
            this.promocion = promocion;
        } catch (OperacionInvalidaException ex) {
            Logger.getLogger(ServicioPromocionMock.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            notificarPromocion();
        } catch (JMSException ex) {
            Logger.getLogger(ServicioPromocionMock.class.getName()).log(Level.SEVERE, null, ex);
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

    public Message createPromocionMessage(Session session) throws JMSException {
        String msg = "Se ha creado un promocion: " + promocion.getId() + "\n";
        msg += "Descripción: " + promocion.getDescripcion() + "\n";
        msg += "Tipo de Mueble: " + promocion.getTipo().name() + "\n";
        msg += "Fecha Inicio: " + promocion.getFechaInicio() + "\n";
        msg += "Fecha Fin: " + promocion.getFechaFin() + "\n";
        TextMessage tm = session.createTextMessage();
        tm.setText(msg);
        tm.setStringProperty("type", "promocion");
        return tm;
    }

    public void notificarPromocion() throws JMSException {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer messageProducer = session.createProducer((Destination) topic);

        try {
            messageProducer.send(createPromocionMessage(session));
        } catch (JMSException ex) {
            Logger.getLogger(ServicioVendedoresMock.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Error cerrando la"
                            + " sesión", e);
                }
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
