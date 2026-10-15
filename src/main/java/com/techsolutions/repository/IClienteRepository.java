package com.techsolutions.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.techsolutions.modelo.Cliente;

public interface IClienteRepository extends JpaRepository<Cliente, Integer> {

    Cliente findByDni(String dni);

    Cliente findByDniAndActivoTrue(String dni);

    List<Cliente> findByDniContainingAndActivoTrue(String dni);

    List<Cliente> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);


    List<Cliente> findByActivoTrue();
    
}