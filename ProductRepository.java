package com.abc.ecommerce.repository;

import com.abc.ecommerce.model.Product;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class ProductRepository {
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Optional<Product> findById(Long id) {
        return Optional.ofNullable(products.get(id));
    }

    public List<Product> search(String keyword) {
        keyword = keyword.toLowerCase();
        List<Product> result = new ArrayList<>();
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(keyword)) {
                result.add(p);
            }
        }
        return result;
    }

    public Product save(Product product) {
        long id = idCounter.incrementAndGet();
        product.setId(id);
        products.put(id, product);
        return product;
    }

    public boolean delete(Long id) {
        return products.remove(id) != null;
    }
}
