package com.rci.omega2.ejb.mongo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.rci.omega2.mongo.entity.Domain;

public interface DomainRepository extends CrudRepository<Domain, String> {
    public List<Domain> findByDominioAndDescricao(int dominio,String descricao);
}
