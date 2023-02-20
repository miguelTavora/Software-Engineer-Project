package dominio.entidades;

public class Anuncio {

    // classe de exemplo, visto que não é bem conhecido o conteudo interno
    // de um anuncio

    private String exemplo;

    public Anuncio(String exemplo) {
        this.exemplo = exemplo;
    }

    public String getExemplo() {
        return exemplo;
    }

    @Override
    public String toString() {
        return "Anuncio{" +
                "exemplo='" + exemplo + '\'' +
                '}';
    }
}
