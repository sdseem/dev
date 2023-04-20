package com.web.dev.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "tags_relation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class TagRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "post_id")
    @JsonProperty("post_id")
    private Integer postId;

    @Column(name = "tag_id")
    @JsonProperty("tag_id")
    private Integer tagId;
}
