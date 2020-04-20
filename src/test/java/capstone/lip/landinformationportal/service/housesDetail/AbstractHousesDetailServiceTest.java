/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.lip.landinformationportal.service.housesDetail;

import capstone.lip.landinformationportal.common.CRUDTest;
import capstone.lip.landinformationportal.entity.HousesDetail;
import capstone.lip.landinformationportal.entity.compositekey.HousesDetailId;
import capstone.lip.landinformationportal.repository.HousesDetailRepository;
import capstone.lip.landinformationportal.service.HousesDetailService;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Phong
 */
public abstract class AbstractHousesDetailServiceTest extends CRUDTest {

    @Autowired
    protected HousesDetailService instance;

    @Autowired
    protected HousesDetailRepository repository;

    protected HousesDetail sampleHousesDetail = new HousesDetail()
            .setId(new HousesDetailId()
                    .setHouseId(DEFAULT_ID)
                    .setHousesFeatureId(DEFAULT_ID))
            .setValue("DEFAULT VALUE");

    protected HousesDetail setHousesDetailID(HousesDetail housesDetail, 
            long houseID, long housesFeatureID) {
        housesDetail.setId(housesDetail.getId()
                .setHouseId(houseID)
                .setHousesFeatureId(housesFeatureID));
        return housesDetail;
    }
    
    protected void testUpdateSuccess(HousesDetail result, long records) {
        //Update success
        if (result != null) {
            HousesDetail actual = repository
                    .findByIdHouseIdAndIdHousesFeatureId(
                            result.getId().getHouseId(),
                            result.getId().getHousesFeatureId());
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