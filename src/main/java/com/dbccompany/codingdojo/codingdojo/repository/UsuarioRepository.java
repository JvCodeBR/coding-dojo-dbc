package com.dbccompany.codingdojo.codingdojo.repository;

import com.dbccompany.codingdojo.codingdojo.model.TipoUsuario;
import com.dbccompany.codingdojo.codingdojo.model.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {

     List<UsuarioEntity> findByTipo(TipoUsuario tipo);
     List<UsuarioEntity> findAllByIdadeGreaterThan(Integer idade);
     List<UsuarioEntity> findAllByDataCriacaoBefore(LocalDate data);
     @Query("SELECT u FROM USUARIO u where DATEDIFF(CURRENT_DATE, u.dataNascimento) / 365 >= 18")
     List<UsuarioEntity> buscarUsuariosMaioresDeIdade();
     Optional<UsuarioEntity> findByEmail(String username);
}
