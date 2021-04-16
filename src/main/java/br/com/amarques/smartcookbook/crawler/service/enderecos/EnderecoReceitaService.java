package br.com.amarques.smartcookbook.crawler.service.enderecos;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnderecoReceitaService {

    private final ScrapingEnderecoReceitasService scrapingReceitasService;

    public void cadastrarReceitas() {
        log.info("Buscando links de Receitas no TudoGostoso...");
        for (int i = 1; i <= 13009; i++) {
            scrapingReceitasService.buscarECadastrarPaginasDeReceitas(i);
        }
    }
}
