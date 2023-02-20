package acessodados.apresentacao;

import dominio.entidades.Anuncio;
import dominio.entidades.Identificador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RepositorioAnuncios {

    // hashmap -> emailUtl, key -> contador para exibir os anuncios e desta forma não
    // exibir sempre o mesmo anuncio e exibir os diversos guardados
    private HashMap<String, Integer> anuncCorrente;

    // atributo de teste que devia obter do Firestore
    private List<Anuncio> anuncios;

    public RepositorioAnuncios() {
        anuncCorrente = new HashMap<String, Integer>();
        anuncios = new ArrayList<Anuncio>();
    }

    // obter o anuncio caso este esteja guardado
    // caso nao esteja vai buscar o primeiro e guarda
    public Anuncio obterAnuncio(Identificador ident) {
        Integer indice = anuncCorrente.get(ident.getEmail());
        if(indice == null) {
            anuncCorrente.put(ident.getEmail(), 1);
            return anuncios.get(0);
        }
        Anuncio anuncio = null;
        try {
            anuncio = anuncios.get(indice);
            anuncCorrente.put(ident.getEmail(), indice+1);
        } catch (Exception e) {
            anuncio = anuncios.get(0);
            anuncCorrente.put(ident.getEmail(), 1);
        }
        return anuncio;
    }

    public boolean inserir(Anuncio anuncio) {
        return this.anuncios.add(anuncio);
    }

    @Override
    public String toString() {
        String anuncioTexto = "\n";
        for(int i = 0; i < anuncios.size(); i++) {
            anuncioTexto = anuncioTexto+anuncios.get(i)+",\n";
        }
        return "RepositorioAnuncios{" +
                "anuncios=" + anuncioTexto +
                "}\n";
    }
}
