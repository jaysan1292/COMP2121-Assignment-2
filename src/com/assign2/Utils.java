/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Jason Recillo
 */
public class Utils {
    public static String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    //TODO: [{name}] ({filename}:{line number}): {message}
    public static void log_debug(String s) {
        System.out.println(String.format("[DEBUG]: %s", s));
    }

    public static void log_debug(String s, Object... o) {
        System.out.println(String.format("[DEBUG]: %s", String.format(s, o)));
    }

    public static void log_info(String s) {
        System.out.println(String.format("[INFO] : %s", s));
    }

    public static void log_info(String s, Object... o) {
        System.out.println(String.format("[INFO] : %s", String.format(s, o)));
    }

    public static void log_warning(String s) {
        System.out.println(String.format("[WARN] : %s", s));
    }

    public static void log_warning(String s, Object... o) {
        System.out.println(String.format("[WARN] : %s", String.format(s, o)));
    }

    public static void log_error(String s) {
        System.out.println(String.format("[ERROR]: %s", s));
    }

    public static void log_error(String s, Object... o) {
        System.out.println(String.format("[ERROR]: %s", String.format(s, o)));
    }
}
