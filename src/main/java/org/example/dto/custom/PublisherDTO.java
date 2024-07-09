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
public class PublisherDTO implements SuperDTO {
    private int id;
    private String name;
    private String location;
    private String contact;
}
