/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author betto
 */
public class Conexion {

    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/";
    private final String DB = "dbsistema";
    private final String USER = "root";
    private final String PASSWORD = "Santino2013";

    public Connection cadena;
    public static Conexion instancia; //Patrón singleton

    private Conexion() {
        this.cadena = null;
    }

    public Connection conectar() throws ClassNotFoundException {
        try {
            Class.forName(DRIVER);
            this.cadena = DriverManager.getConnection(URL + DB, USER, PASSWORD);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            //Después del error cierro las conexiones
            System.exit(0);
        }
        return this.cadena;
    }

    public void desconectar() {
        try {
            this.cadena.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    //Se utiliza el patrón singleton
    public synchronized static Conexion getInstancia() {
        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }
}
