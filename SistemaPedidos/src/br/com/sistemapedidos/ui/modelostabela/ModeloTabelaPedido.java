/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.ui.modelostabela;

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
public class ModeloTabelaPedido extends AbstractTableModel {
    private String[] colunas = new String[]{"id", "Data", "Quantidade Itens"};
    private List<Pedido> lista = new ArrayList();
    
    public ModeloTabelaPedido(List<Pedido> lista){
        this.lista = lista;
    }

    public ModeloTabelaPedido(){
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
        Pedido pedido = lista.get(rowIndex);
        
        switch(columnIndex){
            case 0: return pedido.getId();
            case 1: return new SimpleDateFormat( "dd/MM/YYYY" ).format(pedido.getData().getTime());
            case 2: return pedido.getItens().size();
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        Pedido pedido = lista.get(row);
        switch (col) {
            case 0:
                pedido.setId((int) value); //if column 0 (code)
                break;
            default:
        }
        this.fireTableCellUpdated(row, col);
    }    
    
    public boolean removePedido(Pedido pedido) {
        int linha = this.lista.indexOf(pedido);
        boolean result = this.lista.remove(pedido);
        this.fireTableRowsDeleted(linha,linha);//update JTable
        return result;
    }

    public void adicionaPedido(Pedido pedido) {
        this.lista.add(pedido);
        this.fireTableRowsInserted(lista.size() - 1, lista.size() - 1);
    }

    public void setListaPedidos(List<Pedido> pedidos) {
        this.lista = pedidos;
        this.fireTableDataChanged();
    }

    public void limpaTabela() {
        int indice = lista.size()-1;
        if(indice<0)
            indice=0;
        this.lista = new ArrayList();
        this.fireTableRowsDeleted(0,indice);//update JTable
    }

    public Pedido getPedido(int linha){
        return lista.get(linha);
    }
}
