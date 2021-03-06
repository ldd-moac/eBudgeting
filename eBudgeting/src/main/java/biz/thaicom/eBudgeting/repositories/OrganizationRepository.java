package biz.thaicom.eBudgeting.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import biz.thaicom.eBudgeting.models.hrx.Organization;

public interface OrganizationRepository extends JpaSpecificationExecutor<Organization>,
		PagingAndSortingRepository<Organization, Long> {

	
	
	
}

