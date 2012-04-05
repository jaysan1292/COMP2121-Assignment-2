/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Application.java
 *
 * Created on Mar 30, 2012, 12:14:46 PM
 */
package com.assign2.view;

import com.assign2.Utils;
import com.assign2.data.CustomerAccess;
import com.assign2.business.Customer;
import com.assign2.business.Item;
import com.assign2.business.Order;
import com.assign2.data.ItemAccess;
import com.assign2.data.OrderAccess;
import com.assign2.data.OrderLineAccess;
import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.PatternSyntaxException;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Jason Recillo
 */
public class Application extends JApplet {
    private TableRowSorter<DefaultTableModel> customerSorter;
    private TableRowSorter<DefaultTableModel> itemSorter;

    private enum FormType {
        Customer, Order, Product
    }

    class PopupListener extends MouseAdapter {
        FormType type;

        public PopupListener(FormType type) {
            this.type = type;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            showContextMenu(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            showContextMenu(e);
        }

        private void showContextMenu(MouseEvent e) {
            if (e.isPopupTrigger()) {
                switch (type) {
                    case Customer:
                        mnuCustomerContextMenu.show(tblCustomerList, e.getX(), e.getY());
                        break;
                    case Order:
                        mnuOrderContextMenu.show(tblOrderList, e.getX(), e.getY());
                        break;
                    case Product:
                        mnuProductContextMenu.show(tblItemList, e.getX(), e.getY());
                        break;
                }
            }
        }
    }

    /**
     * This applet can be run as an application!
     * If run in a browser, the main method will be ignored,
     * whereas if run as an executable, the main method will execute.
     * @param args 
     */
    public static void main(String[] args) {
        JApplet applet = new Application();
        applet.init();
        applet.start();

        JFrame window = new JFrame("BTB Computer Hardware");
        window.setContentPane(applet);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    //<editor-fold defaultstate="collapsed" desc="Applet Methods">
    /** Initializes the applet Application */
    @Override
    public void init() {
        //<editor-fold defaultstate="collapsed" desc="Set Windows look and feel">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Application.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Create and display the applet">
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    initComponents();
                    clearStatusBarText();
                    MouseListener customerPopupListener = new PopupListener(FormType.Customer);
                    tblCustomerList.addMouseListener(customerPopupListener);

                    MouseListener orderPopupListener = new PopupListener(FormType.Order);
                    tblOrderList.addMouseListener(orderPopupListener);

                    MouseListener itemPopupListener = new PopupListener(FormType.Product);
                    tblItemList.addMouseListener(itemPopupListener);

                    txtCustomerSearch.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            doCustomerSearch();
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            doCustomerSearch();
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {
                            doCustomerSearch();
                        }
                    });

