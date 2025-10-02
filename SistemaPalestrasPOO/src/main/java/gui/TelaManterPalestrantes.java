package gui;

import javax.swing.*;
import java.awt.*;

public class TelaManterPalestrantes extends JFrame {
    private JButton btnListar, btnBuscar, btnCadastrar, btnSelecionar, btnVoltar;

    public TelaManterPalestrantes() {
        setTitle("Manter Palestrantes");
        setSize(400,300);
        setLayout(new GridLayout(6,1,10,10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JLabel lblTitulo = new JLabel("Gerenciamento de Palestrantes", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo);

        btnListar = new JButton("Listar todos");
        btnBuscar = new JButton("Buscar por ID/Nome");
        btnCadastrar = new JButton("Cadastrar novo");
        btnSelecionar = new JButton("Selecionar palestrante");
        btnVoltar = new JButton("Voltar");

        add(btnListar);
        add(btnBuscar);
        add(btnCadastrar);
        add(btnSelecionar);
        add(btnVoltar);

        btnListar.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "teste1")
        );

        btnBuscar.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "teste2")
        );

        btnCadastrar.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "teste3")
        );

        btnSelecionar.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "teste4")
        );

        btnVoltar.addActionListener(e -> dispose());
    }
}
