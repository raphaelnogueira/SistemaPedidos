/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.ui.modelostabela;

import br.com.sistemapedidos.dominio.modelos.ItemDoPedido;
import br.com.sistemapedidos.dominio.modelos.Pedido;
import br.com.sistemapedidos.dominio.modelos.Produto;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author raphael
 */
public class ModeloTabelaItemDoPedido extends AbstractTableModel {
    private String[] colunas = new String[]{"Descrição", "Quantidade"};
    private List<ItemDoPedido> lista = new ArrayList();
    
    public ModeloTabelaItemDoPedido(List<ItemDoPedido> lista){
        this.lista = lista;
    }

    public ModeloTabelaItemDoPedido(){
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
        ItemDoPedido item = lista.get(rowIndex);
        
        switch(columnIndex){
            case 0: return item.getProduto().getDescricao();
            case 1: return item.getQuantidade();
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        ItemDoPedido item = lista.get(row);
        switch (col) {
            case 1:
                item.setQuantidade((int) value); //if column 0 (code)
                break;
            default:
        }
        this.fireTableCellUpdated(row, col);
    }    
    
    public boolean removeItemDoPedido(ItemDoPedido item) {
        int linha = this.lista.indexOf(item);
        boolean result = this.lista.remove(item);
        this.fireTableRowsDeleted(linha,linha);//update JTable
        return result;
    }

    public void adicionaItemDoPedido(ItemDoPedido item) {
        this.lista.add(item);
        this.fireTableRowsInserted(lista.size() - 1, lista.size() - 1);
    }

    public void setListaItensDoPedido(List<ItemDoPedido> itens) {
        this.lista = itens;
        this.fireTableDataChanged();
    }

    public void limpaTabela() {
        int indice = lista.size()-1;
        if(indice<0)
            indice=0;
        this.lista = new ArrayList();
        this.fireTableRowsDeleted(0,indice);//update JTable
    }

    public ItemDoPedido getItemDoPedido(int linha){
        return lista.get(linha);
    }
}
