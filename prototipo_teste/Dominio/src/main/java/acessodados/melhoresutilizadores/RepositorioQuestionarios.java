package acessodados.melhoresutilizadores;

import dominio.entidades.RespostasQuestionario;

import java.util.ArrayList;
import java.util.List;

public class RepositorioQuestionarios {

    // atributo de teste, deveria estar guardado no Firestore
    private List<RespostasQuestionario> questionarios;
    private int indiceObtido = 0;

    public RepositorioQuestionarios() {
        questionarios = new ArrayList<RespostasQuestionario>();
    }

    public boolean inserir(RespostasQuestionario respostas) {
        boolean existe = false;
        for(RespostasQuestionario questionario : questionarios) {
            if(questionario.getEmailUtl().equals(respostas.getEmailUtl()))
                existe = true;
        }
        if(!existe)
            return questionarios.add(respostas);

        return false;
    }

    public List<RespostasQuestionario> obterNovosQuestionarios() {
        List<RespostasQuestionario> novosQuestionarios = new ArrayList<RespostasQuestionario>();
        int incremento = 0;
        for(int i = indiceObtido; i < questionarios.size(); i++) {
            novosQuestionarios.add(questionarios.get(i));
            incremento++;
        }
        indiceObtido = indiceObtido+incremento;
        return novosQuestionarios;
    }

    public RespostasQuestionario obterQuestionarioUtilizador(String emailUtl) {
        for(RespostasQuestionario questionario : questionarios) {
            if(questionario.getEmailUtl().equals(emailUtl)) return questionario;
        }
        return null;
    }

    @Override
    public String toString() {
        String questionariosNome = "\n";
        for(int i = 0; i < questionarios.size(); i++) {
            questionariosNome = questionariosNome+questionarios.get(i)+",\n";
        }

        return "RepositorioQuestionarios{" +
                "questionarios=" + questionariosNome +
                "}\n";
    }
}
