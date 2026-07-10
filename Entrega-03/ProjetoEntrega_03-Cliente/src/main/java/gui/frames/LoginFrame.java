/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.frames;

/**
 *
 * @author kaueq
 */

import model.Response;
import network.ClientConnection;
import service.ClientService;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private ClientService service;

    private ClientConnection connection;

    private JTextField txtUsuario;

    private JPasswordField txtSenha;

    public LoginFrame(
            ClientService service,
            ClientConnection connection
    ) {

        this.service = service;
        this.connection = connection;

        setTitle("Login");

        setSize(350, 200);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel painelCampos = new JPanel();

        painelCampos.setLayout(
                new GridLayout(2, 2, 5, 5)
        );

        painelCampos.add(
                new JLabel("Usuário:")
        );

        txtUsuario = new JTextField();

        painelCampos.add(txtUsuario);

        painelCampos.add(
                new JLabel("Senha:")
        );

        txtSenha = new JPasswordField();

        painelCampos.add(txtSenha);

        JPanel painelBotoes = new JPanel();

        JButton btnLogin =
                new JButton("Login");

        JButton btnCadastrar =
                new JButton("Cadastrar");

        painelBotoes.add(btnLogin);

        painelBotoes.add(btnCadastrar);

        btnLogin.addActionListener(
                e -> fazerLogin()
        );

        btnCadastrar.addActionListener(
                e -> abrirCadastro()
        );

        add(
                painelCampos,
                BorderLayout.CENTER
        );

        add(
                painelBotoes,
                BorderLayout.SOUTH
        );

        setVisible(true);
    }

    private void fazerLogin() {

        try {

            String usuario =
                    txtUsuario.getText();

            String senha =
                    new String(
                            txtSenha.getPassword()
                    );

            Response res =
                    service.login(
                            usuario,
                            senha
                    );

            if ("200".equals(res.resposta)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Login realizado com sucesso."
                );

                new ChatFrame(
                        service,
                        connection
                );

                dispose();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        res.mensagem
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    private void abrirCadastro() {

        new CadastroFrame(service);
    }
}
