package com.techelevator.model;

import java.util.List;

public interface CampgroundDAO {
	
	public List<Site> returnTopAvailableSites (CampgroundSearch search);
	
	public List<Site> returnTopSitesRequirements (AdvancedSearch search);

}
