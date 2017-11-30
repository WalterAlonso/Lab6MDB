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
public class ServicioMercadeoMock implements IServicioMercadeoMockLocal {

    @Override
    public void mostrarMessage(String msg) {
        Logger.getLogger(ServicioMercadeoMock.class.getName()).log(Level.INFO,
                "Mercadeo: \n" + msg);
    }
}
