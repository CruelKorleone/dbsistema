/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.TableRowSorter;
import negocio.PersonaControl;

/**
 *
 * @author betto
 */
public class frmSeleccionarProveedorCompra extends javax.swing.JDialog {

    private frmIngreso vista;
    private final PersonaControl CONTROL;
    private String accion;
    private String DocAnt;
    private int totalPorPagina = 20;
    private int numPagina = 1;
    private boolean primeraCarga = true;
    private int totalRegistros;

    /**
     * Creates new form frmSeleccionarProveedorCompra
     */
    public frmSeleccionarProveedorCompra(java.awt.Frame parent, frmIngreso frm, boolean modal) throws ClassNotFoundException {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.vista = frm;
        this.setTitle("Seleccionar proveedor");
        this.CONTROL = new PersonaControl();
        this.paginar();
        this.listar("", false);
        this.primeraCarga = false;
        tabGeneral.setEnabledAt(1, false);
        this.accion = "guardar";
        txtId.setVisible(false);
        this.setVisible(true);
    }
    
    private void paginar() throws ClassNotFoundException {
        int totalPaginas;
        
        this.totalRegistros = this.CONTROL.total();
        this.totalPorPagina = Integer.parseInt((String) cboTotalPorPagina.getSelectedItem());

        //Ceil redondea los decimales a un entero superior 3.25 -> 4
        totalPaginas = (int) (Math.ceil((double) this.totalRegistros / this.totalPorPagina));

        //Valido que muestre aunque sea una página en caso de que no haya nada.
        if (totalPaginas == 0) {
            totalPaginas = 1;
        }
        cboNumPagina.removeAllItems();
        
        for (int i = 1; i <= totalPaginas; i++) {
            cboNumPagina.addItem(Integer.toString(i));
        }
        
        cboNumPagina.setSelectedIndex(0);
    }
    
    private void listar(String texto, boolean paginar) throws ClassNotFoundException {
        //Se actualiza la variable total por página, lo que el usuario seleccione en el combo box sea el total de registros que se va a mostrar
        this.totalPorPagina = Integer.parseInt((String) cboTotalPorPagina.getSelectedItem());
        if ((String) cboNumPagina.getSelectedItem() != null) {
            this.numPagina = Integer.parseInt((String) cboNumPagina.getSelectedItem());
        }
        
        if (paginar == true) {
            tablaListado.setModel(this.CONTROL.listarTipo(texto, this.totalPorPagina, this.numPagina, "Proveedor"));
        } else {
            tablaListado.setModel(this.CONTROL.listarTipo(texto, this.totalPorPagina, 1, "Proveedor"));
        }
        
        TableRowSorter orden = new TableRowSorter(tablaListado.getModel());
        tablaListado.setRowSorter(orden);
        //this.formatoCol();
        lblTotalRegistros.setText("Mostrando " + this.CONTROL.totalMostrados() + " de " + this.CONTROL.total() + " registros");
    }

//    private void cargarRoles() throws ClassNotFoundException {
//        DefaultComboBoxModel items = this.CONTROL.seleccionar();
//        cboRol.setModel(items);
//    }
//    private void subirImagenes() {
//        File origen = new File(this.rutaOrigen);
//        File destino = new File(this.rutaDestino);
//        try {
//            InputStream in = new FileInputStream(origen);
//            OutputStream out = new FileOutputStream(destino);
//            byte[] buf = new byte[1024];
//            int len;
//            while ((len = in.read(buf)) > 0) {
//                out.write(buf, 0, len);
//            }
//            in.close();
//            out.close();
//        } catch (IOException e) {
//            JOptionPane.showMessageDialog(this, e.getMessage());
//        }
//    }
    private void limpiar() {
        txtNombre.setText("");
        txtId.setText("");
        txtNumDocumento.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtEmail.setText("");
//        this.imagen = "";
//        this.imagenAnt = "";
//        lblImagen.setIcon(null);
//        this.rutaDestino = "";
//        this.rutaOrigen = "";
        this.accion = "guardar";
    }
    
