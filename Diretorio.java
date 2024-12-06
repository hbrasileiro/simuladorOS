import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Diretorio implements Serializable {
    private static final long serialVersionUID = 1L; // Versão da serialização
    private String nome;
    private List<Arquivo> arquivos; // Lista serializável

    public Diretorio(String nome) {
        this.nome = nome;
        this.arquivos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Arquivo> getArquivos() {
        return arquivos;
    }

    public void adicionarArquivo(Arquivo arquivo) {
        this.arquivos.add(arquivo);
    }

    public void removerArquivo(String nomeArquivo) {
        this.arquivos.removeIf(arquivo -> arquivo.getNome().equals(nomeArquivo));
    }

    @Override
    public String toString() {
        return nome;
    }
}
