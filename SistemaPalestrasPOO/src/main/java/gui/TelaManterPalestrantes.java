package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Palestrante;
import repository.PalestranteRepository;

public class TelaManterPalestrantes extends JFrame {
    private JButton btnListar, btnBuscar, btnCadastrar, btnSelecionar, btnVoltar;
    private PalestranteRepository repo;

    public TelaManterPalestrantes() {
        setTitle("Manter Palestrantes");
        setSize(400, 350);
        setLayout(new GridLayout(6, 1, 10, 10));
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

        repo = new PalestranteRepository();

        btnListar.addActionListener(e -> {
            List<Palestrante> palestrantes = repo.listarTodosComPalestras();
            StringBuilder sb = new StringBuilder();
            for (Palestrante p : palestrantes) {
                sb.append("ID: ").append(p.getId())
                  .append(" - Nome: ").append(p.getNome()).append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.length() == 0 ? "Nenhum palestrante encontrado" : sb.toString());
        });

        btnCadastrar.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Digite o nome do palestrante:");
            if (nome != null && !nome.isBlank()) {
                repo.cadastrar(new Palestrante(nome));
                JOptionPane.showMessageDialog(this, "Palestrante cadastrado com sucesso!");
            }
        });

        btnBuscar.addActionListener(e -> {
            String[] opcoes = {"ID", "Nome"};
            int escolha = JOptionPane.showOptionDialog(
                    this,
                    "Buscar por:",
                    "Buscar Palestrante",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            if (escolha == 0) { // ID
                String idStr = JOptionPane.showInputDialog(this, "Digite o ID do palestrante:");
                try {
                    Long id = Long.parseLong(idStr);
                    Palestrante p = repo.buscarPorIdComPalestras(id);
                    if (p != null) {
                        JOptionPane.showMessageDialog(this, "ID: " + p.getId() + "\nNome: " + p.getNome());
                    } else {
                        JOptionPane.showMessageDialog(this, "Nenhum palestrante encontrado com esse ID");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID inválido!");
                }
            } else if (escolha == 1) { 
                String nome = JOptionPane.showInputDialog(this, "Digite o nome do palestrante:");
                List<Palestrante> resultados = repo.buscarPorNome(nome);
                if (resultados.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nenhum palestrante encontrado com esse nome");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Palestrante p : resultados) {
                        sb.append("ID: ").append(p.getId())
                          .append(" - Nome: ").append(p.getNome()).append("\n");
                    }
                    JOptionPane.showMessageDialog(this, sb.toString());
                }
            }
        });

        btnSelecionar.addActionListener(e -> {
            List<Palestrante> palestrantes = repo.listarTodosComPalestras();
            if (palestrantes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhum palestrante cadastrado!");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (Palestrante p : palestrantes) {
                sb.append("ID: ").append(p.getId())
                  .append(" - Nome: ").append(p.getNome()).append("\n");
            }
            String idStr = JOptionPane.showInputDialog(this, "Selecione o ID do palestrante:\n" + sb);
            try {
                Long id = Long.parseLong(idStr);
                Palestrante p = repo.buscarPorIdComPalestras(id);
                if (p != null) {
                    // Tela de detalhes
                    String[] opcoes = {"Editar Nome", "Visualizar Palestras", "Excluir", "Cancelar"};
                    int escolha = JOptionPane.showOptionDialog(
                            this,
                            "ID: " + p.getId() + "\nNome: " + p.getNome(),
                            "Detalhes do Palestrante",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            opcoes,
                            opcoes[0]
                    );

                    switch (escolha) {
                        case 0: // Editar Nome
                            String novoNome = JOptionPane.showInputDialog(this, "Digite o novo nome:", p.getNome());
                            if (novoNome != null && !novoNome.isBlank()) {
                                p.setNome(novoNome);
                                repo.atualizar(p);
                                JOptionPane.showMessageDialog(this, "Nome atualizado!");
                            }
                            break;

                        case 1: // Visualizar Palestras
                            StringBuilder psb = new StringBuilder();
                            if (p.getPalestras() != null && !p.getPalestras().isEmpty()) {
                                p.getPalestras().forEach(pa -> psb.append(pa.getNome())
                                        .append(" - ").append(pa.getData()).append("\n"));
                            }
                            JOptionPane.showMessageDialog(this, psb.length() == 0 ? "Sem palestras" : psb.toString());
                            break;

                        case 2: // Excluir
                            if (p.getPalestras() != null && !p.getPalestras().isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Não é possível excluir, existem palestras vinculadas!");
                            } else {
                                repo.remover(p);
                                JOptionPane.showMessageDialog(this, "Palestrante removido!");
                            }
                            break;

                        default:
                            break;
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "ID inválido!");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID inválido!");
            }
        });

        btnVoltar.addActionListener(e -> dispose());
    }
}
//