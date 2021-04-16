package br.com.amarques.smartcookbook.crawler.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
public class ReceitaDTO {

    @JsonProperty("nome")
    public final String nome;
    @JsonProperty("modo_preparo")
    public final String modoPreparo;

}
