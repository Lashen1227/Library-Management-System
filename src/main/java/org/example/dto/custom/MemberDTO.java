package org.example.dto.custom;

import org.example.dto.SuperDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO implements SuperDTO {
    private String id;
    private String name;
    private String address;
    private String email;
    private String contact;
}