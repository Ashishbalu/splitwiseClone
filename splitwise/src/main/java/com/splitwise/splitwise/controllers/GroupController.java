package com.splitwise.splitwise.controllers;

import com.splitwise.splitwise.dtos.request.AddMembersRequest;
import com.splitwise.splitwise.dtos.request.CreateGroupRequest;
import com.splitwise.splitwise.dtos.response.AddMembersResponse;
import com.splitwise.splitwise.dtos.response.CommonUserResponse;
import com.splitwise.splitwise.dtos.response.CreateGroupResponse;
import com.splitwise.splitwise.dtos.response.GroupSummaryResponse;
import com.splitwise.splitwise.entities.SplitGroup;
import com.splitwise.splitwise.entities.User;
import com.splitwise.splitwise.payload.ApiResponse;
import com.splitwise.splitwise.repositories.projections.GroupSummmaryProjection;
import com.splitwise.splitwise.services.GroupService;
import com.splitwise.splitwise.utilities.ExpenseUtility;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
            @RequestBody @Valid CreateGroupRequest createGroupRequest) {

        SplitGroup newGroup = groupService.createGroup(createGroupRequest, userId);
        CreateGroupResponse createGroupResponse = new CreateGroupResponse(
                newGroup.getId(),
                newGroup.getGroupName(),
                newGroup.getDescription()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("group created successfully", createGroupResponse));
    }

    //list down a group
    @GetMapping
    public ResponseEntity<ApiResponse<List<GroupSummaryResponse>>> getGroupDetails(
            @RequestHeader("x-user-id") String userId
            ) {
        List<GroupSummmaryProjection> summaryrojection = groupService.getGroupSummaryForUser(userId);
        List<GroupSummaryResponse> groupSummaryResponses = summaryrojection.stream()
                .map(groupSummmaryProjection -> {
                    BigDecimal totalPaid = groupSummmaryProjection.getTotalPaid();
                    BigDecimal totalOwed = groupSummmaryProjection.getTotalOwed();
                    BigDecimal totalBalance = ExpenseUtility.convertToRupees(totalOwed.subtract(totalPaid));
                    String balanceType = totalBalance.compareTo(BigDecimal.ZERO) > 0 ? "Owed" : "Paid";
                    return new GroupSummaryResponse(
                            groupSummmaryProjection.getGroupId(),
                            groupSummmaryProjection.getGroupName(),
                            groupSummmaryProjection.getGroupDescription(),
                            totalBalance, balanceType
                    );
                })
                .toList();
        return ResponseEntity.ok(ApiResponse.success("groups summary fetched successfully:", groupSummaryResponses));
    }

    //add members to the group
    @PostMapping("/{groupId}/members")
    public ResponseEntity<ApiResponse<AddMembersResponse>> addmembers(
            @RequestHeader("x-user-id") String userId,
            @PathVariable String groupId, @RequestBody @Valid AddMembersRequest addMembersRequest
    ) {
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
