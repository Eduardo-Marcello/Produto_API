package br.com.marcello.produtoapi.controller;

import br.com.marcello.produtoapi.exception.ResourceNotFoundException;
import br.com.marcello.produtoapi.model.Produto;
import br.com.marcello.produtoapi.payload.MessagePayload;
import br.com.marcello.produtoapi.service.ProdutoService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/produto")
public class ProdutoController {

    final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Produto>> getAll(@RequestParam(required = false) Optional<String> codigo){
        if(codigo.isEmpty()){
            return ResponseEntity.ok(produtoService.getAll());
        } else {
            List<Produto> voos = produtoService.getByCod(codigo.get());
            if(voos.isEmpty()){
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(voos);
            }
        }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Produto> getById(@PathVariable int id){
        try {
            return ResponseEntity.ok(produtoService.getById(id));
        }catch (ResourceNotFoundException ex){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public void save(@RequestBody Produto produto){
        produtoService.save(produto);

    }

    @PutMapping("/update/{codigo}")
    public ResponseEntity<MessagePayload> update(@PathVariable String codigo, @RequestBody Produto produto){
        try {
            produtoService.update(codigo, produto);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Voo alterado com sucesso!"));
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }

    @DeleteMapping("/delete/{codigo}")
    public ResponseEntity<MessagePayload> deleteByCod(@PathVariable String codigo){
        try {
            produtoService.deleteByCod(codigo);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessagePayload("Voo deletado com sucesso!"));
        } catch (ResourceNotFoundException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessagePayload(ex.getMessage()));
        }
    }

}
