package org.spa.vo.user;

import java.io.Serializable;
import org.spa.model.user.ConsentFormUser;
import org.spa.vo.page.Page;

/**
 * @author Ivy 2016-8-28
 */
public class ConsentFormUserListVO extends Page<ConsentFormUser> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId;
    
    public Long getUserId() {
		return userId;
	}
    public void setUserId(Long userId) {
		this.userId = userId;
	}
}
