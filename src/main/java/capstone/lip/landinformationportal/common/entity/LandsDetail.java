/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capstone.lip.landinformationportal.common.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import capstone.lip.landinformationportal.common.entity.audit.AuditAbstract;
import capstone.lip.landinformationportal.common.entity.compositekey.LandsDetailId;

/**
 *
 * @author Admin
 */
@Entity
@Table(name="LandsDetail")
public class LandsDetail extends AuditAbstract implements Serializable{
    private static final long serialVersionUID = 1L;
 
    @EmbeddedId
    private LandsDetailId id; 

    @Basic(fetch = FetchType.LAZY)
    @ManyToOne
    @MapsId("LandID")
    @JoinColumn(name = "LandID")
    private Land land;
    

    @LazyToOne(LazyToOneOption.NO_PROXY)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("LandsFeatureID")
    @JoinColumn(name = "LandsFeatureID")
    private LandsFeature landsFeature;

    @Column(name = "Value")
    private String value;

	public Land getLand() {
		return land;
	}

	
	public LandsDetail setLand(Land land) {
		this.land = land;
		return this;
	}

	public LandsFeature getLandsFeature() {
		return landsFeature;
	}

	public LandsDetail setLandsFeature(LandsFeature landsFeature) {
		this.landsFeature = landsFeature;
		return this;
	}

	public String getValue() {
		return value;
	}

	public LandsDetail setValue(String value) {
		this.value = value;
		return this;
	}


	public LandsDetailId getId() {
		return id;
	}


	public LandsDetail setId(LandsDetailId id) {
		this.id = id;
		return this;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		LandsDetail other = (LandsDetail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}


}
