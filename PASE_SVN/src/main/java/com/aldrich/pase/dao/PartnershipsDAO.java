package com.aldrich.pase.dao;

import java.util.List;

public interface PartnershipsDAO {
	
	public List<Object[]> getCompanyPartnerships(Integer companyId);

}
