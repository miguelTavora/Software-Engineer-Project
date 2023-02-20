package acessodados.melhoresutilizadores;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import dominio.entidades.Identificador;
import dominio.entidades.RespostasQuestionario;
import dominio.recomendacao.Recomendacao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RepositorioRecomendacoes {

    // o documento é constituido por utilizador1 + separador + utilizador2
    private final String NOME_COLECAO = "Recomendacoes";
    private final String SEPARADOR = ">";
    private final String CAMPO_UTILIZAR_1 = "emailUtl1";
    private final String CAMPO_UTILIZAR_2 = "emailUtl2";
    private final String CAMPO_PRIORIDADE = "prioridade";


    private final Firestore firestore;

    public RepositorioRecomendacoes(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<Recomendacao> obterRecomendacoes(Identificador ident) {
        List<Recomendacao> recomenUtl = new ArrayList<Recomendacao>();


        Iterable<DocumentReference> todosDocs = firestore.collection(NOME_COLECAO).listDocuments();


        for (DocumentReference docref : todosDocs) {
            ApiFuture<DocumentSnapshot> apiFut = docref.get();

            try {
                DocumentSnapshot doc = apiFut.get();

                String email1 = (String)doc.get(CAMPO_UTILIZAR_1);
                if(email1.equals(ident.getEmail())) {
                    String email2 = (String)doc.get(CAMPO_UTILIZAR_2);
                    Long prioridadeLong = (Long) doc.get(CAMPO_PRIORIDADE);
                    int prioridade = prioridadeLong.intValue();

                    Recomendacao recomen = new Recomendacao(email1, email2, prioridade);
                    recomenUtl.add(recomen);
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return recomenUtl;
    }

    public boolean inserirRecomendacoes(List<Recomendacao> recCalculadas) {
        List<Integer> erro = new ArrayList<Integer>();

        for(int i = 0; i < recCalculadas.size(); i++) {
            DocumentReference docRef = firestore.collection(NOME_COLECAO).document(recCalculadas.get(i).getEmailUtl1()
                    +SEPARADOR+recCalculadas.get(i).getEmailUtl2());

            ApiFuture<WriteResult> resultado = docRef.set(recCalculadas.get(i));

            while (!resultado.isDone()) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                resultado.get();
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                erro.add(i);
            }
        }
        return !(erro.size() > 0);
    }

    @Override
    public String toString() {
        String recomendacoesNome = "\n";


        Iterable<DocumentReference> todosDocs = firestore.collection(NOME_COLECAO).listDocuments();

        for (DocumentReference docref : todosDocs) {
            ApiFuture<DocumentSnapshot> apiFut = docref.get();

            try {
                DocumentSnapshot doc = apiFut.get();


                String email1 = (String)doc.get(CAMPO_UTILIZAR_1);
                String email2 = (String)doc.get(CAMPO_UTILIZAR_2);
                Long prioridadeLong = (Long) doc.get(CAMPO_PRIORIDADE);
                int prioridade = prioridadeLong.intValue();

                recomendacoesNome = recomendacoesNome+ new Recomendacao(email1, email2, prioridade).toString()+"\n";

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return "RepositorioRecomendacoes{" +
                "recomendacoes=" + recomendacoesNome +
                "}\n";
    }
}
