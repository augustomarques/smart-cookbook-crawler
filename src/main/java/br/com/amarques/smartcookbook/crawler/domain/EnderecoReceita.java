package br.com.amarques.smartcookbook.crawler.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection = "enderecos_receitas")
public class EnderecoReceita {

    @Id
    private String id;

    @Indexed(unique = true)
    private String endereco;

    @Indexed
    private Boolean download;

    public EnderecoReceita(String endereco) {
        this.download = Boolean.FALSE;
        this.endereco = endereco;
    }

    public void marcarDownloadExecutado() {
        this.download = Boolean.TRUE;
    }
}
