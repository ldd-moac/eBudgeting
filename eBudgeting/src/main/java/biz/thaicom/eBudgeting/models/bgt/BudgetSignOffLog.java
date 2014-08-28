package biz.thaicom.eBudgeting.models.bgt;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import biz.thaicom.eBudgeting.models.hrx.Organization;
import biz.thaicom.eBudgeting.models.hrx.Person;
import biz.thaicom.security.models.User;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="BGT_BUDGETSIGNOFFLOG")
@SequenceGenerator(name="BGT_BUDGETSIGNOFFLOG_SEQ", sequenceName="BGT_BUDGETSIGNOFFLOG_SEQ", allocationSize=1)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BudgetSignOffLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4099366823533592877L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BGT_BUDGETSIGNOFFLOG_SEQ")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="PERSON")
	private Person person;
	
	@ManyToOne
	@JoinColumn(name="ORGANIZATION_ID")
	private Organization organization;
	
	@Enumerated(EnumType.STRING)
	private SignOffStatus toStatus;
	
	@Basic
	private Integer round;
	
	@Basic
	private Integer fiscalYear;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date timestamp;

	@Basic
	private Integer lockLevel;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SignOffStatus getToStatus() {
		return toStatus;
	}

	public void setToStatus(SignOffStatus toStatus) {
		this.toStatus = toStatus;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getRound() {
		return round;
	}

	public void setRound(Integer round) {
		this.round = round;
	}

	public Integer getLockLevel() {
		return lockLevel;
	}

	public void setLockLevel(Integer lockLevel) {
		this.lockLevel = lockLevel;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public Integer getFiscalYear() {
		return fiscalYear;
	}

	public void setFiscalYear(Integer fiscalYear) {
		this.fiscalYear = fiscalYear;
	}


	
}
