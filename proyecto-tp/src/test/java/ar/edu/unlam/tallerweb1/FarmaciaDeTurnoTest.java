package ar.edu.unlam.tallerweb1;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import ar.edu.unlam.tallerweb1.modelo.Farmacia;

public class FarmaciaDeTurnoTest extends SpringTest{

	@Test
    @Transactional @Rollback(true)
    public void buscarPorDia(){
    	Farmacia farmacia = new Farmacia();
    	farmacia.setNombre("Prueba Farmacia");
    	farmacia.setDiaDeTurno("Martes");
    	farmacia.setTelefono("4563-4567");
    	
    	Session session = getSession();
    	session.save(farmacia);
    	
    	List<Farmacia> resultado = getSession().createCriteria(Farmacia.class)
    			.add(Restrictions.eq("diaDeTurno", "Martes"))
    			.list();
    	
    	assertThat(resultado).isNotEmpty();
    	for(Farmacia farmaciaResultado: resultado){
    		System.out.println(farmaciaResultado.getDiaDeTurno());
        	assertThat(farmaciaResultado.getDiaDeTurno()).isEqualTo("Martes");       	
    	}

    }
	
}
