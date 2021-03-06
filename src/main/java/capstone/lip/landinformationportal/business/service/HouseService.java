/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.lip.landinformationportal.business.service;

import capstone.lip.landinformationportal.business.repository.HouseRepository;
import capstone.lip.landinformationportal.business.repository.HousesFeatureRepository;
import capstone.lip.landinformationportal.business.service.Interface.IHouseService;
import capstone.lip.landinformationportal.business.service.Interface.IHousesDetailService;
import capstone.lip.landinformationportal.business.validation.HouseValidation;
import capstone.lip.landinformationportal.business.validation.HousesDetailValidation;
import capstone.lip.landinformationportal.common.entity.House;
import capstone.lip.landinformationportal.common.entity.HousesDetail;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class HouseService implements IHouseService {

	@Autowired
	private HouseRepository houseRepository;

	@Autowired
	private HousesFeatureRepository housesFeatureRepository;

	@Autowired
	private IHousesDetailService housesDetailService;

	@Override
	public House save(House house) {
		try {
			HouseValidation validate = new HouseValidation();
			String error = validate.isValidHouse(house);
			if (!error.isEmpty()) {
				throw new Exception(error);
			}

			if (house.getHouseId() != null) {
				Optional<House> houseTemp = houseRepository.findById(house.getHouseId());
				HousesDetailValidation validateHouseDetail = new HousesDetailValidation();
				String checkHouseDetail = validateHouseDetail.isValidHouseDetail(house.getListHousesDetail(),
						housesFeatureRepository);
				if (!checkHouseDetail.isEmpty()) {
					throw new Exception(checkHouseDetail);
				}
			}

			return houseRepository.save(house);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public boolean delete(List<House> listHouse) {
		try {
			if (listHouse == null)
				throw new Exception("null");
			if (listHouse.isEmpty())
				throw new Exception("empty");
			List<HousesDetail> listHouseDetail = listHouse.stream().map(x -> x.getListHousesDetail())
					.flatMap(List::stream).collect(Collectors.toList());
			housesDetailService.delete(listHouseDetail);
			houseRepository.deleteAll(listHouse);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
