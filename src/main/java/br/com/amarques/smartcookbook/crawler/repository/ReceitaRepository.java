package br.com.amarques.smartcookbook.crawler.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.amarques.smartcookbook.crawler.domain.Receita;

@Repository
public interface ReceitaRepository extends MongoRepository<Receita, String> {

}
