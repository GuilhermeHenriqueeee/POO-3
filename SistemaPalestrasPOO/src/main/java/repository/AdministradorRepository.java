package repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Administrador;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdministradorRepository {

    private final String arquivo = "src/main/resources/administradores.json";
    private List<Administrador> administradores = new ArrayList<>();
    private Gson gson = new Gson();

    public AdministradorRepository() {
        carregar();
    }

    public void salvar(Administrador admin) {
        administradores.add(admin);
        salvarNoArquivo();
    }

    public List<Administrador> listar() {
        return administradores;
    }

    public Administrador buscarPorLogin(String login) {
        return administradores.stream()
                .filter(a -> a.getLogin().equals(login))
                .findFirst()
                .orElse(null);
    }

    private void carregar() {
        File file = new File(arquivo);
        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Type listType = new TypeToken<List<Administrador>>() {}.getType();
                administradores = gson.fromJson(reader, listType);
                if (administradores == null) {
                    administradores = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void salvarNoArquivo() {
        try (FileWriter writer = new FileWriter(arquivo)) {
            gson.toJson(administradores, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
