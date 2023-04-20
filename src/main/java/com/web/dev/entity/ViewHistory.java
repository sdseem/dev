package com.web.dev.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Table(name = "view_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ViewHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id")
    @JsonProperty("user_id")
    private Integer userId;

    @Column(name = "content_type")
    @JsonProperty("content_type")
    private String contentType;

    @Column(name = "content_id")
    @JsonProperty("content_id")
    private Integer contentId;

    @Column(name = "view_date")
    @JsonProperty("view_date")
    private Date viewDate;
}
