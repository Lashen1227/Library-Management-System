package org.example.dto.custom;

import org.example.dto.SuperDTO;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDTO implements SuperDTO {
    private int id;
    private String name;
    private String contact;
    private String address;
}
