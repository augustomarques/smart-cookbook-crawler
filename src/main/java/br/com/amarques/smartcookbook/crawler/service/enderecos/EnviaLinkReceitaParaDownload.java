package br.com.amarques.smartcookbook.crawler.service.enderecos;

import java.util.List;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import br.com.amarques.smartcookbook.crawler.domain.EnderecoReceita;
import br.com.amarques.smartcookbook.crawler.repository.EnderecoReceitaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnviaLinkReceitaParaDownload {

    private final EnderecoReceitaRepository enderecoReceitaRepository;
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void buscaLinksPendentesEEnviaParaRabbit() {
        List<EnderecoReceita> receitasPendentes = enderecoReceitaRepository.findByDownload(Boolean.FALSE);

        for (EnderecoReceita enderecoReceita : receitasPendentes) {
            rabbitTemplate.convertAndSend(queue.getName(), enderecoReceita.getEndereco());
        }
    }
}
