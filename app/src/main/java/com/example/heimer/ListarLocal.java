package com.example.heimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.heimer.Modelo.Local;
import com.example.heimer.database.LocalDAO;

public class ListarLocal extends AppCompatActivity {

    private ListView lvLocal;
    private ArrayAdapter<Local> adapterLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_local);
        setTitle("Locais Cadastrados");
        lvLocal = findViewById(R.id.lvLocal);
        definirOnclickListenerListViewLocal();
    }

    private void definirOnclickListenerListViewLocal(){
        lvLocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Local localClicado = adapterLocal.getItem(position);
                Intent intent = new Intent(ListarLocal.this, CadastroLocal.class);
                intent.putExtra("localEditar", localClicado);
                intent.putExtra("localExcluir", localClicado);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalDAO localDAO = new LocalDAO(getBaseContext());
        adapterLocal = new ArrayAdapter<Local>(ListarLocal.this, android.R.layout.simple_list_item_1, localDAO.listar());
        lvLocal.setAdapter(adapterLocal);
    }

    public void onClickCadastrarLocal(View v){
        Intent intent = new Intent(ListarLocal.this, CadastroLocal.class);
        startActivity(intent);
    }

    public void onClickVoltarLocal(View v){
        Intent intent = new Intent(ListarLocal.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}