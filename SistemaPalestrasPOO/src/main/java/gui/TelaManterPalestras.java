package gui;

import javax.swing.*;
import java.awt.*;

public class TelaManterPalestras extends JFrame {
    private JButton btnListar, btnBuscar, btnCadastrar, btnSelecionar, btnVoltar;

    public TelaManterPalestras() {
        setTitle("Manter Palestras");
        setSize(400,300);
        setLayout(new GridLayout(6,1,10,10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JLabel lblTitulo = new JLabel("Gerenciamento de Palestras", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo);

        btnListar = new JButton("Listar todas");
        btnBuscar = new JButton("Buscar (ID, Palestrante, Evento, Data)");
        btnCadastrar = new JButton("Cadastrar nova");
        btnSelecionar = new JButton("Selecionar palestra");
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
