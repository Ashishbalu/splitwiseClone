package com.splitwise.splitwise.services.impl;

import com.splitwise.splitwise.dtos.request.CreateGroupRequest;
import com.splitwise.splitwise.entities.SplitGroup;
import com.splitwise.splitwise.entities.User;
import com.splitwise.splitwise.exceptions.ResourceDoesNotExist;
import com.splitwise.splitwise.repositories.GroupRepo;
import com.splitwise.splitwise.repositories.UserRepo;
import com.splitwise.splitwise.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepo groupRepo;
    private final UserRepo userRepo;

    @Override
    public SplitGroup createGroup(CreateGroupRequest createGroupRequest, String userId) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new ResourceDoesNotExist("user with id: " + userId + "does not found")
        );
        SplitGroup splitGroup = SplitGroup.builder()
                .groupName(createGroupRequest.groupName())
                .description(createGroupRequest.description())
                .build();

        splitGroup.addUser(user);
        return groupRepo.save(splitGroup);
    }
}
