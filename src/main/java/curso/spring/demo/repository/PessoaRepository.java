package curso.spring.demo.repository;

import curso.spring.demo.model.Pessoa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

    @Query("select p from Pessoa p where p.nome like %?1%") //primeiro parametro
    List<Pessoa> findPessoaByName(String nome);

}
