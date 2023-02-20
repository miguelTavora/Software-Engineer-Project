package acessodados.melhoresutilizadores;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import dominio.entidades.Classificacao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RepositorioClassificacaoUtilizdor {

    private final String NOME_COLECAO = "ClassificacaoUtilizadores";
    // nome do documento é onstituido por "nome utilizador" + separador +"nome outro utilizador"
    private final String SEPARADOR = ">";
    private final String CAMPO_EMAIL_UTL = "emailUtl";
    private final String CAMPO_EMAIL_CORRS = "emailUtlCorr";
    private final String CAMPO_CLASSIFICACAO = "classificao";

    private final Firestore firestore;

    // atributo para enviar as novas classificacoes
    private List<Classificacao> classificacoesNovas;

    public RepositorioClassificacaoUtilizdor(Firestore firestore) {
        this.firestore = firestore;
        classificacoesNovas = new ArrayList<Classificacao>();
    }

    public boolean inserirClassificacao(Classificacao classificacao) {
        DocumentReference docRef = firestore.collection(NOME_COLECAO).document(classificacao.getEmailUtl()
                + SEPARADOR + classificacao.getEmailUtlCorr());

        // verificar se já existe
        try {
            boolean existeDocumento = docRef.get().get().exists();
            if(existeDocumento) return false;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ApiFuture<WriteResult> resultado = docRef.set(classificacao);

        while (!resultado.isDone()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            // tem de ter o get para dar set corretamente
            resultado.get();
            classificacoesNovas.add(classificacao);
            return true;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    // obtem as classificacoes que ainda não foram obtidas previamente
    public List<Classificacao> obterNovasClassificacoes() {
        ArrayList<Classificacao> novasClassificacoes = new ArrayList<Classificacao>(classificacoesNovas);
        classificacoesNovas.clear();
        return novasClassificacoes;
    }

    // obtem todas as classificacoes dadas por um utilizador
    public List<Classificacao> obterClassificacoesUtilizador(String emailUtl) {
        List<Classificacao> classificacaos = new ArrayList<Classificacao>();

        Iterable<DocumentReference> todosDocs = firestore.collection(NOME_COLECAO).listDocuments();

        for (DocumentReference docref : todosDocs) {
            ApiFuture<DocumentSnapshot> apiFut = docref.get();

            try {
                DocumentSnapshot doc = apiFut.get();
                String emailUtlObtido = (String)doc.get(CAMPO_EMAIL_UTL);

                if(emailUtlObtido.contains(emailUtl)) {
                    String emailUtlCorrs = (String)doc.get(CAMPO_EMAIL_CORRS);
                    Long classificacaoLong = (Long) doc.get(CAMPO_CLASSIFICACAO);
                    int classificacao = classificacaoLong.intValue();

                    Classificacao classf = new Classificacao(emailUtl, emailUtlCorrs, classificacao);
                    classificacaos.add(classf);
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return classificacaos;
    }

    @Override
    public String toString() {
        String classificacaoNome = "\n";

        Iterable<DocumentReference> todosDocs = firestore.collection(NOME_COLECAO).listDocuments();

        for (DocumentReference docref : todosDocs) {
            ApiFuture<DocumentSnapshot> apiFut = docref.get();

            try {
                DocumentSnapshot doc = apiFut.get();
                String email1 = (String)doc.get(CAMPO_EMAIL_UTL);
                String email2 = (String)doc.get(CAMPO_EMAIL_CORRS);
                Long classificacaoLong = (Long) doc.get(CAMPO_CLASSIFICACAO);
                int classificacao = classificacaoLong.intValue();
                classificacaoNome = classificacaoNome+ new Classificacao(email1, email2, classificacao).toString()+"\n";

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return "RepositorioClassificacaoUtilizdor{" +
                "classificacoes=" + classificacaoNome +
                '}';
    }
}
