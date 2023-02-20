package dominio.recomendacao;

public class Recomendacao {

    private String emailUtl1;
    private String emailUtl2;
    private int prioridade;

    public Recomendacao(String emailUtl1, String emailUtl2, int prioridade) {
        this.emailUtl1 = emailUtl1;
        this.emailUtl2 = emailUtl2;
        this.prioridade = prioridade;
    }

    public String getEmailUtl1() {
        return emailUtl1;
    }

    public String getEmailUtl2() {
        return emailUtl2;
    }

    public int getPrioridade() {
        return prioridade;
    }

    @Override
    public String toString() {
        return "Recomendacao{" +
                "emailUtl1='" + emailUtl1 + '\'' +
                ", emailUtl2='" + emailUtl2 + '\'' +
                ", prioridade=" + prioridade +
                '}';
    }
}
