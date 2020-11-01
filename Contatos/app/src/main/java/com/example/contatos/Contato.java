package com.example.contatos;

public class Contato {

    private String PrimeiroNome,SegundoNome,TelefoneCelular,TelefoneFixo,Empresa,Genero,Email,dtAniversario;
    private boolean Selecionado;

    public Contato(){}

    private int idContato;
    private char isFavorito;

    public static final String table = "tbContato";
    public static final String id = "idContato";
    public static final String nome1 = "nomeContato";
    public static final String nome2 = "sobrenomeContato";
    public static final String tel1 = "telefoneContato";
    public static final String tel2 = "telefoneFixoContato";
    public static final String empresa = "empresaContato";
    public static final String genero = "generoContato";
    public static final String email = "emailContato";
    public static final String aniversario = "dtAniversarioContato";
    public static final String favorito = "isFavorito";

    public Contato(String primeiroNome, String telefoneCelular) {
        PrimeiroNome = primeiroNome;
        TelefoneCelular = telefoneCelular;
        isFavorito = '0';
        Selecionado = false;
    }

    public String getPrimeiroNome() {
        return PrimeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        PrimeiroNome = primeiroNome;
    }

    public String getSegundoNome() {
        return SegundoNome;
    }

    public void setSegundoNome(String segundoNome) {
        SegundoNome = segundoNome;
    }

    public String getTelefoneCelular() {
        return TelefoneCelular;
    }

    public void setTelefoneCelular(String telefoneCelular) {
        TelefoneCelular = telefoneCelular;
    }

    public String getTelefoneFixo() {
        return TelefoneFixo;
    }

    public void setTelefoneFixo(String telefoneFixo) {
        TelefoneFixo = telefoneFixo;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String empresa) {
        Empresa = empresa;
    }

    public String getGenero() {
        return Genero;
    }

    public void setGenero(String genero) {
        Genero = genero;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getDtAniversario() {
        return dtAniversario;
    }

    public void setDtAniversario(String dtAniversario) {
        this.dtAniversario = dtAniversario;
    }

    public boolean isSelecionado() {
        return Selecionado;
    }

    public void setSelecionado(boolean selecionado) {
        Selecionado = selecionado;
    }

    public char isFavorito() {
        return isFavorito;
    }

    public void setFavorito(char favorito) {
        isFavorito = favorito;
    }

    public int getIdContato() {
        return idContato;
    }

    public void setIdContato(int idContato) {
        this.idContato = idContato;
    }


}
