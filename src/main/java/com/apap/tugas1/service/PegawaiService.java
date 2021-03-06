package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	PegawaiModel getPegawaiDetailByNip(String nip);
	double hitungGaji(PegawaiModel pegawai);
	long hitungUmur (PegawaiModel pegawai);
	List<PegawaiModel> getPegawaiMuda(InstansiModel instansi);
	List<PegawaiModel> getPegawaiTua(InstansiModel instansi);
	void addPegawai(PegawaiModel pegawai);
	void deletePegawai(PegawaiModel pegawai);
	void updatePegawai(PegawaiModel pegawai);
	List<PegawaiModel> findAllPegawai();
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);
	String generateNip(PegawaiModel pegawai);

}
