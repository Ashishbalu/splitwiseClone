package com.splitwise.splitwise.services;

import com.splitwise.splitwise.dtos.request.CreateGroupRequest;
import com.splitwise.splitwise.entities.SplitGroup;

public interface GroupService {
    SplitGroup createGroup(CreateGroupRequest createGroupRequest, String userId);
}
