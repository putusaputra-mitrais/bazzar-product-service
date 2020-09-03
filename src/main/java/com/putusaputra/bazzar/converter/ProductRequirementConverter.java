package com.putusaputra.bazzar.converter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.putusaputra.bazzar.dto.ProductRequirement;
import com.putusaputra.bazzar.util.GlobalUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductRequirementConverter implements AttributeConverter<List<ProductRequirement>, String> {
    
    @Override
    public String convertToDatabaseColumn(List<ProductRequirement> attribute) {
        String jsonString = "";
        
        try {
            jsonString = GlobalUtil.convertObjectToJson(attribute);
        } catch (JsonProcessingException e) {
            log.error(null, e);
        }
        
        return jsonString;
    }

    @Override
    public List<ProductRequirement> convertToEntityAttribute(String dbData) {
        List<ProductRequirement> reqs = new ArrayList<>();
        try {
            JsonNode node = GlobalUtil.convertStringToJsonObject(dbData);
            if (node.isArray()) {
                ArrayNode arrayNode = (ArrayNode) node;
                Iterator<JsonNode> iterator = arrayNode.iterator();
                while(iterator.hasNext()) {
                    JsonNode objNode = (ObjectNode) iterator.next();
                    String productId = objNode.get("productId").asText();
                    
//                    JsonNode productNode = objNode.get("product");
//                    Product product = Product.builder()
//                            .id(productNode.get("id").asText())
//                            .name(productNode.get("name").asText())
//                            .stock(productNode.get("stock").asInt())
//                            .purchasePrice(productNode.get("purchasePrice").asDouble())
//                            .purchasePrice(productNode.get("sellPrice").asDouble())
//                            .build();
                    int qty = objNode.get("qty").asInt();
                    ProductRequirement req = ProductRequirement.builder()
                            .productId(productId)
                            .qty(qty)
                            .build();
                    reqs.add(req);
                }
            }
        } catch (JsonMappingException e) {
            log.error(null, e);
        } catch (JsonProcessingException e) {
            log.error(null, e);
        }
        
        return reqs;
        
    }

}
