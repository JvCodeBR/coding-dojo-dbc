package com.dbccompany.codingdojo.codingdojo.repository;

import com.dbccompany.codingdojo.codingdojo.model.CargoEntity;
import com.dbccompany.codingdojo.codingdojo.model.TipoUsuario;
import com.dbccompany.codingdojo.codingdojo.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<CargoEntity, Integer> {
    Optional<CargoEntity> findByNome(String nome);
}
