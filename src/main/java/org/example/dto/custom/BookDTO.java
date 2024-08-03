package org.example.dto.custom;

import org.example.dto.SuperDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO implements SuperDTO {
    private int id;
    private String name;
    private String isbn;
    private double price;
    private int publisherId;
    private int mainCategoryId;
    private List<Integer> subCategoryIds;
    private List<Integer> authors;
}
