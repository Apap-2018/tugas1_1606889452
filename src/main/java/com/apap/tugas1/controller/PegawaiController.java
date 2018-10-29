package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.PegawaiDb;
import com.apap.tugas1.repository.ProvinsiDb;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {
 
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;

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
	@RequestMapping(value="/pegawai/tambah",method = RequestMethod.POST, params= {"addRow"})
	private String addRow (@ModelAttribute PegawaiModel pegawai, Model model, BindingResult bindingResult) {
		if (pegawai.getJabatanList() == null) {
			pegawai.setJabatanList(new ArrayList());
		}
		pegawai.getJabatanList().add(new JabatanModel());
		
		List<JabatanModel> jab = jabatanService.getAll();
		List<ProvinsiModel> prov = provinsiService.findAllProvinsi();
		model.addAttribute("listOfProvinsi", prov);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jabatanList",jab);
		model.addAttribute("title", "Tambah Pegawai");
		return "add-pegawai";
	}
	@RequestMapping(value = "/pegawai/cari",method = RequestMethod.GET)
	private  String filter(@RequestParam(value = "idProvinsi", required=false) Optional<String> idProvinsi,
			@RequestParam(value="idInstansi",  required=false) Optional<String> id_instansi,
			@RequestParam(value="idJabatan", required=false) Optional<String> id_jabatan,
			Model model) {
		List<JabatanModel> allJabatan = jabatanService.getAll();
		List<InstansiModel> allInstansi = instansiService.getAll();
		List<ProvinsiModel> allProvinsi = provinsiService.findAllProvinsi();
		model.addAttribute("allInstansi",allInstansi);
		model.addAttribute("allProvinsi",allProvinsi);
		model.addAttribute("allJabatan",allJabatan);
		model.addAttribute("title", "Cari Pegawai");
		
		List<PegawaiModel> allPegawai = pegawaiService.findAllPegawai();
		List<PegawaiModel> result = new ArrayList<>();
		if (idProvinsi.isPresent()) {
			if (id_instansi.isPresent() && id_jabatan.isPresent()) {
				List<PegawaiModel> temp = new ArrayList<>();
				Long idInstansi = Long.parseLong(id_instansi.get());
				InstansiModel instansi = instansiService.getInstansiById(idInstansi);
				Long idJabatan = Long.parseLong(id_jabatan.get());
				JabatanModel jabatan = jabatanService.getJabatanById(idJabatan);
				temp = pegawaiService.getPegawaiByInstansi(instansi);
				for (PegawaiModel peg : temp) {
					for (JabatanModel jab : peg.getJabatanList()) {
						if (jab.equals(jabatan)) {
							result.add(peg);
						}
					}
				}
			}
			else if (!(id_instansi.isPresent()) && id_jabatan.isPresent()) {
				List<PegawaiModel> temp = new ArrayList<>();
				Long idProv = Long.parseLong(idProvinsi.get());
				ProvinsiModel prov = provinsiService.getProvinsiById(idProv);
				for (PegawaiModel peg : allPegawai) {
					if (peg.getInstansi().getProvinsi().equals(prov)) {
						temp.add(peg);
					}
				}
				Long idJabatan = Long.parseLong(id_jabatan.get());
				JabatanModel jabatan = jabatanService.getJabatanById(idJabatan);
				for (PegawaiModel peg : temp) {
					for (JabatanModel jab : peg.getJabatanList()) {
						if (jab.equals(jabatan)) {
							result.add(peg);
						}
					}
				}
			}
			else if(id_instansi.isPresent() && !(id_jabatan.isPresent())) { 
				Long idInstansi = Long.parseLong(id_instansi.get());
				InstansiModel instansi = instansiService.getInstansiById(idInstansi);
				result = pegawaiService.getPegawaiByInstansi(instansi);
				
			}
			else if(!(id_instansi.isPresent()) && !(id_jabatan.isPresent())) {
				Long idProv = Long.parseLong(idProvinsi.get());
				ProvinsiModel prov = provinsiService.getProvinsiById(idProv);
				for (PegawaiModel peg : allPegawai) {
					if(peg.getInstansi().getProvinsi().equals(prov)) {
						result.add(peg);
					}
				}
			}
		}
		else {
			if (id_jabatan.isPresent() && id_instansi.isPresent()) {
				List<PegawaiModel> temp = new ArrayList<>();
				Long idInstansi = Long.parseLong(id_instansi.get());
				InstansiModel instansi = instansiService.getInstansiById(idInstansi);
				Long idJabatan = Long.parseLong(id_jabatan.get());
				JabatanModel jabatan = jabatanService.getJabatanById(idJabatan);
				temp = pegawaiService.getPegawaiByInstansi(instansi);
				for (PegawaiModel peg : temp) {
					for (JabatanModel jab : peg.getJabatanList()) {
						if (jab.equals(jabatan)) {
							result.add(peg);
						}
					}
				}
			}
			else if(id_jabatan.isPresent() && !(id_instansi.isPresent())) {
				Long idJabatan = Long.parseLong(id_jabatan.get());
				JabatanModel jabatan = jabatanService.getJabatanById(idJabatan);
				for (PegawaiModel peg : allPegawai) {
					for (JabatanModel jab : peg.getJabatanList()) {
						if (jab.equals(jabatan)) {
							result.add(peg);
						}
					}
				}
			}
			else if(!(id_jabatan.isPresent()) && id_instansi.isPresent()) {
				Long idInstansi = Long.parseLong(id_instansi.get());
				InstansiModel instansi = instansiService.getInstansiById(idInstansi);
				result = pegawaiService.getPegawaiByInstansi(instansi);
			}
			else if(!(id_jabatan.isPresent()) && !(id_instansi.isPresent())) {
				result = null;
			}
		}
		model.addAttribute("allData",result);
		return "find-pegawai";
	}

	@RequestMapping(value = "/pegawai/tambah")
	private String tambahPegawai(Model model) {
		PegawaiModel peg = new PegawaiModel();
		if (peg.getJabatanList()==null) {
			peg.setJabatanList(new ArrayList());
		}
		peg.getJabatanList().add(new JabatanModel());
		List<ProvinsiModel> prov = provinsiService.findAllProvinsi();
		List<JabatanModel> jab = jabatanService.getAll();
		model.addAttribute("jabatanList",jab);
		model.addAttribute("pegawai", peg);
		model.addAttribute("listOfProvinsi", prov);
		model.addAttribute("title", "Tambah Pegawai");
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah/instansi",method = RequestMethod.GET)
	private @ResponseBody List<InstansiModel> cekInstansi(@RequestParam(value="provinsiId") long provinsiId) {
		ProvinsiModel getProv = provinsiService.getProvinsiById(provinsiId);
		
		return getProv.getInstansiList();
	}
	@RequestMapping(value = "/pegawai/ubah")
	private String ubahPegawai(@RequestParam(value="nip") String nip, Model model) {
		PegawaiModel real = pegawaiService.getPegawaiDetailByNip(nip);
		
		List<ProvinsiModel> prov = provinsiService.findAllProvinsi();
		List<JabatanModel> jab = jabatanService.getAll();
		model.addAttribute("jabatanList",jab);
		model.addAttribute("pegawai", real);
		model.addAttribute("listOfProvinsi", prov);
		model.addAttribute("title", "Ubah Data Pegawai");
		return "update-pegawai";
	}
	@RequestMapping(value="/pegawai/ubah",method = RequestMethod.POST, params= {"addRow"})
	private String addRowUpdate (@ModelAttribute PegawaiModel pegawai, Model model, BindingResult bindingResult) {
		if (pegawai.getJabatanList() == null) {
			pegawai.setJabatanList(new ArrayList());
		}
		pegawai.getJabatanList().add(new JabatanModel());
		
		List<ProvinsiModel> prov = provinsiService.findAllProvinsi();
		List<JabatanModel> jab = jabatanService.getAll();
		model.addAttribute("listOfProvinsi", prov);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jabatanList",jab);
		model.addAttribute("title", "Ubah Data Pegawai");
		return "update-pegawai";
	}
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.POST, params= {"submit"})
	private String updatePegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		PegawaiModel real = pegawaiService.getPegawaiDetailByNip(pegawai.getNip());
		real.setNama(pegawai.getNama());
		real.setJabatanList(pegawai.getJabatanList());
		real.setTahunMasuk(pegawai.getTahunMasuk());
		real.setTanggalLahir(pegawai.getTanggalLahir());
		real.setTempatLahir(pegawai.getTempatLahir());
		pegawaiService.addPegawai(real);
		String msg = "Pegawai dengan NIP "+real.getNip()+" berhasil diubah";
		model.addAttribute("msg",msg);
		model.addAttribute("title", "Sukses");
		return "add";
	}

	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"submit"})
	private String tambahPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		String nipPegawai = pegawaiService.generateNip(pegawai);
		pegawai.setNip(nipPegawai);
		pegawaiService.addPegawai(pegawai);
		String msg = "Pegawai dengan NIP "+nipPegawai+" berhasil ditambahkan";
		model.addAttribute("msg",msg);
		model.addAttribute("title", "Sukses");
		return "add";
	}
	

}	

