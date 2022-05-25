package curso.spring.demo.controller;


import curso.spring.demo.model.Pessoa;
import curso.spring.demo.model.Telefone;
import curso.spring.demo.repository.PessoaRepository;
import curso.spring.demo.repository.TelefoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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


    //salvando e redirecionando para apagina com uma lista de pessoas
    @RequestMapping(method = RequestMethod.POST, value = "*/salvarPessoa")//um asterist iguinora tudo que vem antes
    public ModelAndView salvar(@Valid Pessoa pessoa, BindingResult bindingResult) {//BindingResult :usado para retorna a msg de validação


        //se estiver erro na validação nao salva e retorna pra mesma tela informando o erro
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
            Iterable<Pessoa> pessoaIterable = pessoaRepository.findAll();
            modelAndView.addObject("pessoas", pessoaIterable);
            modelAndView.addObject("pessoaobj", new Pessoa());
            List<String> msg = new ArrayList<String>();
            for (ObjectError objectError : bindingResult.getAllErrors()) {
                msg.add(objectError.getDefaultMessage()); // vem das anotações @NotEmply
            }
            modelAndView.addObject("msg", msg);
            return modelAndView;
        } else {
            pessoaRepository.save(pessoa);
            ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
            Iterable<Pessoa> pessoaIterable = pessoaRepository.findAll();
            modelAndView.addObject("pessoas", pessoaIterable);
            modelAndView.addObject("pessoaobj", new Pessoa());
            return modelAndView;
        }
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
        ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
        Optional<Pessoa> pessoaOptional = pessoaRepository.findById(idpessoa);
        modelAndView.addObject("pessoas", pessoaRepository.findAll());
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
        modelAndView.addObject("telefones", telefoneRepository.getTelefones(idpessoa));
        return modelAndView;
    }

    @PostMapping("/addfonepessoa/{pessoaid}")
    public ModelAndView addFonePessoa(Telefone telefone, @PathVariable("pessoaid") Long pessoaid) {

        Pessoa pessoa = pessoaRepository.findById(pessoaid).get();

        if ((telefone != null && (telefone.getNumero() != null) && telefone.getNumero().isEmpty()
                && (telefone.getTipo() != null) && telefone.getTipo().isEmpty())) {

            ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
            modelAndView.addObject("pessoaobj", pessoa);
            modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));


            List<String> msg = new ArrayList<String>();
            msg.add("nao pode conter campos  campos vazios");
            modelAndView.addObject("msg", msg);

            return modelAndView;
        } else {

            assert telefone != null;
            telefone.setPessoa(pessoa);
            telefoneRepository.save(telefone);
            ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
            modelAndView.addObject("pessoaobj", pessoa);
            modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
            return modelAndView;
        }
    }

    @GetMapping("/removertelefone/{idtelefone}")
    public ModelAndView removerTelefone(@PathVariable("idtelefone") Long idtelefone) {
        Pessoa pessoa = telefoneRepository.findById(idtelefone).get().getPessoa();
        telefoneRepository.deleteById(idtelefone);
        ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
        modelAndView.addObject("pessoaobj", pessoa);
        modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));
        return modelAndView;
    }


}
