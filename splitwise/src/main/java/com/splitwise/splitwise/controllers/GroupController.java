package com.splitwise.splitwise.controllers;

import com.splitwise.splitwise.dtos.request.AddMembersRequest;
import com.splitwise.splitwise.dtos.request.CreateGroupRequest;
import com.splitwise.splitwise.dtos.response.AddMembersResponse;
import com.splitwise.splitwise.dtos.response.CommonUserResponse;
import com.splitwise.splitwise.dtos.response.CreateGroupResponse;
import com.splitwise.splitwise.entities.SplitGroup;
import com.splitwise.splitwise.entities.User;
import com.splitwise.splitwise.payload.ApiResponse;
import com.splitwise.splitwise.services.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/v1/group")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    //create a group
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CreateGroupResponse>> createNewGroup(
            @RequestHeader("x-user-id") String userId,
            @RequestBody @Valid CreateGroupRequest createGroupRequest){

         SplitGroup newGroup =groupService.createGroup(createGroupRequest, userId);
         CreateGroupResponse createGroupResponse = new CreateGroupResponse(
                 newGroup.getId(),
                 newGroup.getGroupName(),
                 newGroup.getDescription()
         );

         return ResponseEntity.status(HttpStatus.CREATED).body(
                 ApiResponse.success("group created successfully", createGroupResponse));
    }

    //list down a group

    //add members to the group
    @PostMapping("/{groupId}/members")
    public ResponseEntity<ApiResponse<AddMembersResponse>> addmembers(
            @RequestHeader("x-user-id") String userId,
            @PathVariable String groupId, @RequestBody @Valid AddMembersRequest addMembersRequest
            ){
        List<User> addedMembers = groupService.addMembers(userId, groupId, addMembersRequest);

        List<CommonUserResponse> memberResponse = addedMembers.stream()
                .map(user -> new CommonUserResponse(user.getId(), user.getName(), user.getEmail()))
                .toList();

        AddMembersResponse addMembersResponse = new AddMembersResponse(groupId, memberResponse);

        return ResponseEntity.ok(
                ApiResponse.success("Member added successfully", addMembersResponse)
        );
    }

}
