/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import database.Conexion;
import datos.interfaces.CrudPaginadoInterface;
import entidades.Articulo;
import java.util.*;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author betto
 */
public class ArticuloDAO implements CrudPaginadoInterface<Articulo> {

    private final Conexion CON;
    private PreparedStatement ps;
    private ResultSet rs;
    private boolean resp;

    public ArticuloDAO() {
        CON = Conexion.getInstancia();
    }

    @Override
    public List<Articulo> listar(String texto, int totalPorPagina, int numPagina) throws ClassNotFoundException {
        List<Articulo> registros = new ArrayList();
        try {
            ps = CON.conectar().prepareStatement("SELECT a.id, a.categoria_id, c.nombre as categoria_nombre, a.codigo, a.nombre, "
                    + "a.precio_venta, a.stock, a.descripcion, a.imagen, a.activo "
                    + "FROM articulo a INNER JOIN categoria c "
                    + "ON a.categoria_id = c.id WHERE a.nombre LIKE ? ORDER BY a.id ASC LIMIT ?, ?");
            ps.setString(1, "%" + texto + "%");
            ps.setInt(2, (numPagina - 1) * totalPorPagina);
            ps.setInt(3, totalPorPagina);
            rs = ps.executeQuery();

            while (rs.next()) {
                registros.add(new Articulo(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getBoolean(10)));
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
    
    public List<Articulo> listarArticuloVenta(String texto, int totalPorPagina, int numPagina) throws ClassNotFoundException {
        List<Articulo> registros = new ArrayList();
        try {
            ps = CON.conectar().prepareStatement("SELECT a.id, a.categoria_id, c.nombre as categoria_nombre, a.codigo, a.nombre, "
                    + "a.precio_venta, a.stock, a.descripcion, a.imagen, a.activo "
                    + "FROM articulo a INNER JOIN categoria c "
                    + "ON a.categoria_id = c.id WHERE a.nombre LIKE ? AND a.stock > 0 AND a.activo = true ORDER BY a.id ASC LIMIT ?, ?");
            ps.setString(1, "%" + texto + "%");
            ps.setInt(2, (numPagina - 1) * totalPorPagina);
            ps.setInt(3, totalPorPagina);
            rs = ps.executeQuery();

            while (rs.next()) {
                registros.add(new Articulo(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDouble(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getBoolean(10)));
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

    public Articulo obtenerArticuloCodigoCompras(String codigo) throws ClassNotFoundException {
        Articulo art = null;

        try {
            ps = CON.conectar().prepareStatement("SELECT id, codigo, nombre, precio_venta, stock FROM articulo WHERE codigo = ?");
            ps.setString(1, codigo);
            rs = ps.executeQuery();

            if (rs.next()) {
                art = new Articulo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
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
        return art;
    }
    
    public Articulo obtenerArticuloCodigoVenta(String codigo) throws ClassNotFoundException {
        Articulo art = null;

        try {
            ps = CON.conectar().prepareStatement("SELECT id, codigo, nombre, precio_venta, stock FROM articulo WHERE codigo = ? AND stock > 0 AND activo = true");
            ps.setString(1, codigo);
            rs = ps.executeQuery();

            if (rs.next()) {
                art = new Articulo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getInt(5));
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
        return art;
    }

    @Override
    public boolean insertar(Articulo obj) throws ClassNotFoundException {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("INSERT INTO articulo(categoria_id, codigo, nombre, precio_venta, stock, descripcion, "
                    + "imagen, activo) VALUES (?,?,?,?,?,?,?,1)");
            ps.setInt(1, obj.getCategoriaId());
            ps.setString(2, obj.getCodigo());
            ps.setString(3, obj.getNombre());
            ps.setDouble(4, obj.getPrecioVenta());
            ps.setInt(5, obj.getStock());
            ps.setString(6, obj.getDescripcion());
            ps.setString(7, obj.getImagen());

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
    public boolean actualizar(Articulo obj) throws ClassNotFoundException {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("UPDATE articulo SET categoria_id=?, codigo=?, nombre=?, precio_venta=?, "
                    + "stock=?, descripcion=?, imagen=? WHERE id=?");
            ps.setInt(1, obj.getCategoriaId());
            ps.setString(2, obj.getCodigo());
            ps.setString(3, obj.getNombre());
            ps.setDouble(4, obj.getPrecioVenta());
            ps.setInt(5, obj.getStock());
            ps.setString(6, obj.getDescripcion());
            ps.setString(7, obj.getImagen());
            ps.setInt(8, obj.getId());

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
    public boolean desactivar(int id) throws ClassNotFoundException {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("UPDATE articulo SET activo=0 WHERE id=?");
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
    public boolean activar(int id) throws ClassNotFoundException {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("UPDATE articulo SET activo=1 WHERE id=?");
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
            ps = CON.conectar().prepareStatement("SELECT COUNT(id) FROM articulo");
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
    public boolean existe(String texto) throws ClassNotFoundException {
        resp = false;
        try {
            ps = CON.conectar().prepareStatement("SELECT codigo FROM articulo WHERE codigo=?");
            ps.setString(1, texto);
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

}
