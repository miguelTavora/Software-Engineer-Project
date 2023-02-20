package testeGosto;

import servicoApresentacao.*;

import java.util.List;

public class TesteAtribuirGostoNaoGosto {

    private static final String ENDERECO_IP_SERVIDOR_APRESENTACAO = "localhost";
    private static final int PORTO_SERVER_APRESENTACAO = 8000;

    private static final String emailExemplo1 = "joao@isel.pt";
    private static final String nomeExemplo1 = "João";
    private static final String localizacaoExemplo1 = "Lisboa";

    private static ViewModelExibicaoPessoas viewModelExibicaoPessoas;


    public static void main(String[] args) {
        teste1();

    }

    // teste realizado
    public static void teste1() {
        System.out.println("#### Teste 1 ao domínio -> Atribuir gosto e não gosto ####");

        viewModelExibicaoPessoas = new ViewModelExibicaoPessoas(ENDERECO_IP_SERVIDOR_APRESENTACAO,
                PORTO_SERVER_APRESENTACAO);

        LocalizacaoUtilizador loc = LocalizacaoUtilizador.newBuilder()
                .setNome(nomeExemplo1).setEmail(emailExemplo1).setLocalizacao(localizacaoExemplo1).build();

        // obter o idenficador
        //email='joao@isel.pt',
        // distMaxima=100,
        // idadesAceitaveis=[18, 50],
        // generoInteresse='femenino'
        Identificador ident = viewModelExibicaoPessoas.enviarLocalizacao(loc);
        System.out.println("Enviar lozalização(resultado): ");
        System.out.println(ident);

        // obter os perfis
        // nome='Sara',
        // genero='femenino',
        // distancia=10,
        // idade=23,
        // fotos=[Foto{texto='Foto: 1'},
        // Foto{texto='Foto: 2'}],
        // descricao='nao gosto de ananas',
        // email='sara@isel.pt'
        // nome='Matilde',
        // genero='femenino',
        // distancia=20, idade=30,
        // fotos=[Foto{texto='Foto: 1'},
        // Foto{texto='Foto: 2'}],
        // descricao='nao gosto de bananas',
        // email='matilde@isel.pt'
        // nome='Beatriz',
        // genero='femenino',
        // distancia=50,
        // idade=18,
        // fotos=[Foto{texto='Foto: 1'},
        // Foto{texto='Foto: 2'}],
        // descricao='nao gosto de estudar',
        // email='beatriz@isel.pt'
        // nome='Mafalda',
        // genero='femenino',
        // distancia=100,
        // idade=23,
        // fotos=[Foto{texto='Foto: 1'},
        // Foto{texto='Foto: 2'}],
        // descricao='nao gosto de ananas',
        // email='mafalda@isel.pt'
        // nome='Carolina',
        // genero='femenino',
        // distancia=20,
        // idade=18,
        // fotos=[Foto{texto='Foto: 1'},
        // Foto{texto='Foto: 2'}],
        // descricao='nao gosto de bananas',
        // email='carolina@isel.pt'
        // nome='Catarina',
        // genero='femenino',
        // distancia=50,
        // idade=23,
        // fotos=[Foto{texto='Foto: 1'},
        // Foto{texto='Foto: 2'}],
        // descricao='nao gosto de estudar',
        // email='catarina@isel.pt'
        // nome='Jessica',
        // genero='femenino',
        // distancia=100,
        // idade=30,
        // fotos=[Foto{texto='Foto: 1'},
        // Foto{texto='Foto: 2'}],
        // descricao='nao gosto de ananas',
        // email='jessica@isel.pt'
        // nome='Rita',
        // genero='femenino',
        // distancia=10,
        // idade=18,
        // fotos=[Foto{texto='Foto: 1'},
        // Foto{texto='Foto: 2'}],
        // descricao='nao gosto de bananas',
        // email='rita@isel.pt'
        // nome='Ana',
        // genero='femenino',
        // distancia=10,
        // idade=23,
        // fotos=[Foto{texto='Foto: 1'},
        // Foto{texto='Foto: 2'}],
        // descricao='nao gosto de estudar',
        // email='ana@isel.pt'
        // nome='Joana',
        // genero='femenino',
        // distancia=50,
        // idade=18,
        // fotos=[Foto{texto='Foto: 1'},
        // Foto{texto='Foto: 2'}],
        // descricao='nao gosto de bananas',
        // email='joana@isel.pt'
        List<PerfilExibir> perfis = viewModelExibicaoPessoas.pedirUtilizadores(ident);
        System.out.println("Pedir utilizadores(resultado): "+perfis);
        System.out.println("\n");


        // nome='Marina',
        // genero='femenino',
        // distancia=10,
        // idade=30,
        // fotos=[Foto{texto='Foto: 1'},
        // Foto{texto='Foto: 2'}],
        // descricao='nao gosto de ananas',
        // email='marina@isel.pt'
        List<PerfilExibir> perfis2 = viewModelExibicaoPessoas.pedirUtilizadores(ident);
        System.out.println("Pedir utilizadores(resultado): "+perfis2);

        try {
            // envio do primeiro perfil obtido
            // resultado -> true
            Selecao selecao = Selecao.newBuilder().setGosto(true).setEmailUtilizador(emailExemplo1)
                    .setEmailPerfilApresentado(perfis.get(0).getEmail()).build();
            System.out.println("Seleção: " + selecao.toString().replace("\n", ", "));
            boolean resultado = viewModelExibicaoPessoas.enviarSelecao(selecao);
            System.out.println("Enviar Seleção(resultado): " + resultado+"\n");

            // envio do segundo perfil obtido
            // resultado -> true
            Selecao selecao2 = Selecao.newBuilder().setGosto(true).setEmailUtilizador(emailExemplo1)
                    .setEmailPerfilApresentado(perfis.get(1).getEmail()).build();
            System.out.println("Seleção: " + selecao2.toString().replace("\n", ", "));
            boolean resultado2 = viewModelExibicaoPessoas.enviarSelecao(selecao2);
            System.out.println("Enviar Seleção(resultado): " + resultado2 + "\n");

            // envio do segundo perfil obtido
            // resultado -> false
            System.out.println("Seleção: " + selecao.toString().replace("\n", ", "));
            boolean resultado3 = viewModelExibicaoPessoas.enviarSelecao(selecao);
            System.out.println("Repetir Enviar Seleção(resultado): " + resultado3);
            System.out.println();

        } catch (Exception e) {
            System.out.println("Reiniciar servidor, para testar");
        }



        // obter anuncio
        // resultado -> 'Anuncio exclusivo! Não perca já 5 telemoveis pelo preço de 4!!!'
        Anuncio anuncio = viewModelExibicaoPessoas.pedirAnuncio(ident);
        System.out.println("Pedir anuncio(resultado): "+anuncio.getTexto());

        // obter anuncio
        // resultado -> 'Anuncio exclusivo! Novo aspirador, aspira tudo por metade do preço dos outros!!!'
        Anuncio anuncio2 = viewModelExibicaoPessoas.pedirAnuncio(ident);
        System.out.println("Pedir anuncio(resultado): "+anuncio2.getTexto());

        // resultado -> 'Anuncio novo!! Computador novo por somente 500 euros, não perca já!!!!'
        Anuncio anuncio3 = viewModelExibicaoPessoas.pedirAnuncio(ident);
        System.out.println("Pedir anuncio(resultado): "+anuncio3.getTexto());

        // resultado -> 'Ultimo anuncio!!!'
        Anuncio anuncio4 = viewModelExibicaoPessoas.pedirAnuncio(ident);
        System.out.println("Pedir anuncio(resultado): "+anuncio4.getTexto());

        // resultado -> 'Anuncio exclusivo! Não perca já 5 telemoveis pelo preço de 4!!!'
        Anuncio anuncio5 = viewModelExibicaoPessoas.pedirAnuncio(ident);
        System.out.println("Pedir anuncio(resultado): "+anuncio5.getTexto());

        // resultado -> 'Anuncio exclusivo! Novo aspirador, aspira tudo por metade do preço dos outros!!!'
        Anuncio anuncio6 = viewModelExibicaoPessoas.pedirAnuncio(ident);
        System.out.println("Pedir anuncio(resultado): "+anuncio6.getTexto());
    }

}
