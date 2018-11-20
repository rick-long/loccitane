<%@ page import="org.spa.model.book.BookItem"%>
<%@ page import="com.spa.constant.CommonConstant" %>
<%@ page import="org.joda.time.DateTime" %>
<%@ page import="org.joda.time.Minutes" %>
<%@ page import="org.spa.model.user.User" %>
<%@ page import="org.spa.vo.book.CellVO" %>
<%@ page import="org.spa.vo.book.ViewVO" %>
<%@ page import="org.spa.model.shop.Shop" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.spa.utils.WebThreadLocal" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<c:set var="timeCellWidth" value="70"/>
<c:set var="cellWidth" value="150"/>
<c:set var="cellHeight" value="25"/>
<style>
a,body,div,html,input,li,p,span,strong,textarea,ul,td,tr{margin:0;padding:0;border:0;outline:0;font-size:100%;vertical-align:baseline;background:0 0;font-family:SegoeUI;box-sizing:border-box;-moz-box-sizing:border-box;-webkit-box-sizing:border-box}
	.status-pending {
	    background-color: orangered !important;
	}
	.status-confirm {
	    background-color: #5d7b93 !important;
	}

	.status-checkin_service {
	    background-color: purple !important;
	}

	.status-complete {
	    background-color: darkgreen !important;
	}

	.status-block {
	    background: darkgrey;
	}

	.status-confirm {
   		background-color: #5d7b93 !important;
	}
    .calendar .calendar-content .cell {
        width: 150px;
        height: 25px;
    }
    .calendar .calendar-content .therapist {
        width: 150px;
        height: 25px;
        line-height: 25px;
        border-bottom: 2px dashed #aaa;
    }
		.calendar .calendar-content .time {
	    background: #e3e3e3;
	    text-align: center;
	    border-bottom: 1px dotted #aaa;
	    border-right: 1px dashed #aaa;
	/* 	border-top: 1px dotted #aaa; */
	    position: absolute;
	    z-index: 3;
		height: 25px;
		width:70px;
	}
	
    .calendar .calendar-content .box {
        width: 140px;
        height: 25px;
    }
.header_therapist{
	    z-index: 9;
	    position: -webkit-sticky;
	    position: sticky;
	    top: 0;
	}
	.header_therapist div:first-child{
	    background: #e3e3e3;
	    border-bottom: 1px solid #aaa;
	    position: sticky;
	    top: 0;
	    left: 0;
	    color: transparent;
	    font-size: 0;
	    width: 70px;
	    height: 22px;
	    display: block;
	    z-index: 99;
	}
	.calendar {
	    margin:0;
	    width: 100%;
/* 	    float: left; */
	    position: relative;
	}
	.calendar .calendar-right {
	    width:198px;
	    min-height: 650px;
	    margin: 0 auto;
	    padding: 0;
	    margin: 0 0 0 15px;
	    float: left;
	}
		{
	    background: #e3e3e3;
	    text-align: center;
	    border-bottom: 1px dotted #aaa;
	    position: absolute;
	    z-index: 3;
	}
	.calendar .calendar-content {
	    float: none;
 		overflow:auto;  
	}
.calendar .calendar-content .time-line {
    border: 0;
    float: left;
    height: 1px;
    position: absolute;
    top: 20px;
    width: 0px;
    z-index: 3;
}
.calendar .calendar-content .therapist {
    background: #fff;
    border-bottom: 1px solid #aaa;
    position: absolute;
    text-align: center;
    border-left: 0;
    border-right: 0;
}
.box {
    position: absolute;
    font-size: 10px;
    background: #c1c1c1;
    z-index: 2;
    color: white;
    padding: 4px;
    overflow: hidden;
    width: 120px;
}

.book-item-box,.time-box {

}
.calendar .calendar-content .cell.odd {
    background: #fff;
    border-bottom: dotted #aaa 1px;
	border-right: dashed #aaa 1px;
	border-left: 0;
    overflow: visible;
    z-index: 1;
}
	.calendar .calendar-content .cell {
	    background: #fff;
	    border-bottom: dotted #aaa 1px;
	    border-left: dotted #aaa 1px;
	    position: absolute;
	    overflow: visible;
	    z-index: 1;
	    text-align: center;
	}

	.calendar .calendar-content .cell.even {
	    background: #f8f5f5;
	    border-left: dashed #aaa 0px;
		border-right: dashed #aaa 1px;
	    position: absolute;
	    overflow: visible;
	    z-index: 1;
	}
	.legend {
		position: fixed;
	    color: #fff;
	    line-height: 20px;
	    height: 20px;
	    text-align: left;
	    margin: 20px 10px 0 0;
	}
	
	.legend ul{
	    display: inline;
	    margin: 0;
	    padding:0;
	
	}
	.legend ul li {
	    display: inline;
	    padding: 1px 5px;
	}
	.calendar .calendar-left {
	    background-color: rgba(255,255,255,0.9);
	}
	.calendar-left .legend {
	    display: inline-block;
	    color: #fff;
	    line-height: 19px;
	    height: 19px;
	    text-align: center;
	    margin: 10px 10px 0 0;
	}
