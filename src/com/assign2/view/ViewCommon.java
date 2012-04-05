/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assign2.view;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

/**
 *
 * @author Jason Recillo
 */
public class ViewCommon {
    public static void doLeftClick() {
        try {
            Robot robot = new Robot();
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
        } catch (AWTException ex) {
            //
        }
    }
    public static void doRightClick() {
        try {
            Robot robot = new Robot();
            robot.mousePress(InputEvent.BUTTON3_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_MASK);
        } catch (AWTException ex) {
            //
        }
    }
}
