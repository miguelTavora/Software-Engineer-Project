package dominio.servicos;

import acessodados.apresentacao.RepositorioAnuncios;
import acessodados.apresentacao.RepositorioSelecoes;
import acessodados.apresentacao.RepositorioUtilizadores;
import acessodados.melhoresutilizadores.RepositorioRecomendacoes;
import io.grpc.stub.StreamObserver;
import servicoApresentacao.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServicoApresentacao extends ApresentacaoGrpc.ApresentacaoImplBase {

    private HashMap<dominio.entidades.LocalizacaoUtilizador, dominio.entidades.Identificador> localizacaoUtls;

    // map que para utilizador obtem até que indice já obteve as recomendacoes
    // key -> identificador do utilizador, value -> valor do indice das recomendacoes obtidas
    private HashMap<String, Integer> indicesRecomendacoes;

    private RepositorioUtilizadores repUtls;
    private RepositorioSelecoes repSel;
    private RepositorioAnuncios repAnun;
    private RepositorioRecomendacoes repRecomen;


    public ServicoApresentacao(RepositorioUtilizadores repUtls, RepositorioSelecoes repSel, RepositorioAnuncios repAnun,
                               RepositorioRecomendacoes repRecomen) {
        this.repUtls = repUtls;
        this.repSel = repSel;
        this.repAnun = repAnun;
        this.repRecomen = repRecomen;
        localizacaoUtls = new HashMap<>();
        indicesRecomendacoes = new HashMap<>();

    }

    @Override
    public synchronized void enviarLocalizacaoUtilizador(LocalizacaoUtilizador request, StreamObserver<Identificador> responseObserver) {
        dominio.entidades.LocalizacaoUtilizador localizacao = new dominio.entidades.LocalizacaoUtilizador(
                request.getNome(), request.getEmail(), request.getLocalizacao());

        // obter a entidade guardada e converter para objeto do gRPC
        dominio.entidades.Identificador identificador = repUtls.obterInformacaoIdentificacao(localizacao);
        int[] idadesAceitaveisArray = identificador.getIdadesAceitaveis();
        List<Integer> idadesAceitaveisList = new ArrayList<>();
        for(int i = 0; i < idadesAceitaveisArray.length; i++) {
            idadesAceitaveisList.add(idadesAceitaveisArray[i]);
        }


        Identificador identificadorgRPC = Identificador.newBuilder().setEmail(identificador.getEmail())
                .setDistMaxima(identificador.getDistMaxima()).addAllIdadesAceitaveis(idadesAceitaveisList)
                .setGeneroInteresse(identificador.getGeneroInteresse()).build();
        // enviar o resultado
        responseObserver.onNext(identificadorgRPC);
        responseObserver.onCompleted();

    }

    @Override
    public synchronized void pedirUtilizadores(Identificador request, StreamObserver<PerfilExibir> responseObserver) {

        List<Integer> idadesAceitaveisLista = request.getIdadesAceitaveisList();
        int[] idadesAceitaveis = new int[idadesAceitaveisLista.size()];
        for(int i = 0; i < idadesAceitaveisLista.size(); i++) {
            idadesAceitaveis[i] = idadesAceitaveisLista.get(i);
        }
        dominio.entidades.Identificador identificador = new dominio.entidades.Identificador(
                request.getEmail(), request.getDistMaxima(), idadesAceitaveis, request.getGeneroInteresse());

        List<dominio.recomendacao.Recomendacao> recomendacaos = repRecomen.obterRecomendacoes(identificador);
        Integer indice = indicesRecomendacoes.get(identificador.getEmail());
        // quando não existe nenhuma chave guardada
        if(indice == null) {
            indicesRecomendacoes.put(identificador.getEmail(), 0);
            indice = 0;
        }

        List<dominio.entidades.PerfilExibir> perfis = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            // quando existem recomendacoes para ser obtidas,
            // internamente nesta classe é mantido qual o indice corrente
            if(indice > recomendacaos.size()-1  && recomendacaos.size() != 0) {
                String emailUtlRecomendado = recomendacaos.get(indice).getEmailUtl2();
                dominio.entidades.PerfilExibir perfil = repUtls.obterPerfil(emailUtlRecomendado);
                if(utilizadorAceitavel(identificador, perfil)) {
                    perfis.add(perfil);
                }
            }
            // obtem perfis aleatoriamente
            else {
                dominio.entidades.PerfilExibir perfil = obterPerfilAleatorio(identificador);
                if(perfil == null) break;
                perfis.add(perfil);
            }
        }


        for(int i = 0; i < perfis.size(); i++) {
            dominio.entidades.PerfilExibir perfilObtido = perfis.get(i);
            PerfilExibir.Builder perfilBuilder =  PerfilExibir.newBuilder();
            perfilBuilder.setNome(perfilObtido.getNome());
            perfilBuilder.setGenero(perfilObtido.getGenero());
            perfilBuilder.setDistancia(perfilObtido.getDistancia());
            perfilBuilder.setIdade(perfilObtido.getIdade());

            List<dominio.entidades.Foto> fotosEntidade = perfilObtido.getFotos();
            List<Foto> fotosgRPC = new ArrayList<>();
            for(int j = 0; j < fotosEntidade.size(); j++) {
                Foto foto = Foto.newBuilder().setMsg(fotosEntidade.get(j).getTexto()).build();
                //fotosgRPC.add(fotosEntidade)
                fotosgRPC.add(foto);
            }
            perfilBuilder.addAllFotos(fotosgRPC);
            perfilBuilder.setDescricao(perfilObtido.getDescricao());
            perfilBuilder.setEmail(perfilObtido.getEmail());

            PerfilExibir perfilExibir = perfilBuilder.build();
            responseObserver.onNext(perfilExibir);
        }
        responseObserver.onCompleted();
    }

    @Override
    public synchronized void enviarSelecao(Selecao request, StreamObserver<Resultado> responseObserver) {
        //contruir o objeto das entidades de dominio
        dominio.entidades.Selecao selecao = new dominio.entidades.Selecao(
                request.getGosto(), request.getEmailUtilizador(), request.getEmailPerfilApresentado());

        boolean result = repSel.inserir(selecao);
        Resultado resultado = Resultado.newBuilder().setResultado(result).build();
        // enviar o resultado
        responseObserver.onNext(resultado);
        responseObserver.onCompleted();

        if(result) {
            System.out.println();
            System.out.println("#############   Após adicionar Seleção  #############");
            System.out.println(repSel);
        }
    }

    @Override
    public synchronized void pedirAnuncio(Identificador request, StreamObserver<Anuncio> responseObserver) {
        // obter as idades convertido em array
        List<Integer> idadesAceitaveislista = request.getIdadesAceitaveisList();
        int[] idadesAceitaveis = new int[idadesAceitaveislista.size()];
        for(int i = 0; i < idadesAceitaveislista.size(); i++) {
            idadesAceitaveis[i] = idadesAceitaveislista.get(i);
        }

        dominio.entidades.Identificador identificador = new dominio.entidades.Identificador(
                request.getEmail(), request.getDistMaxima(), idadesAceitaveis, request.getGeneroInteresse());

        // constuir o objeto do gRPC com o objeto da entidade
        dominio.entidades.Anuncio anuncio = repAnun.obterAnuncio(identificador);
        Anuncio anunciogRPC = Anuncio.newBuilder().setTexto(anuncio.getExemplo()).build();

        // enviar o resultado
        responseObserver.onNext(anunciogRPC);
        responseObserver.onCompleted();
    }

    @Override
    public synchronized void pedirInformacaoPerfil(EmailUtlCorr request, StreamObserver<PerfilExibir> responseObserver) {
        String emailUtl = request.getEmail();
        // converter a classe de entidade para uma classe do gRPC
        dominio.entidades.PerfilExibir perfil = repUtls.obterPerfil(emailUtl);
        PerfilExibir.Builder perfilExibirBuild = PerfilExibir.newBuilder();
        perfilExibirBuild.setNome(perfil.getNome());
        perfilExibirBuild.setGenero(perfil.getGenero());
        perfilExibirBuild.setDistancia(perfil.getDistancia());
        perfilExibirBuild.setIdade(perfil.getIdade());
        List<Foto> fotos = new ArrayList<>();
        for(dominio.entidades.Foto foto : perfil.getFotos()) {
            Foto fotogRPC = Foto.newBuilder().setMsg(foto.getTexto()).build();
            fotos.add(fotogRPC);
        }
        perfilExibirBuild.addAllFotos(fotos);
        perfilExibirBuild.setDescricao(perfil.getDescricao());
        perfilExibirBuild.setEmail(perfil.getEmail());

        PerfilExibir perfilExibir = perfilExibirBuild.build();
        // envia a classe obtida
        responseObserver.onNext(perfilExibir);
        responseObserver.onCompleted();
    }

    // este metodo não corresponde à verdadeira verificação de distancia
    // teria de se utilizar coordenadas geograficas
    // mas como não se tem bem a certeza do formato dos dados e calculo
    // das distancias
    private boolean utilizadorAceitavel(dominio.entidades.Identificador identificador,
                                        dominio.entidades.PerfilExibir perfil) {
        boolean resultadoDist = identificador.getDistMaxima() >= perfil.getDistancia();
        boolean resultadoGenero = identificador.getGeneroInteresse().equals(perfil.getGenero());
        boolean resultadoIdade = identificador.getIdadesAceitaveis()[0] <= perfil.getIdade() &&
                identificador.getIdadesAceitaveis()[1] >= perfil.getIdade();

        /*System.out.println("Resultado distancia: "+resultadoDist);
        System.out.println("Resultado genero: "+resultadoGenero);
        System.out.println("Resultado idade: "+resultadoIdade);*/
        return resultadoDist && resultadoGenero && resultadoIdade;

    }

    private dominio.entidades.PerfilExibir obterPerfilAleatorio(dominio.entidades.Identificador identificador) {
        while(true) {
            dominio.entidades.PerfilExibir perfil = repUtls.obterPerfilAleatorio(identificador);
            // é null quando não existem mais perfis para exibir
            if(perfil == null) break;
            boolean aceitavel = utilizadorAceitavel(identificador, perfil);
            boolean comSelecao = repSel.selecaoAtribuida(identificador, perfil);
            if (aceitavel && !comSelecao) return perfil;
        }
        return null;
    }


}
