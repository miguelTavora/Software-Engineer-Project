package dominio.entidades;

import java.util.List;

public class RespostasQuestionario {

    private String emailUtl;
    private List<Integer> respostas;

    public RespostasQuestionario(String emailUtl, List<Integer> respostas) {
        this.emailUtl = emailUtl;
        this.respostas = respostas;
    }

    public String getEmailUtl() {
        return emailUtl;
    }

    public List<Integer> getRespostas() {
        return respostas;
    }

    @Override
    public String toString() {
        return "RespostasQuestionario{" +
                "emailUtl='" + emailUtl + '\'' +
                ", respostas=" + respostas +
                '}';
    }
}
