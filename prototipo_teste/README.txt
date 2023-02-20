Para testar o programa completo:

Executar o teste.bat 
-> Por cada teste este fica bloqueado à espera de input, 
ou seja, por cada jar com o nome começado por Teste 




Para executar cada teste individualmente:
-> executar: teste_atribuir_classificacao.bat   -> para classificar um utilizador
-> executar: teste_atribuir_gosto_nao_gosto.bat -> para obter um identificador, depois obter perfis, depois enviar seleções e por ultimo receber anuncios
-> executar: teste_responder_questionario.bat   -> para responder ao questionario da aplicação



################################

Para saber os dados iniciais da aplicação:

-> ir ao ficheiro dados_iniciais.txt


###################################


Caso de utilização 
-> Calcular melhores utilizadores 
-> Este caso de utilização é executado quando é lançado o domínio,
porque não precisa de apresentação

-> Resultado:

RepositorioRecomendacoes{recomendacoes=
Recomendacao{emailUtl1='jessica@isel.pt', emailUtl2='rita@isel.pt', prioridade=70},
Recomendacao{emailUtl1='rita@isel.pt', emailUtl2='jessica@isel.pt', prioridade=80},
Recomendacao{emailUtl1='joao@isel.pt', emailUtl2='jose@isel.pt', prioridade=60},
Recomendacao{emailUtl1='joao@isel.pt', emailUtl2='ricardo@isel.pt', prioridade=70},
Recomendacao{emailUtl1='joao@isel.pt', emailUtl2='luis@isel.pt', prioridade=80},
Recomendacao{emailUtl1='joao@isel.pt', emailUtl2='sara@isel.pt', prioridade=90},
Recomendacao{emailUtl1='jose@isel.pt', emailUtl2='joao@isel.pt', prioridade=60},
Recomendacao{emailUtl1='jose@isel.pt', emailUtl2='ricardo@isel.pt', prioridade=70},
Recomendacao{emailUtl1='jose@isel.pt', emailUtl2='luis@isel.pt', prioridade=70},
Recomendacao{emailUtl1='jose@isel.pt', emailUtl2='sara@isel.pt', prioridade=70},
Recomendacao{emailUtl1='ricardo@isel.pt', emailUtl2='joao@isel.pt', prioridade=60},
Recomendacao{emailUtl1='ricardo@isel.pt', emailUtl2='jose@isel.pt', prioridade=70},
Recomendacao{emailUtl1='ricardo@isel.pt', emailUtl2='luis@isel.pt', prioridade=70},
Recomendacao{emailUtl1='ricardo@isel.pt', emailUtl2='sara@isel.pt', prioridade=70},
}

----------------------------------------------------------

Este resultado na verdade é a soma das recomendações que já lá foram postas
mais as das classificações. Porque o algoritmo que deveria ser de aprendizagem
automática foi feito pela conversão de classificação para recomendação



#####################################


Caso de utilização
-> Atribuir pontuação a um utilizador
-> Executado quando se executa o teste.bat(1º a ser executado) ou teste_atribuir_classificacao.bat

-> Resultado:
Pedir Informação de perfil(resultado):
nome: "Sara"
genero: "femenino"
distancia: 10
idade: 23
fotos {
  msg: "Foto: 1"
}
fotos {
  msg: "Foto: 2"
}
descricao: "nao gosto de ananas"
email: "sara@isel.pt"

Classificação: emailUtl: "miguel@isel.pt", emailUtlCorr: "sara@isel.pt", classificao: 90,
Enviar classificação(resultado): true

Enviar classificação(resultado): false

--------------------------------------

-> Resultado (no domínio):
RepositorioClassificacaoUtilizdor{classificacoes=
Classificacao{emailUtl='joao@isel.pt', emailUtlCorr='jose@isel.pt', classificao=60},
Classificacao{emailUtl='joao@isel.pt', emailUtlCorr='ricardo@isel.pt', classificao=70},
Classificacao{emailUtl='joao@isel.pt', emailUtlCorr='luis@isel.pt', classificao=80},
Classificacao{emailUtl='joao@isel.pt', emailUtlCorr='sara@isel.pt', classificao=90},
Classificacao{emailUtl='jose@isel.pt', emailUtlCorr='joao@isel.pt', classificao=60},
Classificacao{emailUtl='jose@isel.pt', emailUtlCorr='ricardo@isel.pt', classificao=70},
Classificacao{emailUtl='jose@isel.pt', emailUtlCorr='luis@isel.pt', classificao=70},
Classificacao{emailUtl='jose@isel.pt', emailUtlCorr='sara@isel.pt', classificao=70},
Classificacao{emailUtl='ricardo@isel.pt', emailUtlCorr='joao@isel.pt', classificao=60},
Classificacao{emailUtl='ricardo@isel.pt', emailUtlCorr='jose@isel.pt', classificao=70},
Classificacao{emailUtl='ricardo@isel.pt', emailUtlCorr='luis@isel.pt', classificao=70},
Classificacao{emailUtl='ricardo@isel.pt', emailUtlCorr='sara@isel.pt', classificao=70},
Classificacao{emailUtl='miguel@isel.pt', emailUtlCorr='sara@isel.pt', classificao=90},
}

