/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * $Id$ ServicioCatalogoMock.java
 * Universidad de los Andes (Bogotá - Colombia)
 * Departamento de Ingeniería de Sistemas y Computación
 * Licenciado bajo el esquema Academic Free License version 3.0
 *
 * Ejercicio: Muebles de los Alpes
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.losalpes.servicios;

import com.losalpes.entities.Mueble;
import com.losalpes.entities.Promocion;
import com.losalpes.entities.TipoMueble;
import com.losalpes.excepciones.OperacionInvalidaException;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * Implementacion de los servicios del catálogo de muebles que se le prestan al
 * sistema.
 */
@Stateless
public class ServicioCatalogoMock implements IServicioCatalogoMockRemote, IServicioCatalogoMockLocal {

    //-----------------------------------------------------------
    // Atributos
    //-----------------------------------------------------------
    /**
     * Interface con referencia al servicio de persistencia en el sistema
     */
    @EJB
    private IServicioPersistenciaMockLocal persistencia;

    @Resource(mappedName = "jms/promocionCreadaTopicFactory")
    private ConnectionFactory connectionFactory;

    @Resource(mappedName = "jms/promocionCreadaTopic")
    private Topic topic;
    //-----------------------------------------------------------
    // Constructor
    //-----------------------------------------------------------

    private Mueble muebleC;

    /**
     * Constructor sin argumentos de la clase
     */
    public ServicioCatalogoMock() {
    }

    //-----------------------------------------------------------
    // Métodos
    //-----------------------------------------------------------
    /**
     * Agrega un mueble al sistema
     *
     * @param mueble Nuevo mueble
     */
    @Override
    public void agregarMueble(Mueble mueble) {
        try {
            List<Promocion> promociones = null;
            Promocion prom = new Promocion("Descripcion", TipoMueble.Interior, new Date(), new Date());
            promociones.add(prom);
            mueble.setPromociones(promociones);
            persistencia.create(mueble);

            muebleC = mueble;
        } catch (OperacionInvalidaException ex) {
            Logger.getLogger(ServicioCatalogoMock.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Se elimina un mueble del sistema dado su identificador único
     *
     * @param id Identificador único del mueble
     */
    @Override
    public void eliminarMueble(long id) {
        Mueble m = (Mueble) persistencia.findById(Mueble.class, id);
        try {
            persistencia.delete(m);
        } catch (OperacionInvalidaException ex) {
            Logger.getLogger(ServicioCatalogoMock.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Remueve un ejemplar del mueble (no el mueble)
     *
     * @param id Identificador único del mueble
     */
    @Override
    public void removerEjemplarMueble(long id) {
        ArrayList<Mueble> muebles = (ArrayList<Mueble>) persistencia.findAll(Mueble.class);
        Mueble mueble;
        for (int i = 0; i < muebles.size(); i++) {
            mueble = muebles.get(i);
            if (mueble.getReferencia() == id) {
                int cantidad = mueble.getCantidad();
                mueble.setCantidad(cantidad - 1);
                persistencia.update(mueble);
                break;
            }
        }
    }

    /**
     * Devuelve los muebles del sistema
     *
     * @return muebles Arreglo con todos los muebles del sistema
     */
    @Override
    public List<Mueble> darMuebles() {
        return persistencia.findAll(Mueble.class);
    }

    public Message createPromocionMessage(Session session) throws JMSException {
        ObjectMessage om = session.createObjectMessage();

        om.setObject(muebleC);

        om.setStringProperty("type", "producto");
        return om;
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
