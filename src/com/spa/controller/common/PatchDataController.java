package com.spa.controller.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.spa.model.user.User;
import org.spa.vo.patch.PatchDivaNumberVO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spa.controller.BaseController;

/**
 * Created by Ivy on 2016/08/23.
 */
@Controller
@RequestMapping("patch")
public class PatchDataController extends BaseController{
	
	@RequestMapping("/memberDivaBetween2sys")
	public String patchMemberDivaBetween2sys(HttpServletRequest request,String fromDate,String endDate,Model model) {

		System.out.println("fromDate------"+fromDate);
		System.out.println("endDate------"+endDate);
		
		
		List<PatchDivaNumberVO> oldMemberList=new ArrayList<PatchDivaNumberVO>();
		
		String url = "jdbc:mysql://113.28.55.150:3306/rapporto" ;    
	    String username = "rapporto" ;   
	    String password = "beauty4skindeep!" ; 
	    
		Connection conn = null;
	    try {  
	    	conn=DriverManager.getConnection(url, username,password);  
	        
		}catch (Exception e) {
			e.printStackTrace();
			
		}finally{  
	       	if (null != conn) {  
	       		try {
	    		   conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}  
			}  
	  	}  

	    System.out.println("conn------"+conn);
	    
		Long companyId=2L;
		String hqlSql=
		"SELECT au.email,au.username,au.user_id "+
		" FROM APP_USER au "+ 
		" WHERE au.enabled = 1"+
		" AND au.company_id =?" +
		" AND au.created >=?" +
		" AND au.created <=?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(hqlSql);
			ps.setLong(1, companyId);
			ps.setString(2, fromDate);
			ps.setString(3, endDate);
			
			ResultSet rs = ps.executeQuery();
		
			while ( rs.next() ){
				PatchDivaNumberVO vo = new PatchDivaNumberVO();
				vo.setEmail((String)rs.getString("email"));
				vo.setUsername((String)rs.getString("username"));
				vo.setUserId((Long)rs.getLong("user_id"));
				
				oldMemberList.add(vo);
			}
			rs.close();
			ps.close();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("oldMemberList------"+oldMemberList.size());
		
		StringBuffer patchData=new StringBuffer();
		if(oldMemberList !=null && oldMemberList.size()>0 ){
			model.addAttribute("oldMemberList", oldMemberList.size());
			for(PatchDivaNumberVO oldUser : oldMemberList){
				User newUser = userService.get("email", oldUser.getEmail());
				newUser.setUsername(oldUser.getUsername());
				newUser.setOldId(oldUser.getUserId());
				/* userService.saveOrUpdate(newUser); */
				patchData.append(oldUser.getUsername()).append(",");
			}
		}else{
			model.addAttribute("oldMemberList", 0);
		}
		model.addAttribute("patchData", patchData);
		
		return "patch/memberDivaBetween2sys";
	}
}
