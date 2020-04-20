/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.lip.landinformationportal.service.landsDetail;

import capstone.lip.landinformationportal.common.CRUDTest;
import capstone.lip.landinformationportal.entity.LandsDetail;
import capstone.lip.landinformationportal.entity.compositekey.LandsDetailId;
import capstone.lip.landinformationportal.repository.LandsDetailRepository;
import capstone.lip.landinformationportal.service.LandsDetailService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Phong
 */
public abstract class AbstractLandsDetailServiceTest extends CRUDTest {

    @Autowired
    protected LandsDetailService instance;

    @Autowired
    protected LandsDetailRepository repository;

    protected LandsDetail sampleLandsDetail = new LandsDetail()
            .setId(new LandsDetailId()
                    .setLandId(DEFAULT_ID)
                    .setLandsFeatureId(DEFAULT_ID))
            .setValue("DEFAULT VALUE");

    protected LandsDetail setLandsDetailID(LandsDetail landsDetail, 
            long landID, long landsFeatureID) {
        landsDetail.setId(landsDetail.getId()
                .setLandId(landID)
                .setLandsFeatureId(landsFeatureID));
        return landsDetail;
    }
    
    protected void testUpdateSuccess(LandsDetail result, long records) {
        //Update success
        if (result != null) {
            LandsDetail actual = repository
                    .findByIdLandIdAndIdLandsFeatureId(
                            result.getId().getLandId(),
                            result.getId().getLandsFeatureId());
            //Compare others
            assertEquals(true, actual.equals(result));
            //Test number of records is not changed
            assertEquals(records, repository.count());
        } else {
            fail();
        }
    }

    protected void testDeleteSuccess(boolean result, long id, long records) {
        //Delete success
        if (result) {
            //Test exist in DB
            assertEquals(false, repository.existsById(id));
            //Test number of records is decreased by 1
            assertEquals(records - 1, repository.count());
        } else {
            fail();
        }
    }

    protected void testDeleteSuccess(boolean result, long[] id, long records) {
        //Delete success
        if (result) {
            //Test exist in DB
            for (int i = 0; i < id.length; i++) {
                assertEquals(false, repository.existsById(id[i]));
            }
            //Test number of records is decreased by 1
            assertEquals(records - id.length, repository.count());
        } else {
            fail();
        }
    }
}