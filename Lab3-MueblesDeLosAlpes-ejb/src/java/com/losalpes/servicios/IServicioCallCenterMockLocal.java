/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.servicios;

import com.losalpes.entities.Promocion;
import javax.ejb.Local;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 *
 * @author Juan Paz
 */
@Local
public interface IServicioCallCenterMockLocal {

    public Message createPromocionMessage(Session session) throws JMSException;

    public void notificarPromocion(Promocion promocion) throws JMSException;

}
