/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.teste.modelos;

import br.com.sistemapedidos.dominio.modelos.Produto;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author raphael
 */
public class ProdutoTeste {
    
    public ProdutoTeste() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
     @Test
     public void produtoValido1() {
         Produto produto = new Produto();
         produto.setDescricao("Produto de teste");
         
         boolean produtoValido = produto.valido();
         
         assertEquals(produtoValido, true);
     }
     
     @Test
     public void produtoValido3() {
         Produto produto = new Produto();
         produto.setDescricao("Produto");
         
         boolean produtoValido = produto.valido();
         
         assertEquals(produtoValido, false);
         assertEquals(produto.getErros().size(), 1);
         assertEquals(produto.getErros().get(0), "A descrção do produto deve ter pelo menos 10 caracteres.");
     }
}
