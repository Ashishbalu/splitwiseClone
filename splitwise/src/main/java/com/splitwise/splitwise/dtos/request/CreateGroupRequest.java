package com.splitwise.splitwise.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateGroupRequest(
        @NotBlank
        @Size(max = 100)
        String groupName,
        String description
) {
}
