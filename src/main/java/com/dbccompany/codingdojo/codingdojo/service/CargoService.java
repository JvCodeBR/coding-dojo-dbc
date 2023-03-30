package com.dbccompany.codingdojo.codingdojo.service;

import com.dbccompany.codingdojo.codingdojo.exceptions.RegraDeNegocioException;
import com.dbccompany.codingdojo.codingdojo.model.CargoEntity;
import com.dbccompany.codingdojo.codingdojo.model.UsuarioEntity;
import com.dbccompany.codingdojo.codingdojo.repository.CargoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CargoService {
    private final CargoRepository repository;

//    protected void saveCargo(UsuarioEntity usuarioEntity) throws RegraDeNegocioException {
//        CargoEntity cargoEntity = pegaCargoPeloNome("ADMIN");
//        cargoEntity.getUsuarios().add(compradorEntity);
//        cargoRepository.save(cargoEntity);
//    }

    public CargoEntity pegaCargoPeloNome(String nome) throws RegraDeNegocioException {
        return this.repository.findByNome(nome)
                .orElseThrow(() -> new RegraDeNegocioException("Cargo não encontrado!"));
    }
}