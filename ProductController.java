package com.abc.ecommerce.controller;

import com.abc.ecommerce.model.Product;
import com.abc.ecommerce.service.ProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service = new ProductService();

    @GetMapping
    public List<Product> getCatalog() {
        return service.getCatalog();
    }

    @GetMapping("/{id}")
    public Optional<Product> getProduct(@PathVariable Long id) {
        return service.getProduct(id);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String keyword) {
        return service.search(keyword);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return service.addProduct(product);
    }

    @DeleteMapping("/{id}")
    public boolean deleteProduct(@PathVariable Long id) {
        return service.removeProduct(id);
    }
}
