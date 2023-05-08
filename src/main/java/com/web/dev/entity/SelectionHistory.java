package com.web.dev.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "selection_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class SelectionHistory {
    @Id
    @GeneratedValue(generator = "selection_history_id_seq")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item1")
    private Item item1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item2")
    private Item item2;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item3")
    private Item item3;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "item4")
    private Item item4;

    @Column(name = "date_selected")
    private Timestamp dateSelected;

    @Column(name = "p_sport")
    private String sport;

    @Column(name = "p_skill")
    private String skill;

    @Column(name = "p_gender")
    private String gender;

    @Column(name = "p_weight")
    private String weight;

    @Column(name = "p_height")
    private String height;

    @Column(name = "recommendations")
    private String recommendations;

    @Column(name = "foot_size")
    private String footSize;

    @Column(name = "boot_size")
    private String bootSize;

    @Column(name = "len_rec")
    private String len;

    @Column(name = "pole_len")
    private String poleLen;
}
