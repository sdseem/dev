package com.web.dev.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "sport_level")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class SportLevel {

    @Id
    @GeneratedValue(generator = "sport_level_id_seq")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "level")
    private Integer level;
}
