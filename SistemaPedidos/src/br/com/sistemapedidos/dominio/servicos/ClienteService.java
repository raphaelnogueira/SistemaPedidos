/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.dominio.servicos;

import br.com.sistemapedidos.dominio.modelos.Cliente;
import br.com.sistemapedidos.infra.ClienteDAO;
import java.util.List;

/**
 *
 * @author raphael
 */
public class ClienteService {
    private ClienteDAO clienteDAO;

    public ClienteService() {
        clienteDAO = new ClienteDAO();
    }
    
    public Cliente obterPorId(int id){
        return clienteDAO.obterPorId(id);
    }
    
    public List<Cliente> obterTodos(){
        return clienteDAO.obterTodos();
    }
    
    public void excluir(Cliente cliente){
        if(cliente.getPedidos().isEmpty()){
            clienteDAO.excluir(cliente);
            return;
        }
        
        throw new RuntimeException("O cliente não pode ser exlcuido pois possui pedidos.");
    }
    
    public void atualizar(Cliente cliente){
        if(cliente == null){
            throw new RuntimeException("Não é possível exlcuir um cliente nulo.");
        }
        clienteDAO.atualizar(cliente);
    }
    
}
