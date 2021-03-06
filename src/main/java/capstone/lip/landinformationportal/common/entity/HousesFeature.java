/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.lip.landinformationportal.common.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.array.ListArrayType;

import capstone.lip.landinformationportal.common.entity.audit.AuditAbstract;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "HousesFeature")
@TypeDefs({
    @TypeDef(name = "list-array", typeClass = ListArrayType.class)})
public class HousesFeature extends AuditAbstract implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HousesFeatureID")
    private Long housesFeatureID;
    @Column(name = "HousesFeatureName")
    private String housesFeatureName;
    @Column(name = "HousesFeatureUnit")
    private String housesFeatureUnit;

    @LazyToOne(LazyToOneOption.NO_PROXY)
    @OneToMany(mappedBy = "housesFeature", fetch = FetchType.LAZY)
    private List<HousesDetail> listHousesDetail;

    @Column(name = "HousesFeatureDataType")
    private String HousesFeatureDataType;

    @Type(type = "list-array")
    @Column(name = "HousesFeatureDataRange", columnDefinition = "text[]")
    private List<String> HousesFeatureDataRange;

    public HousesFeature() {
    }

    public List<HousesDetail> getListHousesDetail() {
        return listHousesDetail;
    }

    public void setListHousesDetail(List<HousesDetail> listHousesDetail) {
        this.listHousesDetail = listHousesDetail;
    }

    public HousesFeature(String housesFeatureName, String housesFeatureUnit, String HousesFeatureDataType, List<String> HousesFeatureDataRange) {
        this.housesFeatureName = housesFeatureName;
        this.housesFeatureUnit = housesFeatureUnit;
        this.HousesFeatureDataType = HousesFeatureDataType;
        this.HousesFeatureDataRange = HousesFeatureDataRange;
    }

    public Long getHousesFeatureID() {
        return housesFeatureID;
    }

    public void setHousesFeatureID(Long housesFeatureID) {
        this.housesFeatureID = housesFeatureID;
    }

    public String getHousesFeatureName() {
        return housesFeatureName;
    }

    public void setHousesFeatureName(String housesFeatureName) {
        this.housesFeatureName = housesFeatureName;
    }

    public String getHousesFeatureUnit() {
        return housesFeatureUnit;
    }

    public void setHousesFeatureUnit(String housesFeatureUnit) {
        this.housesFeatureUnit = housesFeatureUnit;
    }

    public String getHousesFeatureDataType() {
        return HousesFeatureDataType;
    }

    public void setHousesFeatureDataType(String housesFeatureDataType) {
        HousesFeatureDataType = housesFeatureDataType;
    }

    public List<String> getHousesFeatureDataRange() {
        return HousesFeatureDataRange;
    }

    public void setHousesFeatureDataRange(List<String> housesFeatureDataRange) {
        HousesFeatureDataRange = housesFeatureDataRange;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((HousesFeatureDataRange == null) ? 0 : HousesFeatureDataRange.hashCode());
		result = prime * result + ((HousesFeatureDataType == null) ? 0 : HousesFeatureDataType.hashCode());
		result = prime * result + ((housesFeatureID == null) ? 0 : housesFeatureID.hashCode());
		result = prime * result + ((housesFeatureName == null) ? 0 : housesFeatureName.hashCode());
		result = prime * result + ((housesFeatureUnit == null) ? 0 : housesFeatureUnit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		HousesFeature other = (HousesFeature) obj;
		if (HousesFeatureDataRange == null) {
			if (other.HousesFeatureDataRange != null)
				return false;
		} else if (!HousesFeatureDataRange.equals(other.HousesFeatureDataRange))
			return false;
		if (HousesFeatureDataType == null) {
			if (other.HousesFeatureDataType != null)
				return false;
		} else if (!HousesFeatureDataType.equals(other.HousesFeatureDataType))
			return false;
		if (housesFeatureID == null) {
			if (other.housesFeatureID != null)
				return false;
		} else if (!housesFeatureID.equals(other.housesFeatureID))
			return false;
		if (housesFeatureName == null) {
			if (other.housesFeatureName != null)
				return false;
		} else if (!housesFeatureName.equals(other.housesFeatureName))
			return false;
		if (housesFeatureUnit == null) {
			if (other.housesFeatureUnit != null)
				return false;
		} else if (!housesFeatureUnit.equals(other.housesFeatureUnit))
			return false;
		return true;
	}
    
}
