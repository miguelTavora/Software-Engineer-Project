package acessodados.apresentacao;

import dominio.entidades.Identificador;
import dominio.entidades.PerfilExibir;
import dominio.entidades.Selecao;

import java.util.ArrayList;
import java.util.List;

public class RepositorioSelecoes {

    // atributo de teste, visto que isto deveria aceder ao Firestore
    private List<Selecao> selecoes;
    private int indiceObtido = 0;

    public RepositorioSelecoes() {
        selecoes = new ArrayList<Selecao>();
    }

    public boolean inserir(Selecao selecao) {
        boolean existe = false;
        for(Selecao sel : selecoes) {
            if(sel.getEmailUtilizador().equals(selecao.getEmailUtilizador()) &&
                    sel.getEmailPerfilApresentado().equals(selecao.getEmailPerfilApresentado()))
                existe = true;
        }
        if(!existe)
            return this.selecoes.add(selecao);

        return false;
    }

    // obtem as selecoes que ainda foram obtidas
    // para isso utilizador um contador
    public List<Selecao> obterNovasSelecoes() {
        List<Selecao> novasSelecoes = new ArrayList<Selecao>();
        int incremento = 0;
        for(int i = indiceObtido; i < selecoes.size(); i++) {
            novasSelecoes.add(selecoes.get(i));
            incremento++;
        }
        // faz set do indice atual
        indiceObtido = indiceObtido+incremento;
        return novasSelecoes;
    }

    // obtem as selecoes de um determinado utilizador
    public List<Selecao> obterSelecoesUtilizador(String emailUtl) {
        List<Selecao> selecoesUtl= new ArrayList<Selecao>();
        for(Selecao selecao : selecoes) {
            if(selecao.getEmailUtilizador().equals(emailUtl))
                selecoesUtl.add(selecao);
        }
        return selecoesUtl;
    }

    public boolean selecaoAtribuida(Identificador ident, PerfilExibir perfil) {
        for(Selecao sel : selecoes) {
            if(ident.getEmail().equals(sel.getEmailUtilizador()) &&
                    perfil.getEmail().equals(sel.getEmailPerfilApresentado()))
                return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String selecoesNome = "\n";
        for(int i = 0; i < selecoes.size(); i++) {
            selecoesNome = selecoesNome + selecoes.get(i)+",\n";
        }

        return "RepositorioSelecoes{" +
                "selecoes=" + selecoesNome +
                "}\n";
    }
}
