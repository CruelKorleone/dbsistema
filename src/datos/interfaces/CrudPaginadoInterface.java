/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos.interfaces;

import java.util.List;

/**
 *
 * @author betto
 */
public interface CrudPaginadoInterface<T> {


        public List<T> listar(String texto, int totalPorPagina, int numPagina) throws Exception;

        public boolean insertar(T obj) throws Exception;

        public boolean actualizar(T obj) throws Exception;

        public boolean desactivar(int id) throws Exception;

        public boolean activar(int id) throws Exception;

        public int total() throws Exception;

        public boolean existe(String texto) throws Exception;
}
