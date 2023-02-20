package apresentacao.perfilCorrespondencia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import apresentacao.R;
import apresentacao.principal.VistaPrincipal;
import classificacaoPerfil.Classificacao;
import servicoApresentacao.PerfilExibir;

public class VistaPerfiCorrespondencia extends AppCompatActivity {

    private ViewModelPerfilCorrespondencia viewModel;

    private String enderecoIP;
    private int porto1;
    private int porto2;
    private String email;
    private String emailCorrs;

    private ImageView img;
    private TextView nome;
    private TextView descricao;
    private EditText classificacao;
    private Button submeter;

    private PerfilExibir perfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_perfil_correspondencia);

        img = findViewById(R.id.foto);
        img.setImageResource(R.drawable.avatar);
        nome = findViewById(R.id.nome);
        descricao = findViewById(R.id.descricao);
        classificacao = findViewById(R.id.classif);
        submeter = findViewById(R.id.submeter);

        Bundle extras = getIntent().getExtras();

        if(extras != null){
            enderecoIP = extras.getString("enderecoIP");
            porto1 = extras.getInt("porto1");
            porto2 = extras.getInt("porto2");
            email = extras.getString("email");
            emailCorrs = extras.getString("emailCorrs");
        }

        viewModel = new ViewModelPerfilCorrespondencia(enderecoIP, porto1, enderecoIP, porto2);

        //perfil = pedirInformacaoPerfil(emailCorrs);
        //exibirPerfilUtilizadorSelecionado();

        enviarClassificacao();

    }

    public PerfilExibir pedirInformacaoPerfil(String emailUtl) {
        PerfilExibir perfil = viewModel.pedirInformacaoPerfil(emailUtl);
        return perfil;
    }

    public void enviarClassificacao() {
        submeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valorCLassf = Integer.parseInt(classificacao.getText().toString());
                if(valorCLassf < 0 || valorCLassf > 100) {
                    Toast.makeText(VistaPerfiCorrespondencia.this, "O valor tem de estar entre 0 e 100",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    exibirClassificacao();
                    Classificacao classificacao = Classificacao.newBuilder().setEmailUtl(email)
                            .setEmailUtlCorr(emailCorrs).setClassificao(valorCLassf).build();
                    //viewModel.enviarClassificacao(classificacao);
                    Toast.makeText(VistaPerfiCorrespondencia.this, "Classificação submetida com successo",
                            Toast.LENGTH_LONG).show();

                }
            }

        });
    }

    public void exibirPerfilUtilizadorSelecionado() {
        String texto = perfil.getNome()+", "+perfil.getIdade()+", "+perfil.getDistancia();
        nome.setText(texto);
        descricao.setText(perfil.getDescricao());
    }

    public void exibirClassificacao() {
        classificacao.setEnabled(false);
        submeter.setEnabled(false);
    }

}