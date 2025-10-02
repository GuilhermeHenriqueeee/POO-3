package gui;

import javax.swing.*;
import main.EscolherForma;

public class TelaCadastroParticipante extends JFrame {
    private JTextField txtNome, txtLogin;
    private JPasswordField txtSenha;
    private JButton btnCadastrar, btnCancelar;

    public TelaCadastroParticipante() {
        setTitle("Cadastro Participante");
        setSize(300,250);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(20,20,80,25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(100,20,160,25);
        add(txtNome);

        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setBounds(20,60,80,25);
        add(lblLogin);

        txtLogin = new JTextField();
        txtLogin.setBounds(100,60,160,25);
        add(txtLogin);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(20,100,80,25);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(100,100,160,25);
        add(txtSenha);

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(40,150,100,30);
        add(btnCadastrar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(160,150,100,30);
        add(btnCancelar);

        btnCadastrar.addActionListener(e -> 
            JOptionPane.showMessageDialog(this, "teste1")
        );

        btnCancelar.addActionListener(e -> {
            EscolherForma escolherForma = new EscolherForma();
            escolherForma.setVisible(true);
            this.dispose();
        });
    }

}
