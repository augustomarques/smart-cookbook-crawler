package br.com.amarques.smartcookbook.crawler.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.amarques.smartcookbook.crawler.domain.EnderecoReceita;

@Repository
public interface EnderecoReceitaRepository extends MongoRepository<EnderecoReceita, String> {

    Optional<EnderecoReceita> findByEndereco(String endereco);

    List<EnderecoReceita> findByDownload(Boolean status);
}
