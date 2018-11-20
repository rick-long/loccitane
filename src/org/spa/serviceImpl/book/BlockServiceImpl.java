package org.spa.serviceImpl.book;

import com.spa.constant.CommonConstant;
import com.spa.runtime.exception.ResourceException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.*;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.quartz.CronExpression;
import org.spa.daoHibenate.base.BaseDaoHibernate;
import org.spa.model.book.Block;
import org.spa.model.book.BlockItem;
import org.spa.model.book.BookItem;
import org.spa.model.shop.OpeningHours;
import org.spa.model.shop.Room;
import org.spa.model.shop.Shop;
import org.spa.model.staff.StaffHoliday;
import org.spa.model.user.User;
import org.spa.service.book.BlockService;
import org.spa.service.book.BookItemService;
import org.spa.service.shop.RoomService;
import org.spa.service.shop.ShopService;
import org.spa.service.user.UserService;
import org.spa.utils.DateUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.book.BlockVO;
import org.spa.vo.book.CellVO;
import org.spa.vo.book.ViewVO;
import org.spa.vo.common.DateTimeRangeVO;
import org.spa.vo.staff.ScheduleDayVO;
import org.spa.vo.staff.ScheduleTimeVO;
import org.spa.vo.staff.ScheduleWeekVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ivy 2016-7-6
 */
