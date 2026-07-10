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
import java.util.List;

public class ServerFrame extends JFrame {

    private DefaultListModel<String> modeloUsuarios;

    private JList<String> listaUsuarios;

    private JTextArea areaLog;

    public ServerFrame() {

        setTitle("Servidor de Chat");

        setSize(800, 500);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        modeloUsuarios =
                new DefaultListModel<>();

        listaUsuarios =
                new JList<>(modeloUsuarios);

        JScrollPane scrollUsuarios =
                new JScrollPane(listaUsuarios);

        scrollUsuarios.setPreferredSize(
                new Dimension(200, 0)
        );

        areaLog =
                new JTextArea();

        areaLog.setEditable(false);

        JScrollPane scrollLog =
                new JScrollPane(areaLog);

        JSplitPane split =
                new JSplitPane(
                        JSplitPane.HORIZONTAL_SPLIT,
                        scrollUsuarios,
                        scrollLog
                );

        split.setDividerLocation(200);

        add(split);

        setVisible(true);
    }

    public void adicionarLog(
            String texto
    ) {

        SwingUtilities.invokeLater(() -> {

            areaLog.append(
                    texto + "\n"
            );
        });
    }

    public void atualizarUsuarios(
            List<String> usuarios
    ) {

        SwingUtilities.invokeLater(() -> {

            modeloUsuarios.clear();

            for (String usuario : usuarios) {

                modeloUsuarios.addElement(
                        usuario
                );
            }
        });
    }
}
