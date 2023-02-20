package dominio.entidades;

public class Classificacao {

    private String emailUtl;
    private String emailUtlCorr;
    private int classificao;

    public Classificacao(String emailUtl, String emailUtlCorr, int classificao) {
        this.emailUtl = emailUtl;
        this.emailUtlCorr = emailUtlCorr;
        this.classificao = classificao;
    }

    public String getEmailUtl() {
        return emailUtl;
    }

    public String getEmailUtlCorr() {
        return emailUtlCorr;
    }

    public int getClassificao() {
        return classificao;
    }

    @Override
    public String toString() {
        return "Classificacao{" +
                "emailUtl='" + emailUtl + '\'' +
                ", emailUtlCorr='" + emailUtlCorr + '\'' +
                ", classificao=" + classificao +
                '}';
    }
}
