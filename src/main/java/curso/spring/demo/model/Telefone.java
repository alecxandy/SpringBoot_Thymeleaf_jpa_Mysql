package curso.spring.demo.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Telefone {

    public Telefone() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String numero;

    private String tipo;

    @ManyToOne(cascade = CascadeType.ALL)
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Telefone)) return false;
        Telefone telefone = (Telefone) o;
        return Objects.equals(getId(), telefone.getId()) && Objects.equals(getNumero(), telefone.getNumero()) && Objects.equals(getTipo(), telefone.getTipo()) && Objects.equals(getPessoa(), telefone.getPessoa());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumero(), getTipo(), getPessoa());
    }
}
