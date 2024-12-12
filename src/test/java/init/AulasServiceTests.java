package init;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
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
import init.exception.AulaDatabaseException;
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
	@DisplayName("Lanza AulaDatabaseException cuando no se puede grabar aula")
	void altaAula_deberiaLanzarAulaDatabaseExceptionSiNoGrabaAula() {
		//Arrange
		AulaDto aulaDto = new AulaDto();
		Aula aula = new Aula();
		when(mapeador.aulaDtoToEntity(aulaDto)).thenReturn(aula);
        when(aulasDao.save(aula)).thenThrow(new RuntimeException());

		//Act 
        assertThrows(AulaDatabaseException.class, () -> {aulasServiceImpl.altaAula(aulaDto);});
		
		//Assert
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
	@DisplayName("Devuelve false cuando el aula no existe")
	void bajaAula_aulaNoExiste() {
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
	
	@Test
	@DisplayName("Lanza AulaDatabaseException cuando no se puede borrar aula")
	void bajaAula_deberiaLanzarAulaDatabaseExceptionSiNoBorraAula() {
		//Arrange
		int idAula = 0;
		when(aulasDao.existsById(idAula)).thenReturn(true);
        doThrow(new RuntimeException()).when(aulasDao).deleteById(idAula);

		//Act 
        assertThrows(AulaDatabaseException.class, () -> {aulasServiceImpl.bajaAula(idAula);});
		
		//Assert
		verify(aulasDao).existsById(idAula);
		verify(aulasDao).deleteById(idAula);
	}
}
