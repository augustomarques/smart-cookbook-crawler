package br.com.amarques.smartcookbook.crawler.scraping;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.io.IOUtils;
import org.bson.types.Binary;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.amarques.smartcookbook.crawler.domain.Receita;
import br.com.amarques.smartcookbook.crawler.exeception.AcessoPaginaException;
import br.com.amarques.smartcookbook.crawler.exeception.DownloadImagemException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScrapingReceita {

    public Receita getReceita(final String link) {
        log.info(String.format("Acessando página [%s]", link));

        Document page = null;
        try {
            page = Jsoup.connect(link).get();
        } catch (IOException e) {
            throw new AcessoPaginaException(MessageFormat.format(
                    "Ocorreu um erro ao acessar a página [link: {0}]. Message: {1}", link, e.getMessage()));
        }

        final String nomeReceita = page.getElementsByClass("recipe-title").select("h1").first().text();

        Elements ingredientes = page.getElementsByClass("p-ingredient");
        final List<String> listaIngredientes = new ArrayList<>();
        for (Element ingrediente : ingredientes) {
            listaIngredientes.add(ingrediente.text());
        }

        final String modoPreparo = page.getElementsByClass("e-instructions").html();

        Binary imagem = null;
        final Elements picElement = page.getElementsByClass("pic");
        if (!picElement.isEmpty()) {
            final String urlImagem = picElement.select("img").first().attr("src");

            try {
                byte[] fileContent = IOUtils.toByteArray(new URL(urlImagem));
                imagem = new Binary(fileContent);
            } catch (IOException e) {
                throw new DownloadImagemException(String.format(
                        "Ocorreu um erro ao realizar o download da imagem [URL: %s]", urlImagem));
            }
        }

        Receita receita = new Receita();
        receita.setNome(nomeReceita);
        receita.setModoPreparo(modoPreparo);
        receita.setUrlOriginal(link);
        receita.setIngredientes(listaIngredientes);

        if (Objects.nonNull(imagem)) {
            receita.setImagem(imagem);
        }

        return receita;
    }
}
