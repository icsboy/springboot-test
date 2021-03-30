package com.approval.approval.controller;

import com.approval.approval.domain.Member;
import com.approval.approval.dto.MemberDto;
import com.approval.approval.service.MemberService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Autowired
    private ModelMapper modelMapper;

    private MemberDto convertToDto(Member member) {
        return modelMapper.map(member, MemberDto.class);
    }

    @GetMapping("/member/{id}")
    public Optional<Member> getMember(@PathVariable @Valid Long id) {
        Optional<Member> member = memberService.findMemberById(id);
        return member;
    }

    @GetMapping("/members/approvers")
    public List<MemberDto> list() {
        List<Member> members = memberService.findMembers();
        return members.stream()
                .map(member -> convertToDto(member))
                .collect(Collectors.toList());
    }
}
