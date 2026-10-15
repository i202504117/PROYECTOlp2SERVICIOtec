package com.techsolutions.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techsolutions.modelo.Rol;
import com.techsolutions.repository.IRolRepository;

@Service
public class RolImplement implements IRolService{
	 @Autowired
	    private IRolRepository repo;

	    public List<Rol> listarRoles() {
	        return repo.findAll();
	    }

}
