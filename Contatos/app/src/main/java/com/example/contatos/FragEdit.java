package com.example.contatos;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class FragEdit extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    MaterialToolbar toolbar;
    TextInputEditText edtName;
    TextInputEditText edtEmail;
    TextInputEditText edtEmpresa;
    RelativeLayout relativeLayout;
    TextInputEditText edtFixo;
    TextInputEditText edtSobrenome;
    TextInputEditText edtAniversario;
    AutoCompleteTextView edtGenero;
    SimpleDateFormat dateFormat;
    TextInputEditText edtNumber;
    Calendar c;
    Contato contato;
    InterfaceFragEdit interfaceFragEdit;
    int position,idContato;
    public FragEdit(Contato contato,int idContato,int position, InterfaceFragEdit interfaceFragEdit){
        this.contato = contato;
        this.idContato = idContato;
        this.position = position;
        this.interfaceFragEdit = interfaceFragEdit;
    }
    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen,android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_edit,container,false);

        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v2->{
            onDestroy();
            onDestroyView();
            onDetach();
        });
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        edtName = view.findViewById(R.id.edtNome);
        edtNumber = view.findViewById(R.id.edtTelefone);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtAniversario = view.findViewById(R.id.edtAniversario);
        edtEmpresa = view.findViewById(R.id.edtEmpresa);
        edtGenero = view.findViewById(R.id.edtGender);
        edtFixo = view.findViewById(R.id.edtFixo);
        relativeLayout = view.findViewById(R.id.RelativeMain);
        edtSobrenome = view.findViewById(R.id.edtSobrenome);

        edtName.setText(contato.getPrimeiroNome());
        edtAniversario.setText(contato.getDtAniversario());
        edtEmail.setText(contato.getEmail());
        edtEmpresa.setText(contato.getEmpresa());
        edtFixo.setText(contato.getTelefoneFixo());
        edtNumber.setText(contato.getTelefoneCelular());
        edtGenero.setText(contato.getGenero());
        edtSobrenome.setText(contato.getSegundoNome());

        edtNumber.addTextChangedListener(Mask.insert("(##)#####-####",edtNumber));
        edtFixo.addTextChangedListener(Mask.insert("(##)####-####",edtFixo));
        edtAniversario.setFocusableInTouchMode(false);
        edtAniversario.setOnClickListener(v2->{
            c = Calendar.getInstance(TimeZone.getDefault(),new Locale("pt", "BR"));
            DatePickerDialog builder = new DatePickerDialog(getContext(),R.style.Theme_myDatePicker,this,
                    c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH));
            builder.getDatePicker().setMaxDate(c.getTimeInMillis());
            builder.show();
        });

        toolbar.setOnMenuItemClickListener(v2->{
            if(verificarNome() == 1 && verificarTelefone() == 1 && verificarFixo() == 1){
                editar();
            }
            return true;
        });
        view.findViewById(R.id.btnEditar).setOnClickListener(v2->{
            if(verificarNome() == 1 && verificarTelefone() == 1 && verificarFixo() == 1){
                editar();
            }
        });

        return view;
    }

    public void editar(){
        String nome="",telefone ="",fixo="",email="",genero="",aniversario="",sobrenome="",empresa="";
        nome = edtName.getText().toString();
        telefone = edtNumber.getText().toString();
        fixo = edtFixo.getText().toString();
        email = edtEmail.getText().toString();
        empresa = edtEmpresa.getText().toString();
        genero = edtGenero.getText().toString();
        aniversario = edtAniversario.getText().toString();
        sobrenome = edtSobrenome.getText().toString();
        contato.setPrimeiroNome(nome);
        contato.setTelefoneCelular(telefone);
        contato.setTelefoneFixo(fixo);
        contato.setEmpresa(empresa);
        contato.setEmail(email);
        contato.setGenero(genero);
        contato.setDtAniversario(aniversario);
        contato.setSegundoNome(sobrenome);
        interfaceFragEdit.updateContato(contato,position);
        onDestroy();
        onDestroyView();
        onDetach();
        Toast.makeText(getContext(),"Contato atualizado.",Toast.LENGTH_SHORT).show();
    }

    public int  verificarNome(){
        if(edtName.getText().toString().trim().isEmpty()){
            edtName.setError("Campo obrigatório!");
            return 0;
        }return 1;
    }

    public int verificarFixo(){
        if(edtFixo.getText().length() < 10 && edtFixo.getText().length() > 0){
            edtFixo.setError("Selecione um telefone válido");
            return 0;
        }return 1;
    }

    public int  verificarTelefone(){
        if(edtNumber.getText().toString().trim().isEmpty()){
            edtNumber.setError("Campo obrigatório!");
            return 0;
        }else if(edtNumber.getText().toString().trim().length() < 11){
            edtNumber.setError("Preencha um telefone válido");
            return -1;
        }
        return 1;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        c.set(year,month,dayOfMonth);
        edtAniversario.setText(dateFormat.format(c.getTimeInMillis()));
    }
}
