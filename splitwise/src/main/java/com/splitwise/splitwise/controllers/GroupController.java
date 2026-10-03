package com.splitwise.splitwise.controllers;

import com.splitwise.splitwise.dtos.request.CreateGroupRequest;
import com.splitwise.splitwise.dtos.response.CreateGroupResponse;
import com.splitwise.splitwise.entities.SplitGroup;
import com.splitwise.splitwise.payload.ApiResponse;
import com.splitwise.splitwise.services.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
                 ApiResponse.success("group created successfully"));
    }

    //list down a group

    //add members to the group

}
