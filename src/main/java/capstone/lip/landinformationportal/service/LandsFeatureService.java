/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.lip.landinformationportal.service;

import capstone.lip.landinformationportal.entity.LandsFeature;
import capstone.lip.landinformationportal.repository.DistrictRepository;
import capstone.lip.landinformationportal.repository.LandsFeatureRepository;
import capstone.lip.landinformationportal.service.Interface.ILandsFeatureService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class LandsFeatureService implements ILandsFeatureService {

    @Autowired
    private LandsFeatureRepository landsfeatureRepository;

    @Override
    public List<LandsFeature> findAll() {
           return landsfeatureRepository.findAll();
    }

    @Override
    public LandsFeature save(LandsFeature landsfeature) {
          return landsfeatureRepository.save(landsfeature);
    }

    @Override
    public void delete(Long landsfeatureID) {
        landsfeatureRepository.deleteById(landsfeatureID);
    }

}
