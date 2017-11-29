/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.jms;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
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
    @ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "type = 'mercadeo'")})
public class MercadeoMessage implements MessageListener {

    @Resource
    private MessageDrivenContext mdc;

    public MercadeoMessage() {
    }

    @Override
    public void onMessage(Message message) {
        TextMessage msg = null;
        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                Logger.getLogger(MercadeoMessage.class.getName()).log(Level.INFO,
                        "Mercadeo: \n" + msg.getText());
            } else {
                Logger.getLogger(MercadeoMessage.class.getName()).log(Level.SEVERE,
                        "Mercadeo: Mensaje de tipo equivocado: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            e.printStackTrace();
            mdc.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    }

}
