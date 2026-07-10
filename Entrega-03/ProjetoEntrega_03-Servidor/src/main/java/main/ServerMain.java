package main;

import gui.ServerGui;

import javax.swing.*;

public class ServerMain {

    public static void main(String[] args) {

        String portaTexto =
                JOptionPane.showInputDialog(
                        "Digite a porta do servidor:"
                );

        if (portaTexto == null) {
            return;
        }

        int porta =
                Integer.parseInt(portaTexto);

        ServerGui gui =
                new ServerGui();

        gui.iniciarServidor(porta);
    }
}