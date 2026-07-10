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
import service.ClientService;

import javax.swing.*;
import java.awt.*;

public class CadastroFrame extends JFrame {

    private ClientService service;

    private JTextField txtNome;

    private JTextField txtUsuario;

    private JPasswordField txtSenha;

    public CadastroFrame(ClientService service) {

        this.service = service;

        setTitle("Cadastro de Usuário");

        setSize(400, 220);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        JPanel painelCampos = new JPanel();

        painelCampos.setLayout(
                new GridLayout(3, 2, 5, 5)
        );

        painelCampos.add(
                new JLabel("Nome:")
        );

        txtNome = new JTextField();

        painelCampos.add(txtNome);

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

        JButton btnCadastrar =
                new JButton("Cadastrar");

        JButton btnCancelar =
                new JButton("Cancelar");

        painelBotoes.add(btnCadastrar);

        painelBotoes.add(btnCancelar);

        btnCadastrar.addActionListener(
                e -> cadastrar()
        );

        btnCancelar.addActionListener(
                e -> dispose()
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

    private void cadastrar() {

        try {

            String nome =
                    txtNome.getText();

            String usuario =
                    txtUsuario.getText();

            String senha =
                    new String(
                            txtSenha.getPassword()
                    );

            Response res =
                    service.cadastrar(
                            nome,
                            usuario,
                            senha
                    );

            if ("200".equals(res.resposta)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Usuário cadastrado com sucesso."
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
}
