/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.ui.modelostabela;

import br.com.sistemapedidos.dominio.modelos.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author raphael
 */
public class ModeloTabelaCliente extends AbstractTableModel {
    private String[] colunas = new String[]{"id","Nome", "cpf"};
    private List<Cliente> lista = new ArrayList();
    
    public ModeloTabelaCliente(List<Cliente> lista){
        this.lista = lista;
    }

    public ModeloTabelaCliente(){
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
        Cliente cliente = lista.get(rowIndex);
        
        switch(columnIndex){
            case 0: return cliente.getId();
            case 1: return cliente.getNomeCompleto();
            case 2: return cliente.getCpf();
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        Cliente cliente = lista.get(row);
        switch (col) {
            case 0:
                cliente.setId((int) value); //if column 0 (code)
                break;
            case 2:
                cliente.setCpf((String) value);
                break;
            default:
        }
        this.fireTableCellUpdated(row, col);
    }    
    
    public boolean removeCliente(Cliente cliente) {
        int linha = this.lista.indexOf(cliente);
        boolean result = this.lista.remove(cliente);
        this.fireTableRowsDeleted(linha,linha);//update JTable
        return result;
    }

    public void adicionaCliente(Cliente cliente) {
        this.lista.add(cliente);
        this.fireTableRowsInserted(lista.size() - 1, lista.size() - 1);
    }

    public void setListaClientes(List<Cliente> clientes) {
        this.lista = clientes;
        this.fireTableDataChanged();
    }

    public void limpaTabela() {
        int indice = lista.size()-1;
        if(indice<0)
            indice=0;
        this.lista = new ArrayList();
        this.fireTableRowsDeleted(0,indice);//update JTable
    }

    public Cliente getCliente(int linha){
        return lista.get(linha);
    }
}
