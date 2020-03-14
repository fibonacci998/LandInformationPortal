package capstone.lip.landinformationportal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import capstone.lip.landinformationportal.entity.District;
import capstone.lip.landinformationportal.entity.Province;
import capstone.lip.landinformationportal.repository.ProvinceRepository;
import capstone.lip.landinformationportal.service.Interface.IProvinceService;

@Service
public class ProvinceService implements IProvinceService{
	
	@Autowired 
	ProvinceRepository provinceRepository;
	
//	EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
	
	public List<Province> findAll(){
		return provinceRepository.findAll();
	}
	
	public Province save(Province province) {
		return provinceRepository.save(province);
	}
	@Override
	public void deleteById(Long id) {
		provinceRepository.deleteById(id);
	}
	
	@Override
	public List<District> getListDistrictByProvinceId(Long provinceId) {
		Province province = provinceRepository.findById(provinceId).get();
		List<District> list = province.getListDistrict();
		return list;
	}

	@Override
	public void delete(Province province) {
		provinceRepository.delete(province);
	}

}
