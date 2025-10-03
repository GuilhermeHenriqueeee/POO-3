package gui;

import javax.swing.*;
import java.awt.*;
import model.Administrador;
import repository.AdministradorRepository;
import main.EscolherEntrada;

public class TelaLoginAdministrador extends JFrame {
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnLogin, btnCancelar;

    public TelaLoginAdministrador() {
        setTitle("Login - Administrador");
        setSize(300, 220);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setBounds(20, 20, 80, 25);
        add(lblLogin);

        txtLogin = new JTextField();
        txtLogin.setBounds(100, 20, 160, 25);
        add(txtLogin);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(20, 60, 80, 25);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(100, 60, 160, 25);
        add(txtSenha);

        JPanel panelBotoes = new JPanel();
        panelBotoes.setBounds(40, 110, 220, 40);
        panelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));

        btnLogin = new JButton("Login");
        btnCancelar = new JButton("Cancelar");

        panelBotoes.add(btnLogin);
        panelBotoes.add(btnCancelar);
        add(panelBotoes);

        btnCancelar.addActionListener(e -> {
            EscolherEntrada escolherEntrada = new EscolherEntrada();
            escolherEntrada.setVisible(true);
            dispose();
        });

        btnLogin.addActionListener(e -> {
            String login = txtLogin.getText();
            String senha = new String(txtSenha.getPassword());

            AdministradorRepository repo = new AdministradorRepository();
            Administrador admin = repo.buscarPorLogin(login);

            if (admin != null && admin.getSenha().equals(senha)) {
                JOptionPane.showMessageDialog(null, "Login de administrador realizado com sucesso!");
                dispose();
                new TelaAdministrador().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Login ou senha incorretos!");
            }
        });
    }
}
