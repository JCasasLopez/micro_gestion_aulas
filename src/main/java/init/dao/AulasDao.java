package init.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import init.entities.Aula;

public interface AulasDao extends JpaRepository<Aula, Integer> {

}
