package com.example.heimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heimer.Modelo.Local;
import com.example.heimer.database.LocalDAO;

public class CadastroLocal extends AppCompatActivity {

    private int id = 0;
    private EditText editTextEndereco;
    private EditText editTextBairro;
    private EditText editTextCidade;
    private EditText editTextCapacidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_local);
        setTitle("Cadastro de Locais");
        editTextEndereco = findViewById(R.id.editText_endereco);
        editTextBairro = findViewById(R.id.editText_bairro);
        editTextCidade = findViewById(R.id.editText_cidade);
        editTextCapacidade = findViewById(R.id.editText_capacidade);

        carregarLocal();
    }

    private void carregarLocal() {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null &&
                intent.getExtras().get("localEditar") != null) {
            Local local = (Local) intent.getExtras().get("localEditar");
            editTextEndereco.setText(local.getEndereco());
            editTextBairro.setText(local.getBairro());
            editTextCidade.setText(local.getCidade());
            editTextCapacidade.setText(String.valueOf(local.getCapacidade()));
            id = local.getId();
        }
    }

    public void onClickVoltar(View v) {
        finish();
    }

    public void onClickCadastrar(View v) {
        String endereco = editTextEndereco.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String cidade = editTextCidade.getText().toString();
        int capacidade = Integer.parseInt(editTextCapacidade.getText().toString());
        Local local = new Local(id, endereco, bairro, cidade, capacidade);
        LocalDAO localDAO = new LocalDAO(getBaseContext());
        boolean salvou = localDAO.salvar(local);
        if (salvou) {
            finish();
        } else {
            Toast.makeText(CadastroLocal.this, "Erro ao salvar, tente mais tarde", Toast.LENGTH_LONG).show();
        }
        finish();
    }

    public void onClickExcluir(View v) {
        Intent intent = getIntent();
        if (intent != null && intent.getExtras() != null && intent.getExtras().getSerializable("localExcluir") != null) {
            Local local = (Local) intent.getExtras().getSerializable("localExcluir");
            LocalDAO localDAO = new LocalDAO(getBaseContext());
            localDAO.excluir(local);
            finish();
        } else {
            Toast.makeText(CadastroLocal.this, "Item não criado, ímpossivel Excluir.", Toast.LENGTH_LONG).show();
        }
    }
}