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
import model.UsuarioLista;
import service.ClientService;

import javax.swing.*;
import java.awt.*;

public class AdminPanel extends JPanel {

    private ClientService service;

    private JTextArea areaUsuarios;

    private JTextField txtUsuario;

    private JTextField txtNome;

    private JTextField txtSenha;

    public AdminPanel(ClientService service) {

        this.service = service;

        setLayout(new BorderLayout());

        areaUsuarios =
                new JTextArea();

        areaUsuarios.setEditable(false);

        JScrollPane scroll =
                new JScrollPane(areaUsuarios);

        add(
                scroll,
                BorderLayout.CENTER
        );

        JPanel painel = new JPanel();

        painel.setLayout(
                new GridLayout(3, 2)
        );

        painel.add(
                new JLabel("Usuário:")
        );

        txtUsuario =
                new JTextField();

        painel.add(txtUsuario);

        painel.add(
                new JLabel("Nome:")
        );

        txtNome =
                new JTextField();

        painel.add(txtNome);

        painel.add(
                new JLabel("Senha:")
        );

        txtSenha =
                new JTextField();

        painel.add(txtSenha);

        add(
                painel,
                BorderLayout.NORTH
        );

        JPanel botoes = new JPanel();

        JButton btnListar =
                new JButton("Listar");

        JButton btnAtualizar =
                new JButton("Atualizar");

        JButton btnExcluir =
                new JButton("Excluir");

        botoes.add(btnListar);
        botoes.add(btnAtualizar);
        botoes.add(btnExcluir);

        add(
                botoes,
                BorderLayout.SOUTH
        );

        btnListar.addActionListener(
                e -> listar()
        );

        btnAtualizar.addActionListener(
                e -> atualizar()
        );

        btnExcluir.addActionListener(
                e -> excluir()
        );
    }

    private void listar() {

        try {

            Response res =
                    service.consultarUsuariosAdmin();

            areaUsuarios.setText("");

            if (res.lista_usuarios != null) {

                for (UsuarioLista u
                        : res.lista_usuarios) {

                    areaUsuarios.append(
                            "Usuário: "
                            + u.usuario
                            + "\n"
                    );

                    areaUsuarios.append(
                            "Nome: "
                            + u.nome
                            + "\n\n"
                    );
                }
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

            Response res =
                    service.atualizarUsuarioAdmin(
                            txtUsuario.getText(),
                            txtNome.getText(),
                            txtSenha.getText()
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

        try {

            Response res =
                    service.deletarUsuarioAdmin(
                            txtUsuario.getText()
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
}
