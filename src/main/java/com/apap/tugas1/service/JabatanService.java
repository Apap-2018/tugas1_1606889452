package com.apap.tugas1.service;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	JabatanModel getJabatanById(long id);
	void addJabatan(JabatanModel jabatan);
	void deleteJabatan(JabatanModel jabatan);
	void updateJabatan(JabatanModel jabatan, long id);
}
