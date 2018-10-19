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

}
