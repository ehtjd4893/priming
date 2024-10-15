package com.priming.toilet.service;

import com.priming.toilet.entity.MemberEntity;
import com.priming.toilet.repository.MemberRepository;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MemberService {

  public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }

  private MemberRepository memberRepository;

  public List<MemberEntity> getAllMembers() {
    return memberRepository.findAll();
  }

  public void registerMember(MemberEntity newMember) {
    memberRepository.save(newMember);
  }

  public List<MemberEntity> getNextToiletCleanUpMember() {
    List<MemberEntity> result = memberRepository.getNextToiletCleanUpMember();

    if (result.isEmpty()) {
      throw new RuntimeException("Can't find next clean-up order");
    }

    return result;
  }

  public List<MemberEntity> getAllToiletCleanUpMembers() {
    return memberRepository.getAllToiletCleanUpMembers();
  }

  public void saveCleanUpCompletionTime(String memberName) throws NameNotFoundException {
    MemberEntity member = memberRepository.findByMemberName(memberName);

    if (member == null) {
      throw new NameNotFoundException("There is no " + memberName + " in DB");
    }

    memberRepository.saveCleanUpCompletionTime(member.getMemberId());
  }
}