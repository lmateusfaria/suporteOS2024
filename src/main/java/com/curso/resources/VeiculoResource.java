package com.curso.resources;

import com.curso.domains.GrupoProduto;
import com.curso.domains.Produto;
import com.curso.domains.dtos.GrupoProdutoDTO;
import com.curso.domains.dtos.ProdutoDTO;
import com.curso.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/produto")
public class ProdutoResource {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping //exemplo http://localhost:8080/produto
    public ResponseEntity<List<ProdutoDTO>> findAll(){
        return ResponseEntity.ok().body(produtoService.findAll());
    }

    @GetMapping(value = "/{id}") //exemplo http://localhost:8080/produto/1
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id){
        Produto obj = this.produtoService.findbyId(id);
        return ResponseEntity.ok().body(new ProdutoDTO(obj));
    }

    @GetMapping(value = "/codigobarra/{codigoBarra}") //exemplo http://localhost:8080/produto/codigobarra/1
    public ResponseEntity<ProdutoDTO> findById(@PathVariable String codigoBarra){
        Produto obj = this.produtoService.findbyCodigoBarra(codigoBarra);
        return ResponseEntity.ok().body(new ProdutoDTO(obj));
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> create(@Valid @RequestBody ProdutoDTO dto) {
        Produto produto = produtoService.create(dto);
        // Cria o URI para o recurso criado
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(produto.getIdProduto()).toUri();
        // Retorna a resposta com o status 201 Created e o local do recurso criado
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProdutoDTO> update(@PathVariable Long id, @Valid @RequestBody ProdutoDTO objDto){
        Produto Obj = produtoService.update(id, objDto);
        return ResponseEntity.ok().body(new ProdutoDTO(Obj));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<GrupoProdutoDTO> delete(@PathVariable Long id){
        produtoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
