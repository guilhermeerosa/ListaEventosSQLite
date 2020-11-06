package com.example.heimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.heimer.Modelo.Evento;
import com.example.heimer.Modelo.Local;
import com.example.heimer.database.EventoDAO;
import com.example.heimer.database.LocalDAO;

public class CadastroEvento extends AppCompatActivity {

    private int id = 0;
    private Spinner spinnerLocais;
    private ArrayAdapter<Local> locaisAdapter;
    private EditText editTextNome;
    private EditText editTextData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_evento);
        setTitle("Cadastro de Eventos");
        spinnerLocais = findViewById(R.id.spinner_locais);
        editTextNome = findViewById(R.id.editText_nome);
        editTextData = findViewById(R.id.editText_data);

        carregarLocais();
        carregarEvento();
    }

    private void carregarLocais(){
        LocalDAO localDAO = new LocalDAO(getBaseContext());
        locaisAdapter = new ArrayAdapter<Local>(CadastroEvento.this,
                android.R.layout.simple_spinner_item,
                localDAO.listar());
        spinnerLocais.setAdapter(locaisAdapter);
    }

    private void carregarEvento(){
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null &&
                intent.getExtras().get("eventoEdicao") != null){
            Evento evento = (Evento) intent.getExtras().get("eventoEdicao");
            editTextNome.setText(evento.getNome());
            editTextData.setText(evento.getData());
            int posicaoEvento = obterPosicaoLocal(evento.getLocal());
            spinnerLocais.setSelection(posicaoEvento);
            id = evento.getId();
        }
    }

    private int obterPosicaoLocal(Local local){
        for(int posicao = 0; posicao < locaisAdapter.getCount(); posicao++){
            if(locaisAdapter.getItem(posicao).getId() == local.getId()){
                return posicao;
            }
        }
        return 0;
    }

    public void onClickVoltar(View v){
        finish();
    }

    public void onClickCadastrar(View v){
        String nome = editTextNome.getText().toString();
        String data = editTextData.getText().toString();
        int posicaoLocal = spinnerLocais.getSelectedItemPosition();
        Local local = (Local) locaisAdapter.getItem(posicaoLocal);
        Evento evento = new Evento(id, nome, data, local);
        EventoDAO eventoDAO = new EventoDAO(getBaseContext());
        boolean salvou = eventoDAO.salvar(evento);
        if(salvou){
            finish();
        }else{
            Toast.makeText(CadastroEvento.this, "Erro ao salvar, tente mais tarde", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public void onClickExcluir(View v){
        Intent intent = getIntent();
        if(intent != null && intent.getExtras() != null && intent.getExtras().getSerializable("eventoExcluir") != null){
            Evento evento = (Evento) intent.getExtras().getSerializable("eventoExcluir");
            EventoDAO eventoDAO = new EventoDAO(getBaseContext());
            eventoDAO.excluir(evento);
            finish();
        }else{
            Toast.makeText(CadastroEvento.this, "Item não criado, ímpossivel Excluir.", Toast.LENGTH_LONG).show();
        }
    }
}