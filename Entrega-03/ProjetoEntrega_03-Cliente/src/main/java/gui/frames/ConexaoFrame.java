/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.frames;

/**
 *
 * @author kaueq
 */

import network.ClientConnection;
import service.ClientService;

import javax.swing.*;
import java.awt.*;

public class ConexaoFrame extends JFrame {

    private JTextField txtIp;
    private JTextField txtPorta;

    public ConexaoFrame() {

        setTitle("Conectar ao Servidor");

        setSize(350, 180);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel painelCampos = new JPanel();

        painelCampos.setLayout(
                new GridLayout(2, 2, 5, 5)
        );

        painelCampos.add(
                new JLabel("IP:")
        );

        txtIp = new JTextField();

        painelCampos.add(txtIp);

        painelCampos.add(
                new JLabel("Porta:")
        );

        txtPorta = new JTextField();

        painelCampos.add(txtPorta);

        JButton btnConectar =
                new JButton("Conectar");

        btnConectar.addActionListener(
                e -> conectar()
        );

        add(
                painelCampos,
                BorderLayout.CENTER
        );

        add(
                btnConectar,
                BorderLayout.SOUTH
        );

        setVisible(true);
    }

    private void conectar() {

        try {

            String ip =
                    txtIp.getText();

            int porta =
                    Integer.parseInt(
                            txtPorta.getText()
                    );

            ClientConnection connection =
                    new ClientConnection(
                            ip,
                            porta
                    );

            ClientService service =
                    new ClientService(
                            connection
                    );

            JOptionPane.showMessageDialog(
                    this,
                    "Conectado ao servidor."
            );

            new LoginFrame(
                    service,
                    connection
            );

            dispose();

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao conectar:\n"
                    + e.getMessage()
            );
        }
    }
}