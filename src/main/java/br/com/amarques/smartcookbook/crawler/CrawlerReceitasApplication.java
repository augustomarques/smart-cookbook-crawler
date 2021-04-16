package br.com.amarques.smartcookbook.crawler;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import br.com.amarques.smartcookbook.crawler.service.enderecos.EnderecoReceitaService;
import lombok.RequiredArgsConstructor;

@EnableScheduling
@SpringBootApplication
@RequiredArgsConstructor
public class CrawlerReceitasApplication implements CommandLineRunner {

    private final EnderecoReceitaService enderecoReceitaService;

    public static void main(String[] args) {
        SpringApplication.run(CrawlerReceitasApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        enderecoReceitaService.cadastrarReceitas();
    }

}
