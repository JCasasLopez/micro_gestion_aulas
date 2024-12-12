package init;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
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
		var inOrder = inOrder(mapeador, aulasDao);
		inOrder.verify(mapeador).aulaDtoToEntity(aulaDto);
		inOrder.verify(aulasDao).save(aula);
	}
	
	@Test
	@DisplayName("Lanza AulaDatabaseException si no se puede grabar aula")
	void altaAula_deberiaLanzarAulaDatabaseExceptionSiNoGrabaAula() {
		//Arrange
		AulaDto aulaDto = new AulaDto();
		Aula aula = new Aula();
		when(mapeador.aulaDtoToEntity(aulaDto)).thenReturn(aula);
        when(aulasDao.save(aula)).thenThrow(new RuntimeException());

		//Act 
        assertThrows(AulaDatabaseException.class, () -> {aulasServiceImpl.altaAula(aulaDto);}, 
        		"Se esperaba AulaDatabaseException al fallar la modificación del aula");
		
		//Assert
        var inOrder = inOrder(mapeador, aulasDao);
		inOrder.verify(mapeador).aulaDtoToEntity(aulaDto);
		inOrder.verify(aulasDao).save(aula);
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
		var inOrder = inOrder(aulasDao);
		inOrder.verify(aulasDao).existsById(idAula);
		inOrder.verify(aulasDao).deleteById(idAula);
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
		var inOrder = inOrder(aulasDao);
		inOrder.verify(aulasDao).existsById(idAula);
		inOrder.verify(aulasDao, never()).deleteById(idAula);
	}
	
	@Test
	@DisplayName("Lanza AulaDatabaseException si no se puede borrar aula")
	void bajaAula_deberiaLanzarAulaDatabaseExceptionSiNoBorraAula() {
		//Arrange
		int idAula = 0;
		when(aulasDao.existsById(idAula)).thenReturn(true);
        doThrow(new RuntimeException()).when(aulasDao).deleteById(idAula);

		//Act 
        assertThrows(AulaDatabaseException.class, () -> {aulasServiceImpl.bajaAula(idAula);}, 
        		"Se esperaba AulaDatabaseException al fallar la modificación del aula");
		
		//Assert
        var inOrder = inOrder(aulasDao);
        inOrder.verify(aulasDao).existsById(idAula);
        inOrder.verify(aulasDao).deleteById(idAula);
	}
	
	@Test
	@DisplayName("Devuelve true si el aula se ha modificado correctamente")
	void modificarAula_deberiaModificarAula() {
		//Arrange
		AulaDto aulaDto = new AulaDto(0, "aula1", 30, false, false);
		Aula aula = new Aula();
		when(aulasDao.existsById(aulaDto.getIdAula())).thenReturn(true);
		when(mapeador.aulaDtoToEntity(aulaDto)).thenReturn(aula);
		when(aulasDao.save(aula)).thenReturn(aula);
		
		//Act
		boolean resultado = aulasServiceImpl.modificarAula(aulaDto);
		
		//Assert
		assertTrue(resultado, "El aula no se ha modificado correctamente");
		var inOrder = inOrder(aulasDao, mapeador, aulasDao);
		inOrder.verify(aulasDao).existsById(aulaDto.getIdAula());
		inOrder.verify(mapeador).aulaDtoToEntity(aulaDto);
		inOrder.verify(aulasDao).save(aula);
	}
	
	@Test
	@DisplayName("Devuelve false si el aula no existe")
	void modificarAula_aulaNoExiste() {
		//Arrange
		AulaDto aulaDto = new AulaDto(0, "aula1", 30, false, false);
		Aula aula = new Aula();
		when(aulasDao.existsById(aulaDto.getIdAula())).thenReturn(false);
		
		//Act
		boolean resultado = aulasServiceImpl.modificarAula(aulaDto);
		
		//Assert
		assertFalse(resultado, "El aula se ha modificado correctamente cuando no existe");
		var inOrder = inOrder(aulasDao, mapeador, aulasDao);
		inOrder.verify(aulasDao).existsById(aulaDto.getIdAula());
		inOrder.verify(mapeador, never()).aulaDtoToEntity(aulaDto);
		inOrder.verify(aulasDao, never()).save(aula);
	}
	
	@Test
	@DisplayName("Lanza AulaDatabaseExcepction si no se puede modificar aula")
	void modificarAula_deberiaLanzarAulaDatabaseExceptionSiNoModificaAula() {
		//Arrange
		AulaDto aulaDto = new AulaDto(0, "aula1", 30, false, false);
		Aula aula = new Aula();
		when(aulasDao.existsById(aulaDto.getIdAula())).thenReturn(true);
		when(mapeador.aulaDtoToEntity(aulaDto)).thenReturn(aula);
		when(aulasDao.save(aula)).thenThrow(new RuntimeException());
				
		//Act
        assertThrows(AulaDatabaseException.class, () -> {aulasServiceImpl.modificarAula(aulaDto);}, 
        		"Se esperaba AulaDatabaseException al fallar la modificación del aula");
				
		//Assert
		var inOrder = inOrder(aulasDao, mapeador, aulasDao);
		inOrder.verify(aulasDao).existsById(aulaDto.getIdAula());
		inOrder.verify(mapeador).aulaDtoToEntity(aulaDto);
		inOrder.verify(aulasDao).save(aula);	
	}
	
}
