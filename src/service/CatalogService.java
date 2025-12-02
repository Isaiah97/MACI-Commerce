package service;

import java.util.ArrayList;
import java.util.List;
import model.Bouquet;

/**
 * 
 * TODO: implementation of catalog operation
 */

public class CatalogService{

	private final List<Bouquet> bouquets = new ArrayList<>();

	public CatalogService() {

		bouquets.add(new Bouquet("Spring Garden Bouquet", "Roses", 39.99));
		bouquets.add(new Bouquet("Romantic Rose Dozen", "Roses", 49.99));
        bouquets.add(new Bouquet("Lavender Dreams", "Mixed", 44.25));
    }

	public List<Bouquet> search (String name, String category, int minPrice, int maxPrice){
		List<Bouquet> all = getAll();
		List <Bouquet> results = new ArrayList<>();

		for (Bouquet b : all) {
			boolean matchesName = b.getName().contains(name);
			boolean matchesCategory = b.getCategory().equalsIgnoreCase(category);
			boolean inRange = b.getPrice() >= minPrice && b.getPrice() <= maxPrice;

			if (matchesName && matchesCategory && inRange) {
				results.add(b);
			}
		}
		return results;
	}
}
