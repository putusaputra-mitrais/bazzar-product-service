package com.putusaputra.bazzar.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.putusaputra.bazzar.converter.ProductRequirementConverter;
import com.putusaputra.bazzar.dto.ProductRequirement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "package_requirement")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageRequirement {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;
    
    @Column(length = 50, nullable = false)
    private String name;
    
    @Column(nullable = false)
    @Convert(converter = ProductRequirementConverter.class)
    private List<ProductRequirement> products;
}
