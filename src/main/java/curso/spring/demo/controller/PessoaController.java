package curso.spring.demo.controller;

import curso.spring.demo.model.Pessoa;
import curso.spring.demo.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/cadastroPessoa")
    public String inicio() {
        return "cadastro/cadastropessoa";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/salvarPessoa")
    public String salvar(Pessoa pessoa) {
        pessoaRepository.save(pessoa);
        return "cadastro/cadastropessoa";
    }

    @RequestMapping(method = RequestMethod.GET, value = "listapessoas")
    public ModelAndView pessoa() {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<Pessoa> pessoaIterable = pessoaRepository.findAll();
        modelAndView.addObject("pessoas", pessoaIterable);
        return modelAndView;
    }

}
