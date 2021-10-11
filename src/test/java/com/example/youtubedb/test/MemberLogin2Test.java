package com.example.youtubedb.test;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class MemberLogin2Test {

  MemberRepository2 repository = new MemberRepository2();
  MemberLogin2 memberLogin2 = new MemberLogin2(repository);

  @Test
  @DisplayName("login ID를 통해 올바른 Member를 가져오니")
  void isTheCorrectMember() {
    //given
    Member2 member = new Member2("loginId", "password");
    repository.save(member);

    //when
    Member2 loginMember = memberLogin2.login("loginId", "password");

    //then
    assertThat(repository.find(member.loginId()), is(loginMember));
    assertThat(repository.find(member.loginId()).password(), is(loginMember.password()));
  }

  @Test
  @DisplayName("login ID를 통해 올바른 Member를 가져오니 - 여러 명일 경우")
  void isTheCorrectMember2() {
    //given
    Member2 member = new Member2("loginId", "password");
    repository.save(member);
    repository.save(new Member2("loginId1", "password1"));
    repository.save(new Member2("loginId2", "password2"));

    //when
    Member2 loginMember = memberLogin2.login("loginId", "password");

    //then
    assertThat(repository.find(member.loginId()), is(loginMember));
    assertThat(repository.find(member.loginId()).password(), is(loginMember.password()));
  }
}