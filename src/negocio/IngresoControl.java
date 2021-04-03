/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import datos.ArticuloDAO;
import datos.IngresoDAO;
import entidades.Articulo;
import entidades.DetalleIngreso;
import entidades.Ingreso;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author betto
 */
public class IngresoControl {

    private final IngresoDAO DATOS;
    private final ArticuloDAO DATOSART;
    private Ingreso obj;
    public int registrosMostrados;
    private DefaultTableModel modeloTabla;

    public IngresoControl() {
        this.DATOS = new IngresoDAO();
        this.DATOSART=new ArticuloDAO();
        this.obj = new Ingreso();
        this.registrosMostrados = 0;
    }

    public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina) throws ClassNotFoundException, Exception {
        List<Ingreso> lista = new ArrayList();
        lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));

        String[] titulos = {"ID", "Cod. Usuario", "Usuario", "Cod. Prov.", "Proveedor", "Tipo de Comp.", "Timbrado", "Número", "Fecha", "Impuesto", "Total", "Estado"};

        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[12];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        this.registrosMostrados = 0;
        for (Ingreso item : lista) {
            registro[0] = Integer.toString(item.getId());
            registro[1] = Integer.toString(item.getUsuarioId());
            registro[2] = item.getUsuarioNombre().toUpperCase();
            registro[3] = Integer.toString(item.getPersonaId());
            registro[4] = item.getPersonaNombre().toUpperCase();
            registro[5] = item.getTipoComprobante().toUpperCase();
            registro[6] = item.getTimComprobante();
            registro[7] = item.getNumComprobante();
            registro[8] = sdf.format(item.getFecha());
            registro[9] = Double.toString(item.getImpuesto());
            registro[10] = Double.toString(item.getTotal());
            registro[11] = item.getEstado().toUpperCase();
            this.modeloTabla.addRow(registro);
            this.registrosMostrados = this.registrosMostrados + 1;
        }
        return this.modeloTabla;
    }
    
    public DefaultTableModel listarDetalle(int id) throws ClassNotFoundException, Exception {
        List<DetalleIngreso> lista = new ArrayList();
        lista.addAll(DATOS.listarDetalle(id));

        String[] titulos = {"ID", "Código", "Articulo", "Cantidad", "Precio", "Subtotal"};

        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[6];

        for (DetalleIngreso item : lista) {
            registro[0] = Integer.toString(item.getArticuloId());
            registro[1] = item.getArticuloCodigo();
            registro[2] = item.getArticuloNombre().toUpperCase();
            registro[3] = Integer.toString(item.getCantidad());
            registro[4] = Double.toString(item.getPrecio());
            registro[5] = Double.toString(item.getSubTotal());

            this.modeloTabla.addRow(registro);

        }
        return this.modeloTabla;
    }
    
    public Articulo obtenerArticuloCodigoCompras(String codigo) throws ClassNotFoundException {
        Articulo art = DATOSART.obtenerArticuloCodigoCompras(codigo);
        return art;
    }

    public String insertar(int personaId, String tipoComprobante, String timComprobante, String numComprobante, double impuesto, double total, DefaultTableModel modeloDetalles) throws ClassNotFoundException {
        if (DATOS.existe(timComprobante, numComprobante)) {
            return "Ya existe un registro con esos datos";
        } else {
            obj.setUsuarioId(Variables.usuarioId);
            obj.setPersonaId(personaId);
            obj.setTipoComprobante(tipoComprobante);
            obj.setTimComprobante(timComprobante);
            obj.setNumComprobante(numComprobante);
            obj.setImpuesto(impuesto);
            obj.setTotal(total);

            List<DetalleIngreso> detalles = new ArrayList();
            int articuloId;
            int cantidad;
            double precio;

            for (int i = 0; i < modeloDetalles.getRowCount(); i++) {
                articuloId = Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 0)));
                cantidad = Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 3)));
                precio = Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 4)));
                detalles.add(new DetalleIngreso(articuloId,cantidad,precio));
            }
            obj.setDetalles(detalles);
            if (DATOS.insertar(obj)) {
                return "OK";
            } else {
                return "Error al insertar";
            }
        }
    }

    public String anular(int id) throws ClassNotFoundException {
        if (DATOS.anular(id)) {
            return "OK";
        } else {
            return "Error al desactivar";
        }
    }

    public int total() throws ClassNotFoundException {
        return DATOS.total();
    }

    public int totalMostrados() {
        return this.registrosMostrados;
    }
}
