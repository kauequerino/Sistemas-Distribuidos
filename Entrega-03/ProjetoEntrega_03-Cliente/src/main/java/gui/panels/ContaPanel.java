/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.panels;

/**
 *
 * @author kaueq
 */


import model.Response;
import service.ClientService;

import javax.swing.*;
import java.awt.*;

public class ContaPanel extends JPanel {

    private ClientService service;

    private JTextField txtNome;

    private JTextField txtUsuario;

    private JPasswordField txtSenha;

    private JButton btnConsultar;

    private JButton btnAtualizar;

    private JButton btnExcluir;

    private JButton btnLogout;

    public ContaPanel(ClientService service) {

        this.service = service;

        setLayout(new BorderLayout());

        JPanel painel = new JPanel();

        painel.setLayout(
                new GridLayout(4, 2, 5, 5)
        );

        painel.add(
                new JLabel("Nome:")
        );

        txtNome = new JTextField();

        painel.add(txtNome);

        painel.add(
                new JLabel("Usuário:")
        );

        txtUsuario = new JTextField();

        txtUsuario.setEditable(false);

        painel.add(txtUsuario);

        painel.add(
                new JLabel("Nova senha:")
        );

        txtSenha = new JPasswordField();

        painel.add(txtSenha);

        btnConsultar =
                new JButton("Consultar");

        btnAtualizar =
                new JButton("Atualizar");

        btnExcluir =
                new JButton("Excluir Conta");

        btnLogout =
                new JButton("Logout");

        JPanel botoes = new JPanel();

        botoes.add(btnConsultar);
        botoes.add(btnAtualizar);
        botoes.add(btnExcluir);
        botoes.add(btnLogout);

        add(
                painel,
                BorderLayout.CENTER
        );

        add(
                botoes,
                BorderLayout.SOUTH
        );

        btnConsultar.addActionListener(
                e -> consultar()
        );

        btnAtualizar.addActionListener(
                e -> atualizar()
        );

        btnExcluir.addActionListener(
                e -> excluir()
        );

        btnLogout.addActionListener(
                e -> logout()
        );
    }

    private void consultar() {

        try {

            Response res =
                    service.consultar();

            if ("200".equals(res.resposta)) {

                txtNome.setText(
                        res.nome
                );

                txtUsuario.setText(
                        res.usuario
                );

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

    private void atualizar() {

        try {

            String nome =
                    txtNome.getText();

            String senha =
                    new String(
                            txtSenha.getPassword()
                    );

            Response res =
                    service.atualizar(
                            nome,
                            senha
                    );

            JOptionPane.showMessageDialog(
                    this,
                    res.mensagem
            );

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    private void excluir() {

        int opcao =
                JOptionPane.showConfirmDialog(
                        this,
                        "Deseja excluir sua conta?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION
                );

        if (opcao != JOptionPane.YES_OPTION) {
            return;
        }

        try {

            Response res =
                    service.deletar();

            JOptionPane.showMessageDialog(
                    this,
                    res.mensagem
            );

            System.exit(0);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }

    private void logout() {

        try {

            Response res =
                    service.logout();

            JOptionPane.showMessageDialog(
                    this,
                    res.mensagem
            );

            System.exit(0);

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage()
            );
        }
    }
}