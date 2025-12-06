package service;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import model.Bouquet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CatalogServiceTest {

    private CatalogService catalog;

    @BeforeEach
    void setUp() {
        catalog = new CatalogService();
    }

    @Test
    void testGetAllReturnsAllBouquets() {
        List<Bouquet> all = catalog.getAll();
        assertEquals(3, all.size());
    }

    @Test
    void testGetAllReturnsCopy() {
        List<Bouquet> all = catalog.getAll();
        all.clear();
        // internal list should NOT be cleared
        assertEquals(3, catalog.getAll().size());
    }

    @Test
    void testSearchNoFiltersReturnsAllInPriceRange() {
        List<Bouquet> results = catalog.search("", "", 0, 100);
        assertEquals(3, results.size());
    }

    @Test
    void testSearchByNameSubstring() {
        List<Bouquet> results = catalog.search("Lavender", "", 0, 100);
        assertEquals(1, results.size());
        assertEquals("Lavender Dreams", results.get(0).getName());
    }

    @Test
    void testSearchByCategory() {
        List<Bouquet> results = catalog.search("", "Roses", 0, 100);
        assertEquals(2, results.size());
    }

    @Test
    void testSearchPriceRangeFiltersOutExpensive() {
        List<Bouquet> results = catalog.search("", "", 0, 40);
        // only the 39.99 bouquet should be in range
        assertEquals(1, results.size());
        assertEquals("Spring Garden Bouquet", results.get(0).getName());
    }

    @Test
    void testSearchNoMatch() {
        List<Bouquet> results = catalog.search("Nonexistent", "", 0, 100);
        assertTrue(results.isEmpty());
    }
}
