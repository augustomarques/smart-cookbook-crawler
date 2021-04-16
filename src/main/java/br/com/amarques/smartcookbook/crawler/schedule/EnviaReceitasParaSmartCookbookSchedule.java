package br.com.amarques.smartcookbook.crawler.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.amarques.smartcookbook.crawler.service.smartcookcook.EnviaReceitaParaSmartcookbook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnviaReceitasParaSmartCookbookSchedule {

    private final EnviaReceitaParaSmartcookbook enviaReceitaParaSmartcookbook;

    @Scheduled(cron = "${cron-envia-smartcookbook:0 0/5 * * * ?}")
    public void executa() {
        log.info("Iniciando CRON para enviar receitas para o Smartcookbook.");
        enviaReceitaParaSmartcookbook.enviarReceitas();
    }
}
