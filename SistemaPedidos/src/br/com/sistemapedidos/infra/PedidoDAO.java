/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.infra;

import br.com.sistemapedidos.dominio.modelos.Pedido;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author raphael
 */
public class PedidoDAO {
    private Connection connection;
    private final boolean closeConnection;
    
    private final String obterPorId = "SELECT * FROM pedidos WHERE id = ?;";
    private final String obterPorCliente = "SELECT * FROM pedidos WHERE id_cliente = ?;";
    private final String salvar = "INSERT INTO pedidos(id_cliente, data) VALUES(?, ?);";

    public PedidoDAO() {
        this.connection = null;
        this.closeConnection = true;
    }
    
    public PedidoDAO(Connection connection) {
        this.connection = connection;
        this.closeConnection = false;
    }
    
    public Pedido obterPorId(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            if(this.connection == null){
                this.connection = ConnectionFactory.getConnection();
            }
            preparedStatement = this.connection.prepareStatement(obterPorId);
            preparedStatement.setLong(1, id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                Pedido pedido = new Pedido();
                pedido.setId(resultSet.getInt("id"));
                pedido.setData(resultSet.getDate("data"));
                ItemDoPedidoDAO itemDoPedidoDAO = new ItemDoPedidoDAO(this.connection);
                pedido.setItens(itemDoPedidoDAO.obterPorPedido(pedido.getId()));
                ClienteDAO clienteDAO = new ClienteDAO(connection);
                pedido.setCliente(clienteDAO.obterPorId(resultSet.getInt("id_cliente")));
                return pedido;
            }else{
                return null;
            }
        }catch(SQLException ex){
            throw new RuntimeException("Erro ao consultar um produto no banco de dados. Origem = " + ex.getMessage());            
        }finally{
            try {
                resultSet.close();
                preparedStatement.close();
                if(closeConnection){
                    this.connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }   
    }

    public List<Pedido> obterPorCliente(int idCliente){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            if(this.connection == null){
                this.connection = ConnectionFactory.getConnection();
            }
            preparedStatement = this.connection.prepareStatement(obterPorCliente);
            preparedStatement.setLong(1, idCliente);
            resultSet = preparedStatement.executeQuery();
            List<Pedido> pedidos = new ArrayList<>();
            ClienteDAO clienteDAO = new ClienteDAO(connection);
            while(resultSet.next()){
                Pedido pedido = new Pedido();
                pedido.setId(resultSet.getInt("id"));
                pedido.setData(resultSet.getDate("data"));
                ItemDoPedidoDAO itemDoPedidoDAO = new ItemDoPedidoDAO(this.connection);
                pedido.setItens(itemDoPedidoDAO.obterPorPedido(pedido.getId()));
                pedido.setCliente(clienteDAO.obterPorIdSemPedidos(resultSet.getInt("id_cliente")));
                pedidos.add(pedido);
            }
            return pedidos;
        }catch(SQLException ex){
            throw new RuntimeException("Erro ao consultar um produto no banco de dados. Origem = " + ex.getMessage());            
        }finally{
            try {
                resultSet.close();
                preparedStatement.close();
                if(closeConnection){
                    this.connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }   
    }

    public void salvar(Pedido pedido) {
        PreparedStatement preparedStatement = null;
        
        try{
            if(this.connection == null){
                this.connection = ConnectionFactory.getConnection();
            }
            
            preparedStatement = this.connection.prepareStatement(salvar, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, pedido.getCliente().getId());
            preparedStatement.setDate(2, new java.sql.Date(pedido.getData().getTime()));
            preparedStatement.execute();
            
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            pedido.setId(resultSet.getInt(1));
        }catch(SQLException ex){
            throw new RuntimeException("Erro ao inserir um pedido no banco de dados. Origem = " + ex.getMessage());
        }finally{
            try {
                preparedStatement.close();
                if(closeConnection){
                    this.connection.close();
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