----------------------------------------

Este resultado é porque para atribuir uma pontuação primeiro tem de ser selecionado um perfil,
depois é selecionado um número e este é enviado.
Na primeira que é enviado, este deve aceitar porque não possui guardado esta classificação, por isso deve dar true.
N segunda é enviado a mesma classificação entre dois utilizadores, como já existe guardado, deve dar false.


No lado do domínio deve dar os valores que já possuía lá mais o resultado que deu sucesso.

#######################################



Caso de utilização
-> Atribuir gosto ou não gosto
-> Executado quando se executa o teste.bat(2º a ser executado) ou teste_atribuir_gosto_nao_gosto.bat

-> Resultado:
Dentro do ficheiro: resultado_atribuir_gosto_nao_gosto.txt


-> Resultado(dominio):
RepositorioSelecoes{selecoes=
Selecao{gosto=true, emailUtilizador='sara@isel.pt', emailPerfilApresentado='matilde@isel.pt'},
Selecao{gosto=true, emailUtilizador='sara@isel.pt', emailPerfilApresentado='beatriz@isel.pt'},
Selecao{gosto=false, emailUtilizador='matilde@isel.pt', emailPerfilApresentado='sara@isel.pt'},
Selecao{gosto=false, emailUtilizador='matilde@isel.pt', emailPerfilApresentado='beatriz@isel.pt'},
Selecao{gosto=true, emailUtilizador='beatriz@isel.pt', emailPerfilApresentado='sara@isel.pt'},
Selecao{gosto=true, emailUtilizador='beatriz@isel.pt', emailPerfilApresentado='matilde@isel.pt'},
Selecao{gosto=true, emailUtilizador='joao@isel.pt', emailPerfilApresentado='sara@isel.pt'},
Selecao{gosto=true, emailUtilizador='joao@isel.pt', emailPerfilApresentado='matilde@isel.pt'},
}


---------------------------------------------------

O resultado no teste é inicialmente obter o seu identificador, de seguida pedir  utilizadores que envia 10 utilizadores,
com o genero de interesse correspondente ao seu identificador, idade entre o intervalo no identificador e distancia inferior à distMaxima.
Para testar o correto funcionamento são chamadas 2 vezes onde a segunda possui menos do que 10 utilizadores no seu interior.

De seguida são enviados três seleções de um utilizador para, dos perfis obtidos. Os dois primeiros são perfis que não possuem seleção
então o seu resultado deve ser true. No terceiro é repetido o primeiro envio e o resultado deve dar false.

Por fim são pedidos anuncios, onde no total só existem 4 anuncios e por isso quando chega ao último anuncio deve recomeçar.
Por isso a partir do quarto anuncio devem ser repetidos.


Em relação ao domínio este deve possuir as antigas seleções mais as duas novas seleções atribuídas.

####################################################




Caso de utilização
-> Responder ao questionário
-> Executado quando se executa o teste.bat(3º a ser executado) ou teste_responder_questionario.bat

-> Resultado:
Questionario: emailUtl: "joao@isel.pt", respostas: 3, respostas: 2, respostas: 1, respostas: 3, respostas: 3, respostas: 3,
Enviar questionario(resultado): true


Questionario: emailUtl: "joao@isel.pt", respostas: 3, respostas: 2, respostas: 1, respostas: 3, respostas: 3, respostas: 3,
Enviar questionario(resultado): false


-> Resultado(dominio):
RepositorioQuestionarios{questionarios=
RespostasQuestionario{emailUtl='ricardo@isel.pt', respostas=[1, 1, 3, 6, 1]},
RespostasQuestionario{emailUtl='luis@isel.pt', respostas=[4, 5, 6, 2, 1]},
RespostasQuestionario{emailUtl='joao@isel.pt', respostas=[3, 2, 1, 3, 3, 3]},
}

-------------------------------------------------

O resultado deve ser primeiro true porque ainda não existe nenhum questionário submetido pelo utilizador com email joao@isel.pt
o segundo resultado deve ser false, porque se tenta enviar nomanente o mesmo questionario e como já existe deve dar false.

No dominio deve ser adicionado o questionario enviado pelo joao@isel.pt
