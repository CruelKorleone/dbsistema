/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import database.Conexion;
import datos.ArticuloDAO;
import datos.CategoriaDAO;
import entidades.Articulo;
import entidades.Categoria;
import java.io.File;
import java.util.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author betto
 */
public class ArticuloControl {

    private final ArticuloDAO DATOS;
    private final CategoriaDAO DATOSCAT;
    private Articulo obj;
    public int registrosMostrados;
    private DefaultTableModel modeloTabla;

    public ArticuloControl() {
        this.DATOS = new ArticuloDAO();
        this.DATOSCAT = new CategoriaDAO();
        this.obj = new Articulo();
        this.registrosMostrados = 0;
    }

    public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina) throws ClassNotFoundException {
        List<Articulo> lista = new ArrayList();
        lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));

        String[] titulos = {"ID", "ID Categoria", "Categoria", "Código", "Nombre", "Precio venta", "Cantidad", "Descripción", "Imagen", "Estado"};

        this.modeloTabla = new DefaultTableModel(null, titulos);

        String estado;
        String[] registro = new String[10];

        this.registrosMostrados = 0;
        for (Articulo item : lista) {
            if (item.isActivo()) {
                estado = "Activo";
            } else {
                estado = "Inactivo";
            }
            registro[0] = Integer.toString(item.getId());
            registro[1] = Integer.toString(item.getCategoriaId());
            registro[2] = item.getCategoriaNombre().toUpperCase();
            registro[3] = item.getCodigo();
            registro[4] = item.getNombre().toUpperCase();
            registro[5] = Double.toString(item.getPrecioVenta());
            registro[6] = Integer.toString(item.getStock());
            registro[7] = item.getDescripcion().toUpperCase();
            registro[8] = item.getImagen();
            registro[9] = estado.toUpperCase();
            this.modeloTabla.addRow(registro);
            this.registrosMostrados = this.registrosMostrados + 1;
        }
        return this.modeloTabla;
    }

    public DefaultTableModel listarArticuloVenta(String texto, int totalPorPagina, int numPagina) throws ClassNotFoundException {
        List<Articulo> lista = new ArrayList();
        lista.addAll(DATOS.listarArticuloVenta(texto, totalPorPagina, numPagina));

        String[] titulos = {"ID", "ID Categoria", "Categoria", "Código", "Nombre", "Precio venta", "Cantidad", "Descripción", "Imagen", "Estado"};

        this.modeloTabla = new DefaultTableModel(null, titulos);

        String estado;
        String[] registro = new String[10];

        this.registrosMostrados = 0;
        for (Articulo item : lista) {
            if (item.isActivo()) {
                estado = "Activo";
            } else {
                estado = "Inactivo";
            }
            registro[0] = Integer.toString(item.getId());
            registro[1] = Integer.toString(item.getCategoriaId());
            registro[2] = item.getCategoriaNombre().toUpperCase();
            registro[3] = item.getCodigo();
            registro[4] = item.getNombre().toUpperCase();
            registro[5] = Double.toString(item.getPrecioVenta());
            registro[6] = Integer.toString(item.getStock());
            registro[7] = item.getDescripcion().toUpperCase();
            registro[8] = item.getImagen();
            registro[9] = estado.toUpperCase();
            this.modeloTabla.addRow(registro);
            this.registrosMostrados = this.registrosMostrados + 1;
        }
        return this.modeloTabla;
    }

    public DefaultComboBoxModel seleccionar() throws ClassNotFoundException {
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Categoria> lista = new ArrayList();
        lista = DATOSCAT.seleccionar();

        for (Categoria item : lista) {
            items.addElement(new Categoria(item.getId(), item.getNombre()));
        }
        return items;
    }

    public String insertar(int categoriaId, String codigo, String nombre, double precioVenta, int stock, String descripcion, String imagen) throws ClassNotFoundException {
        if (DATOS.existe(codigo)) {
            return "Ya existe una articulo con el mismo código";
        } else {
            obj.setCategoriaId(categoriaId);
            obj.setCodigo(codigo);
            obj.setNombre(nombre);
            obj.setPrecioVenta(precioVenta);
            obj.setStock(stock);
            obj.setDescripcion(descripcion);
            obj.setImagen(imagen);

            if (DATOS.insertar(obj)) {
                return "OK";
            } else {
                return "Error al insertar";
            }
        }
    }

    public String actualizar(int id, int categoriaId, String codigo, String codigoAnt, String nombre, double precioVenta, int stock, String descripcion, String imagen) throws ClassNotFoundException {
        if (codigo.equals(codigoAnt)) {
            obj.setId(id);
            obj.setCategoriaId(categoriaId);
            obj.setCodigo(codigo);
            obj.setNombre(nombre);
            obj.setPrecioVenta(precioVenta);
            obj.setStock(stock);
            obj.setDescripcion(descripcion);
            obj.setImagen(imagen);
            if (DATOS.actualizar(obj)) {
                return "OK";
            } else {
                return "Error al actualizar";
            }
        } else {
            if (DATOS.existe(codigo)) {
                return "Ya existe un articulo con el mismo código";
            } else {
                obj.setId(id);
                obj.setCategoriaId(categoriaId);
                obj.setCodigo(codigo);
                obj.setNombre(nombre);
                obj.setPrecioVenta(precioVenta);
                obj.setStock(stock);
                obj.setDescripcion(descripcion);
                obj.setImagen(imagen);
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

    public void reporteArticulos() throws ClassNotFoundException {
        Map p = new HashMap();
        JasperReport report;
        JasperPrint print;

        Conexion cnn = Conexion.getInstancia();
        try {
            report = JasperCompileManager.compileReport(new File("").getAbsolutePath()
                    + "/src/reportes/rptArticulos.jrxml");
            print = JasperFillManager.fillReport(report, p, cnn.conectar());
            JasperViewer view = new JasperViewer(print, false);
            view.setTitle("Reporte de articulos (Demo)");
            view.setVisible(true);
        } catch (JRException e) {
            e.getMessage();
        }
    }
}
