/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gui.panels;

/**
 *
 * @author kaueq
 */
import service.ClientService;
import model.Response;
import gui.listeners.MessageListener;
import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel implements MessageListener {

    private ClientService service;

    // lista visual
    private DefaultListModel<String> modeloUsuarios;

    private JList<String> listaUsuarios;

    // área do chat
    private JTextArea areaMensagens;

    // destinatário
    private JTextField campoDestinatario;

    // mensagem
    private JTextField campoMensagem;

    private JButton btnEnviar;

    private JButton btnBroadcast;

    public ChatPanel(ClientService service) {

        this.service = service;

        setLayout(new BorderLayout());

        // -------------------------
        // LISTA DE USUÁRIOS
        // -------------------------
        modeloUsuarios
                = new DefaultListModel<>();

        listaUsuarios
                = new JList<>(modeloUsuarios);

        JScrollPane scrollUsuarios
                = new JScrollPane(listaUsuarios);

        scrollUsuarios.setPreferredSize(
                new Dimension(180, 0)
        );

        // clique preenche destinatário
        listaUsuarios.addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {

                String usuario
                        = listaUsuarios.getSelectedValue();

                if (usuario != null) {

                    campoDestinatario.setText(
                            usuario
                    );
                }
            }
        });

        // -------------------------
        // ÁREA DE MENSAGENS
        // -------------------------
        areaMensagens
                = new JTextArea();

        areaMensagens.setEditable(false);

        JScrollPane scrollMensagens
                = new JScrollPane(areaMensagens);

        // -------------------------
        // DESTINATÁRIO
        // -------------------------
        campoDestinatario
                = new JTextField();

        JPanel painelDestino
                = new JPanel(
                        new BorderLayout()
                );

        painelDestino.add(
                new JLabel("Destinatário: "),
                BorderLayout.WEST
        );

        painelDestino.add(
                campoDestinatario,
                BorderLayout.CENTER
        );

        // -------------------------
        // MENSAGEM
        // -------------------------
        campoMensagem
                = new JTextField();

        btnEnviar
                = new JButton("Enviar");

        btnBroadcast
                = new JButton("Broadcast");

        btnEnviar.addActionListener(
                e -> enviarMensagem(false)
        );

        btnBroadcast.addActionListener(
                e -> enviarMensagem(true)
        );

        JPanel botoes
                = new JPanel();

        botoes.add(btnEnviar);

        botoes.add(btnBroadcast);

        JPanel painelMensagem
                = new JPanel(
                        new BorderLayout()
                );

        painelMensagem.add(
                campoMensagem,
                BorderLayout.CENTER
        );

        painelMensagem.add(
                botoes,
                BorderLayout.EAST
        );

        JPanel inferior
                = new JPanel(
                        new BorderLayout()
                );

        inferior.add(
                painelDestino,
                BorderLayout.NORTH
        );

        inferior.add(
                painelMensagem,
                BorderLayout.SOUTH
        );

        JPanel centro
                = new JPanel(
                        new BorderLayout()
                );

        centro.add(
                scrollMensagens,
                BorderLayout.CENTER
        );

        centro.add(
                inferior,
                BorderLayout.SOUTH
        );

        add(
                scrollUsuarios,
                BorderLayout.WEST
        );

        add(
                centro,
                BorderLayout.CENTER
        );

        atualizarUsuarios();
    }

    private void atualizarUsuarios() {

        try {

            Response res
                    = service.listarUsuariosLogados();

            if (res.usuariosLogados != null) {

                modeloUsuarios.clear();

                for (String usuario
                        : res.usuariosLogados) {

                    modeloUsuarios.addElement(
                            usuario
                    );
                }
            }

        } catch (Exception e) {

            // ignora
        }
    }

    private void enviarMensagem(
            boolean broadcast
    ) {

        try {

            String mensagem
                    = campoMensagem.getText();

            if (mensagem.isBlank()) {
                return;
            }

            String destinatario;

            if (broadcast) {

                destinatario = "/todos";

            } else {

                destinatario
                        = campoDestinatario.getText();

                if (destinatario.isBlank()) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Informe o destinatário."
                    );

                    return;
                }
            }

            Response res
                    = service.enviarMensagem(
                            destinatario,
                            mensagem
                    );

            if ("200".equals(
                    res.resposta
            )) {

                if (broadcast) {

                    areaMensagens.append(
                            "[Broadcast] "
                            + mensagem
                            + "\n"
                    );

                } else {

                    areaMensagens.append(
                            "Você → "
                            + destinatario
                            + ": "
                            + mensagem
                            + "\n"
                    );
                }

                campoMensagem.setText("");

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

    public void receberMensagem(
            String remetente,
            String mensagem
    ) {

        SwingUtilities.invokeLater(() -> {

            areaMensagens.append(
                    remetente
                    + ": "
                    + mensagem
                    + "\n"
            );
        });
    }

    @Override
    public void novaMensagem(
            String remetente,
            String mensagem
    ) {

        SwingUtilities.invokeLater(() -> {

            areaMensagens.append(
                    remetente
                    + ": "
                    + mensagem
                    + "\n"
            );
        });
    }

}
