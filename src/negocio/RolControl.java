/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;


import datos.RolDAO;
import entidades.Rol;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author betto
 */
public class RolControl {
    private final RolDAO DATOS;
    private Rol obj;
    public int registrosMostrados;
    private DefaultTableModel modeloTabla;
    
    public RolControl() {
        this.DATOS = new RolDAO();
        this.obj=new Rol();
        this.registrosMostrados = 0;
    }
    
    public DefaultTableModel listar() throws ClassNotFoundException {
        List<Rol> lista = new ArrayList();
        lista.addAll(DATOS.listar());

        String[] titulos = {"ID", "Nombre", "Descripción"};
        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[3];

        this.registrosMostrados = 0;
        for (Rol item : lista) {
            registro[0] = Integer.toString(item.getId());
            registro[1] = item.getNombre().toUpperCase();
            registro[2] = item.getDescripcion().toUpperCase();
            this.modeloTabla.addRow(registro);
            this.registrosMostrados = this.registrosMostrados + 1;
        }
        return this.modeloTabla;
    }
    
    public int total() throws ClassNotFoundException {
        return DATOS.total();
    }

    public int totalMostrados() {
        return this.registrosMostrados;
    }
}
