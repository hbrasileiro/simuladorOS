import java.io.Serializable;

public class Arquivo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;

    public Arquivo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }
}
