package org.example.dto.custom;

import org.example.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO implements SuperDTO {
    private int id;
    private String name;
    private String isbn;
    private double price;
    private String author;
    private int publisherId;
    private int mainCategoryId;
}
