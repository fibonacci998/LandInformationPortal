/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.lip.landinformationportal.business.service;

import capstone.lip.landinformationportal.business.repository.RealEstateRepository;
import capstone.lip.landinformationportal.business.service.Interface.IHouseService;
import capstone.lip.landinformationportal.business.service.Interface.IHousesDetailService;
import capstone.lip.landinformationportal.business.service.Interface.ILandService;
import capstone.lip.landinformationportal.business.service.Interface.ILandsDetailService;
import capstone.lip.landinformationportal.business.service.Interface.IRealEstateAdjacentSegmentService;
import capstone.lip.landinformationportal.business.service.Interface.IRealEstateService;
import capstone.lip.landinformationportal.business.specification.RealEstateSpecifications;
import capstone.lip.landinformationportal.business.specification.SearchCriteria;
import capstone.lip.landinformationportal.business.validation.RealEstateValidation;
import capstone.lip.landinformationportal.common.dto.GroupByDateMaxMin;
import capstone.lip.landinformationportal.common.dto.MaxMinAvg;
import capstone.lip.landinformationportal.common.entity.House;
import capstone.lip.landinformationportal.common.entity.HousesDetail;
import capstone.lip.landinformationportal.common.entity.Land;
import capstone.lip.landinformationportal.common.entity.LandsDetail;
import capstone.lip.landinformationportal.common.entity.RealEstate;
import capstone.lip.landinformationportal.common.entity.RealEstateAdjacentSegment;
import capstone.lip.landinformationportal.common.entity.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class RealEstateService implements IRealEstateService {

    @Autowired
    private RealEstateRepository realEstateRepository;

    @Override
    public RealEstate save(RealEstate realEstate) {
        try {
            RealEstateValidation validate = new RealEstateValidation();
    		String error = validate.isRealEstateValid(realEstate);
    		if (!error.isEmpty()) {
    			throw new Exception(error);
    		}
            return realEstateRepository.save(realEstate);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // Hàm này get List Land theo ID của Real Estate
    @Override
    public Land getLand(Long realEstateId) {
        try {
            RealEstate realEstate = realEstateRepository.findById(realEstateId).get();
            Land land = realEstate.getLand();
            return land;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    // Hàm này get List House theo ID của Real Estate
    @Override
    public List<House> getListHouse(Long realEstateId) {
        try {
            RealEstate realEstate = realEstateRepository.findById(realEstateId).get();
            List<House> listHouse = realEstate.getListHouse();
            return listHouse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public RealEstate findById(long realEstateId) {
        try {
            Optional<RealEstate> re = realEstateRepository.findById(realEstateId);
            if (re.isPresent()) {
                return realEstateRepository.findById(realEstateId).get();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<String> listRealEstateSource() {
        try {
            return realEstateRepository.listRealEstateSource();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Page<RealEstate> findByRealEstateStatus(String status, Pageable page) {
        try {
            return realEstateRepository.findByRealEstateStatus(status, page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public long count() {
    	try {
    		return realEstateRepository.count();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
        
    }

    @Override
    public List<RealEstate> listFilterRealEstate(String realEstateName, String realEstateSource, String realEstateStatus) {
        try {
            List<RealEstateSpecifications> listSpec = new ArrayList<>();
            if (realEstateName != null) {
                listSpec.add(new RealEstateSpecifications(new SearchCriteria("realEstateName", ":", realEstateName)));
            }
            if (realEstateSource != null) {
                listSpec.add(new RealEstateSpecifications(new SearchCriteria("realEstateSource", ":=", realEstateSource)));
            }
            if (realEstateStatus != null) {
                listSpec.add(new RealEstateSpecifications(new SearchCriteria("realEstateStatus", ":=", realEstateStatus)));
            }
            switch (listSpec.size()) {
                case 1:
                    return realEstateRepository.findAll(Specification.where(listSpec.get(0)));
                case 2:
                    return realEstateRepository.findAll(Specification.where(listSpec.get(0).and(listSpec.get(1))));
                case 3:
                    return realEstateRepository.findAll(Specification.where(listSpec.get(0).and(listSpec.get(1).and(listSpec.get(2)))));
                default:
                    return realEstateRepository.findAll();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public long countByRealEstateStatus(String status) {
    	try {
    		return realEstateRepository.countByRealEstateStatus(status);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
        
    }

    @Override
    public Page<RealEstate> listFilterRealEstateByAddress(String realEstateAddress, Pageable page) {
        try {
            RealEstateSpecifications spec1 = new RealEstateSpecifications(new SearchCriteria("realEstateName", ":", realEstateAddress));
            RealEstateSpecifications spec2 = new RealEstateSpecifications(new SearchCriteria("realEstateAddress", ":", realEstateAddress));
            return realEstateRepository.findAll(Specification.where(spec1).or(spec2), page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public long countByRealEstateSource(String realEstateAddress, String realEstateSource) {
        try {
            RealEstateSpecifications spec1 = new RealEstateSpecifications(new SearchCriteria("realEstateName", ":", realEstateAddress));
            RealEstateSpecifications spec2 = new RealEstateSpecifications(new SearchCriteria("realEstateAddress", ":", realEstateAddress));
            RealEstateSpecifications spec3 = new RealEstateSpecifications(new SearchCriteria("realEstateSource", ":=", realEstateSource));
            return realEstateRepository.count(Specification.where(Specification.where(spec1).or(spec2)).and(spec3));
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
        
    }

    @Override
    public Page<RealEstate> findAll(Pageable page) {
        try {
            return realEstateRepository.findAll(page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public long countByRealEstateSourceNot(String realEstateAddress, String realEstateSource) {
        try {
            RealEstateSpecifications spec1 = new RealEstateSpecifications(new SearchCriteria("realEstateName", ":", realEstateAddress));
            RealEstateSpecifications spec2 = new RealEstateSpecifications(new SearchCriteria("realEstateAddress", ":", realEstateAddress));
            RealEstateSpecifications spec3 = new RealEstateSpecifications(new SearchCriteria("realEstateSource", "!=", realEstateSource));
            return realEstateRepository.count(Specification.where(Specification.where(spec1).or(spec2)).and(spec3));
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
        
    }

    @Override
    public Page<RealEstate> listFilterRealEstateByAddressAndSource(String realEstateAddress, String realEstateSource, Pageable page) {
        try {
            RealEstateSpecifications spec1 = new RealEstateSpecifications(new SearchCriteria("realEstateName", ":", realEstateAddress));
            RealEstateSpecifications spec2 = new RealEstateSpecifications(new SearchCriteria("realEstateAddress", ":", realEstateAddress));
            RealEstateSpecifications spec3 = new RealEstateSpecifications(new SearchCriteria("realEstateSource", ":=", realEstateSource));
            return realEstateRepository.findAll(Specification.where(Specification.where(spec1).or(spec2)).and(spec3), page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public Page<RealEstate> listFilterRealEstateByAddressAndSourceNot(String realEstateAddress, String realEstateSource, Pageable page) {
        try {
            RealEstateSpecifications spec1 = new RealEstateSpecifications(new SearchCriteria("realEstateName", ":", realEstateAddress));
            RealEstateSpecifications spec2 = new RealEstateSpecifications(new SearchCriteria("realEstateAddress", ":", realEstateAddress));
            RealEstateSpecifications spec3 = new RealEstateSpecifications(new SearchCriteria("realEstateSource", "!=", realEstateSource));
            return realEstateRepository.findAll(Specification.where(Specification.where(spec1).or(spec2)).and(spec3), page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public long countByRealEstateAddress(String address) {
        try {
            RealEstateSpecifications spec1 = new RealEstateSpecifications(new SearchCriteria("realEstateName", ":", address));
            RealEstateSpecifications spec2 = new RealEstateSpecifications(new SearchCriteria("realEstateAddress", ":", address));
            return realEstateRepository.count(Specification.where(spec1).or(spec2));
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
        
    }

    @Override
    public MaxMinAvg listMaxMinAvg(String location) {
        try {
            return realEstateRepository.getMaxMinAvg(location);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<GroupByDateMaxMin> listGroupByDateAndValue(String location) {
        try {
            return realEstateRepository.getGroupTimeAndPrice(location);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Autowired
    ILandsDetailService landsDetailService;

    @Autowired
    ILandService landService;

    @Autowired
    IHousesDetailService housesDetailService;

    @Autowired
    IHouseService houseService;

    @Autowired
    IRealEstateAdjacentSegmentService realEstateAdjacentSegmentService;

    @Override
    public boolean delete(long realEstateId) {
        try {
            RealEstate realEstate = realEstateRepository.findById(realEstateId).get();
//            Land land = realEstate.getLand();
//            if (land != null) {
//                List<LandsDetail> listLandDetail = realEstate.getLand().getListLandsDetail();
//                if (listLandDetail != null) {
//                    landsDetailService.delete(listLandDetail);
//                }
//                landService.delete(land);
//            }
//            List<House> listHouse = realEstate.getListHouse();
//            if (listHouse != null) {
//                for (House house : listHouse) {
//                    List<HousesDetail> listHousesDetails = house.getListHousesDetail();
//                    if (listHousesDetails != null) {
//                        housesDetailService.delete(listHousesDetails);
//                    }
//                    houseService.delete(listHouse);
//                }
//            }
//            List<RealEstateAdjacentSegment> listAdjacentSegment = realEstate.getListRealEstateAdjacentSegment();
//            if (listAdjacentSegment != null) {
//                realEstateAdjacentSegmentService.delete(listAdjacentSegment);
//            }
            houseService.delete(realEstate.getListHouse());
            landService.delete(realEstate.getLand());
            realEstateAdjacentSegmentService.delete(realEstate.getListRealEstateAdjacentSegment());
            realEstateRepository.deleteById(realEstateId);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

}