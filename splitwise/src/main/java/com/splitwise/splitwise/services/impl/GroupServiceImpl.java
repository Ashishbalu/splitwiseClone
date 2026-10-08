package com.splitwise.splitwise.services.impl;

import com.splitwise.splitwise.dtos.request.AddMembersRequest;
import com.splitwise.splitwise.dtos.request.CreateGroupRequest;
import com.splitwise.splitwise.entities.SplitGroup;
import com.splitwise.splitwise.entities.User;
import com.splitwise.splitwise.exceptions.ResourceAlreadyExist;
import com.splitwise.splitwise.exceptions.ResourceDoesNotExist;
import com.splitwise.splitwise.repositories.GroupRepo;
import com.splitwise.splitwise.repositories.UserRepo;
import com.splitwise.splitwise.services.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepo groupRepo;
    private final UserRepo userRepo;

    @Override
    @Transactional
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

    @Override
    @Transactional
    public List<User> addMembers(String userId, String groupId, AddMembersRequest addMembersRequest) {
        User currentUser = userRepo.findById(userId).orElseThrow(
                () -> new ResourceDoesNotExist("user with id: " + userId + "does not found")
        );
        SplitGroup splitGroup = groupRepo.findByIdWithMembers(groupId)
                .orElseThrow(()-> new ResourceDoesNotExist("Group with id: '" + groupId + "', not found "));

        if (!groupRepo.existsByIdAndUser(groupId, currentUser)){
            throw new ResourceDoesNotExist("user with this id: '" + userId + "', does not exit or found");
        }


        List<User> addedmembers = new ArrayList<>();
        List<User> usersByEmails = userRepo.findByEmailIn(addMembersRequest.userEmails());

        if (addedmembers.size() != addMembersRequest.userEmails().size()){
            throw new ResourceDoesNotExist("one or more users with email: " + addMembersRequest.userEmails() + " not found");
        }

        for (User user : usersByEmails){
            if (splitGroup.getUser().contains(user)){
                continue;
            }
            splitGroup.addUser(user);
            addedmembers.add(user);
        }
        groupRepo.save(splitGroup);
        return addedmembers;
    }
}
