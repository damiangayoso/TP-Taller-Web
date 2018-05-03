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
		
		// Creamos un objeto del tipo Barrio y lo seteamos
		Barrio barrio = new Barrio();
		barrio.setNombre("Barrio Prueba");
		
		// lo guardamos
		Session session = getSession();
    	session.save(barrio);
    	
    	// mismo proceso para Direccion y Farmacia
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
    	
    	// pedimos el nombre de barrio a buscar
    	Scanner sc = new Scanner(System.in);
    	System.out.println("Escriba el Barrio a Buscar:");
    	barrioBusqueda = sc.nextLine();
    	
    	// buscamos de acuerdo al nombre aportado anteriormente
    	List<Farmacia> resultado = getSession().createCriteria(Farmacia.class)
    			.createAlias("direccion", "direccion2")
    			.createAlias("direccion2.barrio", "barrioBuscado")
    			.add(Restrictions.eq("barrioBuscado.nombre", barrioBusqueda))
    			.list();
    	
    	// comprobamos que la lista no este vacia
    	assertThat(resultado).isNotEmpty();
    	
    	// recorremos la lista para comprobar que todos los resultados son correctos
    	for(Farmacia barrioResultado: resultado){
        	assertThat(barrioResultado.getDireccion().getBarrio().getNombre()).isEqualTo(barrioBusqueda); 
	   }
	}
}
