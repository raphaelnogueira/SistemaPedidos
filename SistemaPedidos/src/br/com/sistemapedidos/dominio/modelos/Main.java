/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.dominio.modelos;

import br.com.sistemapedidos.infra.ItemDoPedidoDAO;
import br.com.sistemapedidos.infra.PedidoDAO;
import java.util.List;

/**
 *
 * @author raphael
 */
public class Main {
    public static void main(String[] args){
        ItemDoPedidoDAO itemDoPedidoDAO = new ItemDoPedidoDAO();
        
        List<ItemDoPedido> itens = itemDoPedidoDAO.obterPorPedido(1);
        
        PedidoDAO pedidoDAO = new PedidoDAO();
        
        List<Pedido> pedidos = pedidoDAO.obterPorCliente(1);
    }
    
}
