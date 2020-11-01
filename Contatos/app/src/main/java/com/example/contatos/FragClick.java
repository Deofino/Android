package com.example.contatos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textview.MaterialTextView;

public class FragClick extends DialogFragment{
    private final Contato contato;
    private final InterfaceEdit interfaceEdit;
    private final int postition;
    private final int idContato;
    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setStyle(STYLE_NO_TITLE,android.R.style.Theme_DeviceDefault_Light_Dialog_Alert);
    }

    public FragClick(Contato contato,InterfaceEdit interfaceEdit,int postition,int idContato) {
        this.contato = contato;
        this.idContato = idContato;
        this.postition = postition;
        this.interfaceEdit = interfaceEdit;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.frag_click,container,false);
        MaterialTextView nome_e_sobrenome,numeros_de_telefone,email,empresa_ou_escola,aniversario,genero;
        nome_e_sobrenome = v.findViewById(R.id.nameLastName);
        email = v.findViewById(R.id.email);
        numeros_de_telefone = v.findViewById(R.id.numberFix);
        empresa_ou_escola = v.findViewById(R.id.empresa);
        aniversario = v.findViewById(R.id.aniversario);
        genero = v.findViewById(R.id.genero);

        if(contato.getEmail().trim().equals(""))email.setVisibility(View.GONE);
        if(contato.getGenero().trim().equals(""))genero.setVisibility(View.GONE);
        if(contato.getEmpresa().trim().equals(""))empresa_ou_escola.setVisibility(View.GONE);
        if(contato.getDtAniversario().trim().equals(""))aniversario.setVisibility(View.GONE);


        nome_e_sobrenome.setText(this.contato.getPrimeiroNome()+ " "+ this.contato.getSegundoNome());
        numeros_de_telefone.setText(this.contato.getTelefoneCelular() + "   "+ this.contato.getTelefoneFixo());
        email.setText(this.contato.getEmail());
        empresa_ou_escola.setText(this.contato.getEmpresa());
        aniversario.setText(this.contato.getDtAniversario());
        genero.setText(this.contato.getGenero());

        v.findViewById(R.id.delete).setOnClickListener(v1 -> {
            alertDelete();
        });
        v.findViewById(R.id.update).setOnClickListener(v1 -> {
            alertUpdate();
        });
        return v;
    }

    private void alertUpdate() {
        interfaceEdit.update(contato,idContato,postition);
        onDestroy();
        onDestroyView();
        onDetach();
    }

    public void alertDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth);
        builder.setMessage("Deseja mesmo excluir este contato?");
        builder.setTitle("Confirmação");
        builder.setIcon(R.drawable.ic_baseline_error_24);
        builder.setPositiveButton("Sim", (dialog, which) -> {
            interfaceEdit.deletar(contato,postition);
            onDestroy();
            onDestroyView();
            onDetach();
            Toast.makeText(getContext(),"Contato excluido.",Toast.LENGTH_SHORT).show();

        });
        builder.setNegativeButton("Não", (dialog, which) -> dialog.cancel()).show();
    }

}
