package com.putusaputra.bazzar.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.putusaputra.bazzar.dto.ResponseWrapper;
import com.putusaputra.bazzar.model.Product;
import com.putusaputra.bazzar.service.ProductService;
import com.putusaputra.bazzar.util.GlobalUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/product")
@Slf4j
public class ProductController {
    private static final String PRODUCT_DATA_MESSAGE = "Product Data";
    private static final String PRODUCT_SAVE_SUCCESS_MESSAGE = "Product Data saved successfully";
    private static final String PRODUCT_SAVE_FAILED_MESSAGE = "Product Data save failed";
    private static final String PRODUCT_UPDATE_SUCCESS_MESSAGE = "Product Data updated successfully";
    private static final String PRODUCT_UPDATE_FAILED_MESSAGE = "Product Data update failed";
    private static final String PRODUCT_DELETE_SUCCESS_MESSAGE = "Product Data deleted successfully";
    private static final String PRODUCT_DELETE_FAILED_MESSAGE = "Product Data delete failed";
    
    @Autowired
    private ProductService service;
    
    @GetMapping("/get-all")
    public ResponseEntity<ResponseWrapper> getAll() {
        List<Product> response = null;
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();
        
        response = this.service.findAll();
        responseMessage = GlobalUtil.createResponse(PRODUCT_DATA_MESSAGE, response, errors);
        
        return ResponseEntity.ok(responseMessage);
    }
    
    @GetMapping("/get-by-id")
    public ResponseEntity<ResponseWrapper> getById(@RequestParam("productId") String productId) {
        Product response = null;
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();
        
        response = this.service.findById(productId);
        responseMessage = GlobalUtil.createResponse(PRODUCT_DATA_MESSAGE, response, errors);
        
        return ResponseEntity.ok(responseMessage);
    }
    
    @PostMapping("/save")
    public ResponseEntity<ResponseWrapper> save(@Valid @RequestBody Product product, BindingResult bindingResult) {
        Product response = null;
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();
        
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().forEach(bindError -> errors.add(bindError.getDefaultMessage()));
            return ResponseEntity.ok(GlobalUtil.createResponse(PRODUCT_SAVE_FAILED_MESSAGE, response, errors));
        }
        
        try {
            response = this.service.save(product);
            responseMessage = GlobalUtil.createResponse(PRODUCT_SAVE_SUCCESS_MESSAGE, response, errors);
        } catch (Exception e) {
            errors.add(e.getMessage());
            responseMessage = GlobalUtil.createResponse(PRODUCT_SAVE_FAILED_MESSAGE, response, errors);
            log.error(null, e);
        }

        return ResponseEntity.ok(responseMessage);
    }
    
    @PutMapping("/update")
    public ResponseEntity<ResponseWrapper> update(@Valid @RequestBody Product product, BindingResult bindingResult) {
        Product response = null;
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();
        
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().forEach(bindError -> errors.add(bindError.getDefaultMessage()));
            return ResponseEntity.ok(GlobalUtil.createResponse(PRODUCT_UPDATE_FAILED_MESSAGE, response, errors));
        }
        
        try {
            response = this.service.update(product);
            responseMessage = GlobalUtil.createResponse(PRODUCT_UPDATE_SUCCESS_MESSAGE, response, errors);
        } catch (Exception e) {
            errors.add(e.getMessage());
            responseMessage = GlobalUtil.createResponse(PRODUCT_UPDATE_FAILED_MESSAGE, response, errors);
            log.error(null, e);
        }

        return ResponseEntity.ok(responseMessage);
    }
    
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseWrapper> delete(@RequestParam("productId") String productId) {
        Product response = null;
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();

        try {
            this.service.deleteById(productId);
            responseMessage = GlobalUtil.createResponse(PRODUCT_DELETE_SUCCESS_MESSAGE, response, errors);
        } catch (Exception e) {
            errors.add(e.getMessage());
            responseMessage = GlobalUtil.createResponse(PRODUCT_DELETE_FAILED_MESSAGE, response, errors);
            log.error(null, e);
        }

        return ResponseEntity.ok(responseMessage);
    }
}
