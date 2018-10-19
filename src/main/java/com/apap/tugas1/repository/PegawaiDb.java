package com.apap.tugas1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiDb extends JpaRepository<PegawaiModel, Long>{
	PegawaiModel findByNip(String nip);
	List<PegawaiModel> findAllByInstansiOrderByTanggalLahirAsc(InstansiModel instansi);
	List<PegawaiModel> findAllByInstansiOrderByTanggalLahirDesc(InstansiModel instansi);
}
