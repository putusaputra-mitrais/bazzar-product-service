package com.putusaputra.bazzar.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.putusaputra.bazzar.constant.ProductStatus;
import com.putusaputra.bazzar.dto.CreatePackageRequest;
import com.putusaputra.bazzar.dto.ProductRequirement;
import com.putusaputra.bazzar.model.PackageRequirement;
import com.putusaputra.bazzar.model.Product;
import com.putusaputra.bazzar.repository.PackageRequirementRepository;
import com.putusaputra.bazzar.repository.ProductRepository;

@Service
public class PackageService {
    @Autowired
    private PackageRequirementRepository repository;
    
    @Autowired
    private ProductRepository productRepository;
    
    public PackageRequirement saveRequirement(PackageRequirement req) throws Exception {
        if (this.isProductsAvailable(req)) {
            return this.repository.save(req);
        }
        
        throw new Exception("Products not found");
    }
    
    public boolean isProductsAvailable(PackageRequirement req) {
        List<ProductRequirement> productReqs = req.getProducts();
        long filterCount = productReqs.stream().filter(productReq -> {
            return this.productRepository.findById(productReq.getProductId())
                    .isPresent();
        }).count();
        
        if (filterCount == productReqs.size()) {
            return true;
        }
        
        return false;
    }
    
    public boolean isQtySufficient(CreatePackageRequest request) {
        PackageRequirement packageRequirement = this.repository.findById(request.getPackageRequirementId())
                .orElse(null);
        List<ProductRequirement> requirements = packageRequirement.getProducts();
        long filterCount = requirements.stream().filter(requirement -> {
            int qtyAfterPackage = request.getQty() * requirement.getQty();
            Product product = this.productRepository.findById(requirement.getProductId())
                    .orElse(null);
            return qtyAfterPackage <= product.getStock();
        }).count();
        
        if (filterCount == requirements.size()) {
            return true;
        }
        
        return false;
    }
    
    public void decreaseStock(CreatePackageRequest request) {
        PackageRequirement packageRequirement = this.repository.findById(request.getPackageRequirementId())
                .orElse(null);
        List<ProductRequirement> requirements = packageRequirement.getProducts();
        requirements.stream().forEach(requirement -> {
            int qtyAfterPackage = request.getQty() * requirement.getQty();
            Product product = this.productRepository.findById(requirement.getProductId())
                    .orElse(null);
            product.setStock(product.getStock() - qtyAfterPackage);
            this.productRepository.save(product);
        });
    }
    
    @Transactional
    public Product savePackage(CreatePackageRequest request) throws Exception {
        Product result = null;
        
        if (!this.isQtySufficient(request)) {
            throw new Exception("qty is not sufficient");
        }
        
        try {
            PackageRequirement requirement = this.repository.findById(request.getPackageRequirementId())
                    .orElse(null);
            Product product = Product.builder()
                    .name(requirement.getName())
                    .stock(request.getQty())
                    .purchasePrice(request.getPurchasePrice())
                    .sellPrice(request.getSellPrice())
                    .status(ProductStatus.PACKAGE)
                    .build();
            result = this.productRepository.save(product);
            this.decreaseStock(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return result;
    }
}
