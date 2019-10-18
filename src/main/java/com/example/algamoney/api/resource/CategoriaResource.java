//Classe que vai expor tudo o que esta relacionado ao recurso categoria, ele é um controlador

package com.example.algamoney.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.event.RecursoCriadoEvent;
import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@RestController //Controlador Rest, o retorno já é convertido pra JSON
@RequestMapping("/categorias") //mapeamento da requisição
public class CategoriaResource {
	
	@Autowired //Ache uma implementação de categoria repository e injete dentro (Injeção de dependencia)
	private CategoriaRepository categoriaRepository; //Injetado
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@GetMapping //mapeamento do verbo GET
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')") //permissao do usuário logado e escopo de leitura para o client
	public List<Categoria> listar() { //Entidade de resposta
		return categoriaRepository.findAll();		
	}
	
	@PostMapping //Mapeamento do verbo POST
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')") //permissao do usuário logado e escopo de escrita para o client
	//@ResponseStatus(HttpStatus.CREATED) //Retorna o estado de criado
	public  ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) { //@RequestBody Pega o JSON no post e retorna a nova Categoria que está sendo adicionada 
		Categoria categoriaSalva = categoriaRepository.save(categoria); //Guarda a nova categoria salva
		
		//dispara o evento que o listener está ouvindo
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		
		//Retorna o estado de criado e também já devolve o corpo do recurso criado
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);		
	}
	
	@GetMapping("/{codigo}") //{codigo} = Parâmetro
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')") //permissao do usuário logado e escopo de leitura para o client
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
		
		/*Map para transformar o objeto que foi retornado como Optional (isso é feito caso o mesmo não seja null), 
		 *depois retorna o objeto transformado em Optional novamente. Como o retorno do próprio map também é um Optional,
		 *posso utilizar o método orElse para retornar notFound.*/
		return categoriaRepository.findById(codigo)
		    	.map(categoria -> ResponseEntity.ok(categoria))
				.orElse(ResponseEntity.notFound().build());
	}
}
