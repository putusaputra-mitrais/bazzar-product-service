package com.putusaputra.bazzar.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.putusaputra.bazzar.constant.ProductStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    
    @Column(length = 50, nullable = false)
    private String name;
    
    @Column(nullable = false)
    private int stock;
    
    @Column(nullable = false)
    private double purchasePrice;
    
    @Column(nullable = false)
    private double sellPrice;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;
    
    @OneToOne
    @JoinColumn(name = "package_requirement_id")
    private PackageRequirement packageRequirement;
}
