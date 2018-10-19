package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface InstansiService {
	List<InstansiModel> getAll();
	PegawaiModel getTua(InstansiModel instansi);
	PegawaiModel getMuda(InstansiModel instansi);
	InstansiModel getInstansiById(Long id);

}
