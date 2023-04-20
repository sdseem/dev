package com.web.dev.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "fusion_id")
    private String fusionId;

    @Column(name = "email")
    private String email;

    @Column(name = "full_name")
    @JsonProperty("full_name")
    private String fullName;
}
