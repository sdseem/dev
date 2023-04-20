package com.web.dev.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "feedback")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "user_id")
    @JsonProperty("user_id")
    private Integer userId;

    @Column(name = "feedback_title")
    @JsonProperty("feedback_title")
    private String feedbackTitle;

    @Column(name = "feedback_text")
    @JsonProperty("feedback_text")
    private String feedbackText;

}
