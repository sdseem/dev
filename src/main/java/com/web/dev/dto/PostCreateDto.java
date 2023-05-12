package com.web.dev.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class PostCreateDto {
    public String postDescription;
    public String postName;
    public String postContentA;
    public String postContentB;
}
