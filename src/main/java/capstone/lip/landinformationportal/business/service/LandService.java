/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.lip.landinformationportal.business.service;

import capstone.lip.landinformationportal.business.repository.LandRepository;
import capstone.lip.landinformationportal.business.repository.LandsFeatureRepository;
import capstone.lip.landinformationportal.business.service.Interface.ILandService;
import capstone.lip.landinformationportal.business.service.Interface.ILandsDetailService;
import capstone.lip.landinformationportal.business.validation.LandValidation;
import capstone.lip.landinformationportal.business.validation.LandsDetailValidation;
import capstone.lip.landinformationportal.common.entity.Land;
import capstone.lip.landinformationportal.common.entity.LandsDetail;
import java.util.List;
import java.util.Optional;

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
    private LandsFeatureRepository landsFeatureRepository;
    
    
    @Override
    public Land save(Land land) {
    	try {
    		LandValidation validate = new LandValidation();
    		String error = validate.isValidLand(land);
    		if (!error.isEmpty()) {
    			throw new Exception(error);
    		}

    		if (land.getLandId() != null) {
				Optional<Land> houseTemp = landRepository.findById(land.getLandId());
				LandsDetailValidation validateLandDetail = new LandsDetailValidation();
				String checkLandDetail = validateLandDetail.isValidLandDetail(land.getListLandsDetail(), landsFeatureRepository);
				if (!checkLandDetail.isEmpty()) {
					throw new Exception(checkLandDetail);
				}
			}
    		
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
