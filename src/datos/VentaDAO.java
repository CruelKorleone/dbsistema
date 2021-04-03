/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.sql.Statement;
import java.sql.Connection;
import database.Conexion;
import datos.interfaces.CrudVentaInterface;
import entidades.DetalleVenta;
import entidades.Venta;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author betto
 */
public class VentaDAO implements CrudVentaInterface<Venta, DetalleVenta> {

    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public VentaDAO() {
        CON = Conexion.getInstancia();
    }

    @Override
    public List<Venta> listar(String texto, int totalPorPagina, int numPagina) throws Exception {
        List<Venta> registros = new ArrayList();
        try {
            ps = CON.conectar().prepareStatement("SELECT v.id, v.usuario_id, u.nombre as usuario_nombre, v.persona_id, "
                    + "p.nombre as persona_nombre, v.tipo_comprobante, v.tim_comprobante,v.num_comprobante, v.fecha, v.impuesto,v.total, "
                    + "v.estado FROM venta v  "
                    + "INNER JOIN persona p ON v.persona_id = p.id "
                    + "INNER JOIN usuario u ON v.usuario_id = u.id "
                    + "WHERE v.num_comprobante LIKE ? ORDER BY v.id ASC LIMIT ?,?;");
            ps.setString(1, "%" + texto + "%");
            ps.setInt(2, (numPagina - 1) * totalPorPagina);
            ps.setInt(3, totalPorPagina);
            rs = ps.executeQuery();

            while (rs.next()) {
                registros.add(new Venta(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getDate(9), rs.getDouble(10), rs.getDouble(11), rs.getString(12)));
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CON.desconectar();
        }
        return registros;
    }

    @Override
    public List<DetalleVenta> listarDetalle(int id) {
        List<DetalleVenta> registros = new ArrayList();
        try {
            try {
                ps = CON.conectar().prepareStatement("SELECT a.id, a.codigo,a.nombre,a.stock,d.cantidad, d.precio,d.descuento,((d.cantidad * d.precio)-descuento) as sub_total FROM detalle_venta d INNER JOIN articulo a ON d.articulo_id=a.id WHERE d.venta_id=?");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(VentaDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            ps.setInt(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                registros.add(new DetalleVenta(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8)));
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CON.desconectar();
        }
        return registros;
    }

    @Override
    public boolean insertar(Venta obj) throws ClassNotFoundException {
        resp = false;
        Connection conn = null;
        try {
            conn = CON.conectar();
            conn.setAutoCommit(false);
            String sqlInsertVenta = "INSERT INTO venta (persona_id, usuario_id, fecha, tipo_comprobante, tim_comprobante, num_comprobante, impuesto, total, estado) VALUES (?,?,now(),?,?,?,?,?,?)";

            ps = conn.prepareStatement(sqlInsertVenta, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, obj.getPersonaId());
            ps.setInt(2, obj.getUsuarioId());
            ps.setString(3, obj.getTipoComprobante());
            ps.setString(4, obj.getNumTimbrado());
            ps.setString(5, obj.getNumComprobante());
            ps.setDouble(6, obj.getImpuesto());
            ps.setDouble(7, obj.getTotal());
            ps.setString(8, "Aceptado");

            int filasAfectadas = ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            int idGenerado = 0;
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

            //Si se pudo ingresar la cabecera, entonces inserto los detalles
            if (filasAfectadas == 1) {
                String sqlInsertDetalle = "INSERT INTO detalle_venta (venta_id, articulo_id, cantidad, precio,descuento) VALUES(?,?,?,?,?)";
                ps = conn.prepareStatement(sqlInsertDetalle);

                for (DetalleVenta item : obj.getDetalles()) {
                    ps.setInt(1, idGenerado);
                    ps.setInt(2, item.getArticuloId());
                    ps.setInt(3, item.getCantidad());
                    ps.setDouble(4, item.getPrecio());
                    ps.setDouble(5, item.getDescuento());
                    resp = ps.executeUpdate() > 0;
                }
                //Manejo manualmente los commits
                conn.commit();
            } else {
                conn.rollback();
            }

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
                JOptionPane.showMessageDialog(null, e.getMessage());
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(VentaDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(VentaDAO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }

        }
        return resp;
    }

    @Override
    public boolean anular(int id) throws ClassNotFoundException {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("UPDATE venta SET estado='Anulado' WHERE id=?");
            ps.setInt(1, id);

            if (ps.executeUpdate() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            CON.desconectar();
        }
        return resp;
    }

    @Override
    public int total() throws ClassNotFoundException {
        int totalRegistros = 0;
        try {
            ps = CON.conectar().prepareStatement("SELECT COUNT(id) FROM venta");
            rs = ps.executeQuery();

            while (rs.next()) {
                totalRegistros = rs.getInt("COUNT(id)");
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CON.desconectar();
        }
        return totalRegistros;
    }

    @Override
    public boolean existe(String texto1, String texto2) throws ClassNotFoundException {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("SELECT id FROM venta WHERE tim_comprobante=? AND num_comprobante=?");
            ps.setString(1, texto1);
            ps.setString(2, texto2);
            rs = ps.executeQuery();
            rs.next();

            if (rs.getRow() > 0) {
                resp = true;
            }
            ps.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CON.desconectar();
        }
        return resp;
    }

        public String ultimoTim(String tipoComprobante) throws ClassNotFoundException {
        String timComprobante = "";
        try {
            ps = CON.conectar().prepareStatement("SELECT tim_comprobante FROM venta where tim_comprobante=? order by tim_comprobante desc limit 1");
            ps.setString(1, tipoComprobante);
            rs = ps.executeQuery();

            while (rs.next()) {
                timComprobante = rs.getString("tim_comprobante");
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CON.desconectar();
        }
        return timComprobante;
    }

    public String ultimoNumero(String tipoComprobante, String timComprobante) throws ClassNotFoundException {
        String numComprobante = "";
        try {
            ps = CON.conectar().prepareStatement("SELECT num_comprobante FROM venta WHERE tipo_comprobante=? AND tim_comprobante=? order by num_comprobante desc limit 1");
            ps.setString(1, tipoComprobante);
            ps.setString(2, timComprobante);
            rs = ps.executeQuery();

            while (rs.next()) {
                numComprobante = rs.getString("num_comprobante");
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } finally {
            ps = null;
            rs = null;
            CON.desconectar();
        }
        return numComprobante;
    }

}
