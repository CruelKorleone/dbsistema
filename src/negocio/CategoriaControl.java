/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import datos.CategoriaDAO;
import entidades.Categoria;
import java.util.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author betto
 */
public class CategoriaControl {

    private final CategoriaDAO DATOS;
    private Categoria obj;
    public int registrosMostrados;
    private DefaultTableModel modeloTabla;

    public CategoriaControl() {
        this.DATOS = new CategoriaDAO();
        this.obj = new Categoria();
        this.registrosMostrados = 0;
    }

    public DefaultTableModel listar(String texto) throws ClassNotFoundException {
        List<Categoria> lista = new ArrayList();
        lista.addAll(DATOS.listar(texto));

        String[] titulos = {"ID", "Nombre", "Descripción", "Estado"};
        this.modeloTabla = new DefaultTableModel(null, titulos);

        String estado;
        String[] registro = new String[4];

        this.registrosMostrados = 0;
        for (Categoria item : lista) {
            if (item.isActivo()) {
                estado = "Activo";
            } else {
                estado = "Inactivo";
            }
            registro[0] = Integer.toString(item.getId());
            registro[1] = item.getNombre().toUpperCase();
            registro[2] = item.getDescripcion().toUpperCase();
            registro[3] = estado.toUpperCase();
            this.modeloTabla.addRow(registro);
            this.registrosMostrados = this.registrosMostrados + 1;
        }
        return this.modeloTabla;
    }

    public String insertar(String nombre, String descripcion) throws ClassNotFoundException {
        if (DATOS.existe(nombre)) {
            return "Ya existe una categoria con el mismo nombre";
        } else {
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);

            if (DATOS.insertar(obj)) {
                return "OK";
            } else {
                return "Error al insertar";
            }
        }
    }

    public String actualizar(int id, String nombre, String nombreAnt, String descripcion) throws ClassNotFoundException {
        if (nombre.equals(nombreAnt)) {
            obj.setId(id);
            obj.setNombre(nombre);
            obj.setDescripcion(descripcion);
            if (DATOS.actualizar(obj)) {
                return "OK";
            } else {
                return "Error al actualizar";
            }
        } else {
            if (DATOS.existe(nombre)) {
                return "Ya existe una categoria con el mismo nombre";
            } else {
                obj.setId(id);
                obj.setNombre(nombre);
                obj.setDescripcion(descripcion);
                if (DATOS.actualizar(obj)) {
                    return "OK";
                } else {
                    return "Error al actualizar";
                }
            }
        }
    }

    public String desactivar(int id) throws ClassNotFoundException {
        if (DATOS.desactivar(id)) {
            return "OK";
        } else {
            return "Error al desactivar";
        }
    }

    public String activar(int id) throws ClassNotFoundException {
        if (DATOS.activar(id)) {
            return "OK";
        } else {
            return "Error al activar";
        }
    }

    public int total() throws ClassNotFoundException {
        return DATOS.total();
    }

    public int totalMostrados() {
        return this.registrosMostrados;
    }
}
