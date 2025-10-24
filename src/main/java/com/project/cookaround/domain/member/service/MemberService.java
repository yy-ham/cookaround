package com.project.cookaround.domain.member.service;

import com.project.cookaround.domain.member.entity.Member;
import com.project.cookaround.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public Long join(Member member) {
        System.out.println("<Service>");
        validateDuplicateMemberByLoginId(member.getLoginId()); // 중복 아이디 검증
        validateDuplicateMemberByEmail(member.getEmail()); // 중복 이메일 검증
        memberRepository.save(member);
        return member.getId();
    }

    public boolean validateDuplicateMemberByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId).isPresent();
    }

    public boolean validateDuplicateMemberByEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    // 최근 로그인 일시 업데이트
    @Transactional
    public Long updateLastLoginAt(Long id, LocalDateTime lastLoginAt) {
        Member member = memberRepository.findOne(id)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
        member.setLastLoginAt(lastLoginAt);
        return member.getId();
    }

    // 아이디 찾기
    public Member findLoginId(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일입니다."));
        return member;
    }

    // 비밀번호 재설정
    @Transactional
    public Long resetPassword(Member resetPasswordFrom) {
        Member member = memberRepository.findByLoginIdAndEmail(resetPasswordFrom.getLoginId(), resetPasswordFrom.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
        member.setPassword(resetPasswordFrom.getPassword());
        return member.getId();
    }

    // 프로필 이미지 가져오기
    public String getProfile(Long memberId) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));
        return member.getProfile();
    }

    // 프로필 이미지 변경
    @Transactional
    public void updateProfile(Long memberId, MultipartFile newProfile, boolean resetDefault) {
        Member member = memberRepository.findOne(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Path filePath = Paths.get("src/main/resources/static/uploads/profile");
        String originalName = newProfile.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + originalName;
        Path storePath = filePath.resolve(fileName);
        String oldProfile = member.getProfile();

        if (resetDefault) {
            // 1) 커스텀 프로필 (old) -> 기본 프로필 (프로필 삭제 버튼)
            member.setProfile("default.jpg"); // DB에 기본 프로필 파일명 저장
            deleteOldProfile(filePath, oldProfile); // 이전 프로필 파일 삭제

        } else {
            if (oldProfile.equals("default.jpg")) {
                // 2) 기본 프로필 -> 커스텀 프로필 (new)
                saveNewProfile(newProfile, storePath); // 신규 프로필 파일 저장
                member.setProfile(fileName); // DB에 신규 프로필 파일명 저장

            } else {
                // 3) 커스텀 프로필 (old) -> 커스텀 프로필 (new)
                saveNewProfile(newProfile, storePath); // 신규 프로필 파일 저장
                member.setProfile(fileName); // DB에 신규 프로필 파일명 저장

                deleteOldProfile(filePath, oldProfile); // 이전 프로필 파일 삭제
            }
        }
    }

    // 이전 프로필 파일 삭제
    private void deleteOldProfile(Path filePath, String oldProfile) {
        File file = new File(String.valueOf(filePath), oldProfile);
        file.delete();
    }

    // 신규 프로필 파일 저장
    private void saveNewProfile(MultipartFile newProfile, Path storePath) {
        try {
            newProfile.transferTo(storePath);
        } catch (IOException e) {
            throw new RuntimeException("프로필 사진 저장을 실패했습니다.");
        }
    }

}