</style>

<div id="bookTimeTherapistView" class="calendar">
    <div class="calendar-left">
        <div class="calendar-content">
        
    		    <%
		  	   		   List<User> therapistList = (List<User>) request.getAttribute("therapistList");
		   		       pageContext.setAttribute("maxCols", therapistList.size());
		   		       Shop shopInfo = (Shop)request.getAttribute("shopInfo");
		   		       pageContext.setAttribute("shopInfo", shopInfo);
		                ViewVO viewVO = (ViewVO) request.getAttribute("viewVO");
		                DateTime startDateTime = viewVO.getStartTime();
		                DateTime endDateTime = viewVO.getEndTime();
		                request.setAttribute("isTherapist", WebThreadLocal.getUser().getSysRoles().contains(CommonConstant.STAFF_ROLE_REF_THERAPIST));
		                boolean isToday = new DateTime().withTimeAtStartOfDay().isEqual(startDateTime.withTimeAtStartOfDay());
		                request.setAttribute("isToday", isToday);
		                pageContext.setAttribute("startDateTime", startDateTime);
		                pageContext.setAttribute("endDateTime", endDateTime);
		                pageContext.setAttribute("startDate", startDateTime.toString("yyyy-MM-dd"));
  	  		    %>
      		<div class="calendar-content-inner">
            <div class="time-line"></div>
            <div class="time" style="background:#fff;border:none;left: 0;width:${timeCellWidth + cellWidth*maxCols}px;top:0;text-align:left;padding-left:${timeCellWidth }px;">
                  <div >Shop : &nbsp; ${ shopInfo.getName()} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; DateTime  :  &nbsp;${ startDate}</div>
            </div>
            <div class="time" style="top: ${cellHeight}px;"></div>
            <div class="header_therapist">
                <div style="height:${cellHeight}px;width:${timeCellWidth}px"><spring:message code="label.time"/> </div>
            <c:forEach items="${therapistList}" var="item" varStatus="status">
	                <c:choose>
	                    <c:when test="${therapistListSize ne status.count}">
	                          <div class="therapist" data-therapist-id="${item.id}" style="left: ${timeCellWidth + status.index * cellWidth}px;top: ${cellHeight}px;float:left;">${item.displayName}</div>
	                    </c:when>
	                    <c:otherwise>
	                         <div class="therapist" data-therapist-id="${item.id}"
	                     style="left: ${timeCellWidth + status.index * cellWidth}px;top:${cellHeight}px;">${item.displayName}</div>
	                    </c:otherwise>
	                </c:choose>
	                
	          </c:forEach>
            </div>
            <%
                DateTime changeToolTipDirectionTime = startDateTime.plusHours(4); // 前四小时的bookItem, tooltip向下提示
                pageContext.setAttribute("changeToolTipDirectionTime", changeToolTipDirectionTime);
                int timeUnit = CommonConstant.TIME_UNIT;
                pageContext.setAttribute("timeUnit", timeUnit);
                pageContext.setAttribute("timeMillisUnit", timeUnit * 60 * 1000); // 毫秒单位
                int maxRow = Minutes.minutesBetween(startDateTime, endDateTime).getMinutes() / timeUnit;
                int maxCol = therapistList.size();
                pageContext.setAttribute("maxRow", maxRow);
                
                DateTime currentTime = new DateTime(startDateTime.getMillis());
                DateTime nowTime = new DateTime();
                pageContext.setAttribute("nowHour", nowTime.getHourOfDay());
                pageContext.setAttribute("nowMin", nowTime.getMinuteOfHour());
                CellVO cellVO;
                User therapist;
                // 最外层循环
                for (int rowIndex = 0; rowIndex <= maxRow; rowIndex++, currentTime = currentTime.plusMinutes(timeUnit)) {
                    pageContext.setAttribute("currentTime", currentTime);
                    pageContext.setAttribute("rowIndex", rowIndex);
                    if(rowIndex==maxRow){
                    	%>
                    	 <div class="time"
                             style="border:none;left: 0; top: ${cellHeight * (rowIndex + 2)}px;width:${timeCellWidth+ cellWidth*maxCols}px">
					        <div class="legend" style="text-align: right;left: 40%">
					            <ul style="text-align: right;">
					            	<li class="status-pending">
					            	    <a href="javascript:;" onclick="printVoucher();" title='<spring:message code="label.button.print"/>'>
						                   <i id="printTherapistViewId" class="glyphicon glyphicon-print"></i>
						                </a>
					            	</li>
					                <li class="status-pending"><spring:message code="label.book.status.PENDING"/></li>
					                <li class="status-confirm"><spring:message code="label.book.status.CONFIRM"/></li>
					                <li class="status-checkin_service"><spring:message code="label.book.status.CHECKIN_SERVICE"/></li>
					                <li class="status-complete" style="float: right;"><spring:message code="label.book.status.COMPLETE"/></li>
					                <li class="status-block" style="float: right;"><spring:message code="label.book.status.blocked"/></li>
					            </ul>
					        </div>
                         </div>
                    	<% 
                    }else{
                        %>
	                        <div class="time"
	                             style="left: 0; top: ${cellHeight * (rowIndex + 2)}px;"><fmt:formatDate value="${currentTime.toDate()}" pattern="hh:mm a"/>
	                         </div>
	                        <%
			                    }
			                Map<String, CellVO> cellVOMap = (Map<String, CellVO>) request.getAttribute("cellVOMap");
			                for (int colIndex = 0; colIndex < maxCol; colIndex++) {
			                    therapist = therapistList.get(colIndex);
			                    pageContext.setAttribute("therapist", therapist);
			                    pageContext.setAttribute("colIndex", colIndex);
			                    cellVO = cellVOMap.get(currentTime.getMillis() + "," + therapist.getId());
			                    pageContext.setAttribute("cellVO", cellVO);
			                    if(rowIndex < maxRow){
			
			            %>
			            <c:set var="left" value="${timeCellWidth + colIndex * cellWidth}"/>
			            <c:set var="top" value="${cellHeight * (rowIndex + 2)}"/>
			            <div class='cell ${currentTime.getHourOfDay() % 2 == 0 ? "even" : "odd"} ${cellVO ne null ? "block" : ""}'
			                 data-book-item-id="${cellVO.bookItem.id}"
			                 data-block-id="${cellVO.block.id}"
			                 data-time="${currentTime.millis}"
			                 data-therapist="${therapist.id}"
			                 data-therapist-name="${therapist.displayName}"
			                 data-position-left="${left}"
			                 data-position-top="${top}"
			                 style="left: ${left}px; top: ${top}px; border-left:0;<c:if test="${currentTime.getHourOfDay() == nowHour && currentTime.getMinuteOfHour() <= nowMin && nowMin< (currentTime.getMinuteOfHour() + timeUnit) }">border-top:1px solid #ff0000;</c:if> border-bottom:1px dashed #aaa;">
			            </div>
		            <c:choose>
		                <c:when test="${ cellVO ne null && cellVO.parent eq null && cellVO.bookItem ne null}">
		              		<c:set var="currentBookItem" value="${cellVO.bookItem}"/>
		                    <c:set var="currentBook" value="${cellVO.bookItem.book}"/>
		                    <c:set var="currentMember" value="${currentBook.user}"/>
		                    <c:set var="currentRoom" value="${currentBookItem.room}"/>
		                    <c:set var="processTime" value="${currentBookItem.productOption.processTime}"/>
		                    <c:set var="height" value="${cellHeight * cellVO.size}"/>
		                    <c:set var="status_css">
		                    	<c:choose>
			                    	<c:when test="${cellVO.bookItem.isDoubleBooking }">
			                    		status-double
		                            </c:when>
			                    	<c:otherwise>
			                    		status-${currentBookItem.status.toLowerCase()}
			                    	</c:otherwise>
		                    	</c:choose>
		                    	
		                    </c:set>
		                    <div class="book-item-box box <c:if test="${currentBookItem.bookItemTherapists.size() > 1}">box-mulit-therapist</c:if> ${status_css}"
		                         data-toggle="tooltip"
		                         data-placement="${currentTime.isAfter(changeToolTipDirectionTime) ? 'left' : 'right'}"
		                         data-book-id="${currentBookItem.book.id}"
		                         data-book-item-id="${currentBookItem.id}"
		                         data-status="${currentBookItem.status}"
		                         data-time="${currentTime.millis}"
		                         data-book-item="${currentBookItem.id}"
		                         data-children="${cellVO.children ne null ? cellVO.children.size() : 0}"
		                         data-size="${cellVO.size}"
		                         data-therapist="${therapist.id}"
		                         data-on-request="${cellVO.onRequest}"
		                         data-therapist-list="${currentBookItem.therapistIds}"
		                         data-room="${currentRoom.id}"
		                         data-position-left="${left}"
		                         data-position-top="${top}"
		                         data-height="${height}"
		                         data-double-booking="${currentBookItem.isDoubleBooking}"
		                         data-product-option-id="${currentBookItem.productOption.id}"
		                         data-member-id="${currentMember.id}"
		                         style="left: ${left}px; top: ${top}px;height: ${height}px;min-height: ${height}px;width:${cellWidth-9}px;<c:if test="${currentTime.getHourOfDay() == nowHour && currentTime.getMinuteOfHour() < nowMin && nowMin< (currentTime.getMinuteOfHour() + timeUnit) }">border-bottom:1px dashed #ff0000;</c:if>">
		                        
		                        <c:if test="${!cellVO.bookItem.isDoubleBooking }">
		                            <div>
		                                <div>${currentMember.fullName}</div>
		                                <div>${currentBookItem.productName}</div>
		                                <div>${currentMember.mobilePhone}</div>
		                                <div>${currentRoom.name}</div>
		                                <div>
		                                <c:if test="${currentMember.gender eq 'MALE'}">
		                                    <span class="glyphicon glyphicon-user"></span>
		                                </c:if>
		                                <c:if test="${currentBook.pregnancy !=null && currentBook.pregnancy}">
		                                      <span class="glyphicon glyphicon-asterisk" style="color: yellow"></span><!-- PREGNANCY -->
		                                </c:if>
		                                <c:if test="${currentMember.checkWhetherHasExpiringPackage}">
		                                    <span class="glyphicon glyphicon-asterisk" style="color: red"></span>
		                                </c:if>
		                                </div>
		                            </div>
			                        <c:if test="${cellVO.onRequest}">
			                            <div class="on-request">
			                                <span class="glyphicon glyphicon-registration-mark"></span>
			                            </div>
			                        </c:if>
		                        </c:if>
		                        <c:if test="${cellVO.bookItem.isDoubleBooking }">
		                        
		                        <div class="double_booking_children  " >

                        <%
                        	BookItem bookItem =(BookItem)pageContext.getAttribute("currentBookItem");
                        	List<BookItem> childrenOfDoubleBooking = bookItem.getChildrenOfDoubleBooking();
                        	for(BookItem childBI : childrenOfDoubleBooking){
                        		Boolean onRequest =childBI.getRequestedOfTherapist(therapist.getId());
                        		pageContext.setAttribute("childBI", childBI);
                        		pageContext.setAttribute("onRequest", onRequest);
                        		%>
		                        <c:if test="${onRequest}">
		                            <div class="on-request">
		                                <span class="glyphicon glyphicon-registration-mark"></span>
		                            </div>
		                        </c:if>
                        		<%

                        	}
                        %>
                        </div>
                        </c:if>
                    </div>
                </c:when>
               
                <c:when test="${ cellVO ne null && cellVO.parent eq null && cellVO.block ne null}">
                    <c:set var="height" value="${cellHeight * cellVO.size}"/>
                    <div class="box time-box"
                         data-block-id="${cellVO.block.id}"
                         data-repeat-type="${cellVO.block.repeatType}"
                         data-time="${currentTime.millis}"
                         data-children="${cellVO.children.size()}"
                         data-size="${cellVO.size}"
                         data-therapist="${therapist.id}"
                         data-position-left="${left}"
                         data-position-top="${top}"
                         data-height="${height}"
                         style="left: ${left}px; top: ${top}px;height: ${height - 7}px;">
                        <div style="padding-top:1px;padding-bottom:1px;padding-left:1px;padding-right:1px;">
                        	<spring:message code="label.block.type.${cellVO.block.type}"/></br>
                        	<spring:message code="label.remarks"/>: ${cellVO.block.remarks}
                        </div>
                    </div>
                </c:when>
            </c:choose>
            <%
                    }
                }
                }
            %>
        </div>
      	</div>
    </div>
</div>
