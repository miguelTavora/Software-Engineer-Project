package acessodados.apresentacao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import dominio.entidades.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class RepositorioUtilizadores {

    // o formato que é guardado é o nome do documento é o email do utilizador
    private final String NOME_COLECAO = "Utilizadores";

    private final String CAMPO_EMAIL = "email";

    // dados precisos para o perfil de exibir
    private final String CAMPO_NOME = "nome";
    private final String CAMPO_GENERO = "genero";
    private final String CAMPO_DISTANCIA = "distancia";
    private final String CAMPO_IDADE = "idade";
    private final String CAMPO_FOTOS = "fotos";
    private final String CAMPO_DESCRICAO = "descricao";

    // dados necessarios para o identificador
    private final String CAMPO_DIST_MAXIMA = "distMaxima";
    private final String CAMPO_IDADES_ACEITAVEIS = "idadesAceitaveis";
    private final String CAMPO_GENERO_INTERESSE = "generoInteresse";


    // firestore para obter e guardar as selecoes
    private final Firestore firestore;

    // mapa que guarda os indices dos utilizadores aleatorios onde cada utilizador vai
    private HashMap<String, Integer> indiceAleatorio;

    public RepositorioUtilizadores(Firestore firestore) {
        this.firestore = firestore;
        indiceAleatorio = new HashMap<>();
    }

    public Identificador obterInformacaoIdentificacao(LocalizacaoUtilizador locUtilizador) {
        DocumentReference docRef = firestore.collection(NOME_COLECAO).document(locUtilizador.getEmail());

        try {
            DocumentSnapshot docSnapShot = docRef.get().get();

            if(!docSnapShot.exists()) return null;

            // aqui deveria fazer algo em relação à localização mas não foi implementada essa parte
            Long distMaximaLong = (Long) docSnapShot.get(CAMPO_DIST_MAXIMA);
            int distMaxima = distMaximaLong.intValue();

            // obter idades e converter para array
            List<Long> idadesAceitaveisLista = (List<Long>) docSnapShot.get(CAMPO_IDADES_ACEITAVEIS);
            int[] idadesAceitaveis = new int[idadesAceitaveisLista.size()];
            for(int i = 0; i < idadesAceitaveisLista.size(); i++) {
                idadesAceitaveis[i] = idadesAceitaveisLista.get(i).intValue();
            }

            String generoInteresse = (String) docSnapShot.get(CAMPO_GENERO_INTERESSE);

            Identificador ident = new Identificador(locUtilizador.getEmail(), distMaxima, idadesAceitaveis, generoInteresse);

            return ident;

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public PerfilExibir obterPerfil(String emailUtilizador) {
        DocumentReference docRef = firestore.collection(NOME_COLECAO).document(emailUtilizador);
        ApiFuture<DocumentSnapshot> apiFut = docRef.get();


        try {
            DocumentSnapshot docSnapShot = apiFut.get();

            if(!docSnapShot.exists()) return null;

            String nome = (String) docSnapShot.get(CAMPO_NOME);
            String genero = (String) docSnapShot.get(CAMPO_GENERO);


            Long distLong = (Long) docSnapShot.get(CAMPO_DISTANCIA);
            int dist = distLong.intValue();
            Long idadeLong = (Long) docSnapShot.get(CAMPO_IDADE);
            int idade = idadeLong.intValue();

            // tem de ser assim para não dar erro
            List<HashMap<String, String>> fotosTexto = (List<HashMap<String, String>>) docSnapShot.get(CAMPO_FOTOS);
            List<Foto> fotos = new ArrayList<Foto>();
            for(HashMap<String, String> mapaFoto : fotosTexto) {
                String textoFoto = mapaFoto.get("texto");
                Foto foto = new Foto(textoFoto);
                fotos.add(foto);
            }

            //List<Foto> fotos = (List<Foto>) docSnapShot.get(CAMPO_FOTOS);
            String descricao = (String) docSnapShot.get(CAMPO_DESCRICAO);

            PerfilExibir perfil = new PerfilExibir(nome, genero, dist, idade, fotos, descricao, emailUtilizador);
            return perfil;

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public PerfilExibir obterPerfilAleatorio(Identificador ident) {
        Integer indice = indiceAleatorio.get(ident.getEmail());
        if(indice ==  null) {
            indice = 0;
        }

        CollectionReference colRef = firestore.collection(NOME_COLECAO);
        Iterable<DocumentReference> todosDocs = colRef.listDocuments();

        int indiceCurrente = 0;
        boolean entrou = false;
        for (DocumentReference docref : todosDocs) {
            // para passar à frente os que já foram vistos
            if(indiceCurrente >= indice) {
                ApiFuture<DocumentSnapshot> apiFut = docref.get();

                try {
                    DocumentSnapshot doc = apiFut.get();

                    if(!doc.exists()) return null;

                    String email = (String) doc.get(CAMPO_EMAIL);
                    String nome = (String) doc.get(CAMPO_NOME);
                    String genero = (String) doc.get(CAMPO_GENERO);
                    Long distLong = (Long) doc.get(CAMPO_DISTANCIA);
                    int dist = distLong.intValue();
                    Long idadeLong = (Long) doc.get(CAMPO_IDADE);
                    int idade = idadeLong.intValue();


                    List<HashMap<String, String>> fotosTexto = (List<HashMap<String, String>>) doc.get(CAMPO_FOTOS);

                    List<Foto> fotos = new ArrayList<Foto>();
                    for(HashMap<String, String> mapaFoto : fotosTexto) {
                        String textoFoto = mapaFoto.get("texto");
                        Foto foto = new Foto(textoFoto);
                        fotos.add(foto);
                    }

                    String descricao = (String) doc.get(CAMPO_DESCRICAO);

                    PerfilExibir perfil = new PerfilExibir(nome, genero, dist, idade, fotos, descricao, email);
                    indiceAleatorio.put(ident.getEmail(), indice+1);
                    return perfil;

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                entrou = true;
            }
            indiceCurrente++;
        }

        // quando percorre todos começa de novo
        if(!entrou) indiceAleatorio.put(ident.getEmail(), 0);

        return null;
    }

    // este metodo deve ser depois modificado, serve somente de teste
    public boolean inserir(Utilizador utilizador) {
        DocumentReference docRef = firestore.collection(NOME_COLECAO).document(utilizador.getEmail());

        // verificar se já existe
        try {
            boolean existeDocumento = docRef.get().get().exists();
            if(existeDocumento) return false;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        ApiFuture<WriteResult> resultado = docRef.set(utilizador);

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
        String nomes = "\n";
        Iterable<DocumentReference> todosDocs = firestore.collection(NOME_COLECAO).listDocuments();

        for (DocumentReference docref : todosDocs) {
            ApiFuture<DocumentSnapshot> apiFut = docref.get();

            try {
                DocumentSnapshot doc = apiFut.get();

                String email = (String) doc.get(CAMPO_EMAIL);

                String nome = (String) doc.get(CAMPO_NOME);
                String genero = (String) doc.get(CAMPO_GENERO);

                Long distLong = (Long) doc.get(CAMPO_DISTANCIA);
                int dist = distLong.intValue();

                Long idadeLong = (Long) doc.get(CAMPO_IDADE);
                int idade = idadeLong.intValue();

                // reslver esta s«ituação
                List<HashMap<String, String>> fotosTexto = (List<HashMap<String, String>>) doc.get(CAMPO_FOTOS);

                List<Foto> fotos = new ArrayList<Foto>();
                for(HashMap<String, String> mapaFoto : fotosTexto) {
                    String textoFoto = mapaFoto.get("texto");
                    System.out.println("TEXTO::: "+textoFoto);
                    Foto foto = new Foto(textoFoto);
                    fotos.add(foto);
                }

                System.out.println("FOTOS: "+fotos);

                String descricao = (String) doc.get(CAMPO_DESCRICAO);

                Long distMaximaLong = (Long) doc.get(CAMPO_DIST_MAXIMA);
                int distMaxima = distMaximaLong.intValue();

                List<Integer> idadesAceitaveis = (List<Integer>) doc.get(CAMPO_IDADES_ACEITAVEIS);
                String generoInteresse = (String) doc.get(CAMPO_GENERO_INTERESSE);

                Utilizador utl = new Utilizador(email, nome, genero, dist, idade, fotos, descricao, distMaxima, idadesAceitaveis, generoInteresse);
                nomes = nomes+ utl+"\n";

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        return "\nRepositorioUtilizadores{" + nomes + "}\n";
    }
}
