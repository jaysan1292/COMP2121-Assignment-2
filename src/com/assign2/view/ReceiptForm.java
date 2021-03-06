/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ReceiptForm.java
 *
 * Created on Apr 4, 2012, 10:17:58 AM
 */
package com.assign2.view;

import com.assign2.business.Order;
import com.assign2.business.OrderLine;
import com.assign2.data.OrderLineAccess;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 *
 * @author Jason Recillo
 */
public class ReceiptForm extends javax.swing.JPanel {
    private JFrame parent;

    /** Creates new form ReceiptForm */
    public ReceiptForm(JFrame parent, Order o) {
        initComponents();
        this.parent = parent;
        jTextArea1.setText(printReceipt(o));
    }

    private String printReceipt(Order o) {
        StringBuilder receipt = new StringBuilder();
        String newLine = "\n";
        try {
            double total = 0;
            receipt.append(String.format("     BTB COMPUTER HARDWARE\n\n    ** TRANSACTION RECORD **\n\nOrder #: %s \n\n", o.getOrderId()));
            OrderLine[] lines = OrderLineAccess.getOrderLines(o);
            for (OrderLine ol : lines) {
                receipt.append(ol.getItem().getName().substring(0, 22)).append(" ");
                receipt.append(padRight(String.format("%d @", ol.getQuantity()), 4));
                receipt.append(padLeft(String.format("$%.2f", ol.getQuantity() * ol.getItem().getPrice()), 5));
                receipt.append(newLine);
                total += ol.getQuantity() * ol.getItem().getPrice();
            }
            receipt.append(padLeft("==========", 34)).append(newLine);
            receipt.append(padLeft(String.format("TOTAL CAD $%.2f", total), 34));
            receipt.append(newLine).append(newLine);
            receipt.append(String.format("%s",o.getDate())).append(newLine).append(newLine);
            receipt.append("THANK YOU FOR YOUR BUSINESS");
        } catch (SQLException ex) {
            //
        }
        return receipt.toString();
    }

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings ("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(32);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 332, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        parent.setVisible(false);
        parent.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
