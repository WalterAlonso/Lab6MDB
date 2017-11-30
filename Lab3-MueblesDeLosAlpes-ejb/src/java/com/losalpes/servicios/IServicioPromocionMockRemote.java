/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.losalpes.servicios;

import com.losalpes.entities.Promocion;
import com.losalpes.entities.TipoMueble;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author WALTER
 */
@Remote
public interface IServicioPromocionMockRemote {
     /**
     * Agrega una promocion al sistema
     * @param promocion Nueva promocion
     */
    public void agregarPromocion(Promocion promocion);

    /**
     * Elimina una promocion del sistema
     * @param id Identificador único de la promocion a eliminar
     */
    public void eliminarPromocion(long id);

    /**
     * Devuelve todas las promociones del sistema
     * @return promociones Lista de promociones
     */
    public List<Promocion> darPromociones();
    
    /**
     * Devuelve todas las promociones del sistema segun tipo mueble
     * @param tipoMueble Tipo mueble
     * @return promociones Lista de promociones
     */
    public List<Promocion> darPromocionesPorTipoMueble(TipoMueble tipoMueble);
}
