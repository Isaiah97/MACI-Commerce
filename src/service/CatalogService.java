package service;

import java.util.ArrayList;
import java.util.List;
import model.Bouquet;

/**
 * Catalog operations
 */
public class CatalogService {

    private final List<Bouquet> bouquets = new ArrayList<>();

    public CatalogService() {
        // name, description, category, price, available
        bouquets.add(new Bouquet(
            "Spring Garden Bouquet",
            "A hand-arranged mix of roses, lilies, and seasonal greens.",
            "Roses",
            39.99,
            true
        ));

        bouquets.add(new Bouquet(
            "Romantic Rose Dozen",
            "A classic dozen red roses arranged with baby’s breath.",
            "Roses",
            49.99,
            true
        ));

        bouquets.add(new Bouquet(
            "Lavender Dreams",
            "Lavender roses and purple accents for a calming look.",
            "Mixed",
            44.25,
            true
        ));
        
        bouquets.add(new Bouquet(
            "Honeybee Hug",
            "Yellow Sunflowers and Daises with blue accents.",
            "Mixed",
            35.99,
            true
        ));
        
        bouquets.add(new Bouquet(
            "BeetleJuice Boogie",
            "Deep Violet Dablias with a hint of Green Trick Dianthus.",
            "Mixed",
            65.99,
            true
        ));
        
        bouquets.add(new Bouquet(
            "Cottage Charm",
            "Pink Roses mixed with Pink Hydrangeas and varied greenery for a rustic touch.",
            "Mixed",
            22.99,
            true
        ));
        
        bouquets.add(new Bouquet(
            "Tulip Treasures",
            "12 count of different colored tulips.",
            "Tulips",
            65.99,
            true
        ));
        
        bouquets.add(new Bouquet(
            "Happy Camper ",
            "15 Large Traditional Sunflowers.",
            "Sunflowers",
            30.99,
            true
        ));

        bouquets.add(new Bouquet(
            "Garden of Pink",
            "24 count of various shades of pink carnations mixed with white baby's breath.",
            "Carnations",
            15.99,
            true
        ));
    }

    public List<Bouquet> getAll() {
        return new ArrayList<>(bouquets);
    }

    public List<Bouquet> search(String name, String category, int minPrice, int maxPrice) {
        List<Bouquet> results = new ArrayList<>();

        for (Bouquet b : bouquets) {
            boolean matchesName = (
                (name == null || name.isEmpty())
                    || b.getName().toLowerCase().contains(name.toLowerCase())
                    || b.getDescription().toLowerCase().contains(name.toLowerCase())
                );

            boolean matchesCategory = (category == null || category.isEmpty())
                    || b.getCategory().equalsIgnoreCase(category);

            boolean inRange = b.getPrice() >= minPrice && b.getPrice() <= maxPrice;

            if (matchesName && matchesCategory && inRange) {
                results.add(b);
            }
        }

        return results;
    }
}