@Service
public class BlockServiceImpl extends BaseDaoHibernate<Block> implements BlockService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private BookItemService bookItemService;

    @Override
    public Long save(BlockVO blockVO) {
        Block block = transferToBlock(blockVO);
        return saveBlock(block);
    }

    @Override
    public Long saveBlock(Block block) {
        String res = checkBlock(block);
        if (StringUtils.isEmpty(res)) {
            // 检查是否超过年假
            res = checkHoliday(block);
            if (StringUtils.isNotBlank(res)) {
                throw new ResourceException(res);
            }
            return save(block);
        }

        throw new ResourceException(res);
    }

    private String checkHoliday(Block block) {
        String res = "";
        User staff = block.getUser();
        if (staff == null) {
            return res;
        }

        StaffHoliday staffHoliday = staff.getStaffHoliday();
        if (staffHoliday == null) {
            logger.warn("Staff:{} has not setup holiday!", staff.getUsername());
            return res;
        }
        if (CommonConstant.BLOCK_TYPE_ANNUAL_LEAVE.equals(block.getType())) {
            if (staffHoliday.getAnnualLeave() != null) {
                Double count = countAnnualLeaveDays(block.getUser(), block.getStartDate());
                Double currentDays = block.getBlockItems().stream().mapToDouble(BlockItem::getDays).sum();
                Double total = count + currentDays;
                // 这一次请假超过了规定的年假
                if (new Double(staffHoliday.getAnnualLeave()).compareTo(total) < 0) {
                    return "Staff:" + staff.getDisplayName() + " annual leave " + staffHoliday.getAnnualLeave() + " days is over " + total + " days!";
                }
            } else {
                logger.warn("Staff:{} has not setup annual leave!", staff.getUsername());
            }
        } else if (CommonConstant.BLOCK_TYPE_SICK_LEAVE.equals(block.getType())) {
            if (staffHoliday.getSickLeave() != null) {
                Double count = countSickLeaveDays(block.getUser(), block.getStartDate());
                Double currentDays = block.getBlockItems().stream().mapToDouble(BlockItem::getDays).sum();
                Double total = count + currentDays;
                // 这一次请假超过了规定的病假
                if (new Double(staffHoliday.getSickLeave()).compareTo(total) < 0) {
                    return "Staff:" + staff.getDisplayName() + " sick leave " + staffHoliday.getSickLeave() + " days is over " + total + " days!";
                }
            } else {
                logger.warn("Staff:{} has not setup sick leave!", staff.getUsername());
            }
        }
        return res;
    }

    public BlockItem generateRepeatBlockItem(Block block, DateTime currentDate, String status) {
        if (block.isNotRepeat()) {
            throw new RuntimeException("this method is only for repeat block!");
        }
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
        String dateString = currentDate.toString("yyyy-MM-dd ");
        DateTime currentDateStart = dtf.parseDateTime(dateString + block.getStartTime());
        DateTime currentDateEnd = dtf.parseDateTime(dateString + block.getEndTime());
        OpeningHours openingHours = block.getShop().getOpeningHour(currentDateEnd.toDate());
        currentDateStart = openingHours.getClosestStartTime(currentDateStart);
        currentDateEnd = openingHours.getClosestEndTime(currentDateEnd);
        BlockItem blockItem = new BlockItem();
        blockItem.setBlock(block);
        blockItem.setStartTime(currentDateStart.toDate());
        blockItem.setEndTime(currentDateEnd.toDate());
        blockItem.setDays(CommonConstant.getHoursRangeToDays(currentDateStart, currentDateEnd));
        blockItem.setPaidType(CommonConstant.getBlockPaidType(block.getType()));
        blockItem.setStatus(status);
        blockItem.setIsActive(true);
        blockItem.setCreated(now);
        blockItem.setCreatedBy(userName);
        blockItem.setLastUpdated(now);
        blockItem.setLastUpdatedBy(userName);
        block.getBlockItems().add(blockItem);
        return blockItem;
    }

    public void generateNotRepeatBlockItems(Block block) {
        if (!block.isNotRepeat()) {
            throw new RuntimeException("this method is only for not repeat block!");
        }
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        DateTime startDate = new DateTime(block.getStartDate());
        DateTime endDate = new DateTime(block.getEndDate());
        int betweenDays = DateUtil.betweenDays(startDate, endDate); // 相差多少天
        DateTime currentDateStart = startDate; // 一天的开始
        DateTime start;
        DateTime end;
//        System.out.println("betweenDays:" + betweenDays);
        for (int i = betweenDays; i >= 0; i--) {
            end = (i == 0) ? endDate : currentDateStart.millisOfDay().withMaximumValue().withMillisOfSecond(0);
            OpeningHours openingHours = block.getShop().getOpeningHour(currentDateStart.toDate());
            start = openingHours.getClosestStartTime(currentDateStart);
            end = openingHours.getClosestEndTime(end);
            BlockItem blockItem = new BlockItem();
            blockItem.setBlock(block);
            blockItem.setStartTime(start.toDate());
            blockItem.setEndTime(end.toDate());
            blockItem.setDays(CommonConstant.getHoursRangeToDays(start, end));
            blockItem.setPaidType(CommonConstant.getBlockPaidType(block.getType()));
            blockItem.setStatus(CommonConstant.BLOCK_ITEM_STATUS_PENDING);
            blockItem.setIsActive(true);
            blockItem.setCreated(now);
            blockItem.setCreatedBy(userName);
            blockItem.setLastUpdated(now);
            blockItem.setLastUpdatedBy(userName);
            block.getBlockItems().add(blockItem);
            currentDateStart = currentDateStart.plusDays(1).withTimeAtStartOfDay(); // 下一天
        }

    }

    @Override
    public Block transferToBlock(BlockVO blockVO) {
        Block block = new Block();
        Shop shop = shopService.get(blockVO.getShopId());
        block.setShop(shop);
        block.setType(blockVO.getType());
        block.setRemarks(blockVO.getRemarks());
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();
        if (blockVO.getTherapistId() != null) {
            block.setUser(userService.get(blockVO.getTherapistId()));
            // therapist view
            block.setRepeatType(blockVO.getRepeatType());
            if (CommonConstant.BLOCK_REPEAT_TYPE_NONE.equals(blockVO.getRepeatType())) {
                block.setStartDate(blockVO.getStartDateTime());
                block.setEndDate(blockVO.getEndDateTime());
                generateNotRepeatBlockItems(block);
            } else {
                block.setStartDate(new DateTime(blockVO.getRepeatStartDate()).withTimeAtStartOfDay().toDate());
                block.setEndDate(new DateTime(blockVO.getRepeatEndDate()).millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate());
                block.setStartTime(blockVO.getRepeatStartTime());
                block.setEndTime(blockVO.getRepeatEndTime());
                if (StringUtils.isBlank(blockVO.getCronExpression())) {
                    block.setCronExpression(generateCronExpression(blockVO));
                } else {
                    block.setCronExpression(blockVO.getCronExpression());
                }
            }
        } else if (blockVO.getRoomId() != null) {
            block.setRoom(roomService.get(blockVO.getRoomId()));
            block.setRepeatType(CommonConstant.BLOCK_REPEAT_TYPE_NONE);
            block.setStartDate(blockVO.getStartDateTime());
            block.setEndDate(blockVO.getEndDateTime());
            generateNotRepeatBlockItems(block);
        }
        block.setIsActive(true);
        block.setCreated(now);
        block.setCreatedBy(userName);
        block.setLastUpdated(now);
        block.setLastUpdatedBy(userName);
        return block;
    }

    /**
     * 创建cron expression 表达式
     *
     * @param blockVO
     * @return
     */
    private String generateCronExpression(BlockVO blockVO) {
        String repeatType = blockVO.getRepeatType();
        StringBuilder cronBuilder = new StringBuilder("* * * ");
        if (CommonConstant.BLOCK_REPEAT_TYPE_EVERY_DAY.equals(repeatType)) {
            if (blockVO.getDays() != null && blockVO.getDays().size() > 0) {
                cronBuilder.append(StringUtils.join(blockVO.getDays(), ",")).append(" * ?");
            } else {
                logger.error("repeat type:{} generate day cron expression parameter error!", repeatType);
            }
            return cronBuilder.toString();
        }
        if (CommonConstant.BLOCK_REPEAT_TYPE_EVERY_WEEK.equals(repeatType)) {
            if (blockVO.getWeeks() != null && blockVO.getWeeks().size() > 0) {
                cronBuilder.append(" ? * ").append(StringUtils.join(blockVO.getWeeks(), ","));
            } else {
                logger.error("repeat type:{} generate week cron expression parameter error!", repeatType);
            }
            return cronBuilder.toString();
        }
        if (CommonConstant.BLOCK_REPEAT_TYPE_EVERY_MONTH.equals(repeatType)) {
            if (blockVO.getDays() != null && blockVO.getDays().size() > 0) {
                cronBuilder.append(StringUtils.join(blockVO.getDays(), ",")).append(" ");
                if (blockVO.getMonths() != null && blockVO.getMonths().size() > 0) {
                    cronBuilder.append(StringUtils.join(blockVO.getMonths(), ",")).append(" ? ");
                } else {
                    logger.error("repeat type:{} generate month cron expression parameter error!", repeatType);
                }
            } else {
                logger.error("repeat type:{} generate day cron expression parameter error!", repeatType);
            }
            return cronBuilder.toString();
        }
        logger.error("unknown repeat type:{}!", repeatType);
        return "";
    }



    @Override
    public Long update(BlockVO blockVO) {
        Block block = get(blockVO.getId());
        if (block == null) {
            return 0L;
        }
        delete(block); // 删除之前的block
        getSession().flush();
        Long updateStatus = 0L;
        // 一次性block
        if (CommonConstant.BLOCK_REPEAT_TYPE_NONE.equals(block.getRepeatType())) {
            updateStatus = save(blockVO); // 生成新的block
        } else {
            Date now = new Date();
            String userName = WebThreadLocal.getUser().getUsername();
            DateTime currentTime = new DateTime(blockVO.getStartTimeStamp());
            // 找出特殊的blockItem
            BlockItem specialBlockItem = null;
            if (block.getBlockItems().size() > 0) {
                for (BlockItem item : block.getBlockItems()) {
                    if (DateUtil.sameDay(currentTime, new DateTime(item.getStartTime()))) {
                        specialBlockItem = item;
                        break;
                    }
                }
            }

            if (specialBlockItem != null) {
                String previousStartTime = block.getStartTime();
                String previousEndTime = block.getEndTime();
                block.setStartTime(blockVO.getRepeatStartTime());
                block.setEndTime(blockVO.getRepeatEndTime());
                block.getBlockItems().remove(specialBlockItem); // 刪除已經存在的block Item
                // 生成新的blockItem
                BlockItem blockItem = generateRepeatBlockItem(block, currentTime, CommonConstant.BLOCK_ITEM_STATUS_PENDING);
                block.setStartTime(previousStartTime);
                block.setEndTime(previousEndTime);
                block.getBlockItems().add(blockItem);
                block.setLastUpdated(now);
                block.setLastUpdatedBy(userName);
                updateStatus = saveBlock(block);
            } else {
                // 全部更新
                if (CommonConstant.BLOCK_UPDATE_TYPE_ALL.equals(blockVO.getUpdateType())) {
                    if (block.getStartTime().equals(blockVO.getRepeatStartTime()) && block.getEndTime().equals(blockVO.getRepeatEndTime())) {
                        logger.debug("Repeat time did not change!");
                        return 0L;
                    }
                    Date separateTime = currentTime.minusDays(1).millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate(); // 前一天的最后时刻
                    // 把 block repeat 一分为二
                    if (separateTime.after(block.getStartDate())) {
                        Block oldBlock = block.cloneBlock();
                        Block newBlock = block.cloneBlock(); // 克隆新的block
                        oldBlock.setEndDate(separateTime); // 这个block到此结束
                        oldBlock.setLastUpdated(now);
                        oldBlock.setLastUpdatedBy(userName);
                        Set<BlockItem> newItemSet = new HashSet<>();
                        Set<BlockItem> oldItemSet = new HashSet<>();
                        for (BlockItem blockItem : block.getBlockItems()) {
                            blockItem.setId(null); // 清除id
                            if (blockItem.getStartTime().before(separateTime)) {
                                oldItemSet.add(blockItem);
                                blockItem.setBlock(oldBlock);
                            } else {
                                newItemSet.add(blockItem);
                                blockItem.setBlock(newBlock);
                            }
                        }
                        oldBlock.setBlockItems(oldItemSet); // 之前的block item
                        saveBlock(oldBlock);
                        getSession().flush();
                        refresh(oldBlock);
                        // 生成新的 block
                        newBlock.setStartDate(currentTime.withTimeAtStartOfDay().toDate());
                        newBlock.setStartTime(blockVO.getRepeatStartTime());
                        newBlock.setEndTime(blockVO.getRepeatEndTime());
                        newBlock.setBlockItems(newItemSet);
                        updateStatus = saveBlock(newBlock);  // 生成新的block
                    } else {
                        // 只更新repeat time
                        block.setStartTime(blockVO.getRepeatStartTime());
                        block.setEndTime(blockVO.getRepeatEndTime());
                        block.setLastUpdated(now);
                        block.setLastUpdatedBy(userName);
                        String res = checkBlock(block);
                        if (StringUtils.isNotBlank(res)) {
                            throw new ResourceException(res); // 抛出异常, 使事务回滚
                        }
                        if (block.getBlockItems().size() > 0) {
                            for (BlockItem item : block.getBlockItems()) {
                                item.setId(null); // 清除id
                            }
                        }
                        updateStatus = saveBlock(block);
                    }
                } else {
                    // 生成新的blockItem, 保存原来的block repeat time数据，更新到当前的block time
                    String previousStartTime = block.getStartTime();
                    String previousEndTime = block.getEndTime();
                    block.setStartTime(blockVO.getRepeatStartTime());
                    block.setEndTime(blockVO.getRepeatEndTime());
                    // 生成新的blockItem
                    BlockItem blockItem = generateRepeatBlockItem(block, currentTime, CommonConstant.BLOCK_ITEM_STATUS_PENDING);
                    block.setStartTime(previousStartTime); // 恢复之前的block time
                    block.setEndTime(previousEndTime);
                    block.getBlockItems().add(blockItem);
                    block.setLastUpdated(now);
                    block.setLastUpdatedBy(userName);
                    updateStatus = saveBlock(block);
                }
            }
        }
        if (updateStatus == 0L) {
            throw ResourceException.TIME_BLOCK; // 抛出异常, 使事务回滚
        }
        return updateStatus;
    }

    @Override
    public void remove(BlockVO blockVO) {
        Block block = get(blockVO.getId());
        if (block == null) {
            return;
        }
        if (block.isNotRepeat()) {
            // 更新block
            block.setIsActive(false);
            block.setLastUpdated(new Date());
            block.setLastUpdatedBy(WebThreadLocal.getUser().getUsername());
            block.getBlockItems().stream().forEach(e->{
                e.setIsActive(false); // blockItem set to false;
                e.setStatus(CommonConstant.BLOCK_ITEM_STATUS_CANCEL);
            });
            saveOrUpdate(block);
        } else {
            // 处理重复的block
            Date now = new Date();
            String userName = WebThreadLocal.getUser().getUsername();
            if (CommonConstant.BLOCK_UPDATE_TYPE_ALL.equals(blockVO.getUpdateType())) {
                DateTime currentTime = new DateTime(blockVO.getStartTimeStamp());
                Date separateTime = currentTime.minusDays(1).millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate(); // 前一天的最后时刻
                if (block.getStartDate().after(separateTime)) {
                    block.setIsActive(false);
                } else {
                    block.setEndDate(separateTime);
                }
                block.setLastUpdated(now);
                block.setLastUpdatedBy(userName);
            } else {
                boolean findSpecialRecord = false;
//                System.out.println("block.getBlockItems().size():" + block.getBlockItems().size());
                if (block.getBlockItems().size() > 0) {
                    for (BlockItem blockItem : block.getBlockItems()) {
                        if (blockItem.getStartTime().getTime() == blockVO.getStartTimeStamp()) {
                            blockItem.setStatus(CommonConstant.BLOCK_ITEM_STATUS_CANCEL);
                            blockItem.setLastUpdated(now);
                            blockItem.setLastUpdatedBy(userName);
                            findSpecialRecord = true;
                            break;
                        }
                    }
                }
                if (!findSpecialRecord) {
                    generateRepeatBlockItem(block, new DateTime(blockVO.getStartTimeStamp()), CommonConstant.BLOCK_ITEM_STATUS_CANCEL);
                    block.setLastUpdated(now);
                    block.setLastUpdatedBy(userName);
                }
            }
            saveOrUpdate(block);
        }
    }

    /**
     * 判斷是否被block
     *
     * @param newBlock
     * @return
     */
    @Override
    public String checkBlock(Block newBlock) {
        String blockReason = "";
        if (newBlock.getUser() != null) {
            blockReason = checkTherapistBlock(newBlock);
        }
        if (StringUtils.isEmpty(blockReason) && newBlock.getRoom() != null) {
            blockReason = checkRoomBlock(newBlock);
        }
//        System.out.println("isBlock:" + blockReason);
        return blockReason;
    }

    /**
     * 检查block2的时间是否和block1的special block时间冲突
     *
     * @param block1
     * @param newBlock2
     * @return
     */
    private DateTimeRangeVO checkSpecialBlock(Block block1, Block newBlock2) {
        if (block1.getBlockItems().size() > 0) {
            for (BlockItem blockItem : block1.getBlockItems()) {
                if (blockItem.isCancel()) {
                    continue;
                }
                DateTimeRangeVO dateTimeRangeVO = checkBlock(newBlock2, blockItem.getStartTime(), blockItem.getEndTime());
                if (dateTimeRangeVO != null) {
                    return dateTimeRangeVO;
                }
            }
        }
        return null;
    }

    /**
     * 判斷 therapist 是否被block
     *
     * @param newBlock
     * @return
     */
    private String checkTherapistBlock(Block newBlock) {
        User therapist = newBlock.getUser();
        Shop shop = newBlock.getShop();
        long shopId = shop.getId();
        long staffId = therapist.getId();
        Date blockStartDate = newBlock.getStartDate();

        // bookItem 所有block信息
        List<BookItem> bookItemList = bookItemService.getStaffBookItemList(shopId, staffId, blockStartDate);
        // 先检查bookItem
        Interval newBlockInternal = new Interval(newBlock.getStartDate().getTime(), newBlock.getEndDate().getTime());
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHH:mm");
        for (BookItem bookItem : bookItemList) {
            DateTimeRangeVO dateTimeRangeVO = checkBlock(newBlock, bookItem.getAppointmentTime(), bookItem.getAppointmentEndTime());
            if (dateTimeRangeVO != null) {
                return String.format("Time block by booking from %s to %s!", dateTimeRangeVO.startToString(), dateTimeRangeVO.endToString());
            }
        }

        // 其他block的检查
        DateTime blockStartDateTime = new DateTime(blockStartDate);
        DateTime currentWeekStart = blockStartDateTime.minusDays(blockStartDateTime.getDayOfWeek() - 1).withTimeAtStartOfDay();
        // check block
        List<Block> blockList = getStaffBlockList(shopId, staffId, currentWeekStart.toDate());

        for (Block block : blockList) {
            if (CommonConstant.BLOCK_REPEAT_TYPE_NONE.equals(block.getRepeatType())) {
                DateTimeRangeVO dateTimeRangeVO = checkBlock(newBlock, block.getStartDate(), block.getEndDate());
                if (dateTimeRangeVO != null) {
                    return String.format("Time block by another block from %s to %s!", dateTimeRangeVO.startToString(), dateTimeRangeVO.endToString());
                }
            } else if (CommonConstant.BLOCK_REPEAT_TYPE_NONE.equals(newBlock.getRepeatType())) {
                DateTimeRangeVO dateTimeRangeVO = checkBlock(block, newBlock.getStartDate(), newBlock.getEndDate());
                if (dateTimeRangeVO != null) {
                    return String.format("Time block by another block from %s to %s!", dateTimeRangeVO.startToString(), dateTimeRangeVO.endToString());
                }
            } else {
                // block 和newBlock 都是repeat
                if (!newBlockInternal.overlaps(new Interval(block.getStartDate().getTime(), block.getEndDate().getTime()))) {
                    continue; // 两个block没有交集
                }
                // 检查特殊情况
                DateTimeRangeVO dateTimeRangeVO = checkSpecialBlock(block, newBlock);
                if (dateTimeRangeVO != null) {
                    return String.format("Time block by another block from %s to %s!", dateTimeRangeVO.startToString(), dateTimeRangeVO.endToString());
                }

                // newBlock 检查特殊情况
                dateTimeRangeVO = checkSpecialBlock(newBlock, block);
                if (dateTimeRangeVO != null) {
                    return String.format("Time block by another block from %s to %s!", dateTimeRangeVO.startToString(), dateTimeRangeVO.endToString());
                }

                // 获取第一个满足的日期两个block的日期
                DateTime currentTime = new DateTime(block.getStartDate());
                DateTime blockEndTime = new DateTime(block.getEndDate());
                DateTime newBlockEndTime = new DateTime(newBlock.getEndDate());
                DateTime findDate = null;
                try {
                    CronExpression cronExpression = new CronExpression(block.getCronExpression());
                    CronExpression newBlockCronExpression = new CronExpression(newBlock.getCronExpression());
                    Date date = currentTime.toDate();
                    while (currentTime.isBefore(blockEndTime) && currentTime.isBefore(newBlockEndTime)) {
                        if (cronExpression.isSatisfiedBy(date) && newBlockCronExpression.isSatisfiedBy(date)) {
                            findDate = currentTime;
                            break;
                        }
                        currentTime = currentTime.plusDays(1);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (findDate != null) {
                    String appointDateStr = findDate.toString("yyyyMMdd");
//                    System.out.println("appointDateStr + block.getStartTime():" + appointDateStr + block.getStartTime());
                    DateTime start = dateTimeFormatter.parseDateTime(appointDateStr + block.getStartTime());
                    DateTime end = dateTimeFormatter.parseDateTime(appointDateStr + block.getEndTime());

                    dateTimeRangeVO = checkBlock(newBlock, start.toDate(), end.toDate());
                    if (dateTimeRangeVO != null) {
                        return String.format("Time block by another block from %s to %s!", dateTimeRangeVO.startToString(), dateTimeRangeVO.endToString());
                    }
                }
            }
        }
        return "";
    }

    /**
     * start和end的时间，是否被block
     *
     * @param newBlock
     * @param start
     * @param end
     * @return
     */
    private DateTimeRangeVO checkBlock(Block newBlock, Date start, Date end) {
        Interval newBlockInternal = new Interval(newBlock.getStartDate().getTime(), newBlock.getEndDate().getTime());
        long startMillis = start.getTime();
        long endMillis = end.getTime();
        if (CommonConstant.BLOCK_REPEAT_TYPE_NONE.equals(newBlock.getRepeatType())) {
            if (newBlockInternal.overlaps(new Interval(startMillis, endMillis))) {
                return new DateTimeRangeVO(new DateTime(startMillis), new DateTime(endMillis));
            } else {
                return null;
            }
        }

        // repeat处理
        try {
            CronExpression cronExpression = new CronExpression(newBlock.getCronExpression());
            if (!cronExpression.isSatisfiedBy(start)) {
                return null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateTime startDateTime = new DateTime(start);
        if (newBlock.getBlockItems().size() > 0) {
            // 有特殊的处理
            for (BlockItem newBlockItem : newBlock.getBlockItems()) {
                if (newBlockItem.isCancel()) {
                    continue;
                }
                DateTime blockStartTime = new DateTime(newBlockItem.getStartTime());
                if (DateUtil.sameDay(startDateTime, blockStartTime)) {
                    if (new Interval(blockStartTime.getMillis(), newBlockItem.getEndTime().getTime()).overlaps(new Interval(startMillis, endMillis))) {
                        return new DateTimeRangeVO(new DateTime(startMillis), new DateTime(endMillis));
                    }
                }
            }
        }
        
//        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHH:mm");
//        String appointDateStr = startDateTime.toString("yyyyMMdd");
//        DateTime blockStartTime = dateTimeFormatter.parseDateTime(appointDateStr + newBlock.getStartTime());
//        DateTime blockEndTime = dateTimeFormatter.parseDateTime(appointDateStr + newBlock.getEndTime());
//
//        if (new Interval(blockStartTime, blockEndTime).overlaps(new Interval(startMillis, endMillis))) {
//            return new DateTimeRangeVO(new DateTime(startMillis), new DateTime(endMillis));
//        }
        return null;
    }

    public List<CellVO> getCellList(Shop shop, User therapist, Date searchDate) {
        DateTime dateTime = new DateTime(searchDate);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Block.class);
        detachedCriteria.add(Restrictions.eq("shop.id", shop.getId()));
        detachedCriteria.add(Restrictions.le("startDate", dateTime.millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate())); // block_start_time 要比 search_end_time 小， 排除下一天的block
        detachedCriteria.add(Restrictions.ge("endDate", dateTime.withTimeAtStartOfDay().toDate())); // block_end_time 比 search_start_time 大, 排除前一天的block
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.add(Restrictions.eq("user.id", therapist.getId()));
        List<Block> blockList = list(detachedCriteria);
        List<CellVO> list = new ArrayList<>();
        for (Block block : blockList) {
            list.addAll(block.transferToCell(searchDate));
        }
        return list;
    }

    public List<CellVO> getCellList(Shop shop, Room room, Date searchDate) {
        DateTime dateTime = new DateTime(searchDate);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Block.class);
        detachedCriteria.add(Restrictions.eq("shop.id", shop.getId()));
        detachedCriteria.add(Restrictions.le("startDate", dateTime.millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate())); // block_start_time 要比 search_end_time 小， 排除下一天的block
        detachedCriteria.add(Restrictions.ge("endDate", dateTime.withTimeAtStartOfDay().toDate())); // block_end_time 比 search_start_time 大, 排除前一天的block
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.add(Restrictions.eq("room.id", room.getId()));
        List<Block> blockList = list(detachedCriteria);
        List<CellVO> list = new ArrayList<>();
        for (Block block : blockList) {
            list.addAll(block.transferToCell(searchDate));
        }
        return list;
    }

    /**
     * for bookItem
     *
     * @param shop
     * @param therapist
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public boolean checkBlock(Shop shop, User therapist, Date startTime, Date endTime) {
        List<CellVO> cellVOList = getCellList(shop, therapist, startTime);
        return checkBlock(cellVOList, startTime, endTime);
    }

    /**
     * for bookItem
     *
     * @param shop
     * @param room
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public boolean checkBlock(Shop shop, Room room, Date startTime, Date endTime) {
        List<CellVO> cellVOList = getCellList(shop, room, startTime);
        return checkBlock(cellVOList, startTime, endTime);
    }

    private boolean checkBlock(List<CellVO> cellVOList, Date startTime, Date endTime) {
        Date time;
        for (CellVO cellVO : cellVOList) {
            time = cellVO.getTime().toDate();
            if (time.equals(startTime) || (time.after(startTime) && time.before(endTime))) {
                return true;
            }
        }
        return false;
    }

    /*public List<CellVO> getBlockList(Long shopId, Room room, Date searchDate) {
        DateTime dateTime = new DateTime(searchDate);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Block.class);
        detachedCriteria.add(Restrictions.eq("shop.id", shopId));
        detachedCriteria.add(Restrictions.le("startDate", dateTime.millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate())); // block_start_time 要比 search_end_time 小， 排除下一天的block
        detachedCriteria.add(Restrictions.ge("endDate", dateTime.withTimeAtStartOfDay().toDate())); // block_end_time 比 search_start_time 大, 排除前一天的block
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.add(Restrictions.eq("room.id", room.getId()));
        List<Block> blockList = list(detachedCriteria);
        List<CellVO> list = new ArrayList<>();
        for (Block block : blockList) {
            list.addAll(block.transferToCell(searchDate));
        }
        return list;
    }*/

    /**
     * 判断room 是否被block
     *
     * @param newBlock
     * @return
     */
    @Override
    public String checkRoomBlock(Block newBlock) {
        Shop shop = newBlock.getShop();
        Room room = newBlock.getRoom();
        Set<BlockItem> blockItemSet = newBlock.getBlockItems();
        if (!blockItemSet.isEmpty()) {
            for (BlockItem newBlockItem : blockItemSet) {
                if (newBlockItem.isCancel()) {
                    continue;
                }
                // 获取这个room当前blockItem的时间段内的所有已经block
                List<CellVO> list = getCellList(shop, room, newBlockItem.getStartTime());
                for (CellVO cellVO : list) {
                    // 预约时间已经在新的block时间区段内，表示被block
                    if (newBlockItem.isTimeBlock(cellVO.getTime().toDate())) {
                        return "Time has been block on:" + cellVO.getTime().toString("yyyy-MM-dd HH:mm");
                    }
                }
            }
        }

        if (bookItemService.checkBlock(room, newBlock.getStartDate(), newBlock.getEndDate(), new HashSet<>())) {
            String start = new DateTime(newBlock.getStartDate()).toString("yyyy-MM-dd HH:mm");
            String end = new DateTime(newBlock.getEndDate()).toString("yyyy-MM-dd HH:mm");
            return String.format("Time has been Block from %s to %s!", start, end);
        }
        return "";
    }

    @Override
    public List<CellVO> getCellList(ViewVO viewVO) {
        DateTime dateTime = new DateTime(viewVO.getAppointmentTimeStamp());
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Block.class);
        detachedCriteria.add(Restrictions.eq("shop.id", viewVO.getShopId()));
        detachedCriteria.add(Restrictions.le("startDate", dateTime.millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate())); // block_start_time 要比 search_end_time 小， 排除下一天的block
        detachedCriteria.add(Restrictions.ge("endDate", dateTime.withTimeAtStartOfDay().toDate())); // block_end_time 比 search_start_time 大, 排除前一天的block
        detachedCriteria.add(Restrictions.eq("isActive", true));
        if (CommonConstant.THERAPIST_VIEW.equals(viewVO.getViewType())) {
            detachedCriteria.add(Restrictions.isNotNull("user.id")); // 所有技师的block list
        } else {
            detachedCriteria.add(Restrictions.isNotNull("room.id")); // 所有room 的block list
        }
        List<Block> blockList = list(detachedCriteria);
        List<CellVO> cellVOList = new ArrayList<>();
//        System.out.println("blockList：" + blockList.size());
        for (Block block : blockList) {
            cellVOList.addAll(block.transferToCell(dateTime.toDate()));
        }
        return cellVOList;
    }

    public Double countBlockDays(User user, String blockType, Date currentDate) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BlockItem.class);
        detachedCriteria.createAlias("block", "block");
        detachedCriteria.add(Restrictions.eq("block.user.id", user.getId()));
        DateTime startDate = new DateTime(currentDate).withMonthOfYear(DateTimeConstants.JANUARY).withTimeAtStartOfDay();
        DateTime endDate = startDate.withMonthOfYear(DateTimeConstants.DECEMBER).withDayOfMonth(31).millisOfDay().withMaximumValue().withMillisOfSecond(0);
        detachedCriteria.add(Restrictions.ge("block.startDate", startDate.toDate()));
        detachedCriteria.add(Restrictions.le("block.endDate", endDate.toDate()));
        detachedCriteria.add(Restrictions.eq("block.isActive", true));
        detachedCriteria.add(Restrictions.eq("block.type", blockType));
        detachedCriteria.add(Restrictions.ne("status", CommonConstant.BLOCK_ITEM_STATUS_CANCEL));
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.setProjection(Projections.sum("days"));
        Double res = (Double) detachedCriteria.getExecutableCriteria(getSession()).uniqueResult();
