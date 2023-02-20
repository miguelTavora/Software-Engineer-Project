package dominio.entidades;

public class LocalizacaoUtilizador {

    private String nome;
    private String email;
    private String localizacao;

    public LocalizacaoUtilizador(String nome, String email, String localizacao) {
        this.nome = nome;
        this.email = email;
        this.localizacao = localizacao;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    @Override
    public String toString() {
        return "LocalizacaoUtilizador{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", localizacao='" + localizacao + '\'' +
                '}';
    }
}
