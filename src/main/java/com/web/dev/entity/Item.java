package com.web.dev.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "item_name")
    @JsonProperty("item_name")
    private String itemName;

    @Column(name = "item_description")
    @JsonProperty("item_description")
    private String itemDescription;

    @Column(name = "item_price")
    @JsonProperty("item_price")
    private Integer itemPrice;

    @Column(name = "item_picture_path")
    @JsonProperty("item_picture_path")
    private String itemPicturePath;

    @Column(name = "item_sport_type")
    @JsonProperty("item_sport_type")
    private String itemSportType;

    @Column(name = "item_equipment_type")
    @JsonProperty("item_equipment_type")
    private String itemEquipmentType;

    @Column(name = "item_sport_style")
    @JsonProperty("item_sport_style")
    private String itemSportStyle;

    @Column(name = "item_sport_level")
    @JsonProperty("item_sport_level")
    private String itemSportLevel;

    @Column(name = "model_year")
    @JsonProperty("item_model_year")
    private Integer itemModelYear;

    @Column(name = "gender")
    @JsonProperty("gender")
    private String gender;

    @Column(name = "has_mount")
    @JsonProperty("has_mount")
    private Boolean hasMount;
}
