/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author kaueq
 */
import gui.frames.ConexaoFrame;

import javax.swing.SwingUtilities;

public class GuiMain {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new ConexaoFrame();

        });
    }
}