//        System.out.println("res:" + res);
        return res == null ? 0D : res;
    }

    @Override
    public Double countAnnualLeaveDays(User user, Date currentDate) {
        return countBlockDays(user, CommonConstant.BLOCK_TYPE_ANNUAL_LEAVE, currentDate);
    }

    @Override
    public Double countSickLeaveDays(User user, Date currentDate) {
        return countBlockDays(user, CommonConstant.BLOCK_TYPE_SICK_LEAVE, currentDate);
    }

    @Override
    public Double countNoPaidLevaveDays(User user, String payrollMonth) {
    	
    	 DateTimeFormatter format = DateTimeFormat .forPattern("yyyy-MM");  
         DateTime current=DateTime.parse(payrollMonth,format);
         
    	 DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BlockItem.class);
         detachedCriteria.createAlias("block", "block");
         detachedCriteria.add(Restrictions.eq("block.user.id", user.getId()));
//         .withTimeAtStartOfDay()
         DateTime startDate = new DateTime(current).dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
         DateTime endDate = new DateTime(current).dayOfMonth().withMaximumValue().withSecondOfMinute(0);
//         DateTime endDate = startDate.withMonthOfYear(DateTimeConstants.DECEMBER).withDayOfMonth(31).millisOfDay().withMaximumValue().withMillisOfSecond(0);
         detachedCriteria.add(Restrictions.ge("block.startDate", startDate.toDate()));
         detachedCriteria.add(Restrictions.le("block.endDate", endDate.toDate()));
         detachedCriteria.add(Restrictions.eq("block.isActive", true));
         detachedCriteria.add(Restrictions.eq("block.type", CommonConstant.BLOCK_PAID_TYPE_NO_PAID));
         detachedCriteria.add(Restrictions.ne("status", CommonConstant.BLOCK_ITEM_STATUS_CANCEL));
         detachedCriteria.add(Restrictions.eq("isActive", true));
         detachedCriteria.setProjection(Projections.sum("days"));
         Double res = (Double) detachedCriteria.getExecutableCriteria(getSession()).uniqueResult();
