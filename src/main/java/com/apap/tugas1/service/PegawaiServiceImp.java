package com.apap.tugas1.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
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

	@Override
	public long hitungUmur(PegawaiModel pegawai) {
		
		try {
			SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
			String tanggalAcuan = "30 12 2050";
			Date dateAcuan = myFormat.parse(tanggalAcuan);
			long umur = dateAcuan.getTime() - pegawai.getTanggalLahir().getTime();
			return umur;
		}
		catch (ParseException e){
			return (long) 0;
		}
	}

	@Override
	public List<PegawaiModel> getPegawaiMuda(InstansiModel instansi) {
		return pegawaiDb.findAllByInstansiOrderByTanggalLahirDesc(instansi);
	}

	@Override
	public List<PegawaiModel> getPegawaiTua(InstansiModel instansi) {
		return pegawaiDb.findAllByInstansiOrderByTanggalLahirDesc(instansi);
	}

	@Override
	public void addPegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
		
	}

	@Override
	public void deletePegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
		
	}

	@Override
	public void updatePegawai(PegawaiModel pegawai) {
		pegawaiDb.save(pegawai);
		
	}

	@Override
	public List<PegawaiModel> findAllPegawai() {
		return pegawaiDb.findAll();
	}

	@Override
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		return pegawaiDb.findByInstansi(instansi);
	}

	@Override
	public String generateNip(PegawaiModel pegawai) {
		DateFormat df = new SimpleDateFormat("ddMMYY");
		Date tglLahir = pegawai.getTanggalLahir();
		String formatted = df.format(tglLahir);
		System.out.println("date->"+formatted);
		
		Long kodeInstansi = pegawai.getInstansi().getId();
		System.out.println("kode instansi->"+kodeInstansi);
		
		int idAkhir = 0;
		for (PegawaiModel peg : findAllPegawai()) {
			if (peg.getTanggalLahir().equals(pegawai.getTanggalLahir()) && peg.getTahunMasuk().equals(pegawai.getTahunMasuk())) {
				idAkhir+=1;
			}
		}
		idAkhir+=1;
		
		String kodeMasuk = "";
		if (idAkhir<10) {
			kodeMasuk = "0"+idAkhir;
		}
		else {
			kodeMasuk = Integer.toString(idAkhir);
		}
		
		System.out.println(kodeInstansi+formatted+pegawai.getTahunMasuk()+kodeMasuk);
		return kodeInstansi+formatted+pegawai.getTahunMasuk()+kodeMasuk;
	}
}
