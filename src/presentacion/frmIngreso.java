/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import negocio.IngresoControl;

/**
 *
 * @author betto
 */
public class frmIngreso extends javax.swing.JInternalFrame {

    private final IngresoControl CONTROL;
    private String accion;
    private String DocAnt;
//    private String rutaOrigen;
//    private String rutaDestino;
//    private final String DIR = "src/files/articulos/";
//    private String imagen = "";
//    private String imagenAnt;
    private int totalPorPagina = 20;
    private int numPagina = 1;
    private boolean primeraCarga = true;
    private int totalRegistros;

    public DefaultTableModel modeloDetalles;
    public JFrame contenedor;

    /**
     * Creates new form frmCategoria
     */
    public frmIngreso(JFrame frmP) throws ClassNotFoundException, Exception {
        initComponents();
        this.contenedor = frmP;
        this.CONTROL = new IngresoControl();
        this.paginar();
        this.listar("", false);
        this.primeraCarga = false;
        tabGeneral.setEnabledAt(1, false);
        this.accion = "guardar";
        this.crearDetalles();
    }

    private void formatoCol() {
        tablaListado.getColumnModel().getColumn(0).setMaxWidth(0);
        tablaListado.getColumnModel().getColumn(0).setMinWidth(0);
        tablaListado.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        tablaListado.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
//        
        tablaListado.getColumnModel().getColumn(1).setMaxWidth(0);
        tablaListado.getColumnModel().getColumn(1).setMinWidth(0);
        tablaListado.getTableHeader().getColumnModel().getColumn(1).setMaxWidth(0);
        tablaListado.getTableHeader().getColumnModel().getColumn(1).setMinWidth(0);
//        
        tablaListado.getColumnModel().getColumn(3).setMaxWidth(120);
        tablaListado.getColumnModel().getColumn(3).setMinWidth(50);
        tablaListado.getTableHeader().getColumnModel().getColumn(3).setMaxWidth(120);
        tablaListado.getTableHeader().getColumnModel().getColumn(3).setMinWidth(50);
//        
        tablaListado.getColumnModel().getColumn(4).setMaxWidth(600);
        tablaListado.getColumnModel().getColumn(4).setMinWidth(50);
        tablaListado.getTableHeader().getColumnModel().getColumn(4).setMaxWidth(600);
        tablaListado.getTableHeader().getColumnModel().getColumn(4).setMinWidth(50);
//        
//        tablaListado.getColumnModel().getColumn(5).setMaxWidth(0);
//        tablaListado.getColumnModel().getColumn(5).setMinWidth(0);
//        tablaListado.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0);
//        tablaListado.getTableHeader().getColumnModel().getColumn(5).setMinWidth(0);
//        
        tablaListado.getColumnModel().getColumn(6).setMaxWidth(100);
        tablaListado.getColumnModel().getColumn(6).setMinWidth(50);
        tablaListado.getTableHeader().getColumnModel().getColumn(6).setMaxWidth(100);
        tablaListado.getTableHeader().getColumnModel().getColumn(6).setMinWidth(50);
//        
//        tablaListado.getColumnModel().getColumn(7).setMinWidth(0);
//        tablaListado.getColumnModel().getColumn(7).setMaxWidth(0);

        tablaListado.getColumnModel().getColumn(8).setMaxWidth(50);
        tablaListado.getColumnModel().getColumn(8).setMinWidth(0);
    }
//    

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

