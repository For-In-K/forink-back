package com.forink.forink.member.entity;

import lombok.Getter;

@Getter
public enum MemberRoleType {

    ROLE_회원(1),
    ROLE_가이드(2),
    ROLE_예비가이드(3),
    ;

    private final long id;

    MemberRoleType(final long id) {
        this.id = id;
    }
}
