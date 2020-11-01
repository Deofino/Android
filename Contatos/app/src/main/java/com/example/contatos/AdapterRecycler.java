package com.example.contatos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> implements Filterable{
    Random r;
    private int color[] = {R.color.IconYellow,R.color.IconRed,R.color.IconPurple,R.color.IconPink,
            R.color.IconOrange,R.color.IconGreen,R.color.IconBlue};
    private final Context context;
    private final List<Contato> contatoes;
    private final InterfaceRecycler interfaceRecycler;
    private final List<Contato> allcontatoes;
    private final ContatoDAO dao;
    public AdapterRecycler(Context context, InterfaceRecycler interfaceRecycler){
        dao = new ContatoDAO(context);
        this.contatoes = dao.ListarContatos();
        this.interfaceRecycler = interfaceRecycler;
        this.allcontatoes = new ArrayList<>(dao.ListarContatos());
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item,parent,false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        r = new Random();
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(ContextCompat.getColor(context,color[r.nextInt(color.length)]));
        drawable.setStroke(3, ContextCompat.getColor(context,R.color.Black));
        holder.icon.setBackground(drawable);
        holder.nome.setText(allcontatoes.get(position).getPrimeiroNome() + " "+ allcontatoes.get(position).getSegundoNome());
        holder.telefone.setText(allcontatoes.get(position).getTelefoneCelular());
        holder.icon.setText(holder.nome.getText().toString().substring(0,1).toUpperCase());
        holder.favorito.setOnClickListener(v -> {
            if(allcontatoes.get(position).isFavorito() == '0'){
                holder.favorito.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_baseline_star_24));
                allcontatoes.get(position).setFavorito('1');
                dao.FavoritarContato(allcontatoes.get(position).getIdContato());
            }else{
                holder.favorito.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_baseline_star_border_24));
                allcontatoes.get(position).setFavorito('0');
                dao.DesfavoritarContato(allcontatoes.get(position).getIdContato());
            }
        });
        holder.relativeLayout.setOnClickListener(v -> {
            interfaceRecycler.onClickItem(allcontatoes.get(position),position,allcontatoes.get(position).getIdContato());
        });



    }

    public void adionarContato(Contato contato){
        dao.inserirContato(contato);
        contatoes.add(contato);
        allcontatoes.clear();
        allcontatoes.addAll(dao.ListarContatos());
        notifyItemInserted(getItemCount());
    }

    public void deletarContato(Contato contato,int position){
        dao.DeletarContato(contato.getIdContato());
        contatoes.remove(contato);
        allcontatoes.clear();
        allcontatoes.addAll(dao.ListarContatos());
        notifyItemRemoved(position);
    }
     public void updatedContato(Contato contato,int position){
        dao.AtualizarContato(contato);
        allcontatoes.clear();
        allcontatoes.addAll(dao.ListarContatos());
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return allcontatoes.size();
    }


    public void ListarFavoritos(){
            contatoes.clear();
            contatoes.addAll(dao.ListarFavoritos());
            notifyDataSetChanged();
    }
    public void ListarContatos(){
            contatoes.clear();
            contatoes.addAll(allcontatoes);
            notifyDataSetChanged();
    }


    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Contato> ContatosFiltrados = new ArrayList<>();
            if(constraint.toString().trim().isEmpty()){
                ContatosFiltrados.addAll(allcontatoes);
            }else{
                for (Contato c: allcontatoes) {
                    if(c.getPrimeiroNome().toUpperCase().contains(constraint.toString().toUpperCase())){
                        ContatosFiltrados.add(c);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = ContatosFiltrados;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contatoes.clear();
            contatoes.addAll((Collection<? extends Contato>) results.values);
            notifyDataSetChanged();
        }
    };


    public static class
    ViewHolder extends RecyclerView.ViewHolder {
        TextView nome,telefone,icon;
        ImageView favorito;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View v) {
            super(v);
            nome = v.findViewById(R.id.name);
            telefone = v.findViewById(R.id.telefone);
            icon = v.findViewById(R.id.icon);
            favorito = v.findViewById(R.id.favorito);
            relativeLayout = v.findViewById(R.id.mainLayout);
        }
    }
}