    private void listar(String texto, boolean paginar) throws ClassNotFoundException, Exception {
        //Se actualiza la variable total por página, lo que el usuario seleccione en el combo box sea el total de registros que se va a mostrar
        this.totalPorPagina = Integer.parseInt((String) cboTotalPorPagina.getSelectedItem());
        if ((String) cboNumPagina.getSelectedItem() != null) {
            this.numPagina = Integer.parseInt((String) cboNumPagina.getSelectedItem());
        }

        if (paginar == true) {
            tablaListado.setModel(this.CONTROL.listar(texto, this.totalPorPagina, this.numPagina));
        } else {
            tablaListado.setModel(this.CONTROL.listar(texto, this.totalPorPagina, 1));
        }

        TableRowSorter orden = new TableRowSorter(tablaListado.getModel());
        tablaListado.setRowSorter(orden);
        this.formatoCol();
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
    private void crearDetalles() {
        modeloDetalles = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                if (columna == 3) {
                    return columna == 3;
                }
                if (columna == 4) {
                    return columna == 4;
                }
                return columna == 3;
            }

            @Override
            public Object getValueAt(int row, int col) {
                if (col == 5) {
                    Double cantD;
                    try {
                        cantD = Double.parseDouble((String) getValueAt(row, 3));
                    } catch (Exception e) {
                        cantD = 1.0;
                    }
                    Double precioD = Double.parseDouble((String) getValueAt(row, 4));
                    if (cantD != null && precioD != null) {
                        return String.format("%.2f", cantD * precioD);
                    } else {
                        return 0;
                    }
                }
                return super.getValueAt(row, col);
            }

            @Override
            public void setValueAt(Object aValue, int row, int col) {
                super.setValueAt(aValue, row, col);
                calcularTotales();
                fireTableDataChanged();
            }

        };

//        modeloDetalles = new DefaultTableModel() {
//        };
        modeloDetalles.setColumnIdentifiers(new Object[]{"Id", "Código", "Articulo", "Cantidad", "Precio", "Subtotal"});
        tablaDetalles.setModel(modeloDetalles);
    }

    public void agregarDetalles(String id, String codigo, String nombre, String precio) {
        String idT;
        boolean existe = false;

        for (int i = 0; i < this.modeloDetalles.getRowCount(); i++) {
            idT = String.valueOf(this.modeloDetalles.getValueAt(i, 0));
            if (idT.equals(id)) {
                existe = true;
            }
        }
        if (existe) {
            this.mensajeError("El articulo ya ha sido agregado al detalle");
        } else {
            this.modeloDetalles.addRow(new Object[]{id, codigo, nombre, "1", precio, precio});
            this.calcularTotales();
        }
    }

    private void calcularTotales() {
        double total = 0;
        double subTotal;

        int items = modeloDetalles.getRowCount();
        if (items == 0) {
            total = 0;
        } else {
            for (int i = 0; i < items; i++) {
                total = total + Double.parseDouble(String.valueOf(modeloDetalles.getValueAt(i, 5)));
            }
        }
        subTotal = total / (1 + Double.parseDouble(txtImpuesto.getText()));

        txtTotal.setText(String.format("%.0f", total));
        txtSubTotal.setText(String.format("%.0f", subTotal));
        txtTotalImpuesto.setText(String.format("%.0f", total - subTotal));
    }

    private void limpiar() {

        txtNombreProveedor.setText("");
        txtIdProveedor.setText("");
        txtNumTimbrado.setText("");
        txtNumComprobante.setText("");
        txtImpuesto.setText("0.10");
        this.accion = "guardar";

        txtTotal.setText("0");
        txtSubTotal.setText("0");
        txtTotalImpuesto.setText("0");

        this.crearDetalles();
        btnGuardar.setEnabled(true);
        btnQuitar.setEnabled(true);
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
        btnDesactivar = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        cboNumPagina = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cboTotalPorPagina = new javax.swing.JComboBox<>();
        btnVerIngreso = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtNombreProveedor = new javax.swing.JTextField();
        btnSeleccionarProveedor = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboTipoComprobante = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtNumTimbrado = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNumComprobante = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        btnVerArticulos = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDetalles = new javax.swing.JTable();
        txtSubTotal = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        txtTotalImpuesto = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtImpuesto = new javax.swing.JTextField();
        txtIdProveedor = new javax.swing.JTextField();
        btnQuitar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setTitle("Compras");

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

        btnDesactivar.setText("Anular");
        btnDesactivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesactivarActionPerformed(evt);
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

        btnVerIngreso.setText("Ver detalle");
        btnVerIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerIngresoActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVerIngreso, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(btnDesactivar)
                        .addGap(111, 111, 111)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboNumPagina, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cboTotalPorPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
                        .addComponent(lblTotalRegistros, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar)
                            .addComponent(btnNuevo)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnVerIngreso)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalRegistros)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDesactivar)
                        .addComponent(jLabel11)
                        .addComponent(cboNumPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)
                        .addComponent(cboTotalPorPagina, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(159, Short.MAX_VALUE))
        );

        tabGeneral.addTab("Listado", jPanel1);

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

        jLabel4.setText("Proveedor");

        txtNombreProveedor.setEditable(false);
        txtNombreProveedor.setBackground(java.awt.SystemColor.controlLtHighlight);

        btnSeleccionarProveedor.setText("...");
        btnSeleccionarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarProveedorActionPerformed(evt);
            }
        });

        jLabel2.setText("Impuesto");

        jLabel3.setText("Tipo Comp.");

        cboTipoComprobante.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Factura", "Boleta", "Ticket", "Guia" }));

        jLabel5.setText("Timbrado");

        jLabel6.setText("Num Comp.");

        jLabel7.setText("Articulo");

        txtCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCodigoKeyReleased(evt);
            }
        });

        btnVerArticulos.setText("Ver");
        btnVerArticulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerArticulosActionPerformed(evt);
            }
        });

        tablaDetalles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tablaDetalles);

        txtSubTotal.setEditable(false);
        txtSubTotal.setBackground(java.awt.SystemColor.controlLtHighlight);

        txtTotal.setEditable(false);
        txtTotal.setBackground(java.awt.SystemColor.controlLtHighlight);

        txtTotalImpuesto.setEditable(false);
        txtTotalImpuesto.setBackground(java.awt.SystemColor.controlLtHighlight);

        jLabel8.setText("Sub Total");

        jLabel9.setText("Impuesto");

        jLabel10.setText("Total");

        txtImpuesto.setText("0.10");

        txtIdProveedor.setEditable(false);
        txtIdProveedor.setBackground(java.awt.SystemColor.controlLtHighlight);

        btnQuitar.setText("Quitar");
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel10)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(btnGuardar)
                                .addGap(18, 18, 18)
                                .addComponent(btnCancelar)
                                .addGap(530, 530, 530)
                                .addComponent(jLabel9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtSubTotal)
                            .addComponent(txtTotalImpuesto)
                            .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtCodigo))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(cboTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtNumTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel6))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(txtIdProveedor)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, 455, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnVerArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(btnSeleccionarProveedor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2)))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(btnQuitar))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(txtNumComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(102, 102, 102)))
                .addGap(0, 25, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txtNombreProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtIdProveedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSeleccionarProveedor)
                            .addComponent(jLabel2)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cboTipoComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtNumTimbrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtNumComprobante, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVerArticulos)
                    .addComponent(btnQuitar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnGuardar)
                            .addComponent(btnCancelar)
                            .addComponent(txtTotalImpuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(117, 117, 117))
        );

        tabGeneral.addTab("Mantenimiento", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabGeneral))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(tabGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {
            this.listar(txtBuscar.getText(), false);
        } catch (Exception ex) {
            Logger.getLogger(frmIngreso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        tabGeneral.setEnabledAt(1, true);
        tabGeneral.setEnabledAt(0, false);
        tabGeneral.setSelectedIndex(1);
        this.accion = "guardar";
        btnGuardar.setText("Guardar");
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void tablaListadoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaListadoMousePressed

    }//GEN-LAST:event_tablaListadoMousePressed


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

//                txtId.setText(id);
//                txtNombre.setText(nombre);
//                cboTipoDocumento.setSelectedItem(tipoDocumento);
//                txtNumDocumento.setText(numDocumento);
//                txtDireccion.setText(direccion);
//                txtTelefono.setText(telefono);
//                txtEmail.setText(email);
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

    private void btnDesactivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesactivarActionPerformed
        if (tablaListado.getSelectedRowCount() == 1) {
            String id = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 0));
            String comprobante = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 5));
            String timbrado = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 6));
            String numero = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 7));

            if (JOptionPane.showConfirmDialog(this, "Desactivar registro seleccionado? Número: " + numero, "Anular", JOptionPane.YES_NO_OPTION) == 0) {
                try {
                    String resp = this.CONTROL.anular(Integer.parseInt(id));
                    if (resp.equals("OK")) {
                        this.mensajeOk("Anulado");
                        this.listar("", false);
                    } else {
                        this.mensajeError(resp);
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(frmIngreso.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(frmIngreso.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            this.mensajeError("Debe seleccionar un registro para poder desactivar");
        }
    }//GEN-LAST:event_btnDesactivarActionPerformed

    private void cboNumPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNumPaginaActionPerformed

        if (this.primeraCarga == false) {
            try {
                this.listar("", true);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(frmIngreso.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(frmIngreso.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_cboNumPaginaActionPerformed

    private void cboTotalPorPaginaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTotalPorPaginaActionPerformed
        try {
            this.paginar();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmIngreso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_cboTotalPorPaginaActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        tabGeneral.setEnabledAt(1, false);
        tabGeneral.setEnabledAt(0, true);
        tabGeneral.setSelectedIndex(0);
        this.limpiar();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            if (txtIdProveedor.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "Seleccione un proveedor", "Sistema", JOptionPane.WARNING_MESSAGE);
                btnSeleccionarProveedor.requestFocus();
                return;
            }

            if (txtNumTimbrado.getText().length() > 13 || txtNumTimbrado.getText().length() == 0) {
                JOptionPane.showMessageDialog(this, "Formato de timbrado no válido", "Sistema", JOptionPane.WARNING_MESSAGE);
                txtNumTimbrado.requestFocus();
                return;
            }

            if (txtNumComprobante.getText().length() == 0 || txtNumComprobante.getText().length() > 20) {
                JOptionPane.showMessageDialog(this, "Ingrese un número de comprobante correctamente", "Sistema", JOptionPane.WARNING_MESSAGE);
                txtNumComprobante.requestFocus();
                return;
            }

            if (modeloDetalles.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "No ha ingresado ningún articulo a detalle", "Sistema", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String resp = "";
            resp = this.CONTROL.insertar(Integer.parseInt(txtIdProveedor.getText()), (String) cboTipoComprobante.getSelectedItem(), txtNumTimbrado.getText(), txtNumComprobante.getText(), Double.parseDouble(txtImpuesto.getText()), Double.parseDouble(txtTotal.getText()), modeloDetalles);
            if (resp.equals("OK")) {
                try {
                    this.mensajeOk("Registro guardado");
                    this.limpiar();
                    this.listar("", false);
                } catch (Exception ex) {
                    Logger.getLogger(frmIngreso.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                this.mensajeError(resp);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmIngreso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnSeleccionarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarProveedorActionPerformed
        try {
            frmSeleccionarProveedorCompra frm = new frmSeleccionarProveedorCompra(contenedor, this, true);
            frm.toFront();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmIngreso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSeleccionarProveedorActionPerformed

    private void btnVerArticulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerArticulosActionPerformed
        try {
            frmSeleccionarArticuloCompra frm = new frmSeleccionarArticuloCompra(contenedor, this, true);
            frm.toFront();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(frmIngreso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnVerArticulosActionPerformed

    private void txtCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCodigoKeyReleased
        if (txtCodigo.getText().length() > 0) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                try {
                    entidades.Articulo art;
                    art = this.CONTROL.obtenerArticuloCodigoCompras(txtCodigo.getText());
                    if (art == null) {
                        this.mensajeError("No existe un articulo con ese código");
                    } else {
                        this.agregarDetalles(Integer.toString(art.getId()), art.getCodigo(), art.getNombre(), Double.toString(art.getPrecioVenta()));
                        txtCodigo.setText("");
                    }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(frmIngreso.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            this.mensajeError("Ingrese el código del articulo");
        }
    }//GEN-LAST:event_txtCodigoKeyReleased

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        if (tablaDetalles.getSelectedRowCount() == 1) {
            this.modeloDetalles.removeRow(tablaDetalles.getSelectedRow());
            this.calcularTotales();
        } else {
            this.mensajeError("No ha seleccionado nada");
        }
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnVerIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerIngresoActionPerformed

        try {
            if (tablaListado.getSelectedRowCount() == 1) {
                String id = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 0));
                String idProveedor = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 3));
                String nombreProveedor = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 4));
                String tipoComprobante = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 5));
                String numTimbrado = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 6));
                String numComprobante = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 7));
                String impuesto = String.valueOf(tablaListado.getValueAt(tablaListado.getSelectedRow(), 9));

                txtIdProveedor.setText(idProveedor);
                txtNombreProveedor.setText(nombreProveedor);
                cboTipoComprobante.setSelectedItem(tipoComprobante);
                txtNumTimbrado.setText(numTimbrado);
                txtNumComprobante.setText(numComprobante);
                txtImpuesto.setText(impuesto);

                this.modeloDetalles = CONTROL.listarDetalle(Integer.parseInt(id));
                tablaDetalles.setModel(modeloDetalles);
                this.calcularTotales();

                tabGeneral.setEnabledAt(1, true);
                tabGeneral.setEnabledAt(0, false);
                tabGeneral.setSelectedIndex(1);
                btnGuardar.setEnabled(false);
                btnQuitar.setEnabled(false);
            } else {
                this.mensajeError("Debe seleccionar un registro");
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnVerIngresoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDesactivar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnQuitar;
    private javax.swing.JButton btnSeleccionarProveedor;
    private javax.swing.JButton btnVerArticulos;
    private javax.swing.JButton btnVerIngreso;
    private javax.swing.JComboBox<String> cboNumPagina;
    private javax.swing.JComboBox<String> cboTipoComprobante;
    private javax.swing.JComboBox<String> cboTotalPorPagina;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTotalRegistros;
    private javax.swing.JTabbedPane tabGeneral;
    private javax.swing.JTable tablaDetalles;
    private javax.swing.JTable tablaListado;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    public javax.swing.JTextField txtIdProveedor;
    private javax.swing.JTextField txtImpuesto;
    public javax.swing.JTextField txtNombreProveedor;
    private javax.swing.JTextField txtNumComprobante;
    private javax.swing.JTextField txtNumTimbrado;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txtTotalImpuesto;
    // End of variables declaration//GEN-END:variables
}
