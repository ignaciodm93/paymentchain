package com.paymentchain.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paymentchain.product.entities.Product;
import com.paymentchain.product.respository.ProductRepository;

@RestController
@RequestMapping("/product")
public class ProductRestController {
    
    @Autowired
    ProductRepository productRepository;
    
    @GetMapping()
    public List<Product> list() {
        return productRepository.findAll();
    }
    
//    @GetMapping("/{id}")
//    public ResponseEntity<?> get(@PathVariable long id) {
//         Optional<Product> customer = productRepository.findById(id);
//        if (customer.isPresent()) {
//            return new ResponseEntity<>(customer.get(), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    
//    @PutMapping("/{id}")
//    public ResponseEntity<?> put(@PathVariable long id, @RequestBody Product input) {
//         Optional<Product> optionalcustomer = productRepository.findById(id);
//        if (optionalcustomer.isPresent()) {
//            Product newcustomer = optionalcustomer.get();
//            newcustomer.setName(input.getName());
//            newcustomer.setPhone(input.getPhone());
//             Product save = productRepository.save(newcustomer);
//          return new ResponseEntity<>(save, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    
    @PostMapping
    public ResponseEntity<?> post(@RequestBody Product input) {
        Product save = productRepository.save(input);
        return ResponseEntity.ok(save);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
         productRepository.deleteById(id);
         return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
