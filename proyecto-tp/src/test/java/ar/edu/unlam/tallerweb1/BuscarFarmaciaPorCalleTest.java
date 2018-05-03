package ar.edu.unlam.tallerweb1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

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
		
		// Creamos un objeto de tipo Direccion y lo seteamos
		Direccion direccion = new Direccion();
		direccion.setCalle("Prueba Calle");
		direccion.setNumero("4568");
		
		// guardamos
		Session session = getSession();
    	session.save(direccion);
    	
    	// hacemos lo mismo con Farmacia
    	Farmacia farmacia = new Farmacia();
    	farmacia.setNombre("Farmacia A");
    	farmacia.setTelefono("4568-9874");
    	farmacia.setDireccion(direccion);
    	session.save(farmacia);
    	
    	// seteamos el nombre de la calle a buscar 
    	calleBusqueda = "Prueba Calle";
    	
    	// buscamos la calle con el dato aportado anteriormente
    	List<Farmacia> resultado = getSession().createCriteria(Farmacia.class)
    			.createAlias("direccion", "calleBuscada")
    			.add(Restrictions.eq("calleBuscada.calle", calleBusqueda))
    			.list();
    	
    	// comprobamos que la lista no este vacia
    	assertThat(resultado).isNotEmpty();
    	
    	// recorremos la lista para comprobar que todos los resultados sean correctos
    	for(Farmacia calleResultado: resultado){
        	assertThat(calleResultado.getDireccion().getCalle()).isEqualTo(calleBusqueda);       	
    	}
    	
	}
}
