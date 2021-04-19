package br.com.amarques.smartcookbook.crawler.service.smartcookcook;

import java.text.MessageFormat;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.amarques.smartcookbook.crawler.domain.Receita;
import br.com.amarques.smartcookbook.crawler.dto.IngredienteDTO;
import br.com.amarques.smartcookbook.crawler.dto.ReceitaDTO;
import br.com.amarques.smartcookbook.crawler.dto.SimpleEntityDTO;
import br.com.amarques.smartcookbook.crawler.repository.ReceitaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EnviaReceitaParaSmartcookbook {

    private final ReceitaRepository receitaRepository;
    private final RestTemplate restTemplate;

    @Value("${url.smartcookbook}")
    private String urlSmartcookbook;

    public void enviarReceitas() {
        long total = receitaRepository.count();
        long num = total % 1000;
        long contador = 0;
        String url = String.format("%s/receitas", urlSmartcookbook);

        log.info(String.format("Foram encontradas [%d] receitas cadastradas.", total));

        for (int i = 1; i < contador + num; i++) {
            PageRequest pageRequest = PageRequest.of(i, 1000);
            Page<Receita> receitas = receitaRepository.findAll(pageRequest);

            for (Receita receita : receitas) {
                if (Objects.isNull(receita.getNome())) {
                    log.warn(String.format("A receita [id: %s] não tem nome", receita.getId()));
                    return;
                }
                if (Objects.isNull(receita.getModoPreparo())) {
                    log.warn(String.format("A receita [id: %s] não tem modo de preparo", receita.getNome()));
                    return;
                }

                cadastrarReceita(receita, url);
            }

            contador += 1000;
        }
    }

    private void cadastrarReceita(final Receita receita, String url) {
        try {
            log.info(String.format("Enviando receita [nome: %s]", receita.getNome()));

            ReceitaDTO receitaDTO = new ReceitaDTO(receita.getNome(), receita.getModoPreparo());
            SimpleEntityDTO receitaCadastrada = restTemplate.postForObject(url,
                    new HttpEntity<>(receitaDTO), SimpleEntityDTO.class);

            if (Objects.nonNull(receitaCadastrada)) {
                for (String ingrediente : receita.getIngredientes()) {
                    IngredienteDTO ingredienteDTO = new IngredienteDTO(ingrediente);
                    HttpEntity<IngredienteDTO> ingredienteRequest = new HttpEntity<>(ingredienteDTO);

                    SimpleEntityDTO ingredienteCadastrado = restTemplate.postForObject(String.format(
                            "%s/%s/ingredientes", url, receitaCadastrada.id), ingredienteRequest,
                            SimpleEntityDTO.class);

                    if (Objects.isNull(ingredienteCadastrado)) {
                        log.error(String.format("Ocorreu um erro ao cadastrar o ingrediente [nome: %s]", ingrediente));
                    }
                }
            } else {
                log.error(String.format(MessageFormat.format(
                        "Ocorreu um erro ao cadastrar a receita [id: {0}]. Não será possível cadastrar os ingredientes.",
                        receita.getId())));
            }
        } catch (Exception e) {
            log.error(String.format("Ocorreu um erro ao cadastrar a receita. Message: %s", e.getMessage()));
        }
    }
}
