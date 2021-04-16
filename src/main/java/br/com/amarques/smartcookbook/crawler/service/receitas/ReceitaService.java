package br.com.amarques.smartcookbook.crawler.service.receitas;

import java.util.Optional;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.amarques.smartcookbook.crawler.domain.EnderecoReceita;
import br.com.amarques.smartcookbook.crawler.domain.Receita;
import br.com.amarques.smartcookbook.crawler.repository.EnderecoReceitaRepository;
import br.com.amarques.smartcookbook.crawler.repository.ReceitaRepository;
import br.com.amarques.smartcookbook.crawler.scraping.ScrapingReceita;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReceitaService {

    private final ReceitaRepository receitaRepository;
    private final EnderecoReceitaRepository enderecoReceitaRepository;

    @Async
    public void fazerDownloadDaReceita(final String linkReceita) {
        final Receita receita = new ScrapingReceita().getReceita(linkReceita);
        receitaRepository.save(receita);

        final Optional<EnderecoReceita> enderecoReceitaOptional = enderecoReceitaRepository.findByEndereco(linkReceita);
        if (enderecoReceitaOptional.isPresent()) {
            final EnderecoReceita enderecoReceita = enderecoReceitaOptional.get();
            enderecoReceita.marcarDownloadExecutado();
            enderecoReceitaRepository.save(enderecoReceita);
        } else {
            log.info(String.format("A receita [link: %s] não foi encontrada, pornato seu status não foi atualizado.",
                    linkReceita));
        }
    }
}
