package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

public class JabatanController {
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value="/jabatan/tambah",method = RequestMethod.GET)
	private String addJabatan(Model model) {
		model.addAttribute("jabatan",new JabatanModel());
		return "add-jabatan";
	}
	@RequestMapping(value="jabatan/tambah", method =RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan) {
		jabatanService.addJabatan(jabatan);
		return "add";
	}
	@RequestMapping(value="/jabatan/view")
	private String view(@RequestParam(value="idJabatan") long id_jabatan, Model model) {
		model.addAttribute("jabatan", jabatanService.getJabatanById(id_jabatan));
		return "view-jabatan";
	}
	@RequestMapping(value="/jabatan/ubah",method = RequestMethod.POST)
	private String ubahJabatan(@RequestParam(value="idJabatan") long id_jabatan, Model model) {
		model.addAttribute("jabatan",jabatanService.getJabatanById(id_jabatan));
		return "update-jabatan";
	}
	
	@RequestMapping(value="jabatan/ubah", method = RequestMethod.POST)
	private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.updateJabatan(jabatan, jabatan.getId());
		return "update";
	}
	
	@RequestMapping(value="jabatan/hapus", method = RequestMethod.POST)
	private String hapusJabatan(@RequestParam(value="idJabatan") long id_jabatan,Model model) {
		jabatanService.deleteJabatan(jabatanService.getJabatanById(id_jabatan));
		return "delete-jabatan";
	}
	
}
