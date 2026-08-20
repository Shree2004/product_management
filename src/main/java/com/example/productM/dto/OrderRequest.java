package com.example.productM.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

//    @NotNull(message = "User ID is required")
//    private Long userId;

    @NotNull(message = "Address ID is required")
    private Long addressId;
}