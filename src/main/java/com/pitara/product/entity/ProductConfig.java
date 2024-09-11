package com.pitara.product.entity;

import com.pitara.product.dto.ColorCode;
import com.pitara.product.dto.ValueLabel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "config")
@Getter
@Setter
public class ProductConfig {

    @Id
    private String id;

    private String type;
    private List<ValueLabel> category;
    private List<ValueLabel> pattern;
    private List<ValueLabel> fabric;
    private List<ColorCode> color;
    private List<ValueLabel> isAvailable;
    private List<ValueLabel> size;
    private List<ValueLabel> occasion;

    @Field("wash_care")
    private List<ValueLabel> washCare;
    @Field("payment_type")
    private List<ValueLabel> paymentType;

}
