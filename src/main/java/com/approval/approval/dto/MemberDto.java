package com.approval.approval.dto;

import com.approval.approval.domain.Document;
import com.approval.approval.domain.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MemberDto {
    private Long id;

    @NotEmpty
    private String name;

    public Member toEntity() {
        Member member = new Member();
        member.setName(this.name);
        return member;
    }

    public static MemberDto from(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setName(member.getName());
        return memberDto;
    }
}
