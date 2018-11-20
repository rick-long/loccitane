package org.spa.service.book;

import org.spa.dao.base.BaseDao;
import org.spa.model.book.Block;
import org.spa.model.shop.Room;
import org.spa.model.shop.Shop;
import org.spa.model.user.User;
import org.spa.vo.book.BlockVO;
import org.spa.vo.book.CellVO;
import org.spa.vo.book.ViewVO;
import org.spa.vo.staff.ScheduleWeekVO;

import java.util.Date;
import java.util.List;

/**
 * Created by Ivy on 2016-7-6
 */
public interface BlockService extends BaseDao<Block> {

    public Long save(BlockVO blockVO);

    public Long saveBlock(Block block);

    public Long update(BlockVO blockVO);

    public Block transferToBlock(BlockVO blockVO);

    //public List<Resource> getTherapistBlockList(Long shopId, Date searchDate);

    String checkBlock(Block newBlock);

    /*public boolean checkBlock(BookItem bookItem);*/

    public void remove(BlockVO blockVO);

    boolean checkBlock(Shop shop, User therapist, Date startTime, Date endTime);

    boolean checkBlock(Shop shop, Room room, Date startTime, Date endTime);

    String checkRoomBlock(Block newBlock);

    List<CellVO> getCellList(ViewVO viewVO);

    Double countAnnualLeaveDays(User user, Date currentDate);

    Double countSickLeaveDays(User user, Date currentDate);

    Double countNoPaidLevaveDays(User user, String payrollMonth);

    ScheduleWeekVO listSchedule(Long shopId, Long staffId, Date startDate, Date endDate);

    String saveSchedule(ScheduleWeekVO scheduleWeekVO);
    
    List<Block> checkRoomBlock(Long shopId, Long roomId, Date startTime, Date endTime);
}
