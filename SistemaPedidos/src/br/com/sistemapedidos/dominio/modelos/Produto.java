/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.dominio.modelos;

import java.util.ArrayList;

/**
 *
 * @author raphael
 */
public class Produto {
    private int id;
    private String descricao;
    private ArrayList<String> erros;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public ArrayList<String> getErros(){
        return this.erros;
    }
    
    public boolean valido(){
        erros = new ArrayList<>();
        if(this.descricao.length() == 10){
            erros.add("A descrção do produto deve ter pelo menos 10 caracteres.");
        }
        
        return erros.isEmpty();
    }
    
}
