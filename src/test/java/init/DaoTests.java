package init;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import init.dao.AulasDao;
import init.entities.Aula;

@DataJpaTest
public class DaoTests {
	//Verifica que: 
	//1. persista la entidad Aula.
	//2. no persista aulas con el mismo nombre
	
	@Autowired
	AulasDao aulasDao;
	
	@Test
	@DisplayName("Debe persistir entidad Aula")
	public void deberiaPersistirEntidadAula() {
		//Arrange
		Aula aula = new Aula(0, "aula1", 30, true, false);
		
		//Act
		aulasDao.save(aula);
		
		//Assert
		Assertions.assertTrue(aulasDao.existsById(0), "El aula no se ha persistido correctamente");
	}
	
	@Test
	@DisplayName("No debe persistir aulas con el mismo nombre")
	public void noPersisteAulasNombreIdentico() {
		//Arrange
		Aula aula1 = new Aula(0, "aula1", 30, true, false);
		Aula aula2 = new Aula(1, "aula1", 20, true, false);
		
		//Act
		aulasDao.save(aula1);
		aulasDao.save(aula2);
		
		//Assert
		Assertions.assertTrue(aulasDao.existsById(0), "El aula no se ha persistido correctamente");
		Assertions.assertFalse(aulasDao.existsById(1), "Se han persistido aulas con el mismo nombre");
	}

}
