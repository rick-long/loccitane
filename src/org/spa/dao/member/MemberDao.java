package org.spa.dao.member;

import org.spa.dao.base.BaseDao;
import org.spa.model.user.User;
import org.spa.vo.page.Page;
import org.spa.vo.user.MemberAdvanceVO;

import java.util.List;

/**
 * Created by Ivy on 2017/2/28.
 */
public interface MemberDao extends BaseDao<User> {
    Page<User> getMemberList(MemberAdvanceVO advanceVO);

    List<User> getAllMemberList(MemberAdvanceVO advanceVO);

    Page<User> getMemberVisitList(MemberAdvanceVO advanceVO);

    List<User> getAllMemberVisitList(MemberAdvanceVO advanceVO);

    Page<User> getMemberNotVisitList(MemberAdvanceVO advanceVO);

    List<User> getAllMemberNotVisitList(MemberAdvanceVO advanceVO);
    
    public Page<User> getMemberSpentList(MemberAdvanceVO advanceVO);
    
    public List<User> getUsersByIds(final Long[] uIds);
    
    public List<Object[]> getMemberDetailsExportToCSVForSF(final Integer limitNum,final Boolean isDemo);
    
    public void updateUserSetLastModifier(final String date,final Boolean demoRecords);
    
    public List<User> getUsersByBirthdayMonth(final Integer month);
    
    public List<Long> getMemberIdsNotIn(final List<Long> ids);
}
