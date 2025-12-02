package service;

/**
 * 
 * TODO: implementation of catalog operation
 */

public class CatalogService{

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
