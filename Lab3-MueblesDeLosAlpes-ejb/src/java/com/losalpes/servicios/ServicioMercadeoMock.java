/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.servicios;

import com.losalpes.entities.Promocion;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
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
 * @author Juan Paz
 */
@Stateless
public class ServicioMercadeoMock implements IServicioMercadeoMockLocal {

    @Resource(mappedName = "jms/promocionCreadaTopicFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/promocionCreadaTopic")
    private Topic topic;

    private Promocion promocion;

    @Override
    public Message createPromocionMessage(Session session) throws JMSException {
        String msg = "Se ha creado un promocion: " + promocion.getId() + "\n";
        msg += "Tipo Mueble: " + promocion.getTipo().name() + "\n";
        TextMessage tm = session.createTextMessage();
        tm.setText(msg);
        tm.setStringProperty("type", "mercadeo");
        return tm;
    }

    @Override
    public void notificarPromocion(Promocion promocion) throws JMSException {
        this.promocion = promocion;
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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
