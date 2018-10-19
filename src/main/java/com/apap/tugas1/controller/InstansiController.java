package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.PegawaiService;

public class InstansiController {
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private PegawaiService pegawaiService;
	
	
	@RequestMapping(value="/instansi/tuamuda",method = RequestMethod.GET)
	private String tuaMuda(@ModelAttribute InstansiModel instansi, Model model) {
		PegawaiModel siTua = instansiService.getTua(instansi);
		PegawaiModel siMuda= instansiService.getMuda(instansi);
		List<JabatanModel> listJabatanTua = siTua.getJabatanList();
		List<JabatanModel> listJabatanMuda = siMuda.getJabatanList();
		double gajiMuda = pegawaiService.hitungGaji(siMuda);
		double gajiTua = pegawaiService.hitungGaji(siTua);
		model.addAttribute("tertua", siTua);
		model.addAttribute("termuda", siMuda);
		model.addAttribute("listMuda", listJabatanMuda);
		model.addAttribute("listTua", listJabatanTua);
		model.addAttribute("gajiMuda", gajiMuda);
		model.addAttribute("gajiTua", gajiTua);
		return "view-tua-muda";
	}
}
