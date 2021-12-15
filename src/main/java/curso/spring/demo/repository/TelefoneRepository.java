package curso.spring.demo.repository;

import curso.spring.demo.model.Telefone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TelefoneRepository extends CrudRepository<Telefone, Long> {

    @Query("select t from Telefone t where t.pessoa.id = ?1")
    public List<Telefone> getTelefones(Long pessoaid);
}
