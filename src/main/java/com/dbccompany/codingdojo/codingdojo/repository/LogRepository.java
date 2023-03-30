package com.dbccompany.codingdojo.codingdojo.repository;

import com.dbccompany.codingdojo.codingdojo.model.LogEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends MongoRepository<LogEntity, String> {
}
