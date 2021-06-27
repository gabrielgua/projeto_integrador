package com.opet.pi.restControllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.opet.pi.model.Jogo;
import com.opet.pi.repository.JogoRepository;


@RestController
public class JogoRestController {
	
	@Autowired
	private JogoRepository repository;
	
	@GetMapping("/jogos")
	public List<Jogo> listar() {
		return repository.findAll();
	
	}
	
	@PostMapping("/jogos")
	@ResponseStatus(HttpStatus.CREATED)
	public Jogo adicionarJogo(@RequestBody Jogo jogo) {
		return repository.save(jogo);
	}
	
	@GetMapping("/jogos/{id}")
	
	public Jogo listarUmJogo(@PathVariable Long id) {
		return repository
				.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
	@PostMapping("/jogos/{id}")
	public ResponseEntity<Jogo> editar(@RequestBody Jogo jogo, @PathVariable Long id) {
		
		return repository.findById(id)
					.map(j -> {
		               j.setNome(jogo.getNome());
		               j.setCategoria(jogo.getCategoria());
		               Jogo updated = repository.save(j);
		               return ResponseEntity.ok().body(updated);
		           }).orElse(ResponseEntity.notFound().build());
		
	}
	
	@DeleteMapping("/jogos/{id}")
	public void deletarJogo(@PathVariable Long id) {
		repository.deleteById(id);
	}
}
