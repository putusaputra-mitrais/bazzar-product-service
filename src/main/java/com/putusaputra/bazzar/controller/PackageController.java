package com.putusaputra.bazzar.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.putusaputra.bazzar.dto.CreatePackageRequest;
import com.putusaputra.bazzar.dto.ResponseWrapper;
import com.putusaputra.bazzar.model.PackageRequirement;
import com.putusaputra.bazzar.model.Product;
import com.putusaputra.bazzar.service.PackageService;
import com.putusaputra.bazzar.util.GlobalUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/product/package")
@Slf4j
public class PackageController {
    private static final String PACKAGE_DATA_MESSAGE = "Package Data";
    private static final String PACKAGE_SAVE_SUCCESS_MESSAGE = "Package Data saved successfully";
    private static final String PACKAGE_SAVE_FAILED_MESSAGE = "Package Data save failed";
    private static final String PACKAGE_UPDATE_SUCCESS_MESSAGE = "Package Data updated successfully";
    private static final String PACKAGE_UPDATE_FAILED_MESSAGE = "Package Data update failed";
    private static final String PACKAGE_DELETE_SUCCESS_MESSAGE = "Package Data deleted successfully";
    private static final String PACKAGE_DELETE_FAILED_MESSAGE = "Package Data delete failed";
    
    @Autowired
    private PackageService service;
    
    @PostMapping("/save-package-requirement")
    public ResponseEntity<ResponseWrapper> savePackageRequirement(@Valid @RequestBody PackageRequirement req, BindingResult bindingResult) {
        PackageRequirement response = null;
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();
        
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().forEach(bindError -> errors.add(bindError.getDefaultMessage()));
            return ResponseEntity.ok(GlobalUtil.createResponse(PACKAGE_SAVE_FAILED_MESSAGE, response, errors));
        }
        
        try {
            response = this.service.saveRequirement(req);
            responseMessage = GlobalUtil.createResponse(PACKAGE_SAVE_SUCCESS_MESSAGE, response, errors);
        } catch (Exception e) {
            errors.add(e.getMessage());
            responseMessage = GlobalUtil.createResponse(PACKAGE_SAVE_FAILED_MESSAGE, response, errors);
            log.error(null, e);
        }

        return ResponseEntity.ok(responseMessage);
    }
    
    @PostMapping("/save-package")
    public ResponseEntity<ResponseWrapper> savePackage(@Valid @RequestBody CreatePackageRequest req, BindingResult bindingResult) {
        Product response = null;
        ResponseWrapper responseMessage = null;
        List<String> errors = new ArrayList<>();
        
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().stream().forEach(bindError -> errors.add(bindError.getDefaultMessage()));
            return ResponseEntity.ok(GlobalUtil.createResponse(PACKAGE_SAVE_FAILED_MESSAGE, response, errors));
        }
        
        try {
            response = this.service.savePackage(req);
            responseMessage = GlobalUtil.createResponse(PACKAGE_SAVE_SUCCESS_MESSAGE, response, errors);
        } catch (Exception e) {
            errors.add(e.getMessage());
            responseMessage = GlobalUtil.createResponse(PACKAGE_SAVE_FAILED_MESSAGE, response, errors);
            log.error(null, e);
        }

        return ResponseEntity.ok(responseMessage);
    }
}
