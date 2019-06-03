package com.example.easilyapp;

public class Student {

    private String matricula;
    private String nome;

    public Student(String matricula, String nome) {
        this.matricula = matricula;
        this.nome = nome;
    }

    public Student() {
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
