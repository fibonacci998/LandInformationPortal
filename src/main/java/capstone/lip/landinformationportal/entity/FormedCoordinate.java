package capstone.lip.landinformationportal.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
@Entity
@Table(name="FormedCoordinate")
public class FormedCoordinate extends AuditAbstract implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="FoormedCoordinateId")
	private Long formedCoordinateId;
	@Column(name="FormedLat")
	private Double formedLat;
	@Column(name="FormedLng")
	private Double formedLng;
	
	@Basic(fetch = FetchType.LAZY)
	@ManyToOne(optional = false)
	@JoinColumn(name ="SegmentID", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private SegmentOfStreet segmentOfStreet;

	public Long getFormedCoordinateId() {
		return formedCoordinateId;
	}

	public void setFormedCoordinateId(Long formedCoordinateId) {
		this.formedCoordinateId = formedCoordinateId;
	}

	public Double getFormedLat() {
		return formedLat;
	}

	public void setFormedLat(Double formedLat) {
		this.formedLat = formedLat;
	}

	public Double getFormedLng() {
		return formedLng;
	}

	public void setFormedLng(Double formedLng) {
		this.formedLng = formedLng;
	}

	public SegmentOfStreet getSegmentOfStreet() {
		return segmentOfStreet;
	}

	public void setSegmentOfStreet(SegmentOfStreet segmentOfStreet) {
		this.segmentOfStreet = segmentOfStreet;
	}
	
	
}