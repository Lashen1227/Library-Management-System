package org.example.service.custom.impl;

import org.example.dto.custom.MemberDTO;
import org.example.entity.custom.Member;
import org.example.repo.custom.MemberRepo;
import org.example.service.custom.MemberService;
import org.example.util.exceptions.custom.MemberException;
import org.modelmapper.ModelMapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberServiceIMPL implements MemberService {
    private final MemberRepo memberRepo;
    private final ModelMapper modelMapper;

    public MemberServiceIMPL(ModelMapper mapper,MemberRepo memberRepo) {
        this.memberRepo = memberRepo;
        this.modelMapper = mapper;
    }

    @Override
    public boolean add(MemberDTO member) throws MemberException {
        Member entity = this.convertDTOtoEntity(member);
        try {
            boolean isSaved = memberRepo.save(entity);
            return isSaved;
        } catch (SQLException | ClassNotFoundException e) {

            if (e instanceof SQLException) {
                System.out.println("Error Code : "+((SQLException) e).getErrorCode());
                if (((SQLException) e).getErrorCode() == 1062) {
                    throw new MemberException("ID Already Exists - Cannot Save.");
                } else if (((SQLException) e).getErrorCode() == 1406) {
                    String message = ((SQLException) e).getMessage();
                    String[] s = message.split("'");
                    throw new MemberException("Data is To Large For "+s[1]);
                }
            }
            e.printStackTrace();
            throw new MemberException("Error Occurred Please Contact Developer",e);
        }
    }

    @Override
    public boolean delete(String id) throws MemberException{
        try {
            boolean delete = memberRepo.delete(id);
            return delete;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            throw new MemberException("Error Occurred Please Contact Developer", e);
        }
    }

    @Override
    public boolean update(MemberDTO member) throws MemberException {
        Member entity = convertDTOtoEntity(member);
        try {
            boolean isUpdated = memberRepo.update(entity);
            return isUpdated;
        } catch (SQLException | ClassNotFoundException e) {
            if (e instanceof SQLException){
                if (((SQLException) e).getErrorCode() == 1406) {
                    String message = ((SQLException) e).getMessage();
                    String[] s = message.split("'");
                    throw new MemberException("Data is To Large For "+s[1]);
                }
            }
            throw new MemberException("Error Occurred Please Contact Developer",e);
        }
    }

    @Override
    public Optional<MemberDTO> search(String id) throws MemberException {
        try {
            Optional<Member> member = memberRepo.search(id);
            if (member.isPresent()){
                MemberDTO memberDTO = convertEntityToDTO(member.get());
                return Optional.of(memberDTO);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new MemberException("Please Contact Developer",e);
        }
        return Optional.empty();
    }

    @Override
    public List<MemberDTO> getAll() throws MemberException {
        try {
            List<Member> all = memberRepo.getAll();
            if (all.isEmpty()){
                throw new MemberException("No Members Found");
            }
            List<MemberDTO> memberDTOS = new ArrayList<>();
            for (Member member : all) {
                MemberDTO memberDTO = convertEntityToDTO(member);
                memberDTOS.add(memberDTO);
            }
            return memberDTOS;
        } catch (SQLException | ClassNotFoundException e) {
            throw new MemberException("Please Contact Developer",e);
        }
    }



    private Member convertDTOtoEntity(MemberDTO memberDTO){
        Member member = new Member();
        member.setId(memberDTO.getId());
        member.setName(memberDTO.getName());
        member.setAddress(memberDTO.getAddress());
        member.setEmail(memberDTO.getEmail());
        member.setContact(memberDTO.getContact());
        return member;
    }

    private MemberDTO convertEntityToDTO(Member memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setName(memberEntity.getName());
        memberDTO.setAddress(memberEntity.getAddress());
        memberDTO.setEmail(memberEntity.getEmail());
        memberDTO.setContact(memberEntity.getContact());
        return memberDTO;
    }

}