package com.web.dev.dto.db;

import lombok.*;

@Data
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class TagWithCountDto {
    private String tagName;
    private Long countUsages;

    public TagWithCountDto(String tagName, Long countUsages) {
        this.tagName = tagName;
        this.countUsages = countUsages;
    }
}
