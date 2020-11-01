package com.example.contatos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InterfaceFragment,InterfaceRecycler,InterfaceEdit,InterfaceFragEdit{
    MaterialToolbar toolbar;
    MaterialTextView txvNotFound;
    RecyclerView rv;
    AdapterRecycler adapterRecycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txvNotFound = findViewById(R.id.notFound);
        adapterRecycler = new AdapterRecycler(MainActivity.this,this);

        rv = findViewById(R.id.recycler_view);
        rv.setAdapter(adapterRecycler);

        semContatos();


        toolbar = findViewById(R.id.toolbar);
        
        MenuItem item = toolbar.getMenu().findItem(R.id.pesquisa);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Pesquisar contato");
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterRecycler.getFilter().filter(newText);
                return true;
            }
        });
       toolbar.setOnMenuItemClickListener(item1 -> {
           MenuItem star = toolbar.getMenu().findItem(R.id.star);
           if(item1.getItemId() == star.getItemId() && !star.isChecked()){
               toolbar.setTitle("Favoritos");
               star.setChecked(true);
               adapterRecycler.ListarFavoritos();
               semContatos();
               star.setIcon(ContextCompat.getDrawable(MainActivity.this,R.drawable.ic_baseline_star_24));
           }else{
               toolbar.setTitle("Contatos");
               star.setChecked(false);
               adapterRecycler.ListarContatos();
               semContatos();
               star.setIcon(ContextCompat.getDrawable(MainActivity.this,R.drawable.ic_baseline_star_border_24));
           }
           if(item1.getItemId() == R.id.comoUsar){
               alertMenu("Como usar?                         ",getString(R.string.txtHowUse)).setPositiveButton("Quero tentar!",
                       (dialog, which) -> {
                   chamarFragment();
               }).setNeutralButton("OK!", (dialog, which) -> dialog.dismiss()).show();
           }
           if(item1.getItemId() == R.id.info){
               alertMenu("O que é?                         ",getString(R.string.txtInfo)).setPositiveButton("OK!",
                       (dialog, which) -> {
                       }).show();
           }
            if(item1.getItemId() == R.id.config){
               alertMenu("Configurações                         ","EM BREVE...").setPositiveButton("OK!",
                       (dialog, which) -> {
                       }).show();
            }

           return true;
       });
        findViewById(R.id.fab).setOnClickListener(v->{
            chamarFragment();
        });

    }

    public AlertDialog.Builder alertMenu(String title,String texto){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_NoActionBar);
        builder.setTitle(title);
        builder.setMessage(texto);
        return builder;
    }

    public void chamarFragment(){
        FragContato fragContato = new FragContato(this);
        fragContato.show(getSupportFragmentManager(),fragContato.toString());
    }

    public void semContatos(){
        if(adapterRecycler.getItemCount() == 0){
            rv.setVisibility(View.GONE);
            txvNotFound.setVisibility(View.VISIBLE);
        }else{
            txvNotFound.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void inserirContato(Contato contato) {
        adapterRecycler.adionarContato(contato);
        semContatos();
    }

    @Override
    public void onClickItem(Contato contato, int position, int idContato) {
        FragClick fragClick = new FragClick(contato,this,position,idContato);
        fragClick.show(getSupportFragmentManager(), "Fragment Click");
    }
    @Override
    public void onLongClickItem(int position) {
        //era pra ser uma menu action mode aqui pra deletar e favoritar multiplos contatos
    }

    @Override
    public void deletar(Contato contato,int pos) {
        adapterRecycler.deletarContato(contato,pos);
        semContatos();
    }

    @Override
    public void update(Contato contato,int idContato,int pos) {
        FragEdit edit = new FragEdit(contato,idContato,pos, this);
        edit.show(getSupportFragmentManager(), edit.toString());
    }
    @Override
    public void updateContato(Contato c, int pos) {
        adapterRecycler.updatedContato(c,pos);
    }
}