/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ItemForm.java
 *
 * Created on Apr 3, 2012, 12:40:11 PM
 */
package com.assign2.view;

import com.assign2.Utils;
import com.assign2.business.Category;
import com.assign2.business.Item;
import com.assign2.data.CategoryAccess;
import com.assign2.data.ItemAccess;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Jason Recillo
 */
public class ItemForm extends javax.swing.JPanel {
    private JFrame parent;
    private Item theItem;
    private File image;
    private boolean nameChanged, categoryChanged, priceChanged, descriptionChanged, imageChanged, quantityChanged;
    private String originalName, originalDescription;
    private Category originalCategory;
    private double originalPrice;
    private int originalQuantity;
    boolean isNewItem;

    private enum FormType {
        Name, Category, Price, Description, Quantity
    }

    private class CategoryItemListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if ("-- Select Category --".equals(e.getItem().toString())) {
                Utils.log_debug("%s not changed.", FormType.Category);
                categoryChanged = false;
                return;
            }

            if (!isNewItem) {
                if (((Category) e.getItem()).equals(originalCategory)) {
                    Utils.log_debug("%s not changed.", FormType.Category);
                    categoryChanged = false;
                } else {
                    Utils.log_debug("%s changed.", FormType.Category);
                    categoryChanged = true;
                }
            }
        }
    }

    private class QuantityValueListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            if ((Integer) ((JSpinner) e.getSource()).getValue() == originalQuantity) {
                Utils.log_debug("%s not changed.", FormType.Quantity);
                quantityChanged = false;
            } else {
                Utils.log_debug("%s changed.", FormType.Quantity);
                categoryChanged = true;
            }
        }
    }

    public ItemForm(JFrame parent) {
        initComponents();
        populateCategoryComboBox();
        theItem = new Item();
        this.parent = parent;
        lblImagePath.setText("");
        CategoryItemListener listener = new CategoryItemListener();
        cboCategories.addItemListener(listener);
        isNewItem = true;
    }

    public ItemForm(JFrame parent, Item i) {
        try {
            initComponents();
            theItem = i;
            populateCategoryComboBox();
            lblItemId.setText(String.valueOf(i.getItemId()));
            txtName.setText(i.getName());
            //category combo box

            txtPrice.setText(String.valueOf(i.getPrice()));
            txtDescription.setText(i.getDescription());
            imgItemImage.setIcon(new ImageIcon(ItemAccess.getItemImage(i.getItemId(), true)));
            spnQuantityInStock.setValue(i.getQtyInStock());
            CategoryItemListener catListener = new CategoryItemListener();
            cboCategories.addItemListener(catListener);
            QuantityValueListener qtyListener = new QuantityValueListener();
            spnQuantityInStock.addChangeListener(qtyListener);

            originalName = theItem.getName();
            originalCategory = theItem.getCategory();
            originalDescription = theItem.getDescription();
            originalPrice = theItem.getPrice();
            originalQuantity = theItem.getQtyInStock();

            nameChanged = false;
            categoryChanged = false;
            priceChanged = false;
            descriptionChanged = false;
            imageChanged = false;
            quantityChanged = false;
            this.parent = parent;
            lblImagePath.setText("");
            isNewItem = false;
        } catch (SQLException ex) {
            Utils.log_error(ex.getMessage());
        }
    }

    private void populateCategoryComboBox() {
        try {
            Category[] categories = CategoryAccess.getCategories();
            for (Category c : categories) {
                cboCategories.addItem(c);
            }
            try {
                // Set the selected category in the combobox to the category of the item.
                // if this throws NullPointerException, theItem is not set, so
                // don't go any further
                theItem.getName();
                for (int i = 0; i < categories.length; i++) {
                    if (theItem.getCategory().equals(categories[i])) {
                        cboCategories.setSelectedIndex(i + 1);
                        return;
                    }
                }
            } catch (NullPointerException e) {
                Utils.log_info("theItem not set");
            }
        } catch (SQLException ex) {
            Utils.log_error(ex.getMessage());
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings ("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblItemIdLabel = new javax.swing.JLabel();
        lblNameLabel = new javax.swing.JLabel();
        lblCategoryLabel = new javax.swing.JLabel();
        lblPriceLabel = new javax.swing.JLabel();
        lblDescriptionLabel = new javax.swing.JLabel();
        lblQuantityLabel = new javax.swing.JLabel();
        imgItemImage = new javax.swing.JLabel();
        lblItemId = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        cboCategories = new javax.swing.JComboBox();
        txtPrice = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescription = new javax.swing.JTextArea();
        spnQuantityInStock = new javax.swing.JSpinner();
        lblChangeImageLabel = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnChooseImage = new javax.swing.JButton();
        lblImagePath = new javax.swing.JLabel();

        lblItemIdLabel.setText("Item ID:");

        lblNameLabel.setText("Name:");

        lblCategoryLabel.setText("Category:");

        lblPriceLabel.setText("Price:");

        lblDescriptionLabel.setText("Description:");

        lblQuantityLabel.setText("Quantity in Stock:");

        imgItemImage.setMaximumSize(new java.awt.Dimension(100, 100));
        imgItemImage.setMinimumSize(new java.awt.Dimension(100, 100));
        imgItemImage.setPreferredSize(new java.awt.Dimension(100, 100));

        lblItemId.setText("Item not saved");

        txtName.setText("Item Name");
        txtName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtNameFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNameFocusLost(evt);
            }
        });

        cboCategories.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-- Select Category --" }));

        txtPrice.setText("Price");
        txtPrice.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPriceFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPriceFocusLost(evt);
            }
        });

        txtDescription.setColumns(20);
        txtDescription.setFont(new java.awt.Font("Tahoma", 0, 11));
        txtDescription.setRows(5);
        txtDescription.setText("Description");
        txtDescription.setWrapStyleWord(true);
        txtDescription.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtDescriptionFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescriptionFocusLost(evt);
            }
        });
        jScrollPane1.setViewportView(txtDescription);

        lblChangeImageLabel.setText("Image:");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnChooseImage.setText("Choose File...");
        btnChooseImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseImageActionPerformed(evt);
            }
        });

        lblImagePath.setText("placeholder");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(imgItemImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblItemIdLabel)
                            .addComponent(lblNameLabel)
                            .addComponent(lblCategoryLabel)
                            .addComponent(lblPriceLabel)
                            .addComponent(lblDescriptionLabel)
                            .addComponent(lblQuantityLabel)
                            .addComponent(lblChangeImageLabel))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblItemId)
                            .addComponent(txtName, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                            .addComponent(spnQuantityInStock, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(cboCategories, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtPrice, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnChooseImage)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblImagePath, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblItemIdLabel)
                            .addComponent(lblItemId))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblNameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboCategories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCategoryLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPriceLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescriptionLabel)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spnQuantityInStock, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblQuantityLabel)))
                    .addComponent(imgItemImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblChangeImageLabel)
                    .addComponent(btnChooseImage)
                    .addComponent(lblImagePath))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSave))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnChooseImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseImageActionPerformed
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            image = fc.getSelectedFile();
            String path = image.getAbsolutePath();
            lblImagePath.setText(String.format("%s...%s", path.substring(0, 9), path.substring(path.length() - 10, path.length())));
            imageChanged = true;
        }
    }//GEN-LAST:event_btnChooseImageActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        parent.setVisible(false);
        parent.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        try {
            if (isNewItem) {
                Item i = new Item();
                if (!("-- Select Category --".equals(cboCategories.getSelectedItem()))) {
                    i.setCategory((Category) cboCategories.getSelectedItem());
                } else {
                    Utils.log_error("Please select a category.");
                    return;
                }
                i.setDescription(txtDescription.getText());
                i.setName(txtName.getText());
                i.setPrice(Double.parseDouble(txtPrice.getText()));
                i.setQtyInStock((Integer) spnQuantityInStock.getValue());
                try {
                    i.setImage(image.getAbsolutePath());
                } catch (NullPointerException e) {
                    i.setImage("");
                }
                ItemAccess.addNewItem(i);
            } else {
                // only send changed values to the database
                int itemId = Integer.valueOf(lblItemId.getText());
                if (priceChanged) {
                    ItemAccess.updateItem(itemId, ItemAccess.PRICE, String.valueOf(txtPrice));
                }
                if (nameChanged) {
                    ItemAccess.updateItem(itemId, ItemAccess.NAME, txtName.getText());
                }
                if (categoryChanged) {
                    ItemAccess.updateItem(itemId, ItemAccess.CATEGORY, String.valueOf(originalCategory.getCategoryId()));
                }
                if (quantityChanged) {
                    ItemAccess.updateItem(itemId, ItemAccess.QUANTITY_IN_STOCK, String.valueOf(spnQuantityInStock.getValue()));
                }
                if (descriptionChanged) {
                    ItemAccess.updateItem(itemId, ItemAccess.DESCRIPTION, txtDescription.getText());
                }
                if (imageChanged) {
                    ItemAccess.saveItemImage(itemId, image.getAbsolutePath());
                }
            }

            parent.setVisible(false);
            parent.dispose();
        } catch (SQLException ex) {
            Utils.log_error(ex.getMessage());
        } catch (FileNotFoundException ex) {
            Utils.log_error(ex.getMessage());
        } catch (NumberFormatException ex) {
            Utils.log_error(ex.getMessage());
        } catch (NullPointerException ex) {
            Utils.log_error(ex.getMessage());
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtNameFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusGained
        if ("Item Name".equals(txtName.getText())) {
            txtName.setText("");
            Utils.log_debug("%s changed.", FormType.Name);
            nameChanged = true;
        } else if ("".equals(txtName.getText())) {
            txtName.setText("Item Name");
            Utils.log_debug("%s not changed.", FormType.Name);
            nameChanged = false;
        }

        if (txtName.getText().equals(originalName)) {
            Utils.log_debug("%s not changed.", FormType.Name);
            nameChanged = false;
        } else {
            Utils.log_debug("%s changed.", FormType.Name);
            nameChanged = true;
        }
    }//GEN-LAST:event_txtNameFocusGained

    private void txtNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNameFocusLost
        if ("Item Name".equals(txtName.getText())) {
            txtName.setText("");
            Utils.log_debug("%s changed.", FormType.Name);
            nameChanged = true;
        } else if ("".equals(txtName.getText())) {
            txtName.setText("Item Name");
            Utils.log_debug("%s not changed.", FormType.Name);
            nameChanged = false;
        }

        if (txtName.getText().equals(originalName)) {
            Utils.log_debug("%s not changed.", FormType.Name);
            nameChanged = false;
        } else {
            Utils.log_debug("%s changed.", FormType.Name);
            nameChanged = true;
        }
    }//GEN-LAST:event_txtNameFocusLost

    private void txtPriceFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPriceFocusGained
        if ("Price".equals(txtPrice.getText())) {
            txtPrice.setText("");
            Utils.log_debug("%s changed.", FormType.Price);
            priceChanged = true;
        } else if ("".equals(txtPrice.getText())) {
            txtPrice.setText("Price");
            Utils.log_debug("%s not changed.", FormType.Price);
            priceChanged = false;
        }

        if (txtPrice.getText().equals(String.valueOf(originalPrice))) {
            Utils.log_debug("%s not changed.", FormType.Price);
            priceChanged = false;
        } else {
            Utils.log_debug("%s changed.", FormType.Price);
            priceChanged = true;
        }
    }//GEN-LAST:event_txtPriceFocusGained

    private void txtPriceFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPriceFocusLost
        if ("Price".equals(txtPrice.getText())) {
            txtPrice.setText("");
            Utils.log_debug("%s changed.", FormType.Price);
            priceChanged = true;
        } else if ("".equals(txtPrice.getText())) {
            txtPrice.setText("Price");
            Utils.log_debug("%s not changed.", FormType.Price);
            priceChanged = false;
        }

        if (txtPrice.getText().equals(String.valueOf(originalPrice))) {
            Utils.log_debug("%s not changed.", FormType.Price);
            priceChanged = false;
        } else {
            Utils.log_debug("%s changed.", FormType.Price);
            priceChanged = true;
        }
    }//GEN-LAST:event_txtPriceFocusLost

    private void txtDescriptionFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescriptionFocusGained
        if ("Description".equals(txtDescription.getText())) {
            txtDescription.setText("");
            Utils.log_debug("%s changed.", FormType.Description);
            descriptionChanged = true;
        } else if ("".equals(txtDescription.getText())) {
            txtDescription.setText("Description");
            Utils.log_debug("%s not changed.", FormType.Description);
            descriptionChanged = false;
        }

        if (txtDescription.getText().equals(String.valueOf(originalDescription))) {
            Utils.log_debug("%s not changed.", FormType.Description);
            descriptionChanged = false;
        } else {
            Utils.log_debug("%s changed.", FormType.Description);
            descriptionChanged = true;
        }
    }//GEN-LAST:event_txtDescriptionFocusGained

    private void txtDescriptionFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescriptionFocusLost
        if ("Description".equals(txtDescription.getText())) {
            txtDescription.setText("");
            Utils.log_debug("%s changed.", FormType.Description);
            descriptionChanged = true;
        } else if ("".equals(txtDescription.getText())) {
            txtDescription.setText("Description");
            Utils.log_debug("%s not changed.", FormType.Description);
            descriptionChanged = false;
        }

        if (txtDescription.getText().equals(String.valueOf(originalDescription))) {
            Utils.log_debug("%s not changed.", FormType.Description);
            descriptionChanged = false;
        } else {
            Utils.log_debug("%s changed.", FormType.Description);
            descriptionChanged = true;
        }
    }//GEN-LAST:event_txtDescriptionFocusLost
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnChooseImage;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cboCategories;
    private javax.swing.JLabel imgItemImage;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCategoryLabel;
    private javax.swing.JLabel lblChangeImageLabel;
    private javax.swing.JLabel lblDescriptionLabel;
    private javax.swing.JLabel lblImagePath;
    private javax.swing.JLabel lblItemId;
    private javax.swing.JLabel lblItemIdLabel;
    private javax.swing.JLabel lblNameLabel;
    private javax.swing.JLabel lblPriceLabel;
    private javax.swing.JLabel lblQuantityLabel;
    private javax.swing.JSpinner spnQuantityInStock;
    private javax.swing.JTextArea txtDescription;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    // End of variables declaration//GEN-END:variables
}
