package com.apap.tugas1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.PegawaiDb;
import com.apap.tugas1.repository.ProvinsiDb;

@Service
@Transactional
public class ProvinsiServiceImp implements ProvinsiService {
	private PegawaiDb pegawaiDb;
	private InstansiDb instansiDb;
	private PegawaiModel pegawaiModel;
	private InstansiModel instansiModel;
	private ProvinsiModel provinsiModel;
	
	@Autowired
	private ProvinsiDb provinsiDb;
	
	@Override
	public Optional<ProvinsiModel> getProvinsiById(long id) {
		return provinsiDb.findById(id);
	}
	
}