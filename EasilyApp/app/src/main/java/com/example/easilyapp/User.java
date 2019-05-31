package com.example.easilyapp;

import java.io.Serializable;

public class User {

    private String username;
    private String matricula;
    private String eMail;
    private String senha;
    private final int quantDigitosProf = 7;
    private final int quantDigitosAluno = 12;
    private String tipoUser;


    public User(String username, String matricula, String eMail, String senha) {
        this.username = username;
        this.matricula = matricula;
        this.eMail = eMail;
        this.senha = senha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void tipoUser(String tipoUser) {
        this.tipoUser = tipoUser;

    }
}
