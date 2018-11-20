package org.spa.vo.user;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by Ivy on 2016-6-13
 */
public class ResetPasswordVO implements Serializable {

    private String code;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
