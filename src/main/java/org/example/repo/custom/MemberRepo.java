package org.example.repo.custom;

import org.example.entity.custom.Member;
import org.example.repo.CrudRepository;

public interface MemberRepo extends CrudRepository<Member,String> {
}