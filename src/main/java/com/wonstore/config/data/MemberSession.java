package com.wonstore.config.data;

import lombok.Builder;

public class MemberSession {

    public final Long id;

    @Builder
    public MemberSession(Long id) {
        this.id = id;
    }
}
