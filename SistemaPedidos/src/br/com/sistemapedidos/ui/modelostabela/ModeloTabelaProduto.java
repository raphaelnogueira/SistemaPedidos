/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.ui.modelostabela;

import br.com.sistemapedidos.dominio.modelos.Produto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author raphael
 */
public class ModeloTabelaProduto extends AbstractTableModel {
    private String[] colunas = new String[]{"id", "Descrição"};
    private List<Produto> lista = new ArrayList();
    
    public ModeloTabelaProduto(List<Produto> lista){
        this.lista = lista;
    }

    public ModeloTabelaProduto(){
    }

    @Override
    public int getRowCount() {
        return this.lista.size();
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }
    
    @Override
    public String getColumnName(int index) {
        return this.colunas[index];
    }
    
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Produto produto = lista.get(rowIndex);
        
        switch(columnIndex){
            case 0: return produto.getId();
            case 1: return produto.getDescricao();
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        Produto produto = lista.get(row);
        switch (col) {
            case 0:
                produto.setId((int) value); //if column 0 (code)
                break;
            case 1:
                produto.setDescricao((String) value);
                break;
            default:
        }
        this.fireTableCellUpdated(row, col);
    }    
    
    public boolean removeProduto(Produto produto) {
        int linha = this.lista.indexOf(produto);
        boolean result = this.lista.remove(produto);
        this.fireTableRowsDeleted(linha,linha);//update JTable
        return result;
    }

    public void adicionaProduto(Produto produto) {
        this.lista.add(produto);
        this.fireTableRowsInserted(lista.size() - 1, lista.size() - 1);
    }

    public void setListaProdutos(List<Produto> produtos) {
        this.lista = produtos;
        this.fireTableDataChanged();
    }

    public void limpaTabela() {
        int indice = lista.size()-1;
        if(indice<0)
            indice=0;
        this.lista = new ArrayList();
        this.fireTableRowsDeleted(0,indice);//update JTable
    }

    public Produto getProduto(int linha){
        return lista.get(linha);
    }
}
