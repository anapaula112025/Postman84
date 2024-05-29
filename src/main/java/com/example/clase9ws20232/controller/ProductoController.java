package com.example.clase9ws20232.controller;

import com.example.clase9ws20232.entity.Product;
import com.example.clase9ws20232.entity.Supplier;
import com.example.clase9ws20232.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductoController {

    final ProductRepository productRepository;

    public ProductoController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //LISTAR
    @GetMapping(value = {"/list", ""})
    public List<Product> listaProductos() {
        return productRepository.findAll();
    }

    //OBTENER POR ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<HashMap<String, Object>> buscarProducto(@PathVariable("id") String idStr) {

        HashMap<String, Object> respuesta = new HashMap<>();

        try {
            int id = Integer.parseInt(idStr);
            Optional<Product> byId = productRepository.findById(id);

            if (byId.isPresent()) {
                respuesta.put("result", "ok");
                respuesta.put("producto", byId.get());
                return ResponseEntity.ok(respuesta);
            } else {
                respuesta.put("msg", "Producto no encontrado");
            }

        } catch (NumberFormatException e) {
            respuesta.put("msg", "el ID debe ser un número entero positivo");
        }
        respuesta.put("result", "failure");
        return ResponseEntity.badRequest().body(respuesta);
    }

    // CREAR /product
    @PostMapping(value = {"", "/"})
    public ResponseEntity<HashMap<String, Object>> guardarProducto(
            @RequestBody Product product,
            @RequestParam(value = "fetchId", required = false) boolean fetchId) {

        HashMap<String, Object> responseJson = new HashMap<>();

        productRepository.save(product);
        if (fetchId) {
            responseJson.put("id", product.getId());
        }
        responseJson.put("estado", "creado");
        return ResponseEntity.status(HttpStatus.CREATED).body(responseJson);
    }

    // ACTUALIZAR
    @PutMapping(value = {"", "/"}, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<HashMap<String, Object>> actualizar(Product productRecibido) {

        HashMap<String, Object> rpta = new HashMap<>();

        if (productRecibido.getId() > 0) {

            Optional<Product> byId = productRepository.findById(productRecibido.getId());
            if (byId.isPresent()) {
                Product productFromDb = byId.get();

                if (productRecibido.getProductName() != null)
                    productFromDb.setProductName(productRecibido.getProductName());

                if (productRecibido.getUnitPrice() != null)
                    productFromDb.setUnitPrice(productRecibido.getUnitPrice());

                if (productRecibido.getUnitsInStock() != null)
                    productFromDb.setUnitsInStock(productRecibido.getUnitsInStock());

                if (productRecibido.getUnitsOnOrder() != null)
                    productFromDb.setUnitsOnOrder(productRecibido.getUnitsOnOrder());

                if (productRecibido.getSupplier() != null)
                    productFromDb.setSupplier(productRecibido.getSupplier());

                if (productRecibido.getCategory() != null)
                    productFromDb.setCategory(productRecibido.getCategory());

                if (productRecibido.getQuantityPerUnit() != null)
                    productFromDb.setQuantityPerUnit(productRecibido.getQuantityPerUnit());

                if (productRecibido.getReorderLevel() != null)
                    productFromDb.setReorderLevel(productRecibido.getReorderLevel());

                if (productRecibido.getDiscontinued() != null)
                    productFromDb.setDiscontinued(productRecibido.getDiscontinued());

                productRepository.save(productFromDb);
                rpta.put("estado", "actualizado");
                return ResponseEntity.ok(rpta);
            } else {
                rpta.put("estado", "error");
                rpta.put("msg", "El producto a actualizar no existe");
                return ResponseEntity.badRequest().body(rpta);
            }
        } else {
            rpta.put("estado", "error");
            rpta.put("msg", "debe enviar un producto con ID");
            return ResponseEntity.badRequest().body(rpta);
        }
    }

    // Eliminar un producto po ID
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HashMap<String, Object>> borrar(@PathVariable("id") String idStr){

        HashMap<String, Object> rpta = new HashMap<>();
        try{
            int id = Integer.parseInt(idStr);

            if(productRepository.existsById(id)){
                productRepository.deleteById(id);
                rpta.put("estado","borrado exitoso");
                return ResponseEntity.ok(rpta);
            }else{
                rpta.put("estado","error");
                rpta.put("msg","el ID enviado no existe");
                return ResponseEntity.badRequest().body(rpta);
            }
        }catch (NumberFormatException e){
            rpta.put("estado","error");
            rpta.put("msg","el ID debe ser un número");
            return ResponseEntity.badRequest().body(rpta);
        }
    }


    @GetMapping(value = "/prueba", produces = MediaType.APPLICATION_JSON_VALUE)
    public String prueba() {
        return "{\"msg\": \"esto es una prueba\"}";
    }

    @GetMapping("/buscar/{id}")
    public Product buscarF1(@PathVariable("id") int id) {
        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            return null;
        }
    }

    /*
    si id existe -> {result: "ok", producto: <producto>}
    si el id no existe -> {result: "no existe"}
     */
    @GetMapping("/buscar2/{id}")
    public HashMap<String, Object> buscarF2(@PathVariable("id") int id) {

        HashMap<String, Object> respuesta = new HashMap<>();

        Optional<Product> byId = productRepository.findById(id);
        if (byId.isPresent()) {
            respuesta.put("result", "ok");
            respuesta.put("producto", byId.get());
            return respuesta;
        } else {
            respuesta.put("result", "no existe");
            return respuesta;
        }
    }

    //Si no manda nada para crear un producto o para actualizar un producto

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<HashMap<String, String>> gestionException(HttpServletRequest request) {
        HashMap<String, String> responseMap = new HashMap<>();
        if (request.getMethod().equals("POST") || request.getMethod().equals("PUT")) {
            responseMap.put("estado", "error");
            responseMap.put("msg", "Debe enviar un producto");
        }
        return ResponseEntity.badRequest().body(responseMap);
    }


}
