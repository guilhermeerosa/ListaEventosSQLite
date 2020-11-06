package com.example.heimer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.example.heimer.Modelo.Evento;
import com.example.heimer.database.EventoDAO;

public class MainActivity extends AppCompatActivity {

    private ListView lvEventos;
    private ArrayAdapter<Evento> adapterEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Próximos Eventos");
        lvEventos = findViewById(R.id.lvEvento);
        definirOnclickListenerListView();
    }

    private void definirOnclickListenerListView(){
        lvEventos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Evento eventoClicado = adapterEvento.getItem(position);
                Intent intent = new Intent(MainActivity.this, CadastroEvento.class);
                intent.putExtra("eventoEdicao", eventoClicado);
                intent.putExtra("eventoExcluir", eventoClicado);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventoDAO eventoDAO = new EventoDAO(getBaseContext());
        adapterEvento = new ArrayAdapter<Evento>(MainActivity.this, android.R.layout.simple_list_item_1, eventoDAO.listar());
        lvEventos.setAdapter(adapterEvento);
    }

    public void onClickCadastrarEvento(View v){
        Intent intent = new Intent(MainActivity.this, CadastroEvento.class);
        startActivity(intent);
    }

    public void onClickListarLocais(View v){
        Intent intent = new Intent(MainActivity.this, ListarLocal.class);
        startActivity(intent);
        finish();
    }
}