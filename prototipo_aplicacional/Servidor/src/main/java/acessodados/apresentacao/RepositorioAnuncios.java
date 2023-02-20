package acessodados.apresentacao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import dominio.entidades.Anuncio;
import dominio.entidades.Identificador;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class RepositorioAnuncios {

    // hashmap -> emailUtl, key -> contador para exibir os anuncios e desta forma não
    // exibir sempre o mesmo anuncio e exibir os diversos guardados
    private HashMap<String, Integer> anuncCorrente;

    // firestore é constitído de colecoes e documentos
    private final String NOME_COLECAO = "Anuncios";
    private final String NOME_FIELD = "exemplo";
    private final Firestore firestore;


    public RepositorioAnuncios(Firestore firestore) {
        anuncCorrente = new HashMap<String, Integer>();
        this.firestore = firestore;
    }

    // obter o anuncio caso este esteja guardado
    // caso nao esteja vai buscar o primeiro e guarda
    public Anuncio obterAnuncio(Identificador ident) {
        Integer indice = anuncCorrente.get(ident.getEmail());
        if(indice == null) {
            indice = 0;
        }

        CollectionReference colRef = firestore.collection(NOME_COLECAO);

        try {
            while(true) {
                DocumentReference docRef = colRef.document("" + indice);
                ApiFuture<DocumentSnapshot> apiFut = docRef.get();

                DocumentSnapshot docSnapShot = apiFut.get();//

                // se encontrar retorna
                if(docSnapShot.get(NOME_FIELD) != null) {
                    Anuncio anuncio = new Anuncio((String)docSnapShot.get(NOME_FIELD));
                    anuncCorrente.put(ident.getEmail(), indice+1);
                    return anuncio;
                }
                // se é 0 é porque não existe nenhum documento e retorna null
                else if(indice == 0) break;

                // é porque foram percorridos todos os anuncios e tem de voltar ao primeiro
                else indice = 0;

            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        anuncCorrente.put(ident.getEmail(), indice+1);
        return null;
    }

    public boolean inserir(Anuncio anuncio) {
        CollectionReference colRef = firestore.collection(NOME_COLECAO);

        int indiceAnuncio = 0;
        while(true) {
            DocumentReference docRef = colRef.document(""+indiceAnuncio);

            try {
                boolean existeDocumento = docRef.get().get().exists();
                if(existeDocumento) indiceAnuncio++;
                else break;
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        DocumentReference docRef = colRef.document(""+indiceAnuncio);

        ApiFuture<WriteResult> resultado = docRef.set(anuncio);

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
            return true;
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        String anuncioTexto = "\n";

        CollectionReference colRef = firestore.collection(NOME_COLECAO);
        int indice = 0;
        try {
            while (true) {
                DocumentReference docRef = colRef.document("" + indice);
                DocumentSnapshot docSnapShot = docRef.get().get();

                // para funcionar com o método toObject teria de ser com construtor por default e sets
                String texto = (String)docSnapShot.get(NOME_FIELD);
                if (texto != null) {
                    anuncioTexto = anuncioTexto + new Anuncio(texto).toString() + "\n";
                    indice++;
                }

                else break;
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return "Anuncios{" + anuncioTexto + "}\n";
    }
}
