package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.InstansiDb;

@Service
@Transactional
public class InstansiServiceImp implements InstansiService {

	@Autowired
	private InstansiDb instansiDb;
	
	@Autowired
	private PegawaiService pegServ;
	
	@Override
	public List<InstansiModel> getAll() {
		return instansiDb.findAll();
		
	}
	@Override
	public PegawaiModel getTua(InstansiModel instansi) {
		PegawaiModel pegawaiTua = new PegawaiModel();
		long umurTua = 0;
		
		for(PegawaiModel pegawai : instansi.getPegawaiInstansi()) {
			long umurTemp = pegServ.hitungUmur(pegawai);
			if (umurTemp > umurTua) {
				umurTua=umurTemp;
				pegawaiTua=pegawai;
			}
		}
		return pegawaiTua;
	}
	
	@Override
	public PegawaiModel getMuda(InstansiModel instansi) {
		PegawaiModel pegawaiMuda = new PegawaiModel();
		long umurMuda = 100;
		
		for(PegawaiModel pegawai : instansi.getPegawaiInstansi()) {
			long umurTemp = pegServ.hitungUmur(pegawai);
			if (umurTemp < umurMuda) {
				umurMuda=umurTemp;
				pegawaiMuda=pegawai;
			}
		}
		return pegawaiMuda;
	}

}
