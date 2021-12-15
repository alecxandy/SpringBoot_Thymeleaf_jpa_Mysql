package curso.spring.demo.controller;


import curso.spring.demo.model.Pessoa;
import curso.spring.demo.model.Telefone;
import curso.spring.demo.repository.PessoaRepository;
import curso.spring.demo.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class PessoaController {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private TelefoneRepository telefoneRepository;


    @RequestMapping(method = RequestMethod.GET, value = "/cadastroPessoa")
    public ModelAndView inicio() {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoaobj", new Pessoa());
        Iterable<Pessoa> pessoaIterable = pessoaRepository.findAll();
        modelAndView.addObject("pessoas", pessoaIterable);
        return modelAndView;
    }


    //salvando e redirecionando para apagina com um alista de pessoas
    @RequestMapping(method = RequestMethod.POST, value = "*/salvarPessoa")//um asterist iguinora tudo que vem antes
    public ModelAndView salvar(Pessoa pessoa) {
        pessoaRepository.save(pessoa);
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<Pessoa> pessoaIterable = pessoaRepository.findAll();
        modelAndView.addObject("pessoas", pessoaIterable);
        modelAndView.addObject("pessoaobj", new Pessoa());
        return modelAndView;
    }


    @RequestMapping(method = RequestMethod.GET, value = "listapessoas")
    public ModelAndView pessoa() {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        Iterable<Pessoa> pessoaIterable = pessoaRepository.findAll();
        modelAndView.addObject("pessoas", pessoaIterable);
        modelAndView.addObject("pessoaobj", new Pessoa());
        return modelAndView;
    }


    @GetMapping("/editarpessoa/{idpessoa}")
    public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(idpessoa);
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoaobj", pessoaOptional.get());
        return modelAndView;
    }


    @GetMapping("/removerpessoa/{idpessoa}")
    public ModelAndView remover(@PathVariable("idpessoa") Long idpessoa) {
        pessoaRepository.deleteById(idpessoa);
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoas", pessoaRepository.findAll());
        modelAndView.addObject("pessoaobj", new Pessoa());
        return modelAndView;
    }

    @PostMapping("/pesquisarpessoas")
    public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        modelAndView.addObject("pessoas", pessoaRepository.findPessoaByName(nomepesquisa));
        modelAndView.addObject("pessoaobj", new Pessoa());
        return modelAndView;
    }

    //metodo que direcionar para a tela de telefone lavando o objeto pessoa
    @GetMapping("telefonepessoa/{idpessoa}")
    public ModelAndView telefones(@PathVariable("idpessoa") Long idpessoa) {
        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(idpessoa);
        modelAndView.addObject("pessoaobj", pessoaOptional.get());
        modelAndView.addObject("telefones",telefoneRepository.getTelefones(idpessoa));
        return modelAndView;
    }

    @PostMapping("/addfonepessoa/{pessoaid}")
    public ModelAndView addFonePessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {
        Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
        telefone.setPessoa(pessoa);
        telefoneRepository.save(telefone);
        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        modelAndView.addObject("pessoaobj", pessoa);
        modelAndView.addObject("telefones",telefoneRepository.getTelefones(pessoaid));
        return modelAndView;
    }

}
