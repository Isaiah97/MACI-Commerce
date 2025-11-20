package service;

import model.*;

import model.Bouquet;
import java.util.List;

public class ProductManager {
    private List<Bouquet> catalog;

    public ProductManager(List<Bouquet> catalog) {
        this.catalog = catalog;
    }

    public void addProduct(Bouquet bouquet) {
        catalog.add(bouquet);
    }

    public boolean removeProductById(String id) {
        return catalog.removeIf(b -> b.getId().equals(id));
    }
}
