package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Palestra;
import model.Palestrante;
import repository.PalestraRepository;
import repository.PalestranteRepository;

public class TelaManterPalestras extends JFrame {
    private JButton btnListar, btnBuscar, btnCadastrar, btnSelecionar, btnVoltar;
    private PalestraRepository repo;
    private PalestranteRepository repoPalestrante;

    public TelaManterPalestras() {
        setTitle("Manter Palestras");
        setSize(400, 350);
        setLayout(new GridLayout(6, 1, 10, 10));
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

        repo = new PalestraRepository();
        repoPalestrante = new PalestranteRepository();

        btnListar.addActionListener(e -> {
            List<Palestra> palestras = repo.listarTodas();
            StringBuilder sb = new StringBuilder();
            for (Palestra p : palestras) {
                sb.append("ID: ").append(p.getId())
                  .append(" - Nome: ").append(p.getNome())
                  .append(" - Evento: ").append(p.getEvento())
                  .append(" - Data: ").append(p.getData())
                  .append(" - Participantes: ").append(p.getParticipantes().size())
                  .append("\n");
            }
            JOptionPane.showMessageDialog(this, sb.length() == 0 ? "Nenhuma palestra encontrada" : sb.toString());
        });

        btnCadastrar.addActionListener(e -> {
            String nome = JOptionPane.showInputDialog(this, "Digite o nome da palestra:");
            String evento = JOptionPane.showInputDialog(this, "Digite o nome do evento:");
            String data = JOptionPane.showInputDialog(this, "Digite a data (dd/MM/yyyy):");

            if (nome != null && !nome.isBlank()) {
                Palestra p = new Palestra();
                p.setNome(nome);
                p.setEvento(evento);
                p.setData(data);
                repo.cadastrar(p);
                JOptionPane.showMessageDialog(this, "Palestra cadastrada com sucesso!");
            }
        });

        btnBuscar.addActionListener(e -> {
            String[] opcoes = {"ID", "Nome", "Evento", "Data"};
            int escolha = JOptionPane.showOptionDialog(
                    this,
                    "Buscar por:",
                    "Buscar Palestra",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opcoes,
                    opcoes[0]
            );

            List<Palestra> resultados = null;

            switch (escolha) {
                case 0: // ID
                    try {
                        String idStr = JOptionPane.showInputDialog(this, "Digite o ID:");
                        Long id = Long.parseLong(idStr);
                        Palestra p = repo.buscarPorIdComParticipantes(id);
                        resultados = p != null ? List.of(p) : List.of();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "ID inválido!");
                    }
                    break;
                case 1: // Nome
                    String nome = JOptionPane.showInputDialog(this, "Digite o nome da palestra:");
                    resultados = repo.buscarPorNome(nome);
                    break;
                case 2: // Evento
                    String evento = JOptionPane.showInputDialog(this, "Digite o nome do evento:");
                    resultados = repo.buscarPorEvento(evento);
                    break;
                case 3: // Data
                    String data = JOptionPane.showInputDialog(this, "Digite a data (dd/MM/yyyy):");
                    resultados = repo.buscarPorData(data);
                    break;
            }

            if (resultados != null && !resultados.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (Palestra p : resultados) {
                    sb.append("ID: ").append(p.getId())
                      .append(" - Nome: ").append(p.getNome())
                      .append(" - Evento: ").append(p.getEvento())
                      .append(" - Data: ").append(p.getData())
                      .append(" - Participantes: ").append(p.getParticipantes() != null ? p.getParticipantes().size() : 0)
                      .append("\n");
                }
                JOptionPane.showMessageDialog(this, sb.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Nenhuma palestra encontrada");
            }
        });

        btnSelecionar.addActionListener(e -> {
            List<Palestra> palestras = repo.listarTodas();
            if (palestras.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma palestra cadastrada!");
                return;
            }

            StringBuilder sb = new StringBuilder();
            for (Palestra p : palestras) {
                sb.append("ID: ").append(p.getId())
                  .append(" - Nome: ").append(p.getNome())
                  .append("\n");
            }

            String idStr = JOptionPane.showInputDialog(this, "Selecione o ID da palestra:\n" + sb);
            try {
                Long id = Long.parseLong(idStr);
                // Buscamos com participantes para garantir que a lista está carregada (mesmo que não a usemos para desvincular)
                Palestra p = repo.buscarPorIdComParticipantes(id); 
                if (p != null) {
                    
                    // Array de Opções ATUALIZADO
                    String palestranteAtual = (p.getPalestrante() != null) ? p.getPalestrante().getNome() : "Nenhum";
                    
                    String[] opcoes = {"Editar Nome", "Editar Evento/Data", "Vincular Palestrante", "Desvincular Palestrante", "Visualizar Participantes", "Excluir", "Cancelar"};
                    
                    int escolha = JOptionPane.showOptionDialog(
                                this,
                                "ID: " + p.getId() + "\nNome: " + p.getNome() + "\nPalestrante: " + palestranteAtual,
                                "Detalhes da Palestra",
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
                        case 1: // Editar Evento/Data 
                            String novoEvento = JOptionPane.showInputDialog(this, "Digite o novo evento:", p.getEvento());
                            String novaData = JOptionPane.showInputDialog(this, "Digite a nova data (dd/MM/yyyy):", p.getData());
                            if (novoEvento != null && novaData != null) {
                                p.setEvento(novoEvento);
                                p.setData(novaData);
                                repo.atualizar(p);
                                JOptionPane.showMessageDialog(this, "Evento/Data atualizados!");
                            }
                            break;
                        case 2: // Vincular Palestrante 
                            List<Palestrante> palestrantes = repoPalestrante.listarTodos();
                            if (palestrantes.isEmpty()) {
                                JOptionPane.showMessageDialog(this, "Nenhum palestrante cadastrado!");
                                break;
                            }
                            StringBuilder sbP = new StringBuilder();
                            for (Palestrante pl : palestrantes) {
                                sbP.append("ID: ").append(pl.getId()).append(" - Nome: ").append(pl.getNome()).append("\n");
                            }
                            String idPStr = JOptionPane.showInputDialog(this, "Escolha o ID do palestrante:\n" + sbP);
                            try {
                                Long idP = Long.parseLong(idPStr);
                                Palestrante pl = repoPalestrante.buscarPorId(idP);
                                if (pl != null) {
                                    p.setPalestrante(pl);
                                    repo.atualizar(p);
                                    JOptionPane.showMessageDialog(this, "Palestrante vinculado!");
                                } else {
                                    JOptionPane.showMessageDialog(this, "ID inválido!");
                                }
                            } catch (NumberFormatException ex) {
                                JOptionPane.showMessageDialog(this, "ID inválido!");
                            }
                            break;
                        
                        case 3: // Desvincular Palestrante
                            if (p.getPalestrante() != null) {
                                int confirm = JOptionPane.showConfirmDialog(this,
                                        "Deseja realmente desvincular o palestrante " + p.getPalestrante().getNome() + " desta palestra?",
                                        "Confirmar Desvinculação",
                                        JOptionPane.YES_NO_OPTION);

                                if (confirm == JOptionPane.YES_OPTION) {
                                    repo.desvincularPalestrante(p);
                                    // p.setPalestrante(null); // Atualiza o objeto em memória (já feito implicitamente pelo método, mas é bom para coerência local)
                                    JOptionPane.showMessageDialog(this, "Palestrante desvinculado com sucesso!");
                                }
                            } else {
                                JOptionPane.showMessageDialog(this, "Esta palestra já está sem palestrante vinculado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                            }
                            break;

                        case 4: // Visualizar Participantes 
                            if (p.getParticipantes() != null && !p.getParticipantes().isEmpty()) {
                                StringBuilder psb = new StringBuilder();
                                p.getParticipantes().forEach(part -> psb.append(part.getNome()).append("\n"));
                                JOptionPane.showMessageDialog(this, psb.toString());
                            } else {
                                JOptionPane.showMessageDialog(this, "Nenhum participante inscrito!");
                            }
                            break;
                        case 5: // Excluir 
                            if ((p.getParticipantes() != null && !p.getParticipantes().isEmpty()) || p.getPalestrante() != null) {
                                JOptionPane.showMessageDialog(this, "Não é possível excluir, há palestrante ou participantes vinculados!");
                            } else {
                                repo.remover(p);
                                JOptionPane.showMessageDialog(this, "Palestra removida!");
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