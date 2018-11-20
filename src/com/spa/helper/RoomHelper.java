package com.spa.helper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spa.model.shop.Room;


/**
 * room 组合类
 *
 * @author Ivy on 2016-6-21
 */
public class RoomHelper {

    private static final Logger logger = LoggerFactory.getLogger(RoomHelper.class);

    private static List<List<Room>> combine(Room[] a, int count) {
        if (null == a || a.length == 0)
            return null;
        Room[] b = new Room[a.length];
        return getCombination(a, 0, b, 0, count);
    }

    private static List<List<Room>> getCombination(Room[] a, int begin, Room b[], int index, int count) {
        List<List<Room>> result = new ArrayList<>();
        if (index >= a.length) {
            return new ArrayList<>();
        }
        for (int i = begin; i < a.length; i++) {
            b[index] = a[i];
            List<Room> list = new ArrayList<>();
            // 排除只有一个数的组合情况
            if (index >= count) {
                return new ArrayList<>();
            }
            for (int j = 0; j < index + 1; j++) {
                list.add(b[j]);
            }
            result.add(list);
            result.addAll(getCombination(a, i + 1, b, index + 1, count));
        }
        return result;
    }

    public static List<Room> assignRoom(List<Room> roomList, int guestAmount) {
        return assignRoom(roomList, guestAmount, 0);
    }
    
    /**
     * 分配房间
     * 用于获取当前容量到浮动容量范围的房间，
     * 比如获取容量为2的房间的话，
     * 同时会获取 3, 4, 5 这些房间,
     * 优先获取2, 2没有，则获取3 ，以此类推.
     * 如果都没有，则获取组合的房间，比如 1+1(两个容量为1的房间)
     * 如果都没有，则返回更高容量的房间(比如 6, 7, 8 容量的房间)
     * 如果更高的容量的房间都找不到，则房间获取失败
     *
     * @param roomList          可用的room集合
     * @param guestAmount       客人数量
     * @param roomCapacityRange 房间容量浮动大小
     * @return
     */
    public static List<Room> assignRoom(List<Room> roomList, int guestAmount, int roomCapacityRange) {
        List<Room> result = new ArrayList<>();
        if (roomList == null || roomList.size() == 0) {
            return result;
        }
        Room[] rooms = new Room[roomList.size()];
        // 判断是否有相等容量的room, 如果找到，则返回。
        for (int i = roomList.size() - 1; i >= 0; i--) {
            Room room = roomList.get(i);
            if (guestAmount == room.getCapacity()) {
                result.add(room);
                logger.debug("assigned room: {}", result);
                return result;
            }
            rooms[i] = room;
        }

        //  获取房间所有的组合结果
        List<List<Room>> combineResult = combine(rooms, guestAmount);
        // 按容量升序排列, 如果容量相同，则房间数最小的优先
        Collections.sort(combineResult, (roomList1, roomList2) -> {
            int res = roomList1.stream().mapToInt(Room::getCapacity).sum() - roomList2.stream().mapToInt(Room::getCapacity).sum();
            return res == 0 ? (roomList1.size() - roomList2.size()) : res;
        });
        logger.debug("Room combine result:");
        for (List<Room> rooms2 : combineResult) {
            logger.debug(rooms2 + ":" + rooms2.stream().mapToInt(Room::getCapacity).sum());
        }

        if (roomCapacityRange > 0) {
            List<List<Room>> candidateRoomList = new ArrayList<>(); // 候选的房间集合
            List<Integer> capacityList = new ArrayList<>();
            for (int i = 0; i <= roomCapacityRange; i++) {
                capacityList.add(guestAmount + i);
            }
            for (List<Room> rooms2 : combineResult) {
                int currentCapacity = rooms2.stream().mapToInt(Room::getCapacity).sum();
                if (capacityList.contains(currentCapacity)) {
                    candidateRoomList.add(rooms2);
                    continue;
                }
                // 一个都找不到，必须获取一个容量更大的room
                if (candidateRoomList.isEmpty() && currentCapacity > guestAmount) {
                    candidateRoomList.add(rooms2);
                }
            }
            // 从候选的集合中返回最好的房间
            if (candidateRoomList.isEmpty()) {
                return result;
            } else {
                // 对候选集合排序
                Collections.sort(candidateRoomList, (roomList1, roomList2) -> {
                    int res = roomList1.size() - roomList2.size();
                    // 如果房间个数相同，返回容量最小的
                    return res == 0 ? (roomList1.stream().mapToInt(Room::getCapacity).sum() - roomList2.stream().mapToInt(Room::getCapacity).sum()) : res;
                });
                return candidateRoomList.get(0); // 返回第一个
            }
        } else {
            // 获取最接近的room组合
            for (List<Room> rooms2 : combineResult) {
                if (rooms2.stream().mapToInt(Room::getCapacity).sum() >= guestAmount) {
                    result = rooms2;
                    break;
                }
            }
        }
        logger.debug("assigned room: {}", result);
        // sort room by display order
        Collections.sort(result, (e1, e2) -> e1.getSort().compareTo(e2.getSort()));
        return result;
    }

    //返回一个房间
    public static Room getAssignRoom(List<Room> roomList, int guestAmount, int roomCapacityRange) {
        List<Room> rooms = assignRoom(roomList, guestAmount, roomCapacityRange);
        if (rooms.size() == 1) {
            return rooms.get(0);
        }
        return null;
    }
}
