package br.com.marcello.produtoapi.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Produto {
    @NotBlank
    private int id;
    @NotBlank @Size(min = 2, max = 10)
    private String codigo;
    private String nome;
    private String descricao;
    private double preco;

}
