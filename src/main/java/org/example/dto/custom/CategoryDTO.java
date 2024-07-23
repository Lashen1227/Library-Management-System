package org.example.dto.custom;

import org.example.dto.SuperDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDTO implements SuperDTO {
    private int id;
    private String name;
}
