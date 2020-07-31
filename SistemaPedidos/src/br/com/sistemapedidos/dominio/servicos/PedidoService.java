/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.dominio.servicos;

import br.com.sistemapedidos.dominio.modelos.Pedido;
import br.com.sistemapedidos.infra.ItemDoPedidoDAO;
import br.com.sistemapedidos.infra.PedidoDAO;

/**
 *
 * @author raphael
 */
public class PedidoService {
    private PedidoDAO pedidoDAO;
    private ItemDoPedidoDAO itemDoPedidoDAO;

    public PedidoService() {
        pedidoDAO = new PedidoDAO();
        itemDoPedidoDAO = new ItemDoPedidoDAO();
    }
    
    public void criarPedido(Pedido pedido){
        if(pedido.getItens().isEmpty()){
            throw new RuntimeException("Pelo menos 1 item deve ser incluido no pedido.");
        }
        
        pedidoDAO.salvar(pedido);
        
        pedido.getItens().forEach((item) -> {
            itemDoPedidoDAO.salvar(item, pedido.getId());
        });
    } 
}
