package com.example.easilyapp;

public class ProfessorClass {

    private String nomeCompleto;
    private int matricula;
    private String eMail;
    private String senha;

    public ProfessorClass(String nomeCompleto, int matricula, String eMail, String senha) {
        this.nomeCompleto = nomeCompleto;
        this.matricula = matricula;
        this.eMail = eMail;
        this.senha = senha;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
