package com.web.dev.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "post_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class PostComment {
    @Id
    @GeneratedValue(generator = "post_comments_id_seq")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_user", nullable = false)
    private User user;

    @Column(name = "comment_post")
    private Integer post;

    @Column(name = "comment_text")
    public String text;

    @Column(name = "date_created")
    private Date dateCreated;
}
