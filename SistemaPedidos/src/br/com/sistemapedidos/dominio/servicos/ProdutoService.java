/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.dominio.servicos;

import br.com.sistemapedidos.dominio.modelos.Pedido;
import br.com.sistemapedidos.dominio.modelos.Produto;
import br.com.sistemapedidos.infra.ItemDoPedidoDAO;
import br.com.sistemapedidos.infra.PedidoDAO;
import br.com.sistemapedidos.infra.ProdutoDAO;
import java.util.List;

/**
 *
 * @author raphael
 */
public class ProdutoService {
    private ProdutoDAO produtoDAO;
    private ItemDoPedidoDAO itemDoPedidoDAO;

    public ProdutoService() {
        produtoDAO = new ProdutoDAO();
        itemDoPedidoDAO = new ItemDoPedidoDAO();
    }
    
    
    public List<Produto> obterTodos(){
        return produtoDAO.obterTodos();
    }
    
    public void salvar(Produto produto){
        if(produto.valido()){
            produtoDAO.salvar(produto);
            return;
        }
        
        throw new RuntimeException("Não é possível salvar um produto inválido.\n" + String.join("\n", produto.getErros()));
    }
    
    public void excluir(Produto produto){
        if(itemDoPedidoDAO.obterPorProduto(produto.getId()).isEmpty()){
            produtoDAO.excluir(produto);
            return;
        }
        
        throw new RuntimeException("O produto não pode ser excluido pois está relacionado com pedidos.");
    }
    
    public void atualizar(Produto produto){
        if(!itemDoPedidoDAO.obterPorProduto(produto.getId()).isEmpty()){
            throw new RuntimeException("O produto não pode ser atualizado pois está relacionado com pedidos.");
        }
        
        if(!produto.valido()){
            throw new RuntimeException("Não é possível salvar um produto inválido.\n" + String.join("\n", produto.getErros()));
        }
        
        produtoDAO.atualizar(produto);        
    }
}
