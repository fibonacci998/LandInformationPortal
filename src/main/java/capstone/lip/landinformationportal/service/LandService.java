/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.lip.landinformationportal.service;

import capstone.lip.landinformationportal.dto.LandFeatureValue;
import capstone.lip.landinformationportal.entity.Land;
import capstone.lip.landinformationportal.entity.LandsDetail;
import capstone.lip.landinformationportal.entity.RealEstate;
import capstone.lip.landinformationportal.entity.compositekey.LandsDetailId;
import capstone.lip.landinformationportal.repository.LandRepository;
import capstone.lip.landinformationportal.repository.LandsDetailRepository;
import capstone.lip.landinformationportal.service.Interface.ILandService;
import capstone.lip.landinformationportal.service.Interface.ILandsDetailService;
import capstone.lip.landinformationportal.validation.LandValidation;
import capstone.lip.landinformationportal.validation.RealEstateValidation;
import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class LandService implements ILandService {

    @Autowired
    private LandRepository landRepository;

    @Autowired
    private ILandsDetailService landDetailService;
    
    @Autowired
    private LandsDetailRepository landsDetailRepository;
    
    
    @Override
    public Land save(Land land) {
    	try {
    		LandValidation validate = new LandValidation();
    		String error = validate.isValidLand(land);
    		if (!error.isEmpty()) {
    			throw new Exception(error);
    		}
//    		for (LandsDetail element:land.getListLandsDetail()) {
//    			if (landsDetailRepository.findByIdLandIdAndIdLandsFeatureId(element.getId().getLandId(), 
//    					element.getId().getLandsFeatureId()) == null){
//    				throw new Exception("LandId not true");
//    			}
//    		}
    		
    		return landRepository.save(land);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
        
    }

    @Override
    public boolean delete(Land land) {
    	try {
    		if (land == null) throw new Exception("null");
    		if (!landRepository.existsById(land.getLandId())) {
    			throw new Exception("Land not found");
    		}
    		List<LandsDetail> landDetail = land.getListLandsDetail();
    		landDetailService.delete(landDetail);
    		landRepository.delete(land);
    		return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
        
    }

    
}
