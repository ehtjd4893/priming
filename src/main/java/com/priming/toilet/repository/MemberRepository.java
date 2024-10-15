package com.priming.toilet.repository;

import com.priming.toilet.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {

  @Query("SELECT new com.priming.toilet.entity.MemberEntity(m.memberId, m.memberName, m.dayOfCleaning) FROM MemberEntity m WHERE m.cleanUpMember = true ORDER BY m.dayOfCleaning ASC")
  List<MemberEntity> getAllToiletCleanUpMembers();

  @Query(value = "SELECT new com.priming.toilet.entity.MemberEntity(m.memberId, m.memberName, m.dayOfCleaning) from MemberEntity m WHERE m.cleanUpMember = true and m.dayOfCleaning = (SELECT MIN(m2.dayOfCleaning) FROM MemberEntity m2)")
  List<MemberEntity> getNextToiletCleanUpMember();

  @Query(value = "UPDATE MEMBER SET dayOfCleaning = CURRENT_TIMESTAMP() WHERE memberId = :memberId",  nativeQuery = true)
  @Transactional
  @Modifying
  void saveCleanUpCompletionTime(@Param("memberId") long memberId);

  @Query(value = "SELECT new com.priming.toilet.entity.MemberEntity(m.memberId, m.memberName) FROM MemberEntity m WHERE m.memberName = :memberName")
  MemberEntity findByMemberName(@Param("memberName") String memberName);
}
