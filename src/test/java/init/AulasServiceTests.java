package init;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
	@DisplayName("Efectúa el alta de un aula correctamente")
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
	@DisplayName("Se produce un error al efectuar el alta de un aula")
	void altaAula_errorAlDardeAltaAula() {
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
	@DisplayName("Efectúa la baja de un aula correctamente")
	void bajaAula_deberiaDarDeBajaAula() {
		//Arrange
		int idAula = 0;
		when(aulasDao.existsById(idAula)).thenReturn(true);
		
		//Act
		aulasServiceImpl.bajaAula(idAula);
		
		//Assert
		verify(aulasDao).existsById(idAula);
		verify(aulasDao).deleteById(idAula);
		
	}
}
