package com.approval.approval.service;

import com.approval.approval.domain.Member;
import com.approval.approval.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 전체 멤버 조회
     * @return
     */
    public List<Member> findMembers() {
        List<Member> members = memberRepository.findByRole("approver");
        return members;
    }

    public Optional<Member> findMemberById(Long id) {
        return memberRepository.findById(id);
    }
}
