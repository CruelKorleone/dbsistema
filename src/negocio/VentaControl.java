/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import datos.ArticuloDAO;
import datos.VentaDAO;
import entidades.Articulo;
import entidades.DetalleVenta;
import entidades.Venta;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author betto
 */
public class VentaControl {

    private final VentaDAO DATOS;
    private final ArticuloDAO DATOSART;
    private Venta obj;
    public int registrosMostrados;
    private DefaultTableModel modeloTabla;

    public VentaControl() {
        this.DATOS = new VentaDAO();
        this.DATOSART = new ArticuloDAO();
        this.obj = new Venta();
        this.registrosMostrados = 0;
    }

    public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina) throws ClassNotFoundException, Exception {
        List<Venta> lista = new ArrayList();
        lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));

        String[] titulos = {"ID", "Cod. Usuario", "Usuario", "Cod. Cliente", "Cliente", "Tipo de Comp.", "Timbrado", "Número", "Fecha", "Impuesto", "Total", "Estado"};

        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[12];
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        this.registrosMostrados = 0;
        for (Venta item : lista) {
            registro[0] = Integer.toString(item.getId());
            registro[1] = Integer.toString(item.getUsuarioId());
            registro[2] = item.getUsuarioNombre().toUpperCase();
            registro[3] = Integer.toString(item.getPersonaId());
            registro[4] = item.getPersonaNombre().toUpperCase();
            registro[5] = item.getTipoComprobante().toUpperCase();
            registro[6] = item.getNumTimbrado();
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
        List<DetalleVenta> lista = new ArrayList();
        lista.addAll(DATOS.listarDetalle(id));

        String[] titulos = {"ID", "Código", "Articulo", "Stock", "Cantidad", "Precio", "Descuento", "Subtotal"};

        this.modeloTabla = new DefaultTableModel(null, titulos);

        String[] registro = new String[8];

        for (DetalleVenta item : lista) {
            registro[0] = Integer.toString(item.getArticuloId());
            registro[1] = item.getArticuloCodigo();
            registro[2] = item.getArticuloNombre().toUpperCase();
            registro[3] = Integer.toString(item.getArticuloStock());
            registro[4] = Integer.toString(item.getCantidad());
            registro[5] = Double.toString(item.getPrecio());
            registro[6] = Double.toString(item.getDescuento());
            registro[7] = Double.toString(item.getSubTotal());

            this.modeloTabla.addRow(registro);

        }
        return this.modeloTabla;
    }

    public Articulo obtenerArticuloCodigoVenta(String codigo) throws ClassNotFoundException {
        Articulo art = DATOSART.obtenerArticuloCodigoVenta(codigo);
        return art;
    }

    public String insertar(int personaId, String tipoComprobante, String timComprobante, String numComprobante, double impuesto, double total, DefaultTableModel modeloDetalles) throws ClassNotFoundException {
        if (DATOS.existe(timComprobante, numComprobante)) {
            return "Ya existe un registro con esos datos";
        } else {
            obj.setUsuarioId(Variables.usuarioId);
            obj.setPersonaId(personaId);
            obj.setTipoComprobante(tipoComprobante);
            obj.setNumTimbrado(timComprobante);
            obj.setNumComprobante(numComprobante);
            obj.setImpuesto(impuesto);
            obj.setTotal(total);

            List<DetalleVenta> detalles = new ArrayList();
            int articuloId;
            int cantidad;
            double precio;
            double descuento;

            for (int i = 0; i < modeloDetalles.getRowCount(); i++) {
                articuloId = Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 0)));
                cantidad = Integer.parseInt(String.valueOf(modeloDetalles.getValueAt(i, 4)));
                precio = Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 5)));
                descuento = Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 6)));
                detalles.add(new DetalleVenta(articuloId, cantidad, precio, descuento));
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
    
    public String ultimoTim(String tipoComprobante) throws ClassNotFoundException {
        return this.DATOS.ultimoTim(tipoComprobante);
    }
    
    public String ultimoNumero(String tipoComprobante,String timComprobante) throws ClassNotFoundException {
        return this.DATOS.ultimoNumero(tipoComprobante, timComprobante);
    }
}
