package biz.thaicom.security.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import biz.thaicom.eBudgeting.models.hrx.Organization;
import biz.thaicom.eBudgeting.models.hrx.Organization_;
import biz.thaicom.eBudgeting.models.hrx.Person;
import biz.thaicom.eBudgeting.models.hrx.Person_;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


@Entity
@Table(name="SEC_USER")
@SequenceGenerator(name="SEC_USER_SEQ", sequenceName="SEC_USER_SEQ", allocationSize=1)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8336069626385451551L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_USER_SEQ")
	private Long id;
	
	@Basic
	private String username;
	
	@Basic
	private String password;

	@OneToOne
	@JoinColumn(name="PERSON_HRX_PERSON_ID") 
	private Person person;
	
	@Transient
	private List<Group> groups;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	public static Specification<User> UserHasNameLike(final String name) {
		return new Specification<User>() {
			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				Join<User, Person> p = root.join(User_.person);
				Join<Person, Organization> o = p.join(Person_.workAt);
				if (Long.class != query.getResultType()) {
					root.fetch(User_.person).fetch(Person_.workAt);
				}
				
				
				return cb.or(
						cb.like(root.get(User_.username), name),
						cb.like(p.get(Person_.firstName), name),
						cb.like(p.get(Person_.lastName), name),
						cb.like(o.get(Organization_.name), name));
			}
		};
	}
	
	
}
