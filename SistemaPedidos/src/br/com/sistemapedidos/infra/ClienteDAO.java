/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.infra;

import br.com.sistemapedidos.dominio.modelos.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raphael
 */
public class ClienteDAO {
    private Connection connection;
    private final boolean closeConnection;
    
    private final String obterPorId = "SELECT * FROM clientes WHERE id = ?;";
    private final String excluir = "DELETE FROM clientes WHERE id = ?;";
    private final String atualizar = "UPDATE clientes SET  nome = ?, sobrenome = ?, cpf = ? WHERE id = ?;";
    private final String obterTodos = "SELECT * FROM clientes;";

    public ClienteDAO() {
        this.connection = null;
        this.closeConnection = true;
    }
    
    public ClienteDAO(Connection connection) {
        this.connection = connection;
        this.closeConnection = false;
    } 
            
    public Cliente obterPorId(int id) {
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
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setSobrenome(resultSet.getString("sobrenome"));
                cliente.setCpf(resultSet.getString("cpf"));
                PedidoDAO pedidoDAO = new PedidoDAO(this.connection);
                cliente.setPedidos(pedidoDAO.obterPorCliente(cliente.getId()));
                return cliente;
            }else{
                return null;
            }
        }catch(SQLException ex){
            throw new RuntimeException("Erro ao consultar um cliente no banco de dados. Origem = " + ex.getMessage());            
        }finally{
            try {
                resultSet.close();
                preparedStatement.close();
                if(this.closeConnection){
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
    }
    
    public Cliente obterPorIdSemPedidos(int id) {
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
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setSobrenome(resultSet.getString("sobrenome"));
                cliente.setCpf(resultSet.getString("cpf"));
                return cliente;
            }else{
                return null;
            }
        }catch(SQLException ex){
            throw new RuntimeException("Erro ao consultar um cliente no banco de dados. Origem = " + ex.getMessage());            
        }finally{
            try {
                resultSet.close();
                preparedStatement.close();
                if(this.closeConnection){
                    connection.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
    }

    public List<Cliente> obterTodos(){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            if(this.connection == null){
                this.connection = ConnectionFactory.getConnection();
            }
            
            preparedStatement = this.connection.prepareStatement(obterTodos);
            resultSet = preparedStatement.executeQuery();
            List<Cliente> clientes = new ArrayList<>();
            while (resultSet.next()) {        
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setSobrenome(resultSet.getString("sobrenome"));
                cliente.setCpf(resultSet.getString("cpf"));
                PedidoDAO pedidoDAO = new PedidoDAO(this.connection);
                cliente.setPedidos(pedidoDAO.obterPorCliente(cliente.getId()));
                clientes.add(cliente);
            }
            return clientes;
        } catch(SQLException ex){
            throw new RuntimeException(ex);
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
    
    public void atualizar(Cliente cliente) {
        PreparedStatement preparedStatement = null;
        
        try{
            this.connection = ConnectionFactory.getConnection();
            preparedStatement = this.connection.prepareStatement(atualizar);
            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getSobrenome());
            preparedStatement.setString(3, cliente.getCpf());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                preparedStatement.close();
                this.connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
    public void excluir(Cliente cliente) {
        PreparedStatement preparedStatement = null;
        
        try{
            this.connection = ConnectionFactory.getConnection();
            preparedStatement = this.connection.prepareStatement(excluir);
            preparedStatement.setInt(1, cliente.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally{
            try {
                preparedStatement.close();
                this.connection.close();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    
}
