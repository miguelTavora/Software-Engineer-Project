package acessodados.apresentacao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import dominio.entidades.Identificador;
import dominio.entidades.PerfilExibir;
import dominio.entidades.Selecao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RepositorioSelecoes {

    private final String NOME_COLECAO = "Selecoes";
    // nome do documento é onstituido por "nome utilizador" + separador +"nome outro utilizador"
    private final String SEPARADOR = ">";
    // strings uteis para obter valores da firestore
    private final String NOME_FIELD_UTL = "emailUtilizador";
    private final String NOME_FIELD_UTL_APRS = "emailPerfilApresentado";
    private final String NOME_FIELD_GOSTO = "gosto";

    // firestore para obter e guardar as selecoes
    private final Firestore firestore;
    // util para não estar sermpre a utilizar
    private final CollectionReference colRef;

    // atributo para enviar as selecoes novas criadas
    private ArrayList<Selecao> selecoesNovas;

    public RepositorioSelecoes(Firestore firestore) {
        this.firestore = firestore;
        colRef = firestore.collection(NOME_COLECAO);
        selecoesNovas = new ArrayList<Selecao>();
    }

    public synchronized boolean inserir(Selecao selecao) {
        DocumentReference docRef = colRef.document(selecao.getEmailUtilizador()
                + SEPARADOR + selecao.getEmailPerfilApresentado());

        // verificar se já existe
        try {
            boolean existeDocumento = docRef.get().get().exists();
            if(existeDocumento) return false;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ApiFuture<WriteResult> resultado = docRef.set(selecao);

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
            selecoesNovas.add(selecao);
            return true;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    // obtem as selecoes que ainda foram obtidas
    // para isso utilizador um contador
    public synchronized List<Selecao> obterNovasSelecoes() {
        ArrayList<Selecao> novasSelecoes = new ArrayList<Selecao>(selecoesNovas);
        selecoesNovas.clear();
        return novasSelecoes;
    }

    // obtem as selecoes de um determinado utilizador
    public List<Selecao> obterSelecoesUtilizador(String emailUtl)  {
        List<Selecao> selecoes = new ArrayList<Selecao>();


        Iterable<DocumentReference> todosDocs = colRef.listDocuments();


        for (DocumentReference docref : todosDocs) {
            ApiFuture<DocumentSnapshot> apiFut = docref.get();

            try {
                DocumentSnapshot doc = apiFut.get();
                String email1 = (String)doc.get(NOME_FIELD_UTL);
                String email2 = (String)doc.get(NOME_FIELD_UTL_APRS);

                if(email1.contains(emailUtl)) {
                    Selecao sel = new Selecao((boolean)doc.get(NOME_FIELD_GOSTO), email1, email2);
                    selecoes.add(sel);
                }

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return selecoes;
    }

    public boolean selecaoAtribuida(Identificador ident, PerfilExibir perfil) {
        DocumentReference docRef = colRef.document(ident.getEmail()+SEPARADOR + perfil.getEmail());

        try {
            return docRef.get().get().exists();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public String toString() {
        String selecoesNome = "\n";

        Iterable<DocumentReference> todosDocs = colRef.listDocuments();


        for (DocumentReference docref : todosDocs) {
            ApiFuture<DocumentSnapshot> apiFut = docref.get();

            try {
                DocumentSnapshot doc = apiFut.get();
                String email1 = (String)doc.get(NOME_FIELD_UTL);
                String email2 = (String)doc.get(NOME_FIELD_UTL_APRS);
                boolean gosto = (boolean)doc.get(NOME_FIELD_GOSTO);
                selecoesNome = selecoesNome+ new Selecao(gosto, email1, email2).toString()+"\n";

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return "RepositorioSelecoes{" +
                "selecoes=" + selecoesNome +
                "}\n";
    }
}