                    txtItemSearch.getDocument().addDocumentListener(new DocumentListener() {
                        @Override
                        public void insertUpdate(DocumentEvent e) {
                            doItemSearch();
                        }

                        @Override
                        public void removeUpdate(DocumentEvent e) {
                            doItemSearch();
                        }

                        @Override
                        public void changedUpdate(DocumentEvent e) {
                            doItemSearch();
                        }
                    });
                }
            });

            DefaultTableModel customerTableModel = (DefaultTableModel) tblCustomerList.getModel();
            customerSorter = new TableRowSorter<DefaultTableModel>(customerTableModel);
            tblCustomerList.setRowSorter(customerSorter);

            DefaultTableModel itemTableModel = (DefaultTableModel) tblItemList.getModel();
            itemSorter = new TableRowSorter<DefaultTableModel>(itemTableModel);
            tblItemList.setRowSorter(itemSorter);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //</editor-fold>
    }
    //</editor-fold>

    public void setStatusBarText(String text) {
        lblStatusBarText.setText(String.format("  %s", text));
    }

    public void clearStatusBarText() {
        setStatusBarText("");
    }

    public void setProgressBarEnabled(boolean enabled) {
        if (enabled) {
            prgProgressBar.setEnabled(true);
            prgProgressBar.setIndeterminate(true);
        } else {
            prgProgressBar.setEnabled(false);
            prgProgressBar.setIndeterminate(false);
        }
    }

    private void openChildForm(JFrame frame, JPanel form) throws HeadlessException {
        frame.setContentPane(form);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(this);
        frame.setAlwaysOnTop(true);
        frame.setVisible(true);
    }

    private void doCustomerSearch() {
        if (!("Search...".equals(txtCustomerSearch.getText()))) {
            RowFilter<DefaultTableModel, Object> filter = null;
            try {
                filter = RowFilter.regexFilter("(?i)" + txtCustomerSearch.getText(), 0, 1, 2, 3, 4);
            } catch (PatternSyntaxException e) {
                return;
            }
            customerSorter.setRowFilter(filter);
        }
    }

    private void doItemSearch() {
        if (!("Search...".equals(txtItemSearch.getText()))) {
            RowFilter<DefaultTableModel, Object> filter = null;
            try {
                filter = RowFilter.regexFilter("(?i)" + txtItemSearch.getText(), 0, 1, 2, 3, 4);
            } catch (PatternSyntaxException e) {
                return;
            }
            itemSorter.setRowFilter(filter);
        }
    }

    public void refreshCustomerTable() {
        btnLoadCustomers.doClick();
    }

    public void refreshItemTable() {
        btnLoadItems.doClick();
    }

    public void refreshOrderTable() {
        btnLoadOrders.doClick();
    }

    private void setFonts(Font font) {
        lblJasonLabel.setFont(font);
        lblJohnLabel.setFont(font);
        lblPeterLabel.setFont(font);
        lblCourseLabel.setFont(font);
        lblProfessorLabel.setFont(font);
    }

    //<editor-fold defaultstate="collapsed" desc="Generated Code">
    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings ("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mnuCustomerContextMenu = new javax.swing.JPopupMenu();
        itmEditCustomer = new javax.swing.JMenuItem();
        mnuProductContextMenu = new javax.swing.JPopupMenu();
        itmEditProduct = new javax.swing.JMenuItem();
        mnuOrderContextMenu = new javax.swing.JPopupMenu();
        itmEditOrder = new javax.swing.JMenuItem();
        barStatusBar = new javax.swing.JPanel();
        lblStatusBarText = new javax.swing.JLabel();
        prgProgressBar = new javax.swing.JProgressBar();
        tabTabbedPane = new javax.swing.JTabbedPane();
        frmWelcome = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblLogoLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblJasonLabel = new javax.swing.JLabel();
        lblPeterLabel = new javax.swing.JLabel();
        lblCourseLabel = new javax.swing.JLabel();
        lblJohnLabel = new javax.swing.JLabel();
        lblProfessorLabel = new javax.swing.JLabel();
        frmCustomer = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCustomerList = new javax.swing.JTable();
        btnLoadCustomers = new javax.swing.JButton();
        btnAddNewCustomer = new javax.swing.JButton();
        lblCustomerSearchLabel = new javax.swing.JLabel();
        txtCustomerSearch = new javax.swing.JTextField();
        btnDeleteCustomer = new javax.swing.JButton();
        frmItem = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblItemList = new javax.swing.JTable();
        btnLoadItems = new javax.swing.JButton();
        btnAddNewItem = new javax.swing.JButton();
        txtItemSearch = new javax.swing.JTextField();
        lblItemSearchLabel = new javax.swing.JLabel();
        btnDeleteItem = new javax.swing.JButton();
        frmOrders = new javax.swing.JPanel();
        btnLoadOrders = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblOrderList = new javax.swing.JTable();
        btnAddNewOrder = new javax.swing.JButton();
        mnuMainMenu = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        itmExit = new javax.swing.JMenuItem();
        mnuView = new javax.swing.JMenu();
        mnuFont = new javax.swing.JMenu();
        itmDefault = new javax.swing.JMenuItem();
        itmSegoeUI = new javax.swing.JMenuItem();
        itmLucida = new javax.swing.JMenuItem();
        mnuColor = new javax.swing.JMenu();
        itmDefaultColor = new javax.swing.JMenuItem();
        itmBlueColor = new javax.swing.JMenuItem();
        itemOrangeColor = new javax.swing.JMenuItem();

        itmEditCustomer.setMnemonic('c');
        itmEditCustomer.setText("Edit customer...");
        itmEditCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmEditCustomerActionPerformed(evt);
            }
        });
        mnuCustomerContextMenu.add(itmEditCustomer);

        itmEditProduct.setMnemonic('p');
        itmEditProduct.setText("Edit product...");
        itmEditProduct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmEditProductActionPerformed(evt);
            }
        });
        mnuProductContextMenu.add(itmEditProduct);

        itmEditOrder.setMnemonic('o');
        itmEditOrder.setText("Edit order...");
        itmEditOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmEditOrderActionPerformed(evt);
            }
        });
        mnuOrderContextMenu.add(itmEditOrder);

        setBackground(new java.awt.Color(240, 240, 240));
        setFont(new java.awt.Font("Segoe UI", 0, 12));

        barStatusBar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        barStatusBar.setMaximumSize(new java.awt.Dimension(32767, 22));
        barStatusBar.setPreferredSize(new java.awt.Dimension(595, 22));

        lblStatusBarText.setFont(getFont());
        lblStatusBarText.setText("  placeholder label");
        lblStatusBarText.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout barStatusBarLayout = new javax.swing.GroupLayout(barStatusBar);
        barStatusBar.setLayout(barStatusBarLayout);
        barStatusBarLayout.setHorizontalGroup(
            barStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, barStatusBarLayout.createSequentialGroup()
                .addComponent(lblStatusBarText, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addGap(38, 38, 38)
                .addComponent(prgProgressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        barStatusBarLayout.setVerticalGroup(
            barStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(barStatusBarLayout.createSequentialGroup()
                .addGroup(barStatusBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(prgProgressBar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 16, Short.MAX_VALUE)
                    .addComponent(lblStatusBarText))
                .addContainerGap())
        );

        getContentPane().add(barStatusBar, java.awt.BorderLayout.SOUTH);

        tabTabbedPane.setPreferredSize(new java.awt.Dimension(667, 410));

        jPanel1.setLayout(new java.awt.BorderLayout());

        lblLogoLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/assign2/view/logo.png"))); // NOI18N
        lblLogoLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(lblLogoLabel, java.awt.BorderLayout.CENTER);

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));

        lblJasonLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblJasonLabel.setText("Jason Recillo - 100726948");

        lblPeterLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
        lblPeterLabel.setText("Peter Le - 100714258");

        lblCourseLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
        lblCourseLabel.setText("COMP 2121 - Internet Application Development");

        lblJohnLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
        lblJohnLabel.setText("John Jess Migia - 100713187");

        lblProfessorLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
        lblProfessorLabel.setText("Submitted to: Anjana Shah");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJasonLabel)
                    .addComponent(lblPeterLabel)
                    .addComponent(lblJohnLabel)
                    .addComponent(lblCourseLabel)
                    .addComponent(lblProfessorLabel))
                .addContainerGap(388, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblJasonLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPeterLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJohnLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCourseLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProfessorLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        javax.swing.GroupLayout frmWelcomeLayout = new javax.swing.GroupLayout(frmWelcome);
        frmWelcome.setLayout(frmWelcomeLayout);
        frmWelcomeLayout.setHorizontalGroup(
            frmWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmWelcomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
                .addContainerGap())
        );
        frmWelcomeLayout.setVerticalGroup(
            frmWelcomeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmWelcomeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabTabbedPane.addTab("Welcome", frmWelcome);

        tblCustomerList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "First Name", "Last Name", "Address", "Phone Number"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCustomerList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblCustomerListMouseReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tblCustomerList);
        tblCustomerList.getColumnModel().getColumn(0).setResizable(false);
        tblCustomerList.getColumnModel().getColumn(0).setPreferredWidth(32);
        tblCustomerList.getColumnModel().getColumn(1).setResizable(false);
        tblCustomerList.getColumnModel().getColumn(1).setPreferredWidth(72);
        tblCustomerList.getColumnModel().getColumn(2).setResizable(false);
        tblCustomerList.getColumnModel().getColumn(2).setPreferredWidth(88);
        tblCustomerList.getColumnModel().getColumn(3).setResizable(false);
        tblCustomerList.getColumnModel().getColumn(3).setPreferredWidth(261);
        tblCustomerList.getColumnModel().getColumn(4).setResizable(false);
        tblCustomerList.getColumnModel().getColumn(4).setPreferredWidth(87);

        btnLoadCustomers.setText("Load Customers");
        btnLoadCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadCustomersActionPerformed(evt);
            }
        });

        btnAddNewCustomer.setText("Add New Customer");
        btnAddNewCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewCustomerActionPerformed(evt);
            }
        });

        lblCustomerSearchLabel.setText("Search:");

        txtCustomerSearch.setText("Search...");
        txtCustomerSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtCustomerSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCustomerSearchFocusLost(evt);
            }
        });

        btnDeleteCustomer.setText("Delete Customer");
        btnDeleteCustomer.setEnabled(false);
        btnDeleteCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmCustomerLayout = new javax.swing.GroupLayout(frmCustomer);
        frmCustomer.setLayout(frmCustomerLayout);
        frmCustomerLayout.setHorizontalGroup(
            frmCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, frmCustomerLayout.createSequentialGroup()
                        .addComponent(btnLoadCustomers)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddNewCustomer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteCustomer)
                        .addGap(55, 55, 55)
                        .addComponent(lblCustomerSearchLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCustomerSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)))
                .addContainerGap())
        );
        frmCustomerLayout.setVerticalGroup(
            frmCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmCustomerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmCustomerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoadCustomers)
                    .addComponent(btnAddNewCustomer)
                    .addComponent(lblCustomerSearchLabel)
                    .addComponent(txtCustomerSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteCustomer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabTabbedPane.addTab("Customer", frmCustomer);

        tblItemList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Category", "Price", "Description", "Image", "Qty"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblItemList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblItemListMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tblItemList);
        tblItemList.getColumnModel().getColumn(0).setResizable(false);
        tblItemList.getColumnModel().getColumn(0).setPreferredWidth(26);
        tblItemList.getColumnModel().getColumn(1).setResizable(false);
        tblItemList.getColumnModel().getColumn(1).setPreferredWidth(124);
        tblItemList.getColumnModel().getColumn(2).setResizable(false);
        tblItemList.getColumnModel().getColumn(2).setPreferredWidth(87);
        tblItemList.getColumnModel().getColumn(3).setResizable(false);
        tblItemList.getColumnModel().getColumn(3).setPreferredWidth(49);
        tblItemList.getColumnModel().getColumn(4).setResizable(false);
        tblItemList.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblItemList.getColumnModel().getColumn(5).setResizable(false);
        tblItemList.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblItemList.getColumnModel().getColumn(6).setResizable(false);
        tblItemList.getColumnModel().getColumn(6).setPreferredWidth(54);

        btnLoadItems.setText("Load Items");
        btnLoadItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadItemsActionPerformed(evt);
            }
        });

        btnAddNewItem.setText("Add New Item");
        btnAddNewItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewItemActionPerformed(evt);
            }
        });

        txtItemSearch.setText("Search...");
        txtItemSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtItemSearchFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtItemSearchFocusLost(evt);
            }
        });

        lblItemSearchLabel.setText("Search:");

        btnDeleteItem.setText("Delete Item");
        btnDeleteItem.setEnabled(false);
        btnDeleteItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmItemLayout = new javax.swing.GroupLayout(frmItem);
        frmItem.setLayout(frmItemLayout);
        frmItemLayout.setHorizontalGroup(
            frmItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, frmItemLayout.createSequentialGroup()
                        .addComponent(btnLoadItems)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddNewItem)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteItem)
                        .addGap(143, 143, 143)
                        .addComponent(lblItemSearchLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtItemSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)))
                .addContainerGap())
        );
        frmItemLayout.setVerticalGroup(
            frmItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmItemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmItemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoadItems)
                    .addComponent(btnAddNewItem)
                    .addComponent(lblItemSearchLabel)
                    .addComponent(txtItemSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDeleteItem))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabTabbedPane.addTab("Products", frmItem);

        btnLoadOrders.setText("Load Orders");
        btnLoadOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadOrdersActionPerformed(evt);
            }
        });

        tblOrderList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Customer Name", "Order Date", "Number of Items"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOrderList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblOrderListMouseReleased(evt);
            }
        });
        jScrollPane3.setViewportView(tblOrderList);
        tblOrderList.getColumnModel().getColumn(0).setResizable(false);
        tblOrderList.getColumnModel().getColumn(0).setPreferredWidth(63);
        tblOrderList.getColumnModel().getColumn(1).setResizable(false);
        tblOrderList.getColumnModel().getColumn(1).setPreferredWidth(190);
        tblOrderList.getColumnModel().getColumn(2).setResizable(false);
        tblOrderList.getColumnModel().getColumn(2).setPreferredWidth(193);
        tblOrderList.getColumnModel().getColumn(3).setResizable(false);
        tblOrderList.getColumnModel().getColumn(3).setPreferredWidth(193);

        btnAddNewOrder.setText("Add New Order");
        btnAddNewOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewOrderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frmOrdersLayout = new javax.swing.GroupLayout(frmOrders);
        frmOrders.setLayout(frmOrdersLayout);
        frmOrdersLayout.setHorizontalGroup(
            frmOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, frmOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 692, Short.MAX_VALUE)
                    .addGroup(frmOrdersLayout.createSequentialGroup()
                        .addComponent(btnLoadOrders)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddNewOrder)))
                .addContainerGap())
        );
        frmOrdersLayout.setVerticalGroup(
            frmOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frmOrdersLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frmOrdersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLoadOrders)
                    .addComponent(btnAddNewOrder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabTabbedPane.addTab("Orders", frmOrders);

        getContentPane().add(tabTabbedPane, java.awt.BorderLayout.CENTER);

        mnuFile.setMnemonic('f');
        mnuFile.setText("File");

        itmExit.setMnemonic('x');
        itmExit.setText("Exit");
        itmExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                itmExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                itmExitMouseExited(evt);
            }
        });
        itmExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmExitActionPerformed(evt);
            }
        });
        mnuFile.add(itmExit);

        mnuMainMenu.add(mnuFile);

        mnuView.setMnemonic('v');
        mnuView.setText("View");

        mnuFont.setText("Change Font");
        mnuFont.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mnuFontMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                mnuFontMouseExited(evt);
            }
        });

        itmDefault.setText("Default");
        itmDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmDefaultActionPerformed(evt);
            }
        });
        mnuFont.add(itmDefault);

        itmSegoeUI.setText("Segoe UI");
        itmSegoeUI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmSegoeUIActionPerformed(evt);
            }
        });
        mnuFont.add(itmSegoeUI);

        itmLucida.setText("Lucida Grande");
        itmLucida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmLucidaActionPerformed(evt);
            }
        });
        mnuFont.add(itmLucida);

        mnuView.add(mnuFont);

        mnuColor.setText("jMenu1");

        itmDefaultColor.setText("jMenuItem1");
        itmDefaultColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmDefaultColorActionPerformed(evt);
            }
        });
        mnuColor.add(itmDefaultColor);

        itmBlueColor.setText("jMenuItem1");
        itmBlueColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itmBlueColorActionPerformed(evt);
            }
        });
        mnuColor.add(itmBlueColor);

        itemOrangeColor.setText("jMenuItem1");
        itemOrangeColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemOrangeColorActionPerformed(evt);
            }
        });
        mnuColor.add(itemOrangeColor);

        mnuView.add(mnuColor);

        mnuMainMenu.add(mnuView);

        setJMenuBar(mnuMainMenu);
    }// </editor-fold>//GEN-END:initComponents
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Event Handlers">

    private void mnuFontMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuFontMouseEntered
        setStatusBarText("Changes the current font.");
    }//GEN-LAST:event_mnuFontMouseEntered

    private void mnuFontMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mnuFontMouseExited
        clearStatusBarText();
    }//GEN-LAST:event_mnuFontMouseExited

    private void itmExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itmExitMouseEntered
        setStatusBarText("Exits the application.");
    }//GEN-LAST:event_itmExitMouseEntered

    private void itmExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itmExitMouseExited
        clearStatusBarText();
    }//GEN-LAST:event_itmExitMouseExited

    private void itmExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_itmExitActionPerformed

    private void btnLoadCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadCustomersActionPerformed
        Thread loadCustomerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DefaultTableModel model = (DefaultTableModel) tblCustomerList.getModel();
                    model.setRowCount(0);

                    setStatusBarText("Retrieving customers from database...");
                    setProgressBarEnabled(true);
                    Utils.log_info("Retrieving customers from database...");
                    Customer[] list = CustomerAccess.getCustomers();
                    for (Customer c : list) {
                        ArrayList<Object> cust = new ArrayList<Object>();
                        cust.add(c.getCustomerId());
                        cust.add(c.getFirstName());
                        cust.add(c.getLastName());
                        cust.add(c.getAddress());
                        cust.add(Utils.formatPhoneNumber(c));
                        //add customers to table here
                        model.addRow(cust.toArray());
                    }
                    setStatusBarText("Done.");
                    setProgressBarEnabled(false);
                    Utils.log_info("Done loading customers.");
                } catch (SQLException ex) {
                    Utils.log_error(ex.getMessage());
                }
            }
        }, "RetrieveCustomers");
        loadCustomerThread.start();
    }//GEN-LAST:event_btnLoadCustomersActionPerformed

    private void btnLoadItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadItemsActionPerformed
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DefaultTableModel model = (DefaultTableModel) tblItemList.getModel();
                    model.setRowCount(0);

                    setStatusBarText("Retrieving items from database...");
                    setProgressBarEnabled(true);
                    Utils.log_info("Retrieving items from database...");
                    Item[] list = ItemAccess.getItems();
                    for (Item i : list) {
                        ArrayList<Object> items = new ArrayList<Object>();
                        items.add(i.getItemId());
                        items.add(i.getName());
                        items.add(i.getCategory().getCategoryName());
                        items.add(i.getPrice());
                        items.add(i.getDescription() == null ? "no description" : i.getDescription());
                        items.add(i.getImage() == null ? "no image" : "<binary data>");
                        items.add(i.getQtyInStock());
                        //add items to table here
                        model.addRow(items.toArray());
                    }
                    setStatusBarText("Done.");
                    setProgressBarEnabled(false);
                    Utils.log_info("Done loading items.");
                } catch (SQLException ex) {
                    Utils.log_error(ex.getMessage());
                }
            }
        }, "RetrieveItems");
        thread.start();
    }//GEN-LAST:event_btnLoadItemsActionPerformed

    private void btnLoadOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadOrdersActionPerformed
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DefaultTableModel model = (DefaultTableModel) tblOrderList.getModel();
                    model.setRowCount(0);

                    setStatusBarText("Retrieving orders from database...");
                    setProgressBarEnabled(true);
                    Utils.log_info("Retrieving orders from database...");
                    Order[] list = OrderAccess.getOrders();
                    for (Order o : list) {
                        ArrayList<Object> orders = new ArrayList<Object>();
                        orders.add(o.getOrderId());
                        orders.add(String.format("%s %s", o.getCustomer().getFirstName(), o.getCustomer().getLastName()));
                        orders.add(o.getDate());
                        orders.add(OrderLineAccess.getOrderLineCount(o));
                        model.addRow(orders.toArray());
                    }
                    setStatusBarText("Done.");
                    setProgressBarEnabled(false);
                    Utils.log_info("Done loading orders.");
                } catch (SQLException ex) {
                    Utils.log_error(ex.getMessage());
                }
            }
        }, "RetrieveOrders");
        thread.start();
    }//GEN-LAST:event_btnLoadOrdersActionPerformed

    private void btnAddNewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewItemActionPerformed
        JFrame frame = new JFrame("New Item");
        openChildForm(frame, new ItemForm(frame));
    }//GEN-LAST:event_btnAddNewItemActionPerformed

    private void btnAddNewCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewCustomerActionPerformed
        JFrame frame = new JFrame("New Customer");
        openChildForm(frame, new CustomerForm(frame));
    }//GEN-LAST:event_btnAddNewCustomerActionPerformed

    private void btnAddNewOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewOrderActionPerformed
        JFrame frame = new JFrame("New Order");
        openChildForm(frame, new OrderForm(frame));
    }//GEN-LAST:event_btnAddNewOrderActionPerformed

    private void tblCustomerListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCustomerListMouseReleased
        if (evt.isPopupTrigger()) {
            Point p = evt.getPoint();
            int rowNumber = tblCustomerList.rowAtPoint(p);
            ListSelectionModel model = tblCustomerList.getSelectionModel();
            model.setSelectionInterval(rowNumber, rowNumber);
        }

        if (tblCustomerList.getSelectedRow() != -1) {
            btnDeleteCustomer.setEnabled(true);
        }
    }//GEN-LAST:event_tblCustomerListMouseReleased

    private void itmEditCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmEditCustomerActionPerformed
        try {
            int customerId = (Integer) tblCustomerList.getModel().getValueAt(tblCustomerList.getSelectedRow(), 0);
            Customer c = CustomerAccess.findCustomer(customerId);
            JFrame frame = new JFrame(String.format("Edit %s", c.getFullName()));
            openChildForm(frame, new CustomerForm(frame, c));
        } catch (SQLException ex) {
            Utils.log_error("Could not open customer information for editing.");
        } catch (HeadlessException ex) {
            //
        }
    }//GEN-LAST:event_itmEditCustomerActionPerformed

    private void itmEditProductActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmEditProductActionPerformed
        try {
            int itemId = (Integer) tblItemList.getModel().getValueAt(tblItemList.getSelectedRow(), 0);
            Item i = ItemAccess.findItem(itemId);
            JFrame frame = new JFrame(String.format("Edit %s", i.getName()));
            openChildForm(frame, new ItemForm(frame, i));
        } catch (SQLException ex) {
            Utils.log_error("Could not open item information for editing.");
        } catch (HeadlessException ex) {
            //
        }
    }//GEN-LAST:event_itmEditProductActionPerformed

    private void itmEditOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmEditOrderActionPerformed
        try {
            int orderId = (Integer) tblOrderList.getModel().getValueAt(tblOrderList.getSelectedRow(), 0);
            Order o = OrderAccess.findOrder(orderId);
            JFrame frame = new JFrame(String.format("Edit order for %s", o.getCustomer().getFullName()));
            openChildForm(frame, new OrderForm(frame, o));
        } catch (SQLException ex) {
            Utils.log_error("Could not open order information for editing.");
        } catch (HeadlessException ex) {
            //
        }
    }//GEN-LAST:event_itmEditOrderActionPerformed

    private void btnDeleteCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteCustomerActionPerformed
        int row = tblCustomerList.getSelectedRow();
        int customerId = (Integer) tblCustomerList.getModel().getValueAt(row, 0);
        try {
            if (JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to delete the selected customer?", "Delete?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                CustomerAccess.deleteCustomer(customerId);
            }
        } catch (SQLException ex) {
            Utils.log_error(ex.getMessage());
        }
        refreshCustomerTable();
    }//GEN-LAST:event_btnDeleteCustomerActionPerformed

    private void txtCustomerSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCustomerSearchFocusGained
        if ("Search...".equals(txtCustomerSearch.getText())) {
            txtCustomerSearch.setText("");
        }
    }//GEN-LAST:event_txtCustomerSearchFocusGained

    private void txtCustomerSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCustomerSearchFocusLost
        if ("".equals(txtCustomerSearch.getText())) {
            txtCustomerSearch.setText("Search...");
        }
    }//GEN-LAST:event_txtCustomerSearchFocusLost

    private void txtItemSearchFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItemSearchFocusGained
        if ("Search...".equals(txtItemSearch.getText())) {
            txtItemSearch.setText("");
        }
    }//GEN-LAST:event_txtItemSearchFocusGained

    private void txtItemSearchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtItemSearchFocusLost
        if ("".equals(txtItemSearch.getText())) {
            txtItemSearch.setText("Search...");
        }
    }//GEN-LAST:event_txtItemSearchFocusLost

    private void btnDeleteItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteItemActionPerformed
        int row = tblItemList.getSelectedRow();
        int itemId = (Integer) tblItemList.getModel().getValueAt(row, 0);
        try {
            if (JOptionPane.showConfirmDialog(rootPane, "Are you sure you want to delete the selected item?", "Delete?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                ItemAccess.deleteItem(itemId);
            }
        } catch (SQLException ex) {
            Utils.log_error(ex.getMessage());
        }
        refreshItemTable();
    }//GEN-LAST:event_btnDeleteItemActionPerformed

    private void tblItemListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblItemListMouseReleased
        if (evt.isPopupTrigger()) {
            Point p = evt.getPoint();
            int rowNumber = tblItemList.rowAtPoint(p);
            ListSelectionModel model = tblItemList.getSelectionModel();
            model.setSelectionInterval(rowNumber, rowNumber);
        }

        if (tblItemList.getSelectedRow() != -1) {
            btnDeleteItem.setEnabled(true);
        }
    }//GEN-LAST:event_tblItemListMouseReleased

    private void tblOrderListMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrderListMouseReleased
        if (evt.isPopupTrigger()) {
            Point p = evt.getPoint();
            int rowNumber = tblOrderList.rowAtPoint(p);
            ListSelectionModel model = tblCustomerList.getSelectionModel();
            model.setSelectionInterval(rowNumber, rowNumber);
        }
    }//GEN-LAST:event_tblOrderListMouseReleased

    private void itmDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmDefaultActionPerformed
        Font font = new Font("Tahoma", Font.PLAIN, 14);
        setFonts(font);
    }//GEN-LAST:event_itmDefaultActionPerformed

    private void itmSegoeUIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmSegoeUIActionPerformed
        Font font = new Font("Segoe UI", Font.PLAIN, 14);
        setFonts(font);
    }//GEN-LAST:event_itmSegoeUIActionPerformed

    private void itmLucidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmLucidaActionPerformed
        Font font = new Font("Lucida Sans", Font.PLAIN, 14);
        setFonts(font);
    }//GEN-LAST:event_itmLucidaActionPerformed

    private void itmDefaultColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmDefaultColorActionPerformed
        Color color = new Color(240, 240, 240);
        jPanel1.setBackground(color);
    }//GEN-LAST:event_itmDefaultColorActionPerformed

    private void itmBlueColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itmBlueColorActionPerformed
        Color color = new Color(37, 170, 225);
        jPanel1.setBackground(color);
    }//GEN-LAST:event_itmBlueColorActionPerformed

    private void itemOrangeColorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemOrangeColorActionPerformed
        Color color = new Color(225, 170, 37);
        jPanel1.setBackground(color);
    }//GEN-LAST:event_itemOrangeColorActionPerformed
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Generated Code">
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel barStatusBar;
    private javax.swing.JButton btnAddNewCustomer;
    private javax.swing.JButton btnAddNewItem;
    private javax.swing.JButton btnAddNewOrder;
    private javax.swing.JButton btnDeleteCustomer;
    private javax.swing.JButton btnDeleteItem;
    private javax.swing.JButton btnLoadCustomers;
    private javax.swing.JButton btnLoadItems;
    private javax.swing.JButton btnLoadOrders;
    private javax.swing.JPanel frmCustomer;
    private javax.swing.JPanel frmItem;
    private javax.swing.JPanel frmOrders;
    private javax.swing.JPanel frmWelcome;
    private javax.swing.JMenuItem itemOrangeColor;
    private javax.swing.JMenuItem itmBlueColor;
    private javax.swing.JMenuItem itmDefault;
    private javax.swing.JMenuItem itmDefaultColor;
    private javax.swing.JMenuItem itmEditCustomer;
    private javax.swing.JMenuItem itmEditOrder;
    private javax.swing.JMenuItem itmEditProduct;
    private javax.swing.JMenuItem itmExit;
    private javax.swing.JMenuItem itmLucida;
    private javax.swing.JMenuItem itmSegoeUI;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCourseLabel;
    private javax.swing.JLabel lblCustomerSearchLabel;
    private javax.swing.JLabel lblItemSearchLabel;
    private javax.swing.JLabel lblJasonLabel;
    private javax.swing.JLabel lblJohnLabel;
    private javax.swing.JLabel lblLogoLabel;
    private javax.swing.JLabel lblPeterLabel;
    private javax.swing.JLabel lblProfessorLabel;
    private javax.swing.JLabel lblStatusBarText;
    private javax.swing.JMenu mnuColor;
    private javax.swing.JPopupMenu mnuCustomerContextMenu;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenu mnuFont;
    private javax.swing.JMenuBar mnuMainMenu;
    private javax.swing.JPopupMenu mnuOrderContextMenu;
    private javax.swing.JPopupMenu mnuProductContextMenu;
    private javax.swing.JMenu mnuView;
    private javax.swing.JProgressBar prgProgressBar;
    private javax.swing.JTabbedPane tabTabbedPane;
    private javax.swing.JTable tblCustomerList;
    private javax.swing.JTable tblItemList;
    private javax.swing.JTable tblOrderList;
    private javax.swing.JTextField txtCustomerSearch;
    private javax.swing.JTextField txtItemSearch;
    // End of variables declaration//GEN-END:variables
    //</editor-fold>
}
