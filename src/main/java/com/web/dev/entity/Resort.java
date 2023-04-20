package com.web.dev.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "Resorts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Resort {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "resort_name")
    @JsonProperty("resort_name")
    private String resortName;

    @Column(name = "main_desc")
    @JsonProperty("main_desc")
    private String mainDesc;

    @Column(name = "main_content")
    @JsonProperty("main_content")
    private String mainContent;

    @Column(name = "resort_main_picture")
    @JsonProperty("resort_main_picture")
    private String resortMainPicture;

    @Column(name = "resort_pic_a")
    @JsonProperty("resort_pic_a")
    private String resortPicA;

    @Column(name = "resort_pic_b")
    @JsonProperty("resort_pic_b")
    private String resortPicB;

    @Column(name = "resort_pic_c")
    @JsonProperty("resort_pic_c")
    private String resortPicC;

    @Column(name = "resort_place")
    @JsonProperty("resort_place")
    private String resortPlace;

    @Column(name = "view_count")
    @JsonProperty("view_count")
    private Integer viewCount;
}
