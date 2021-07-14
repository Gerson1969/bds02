package com.devsuperior.bds02.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.devsuperior.bds02.dto.CityDTO;
import com.devsuperior.bds02.entities.City;
import com.devsuperior.bds02.repositories.CityRepository;
import com.devsuperior.bds02.services.exception.DataBaseException;
import com.devsuperior.bds02.services.exception.ResourceNotFoundException;

@Service
public class CityServices {
	@Autowired
	private CityRepository repository;
	
	public List<CityDTO> findAll() {
		List<City> list = repository.findAll(Sort.by("name"));
		return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);			
		}
		catch (EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Id Not Found" + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity Violation");
		}
	}
}
