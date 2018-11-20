package com.spa.controller.shop;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.spa.model.shop.Room;
import org.spa.utils.CollectionUtils;
import org.spa.utils.I18nUtil;
import org.spa.utils.WebThreadLocal;
import org.spa.vo.ajax.AjaxForm;
import org.spa.vo.ajax.AjaxFormHelper;
import org.spa.vo.page.Page;
import org.spa.vo.shop.RoomAddVO;
import org.spa.vo.shop.RoomEditVO;
import org.spa.vo.shop.RoomListVO;
import org.spa.vo.shop.RoomVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/03/28.
 */
@Controller
@RequestMapping("room")
public class RoomController extends BaseController {

    @RequestMapping("toView")
    public String management(Model model) {
        RoomListVO roomListVO = new RoomListVO();
        model.addAttribute("roomListVO",roomListVO);
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        return "room/roomManagement";
    }

    @RequestMapping("list")
    public String list(Model model, RoomListVO roomListVO) {

        String name = roomListVO.getName();
        String isActive = roomListVO.getIsActive();
        Long shopId=roomListVO.getShopId();
        
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Room.class);

        if (StringUtils.isNotBlank(name)) {
            detachedCriteria.add(Restrictions.like("name", name, MatchMode.ANYWHERE));
        }
        if (shopId !=null) {
        	detachedCriteria.add(Restrictions.eq("shop.id", shopId));
        }else{
        	detachedCriteria.add(Restrictions.in("shop.id", WebThreadLocal.getHomeShopIdsByLoginStaff()));
        }
        if (StringUtils.isNotBlank(isActive)) {
            detachedCriteria.add(Restrictions.eq("isActive", Boolean.parseBoolean(isActive)));
        }
        //page start
        Page<Room> roomPage = roomService.list(detachedCriteria, roomListVO.getPageNumber(), roomListVO.getPageSize());
        CollectionUtils.sort(roomPage.getList(),"sort",true);
        model.addAttribute("page", roomPage);

        try {
            model.addAttribute("curQueryString", URLEncoder.encode(JSONObject.toJSONString(roomListVO), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //page end
        return "room/roomList";
    }

    @RequestMapping("toAdd")
    public String toAdd(RoomAddVO roomAddVO, Model model) {
        model.addAttribute("shopList", shopService.getListByCompany(WebThreadLocal.getCompany().getId(), false));
        return "room/roomAdd";
    }

    @RequestMapping("add")
    @ResponseBody
    public AjaxForm add(@Valid RoomAddVO roomAddVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            roomService.save(roomAddVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.add.successfully"));
        }
    }

    @RequestMapping("toEdit")
    public String toEdit(RoomEditVO roomEditVO, Model model) {
        Room room = roomService.get(roomEditVO.getId());
        if(room != null) {
            BeanUtils.copyProperties(room, roomEditVO);
        }
        model.addAttribute("roomEditVO", roomEditVO);
        model.addAttribute("room", room);
        return "room/roomEdit";
    }

    @RequestMapping("edit")
    @ResponseBody
    public AjaxForm edit(@Valid RoomEditVO roomEditVO, BindingResult result) {
        if (result.hasErrors()) {
            return AjaxFormHelper.error(result);
        } else {
            roomService.update(roomEditVO);
            return AjaxFormHelper.success(I18nUtil.getMessageKey("label.edit.successfully"));
        }
    }

    @RequestMapping("remove")
    @ResponseBody
    public AjaxForm remove(RoomEditVO roomEditVO) {
    	Room room = roomService.get(roomEditVO.getId());
        if (room != null) {
        	try {
        		room.setIsActive(false);
        		roomService.save(room);
			} catch (Exception e) {
				e.printStackTrace();
				return  AjaxFormHelper.error("Errors happened!");
			}
        }else{
        	return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.remove.successfully"));
    }
    
    @RequestMapping("enabled")
    @ResponseBody
    public AjaxForm enabled(RoomEditVO roomEditVO) {
    	Room room = roomService.get(roomEditVO.getId());
        if (room != null) {
        	try {
        		
        		room.setIsActive(true);
        		roomService.save(room);
			} catch (Exception e) {
				e.printStackTrace();
				return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.remove.failed"));
			}
        }else{
        	return  AjaxFormHelper.error(I18nUtil.getMessageKey("label.errors.cannot.find.id"));
        }
        return AjaxFormHelper.success(I18nUtil.getMessageKey("label.remove.successfully"));
    }
    
    @RequestMapping("getAvailableRoomList")
    @ResponseBody
    public List<RoomVO> getAvailableRoomList(Long productOptionId, Long shopId) {
        List<Room> roomList = roomService.getAvailableRoomList(productOptionId, shopId);
        List<RoomVO> roomVOList = new ArrayList<>();
        for (Room room : roomList) {
            RoomVO roomVO = new RoomVO();
            BeanUtils.copyProperties(room, roomVO);
            roomVOList.add(roomVO);
        }
        return roomVOList;
    }


    @RequestMapping("roomSelectList")
    public String roomSelectList(Long shopId, Long companyId, Boolean showAll, Long roomId, Model model) {

        DetachedCriteria dc = DetachedCriteria.forClass(Room.class);
        dc.add(Restrictions.eq("isActive", true));
        dc.add(Restrictions.eq("company.id", companyId));

        if(shopId !=null && shopId.longValue() > 0){
            dc.add(Restrictions.eq("shop.id",shopId));
        }
        List<Room> roomList = roomService.list(dc);

        model.addAttribute("roomList",roomList);
        model.addAttribute("showAll", showAll);
        model.addAttribute("roomId", roomId);

        return "room/roomSelectList";
    }
}

