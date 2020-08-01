/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sistemapedidos.teste.modelos;

import br.com.sistemapedidos.dominio.modelos.Cliente;
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
public class ClienteTeste {
    
    public ClienteTeste() {
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
    public void testClienteValido1() {
        Cliente cliente = new Cliente();
        cliente.setNome("Jose");
        cliente.setSobrenome("Rodrigues");
        cliente.setCpf("073.048.659-45");
        
        boolean clienteValido = cliente.valido();
        
        assertEquals(clienteValido, true);
        assertEquals(cliente.getErros().size(), 0);
    }
    
    @Test
    public void testClienteValido2() {
        Cliente cliente = new Cliente();
        cliente.setSobrenome("Rodrigues");
        cliente.setCpf("073.048.659-45");
        
        boolean clienteValido = cliente.valido();
        
        assertEquals(clienteValido, false);
        assertEquals(cliente.getErros().size(), 1);
        assertEquals(cliente.getErros().get(0), "O nome é obrigatório");
    }
    
    @Test
    public void testClienteValido3() {
        Cliente cliente = new Cliente();
        cliente.setNome("R");
        cliente.setSobrenome("Rodrigues");
        cliente.setCpf("073.048.659-45");
        
        boolean clienteValido = cliente.valido();
        
        assertEquals(clienteValido, false);
        assertEquals(cliente.getErros().size(), 1);
        assertEquals(cliente.getErros().get(0), "O nome deve ter mais de 2 caracteres");
    }
    
    @Test
    public void testClienteValido4() {
        Cliente cliente = new Cliente();
        cliente.setNome("Jose");
        cliente.setCpf("073.048.659-45");
        
        boolean clienteValido = cliente.valido();
        
        assertEquals(clienteValido, false);
        assertEquals(cliente.getErros().size(), 1);
        assertEquals(cliente.getErros().get(0), "O sobrenome é obrigatório");
    }
    
    @Test
    public void testClienteValido5() {
        Cliente cliente = new Cliente();
        cliente.setNome("Jose");
        cliente.setSobrenome("R");
        cliente.setCpf("073.048.659-45");
        
        boolean clienteValido = cliente.valido();
        
        assertEquals(clienteValido, false);
        assertEquals(cliente.getErros().size(), 1);
        assertEquals(cliente.getErros().get(0), "O sobrenome deve ter mais de 2 caracteres");
    }
    
    @Test
    public void testClienteValido6() {
        Cliente cliente = new Cliente();
        cliente.setNome("Jose");
        cliente.setSobrenome("Rodrigues");
        cliente.setCpf("073.048.659-4");
        
        boolean clienteValido = cliente.valido();
        
        assertEquals(clienteValido, false);
        assertEquals(cliente.getErros().size(), 1);
        assertEquals(cliente.getErros().get(0), "O cpf deve ter 14 caracteres");
    }
    
    @Test
    public void testClienteValido7() {
        Cliente cliente = new Cliente();
        cliente.setNome("Jose");
        cliente.setSobrenome("Rodrigues");
        cliente.setCpf("073.048.659-434");
        
        boolean clienteValido = cliente.valido();
        
        assertEquals(clienteValido, false);
        assertEquals(cliente.getErros().size(), 1);
        assertEquals(cliente.getErros().get(0), "O cpf deve ter 14 caracteres");
    }
}
