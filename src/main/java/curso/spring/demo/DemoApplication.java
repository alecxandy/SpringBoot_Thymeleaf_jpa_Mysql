package curso.spring.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "curso.spring.demo") //scanear as entidades a partir desse pacote
@ComponentScan(basePackages = {"curso.*"}) // força a scanear todos os pacotes do projeto
@EnableJpaRepositories(basePackages = {"curso.spring.demo.repository"}) //passando o caminho do pacote repository
@EnableTransactionManagement
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


}
