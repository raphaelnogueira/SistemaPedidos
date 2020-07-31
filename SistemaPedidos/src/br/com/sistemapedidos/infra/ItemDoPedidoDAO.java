/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.infra;

import br.com.sistemapedidos.dominio.modelos.ItemDoPedido;
import br.com.sistemapedidos.dominio.modelos.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raphael
 */
public class ItemDoPedidoDAO {
    private Connection connection;
    private final boolean closeConnection;
    
    private final String obterPorPedido = "SELECT * FROM item_do_pedido WHERE id_pedido = ?;";
    private final String obterPorProduto = "SELECT * FROM item_do_pedido WHERE id_pedido = ?;";

    public ItemDoPedidoDAO() {
        this.connection = null;
        this.closeConnection = true;
    }
    
    public ItemDoPedidoDAO(Connection connection) {
        this.connection = connection;
        this.closeConnection = false;
    }
            
    public List<ItemDoPedido> obterPorPedido(int idPedido) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            if(this.connection == null){
                this.connection = ConnectionFactory.getConnection();
            }
            
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(obterPorPedido);
            preparedStatement.setLong(1, idPedido);
            resultSet = preparedStatement.executeQuery();
            List<ItemDoPedido> itens = new ArrayList<>();
            while(resultSet.next()){
                ItemDoPedido itemDoPedido = new ItemDoPedido();
                itemDoPedido.setQuantidade(resultSet.getInt("quantidade"));
                
                ProdutoDAO produtoDAO = new ProdutoDAO(connection);
                Produto produto = produtoDAO.obterPorId(resultSet.getInt("id_produto"));
                itemDoPedido.setProduto(produto);
                itens.add(itemDoPedido);
            }
            return itens;
        }catch(SQLException ex){
            throw new RuntimeException("Erro ao consultar um produto no banco de dados. Origem = " + ex.getMessage());            
        }finally{
            try {
                resultSet.close();
                preparedStatement.close();
                if(this.closeConnection){
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }   
    }
    
    public List<ItemDoPedido> obterPorProduto(int idProduto) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            if(this.connection == null){
                this.connection = ConnectionFactory.getConnection();
            }
            
            connection = ConnectionFactory.getConnection();
            preparedStatement = connection.prepareStatement(obterPorProduto);
            preparedStatement.setLong(1, idProduto);
            resultSet = preparedStatement.executeQuery();
            List<ItemDoPedido> itens = new ArrayList<>();
            while(resultSet.next()){
                ItemDoPedido itemDoPedido = new ItemDoPedido();
                itemDoPedido.setQuantidade(resultSet.getInt("quantidade"));
                
                ProdutoDAO produtoDAO = new ProdutoDAO(connection);
                Produto produto = produtoDAO.obterPorId(resultSet.getInt("id_produto"));
                itemDoPedido.setProduto(produto);
                itens.add(itemDoPedido);
            }
            return itens;
        }catch(SQLException ex){
            throw new RuntimeException("Erro ao consultar um produto no banco de dados. Origem = " + ex.getMessage());            
        }finally{
            try {
                resultSet.close();
                preparedStatement.close();
                if(this.closeConnection){
                    connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }   
    }
}
