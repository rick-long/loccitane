package org.spa.vo.app.member;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.spa.model.company.Company;
import org.spa.utils.WebThreadLocal;

import java.io.Serializable;

/**
 * Created by Ivy on 2016-8-30
 */
public class AppsForgetPasswordVO implements Serializable {

    @NotBlank
    @Email
    private String email;

    private Company company = WebThreadLocal.getCompany();

    private String accountType;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
