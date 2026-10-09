package com.splitwise.splitwise.services;

import com.splitwise.splitwise.dtos.request.AddMembersRequest;
import com.splitwise.splitwise.dtos.request.CreateGroupRequest;
import com.splitwise.splitwise.entities.SplitGroup;
import com.splitwise.splitwise.entities.User;
import com.splitwise.splitwise.repositories.projections.GroupSummmaryProjection;

import java.util.List;

public interface GroupService {
    SplitGroup createGroup(CreateGroupRequest createGroupRequest, String userId);

    List<GroupSummmaryProjection> getGroupSummaryForUser(String userId);

    List<User> addMembers(String userId, String groupId, AddMembersRequest addMembersRequest);
}
