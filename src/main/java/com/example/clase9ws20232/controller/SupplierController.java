package com.example.clase9ws20232.controller;

import com.example.clase9ws20232.entity.Supplier;
import com.example.clase9ws20232.repository.SupplierRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

    final SupplierRepository supplierRepository;

    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping("")
    public List<Supplier> listar() {
        return supplierRepository.findAll();
    }

}
