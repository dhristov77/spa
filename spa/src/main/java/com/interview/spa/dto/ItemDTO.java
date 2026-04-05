package com.interview.spa.dto;

import com.interview.spa.enums.ItemType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDTO {
    private Long id;
    private Long number;
    private ItemType type;  
    private String text;
}
