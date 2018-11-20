package org.spa.vo.app.member;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Ivy on 2016-6-13
 */
public class ChangeAppsPasswordVO implements Serializable {

    @NotNull
    private Long userId;

    // 是否需要检查old password
    private Boolean checkPassword = true;

    private String oldPassword;

    private String password;

    private String confirmPassword;

    // 是否通过email发送password
    private Boolean sendPasswordByEmail;

    public Boolean getCheckPassword() {
        return checkPassword;
    }

    public void setCheckPassword(Boolean checkPassword) {
        this.checkPassword = checkPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getSendPasswordByEmail() {
        return sendPasswordByEmail;
    }

    public void setSendPasswordByEmail(Boolean sendPasswordByEmail) {
        this.sendPasswordByEmail = sendPasswordByEmail;
    }
}
