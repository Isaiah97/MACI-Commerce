package service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import model.Bouquet;

public class CatalogServiceTest {

    @Test
    void testSearchAllReturnsAllBouquets() {
        CatalogService catalog = new CatalogService();

        // name/category empty, wide price range -> should basically return all
        List<Bouquet> results = catalog.search("", "", 0, 10_000);

        // we know constructor seeds at least 3 bouquets
        assertTrue(results.size() >= 3, "Expected at least 3 seeded bouquets");
    }

    @Test
    void testSearchByCategory() {
        CatalogService catalog = new CatalogService();

        List<Bouquet> results = catalog.search("", "Roses", 0, 10_000);

        assertFalse(results.isEmpty(), "Expected at least one bouquet in category 'Roses'");
        for (Bouquet b : results) {
            assertEquals("Roses", b.getCategory(), "All results should be in category 'Roses'");
        }
    }

    @Test
    void testSearchPriceRangeFiltersOutExpensive() {
        CatalogService catalog = new CatalogService();

        // Only allow up to e.g. 45; anything above should be filtered out
        List<Bouquet> results = catalog.search("", "", 0, 45);

        assertFalse(results.isEmpty(), "Expected some bouquets within price range");
        for (Bouquet b : results) {
            assertTrue(b.getPrice() <= 45.0,
                    "Bouquet " + b.getName() + " should not exceed max price 45");
        }

        // Optional: ensure the expensive one is not present by name
        boolean hasRomanticRoseDozen = results.stream()
                .anyMatch(b -> "Romantic Rose Dozen".equals(b.getName()));
        assertFalse(hasRomanticRoseDozen, "Romantic Rose Dozen should be filtered out as too expensive");
    }

    @Test
    void testSearchByNameSubstring() {
        CatalogService catalog = new CatalogService();

        List<Bouquet> results = catalog.search("Lavender", "", 0, 10_000);

        assertFalse(results.isEmpty(), "Expected at least one bouquet with 'Lavender' in the name");

        // Check that at least one result has the expected name
        boolean hasLavenderDreams = results.stream()
                .anyMatch(b -> "Lavender Dreams".equals(b.getName()));
        assertTrue(hasLavenderDreams, "Expected to find 'Lavender Dreams' in search results");
    }
}
