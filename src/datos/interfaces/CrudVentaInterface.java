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
public interface CrudVentaInterface<T,D> {
    public List<T> listar(String texto, int totalPorPagina, int numPagina) throws Exception;
    public List<D> listarDetalle(int id);
    public boolean insertar(T obj) throws Exception;
    public boolean anular(int id) throws Exception;
    public int total() throws Exception;
    public boolean existe(String texto1, String texto2) throws Exception;
}
