/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.dominio.modelos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raphael
 */
public class Cliente {
    private int id;
    private String nome;
    private String sobrenome;
    private String cpf;
    private List<Pedido> pedidos;
    private ArrayList<String> erros;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }
    
    public String getNomeCompleto() {
        return nome + " " + sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }
    
    public ArrayList<String> getErros(){
        return this.erros;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    
    public boolean valido(){
        erros = new ArrayList<>();
        if(this.nome == null){
            erros.add("O nome é obrigatório");
        }
        
        if(this.nome != null && this.nome.length() < 2){
            erros.add("O nome deve ter mais de 2 caracteres");
        }
        
        if(this.sobrenome == null){
            erros.add("O sobrenome é obrigatório");
        }
        
        if(this.sobrenome != null && this.sobrenome.length() < 2){
            erros.add("O sobrenome deve ter mais de 2 caracteres");
        }
        
        if(this.cpf.length() != 14){
            erros.add("O cpf deve ter 14 caracteres");
        }
        
        return erros.isEmpty();
    }
}
