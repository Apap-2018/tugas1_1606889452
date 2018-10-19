package com.apap.tugas1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface InstansiDb extends JpaRepository<InstansiModel, Long> {
	InstansiModel findById(int id);

}
