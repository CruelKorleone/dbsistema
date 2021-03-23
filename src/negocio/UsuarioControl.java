/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import datos.RolDAO;
import datos.UsuarioDAO;
import entidades.Rol;
import entidades.Usuario;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author betto
 */
public class UsuarioControl {

    private final UsuarioDAO DATOS;
    private final RolDAO DATOSROL;
    private Usuario obj;
    private DefaultTableModel modeloTabla;
    public int registrosMostrados;

    public UsuarioControl() {
        this.DATOS = new UsuarioDAO();
        this.DATOSROL = new RolDAO();
        this.obj = new Usuario();
        this.registrosMostrados = 0;
    }

    public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina) throws ClassNotFoundException {
        List<Usuario> lista = new ArrayList();
        lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));

        String[] titulos = {"ID", "Rol ID", "Rol", "Usuario", "Documento", "Num. Doc.", "Dirección", "Teléfono", "Email", "Clave", "Estado"};

        this.modeloTabla = new DefaultTableModel(null, titulos);

        String estado;
        String[] registro = new String[11];

        this.registrosMostrados = 0;
        for (Usuario item : lista) {
            if (item.isActivo()) {
                estado = "Activo";
            } else {
                estado = "Inactivo";
            }
            registro[0] = Integer.toString(item.getId());
            registro[1] = Integer.toString(item.getRolId());
            registro[2] = item.getRolNombre().toUpperCase();
            registro[3] = item.getNombre().toUpperCase();
            registro[4] = item.getTipoDocumento().toUpperCase();
            registro[5] = item.getNumDocumento();
            registro[6] = item.getDireccion().toUpperCase();
            registro[7] = item.getTelefono();
            registro[8] = item.getEmail().toLowerCase();
            registro[9] = item.getClave();
            registro[10] = estado.toUpperCase();
            this.modeloTabla.addRow(registro);
            this.registrosMostrados = this.registrosMostrados + 1;
        }
        return this.modeloTabla;
    }

    public DefaultComboBoxModel seleccionar() throws ClassNotFoundException {
        DefaultComboBoxModel items = new DefaultComboBoxModel();
        List<Rol> lista = new ArrayList();
        lista = DATOSROL.seleccionar();

        for (Rol item : lista) {
            items.addElement(new Rol(item.getId(), item.getNombre()));
        }
        return items;
    }

    private static String encriptar(String valor) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

        byte[] hash = md.digest(valor.getBytes());
        StringBuilder sb = new StringBuilder();

        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();

    }

    public String insertar(int RolId, String nombre, String tipoDocumento, String numDocumento, String direccion, String telefono, String email, String clave) throws ClassNotFoundException {
        if (DATOS.existe(numDocumento)) {
            return "Ya existe un usuario con el mismo número de documento";
        } else {
            obj.setRolId(RolId);
            obj.setNombre(nombre);
            obj.setTipoDocumento(tipoDocumento);
            obj.setNumDocumento(numDocumento);
            obj.setDireccion(direccion);
            obj.setTelefono(telefono);
            obj.setEmail(email);
            obj.setClave(this.encriptar(clave));

            if (DATOS.insertar(obj)) {
                return "OK";
            } else {
                return "Error al insertar";
            }
        }
    }

    public String actualizar(int id, int RolId, String nombre, String tipoDocumento, String numDocumento, String numDocumentoAnt, String direccion, String telefono, String email, String clave) throws ClassNotFoundException {
        if (numDocumento.equals(numDocumentoAnt)) {
            obj.setId(id);
            obj.setRolId(RolId);
            obj.setNombre(nombre);
            obj.setTipoDocumento(tipoDocumento);
            obj.setNumDocumento(numDocumento);
            obj.setDireccion(direccion);
            obj.setTelefono(telefono);
            obj.setEmail(email);

            String encriptado;
            if (clave.length() == 64) {
                encriptado = clave;
            } else {
                encriptado = this.encriptar(clave);
            }
            obj.setClave(encriptado);
            if (DATOS.actualizar(obj)) {
                return "OK";
            } else {
                return "Error al actualizar";
            }
        } else {
            if (DATOS.existe(numDocumento)) {
                return "Ya existe un usario con el mismo número de documento";
            } else {
                obj.setRolId(RolId);
                obj.setNombre(nombre);
                obj.setTipoDocumento(tipoDocumento);
                obj.setNumDocumento(numDocumento);
                obj.setDireccion(direccion);
                obj.setTelefono(telefono);
                obj.setEmail(email);

                String encriptado;
                if (clave.length() == 64) {
                    encriptado = clave;
                } else {
                    encriptado = this.encriptar(clave);
                }
                obj.setClave(encriptado);
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
