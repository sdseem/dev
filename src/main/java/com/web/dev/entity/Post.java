package com.web.dev.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import lombok.*;

import java.util.Date;


@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "post_creator")
    @JsonProperty("post_creator")
    private Integer postCreator;

    @Column(name = "publication_date")
    @JsonProperty("publication_date")
    private Date publicationDate;

    @Column(name = "post_description")
    @JsonProperty("post_description")
    private String postDescription;

    @Column(name = "post_name")
    @JsonProperty("post_name")
    private String postName;

    @Column(name = "post_content_a")
    @JsonProperty("post_content_a")
    private String postContentA;

    @Column(name = "post_content_b")
    @JsonProperty("post_content_b")
    private String postContentB;

    @Column(name = "post_main_picture")
    @JsonProperty("post_main_picture")
    private String postMainPicture;

    @Column(name = "post_pic_a")
    @JsonProperty("post_pic_a")
    private String postPicA;

    @Column(name = "view_count")
    @JsonProperty("view_count")
    private int viewCount;
}
