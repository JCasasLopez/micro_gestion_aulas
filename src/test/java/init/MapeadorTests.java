package init;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import init.entities.Aula;
import init.model.AulaDto;
import init.utilidades.Mapeador;

//Todas estas anotaciones evitan tener que levantar el contexto entero con @SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@Import(Mapeador.class)
public class MapeadorTests {
	
	@Autowired
	private Mapeador mapeador;

	@Test
	@DisplayName("Mapea correctamente de AulaDto a Aula")
	public void mapeaCorrectamenteAAula() {
		//Arrange
		AulaDto aulaDto = new AulaDto(0, "aula1", 30, false, true);
		
		//Act
		Aula aulaMapeada = mapeador.aulaDtoToEntity(aulaDto);
		
		//Assert
		assertAll("Verificación mapeo AulaDto a Aula", 
				() -> assertEquals(aulaMapeada.getIdAula(), aulaDto.getIdAula(), 
																	"Los idAula no coinciden"),
				() -> assertEquals(aulaMapeada.getNombre(), aulaDto.getNombre(), 
																	"Los nombres no coinciden"),
				() -> assertEquals(aulaMapeada.getCapacidad(), aulaDto.getCapacidad(), 
																	"Las capacidades no coinciden"),
				() -> assertEquals(aulaMapeada.isProyector(), aulaDto.isProyector(), 
																	"Los estados de proyector no coinciden"),
				() -> assertEquals(aulaMapeada.isAltavoces(), aulaDto.isAltavoces(), 
																	"Los estados de altavoces no coinciden")
		);
		
	}
	
	@Test
	@DisplayName("Mapea correctamente de Aula a AulaDto")
	public void mapeaCorrectamenteAAulaDto() {
		//Arrange
		Aula aula = new Aula(0, "aula1", 30, false, true);
		
		//Act
		AulaDto aulaDtoMapeada = mapeador.aulaEntityToDto(aula);
		
		//Assert
		assertAll("Verificación mapeo AulaDto a Aula", 
				() -> assertEquals(aulaDtoMapeada.getIdAula(), aula.getIdAula(), 
																	"Los idAula no coinciden"),
				() -> assertEquals(aulaDtoMapeada.getNombre(), aula.getNombre(), 
																	"Los nombres no coinciden"),
				() -> assertEquals(aulaDtoMapeada.getCapacidad(), aula.getCapacidad(), 
																	"Las capacidades no coinciden"),
				() -> assertEquals(aulaDtoMapeada.isProyector(), aula.isProyector(), 
																	"Los estados de proyector no coinciden"),
				() -> assertEquals(aulaDtoMapeada.isAltavoces(), aula.isAltavoces(), 
																	"Los estados de altavoces no coinciden")
		);
		
	}
}
