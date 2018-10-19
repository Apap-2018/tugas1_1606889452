package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.PegawaiDb;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class PegawaiController {
 
	@Autowired
	private PegawaiService pegawaiService;

	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private InstansiDb instansiDb;
	
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Autowired
	private ProvinsiDb provinsiDb;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("listInstansi", instansiService.getAll());	
		model.addAttribute("listJabatan", jabatanService.getAll());	
		return "home";
	}
	
	@RequestMapping(value="/pegawai", method = RequestMethod.GET)
	private String viewPegawai(@RequestParam("nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip);
		List<JabatanModel> listJabatan = pegawai.getJabatanList();
		double gaji = pegawaiService.hitungGaji(pegawai);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("gaji", (int) gaji);
		model.addAttribute("listJabatan", listJabatan);
		return "view-pegawai";
	}
	@RequestMapping(value="/pegawai/tuamuda", method = RequestMethod.GET)
	private String viewTuaMuda(@RequestParam("idInstansi") long instansiId, Model model) {
		InstansiModel instansi = instansiService.getInstansiById(instansiId);
		List<PegawaiModel> listPegawaiMuda = pegawaiService.getPegawaiMuda(instansi);
		List<PegawaiModel> listPegawaiTua = pegawaiService.getPegawaiTua(instansi);
		PegawaiModel pegawaiMuda = listPegawaiMuda.get(0);
		PegawaiModel pegawaiTua = listPegawaiTua.get(0);
		String namaTua = listPegawaiTua.get(0).getNama();
		String namaInstansiMuda = pegawaiMuda.getInstansi().getNama();
		String namaInstansiTua = pegawaiTua.getInstansi().getNama();
		List<JabatanModel> listJabatanMuda = pegawaiMuda.getJabatanList();
		List<JabatanModel> listJabatanTua = pegawaiTua.getJabatanList();
		model.addAttribute("pegawaiMuda", pegawaiMuda);
		model.addAttribute("pegawaiTua", pegawaiTua);
		model.addAttribute("namaInstansiMuda", namaInstansiMuda);
		model.addAttribute("namaInstansiTua", namaInstansiTua);
		model.addAttribute("listJabatanMuda", listJabatanMuda);
		model.addAttribute("listJabatanTua", listJabatanTua);
		return "view-tua-muda";
	}
	
	@RequestMapping("/pegawai/tambah")
	private String tambahPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		if(pegawai.getJabatanList()==null) {
			pegawai.setJabatanList(new ArrayList());
		}
	}
	pegawai.getJabatanList().add(new JabatanModel());
	List<ProvinsiModel> provinsiList =provinsiDb.findAll();
	List<JabatanModel> jabatanList = jabatanDb.findAll();
	model.addAttribute("jabatanList",jabatanList);
	model.addAttribute("pegawai",pegawai);
	model.addAttribute("provinsi", provinsiList);
	return "tambah-pegawai";
	
}	
}
