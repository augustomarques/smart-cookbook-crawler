package br.com.amarques.smartcookbook.crawler.schedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.amarques.smartcookbook.crawler.service.enderecos.EnviaLinkReceitaParaDownload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class EnviaLinksPendentesParaDownloadSchedule {

    private final EnviaLinkReceitaParaDownload processaReceitasPendentes;

    @Scheduled(cron = "${cron-receitas-pendentes:0 0/1 * * * ?}")
    public void executa() {
        log.info("Iniciando CRON para processar receitas pendentes.");
        processaReceitasPendentes.buscaLinksPendentesEEnviaParaRabbit();
    }
}
