package ar.edu.unlam.tallerweb1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.modelo.Barrio;
import ar.edu.unlam.tallerweb1.modelo.Direccion;
import ar.edu.unlam.tallerweb1.modelo.Farmacia;

public class BuscarFarmaciaPorBarrioTest extends SpringTest{
	
	@Test
    @Transactional @Rollback(true)
    public void buscarPorBarrio(){
		String barrioBusqueda;
		
		Barrio barrio = new Barrio();
		barrio.setNombre("Barrio Prueba");
		
		Session session = getSession();
    	session.save(barrio);
    	
		Direccion direccion = new Direccion();
		direccion.setCalle("Prueba Calle");
		direccion.setNumero("4568");
		direccion.setBarrio(barrio);
		
    	session.save(direccion);
    	
    	Farmacia farmacia = new Farmacia();
    	farmacia.setNombre("Farmacia A");
    	farmacia.setTelefono("4568-9874");
    	farmacia.setDireccion(direccion);
    	
    	session.save(farmacia);
    	
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Escriba el Barrio a Buscar:");
    	barrioBusqueda = sc.nextLine();
    	
    	List<Farmacia> resultado = getSession().createCriteria(Farmacia.class)
    			.createAlias("direccion", "direccion2")
    			.createAlias("direccion2.barrio", "barrioBuscado")
    			.add(Restrictions.eq("barrioBuscado.nombre", barrioBusqueda))
    			.list();
    	
    	assertThat(resultado).isNotEmpty();
    	for(Farmacia barrioResultado: resultado){
        	assertThat(barrioResultado.getDireccion().getBarrio().getNombre()).isEqualTo("Barrio Prueba"); 
	   }
	}
}
