package com.putusaputra.bazzar.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequirement {
    private String productId;
    private int qty;
}
