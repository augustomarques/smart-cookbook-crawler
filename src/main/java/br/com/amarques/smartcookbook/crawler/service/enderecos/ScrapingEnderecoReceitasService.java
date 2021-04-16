package br.com.amarques.smartcookbook.crawler.service.enderecos;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.amarques.smartcookbook.crawler.domain.EnderecoReceita;
import br.com.amarques.smartcookbook.crawler.repository.EnderecoReceitaRepository;
import br.com.amarques.smartcookbook.crawler.scraping.ScrapingEnderecoReceitas;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScrapingEnderecoReceitasService {

    private final EnderecoReceitaRepository enderecoReceitaRepository;

    @Async
    public void buscarECadastrarPaginasDeReceitas(final Integer numPagina) {
        final List<EnderecoReceita> enderecosReceitas = new ScrapingEnderecoReceitas().lerIndice(numPagina);
        enderecoReceitaRepository.saveAll(enderecosReceitas);
    }
}
