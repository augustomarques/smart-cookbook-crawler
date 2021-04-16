package br.com.amarques.smartcookbook.crawler.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.amarques.smartcookbook.crawler.service.receitas.ReceitaService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LinkReceitaListener {

    private final ReceitaService receitaService;

    @RabbitListener(queues = {"${queue.receita.name}"})
    public void receive(@Payload String linkReceita) {
        receitaService.fazerDownloadDaReceita(linkReceita);
    }
}
