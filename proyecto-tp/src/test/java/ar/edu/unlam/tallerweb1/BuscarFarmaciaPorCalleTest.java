package ar.edu.unlam.tallerweb1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unlam.tallerweb1.modelo.Direccion;
import ar.edu.unlam.tallerweb1.modelo.Farmacia;

public class BuscarFarmaciaPorCalleTest extends SpringTest{

	@Test
    @Transactional @Rollback(true)
    public void buscarPorDireccion(){
		String calleBusqueda;
		
		Direccion direccion = new Direccion();
		direccion.setCalle("Prueba Calle");
		direccion.setNumero("4568");
		
		Session session = getSession();
    	session.save(direccion);
    	
    	Farmacia farmacia = new Farmacia();
    	farmacia.setNombre("Farmacia A");
    	farmacia.setTelefono("4568-9874");
    	farmacia.setDireccion(direccion);
    	session.save(farmacia);
    	
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Escriba la Caller a Buscar:");
    	calleBusqueda = sc.nextLine();
    	
    	List<Farmacia> resultado = getSession().createCriteria(Farmacia.class)
    			.createAlias("direccion", "calleBuscada")
    			.add(Restrictions.eq("calleBuscada.calle", calleBusqueda))
    			.list();
    	
    	assertThat(resultado).isNotEmpty();
    	for(Farmacia calleResultado: resultado){
        	assertThat(calleResultado.getDireccion().getCalle()).isEqualTo("Prueba Calle");       	
    	}
    	
	}
}
