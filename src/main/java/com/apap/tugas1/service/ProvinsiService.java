package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.ProvinsiModel;


public interface ProvinsiService {

	ProvinsiModel getProvinsiById(long id);
	List<ProvinsiModel> findAllProvinsi();

	
	
}