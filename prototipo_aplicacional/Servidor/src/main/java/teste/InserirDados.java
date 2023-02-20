package teste;

import acessodados.apresentacao.RepositorioAnuncios;
import acessodados.apresentacao.RepositorioSelecoes;
import acessodados.apresentacao.RepositorioUtilizadores;
import acessodados.melhoresutilizadores.RepositorioClassificacaoUtilizdor;
import acessodados.melhoresutilizadores.RepositorioQuestionarios;
import acessodados.melhoresutilizadores.RepositorioRecomendacoes;
import dominio.entidades.*;
import dominio.recomendacao.Recomendacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InserirDados {

    private RepositorioAnuncios repAnunc;
    private RepositorioSelecoes repSel;
    private RepositorioUtilizadores repUtls;
    private RepositorioClassificacaoUtilizdor repClassUtls;
    private RepositorioQuestionarios repQues;
    private RepositorioRecomendacoes repRecomen;

    public InserirDados(RepositorioAnuncios repAnunc, RepositorioSelecoes repSel, RepositorioUtilizadores repUtls,
                        RepositorioClassificacaoUtilizdor repClassUtls, RepositorioQuestionarios repQues,
                        RepositorioRecomendacoes repRecomen) {
        this.repAnunc = repAnunc;
        this.repSel = repSel;
        this.repUtls = repUtls;
        this.repClassUtls = repClassUtls;
        this.repQues = repQues;
        this.repRecomen = repRecomen;
    }

    public void inserirDadosAnuncio() {
        String textoAnuncio = "Anuncio exclusivo! Não perca já 5 telemoveis pelo preço de 4!!!";
        String textoAnuncio2 = "Anuncio exclusivo! Novo aspirador, aspira tudo por metade do preço dos outros!!!";
        String textoAnuncio3 = "Anuncio novo!! Computador novo por somente 500 euros, não perca já!!!!";
        String textoAnuncio4 = "Ultimo anuncio!!!";

        Anuncio anuncio1 = new Anuncio(textoAnuncio);
        Anuncio anuncio2 = new Anuncio(textoAnuncio2);
        Anuncio anuncio3 = new Anuncio(textoAnuncio3);
        Anuncio anuncio4 = new Anuncio(textoAnuncio4);

        this.repAnunc.inserir(anuncio1);
        this.repAnunc.inserir(anuncio2);
        this.repAnunc.inserir(anuncio3);
        this.repAnunc.inserir(anuncio4);
    }

    public void inserirDadosUtilizadores() {
        String nome1 = "João";
        String nome2 = "José";
        String nome3 = "Ricardo";
        String nome4 = "Luís";
        String nome5 = "Sara";
        String nome6 = "Matilde";
        String nome7 = "Beatriz";
        String nome8 = "Mafalda";
        String nome9 = "Ruben";
        String nome10 = "Carolina";
        String nome11 = "Catarina";
        String nome12 = "Jessica";
        String nome13 = "Rita";
        String nome14 = "Pedro";
        String nome15 = "Miguel";
        String nome16 = "Roberto";
        String nome17 = "Ana";
        String nome18 = "Bernardo";
        String nome19 = "Joana";
        String nome20 = "Carlos";
        String nome21 = "Marina";
        String nome22 = "Diogo";


        String genero1 = "masculino";
        String genero2 = "femenino";


        int dist1 = 10;
        int dist2 = 20;
        int dist3 = 50;
        int dist4 = 100;

        int idade1 = 18;
        int idade2 = 23;
        int idade3 = 30;


        List<Foto> fotos = new ArrayList<>();
        for(int i = 1; i < 3; i++) {
            Foto foto = new Foto("Foto: "+i);
            fotos.add(foto);

        }

        String descricao1 = "nao gosto de estudar";
        String descricao2 = "nao gosto de ananas";
        String descricao3 = "nao gosto de bananas";


        String email1 = "joao@isel.pt";
        String email2 = "jose@isel.pt";
        String email3 = "ricardo@isel.pt";
        String email4 = "luis@isel.pt";
        String email5 = "sara@isel.pt";
        String email6 = "matilde@isel.pt";
        String email7 = "beatriz@isel.pt";
        String email8 = "mafalda@isel.pt";
        String email9 = "ruben@isel.pt";
        String email10 = "carolina@isel.pt";
        String email11 = "catarina@isel.pt";
        String email12 = "jessica@isel.pt";
        String email13 = "rita@isel.pt";
        String email14 = "pedro@isel.pt";
        String email15 = "miguel@isel.pt";
        String email16 = "roberto@isel.pt";
        String email17 = "ana@isel.pt";
        String email18 = "bernardo@isel.pt";
        String email19 = "joana@isel.pt";
        String email20 = "carlos@isel.pt";
        String email21 = "marina@isel.pt";
        String email22 = "diogo@isel.pt";


        List<Integer> idadesAceitaveis1 = new ArrayList<>();
        idadesAceitaveis1.add(18);idadesAceitaveis1.add(50);
        List<Integer> idadesAceitaveis2 = new ArrayList<>();
        idadesAceitaveis2.add(18);idadesAceitaveis2.add(25);
        List<Integer> idadesAceitaveis3 = new ArrayList<>();
        idadesAceitaveis3.add(18);idadesAceitaveis3.add(22);


        Utilizador utl1 = new Utilizador(email1, nome1, genero1, dist1, idade1, fotos, descricao1, dist4, idadesAceitaveis1, genero2);
        Utilizador utl2 = new Utilizador(email2,nome2, genero1, dist2, idade2, fotos, descricao2, dist4, idadesAceitaveis1, genero2);
        Utilizador utl3 = new Utilizador(email3,nome3, genero1, dist3, idade3, fotos, descricao3, dist4, idadesAceitaveis2, genero2);
        Utilizador utl4 = new Utilizador(email4,nome4, genero1, dist4, idade1, fotos, descricao1, dist4, idadesAceitaveis1, genero2);
        Utilizador utl5 = new Utilizador(email5,nome5, genero2, dist1, idade2, fotos, descricao2, dist3, idadesAceitaveis3, genero1);


        Utilizador utl6 = new Utilizador(email6,nome6, genero2, dist2, idade3, fotos, descricao3, dist4, idadesAceitaveis1, genero1);
        Utilizador utl7 = new Utilizador(email7,nome7, genero2, dist3, idade1, fotos, descricao1, dist4, idadesAceitaveis1, genero1);
        Utilizador utl8 = new Utilizador(email8,nome8, genero2, dist4, idade2, fotos, descricao2, dist4, idadesAceitaveis2, genero2);
        Utilizador utl9 = new Utilizador(email9,nome9, genero1, dist1, idade3, fotos, descricao1, dist4, idadesAceitaveis1, genero2);
        Utilizador utl10 = new Utilizador(email10,nome10, genero2, dist2, idade1, fotos, descricao3, dist2, idadesAceitaveis3, genero1);


        Utilizador utl11 = new Utilizador(email11,nome11, genero2, dist3, idade2, fotos, descricao1, dist4, idadesAceitaveis1, genero1);
        Utilizador utl12 = new Utilizador(email12,nome12, genero2, dist4, idade3, fotos, descricao2, dist4, idadesAceitaveis1, genero1);
        Utilizador utl13 = new Utilizador(email13,nome13, genero2, dist1, idade1, fotos, descricao3, dist4, idadesAceitaveis2, genero2);
        Utilizador utl14 = new Utilizador(email14,nome14, genero1, dist2, idade2, fotos, descricao1, dist4, idadesAceitaveis1, genero2);
        Utilizador utl15 = new Utilizador(email15,nome15, genero1, dist3, idade3, fotos, descricao2, dist1, idadesAceitaveis3, genero2);


        Utilizador utl16 = new Utilizador(email16,nome16, genero1, dist4, idade1, fotos, descricao3, dist4, idadesAceitaveis1, genero1);
        Utilizador utl17 = new Utilizador(email17,nome17, genero2, dist1, idade2, fotos, descricao1, dist4, idadesAceitaveis1, genero1);
        Utilizador utl18 = new Utilizador(email18,nome18, genero1, dist2, idade3, fotos, descricao2, dist4, idadesAceitaveis2, genero1);
        Utilizador utl19 = new Utilizador(email19,nome19, genero2, dist3, idade1, fotos, descricao3, dist2, idadesAceitaveis1, genero2);
        Utilizador utl20 = new Utilizador(email20,nome20, genero1, dist4, idade2, fotos, descricao1, dist3, idadesAceitaveis3, genero2);


        Utilizador utl21 = new Utilizador(email21,nome21, genero2, dist1, idade3, fotos, descricao2, dist4, idadesAceitaveis1, genero1);
        Utilizador utl22 = new Utilizador(email22,nome22, genero1, dist2, idade1, fotos, descricao3, dist4, idadesAceitaveis1, genero2);

        this.repUtls.inserir(utl1);
        this.repUtls.inserir(utl2);
        this.repUtls.inserir(utl3);
        this.repUtls.inserir(utl4);
        this.repUtls.inserir(utl5);


        this.repUtls.inserir(utl6);
        this.repUtls.inserir(utl7);
        this.repUtls.inserir(utl8);
        this.repUtls.inserir(utl9);
        this.repUtls.inserir(utl10);


        this.repUtls.inserir(utl11);
        this.repUtls.inserir(utl12);
        this.repUtls.inserir(utl13);
        this.repUtls.inserir(utl14);
        this.repUtls.inserir(utl15);


        this.repUtls.inserir(utl16);
        this.repUtls.inserir(utl17);
        this.repUtls.inserir(utl18);
        this.repUtls.inserir(utl19);
        this.repUtls.inserir(utl20);


        this.repUtls.inserir(utl21);
        this.repUtls.inserir(utl22);

    }

    public void inserirQuestionario() {
        String email3 = "ricardo@isel.pt";
        String email4 = "luis@isel.pt";

        List<Integer> respostasQuestionario1 = new ArrayList<>();
        List<Integer> respostasQuestionario2 = new ArrayList<>();
        Random r = new Random();
        for(int i = 0; i < 5; i++) {
            int result1 = r.nextInt(6) + 1;
            int result2 = r.nextInt(6) + 1;
            respostasQuestionario1.add(result1);
            respostasQuestionario2.add(result2);
        }


        RespostasQuestionario questionario1 = new RespostasQuestionario(email3, respostasQuestionario1);
        RespostasQuestionario questionario2 = new RespostasQuestionario(email4, respostasQuestionario2);

        this.repQues.inserir(questionario1);
        this.repQues.inserir(questionario2);
    }

    public void inserirClasificacaoUtilizadores() {
        String email1 = "joao@isel.pt";
        String email2 = "jose@isel.pt";
        String email3 = "ricardo@isel.pt";
        String email4 = "luis@isel.pt";
        String email5 = "sara@isel.pt";

        int classificacao1 = 60;
        int classificacao2 = 70;
        int classificacao3 = 80;
        int classificacao4 = 90;

        Classificacao classf1 = new Classificacao(email1, email2, classificacao1);
        Classificacao classf2 = new Classificacao(email1, email3, classificacao2);
        Classificacao classf3 = new Classificacao(email1, email4, classificacao3);
        Classificacao classf4 = new Classificacao(email1, email5, classificacao4);

        Classificacao classf5 = new Classificacao(email2, email1, classificacao1);
        Classificacao classf6 = new Classificacao(email2, email3, classificacao2);
        Classificacao classf7 = new Classificacao(email2, email4, classificacao2);
        Classificacao classf8 = new Classificacao(email2, email5, classificacao2);

        Classificacao classf9 = new Classificacao(email3, email1, classificacao1);
        Classificacao classf10 = new Classificacao(email3, email2, classificacao2);
        Classificacao classf11 = new Classificacao(email3, email4, classificacao2);
        Classificacao classf12 = new Classificacao(email3, email5, classificacao2);


        this.repClassUtls.inserirClassificacao(classf1);
        this.repClassUtls.inserirClassificacao(classf2);
        this.repClassUtls.inserirClassificacao(classf3);
        this.repClassUtls.inserirClassificacao(classf4);

        this.repClassUtls.inserirClassificacao(classf5);
        this.repClassUtls.inserirClassificacao(classf6);
        this.repClassUtls.inserirClassificacao(classf7);
        this.repClassUtls.inserirClassificacao(classf8);

        this.repClassUtls.inserirClassificacao(classf9);
        this.repClassUtls.inserirClassificacao(classf10);
        this.repClassUtls.inserirClassificacao(classf11);
        this.repClassUtls.inserirClassificacao(classf12);

    }

    public void inserirSelecoes() {
        String email5 = "sara@isel.pt";
        String email6 = "matilde@isel.pt";
        String email7 = "beatriz@isel.pt";

        boolean gosto = true;
        boolean naoGosto = false;

        Selecao sel1 = new Selecao(gosto, email5, email6);
        Selecao sel2 = new Selecao(gosto, email5, email7);

        Selecao sel3 = new Selecao(naoGosto, email6, email5);
        Selecao sel4 = new Selecao(naoGosto, email6, email7);

        Selecao sel5 = new Selecao(gosto, email7, email5);
        Selecao sel6 = new Selecao(gosto, email7, email6);


        this.repSel.inserir(sel1);
        this.repSel.inserir(sel2);

        this.repSel.inserir(sel3);
        this.repSel.inserir(sel4);

        this.repSel.inserir(sel5);
        this.repSel.inserir(sel6);

    }


    public void inserirRecomendacoes() {
        String email12 = "jessica@isel.pt";
        String email13 = "rita@isel.pt";

        int priodade1 = 70;
        int priodade2 = 80;

        Recomendacao rec1 = new Recomendacao(email12, email13, priodade1);
        Recomendacao rec2 = new Recomendacao(email13, email12, priodade2);
        List<Recomendacao> list = new ArrayList<>();
        list.add(rec1);
        list.add(rec2);

        this.repRecomen.inserirRecomendacoes(list);

    }

}
