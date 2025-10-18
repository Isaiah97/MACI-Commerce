package service;

import model.Bouquet;
import java.util.*;
import java.util.stream.Collectors;

public class BouquetCatalog {
    private List<Bouquet> bouquets;

    public BouquetCatalog() {
        bouquets = new ArrayList<>(List.of(
            new Bouquet("B001", "Rose Delight", "Roses", 29.99, true),
            new Bouquet("B002", "Tulip Charm", "Tulips", 19.99, true),
            new Bouquet("B003", "Orchid Elegance", "Orchids", 39.99, false)
        ));
    }

    public List<Bouquet> search(String keyword, String category, double minPrice, double maxPrice) {
        return bouquets.stream()
            .filter(b -> b.getName().toLowerCase().contains(keyword.toLowerCase()) ||
                         b.getCategory().equalsIgnoreCase(category))
            .filter(b -> b.getPrice() >= minPrice && b.getPrice() <= maxPrice)
            .filter(Bouquet::isInStock)
            .collect(Collectors.toList());
    }

    public List<Bouquet> getAll() {
        return bouquets;
    }
}
