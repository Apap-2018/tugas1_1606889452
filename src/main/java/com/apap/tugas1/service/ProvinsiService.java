package com.apap.tugas1.service;

import java.util.Optional;

import com.apap.tugas1.model.ProvinsiModel;

public interface ProvinsiService {

	Optional<ProvinsiModel> getProvinsiById(long id);

	
	
}