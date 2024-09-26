package com.priming.toilet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "MEMBER")
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity {

  public MemberEntity(int memberId, String memberName) {
    this.memberId = memberId;
    this.memberName = memberName;
  }

  public MemberEntity(int memberId, String memberName, String dayOfCleaning) {
    this.memberId = memberId;
    this.memberName = memberName;
    this.dayOfCleaning = dayOfCleaning;
  }

  @Id
  @Column(name = "memberId")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int memberId;

  @Column(name = "memberName")
  private String memberName;

  @Column(name = "cleanUpMember")
  private boolean cleanUpMember;

  @Column(name = "phoneNumber")
  private String phoneNumber;

  @Column(name = "dayOfCleaning")
  private String dayOfCleaning;
}