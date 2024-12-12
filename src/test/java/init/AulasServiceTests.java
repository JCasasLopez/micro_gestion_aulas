package init;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import init.dao.AulasDao;
import init.entities.Aula;
import init.model.AulaDto;
import init.service.AulasServiceImpl;
import init.utilidades.Mapeador;

@ExtendWith(MockitoExtension.class)
public class AulasServiceTests {
	
	@Mock
	Mapeador mapeador;
	
	@Mock
	AulasDao aulasDao;
	
	@InjectMocks
	AulasServiceImpl aulasServiceImpl;
	
	@Test
	@DisplayName("Devuelve true cuando el alta se efectúa correctamente")
	void altaAula_deberiaDardeAltaAula() {
		//Arrange
		AulaDto aulaDto = new AulaDto();
		Aula aula = new Aula();
		when(mapeador.aulaDtoToEntity(aulaDto)).thenReturn(aula);
		when(aulasDao.save(aula)).thenReturn(aula);
		
		//Act
		boolean resultado = aulasServiceImpl.altaAula(aulaDto);
		
		//Assert
		assertTrue(resultado, "El aula no se ha guardado correctamente");
		verify(mapeador).aulaDtoToEntity(aulaDto);
		verify(aulasDao).save(aula);
	}
	
	@Test
	@DisplayName("Devuelve false cuando el alta NO se efectúa correctamente")
	void altaAula_deberiaNoDardeAltaAula() {
		//Arrange
		AulaDto aulaDto = new AulaDto();
		Aula aula = new Aula();
		when(mapeador.aulaDtoToEntity(aulaDto)).thenReturn(aula);
		when(aulasDao.save(aula)).thenReturn(null);
		
		//Act
		boolean resultado = aulasServiceImpl.altaAula(aulaDto);
		
		//Assert
		assertFalse(resultado, "El aula se ha guardado cuando no debía");
		verify(mapeador).aulaDtoToEntity(aulaDto);
		verify(aulasDao).save(aula);
	}
	
	@Test
	@DisplayName("Devuelve true cuando la baja se efectúa correctamente")
	void bajaAula_deberiaDarDeBajaAula() {
		//Arrange
		int idAula = 0;
		when(aulasDao.existsById(idAula)).thenReturn(true);
		
		//Act
		boolean resultado = aulasServiceImpl.bajaAula(idAula);
		
		//Assert
		assertTrue(resultado, "El aula se ha borrado correctamente");
		verify(aulasDao).existsById(idAula);
		verify(aulasDao).deleteById(idAula);
	}
	
	@Test
	@DisplayName("Devuelve false cuando la baja se efectúa correctamente")
	void bajaAula_deberiaNoDardeBajaAula() {
		//Arrange
		int idAula = 0;
		when(aulasDao.existsById(idAula)).thenReturn(false);
		
		//Act
		boolean resultado = aulasServiceImpl.bajaAula(idAula);
		
		//Assert
		assertFalse(resultado, "El aula NO se ha borrado correctamente");
		verify(aulasDao).existsById(idAula);
		verify(aulasDao, never()).deleteById(idAula);
	}
}