    private void mensajeError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Sistema", JOptionPane.ERROR_MESSAGE);
    }
    
    private void mensajeOk(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Sistema", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabGeneral = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaListado = new javax.swing.JTable();
        lblTotalRegistros = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnDesactivar = new javax.swing.JButton();
        btnActivar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        cboNumPagina = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cboTotalPorPagina = new javax.swing.JComboBox<>();
        btnSelccionarProveedor = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cboTipoDocumento = new javax.swing.JComboBox<>();
        txtDireccion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtNumDocumento = new javax.swing.JTextField();
        txtTelefono = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Nombre");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tablaListado = new javax.swing.JTable() {

            public boolean isCellEditable(int row, int column) {
                for(int i = 0; i < tablaListado.getRowCount(); i++) {
                    if(row == i) {
                        return false;
                    }
                }
                return true;
            }
        };
        tablaListado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablaListado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaListadoMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tablaListadoMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tablaListado);

        lblTotalRegistros.setText("Registros");

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnDesactivar.setText("Desactivar");
        btnDesactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarActionPerformed(evt);
            }
        });

        btnActivar.setText("Activar");
        btnActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivarActionPerformed(evt);
            }
        });

        jLabel11.setText("# de página");

        cboNumPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNumPaginaActionPerformed(evt);
            }
        });

        jLabel12.setText("Total de registros por página");

        cboTotalPorPagina.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "20", "40", "60", "80", "100" }));
        cboTotalPorPagina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTotalPorPaginaActionPerformed(evt);
            }
        });

        btnSelccionarProveedor.setText("Seleccionar");
        btnSelccionarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelccionarProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(38, 38, 38)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNuevo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditar)
                        .addGap(118, 118, 118)
                        .addComponent(btnSelccionarProveedor)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnDesactivar)
                        .addGap(18, 18, 18)
                        .addComponent(btnActivar)
                        .addGap(39, 39, 39)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboNumPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboTotalPorPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTotalRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnNuevo)
                    .addComponent(btnEditar)
                    .addComponent(btnSelccionarProveedor))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalRegistros)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDesactivar)
                        .addComponent(btnActivar)
                        .addComponent(jLabel11)
                        .addComponent(cboNumPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(cboTotalPorPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(72, Short.MAX_VALUE))
        );

        tabGeneral.addTab("Listado", jPanel1);

        jLabel2.setText("Nombre");

        jLabel3.setText("Teléfono");

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel6.setText("Num. Doc.");

        jLabel7.setText("Dirección");

        jLabel9.setText("Tipo doc.");

        cboTipoDocumento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CI", "RUC", "Pasaporte" }));

        jLabel5.setText("Email");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNumDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(btnGuardar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnCancelar))
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtDireccion, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(300, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTipoDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtNumDocumento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnCancelar))
                .addGap(78, 78, 78))
        );

        tabGeneral.addTab("Mantenimiento", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabGeneral)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabGeneral)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            this.listar(txtBuscar.getText(), false);
        } catch (ClassNotFoundException ex) {
            //Logger.get(frmProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tablaListadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaListadoMouseClicked
        if (evt.getClickCount() == 2) {
            if (tablaListado.getSelectedRowCount() == 1) {
                String id = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 0));
                //            String rolPersona = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 1));
                String nombre = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 2));
                String tipoDocumento = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 3));
                String numDocumento = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 4));
                this.DocAnt = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 4));
                String direccion = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 5));
                String telefono = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 6));
                String email = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 7));
                
                txtId.setText(id);
                txtNombre.setText(nombre);
                cboTipoDocumento.setSelectedItem(tipoDocumento);
                txtNumDocumento.setText(numDocumento);
                txtDireccion.setText(direccion);
                txtTelefono.setText(telefono);
                txtEmail.setText(email);

                //            ImageIcon im = new ImageIcon(this.DIR + this.imagenAnt);
                //            Icon icono = new ImageIcon(im.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_DEFAULT));
                //            lblImagen.setIcon(icono);
                //            lblImagen.repaint();
                tabGeneral.setEnabledAt(1, true);
                tabGeneral.setEnabledAt(0, false);
                tabGeneral.setSelectedIndex(1);
                
                this.accion = "editar";
                btnGuardar.setText("Editar");
            }
        }
    }//GEN-LAST:event_tablaListadoMouseClicked

    private void tablaListadoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaListadoMousePressed

    }//GEN-LAST:event_tablaListadoMousePressed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        tabGeneral.setEnabledAt(1, true);
        tabGeneral.setEnabledAt(0, false);
        tabGeneral.setSelectedIndex(1);
        this.accion = "guardar";
        btnGuardar.setText("Guardar");
        txtId.setVisible(false);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (tablaListado.getSelectedRowCount() == 1) {
            String id = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 0));
            //            String rolPersona = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 1));
            String nombre = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 2));
            String tipoDocumento = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 3));
            String numDocumento = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 4));
            this.DocAnt = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 4));
            String direccion = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 5));
            String telefono = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 6));
            String email = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 7));
            
            txtId.setText(id);
            txtNombre.setText(nombre);
            cboTipoDocumento.setSelectedItem(tipoDocumento);
            txtNumDocumento.setText(numDocumento);
            txtDireccion.setText(direccion);
            txtTelefono.setText(telefono);
            txtEmail.setText(email);

            //            ImageIcon im = new ImageIcon(this.DIR + this.imagenAnt);
            //            Icon icono = new ImageIcon(im.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_DEFAULT));
            //            lblImagen.setIcon(icono);
            //            lblImagen.repaint();
            tabGeneral.setEnabledAt(1, true);
            tabGeneral.setEnabledAt(0, false);
            tabGeneral.setSelectedIndex(1);
            
            this.accion = "editar";
            btnGuardar.setText("Editar");
        } else {
            this.mensajeError("Debe seleccionar un registro para poder editar");
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarActionPerformed
        if (tablaListado.getSelectedRowCount() == 1) {
            String id = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 0));
            String nombre = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 2));
            if (JOptionPane.showConfirmDialog(this, "Desactivar registro seleccionado?", "Sistema", JOptionPane.YES_NO_OPTION) == 0) {
                try {
                    String resp = this.CONTROL.desactivar(Integer.parseInt(id));
                    if (resp.equals("OK")) {
                        this.mensajeOk("Desactivado");
                        this.listar("", false);
                    } else {
                        this.mensajeError(resp);
                    }
                } catch (ClassNotFoundException ex) {
                    //Logger.getLogger(frmProveedor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            this.mensajeError("Debe seleccionar un registro para poder desactivar");
        }
    }//GEN-LAST:event_btnDesactivarActionPerformed

    private void btnActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarActionPerformed
        if (tablaListado.getSelectedRowCount() == 1) {
            String id = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 0));
            String nombre = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 2));
            if (JOptionPane.showConfirmDialog(this, "Activar registro seleccionado?", "Sistema", JOptionPane.YES_NO_OPTION) == 0) {
                try {
                    String resp = this.CONTROL.activar(Integer.parseInt(id));
                    if (resp.equals("OK")) {
                        this.mensajeOk("Activado");
                        this.listar("", false);
                    } else {
                        this.mensajeError(resp);
                    }
                } catch (ClassNotFoundException ex) {
                    //Logger.getLogger(frmProveedor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            this.mensajeError("Debe seleccionar un registro para poder activar");
        }
    }//GEN-LAST:event_btnActivarActionPerformed

    private void cboNumPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNumPaginaActionPerformed
        
        if (this.primeraCarga == false) {
            try {
                this.listar("", true);
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(frmProveedor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cboNumPaginaActionPerformed

    private void cboTotalPorPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTotalPorPaginaActionPerformed
        try {
            this.paginar();
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(frmProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cboTotalPorPaginaActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (txtNombre.getText().length() == 0 || txtNombre.getText().length() > 70) {
            JOptionPane.showMessageDialog(this, "Ingrese un nombre correctamemente", "Sistema", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return;
        }
        
        if (txtEmail.getText().length() > 50) {
            JOptionPane.showMessageDialog(this, "Ingrese un email correctamente", "Sistema", JOptionPane.WARNING_MESSAGE);
            txtEmail.requestFocus();
            return;
        }
        
        if (txtNumDocumento.getText().length() == 0) {
            JOptionPane.showMessageDialog(this, "Ingrese el número de documento", "Sistema", JOptionPane.WARNING_MESSAGE);
            txtNumDocumento.requestFocus();
            return;
        }
        
        String resp = null;
        if (accion.equals("editar")) {
            try {
                //                String imagenActual = "";
                //                if (this.imagen.equals("")) {
                //                    imagenActual = this.imagenAnt;
                //                } else {
                //                    imagenActual = this.imagen;
                //                }
                //                Rol seleccionado = (Rol) cboRol.getSelectedItem();
                resp = this.CONTROL.actualizar(Integer.parseInt(txtId.getText()), "Proveedor", txtNombre.getText(), (String) cboTipoDocumento.getSelectedItem(), txtNumDocumento.getText(), this.DocAnt, txtDireccion.getText(), txtTelefono.getText(), txtEmail.getText());
                
                if (resp.equals("OK")) {
                    //                    if (this.imagen.equals("")) {
                    //                        this.subirImagenes();
                    //                    }
                    this.mensajeOk("Registro actualizado");
                    this.limpiar();
                    this.listar("", false);
                    tabGeneral.setSelectedIndex(0);
                    tabGeneral.setEnabledAt(1, false);
                    tabGeneral.setEnabledAt(0, true);
                } else {
                    this.mensajeError(resp);
                }
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(frmProveedor.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                //Guardar
                //                Rol seleccionado = (Rol) cboRol.getSelectedItem();
                resp = this.CONTROL.insertar("Proveedor", txtNombre.getText(), (String) cboTipoDocumento.getSelectedItem(), txtNumDocumento.getText(), txtDireccion.getText(), txtTelefono.getText(), txtEmail.getText());
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(frmProveedor.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if (resp.equals("OK")) {
                try {
                    //                    if (!this.imagen.equals("")) {
                    //                        this.subirImagenes();
                    //                    }
                    this.mensajeOk("Registro guardado");
                    this.limpiar();
                    this.listar("", false);
                } catch (ClassNotFoundException ex) {
                    //Logger.getLogger(frmProveedor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                this.mensajeError(resp);
            }
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        tabGeneral.setEnabledAt(1, false);
        tabGeneral.setEnabledAt(0, true);
        tabGeneral.setSelectedIndex(0);
        this.limpiar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSelccionarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelccionarProveedorActionPerformed
        if (tablaListado.getSelectedRowCount() == 1) {
            String id = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 0));
            String nombre = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 2));
            
            this.vista.txtIdProveedor.setText(id);
            this.vista.txtNombreProveedor.setText(nombre);
            this.setVisible(false);
        } else {
            this.mensajeError("Seleccione un registro");
        }
    }//GEN-LAST:event_btnSelccionarProveedorActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmSeleccionarProveedorCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmSeleccionarProveedorCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmSeleccionarProveedorCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmSeleccionarProveedorCompra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActivar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDesactivar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnSelccionarProveedor;
    private javax.swing.JComboBox<String> cboNumPagina;
    private javax.swing.JComboBox<String> cboTipoDocumento;
    private javax.swing.JComboBox<String> cboTotalPorPagina;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTotalRegistros;
    private javax.swing.JTabbedPane tabGeneral;
    private javax.swing.JTable tablaListado;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumDocumento;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
