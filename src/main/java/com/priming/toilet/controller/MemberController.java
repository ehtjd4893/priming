package com.priming.toilet.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.priming.toilet.entity.MemberEntity;
import com.priming.toilet.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.NameNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@RestController
public class MemberController {

  private final MemberService memberService;

  public MemberController(MemberService memberService) {
    this.memberService = memberService;
  }


  @GetMapping("/get-all-members")
  public List<MemberEntity> getAllmembers() {
    return memberService.getAllMembers();
  }

  @PostMapping("/register-member")
  public void registerMember(@RequestBody MemberEntity newMember) {
    memberService.registerMember(newMember);
  }

  @PostMapping("/register-member-for-kakao")
  public void registerMemberForKakao(@RequestBody Map<String, Object> params) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    String jsonInString = mapper.writeValueAsString(params);
    MemberEntity newMember = new MemberEntity();

    memberService.registerMember(newMember);
  }

  @GetMapping("/next-toilet-cleanup-member")
  public List<MemberEntity> getNextToiletCleanUpMember() {
    return memberService.getNextToiletCleanUpMember();
  }

  @PostMapping("/next-toilet-cleanup-member")
  public ResponseEntity<MemberEntity> getNextToiletCleanUpMemberForKakao() {
    List<MemberEntity> members = memberService.getNextToiletCleanUpMember();

    return ResponseEntity.ok(members.get(0));
  }

  @GetMapping("/all-toilet-cleanup-members")
  public List<MemberEntity> getAllToiletCleanUpMembers() {
    return memberService.getAllToiletCleanUpMembers();
  }

  @PostMapping("/all-toilet-cleanup-members")
  public ResponseEntity<?> getAllToiletCleanUpMembersForKakao() {
    List<MemberEntity> members = memberService.getAllToiletCleanUpMembers();
    Map<String, Object> mapOfMemberEntity = new HashMap<>();
    mapOfMemberEntity.put("numberOfMembers", members.size());

    if (!members.isEmpty()) {
      for (int i = 0; i < members.size(); i++) {
        MemberEntity member = members.get(i);
        String key = "member" + i;
        mapOfMemberEntity.put(key, member);
      }

      return ResponseEntity.ok(mapOfMemberEntity);
    } else {
      return ResponseEntity.ok(Collections.singletonMap("text", "청소 가능 인원이 없습니다."));
    }
  }

  @PutMapping("/save-cleanup-completion-time")
  public void saveCleanUpCompletionTime(@RequestParam("memberName") String memberName) throws NameNotFoundException {
    memberService.saveCleanUpCompletionTime(memberName);
  }

  @PostMapping("/save-cleanup-completion-time-for-kakao")
  public ResponseEntity<?> saveCleanUpCompletionTimeForKakao(@RequestBody Map<String, Object> params) {
    try {
    LinkedHashMap<String, Object> action = (LinkedHashMap<String, Object>) params.get("action");
    LinkedHashMap<String, Object> detailParams = (LinkedHashMap<String, Object>) action.get("detailParams");
    String memberName = Arrays.stream(((LinkedHashMap<String, Object>) detailParams.get("memberName")).get("origin").toString().split(" ")).toList().get(0);

    memberService.saveCleanUpCompletionTime(memberName);
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.badRequest().body(Collections.singletonMap("responseText", "청소 날짜 업데이트 실패! 관리자에게 문의하세요!"));
    }
    return ResponseEntity.ok(Collections.singletonMap("responseText", "청소 날짜 업데이트 완료! 고생하셨습니다!"));
  }

}
