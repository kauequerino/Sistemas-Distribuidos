/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui;

/**
 *
 * @author kaueq
 */
import javax.swing.*;
import java.awt.*;

public class ServerStartFrame extends JFrame {

    private JTextField txtPorta;

    public ServerStartFrame() {

        setTitle("Servidor");

        setSize(300, 150);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel painel = new JPanel(
                new GridLayout(1, 2, 5, 5)
        );

        painel.add(
                new JLabel("Porta:")
        );

        txtPorta = new JTextField();

        painel.add(txtPorta);

        JButton btnIniciar
                = new JButton("Iniciar Servidor");

        btnIniciar.addActionListener(
                e -> iniciarServidor()
        );

        add(
                painel,
                BorderLayout.CENTER
        );

        add(
                btnIniciar,
                BorderLayout.SOUTH
        );

        setVisible(true);
    }

    private void iniciarServidor() {

        try {

            int porta
                    = Integer.parseInt(
                            txtPorta.getText()
                    );

            ServerGui gui = new ServerGui();
            gui.iniciarServidor(porta);

            /*
         * Aqui depois vamos iniciar o servidor.
             */
            dispose();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Porta inválida."
            );
        }
    }
}