//         System.out.println("res:" + res);
         return res == null ? 0D : res;
    }

    /**
     * 计算出这个员工的上班计划
     *
     *  @param shopId
     * @param staffId
     * @param startDate
     * @param endDate
     */
    @Override
    public ScheduleWeekVO listSchedule(Long shopId, Long staffId, Date startDate, Date endDate) {
        Date start = DateUtil.getFirstMinuts(startDate);
        
        DateTime end = new DateTime(endDate).millisOfDay().withMaximumValue().withMillisOfSecond(0);

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Block.class);
        detachedCriteria.add(Restrictions.eq("shop.id", shopId));
        detachedCriteria.add(Restrictions.eq("user.id", staffId));
        detachedCriteria.add(Restrictions.le("startDate", start));
        detachedCriteria.add(Restrictions.eq("isActive", true));
//        detachedCriteria.add(Restrictions.eq("neverExpired", true));
        detachedCriteria.addOrder(Order.asc("startDate")); // 最先的在最前面
        List<Block> blockList = list(detachedCriteria);
        
//        System.out.println("blockList size ::"+blockList.size());
        
        DateTime currentTime = new DateTime(start);
        ScheduleWeekVO scheduleWeekVO = new ScheduleWeekVO();
        scheduleWeekVO.setStartDate(start);
        scheduleWeekVO.setEndDate(end.toDate());
        List<ScheduleDayVO> scheduleDayVOList = scheduleWeekVO.getScheduleDayVOList();
