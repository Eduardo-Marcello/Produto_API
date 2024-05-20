package br.com.marcello.produtoapi.service;

import br.com.marcello.produtoapi.exception.ResourceNotFoundException;
import br.com.marcello.produtoapi.model.Produto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class ProdutoService {

    List<Produto> produtos = initValues();

    private List<Produto> initValues() {
        List<Produto> produtos = new ArrayList<Produto>();
        produtos.add(new Produto(1, "12", "Pão", "Padaraia", 0.75));
        produtos.add(new Produto(2, "15", "Presunto", "Frios", 2.75));

        return produtos;
    }

    private boolean resourceNotFound(String code){
        return produtos.stream().filter(produto -> Objects.equals(produto.getCodigo(), code)).findFirst().isEmpty() &&
                !code.matches("[0-9]*") &&
                code.length() < 2;
    }

    private int findId(String codigo){
        int produtoId = -1;
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getCodigo().equals(codigo)) {
                produtoId = i;
                break;
            }
        }
        return produtoId;
    }

    public List<Produto> getAll(){
        return this.produtos;
    }

    public Produto getById(int id) {
        if(id <= 0){
            throw new ResourceNotFoundException("Valor Inválido - Id inexistente!");
        } else {
            return this.produtos.get(id-1);
        }

    }

    public List<Produto> getByCod(String codigo) {
        System.out.println(resourceNotFound(codigo));
        if (resourceNotFound(codigo)) {
            throw new ResourceNotFoundException("Valor Inválido - Codigo inexistente!");
        } else {
            return getAll().stream().filter(voo -> voo.getCodigo().startsWith(codigo)).toList();
        }
    }

    public void save(Produto produto) {
        produtos.add(produto);
    }

    public void update(String codigo, Produto produto) {
        if(resourceNotFound(codigo)){
            throw new ResourceNotFoundException("Voo não encontrado!");
        } else {
            produtos.set(findId(codigo), produto);
        }
    }

    public void deleteByCod(String codigo) {
        if(resourceNotFound(codigo)){
            throw new ResourceNotFoundException("Voo não encontrado!");
        } else {
            produtos.remove(produtos.get(findId(codigo)));
        }

    }

}
