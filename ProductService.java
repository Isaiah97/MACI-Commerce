package com.abc.ecommerce.service;

import com.abc.ecommerce.model.Product;
import com.abc.ecommerce.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private final ProductRepository repository = new ProductRepository();

    public List<Product> getCatalog() {
        return repository.findAll();
    }

    public Optional<Product> getProduct(Long id) {
        return repository.findById(id);
    }

    public List<Product> search(String keyword) {
        return repository.search(keyword);
    }

    public Product addProduct(Product product) {
        return repository.save(product);
    }

    public boolean removeProduct(Long id) {
        return repository.delete(id);
    }
}
