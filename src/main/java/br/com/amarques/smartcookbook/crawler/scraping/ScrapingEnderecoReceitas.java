package br.com.amarques.smartcookbook.crawler.scraping;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.amarques.smartcookbook.crawler.domain.EnderecoReceita;
import br.com.amarques.smartcookbook.crawler.exeception.AcessoPaginaException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScrapingEnderecoReceitas {

    private static final String URL_TUDO_GOSTOSO = "https://www.tudogostoso.com.br";
    private static final String URL_PAGINA_RECEITAS = URL_TUDO_GOSTOSO + "/receitas?cod=75144&page=";

    public List<EnderecoReceita> lerIndice(Integer numPagina) {
        log.info(String.format("Lendo página [%s] de receitas.", numPagina));

        Document page = null;
        try {
            page = Jsoup.connect(new StringBuilder(URL_PAGINA_RECEITAS).append(numPagina).toString()).get();
        } catch (IOException e) {
            throw new AcessoPaginaException(MessageFormat.format(
                    "ocorreu um erro ao acessar a página de receitas [número: {0}]. Message: {1}",
                    numPagina, e.getMessage()));
        }

        Elements linkDasReceitas = page.getElementsByClass("link row m-0");

        List<EnderecoReceita> enderecosReceitas = new ArrayList<>();

        for (Element element : linkDasReceitas) {
            String linkReceita = element.attr("href");
            enderecosReceitas.add(new EnderecoReceita(new StringBuilder(URL_TUDO_GOSTOSO)
                    .append(linkReceita)
                    .toString()));
        }

        return enderecosReceitas;
    }
}
