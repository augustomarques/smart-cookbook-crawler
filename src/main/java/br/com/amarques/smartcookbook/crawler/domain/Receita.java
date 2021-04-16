package br.com.amarques.smartcookbook.crawler.domain;

import java.util.List;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "receitas")
public class Receita {

    @Id
    private String id;

    private String nome;

    private List<String> ingredientes;

    private String modoPreparo;

    private Binary imagem;

    private String urlOriginal;

}
