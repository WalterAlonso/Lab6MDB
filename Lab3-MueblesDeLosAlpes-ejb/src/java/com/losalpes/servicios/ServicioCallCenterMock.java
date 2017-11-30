/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.servicios;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author Juan Paz
 */
@Stateless
public class ServicioCallCenterMock implements IServicioCallCenterMockLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void mostrarMessage(String msg) {
        Logger.getLogger(ServicioCallCenterMock.class.getName()).log(Level.INFO,
                "CallCenter: \n" + msg);
    }
}
