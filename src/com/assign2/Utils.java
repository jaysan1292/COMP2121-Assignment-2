/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2;

import com.assign2.business.Customer;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Jason Recillo
 */
public class Utils {
    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String formatPhoneNumber(Customer c) {
        String out = c.getPhoneNumber();
        return String.format("(%s) %s-%s", out.substring(0, 3), out.substring(3, 6), out.substring(6, 10));
    }

    //<editor-fold defaultstate="collapsed" desc="Debug Loggers">
    public static void log_debug(String s) {
        StackTraceElement frame = getFrame();
        System.out.println(String.format("[DEBUG] (%s:%s): %s", frame.getFileName(), frame.getLineNumber(), s));
    }

    public static void log_debug(String s, Object... o) {
        StackTraceElement frame = getFrame();
        System.out.println(String.format("[DEBUG] (%s:%s): %s", frame.getFileName(), frame.getLineNumber(), String.format(s, o)));
    }

    public static void log_info(String s) {
        StackTraceElement frame = getFrame();
        System.out.println(String.format(" [INFO] (%s:%s): %s", frame.getFileName(), frame.getLineNumber(), s));
    }

    public static void log_info(String s, Object... o) {
        StackTraceElement frame = getFrame();
        System.out.println(String.format(" [INFO] (%s:%s): %s", frame.getFileName(), frame.getLineNumber(), String.format(s, o)));
    }

    public static void log_warning(String s) {
        StackTraceElement frame = getFrame();
        System.out.println(String.format(" [WARN] (%s:%s): %s", frame.getFileName(), frame.getLineNumber(), s));
    }

    public static void log_warning(String s, Object... o) {
        StackTraceElement frame = getFrame();
        System.out.println(String.format(" [WARN] (%s:%s): %s", frame.getFileName(), frame.getLineNumber(), String.format(s, o)));
    }

    public static void log_error(String s) {
        StackTraceElement frame = getFrame();
        System.err.println(String.format("[ERROR] (%s:%s): %s", frame.getFileName(), frame.getLineNumber(), s));
        JOptionPane.showMessageDialog(null, s,"Error",JOptionPane.ERROR_MESSAGE);
    }

    public static void log_error(String s, Object... o) {
        StackTraceElement frame = getFrame();
        System.err.println(String.format("[ERROR] (%s:%s): %s", frame.getFileName(), frame.getLineNumber(), String.format(s, o)));
        JOptionPane.showMessageDialog(null, String.format(s, o),"Error",JOptionPane.ERROR_MESSAGE);
    }

    private static StackTraceElement getFrame() {
        return Thread.currentThread().getStackTrace()[3];
    }
    //</editor-fold>
}
