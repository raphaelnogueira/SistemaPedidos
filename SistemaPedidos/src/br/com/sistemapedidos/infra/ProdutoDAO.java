/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.infra;

import br.com.sistemapedidos.dominio.modelos.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author raphael
 */
public class ProdutoDAO {
    private Connection connection;
    private final boolean closeConnection;
    
    private final String obterPorId = "SELECT * FROM produtos WHERE id = ?;";
    private final String obterTodos = "SELECT * FROM produtos;";
    private final String obterPorPedido = "SELECT pedidos.id, pedidos.descricao FROM produtos INNER JOIN item_do_pedido on produtos.id = item_do_pedido.id_produto WHERE item_do_pedido.id_pedido = ?;";
    private final String salvar = "INSERT INTO produtos(descricao) VALUES(?);";
    private final String atualizar = "UPDATE produtos SET descricao = ? WHERE id = ?;";
    private final String excluir = "DELETE FROM produtos WHERE id = ?;";

    public ProdutoDAO() {
        this.connection = null;
        this.closeConnection = true;
    }
    
    public ProdutoDAO(Connection connection) {
        this.connection = connection;
        this.closeConnection = false;
    }  
            
    public Produto obterPorId(int id) {
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
                Produto produto = new Produto();
                produto.setId(resultSet.getInt("id"));
                produto.setDescricao(resultSet.getString("descricao"));
                return produto;
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

    public List<Produto> obterTodos(){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            if(this.connection == null){
                this.connection = ConnectionFactory.getConnection();
            }
            
            preparedStatement = this.connection.prepareStatement(obterTodos);
            resultSet = preparedStatement.executeQuery();
            List<Produto> produtos = new ArrayList<>();
            while (resultSet.next()) {        
                Produto produto = new Produto();
                produto.setId(resultSet.getInt("id"));
                produto.setDescricao(resultSet.getString("descricao"));
                produtos.add(produto);
            }
            return produtos;
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
    
    public List<Produto> obterPorPedido(int idPedido){
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            if(this.connection == null){
                this.connection = ConnectionFactory.getConnection();
            }
            preparedStatement = this.connection.prepareStatement(obterPorPedido);
            preparedStatement.setLong(1, idPedido);
            resultSet = preparedStatement.executeQuery();
            List<Produto> produtos = new ArrayList<>();
            while (resultSet.next()) {        
                Produto produto = new Produto();
                produto.setId(resultSet.getInt("id"));
                produto.setDescricao(resultSet.getString("descricao"));
                produtos.add(produto);
            }
            return produtos;
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
    
    public void salvar(Produto produto) {
        PreparedStatement preparedStatement = null;
        
        try{
            if(this.connection == null){
                this.connection = ConnectionFactory.getConnection();
            }
            
            preparedStatement = this.connection.prepareStatement(salvar, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, produto.getDescricao());
            preparedStatement.execute();
            
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            produto.setId(resultSet.getInt(1));
        }catch(SQLException ex){
            throw new RuntimeException("Erro ao inserir um produto no banco de dados. Origem = " + ex.getMessage());
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

    public void atualizar(Produto produto) {
        PreparedStatement preparedStatement = null;
        
        try{
            if(this.connection == null){
                this.connection = ConnectionFactory.getConnection();
            }
            preparedStatement = this.connection.prepareStatement(atualizar);
            preparedStatement.setString(1, produto.getDescricao());
            preparedStatement.setInt(2, produto.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
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
    
    public void excluir(Produto produto) {
        PreparedStatement preparedStatement = null;
        
        try{
            if(this.connection == null){
                this.connection = ConnectionFactory.getConnection();
            }
            preparedStatement = this.connection.prepareStatement(excluir);
            preparedStatement.setInt(1, produto.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally{
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
