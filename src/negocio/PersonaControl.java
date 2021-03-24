/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import datos.PersonaDAO;
import entidades.Persona;
import java.util.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author betto
 */
public class PersonaControl {

    private final PersonaDAO DATOS;
    private Persona obj;
    private DefaultTableModel modeloTabla;
    public int registrosMostrados;

    public PersonaControl() {
        this.DATOS = new PersonaDAO();
        this.obj = new Persona();
        this.registrosMostrados = 0;
    }

    public DefaultTableModel listar(String texto, int totalPorPagina, int numPagina) throws ClassNotFoundException {
        List<Persona> lista = new ArrayList();
        lista.addAll(DATOS.listar(texto, totalPorPagina, numPagina));

        String[] titulos = {"ID", "Tipo", "Razón Social", "Documento", "Num. Doc.", "Dirección", "Teléfono", "Email", "Estado"};

        this.modeloTabla = new DefaultTableModel(null, titulos);

        String estado;
        String[] registro = new String[9];

        this.registrosMostrados = 0;
        for (Persona item : lista) {
            if (item.isActivo()) {
                estado = "Activo";
            } else {
                estado = "Inactivo";
            }
            registro[0] = Integer.toString(item.getId());
            registro[1] = item.getTipoPersona().toUpperCase();
            registro[2] = item.getNombre().toUpperCase();
            registro[3] = item.getTipoDocumento().toUpperCase();
            registro[4] = item.getNumDocumento();
            registro[5] = item.getDireccion().toUpperCase();
            registro[6] = item.getTelefono();
            registro[7] = item.getEmail().toLowerCase();
            registro[8] = estado.toUpperCase();
            this.modeloTabla.addRow(registro);
            this.registrosMostrados = this.registrosMostrados + 1;
        }
        return this.modeloTabla;
    }

//    public DefaultComboBoxModel seleccionar() throws ClassNotFoundException {
//        DefaultComboBoxModel items = new DefaultComboBoxModel();
//        List<Rol> lista = new ArrayList();
//        lista = DATOSROL.seleccionar();
//
//        for (Rol item : lista) {
//            items.addElement(new Rol(item.getId(), item.getNombre()));
//        }
//        return items;
//    }
//    private static String encriptar(String valor) {
//        MessageDigest md;
//        try {
//            md = MessageDigest.getInstance("SHA-256");
//        } catch (NoSuchAlgorithmException e) {
//            return null;
//        }
//
//        byte[] hash = md.digest(valor.getBytes());
//        StringBuilder sb = new StringBuilder();
//
//        for (byte b : hash) {
//            sb.append(String.format("%02x", b));
//        }
//
//        return sb.toString();
//
//    }
    public String insertar(String tipoPersona, String nombre, String tipoDocumento, String numDocumento, String direccion, String telefono, String email) throws ClassNotFoundException {
        if (DATOS.existe(numDocumento)) {
            return "El número de documento ya se encuentra registrado";
        } else {
            obj.setTipoPersona(tipoPersona);
            obj.setNombre(nombre);
            obj.setTipoDocumento(tipoDocumento);
            obj.setNumDocumento(numDocumento);
            obj.setDireccion(direccion);
            obj.setTelefono(telefono);
            obj.setEmail(email);

            if (DATOS.insertar(obj)) {
                return "OK";
            } else {
                return "Error al insertar";
            }
        }
    }

    public String actualizar(int id, String tipoPersona, String nombre, String tipoDocumento, String numDocumento, String numDocumentoAnt, String direccion, String telefono, String email) throws ClassNotFoundException {
        if (numDocumento.equals(numDocumentoAnt)) {
            obj.setId(id);
            obj.setTipoPersona(tipoPersona);
            obj.setNombre(nombre);
            obj.setTipoDocumento(tipoDocumento);
            obj.setNumDocumento(numDocumento);
            obj.setDireccion(direccion);
            obj.setTelefono(telefono);
            obj.setEmail(email);

//            String encriptado;
//            if (clave.length() == 64) {
//                encriptado = clave;
//            } else {
//                encriptado = this.encriptar(clave);
//            }
//            obj.setClave(encriptado);
            if (DATOS.actualizar(obj)) {
                return "OK";
            } else {
                return "Error al actualizar";
            }
        } else {
            if (DATOS.existe(numDocumento)) {
                return "El número de documento ya se encuentra registrado";
            } else {
                obj.setId(id);
                obj.setTipoPersona(tipoPersona);
                obj.setNombre(nombre);
                obj.setTipoDocumento(tipoDocumento);
                obj.setNumDocumento(numDocumento);
                obj.setDireccion(direccion);
                obj.setTelefono(telefono);
                obj.setEmail(email);

//                String encriptado;
//                if (clave.length() == 64) {
//                    encriptado = clave;
//                } else {
//                    encriptado = this.encriptar(clave);
//                }
//                obj.setClave(encriptado);
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
