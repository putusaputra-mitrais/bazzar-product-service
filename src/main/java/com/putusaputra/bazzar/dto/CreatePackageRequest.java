package com.putusaputra.bazzar.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreatePackageRequest {
    private String packageRequirementId;
    private int qty;
    private double purchasePrice;
    private double sellPrice;
}
