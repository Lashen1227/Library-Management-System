package org.example.service;

import javafx.scene.control.Alert;
import org.example.dto.MemberDTO;
import org.example.entity.Member;
import org.example.repo.MemberRepo;

import java.sql.SQLException;

public class MemberService {
    private final MemberRepo memberRepo = new MemberRepo();
    public boolean addMember(MemberDTO member){
        Member entity = this.covnertDTOtoEntity(member);
        try {
            boolean isSaved = memberRepo.saveMember(entity);
            return isSaved;
        } catch (SQLException | ClassNotFoundException e) {
            if (e instanceof SQLException) {
                if (((SQLException) e).getErrorCode() == 1062) {
                    new Alert(Alert.AlertType.ERROR, "Duplicate Entry").show();
                }
            }
            return false;
        }
    }



    public Member covnertDTOtoEntity(MemberDTO memberDTO){
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setName(memberDTO.getName());
        member.setAddress(memberDTO.getAddress());
        member.setEmail(memberDTO.getEmail());
        member.setContact(memberDTO.getContact());
        return member;
    }

}
