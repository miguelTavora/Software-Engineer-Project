package acessodados.melhoresutilizadores;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import dominio.entidades.RespostasQuestionario;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RepositorioQuestionarios {

    // o nome do documento é o email do utilizador
    private final String NOME_COLECAO = "Questionarios";
    private final String CAMPO_EMAIL = "emailUtl";
    private final String CAMPO_RESPOSTAS_QUESTIONARIO = "respostas";

    // guarda os novos questionarios guardados no firestore
    private List<RespostasQuestionario> questionariosNovos;

    private final Firestore firestore;

    public RepositorioQuestionarios(Firestore firestore) {
        this.firestore = firestore;
        questionariosNovos = new ArrayList<RespostasQuestionario>();
    }

    public boolean inserir(RespostasQuestionario respostas) {
        DocumentReference docRef = firestore.collection(NOME_COLECAO).document(respostas.getEmailUtl());

        // verificar se já existe
        try {
            boolean existeDocumento = docRef.get().get().exists();
            if(existeDocumento) return false;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ApiFuture<WriteResult> resultado = docRef.set(respostas);

        while (!resultado.isDone()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            resultado.get();
            questionariosNovos.add(respostas);
            return true;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<RespostasQuestionario> obterNovosQuestionarios() {
        List<RespostasQuestionario> novosQuestionarios = new ArrayList<RespostasQuestionario>(questionariosNovos);
        questionariosNovos.clear();
        return novosQuestionarios;
    }

    public RespostasQuestionario obterQuestionarioUtilizador(String emailUtl) {
        DocumentReference docRef = firestore.collection(NOME_COLECAO).document(emailUtl);


        try {
            boolean existeDocumento = docRef.get().get().exists();
            if (!existeDocumento) return null;

            DocumentSnapshot doc = docRef.get().get();

            List<Long> respostasLong = (List<Long>) doc.get(CAMPO_RESPOSTAS_QUESTIONARIO);
            List<Integer> respostas = new ArrayList<Integer>();
            for(Long r : respostasLong) {
                respostas.add(r.intValue());
            }
            RespostasQuestionario questionarioEntidade = new RespostasQuestionario(emailUtl, respostas);
            return questionarioEntidade;

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String toString() {
        String questionariosNome = "\n";


        Iterable<DocumentReference> todosDocs = firestore.collection(NOME_COLECAO).listDocuments();

        for (DocumentReference docref : todosDocs) {
            ApiFuture<DocumentSnapshot> apiFut = docref.get();

            try {
                DocumentSnapshot doc = apiFut.get();
                String email = (String)doc.get(CAMPO_EMAIL);
                List<Long> respostasLong = (List<Long>) doc.get(CAMPO_RESPOSTAS_QUESTIONARIO);
                List<Integer> respostas = new ArrayList<Integer>();
                for(Long r : respostasLong) {
                    respostas.add(r.intValue());
                }

                questionariosNome = questionariosNome+ new RespostasQuestionario(email, respostas).toString()+"\n";

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return "RepositorioQuestionarios{" +
                "questionarios=" + questionariosNome +
                "}\n";
    }
}
