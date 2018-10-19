package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value="/jabatan/tambah",method = RequestMethod.GET)
	private String addJabatan(Model model) {
		model.addAttribute("jabatan",new JabatanModel());
		return "add-jabatan";
	}
	@RequestMapping(value="/jabatan/tambah", method =RequestMethod.POST)
	private String addJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("jabatan",jabatan);
		model.addAttribute("message", "Jabatan berhasil ditambahkan");
		return "add-jabatan";
	}
	
	@RequestMapping(value="/jabatan/view", method = RequestMethod.GET)
	private String view(@RequestParam(value="idJabatan") long id_jabatan, Model model) {
		model.addAttribute("jabatan", jabatanService.getJabatanById(id_jabatan));
		model.addAttribute("gaji", (long)jabatanService.getJabatanById(id_jabatan).getGajiPokok());
		return "view-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah",method = RequestMethod.POST)
	private String ubahJabatan(@RequestParam(value="idJabatan") long id_jabatan, Model model) {
		model.addAttribute("jabatan",jabatanService.getJabatanById(id_jabatan));
		return "update-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah/", method = RequestMethod.POST)
	private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.updateJabatan(jabatan, jabatan.getId());
		model.addAttribute("jabatan",jabatan);
		model.addAttribute("message","Data jabatan berhasil diubah");
		return "update-jabatan";
	}
	
	@RequestMapping(value="/jabatan/hapus", method = RequestMethod.POST)
	private String hapusJabatan(@RequestParam(value="idJabatan") long id_jabatan,Model model, RedirectAttributes ra)throws Exception {
		try {
			jabatanService.deleteJabatan(jabatanService.getJabatanById(id_jabatan));
			ra.addFlashAttribute("message", "Jabatan berhasil dihapus :)");
			return "redirect:/";
		} catch (Exception e) {
			ra.addFlashAttribute("message", "Jabatan gagal dihapus");
			return "redirect:/";
		}
	}
	@RequestMapping(value="/jabatan/viewAll", method = RequestMethod.GET)
	private String viewAll(Model model) {
		model.addAttribute("listJabatan", jabatanService.getAll());
		return "view-all";
	}

	
}
