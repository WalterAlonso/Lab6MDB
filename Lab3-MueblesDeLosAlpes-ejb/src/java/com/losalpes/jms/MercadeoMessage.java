/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.jms;

import com.losalpes.entities.Mueble;
import com.losalpes.entities.Promocion;
import com.losalpes.servicios.IServicioCallCenterMockLocal;
import com.losalpes.servicios.IServicioMercadeoMockLocal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 *
 * @author Juan Paz
 */
@MessageDriven(mappedName = "jms/promocionCreadaTopic", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
    ,
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic")
    ,
    @ActivationConfigProperty(propertyName = "subscriptionDurability", propertyValue = "Durable")
    ,
    @ActivationConfigProperty(propertyName = "clientId", propertyValue = "MercadeoMessage")
    ,
    @ActivationConfigProperty(propertyName = "subscriptionName", propertyValue = "MercadeoMessage")
    ,
    @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "type = 'producto'")})
public class MercadeoMessage implements MessageListener {

    @Resource
    private MessageDrivenContext mdc;

    @EJB
    private IServicioMercadeoMockLocal mercadeo;

    public MercadeoMessage() {
    }

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage msg = null;
            if (message instanceof ObjectMessage) {
                msg = (ObjectMessage) message;
                Mueble mueble;
                mueble = (Mueble) msg.getObject();

                String txt = createMessage(mueble);
                mercadeo.mostrarMessage(txt);
            } else {
                Logger.getLogger(MercadeoMessage.class.getName()).log(Level.SEVERE,
                        "Mercadeo: Mensaje de tipo equivocado: " + message.getClass().getName());
            }
        } catch (Throwable te) {
            te.printStackTrace();
            mdc.setRollbackOnly();
        }

    }

    private String createMessage(Mueble mueble) {
        String msg = "Se ha añadido una promocion a un mueble:\n";
        msg += "Nombre: " + mueble.getNombre() + "\n";
        msg += "Descripción: " + mueble.getDescripcion() + "\n";
        msg += "Precio: " + mueble.getPrecio() + "\n";
        msg += "Referencia: " + mueble.getReferencia() + "\n";
        msg += "Tipo: " + mueble.getTipo().name() + "\n";

        return msg;
    }
}
