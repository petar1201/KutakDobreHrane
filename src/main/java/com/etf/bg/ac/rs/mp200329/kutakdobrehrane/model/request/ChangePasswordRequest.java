package com.etf.bg.ac.rs.mp200329.kutakdobrehrane.model.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String username;
    private String oldPassword;
    private String newPassword;
}
