package com.example.backend.repository.member;

import com.example.backend.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQuerydsl {
    Optional<Member> findByEmail(String email);
}
