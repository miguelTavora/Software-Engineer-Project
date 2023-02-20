package dominio.entidades;

public class Selecao {

    private boolean gosto;
    private String emailUtilizador;
    private String emailPerfilApresentado;

    public Selecao(boolean gosto, String emailUtilizador, String emailPerfilApresentado) {
        this.gosto = gosto;
        this.emailUtilizador = emailUtilizador;
        this.emailPerfilApresentado = emailPerfilApresentado;
    }

    public boolean getGosto() {
        return gosto;
    }

    public String getEmailUtilizador() {
        return emailUtilizador;
    }

    public String getEmailPerfilApresentado() {
        return emailPerfilApresentado;
    }

    @Override
    public String toString() {
        return "Selecao{" +
                "gosto=" + gosto +
                ", emailUtilizador='" + emailUtilizador + '\'' +
                ", emailPerfilApresentado='" + emailPerfilApresentado + '\'' +
                '}';
    }
}
