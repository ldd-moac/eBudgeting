package biz.thaicom.eBudgeting.models.pln;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import biz.thaicom.eBudgeting.models.hrx.Organization;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="PLN_TARGETVALUE")
@SequenceGenerator(name="PLN_TARGETVALUE_SEQ", sequenceName="PLN_TARGETVALUE_SEQ", allocationSize=1)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class TargetValue implements Serializable{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6113798618441927601L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PLN_TARGETVALUE_SEQ")
	private Long id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="OBJECTIVETARGET_ID")
	private ObjectiveTarget target;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="OWNER_ORAGANIZATION_ID")
	private Organization owner;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="FOROBJECTIVE_ID")
	private Objective forObjective;
	
	
	@Basic
	private Long requestedValue;

	@Basic
	private Long requestedValueNext1Year;
	
	@Basic
	private Long requestedValueNext2Year;
	
	@Basic
	private Long requestedValueNext3Year;
	
	
	
	@Basic
	private Long allocatedValue;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ObjectiveTarget getTarget() {
		return target;
	}

	public void setTarget(ObjectiveTarget target) {
		this.target = target;
	}

	public Organization getOwner() {
		return owner;
	}

	public void setOwner(Organization owner) {
		this.owner = owner;
	}

	public Long getRequestedValue() {
		return requestedValue;
	}

	public void setRequestedValue(Long requestedValue) {
		this.requestedValue = requestedValue;
	}

	public Long getAllocatedValue() {
		return allocatedValue;
	}

	public void setAllocatedValue(Long allocatedValue) {
		this.allocatedValue = allocatedValue;
	}

	public Objective getForObjective() {
		return forObjective;
	}

	public void setForObjective(Objective forObjective) {
		this.forObjective = forObjective;
	}
	
	

	public Long getRequestedValueNext1Year() {
		if(this.requestedValueNext1Year == null) return 0L;
		return requestedValueNext1Year;
	}

	public void setRequestedValueNext1Year(Long requestedValueNext1Year) {
		this.requestedValueNext1Year = requestedValueNext1Year;
	}

	public Long getRequestedValueNext2Year() {
		if(this.requestedValueNext2Year == null) return 0L;
		return requestedValueNext2Year;
	}

	public void setRequestedValueNext2Year(Long requestedValueNext2Year) {
		this.requestedValueNext2Year = requestedValueNext2Year;
	}

	public Long getRequestedValueNext3Year() {
		if(this.requestedValueNext3Year == null) return 0L;
		return requestedValueNext3Year;
	}

	public void setRequestedValueNext3Year(Long requestedValueNext3Year) {
		this.requestedValueNext3Year = requestedValueNext3Year;
	}

	public void adjustRequestedValue(Long adjustedRequestedValue,
			Long adjustedRequestedValueNext1Year,
			Long adjustedRequestedValueNext2Year,
			Long adjustedRequestedValueNext3Year) {
		
		if(this.requestedValue != null) {
			this.requestedValue -= adjustedRequestedValue;
		} else {
			this.requestedValue = 0L - adjustedRequestedValue;
		}
		
		if(this.requestedValueNext1Year != null) {
			this.requestedValueNext1Year -= adjustedRequestedValueNext1Year;
		} else {
			this.requestedValueNext1Year = 0L - adjustedRequestedValueNext1Year;
		}
		
		if(this.requestedValueNext2Year != null) {
			this.requestedValueNext2Year -= adjustedRequestedValueNext2Year;
		} else {
			this.requestedValueNext2Year = 0L - adjustedRequestedValueNext2Year;
		}
		
		if(this.requestedValueNext3Year != null) {
			this.requestedValueNext3Year -= adjustedRequestedValueNext3Year;
		} else {
			this.requestedValueNext3Year = 0L - adjustedRequestedValueNext3Year;
		}
		
	}

	public void adjustAllocatedValue(Long adjustedRequestedValue) {
		if(this.allocatedValue != null) {
			this.allocatedValue -= adjustedRequestedValue;
		} else {
			this.allocatedValue = 0L - adjustedRequestedValue;
		}
		
	}

	
	
	
}
