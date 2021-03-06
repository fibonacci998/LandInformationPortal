/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.lip.landinformationportal.business.service;

import capstone.lip.landinformationportal.business.repository.LandRepository;
import capstone.lip.landinformationportal.business.repository.LandsDetailRepository;
import capstone.lip.landinformationportal.business.repository.LandsFeatureRepository;
import capstone.lip.landinformationportal.business.service.Interface.ILandsDetailService;
import capstone.lip.landinformationportal.common.entity.LandsDetail;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class LandsDetailService implements ILandsDetailService {

    @Autowired
    private LandsDetailRepository landsDetailRepository;

    @Autowired
    private LandRepository landRepository;
    
    @Autowired
    private LandsFeatureRepository landsFeatureRepository;
    
    @Override
    public LandsDetail save(LandsDetail landsDetail) {
        try {
        	if (!landRepository.existsById(landsDetail.getId().getLandId())) {
        		throw new Exception("Land not found");
        	}
        	if (!landsFeatureRepository.existsById(landsDetail.getId().getLandsFeatureId())) {
        		throw new Exception("Land feature not found");
        	}
            return landsDetailRepository.save(landsDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean delete(List<LandsDetail> listLandsDetail) {
    	try {
    		if (listLandsDetail == null) throw new Exception("null");
    		if (listLandsDetail.isEmpty()) throw new Exception("empty");
    		for (LandsDetail element: listLandsDetail) {
    			if (landsDetailRepository.findByIdLandIdAndIdLandsFeatureId(element.getId().getLandId(), 
    					element.getId().getLandsFeatureId()) == null){
    				throw new Exception("Id not found");
				}
    		}
    		
    		landsDetailRepository.deleteAll(listLandsDetail);
    		return true;
    	}catch(Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }

}
