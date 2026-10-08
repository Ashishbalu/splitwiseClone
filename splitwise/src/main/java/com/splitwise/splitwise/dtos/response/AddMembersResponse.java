package com.splitwise.splitwise.dtos.response;

import java.util.List;

public record AddMembersResponse(
        String groupId,
        List<CommonUserResponse> addedMembers
) {
}
