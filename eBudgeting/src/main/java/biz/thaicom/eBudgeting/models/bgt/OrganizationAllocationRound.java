package biz.thaicom.eBudgeting.models.bgt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="BGT_ORGALLOCATIONROUND")
@SequenceGenerator(name="BGT_ORGALLOCATIONROUND_SEQ", sequenceName="BGT_ORGALLOCATIONROUND_SEQ", allocationSize=1)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope=OrganizationAllocationRound.class)
public class OrganizationAllocationRound implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7572428061490384745L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BGT_ORGALLOCATIONROUND_SEQ")
	private Long id;
	
	@Column(name="ROUND")
	private Integer round;
	
	@Column(name="FISCALYEAR")
	private Integer fiscalYear;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATEDDATE")
	private Date createdDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(Integer fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	
	

}