//        System.out.println("start date ----"+start+"---end date---"+end.toDate());
        while (currentTime.isBefore(end) || currentTime.equals(end)) {
            Date currentDate = currentTime.toDate();
            ScheduleDayVO scheduleDayVO = new ScheduleDayVO();
            Map<String, ScheduleTimeVO> timeVOMap = scheduleDayVO.getScheduleTimeVOMap();
            scheduleDayVO.setDate(currentDate);
            scheduleDayVO.setName(currentTime.toString("EEEE", Locale.US));
            scheduleDayVO.setType(CommonConstant.WORKDAY); // 默认是workDay
//            System.out.println("11111111------ScheduleDayVO -----currentDate ----"+currentDate+"---currentTime---"+currentTime +"---type----"+scheduleDayVO.getType() +"----blockList size---"+blockList.size());
            for (Block block : blockList) {
                boolean satisfied = false;
                try {
                    satisfied = StringUtils.isNotBlank(block.getCronExpression()) && new CronExpression(block.getCronExpression()).isSatisfiedBy(currentDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                System.out.println("2222222-----satisfied ----"+satisfied+"---block.getType()--"+block.getType() +"----CronExpression----"+block.getCronExpression()+"---block id---"+block.getId());
                // 时间不满足，跳过
                if (!satisfied) {
                    continue;
                }
                // 满足
                boolean foundBookItem = false;
                if (block.getBlockItems().size() > 0) {
                    for (BlockItem blockItem : block.getBlockItems()) {
                        // 满足特殊情况
                    	if (new DateTime(blockItem.getStartTime()).withTimeAtStartOfDay().equals(currentTime)) {
                            foundBookItem = true;
                            if (!blockItem.getStatus().equals(CommonConstant.BLOCK_ITEM_STATUS_CANCEL)) {
                                // 按特殊的设置处理
                                if (CommonConstant.BLOCK_TYPE_DAY_OFF.equals(block.getType())) {
                                    scheduleDayVO.setType(CommonConstant.DAY_OFF);
                                } else if (CommonConstant.BLOCK_TYPE_BEFORE_WORK_DAY.equals(block.getType())) {
                                    ScheduleTimeVO scheduleTimeVO = timeVOMap.get(CommonConstant.WORK_HOURS);
                                    if (scheduleTimeVO == null) {
                                        scheduleTimeVO = new ScheduleTimeVO();
                                        timeVOMap.put(CommonConstant.WORK_HOURS, scheduleTimeVO);
                                    }
                                    scheduleTimeVO.setStartTime(new DateTime(blockItem.getEndTime()).toString("HH:mm")); // 设置工作日的上班时间
                                } else if (CommonConstant.BLOCK_TYPE_AFTER_WORK_DAY.equals(block.getType())) {
                                    ScheduleTimeVO scheduleTimeVO = timeVOMap.get(CommonConstant.WORK_HOURS);
                                    if (scheduleTimeVO == null) {
                                        scheduleTimeVO = new ScheduleTimeVO();
                                        timeVOMap.put(CommonConstant.WORK_HOURS, scheduleTimeVO);
                                    }
                                    scheduleTimeVO.setEndTime(new DateTime(blockItem.getStartTime()).toString("HH:mm")); // 设置工作日的下班时间
                                } else if (CommonConstant.BLOCK_TYPE_LUNCH.equals(block.getType())) {
                                    ScheduleTimeVO scheduleTimeVO = new ScheduleTimeVO();
                                    scheduleTimeVO.setStartTime(new DateTime(blockItem.getStartTime()).toString("HH:mm"));
                                    scheduleTimeVO.setEndTime(new DateTime(blockItem.getEndTime()).toString("HH:mm"));
                                    timeVOMap.put(CommonConstant.LUNCH, scheduleTimeVO);
                                }
                            }
                            break; // 满足一次，其他的不需要再处理
                        }
                    }
                }

                // 没有找到特殊的bookItem处理，则 正常处理
                if (!foundBookItem) {
//                	 System.out.println("2222222-----foundBookItem ----"+foundBookItem+"---block.getType()--"+block.getType() +"---block id---"+block.getId() +"--start date---"+block.getStartDate()+"---start time---"+block.getStartTime());
                    if (CommonConstant.BLOCK_TYPE_DAY_OFF.equals(block.getType())) {
                        scheduleDayVO.setType(CommonConstant.DAY_OFF);
                        continue;
                    }else{
                    	scheduleDayVO.setType(CommonConstant.WORKDAY);
                    	if (CommonConstant.BLOCK_TYPE_BEFORE_WORK_DAY.equals(block.getType())) {
                            ScheduleTimeVO scheduleTimeVO = timeVOMap.get(CommonConstant.WORK_HOURS);
                            if (scheduleTimeVO == null) {
                                scheduleTimeVO = new ScheduleTimeVO();
                                timeVOMap.put(CommonConstant.WORK_HOURS, scheduleTimeVO);
                            }
                            scheduleTimeVO.setStartTime(block.getEndTime()); // 设置工作日的上班时间
                        } else if (CommonConstant.BLOCK_TYPE_AFTER_WORK_DAY.equals(block.getType())) {
                            ScheduleTimeVO scheduleTimeVO = timeVOMap.get(CommonConstant.WORK_HOURS);
                            if (scheduleTimeVO == null) {
                                scheduleTimeVO = new ScheduleTimeVO();
                                timeVOMap.put(CommonConstant.WORK_HOURS, scheduleTimeVO);
                            }
                            scheduleTimeVO.setEndTime(block.getStartTime()); // 设置工作日的下班时间
                        } else if (CommonConstant.BLOCK_TYPE_LUNCH.equals(block.getType())) {
                            ScheduleTimeVO scheduleTimeVO = new ScheduleTimeVO();
                            scheduleTimeVO.setStartTime(block.getStartTime());
                            scheduleTimeVO.setEndTime(block.getEndTime());
                            timeVOMap.put(CommonConstant.LUNCH, scheduleTimeVO);
                        }
                    } 
                }
            }
            // 工作日没有找到scheduleTime,默认添加一个
            if (CommonConstant.WORKDAY.equals(scheduleDayVO.getType()) && scheduleDayVO.getScheduleTimeVOMap().size() == 0) {
                ScheduleTimeVO scheduleTimeVO = new ScheduleTimeVO();
                scheduleTimeVO.setName(CommonConstant.WORK_HOURS);
                Shop shop = shopService.get(shopId);
                OpeningHours openingHours = shop.getOpeningHour(currentDate);
                scheduleTimeVO.setStartTime(openingHours.getOpenTime());
                scheduleTimeVO.setEndTime(openingHours.getCloseTime());
                scheduleDayVO.getScheduleTimeVOMap().put(CommonConstant.WORK_HOURS, scheduleTimeVO);
            }
            scheduleDayVOList.add(scheduleDayVO);

            currentTime = currentTime.plusDays(1); // 下一天
        }
//        for(ScheduleDayVO vo :scheduleWeekVO.getScheduleDayVOList()){
//        	System.out.println(""+vo.getName()+vo.getType()+vo.getDate()+vo.getScheduleTimeVOMap());
//        }
        return scheduleWeekVO;
    }

    public List<Block> getStaffHolidayBlockList(Long shopId, Long staffId, Date startDate) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Block.class);
        detachedCriteria.add(Restrictions.eq("shop.id", shopId));
        detachedCriteria.add(Restrictions.eq("user.id", staffId));
        detachedCriteria.add(Restrictions.ge("startDate", new DateTime(startDate).withTimeAtStartOfDay().toDate()));
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.add(Restrictions.in("type", CommonConstant.BLOCK_HOLIDAY_LIST));
        return list(detachedCriteria);
    }

    public List<Block> getStaffBlockList(Long shopId, Long staffId, Date startDate) {
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Block.class);
        detachedCriteria.add(Restrictions.eq("shop.id", shopId));
        detachedCriteria.add(Restrictions.eq("user.id", staffId));
        detachedCriteria.add(Restrictions.ge("startDate", new DateTime(startDate).withTimeAtStartOfDay().toDate()));
        detachedCriteria.add(Restrictions.eq("isActive", true));
        return list(detachedCriteria);
    }

    /**
     * 检查schedule是否和现有的booking 和 block发生冲突
     *
     * @param scheduleWeekVO
     * @return
     */
    public String checkSchedule(ScheduleWeekVO scheduleWeekVO) {
        Long shopId = scheduleWeekVO.getShopId();
        Long staffId = scheduleWeekVO.getStaffId();
        Date startDate = scheduleWeekVO.getStartDate();
        List<BookItem> bookItemList = bookItemService.getStaffBookItemList(shopId, staffId, startDate);

        Map<String, ScheduleDayVO> scheduleDayVOMap = scheduleWeekVO.getScheduleDayVOList().stream().collect(Collectors.toMap(ScheduleDayVO::getName, p -> p));

        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMddHH:mm");

        // 先检查bookItem
        for (BookItem bookItem : bookItemList) {
            DateTime appointmentStart = new DateTime(bookItem.getAppointmentTime());
            ScheduleDayVO scheduleDayVO = scheduleDayVOMap.get(appointmentStart.toString("EEEE", Locale.US));
            if (CommonConstant.WORKDAY.equals(scheduleDayVO.getType())) {
                DateTime appointmentEnd = new DateTime(bookItem.getAppointmentEndTime());
                ScheduleTimeVO scheduleTimeVO = scheduleDayVO.getScheduleTimeVOMap().get(CommonConstant.WORK_HOURS);
                String currentDayStr = appointmentStart.toString("yyyyMMdd");
                DateTime workdayStart = dateTimeFormatter.parseDateTime(currentDayStr + scheduleTimeVO.getStartTime());
                DateTime workdayEnd = dateTimeFormatter.parseDateTime(currentDayStr + scheduleTimeVO.getEndTime());
                //boolean betweenWorkHours = (appointmentStart.isEqual(workdayStart) || appointmentStart.isAfter(workdayStart)) && (appointmentEnd.isEqual(workdayEnd) || appointmentEnd.isBefore(workdayEnd));
                // 超出工作的时间范围
                if (!new Interval(workdayStart, workdayEnd).contains(new Interval(appointmentStart, appointmentEnd))) {
                    return "Cannot set work hours because has booking from " + appointmentStart.toString("yyyy-MM-dd HH:mm") + " to " + appointmentEnd.toString("yyyy-MM-dd HH:mm");
                }
            } else if (CommonConstant.DAY_OFF.equals(scheduleDayVO.getType())) {
                return "Cannot set day off on " + scheduleDayVO.getName() + " because has booking on " + appointmentStart.toString("yyyy-MM-dd HH:mm") + "!";
            }
        }

        // check block
        List<Block> blockList = getStaffHolidayBlockList(shopId, staffId, startDate);

        logger.debug("blockList.size():{}", blockList.size());
        for (Block block : blockList) {
            // 一次性处理
            if (CommonConstant.BLOCK_REPEAT_TYPE_NONE.equals(block.getRepeatType())) {
                DateTime blockStart = new DateTime(block.getStartDate());
                ScheduleDayVO scheduleDayVO = scheduleDayVOMap.get(blockStart.toString("EEEE", Locale.US));
                if (CommonConstant.WORKDAY.equals(scheduleDayVO.getType())) {
                    DateTime blockEnd = new DateTime(block.getEndDate());
                    ScheduleTimeVO scheduleTimeVO = scheduleDayVO.getScheduleTimeVOMap().get(CommonConstant.WORK_HOURS);
                    String currentDayStr = blockStart.toString("yyyyMMdd");
                    DateTime workdayStart = dateTimeFormatter.parseDateTime(currentDayStr + scheduleTimeVO.getStartTime());
                    DateTime workdayEnd = dateTimeFormatter.parseDateTime(currentDayStr + scheduleTimeVO.getEndTime());
                    //boolean betweenWorkHours = ((blockStart.isEqual(workdayStart) || blockStart.isAfter(workdayStart))) && (blockEnd.isEqual(workdayEnd) || blockEnd.isBefore(workdayEnd));
                    if (!new Interval(workdayStart, workdayEnd).contains(new Interval(blockStart, blockEnd))) {
                        // 超出工作的时间范围
                        return "Cannot set work hours because has block from " + blockStart.toString("yyyy-MM-dd HH:mm") + " to " + blockEnd.toString("yyyy-MM-dd HH:mm");
                    }
                } else if (CommonConstant.DAY_OFF.equals(scheduleDayVO.getType())) {
                    return "Cannot set day off on " + scheduleDayVO.getName() + " because has block on " + blockStart.toString("yyyy-MM-dd HH:mm") + "!";
                }
            } else {
                DateTime blockRepeatStartDate = new DateTime(block.getStartDate());
                DateTime currentBlockTime = new DateTime(blockRepeatStartDate);
                DateTime blockRepeatEndDate = new DateTime(block.getEndDate());
                try {
                    while (currentBlockTime.isBefore(blockRepeatEndDate)) {
                        // 满足
                        if (StringUtils.isNotBlank(block.getCronExpression()) && new CronExpression(block.getCronExpression()).isSatisfiedBy(currentBlockTime.toDate())) {
                            // 特殊日期处理
                            if (block.getBlockItems().size() > 0) {
                                for (BlockItem blockItem : block.getBlockItems()) {
                                    // 找到同一天
                                    if (new DateTime(blockItem.getStartTime()).withTimeAtStartOfDay().isEqual(currentBlockTime)) {
                                        ScheduleDayVO scheduleDayVO = scheduleDayVOMap.get(currentBlockTime.toString("EEEE", Locale.US));
                                        if (!CommonConstant.BLOCK_ITEM_STATUS_CANCEL.equals(blockItem.getStatus())) {
                                            DateTime blockStart = new DateTime(blockItem.getStartTime());
                                            DateTime blockEnd = new DateTime(blockItem.getEndTime());
                                            // 工作日检查
                                            if (CommonConstant.WORKDAY.equals(scheduleDayVO.getType())) {
                                                ScheduleTimeVO scheduleTimeVO = scheduleDayVO.getScheduleTimeVOMap().get(CommonConstant.WORK_HOURS);
                                                String currentDayStr = blockStart.toString("yyyyMMdd");
                                                DateTime workdayStart = dateTimeFormatter.parseDateTime(currentDayStr + scheduleTimeVO.getStartTime());
                                                DateTime workdayEnd = dateTimeFormatter.parseDateTime(currentDayStr + scheduleTimeVO.getEndTime());
                                                //boolean betweenWorkHours = (blockStart.isEqual(workdayStart) || blockStart.isAfter(workdayStart)) && (blockEnd.isEqual(workdayEnd) || blockEnd.isBefore(workdayEnd));
                                                if (!new Interval(workdayStart, workdayEnd).contains(new Interval(blockStart, blockEnd))) {
                                                    // 超出工作的时间范围
                                                    return "Cannot set work hours because has block from " + blockStart.toString("yyyy-MM-dd HH:mm") + " to " + blockEnd.toString("yyyy-MM-dd HH:mm");
                                                }
                                            } else if (CommonConstant.DAY_OFF.equals(scheduleDayVO.getType())) {
                                                return "Cannot set day off on " + scheduleDayVO.getName() + " because has block on " + blockStart.toString("yyyy-MM-dd HH:mm") + "!";
                                            }

                                        }
                                        break;
                                    }
                                }
                            } else {
                                ScheduleDayVO scheduleDayVO = scheduleDayVOMap.get(currentBlockTime.toString("EEEE", Locale.US));
                                // 没有特殊的记录
                                String currentDayStr = currentBlockTime.toString("yyyyMMdd");
                                DateTime blockStart = dateTimeFormatter.parseDateTime(currentDayStr + block.getStartTime());
                                DateTime blockEnd = dateTimeFormatter.parseDateTime(currentDayStr + block.getEndTime());
                                // 工作日检查
                                if (CommonConstant.WORKDAY.equals(scheduleDayVO.getType())) {
                                    ScheduleTimeVO scheduleTimeVO = scheduleDayVO.getScheduleTimeVOMap().get(CommonConstant.WORK_HOURS);
                                    DateTime workdayStart = dateTimeFormatter.parseDateTime(currentDayStr + scheduleTimeVO.getStartTime());
                                    DateTime workdayEnd = dateTimeFormatter.parseDateTime(currentDayStr + scheduleTimeVO.getEndTime());
                                    //boolean betweenWorkHours = (blockStart.isEqual(workdayStart) || blockStart.isAfter(workdayStart)) && (blockEnd.isEqual(workdayEnd) || blockEnd.isBefore(workdayEnd));
                                    if (!new Interval(workdayStart, workdayEnd).contains(new Interval(blockStart, blockEnd))) { // 超出工作的时间范围
                                        return "Cannot set work hours because has block from " + blockStart.toString("yyyy-MM-dd HH:mm") + " to " + blockEnd.toString("yyyy-MM-dd HH:mm");
                                    }
                                } else if (CommonConstant.DAY_OFF.equals(scheduleDayVO.getType())) {
                                    return "Cannot set day off on " + scheduleDayVO.getName() + " because has block on " + blockStart.toString("yyyy-MM-dd HH:mm") + "!";
                                }
                            }
                        }
                        currentBlockTime = currentBlockTime.plusDays(1); // 下一天
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * 保存staff一周的工作计划
     *
     * @param scheduleWeekVO
     * @return
     */
    @Override
    public String saveSchedule(ScheduleWeekVO scheduleWeekVO) {
        String res = checkSchedule(scheduleWeekVO);
        if (StringUtils.isNotBlank(res)) {
            return res;
        }
        System.out.println("start to saveBlock schedule");
        Long shopId = scheduleWeekVO.getShopId();
        Long staffId = scheduleWeekVO.getStaffId();
        Date startDate = scheduleWeekVO.getStartDate();

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Block.class);
        detachedCriteria.add(Restrictions.eq("shop.id", shopId));
        detachedCriteria.add(Restrictions.eq("user.id", staffId));
        detachedCriteria.add(Restrictions.eq("isActive", true));
        detachedCriteria.add(Restrictions.in("type", CommonConstant.BLOCK_WORK_DAY_LIST));
        //Conjunction conjunction = Restrictions.conjunction();
        Disjunction disjunction = Restrictions.disjunction();
        //conjunction.add(Restrictions.le("endDate", new DateTime(scheduleWeekVO.getEndDate()).millisOfDay().withMaximumValue().toDate()));
        disjunction.add(Restrictions.ge("startDate", new DateTime(startDate).withTimeAtStartOfDay().toDate()));
        disjunction.add(Restrictions.eq("neverExpired", true));
        detachedCriteria.add(disjunction);

        List<Block> blockList = list(detachedCriteria);

        System.out.println("current Block List:"+ blockList.size());

        User staff = userService.get(scheduleWeekVO.getStaffId());
        Shop shop = shopService.get(scheduleWeekVO.getShopId());
        Date now = new Date();
        String userName = WebThreadLocal.getUser().getUsername();

        Map<String, List<Block>> blockMap = new HashMap<>();
        for (Block block : blockList) {
            List<Block> dayBlockList = blockMap.get(block.getCronExpression());
            if (dayBlockList == null) {
                dayBlockList = new ArrayList<>();
                blockMap.put(block.getCronExpression(), dayBlockList);
            }
            dayBlockList.add(block);
        }

        // 开始处理保存
        for (ScheduleDayVO dayVO : scheduleWeekVO.getScheduleDayVOList()) {
            String cronExpression = "* * * ? * " + new DateTime(dayVO.getDate()).toString("EEE", Locale.US).toUpperCase();
            List<Block> dayBlockList = blockMap.get(cronExpression);
            List<Block> beforeList = new ArrayList<>();
            List<Block> lunchList = new ArrayList<>();
            List<Block> afterList = new ArrayList<>();
            List<Block> dayOffList = new ArrayList<>();

            if(dayBlockList != null) {
                for (Block block : dayBlockList) {
                    if (CommonConstant.BLOCK_TYPE_BEFORE_WORK_DAY.equals(block.getType())) {
                        beforeList.add(block);
                    } else if (CommonConstant.BLOCK_TYPE_LUNCH.equals(block.getType())) {
                        lunchList.add(block);
                    } else if (CommonConstant.BLOCK_TYPE_AFTER_WORK_DAY.equals(block.getType())) {
                        afterList.add(block);
                    } else if (CommonConstant.BLOCK_TYPE_DAY_OFF.equals(block.getType())) {
                        dayOffList.add(block);
                    }
                }
            }

            if (CommonConstant.WORKDAY.equals(dayVO.getType())) {
                Block dayOffBlock = getUpdateBlock(dayOffList, scheduleWeekVO, now, userName);
                if (dayOffBlock != null) {
                    delete(dayOffBlock);
                }
                // 上班前的block
                ScheduleTimeVO workHours = dayVO.getScheduleTimeVOMap().get(CommonConstant.WORK_HOURS);
                Block beforeWorkDayBlock = getUpdateBlock(beforeList, scheduleWeekVO, now, userName);
                if (beforeWorkDayBlock == null) {
                    beforeWorkDayBlock = new Block();
                }
                beforeWorkDayBlock.getBlockItems().clear(); // 清除之前的设置
                beforeWorkDayBlock.setStartDate(scheduleWeekVO.getStartDate());
                beforeWorkDayBlock.setEndDate(CommonConstant.BLOCK_NEVER_STOP_DATE);
                beforeWorkDayBlock.setUser(staff);
                beforeWorkDayBlock.setShop(shop);
                beforeWorkDayBlock.setStartTime("00:00");
                beforeWorkDayBlock.setEndTime(workHours.getStartTime());
                beforeWorkDayBlock.setRepeatType(CommonConstant.BLOCK_REPEAT_TYPE_EVERY_WEEK);
                beforeWorkDayBlock.setType(CommonConstant.BLOCK_TYPE_BEFORE_WORK_DAY);
                beforeWorkDayBlock.setNeverExpired(true);
                beforeWorkDayBlock.setIsActive(true);
                beforeWorkDayBlock.setCreated(now);
                beforeWorkDayBlock.setCreatedBy(userName);
                beforeWorkDayBlock.setLastUpdated(now);
                beforeWorkDayBlock.setLastUpdatedBy(userName);
                beforeWorkDayBlock.setCronExpression(cronExpression);
                saveOrUpdate(beforeWorkDayBlock);

                // lunch time block
                ScheduleTimeVO lunchTimeVO = dayVO.getScheduleTimeVOMap().get(CommonConstant.LUNCH);

                Block lunchTimeBlock = getUpdateBlock(lunchList, scheduleWeekVO, now, userName);
                if (StringUtils.isNotBlank(lunchTimeVO.getStartTime()) && StringUtils.isNotBlank(lunchTimeVO.getEndTime())) {
                    if (lunchTimeBlock == null) {
                        lunchTimeBlock = new Block();
                    }
                    lunchTimeBlock.getBlockItems().clear(); // 清除之前的设置
                    lunchTimeBlock.setStartDate(scheduleWeekVO.getStartDate());
                    lunchTimeBlock.setEndDate(CommonConstant.BLOCK_NEVER_STOP_DATE);
                    lunchTimeBlock.setUser(staff);
                    lunchTimeBlock.setShop(shop);
                    lunchTimeBlock.setStartTime(lunchTimeVO.getStartTime());
                    lunchTimeBlock.setEndTime(lunchTimeVO.getEndTime());
                    lunchTimeBlock.setRepeatType(CommonConstant.BLOCK_REPEAT_TYPE_EVERY_WEEK);
                    lunchTimeBlock.setType(CommonConstant.BLOCK_TYPE_LUNCH);
                    lunchTimeBlock.setNeverExpired(true);
                    lunchTimeBlock.setIsActive(true);
                    lunchTimeBlock.setCreated(now);
                    lunchTimeBlock.setCreatedBy(userName);
                    lunchTimeBlock.setLastUpdated(now);
                    lunchTimeBlock.setLastUpdatedBy(userName);
                    lunchTimeBlock.setCronExpression(cronExpression);
                    saveOrUpdate(lunchTimeBlock);
                } else if (lunchTimeBlock != null) {
                    delete(lunchTimeBlock); // 删除当前的设置
                }

                // 下班后的block
                Block afterWorkDayBlock = getUpdateBlock(afterList, scheduleWeekVO, now, userName);
                if (afterWorkDayBlock == null) {
                    afterWorkDayBlock = new Block();
                }
                afterWorkDayBlock.getBlockItems().clear(); // 清除之前的设置
                afterWorkDayBlock.setStartDate(scheduleWeekVO.getStartDate());
                afterWorkDayBlock.setEndDate(CommonConstant.BLOCK_NEVER_STOP_DATE);
                afterWorkDayBlock.setUser(staff);
                afterWorkDayBlock.setShop(shop);
                afterWorkDayBlock.setStartTime(workHours.getEndTime());
                afterWorkDayBlock.setEndTime("23:59");
                afterWorkDayBlock.setRepeatType(CommonConstant.BLOCK_REPEAT_TYPE_EVERY_WEEK);
                afterWorkDayBlock.setType(CommonConstant.BLOCK_TYPE_AFTER_WORK_DAY);
                afterWorkDayBlock.setNeverExpired(true);
                afterWorkDayBlock.setIsActive(true);
                afterWorkDayBlock.setCreated(now);
                afterWorkDayBlock.setCreatedBy(userName);
                afterWorkDayBlock.setLastUpdated(now);
                afterWorkDayBlock.setLastUpdatedBy(userName);
                afterWorkDayBlock.setCronExpression(cronExpression);
                saveOrUpdate(afterWorkDayBlock);

            } else if (CommonConstant.DAY_OFF.equals(dayVO.getType())) {

                // 找到之前的block并且删除
                Block beforeWorkDayBlock = getUpdateBlock(beforeList, scheduleWeekVO, now, userName);
                if (beforeWorkDayBlock != null) {
                    delete(beforeWorkDayBlock);
                }
                Block lunchTimeBlock = getUpdateBlock(lunchList, scheduleWeekVO, now, userName);
                if (lunchTimeBlock != null) {
                    delete(lunchTimeBlock);
                }
                Block afterWorkDayBlock = getUpdateBlock(afterList, scheduleWeekVO, now, userName);
                if (afterWorkDayBlock != null) {
                    delete(afterWorkDayBlock);
                }

                Block dayOffBlock = getUpdateBlock(dayOffList, scheduleWeekVO, now, userName);
                if (dayOffBlock == null) {
                    dayOffBlock = new Block();
                }
                dayOffBlock.getBlockItems().clear(); // 清除之前的设置
                dayOffBlock.setStartDate(scheduleWeekVO.getStartDate());
                dayOffBlock.setEndDate(CommonConstant.BLOCK_NEVER_STOP_DATE);
                dayOffBlock.setUser(staff);
                dayOffBlock.setShop(shop);
                dayOffBlock.setStartTime("00:00");
                dayOffBlock.setEndTime("23:59");
                dayOffBlock.setRepeatType(CommonConstant.BLOCK_REPEAT_TYPE_EVERY_WEEK);
                dayOffBlock.setType(CommonConstant.BLOCK_TYPE_DAY_OFF);
                dayOffBlock.setNeverExpired(true);
                dayOffBlock.setIsActive(true);
                dayOffBlock.setCreated(now);
                dayOffBlock.setCreatedBy(userName);
                dayOffBlock.setLastUpdated(now);
                dayOffBlock.setLastUpdatedBy(userName);
                dayOffBlock.setCronExpression(cronExpression);
                saveOrUpdate(dayOffBlock);
            } else {
                logger.error("Parameter error!");
            }
        }
        return "";
    }

    private Block getUpdateBlock(List<Block> blockList, ScheduleWeekVO scheduleWeekVO, Date now, String userName) {
        Block updateBlock = null;
        if (blockList.size() > 0) {
            for (Block block : blockList) {
                // 找到需要修改的block
                if (block.getNeverExpired() != null && block.getNeverExpired()) {
                    // 需要拆分
                    if (block.getStartDate().before(scheduleWeekVO.getStartDate())) {
                        // 当前block截取到scheduleWeekVO的前一天
                        System.out.println("previousDate:" + new DateTime(scheduleWeekVO.getStartDate()).minusDays(1).millisOfDay().withMaximumValue().withMillisOfSecond(0));
                        block.setEndDate(new DateTime(scheduleWeekVO.getStartDate()).minusDays(1).millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate());
                        block.setNeverExpired(false);
                        block.setLastUpdated(now);
                        block.setLastUpdatedBy(userName);
                        saveOrUpdate(block);
                    } else {
                        updateBlock = block;
                    }
                } else {
                    delete(block);  // 其他的都要删除
                }
            }
        }
        return updateBlock;
    }

	@Override
	public List<Block> checkRoomBlock(Long shopId, Long roomId, Date startTime, Date endTime) {
        DateTime dateTime = new DateTime(startTime);
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Block.class);
        detachedCriteria.createAlias("room", "r");
        detachedCriteria.add(Restrictions.eq("shop.id", shopId));
        detachedCriteria.add(Restrictions.le("startDate", dateTime.millisOfDay().withMaximumValue().withMillisOfSecond(0).toDate())); // block_start_time 要比 search_end_time 小， 排除下一天的block
        detachedCriteria.add(Restrictions.ge("endDate", dateTime.withTimeAtStartOfDay().toDate())); // block_end_time 比 search_start_time 大, 排除前一天的block
        detachedCriteria.add(Restrictions.eq("isActive", true));
        if(Objects.nonNull(roomId)) {
        	detachedCriteria.add(Restrictions.eq("r.id", roomId));
        }
        List<Block> blockList = list(detachedCriteria);
        return blockList;
	}

}
