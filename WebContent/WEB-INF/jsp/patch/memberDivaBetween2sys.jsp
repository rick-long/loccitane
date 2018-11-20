<%@page import="java.sql.DriverManager"%>
<%@page import="org.spa.vo.patch.PatchDivaNumberVO"%>
<%@page import="org.spa.model.user.User"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="org.spa.serviceImpl.user.UserServiceImpl"%>
<%@page import="org.spa.service.user.UserService"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="javax.naming.InitialContext"%>
<%@page import="javax.naming.Context"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.*"%>
<%@page import="org.springframework.context.support.ClassPathXmlApplicationContext"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>

<%
	
	String[] locations = new String[] {"spring-hibernate.xml"};
	ApplicationContext ctx = new ClassPathXmlApplicationContext(locations);

	UserService userService = ctx.getBean(UserServiceImpl.class);
	
	/* String fromDate=(String)request.getAttribute("fromDate");	//yyyy-MM-dd
	String endDate=(String)request.getAttribute("endDate");	//yyyy-MM-dd */
	
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
    	   conn.close();  
		}  
  	}  

	Long companyId=2L;
	String hqlSql=
	"SELECT au.email,au.username,au.user_id "+
	" FROM APP_USER au "+ 
	" WHERE au.enabled = 1"+
	" AND au.company_id =?" +
	" AND au.account_type =?" +
	" AND au.created >=?" +
	" AND au.created <=?";
	
	try {
		PreparedStatement ps = conn.prepareStatement(hqlSql);
		ps.setLong(1, companyId);
		ps.setString(2, "CLIENT");
		ps.setString(3, "2017-01-05");
		ps.setString(4, "2017-01-06");
		
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
	
	if(oldMemberList !=null && oldMemberList.size()>0 ){
		out.println(" patch data size :"+oldMemberList +", member's diva number:::");
		for(PatchDivaNumberVO oldUser : oldMemberList){
			User newUser = userService.get("email", oldUser.getEmail());
			newUser.setUsername(oldUser.getUsername());
			newUser.setOldId(oldUser.getUserId());
			/* userService.saveOrUpdate(newUser); */
			out.println(oldUser.getUsername()+"<br/>");
		}
	}else{
		out.println("Empty data patched <br/>");
	}
	
%>