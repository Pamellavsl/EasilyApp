package com.example.easilyapp;

import java.io.Serializable;

public class User {

    private String username;
    private String matricula;
    private String eMail;
    private String senha;
    private String tipoUser;

    public User(String username, String matricula, String eMail, String senha, String tipoUser) {
        this.username = username;
        this.matricula = matricula;
        this.eMail = eMail;
        this.senha = senha;
        this.tipoUser = tipoUser;
    }

    public User() {

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


    public String getTipoUser() {
        return tipoUser;
    }

    public void setTipoUser(String tipoUser) {
        this.tipoUser = tipoUser;
    }

    public static String Usuario(String matricula){
        int quantDigitosProf = 7;
        int quantDigitosAluno = 12;

        if (matricula.toCharArray().length == quantDigitosAluno) {
            return "Aluno";
        }

        else if (matricula.toCharArray().length == quantDigitosProf) {

            return "Professor";
        }

        return "Erro";
    }
}
