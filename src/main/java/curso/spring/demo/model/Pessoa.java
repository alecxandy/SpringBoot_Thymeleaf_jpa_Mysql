package curso.spring.demo.model;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
public class Pessoa implements Serializable {

    public Pessoa() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Nome não pode ser nulo")
    @NotEmpty(message = "nome nao pode ser vazio")
    private String nome;

    @NotNull(message = "sobrenome não pode ser nulo")
    @NotEmpty(message = "sobrenome nao pode ser vazio")
    private String sobrenome;

    @Min(value = 18, message = "idade nao pode ser menor que 18")
    private int idade;

    @OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Telefone> telefoneList;

    public List<Telefone> getTelefoneList() {
        return telefoneList;
    }

    public void setTelefoneList(List<Telefone> telefoneList) {
        this.telefoneList = telefoneList;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pessoa)) return false;
        Pessoa pessoa = (Pessoa) o;
        return getIdade() == pessoa.getIdade() && Objects.equals(getId(), pessoa.getId()) && Objects.equals(getNome(), pessoa.getNome()) && Objects.equals(getSobrenome(), pessoa.getSobrenome()) && Objects.equals(getTelefoneList(), pessoa.getTelefoneList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getSobrenome(), getIdade(), getTelefoneList());
    }
}
