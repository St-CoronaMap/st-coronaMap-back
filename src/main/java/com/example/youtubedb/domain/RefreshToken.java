//package com.example.youtubedb.domain;
//
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Getter
//@NoArgsConstructor
//@Entity
//@Table(name = "refresh_token")
//public class RefreshToken {
//
//    @Id
//    private String key;
//    private String value;
//
//    public RefreshToken updateValue(String token) {
//        this.value = token;
//        return this;
//    }
//
//    @Builder
//    public RefreshToken(String key, String value) {
//        this.key = key;
//        this.value = value;
//    }
//}