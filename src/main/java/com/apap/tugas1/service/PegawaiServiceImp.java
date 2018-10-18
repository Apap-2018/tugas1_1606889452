package com.apap.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
@Transactional
public class PegawaiServiceImp implements PegawaiService {
	@Autowired
	private PegawaiDb pegawaiDb;

	@Override
	public PegawaiModel getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}

	@Override
	public double hitungGaji(PegawaiModel pegawai) {
		double gajiPokokFix=0;
		double gajiFix=0;
		
		for(JabatanModel jabatan : pegawai.getJabatanList()) {
			double gajiPokok = jabatan.getGajiPokok();

			if(gajiPokok > gajiPokokFix) {
				gajiPokokFix=gajiPokok;

			}
			gajiFix= gajiPokokFix + ((pegawai.getInstansi().getProvinsi().getPresentaseTunjangan()/100)*gajiPokokFix);

		}
		return gajiFix;
		
	}

	

}
