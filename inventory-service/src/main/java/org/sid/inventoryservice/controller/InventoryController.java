package org.sid.inventoryservice.controller;

import org.sid.inventoryservice.enteties.Product;
import org.sid.inventoryservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class InventoryController {

    @Autowired
    ProductRepository productRepository;

    @PostMapping("/add_product")
    public Product addProduct(@RequestBody Product product){
        return productRepository.save(product);
    }



    @DeleteMapping("/delete_product/{Id}")
    public void deleteProduct(@PathVariable("Id") Long id) {
        this.productRepository.deleteById(id);
    }



}
