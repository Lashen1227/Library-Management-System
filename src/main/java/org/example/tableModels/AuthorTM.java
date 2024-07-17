package org.example.tableModels;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorTM {
    private int id;
    private String name;
    private String contact;
}