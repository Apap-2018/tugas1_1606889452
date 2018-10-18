package com.apap.tugas1.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
@Transactional
public class JabatanServiceImp implements JabatanService{

	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public JabatanModel getJabatanById(long id) {
		return jabatanDb.findById(id);
	}

	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
		
	}

	@Override
	public void deleteJabatan(JabatanModel jabatan) {
		jabatanDb.delete(jabatan);
		
	}

	@Override
	public void updateJabatan(JabatanModel jabatan, long id) {
		JabatanModel target = jabatanDb.findById(id);
		target.setNama(jabatan.getNama());
		target.setDeskripsi(jabatan.getDeskripsi());
		target.setGajiPokok(jabatan.getGajiPokok());
		jabatanDb.save(target);
	}

}
