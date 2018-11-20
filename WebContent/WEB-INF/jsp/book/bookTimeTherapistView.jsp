<%@page import="org.spa.model.book.BookItem"%>
<%@ page import="com.spa.constant.CommonConstant" %>
<%@ page import="org.joda.time.DateTime" %>
<%@ page import="org.joda.time.Minutes" %>
<%@ page import="org.spa.model.user.User" %>
<%@ page import="org.spa.vo.book.CellVO" %>
<%@ page import="org.spa.vo.book.ViewVO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.spa.utils.WebThreadLocal" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<c:set var="timeCellWidth" value="70"/>
<c:set var="cellWidth" value="150"/>
<c:set var="cellHeight" value="25"/>
<style>
.time-line{background-color: rgb(255, 126, 126);}
    .hide_title_pending{
        background-color: orangered !important;
        border-top:1px solid #000;
    }
    .hide_title_confirmed{
        background-color: #5d7b93 !important;
        border-top:1px solid #000;
    }
    .hide_title_confirm{
        background-color: #5d7b93 !important;
        border-top:1px solid #000;
    }
    .hide_title_checkin_service{
        background-color: purple !important;
        border-top:1px solid #000;
    }
    .hide_title_complete{
        background-color: darkgreen !important;
        border-top:1px solid #000;

    }
    .hide_title_block{
        background: darkgrey;
    }

    .calendar .calendar-content .cell {
        width: ${cellWidth}px;
        height: ${cellHeight}px;
    }

    .calendar .calendar-content .time {
        width: ${timeCellWidth}px;
        height: ${cellHeight}px;
        line-height: ${cellHeight}px;

    }

    .calendar .calendar-content .therapist {
        width: ${cellWidth}px;
        height: ${cellHeight}px;
        line-height: ${cellHeight}px;
    }

    .calendar .calendar-content .box {
        width: ${cellWidth}px;
        height: ${cellHeight}px;
      /*  background: yellow;*/
    }
	.tooltip-inner{
		background-color:#5d7b93
	}
	.tooltip.right .tooltip-arrow{
		border-right-color:#5d7b93
	}
    #shopTab{
        margin-top:22px !important;
    }

</style>
<h3 class='text-h3-white'>
    <spring:message code="label.therapist.view"/>
</h3>
<%-- shop的开门时间 --%>
<div id="bookTimeTherapistView" class="calendar">
    <div class="calendar-left">
        <div class="calendar-head">
            <div class="hide-calendar-btn">
                <a id="toggleCalendar" class="btn btn-default" href="javascript:;" role="button">
                    <i class="glyphicon glyphicon-resize-horizontal"></i>
                </a>
            </div>
            <div class="hide-calendar-btn">
                   <a data-permission="book:bookTimeTherapistView" href="javascript:;" class="btn btn-primary form-page" style="background-color: lightgrey;" onclick="printTherapistBookTimeViewToPDF(${startDateTimeStamp});" title='<spring:message code="label.button.print"/>'>
	                   <i id="printTherapistViewId" class="glyphicon glyphicon-print"></i>
	               </a>
            </div>
            <div id="currentTimeStamp" class="timedate" data-millis="${viewVO.startTime.millis}">
                <a class="btn btn-default changeCalendarBtn" data-date='${viewVO.startTime.minusDays(1).millis}'
                   href="javascript:;" role="button">
                    <i class="glyphicon glyphicon-chevron-left"></i>
                </a>
                <span class="calenar_s"><fmt:formatDate value="${viewVO.startTime.toDate()}"
                                                        pattern="yyyy-MM-dd"/></span>
                <a class="btn btn-default changeCalendarBtn" data-date='${viewVO.startTime.plusDays(1).millis}'
                   href="javascript:;" role="button">
                    <i class="glyphicon glyphicon-chevron-right"></i>
                </a>
            </div>
               	<input type="hidden" id="shopId" value="${firstShopId }">
        	<div class="calendar-shop">
            <ul id="shopTab" class="nav nav-tabs">
                <c:forEach items="${shopList}" var="item" varStatus="status">
                	
                    <li class='<c:if test="${item.id == viewVO.shopId}">active</c:if>'
                        data-shop-id="${item.id}">
                        <a class="shopLink" href='javascript:' data-shop-id="${item.id}">${item.name}</a>
                    </li>
                </c:forEach>
            </ul>
            </div>
        </div>
        
        <div class="calendar-content">
        <div class="calendar-content-inner">
            <div class="time-line"></div>
            <div class="time"></div>
            <div class="header_therapist">
                <div style="height:${cellHeight}px;width:${timeCellWidth+1}px"><spring:message code="label.time"/> </div>
            <c:forEach items="${therapistList}" var="item" varStatus="status">
	                         <div class="therapist" data-therapist-id="${item.id}"
	                     style="left: ${timeCellWidth + status.index * cellWidth}px;top: 0;">${item.displayName}</div>
            </c:forEach>
            </div>
            <%
                request.setAttribute("isTherapist", WebThreadLocal.getUser().getSysRoles().contains(CommonConstant.STAFF_ROLE_REF_THERAPIST));
                List<User> therapistList = (List<User>) request.getAttribute("therapistList");
                ViewVO viewVO = (ViewVO) request.getAttribute("viewVO");
                DateTime startDateTime = viewVO.getStartTime();
                DateTime endDateTime = viewVO.getEndTime();
                boolean isToday = new DateTime().withTimeAtStartOfDay().isEqual(startDateTime.withTimeAtStartOfDay());
                request.setAttribute("isToday", isToday);
                pageContext.setAttribute("startDateTime", startDateTime);
                pageContext.setAttribute("endDateTime", endDateTime);
                pageContext.setAttribute("startDate", startDateTime.toString("yyyy-MM-dd"));
                DateTime changeToolTipDirectionTime = startDateTime.plusHours(4); // 前四小时的bookItem, tooltip向下提示
                pageContext.setAttribute("changeToolTipDirectionTime", changeToolTipDirectionTime);
                int timeUnit = CommonConstant.TIME_UNIT;
                pageContext.setAttribute("timeUnit", timeUnit);
                pageContext.setAttribute("timeMillisUnit", timeUnit * 60 * 1000); // 毫秒单位
                int maxRow = Minutes.minutesBetween(startDateTime, endDateTime).getMinutes() / timeUnit;
                int maxCol = therapistList.size();
                pageContext.setAttribute("maxRow", maxRow);
                DateTime currentTime = new DateTime(startDateTime.getMillis());
                
                CellVO cellVO;
                User therapist;
                // 最外层循环
                for (int rowIndex = 0; rowIndex < maxRow; rowIndex++, currentTime = currentTime.plusMinutes(timeUnit)) {
                    pageContext.setAttribute("currentTime", currentTime);
                    pageContext.setAttribute("rowIndex", rowIndex);
            %>
            <div class="time"
                 style="left: 0; top: ${cellHeight * (rowIndex + 1)}px;"><fmt:formatDate value="${currentTime.toDate()}" pattern="hh:mm a"/></div>
            <%
                Map<String, CellVO> cellVOMap = (Map<String, CellVO>) request.getAttribute("cellVOMap");
                for (int colIndex = 0; colIndex < maxCol; colIndex++) {
                    therapist = therapistList.get(colIndex);
                    pageContext.setAttribute("therapist", therapist);
                    pageContext.setAttribute("colIndex", colIndex);
                    cellVO = cellVOMap.get(currentTime.getMillis() + "," + therapist.getId());
                    pageContext.setAttribute("cellVO", cellVO);
            %>
            <c:set var="left" value="${timeCellWidth + colIndex * cellWidth}"/>
            <c:set var="top" value="${cellHeight * (rowIndex + 1)}"/>
            <div class='cell ${currentTime.getHourOfDay() % 2 == 0 ? "even" : "odd"} ${cellVO ne null ? "block" : ""}'
                 data-book-item-id="${cellVO.bookItem.id}"
                 data-block-id="${cellVO.block.id}"
                 data-time="${currentTime.millis}"
                 data-therapist="${therapist.id}"
                 data-therapist-name="${therapist.displayName}"
                 data-position-left="${left}"
                 data-position-top="${top}"
                 style="left: ${left}px; top: ${top}px;">
                <div id="message" class="hide"><fmt:formatDate value="${currentTime.toDate()}" pattern="hh:mm a"/>
                    / ${therapist.displayName}</div>
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
                         data-placement="right"
                         data-container="body"
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
                         data-position-right="${right}"
                         data-position-top="${top}"
                         data-height="${height}"
                         data-double-booking="${currentBookItem.isDoubleBooking}"
                         data-product-option-id="${currentBookItem.productOption.id}"
                         data-member-id="${currentMember.id}"
                         style="left: ${left}px; top: ${top}px;height: ${height}px;min-height: ${height}px;padding:1px;">
                        
                       <%-- Ivy hide double booking on 2018-09-11,please keep the coding <c:if test="${!cellVO.bookItem.isDoubleBooking }"> --%>
                            <div>
                            	<div>
                                        ${currentMember.fullName}
                                <%--<c:choose>
                            	<c:when test="${currentBook.guest !=null && currentBook.guest.firstName != null && currentBook.guest.lastName != null}">
                            		${currentBook.guest.firstName} ${currentBook.guest.lastName}
                            	</c:when>
                            	<c:otherwise>${currentMember.fullName}</c:otherwise>
                            	</c:choose>--%>
                                </div>
                                <div>${currentBookItem.productName}</div>
                                <div>${currentMember.mobilePhone}</div>
                                <div>${currentRoom.name}</div>
                                <div>
                                <c:if test="${currentMember.gender eq 'MALE'}">
                                    <img src="<c:url value='/resources/img/m.png'/>"/>
                                </c:if>
                                <c:if test="${currentBook.pregnancy !=null && currentBook.pregnancy}">
                                    <img src="<c:url value='/resources/img/p.png'/>"/>
                                </c:if>
                                <c:if test="${currentMember.checkWhetherHasExpiringPackage}">
                                    <img src="<c:url value='/resources/img/e.png'/>"/>
                                </c:if>
                                </div>
                            </div>
	                        <c:if test="${cellVO.onRequest}">
	                            <div class="on-request">
	                                <span class="glyphicon glyphicon-registration-mark"></span>
	                            </div>
	                        </c:if>
                       <%-- </c:if> --%>
                       <%-- Ivy hide double booking on 2018-09-11,please keep the coding: <c:if test="${cellVO.bookItem.isDoubleBooking }">
                        
                        <div class="double_booking_children  " >

                        <%
                        	BookItem bookItem =(BookItem)pageContext.getAttribute("currentBookItem");
                        	List<BookItem> childrenOfDoubleBooking = bookItem.getChildrenOfDoubleBooking();
                        	for(BookItem childBI : childrenOfDoubleBooking){
                        		Boolean onRequest =childBI.getRequestedOfTherapist(therapist.getId());
                        		pageContext.setAttribute("childBI", childBI);
                        		pageContext.setAttribute("onRequest", onRequest);


                        		%>
	
	                        		<div>
			                            <div>${childBI.book.user.fullName}</div>
			                            <div>${childBI.productName}</div>
			                            <div>${childBI.room.name}</div>
			                        </div>
		                        <c:if test="${onRequest}">
		                            <div class="on-request">
		                                <span class="glyphicon glyphicon-registration-mark"></span>
		                            </div>
		                        </c:if>
                        		<%

                        	}
                        %>
                        </div>
                        </c:if> --%>
                        <div class="hide title">
                        	<div class="hide_title_${currentBookItem.status.toLowerCase()}">
                                <div><spring:message code="label.client"/>: ${currentMember.fullName}</div>
                            <div><spring:message code="label.treatment"/>: ${currentBookItem.productName}</div>
                            <div><spring:message code="label.start.time"/>:
                                <fmt:formatDate value="${currentTime.toDate()}" pattern="hh:mm a"/></div>
                           <div><spring:message code="label.end.time"/>:
                                <fmt:formatDate value="${currentBookItem.appointmentEndTime}" pattern="hh:mm a"/></div>
                            <div><spring:message code="label.duration"/>: ${currentBookItem.duration}
                                <c:if test="${processTime gt 0}">(<spring:message
                                        code="label.process.time"/>:${processTime})</c:if></div>
                            <div><spring:message code="label.amount"/>: <spring:message code="label.currency.default"/>${currentBookItem.price}</div>
                            <div><spring:message code="label.therapist"/>: ${currentBookItem.therapistNames}</div>
                            <div><spring:message code="label.room"/>: ${currentRoom.name}</div>
                            <div><spring:message code="label.status"/>:
                                <spring:message code="label.book.status.${currentBookItem.status}"/></div>
                            <div>Booked By: ${currentBookItem.createdBy}</div>
                                <c:choose>
                                    <c:when test="${currentBook.guest == null}">
                                        <div><spring:message code="label.cash.package"/>: <spring:message code="label.currency.default"/>${currentMember.remainValueOfCashPackage}</div>
                                        <div><spring:message code="label.member.remarks"/>: ${currentMember.remarks}</div>
                                    </c:when>
                                    <c:otherwise>
                                        <div><spring:message code="label.contact.number"/>: ${currentBook.guest.mobilePhone}</div>
                                    </c:otherwise>
                                </c:choose>

                                <div><spring:message code="label.booking.remarks"/>: ${currentBook.remarks}</div>
                            </div>
                           <%--Ivy hide double booking on 2018-09-11,please keep the coding: <c:if test="${cellVO.bookItem.isDoubleBooking }">
		                       <div class="double_booking_children">
		                        <%
		                        	BookItem bookItem =(BookItem)pageContext.getAttribute("currentBookItem");
		                        	List<BookItem> childrenOfDoubleBooking = bookItem.getChildrenOfDoubleBooking();
		                        	for(BookItem childBI : childrenOfDoubleBooking){
		                        		Boolean onRequest =childBI.getRequestedOfTherapist(therapist.getId());
		                        		pageContext.setAttribute("childBI", childBI);
		                        		pageContext.setAttribute("onRequest", onRequest);
		                        		%>
		                        		<div class="hide_title_${childBI.status.toLowerCase()}">
			                            <div><spring:message code="label.client"/>: ${childBI.book.user.fullName}</div>
			                            <div>
			                                <spring:message code="label.mobile.phone"/>:
			                                <c:choose>
			                                    <c:when test="${isTherapist}">
			                                        ${childBI.book.user.guest ? childBI.book.guest.mobilePhoneHiddenPart : childBI.book.user.mobilePhoneHiddenPart}
			                                    </c:when>
			                                    <c:otherwise>
			                                        ${childBI.book.user.guest ? childBI.book.guest.mobilePhone : childBI.book.user.mobilePhone}
			                                    </c:otherwise>
			                                </c:choose>
			                            </div>
			                            <div><spring:message code="label.treatment"/>: ${childBI.productName}</div>
			                            <div><spring:message code="label.start.time"/>:
			                                <fmt:formatDate value="${childBI.appointmentTime}" pattern="hh:mm a"/></div>
			                           <div><spring:message code="label.end.time"/>:
			                                <fmt:formatDate value="${childBI.appointmentEndTime}" pattern="hh:mm a"/></div>
			                            <div><spring:message code="label.duration"/>: ${childBI.duration}
			                                <c:if test="${childBI.productOption.processTime gt 0}">(<spring:message
			                                        code="label.process.time"/>:${childBI.productOption.processTime})</c:if></div>
			                            <div><spring:message code="label.amount"/>: ${childBI.price}</div>
			                            <div><spring:message code="label.therapist"/>: ${childBI.therapistNames}</div>
			                            <div><spring:message code="label.room"/>: ${childBI.room.name}</div>
			                            <div><spring:message code="label.status"/>:
			                                <spring:message code="label.book.status.${childBI.status}"/></div>
			                            <div>
				                            <c:if test="${onRequest}">
					                            <div class="on-request">
					                                <span class="glyphicon glyphicon-registration-mark"></span>
					                            </div>
				                        	</c:if>
				                        </div>
			                            <div>Booked By: ${childBI.createdBy}</div>
			                           </div>
		                        		<%
		                        	}
		                        %>
		                        </div>
	                        </c:if> --%>
                        </div>
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
                         data-position-right="${right}"
                         data-position-top="${top}"
                         data-height="${height}"
                         style="left: ${left}px; top: ${top}px;height: ${height}px;z-index:9999;">
                        <div style="padding:1px;">
                        	<spring:message code="label.block.type.${cellVO.block.type}"/></br>
                        	<spring:message code="label.remarks"/>: ${cellVO.block.remarks}
                        </div>
                    </div>
                </c:when>
            </c:choose>
            <%
                    }
                }
            %>
        </div>
        </div>

        <div class="legend">
            <ul>
            	<%-- <li class="status-double"><spring:message code="label.book.status.double"/></li> --%>
                <li class="status-pending"><spring:message code="label.book.status.PENDING"/></li>
                <li class="status-confirm"><spring:message code="label.book.status.CONFIRM"/></li>
                <li class="status-checkin_service"><spring:message code="label.book.status.CHECKIN_SERVICE"/></li>
                <li class="status-complete"><spring:message code="label.book.status.COMPLETE"/></li>
                <li class="status-block"><spring:message code="label.book.status.blocked"/></li>
            </ul>
        </div>
    </div>
    <div class="calendar-right">
        <div id="datePicker"></div>
        <div class="clear-both"></div>
        <div id="waitingList" class="waiting-list"></div>
    </div>
</div>

<script type="text/javascript">
	
	function printTherapistBookTimeViewToPDF(id){
		var shopid = $("#shopId", getContext()).val();
        BootstrapDialog.confirm({
            title: '<spring:message code="label.prepaid.confirmation"/> ',
            message: '<spring:message code="label.book.print.therapist.view"/> ',
            callback: function (status) {
                if (status) {
                    // 發起動作請求
                  window.location.href ='<c:url value="/book/printTherapistBookTimeViewToPDF?startDateTimeStamp="/>'+id+"&shopId="+shopid;
                }
            }
        });
   };

    $(function () {
        <c:if test="${viewVO.rightCalendarHide}">
        $('.calendar-right', getContext()).hide();
        $('.calendar-left', getContext()).width($(window).width() - 10 - $('.calendar-left', getContext()).offset().left);
        </c:if>

        // parent一定要是加载的内容里面最大的div，保证注册到这个里的事件可以随着内容的清空而清空
        // 計算calendar的顯示高度
        function calHeightAndWidth() {
            var $window = $(window);
            var calendarLeft = $('.calendar-left', getContext());
            var calendarRight = $('.calendar-right', getContext());
            var calendarContent = $(".calendar-content", getContext());
            if (calendarRight.is(':hidden')) {
                calendarLeft.width($window.width() - 10 - calendarLeft.offset().left);
            } else {
                calendarLeft.width($window.width() - 250 - calendarLeft.offset().left);
            }
            calendarContent.height($window.height() - 70 - calendarContent.offset().top);
            calendarRight.height($window.height() - calendarRight.offset().top + 28);
            $('#waitingList', getContext()).height($window.height() - 312 - $('#datePicker', getContext()).offset().top);
        }

        calHeightAndWidth(); // 调整高度

        $('#toggleCalendar', getContext()).click(function () {
            var calendarLeft = $('.calendar-left', getContext());
            var calendarRight = $('.calendar-right', getContext());
            // 隐藏时，扩展
            if (calendarRight.is(":hidden")) {
                calendarRight.show();
                calendarLeft.width($(window).width() - 250 - calendarLeft.offset().left);
            } else {
                calendarRight.hide();
                calendarLeft.width($(window).width() - 10 - calendarLeft.offset().left);
            }
        });


        //chanson 测试滚动
        $('.calendar-content').scroll(function() {
            $(this).find('.time').css('left', $(this).scrollLeft());
            //$(this).find('.header_therapist').css('top', $(this).scrollTop());
        });


        // 屏蔽time-box的resize事件
        $("div.time-box", getContext()).resize(function (event) {
            event.preventDefault();
            event.stopPropagation();
        });

        var divCell = $("div.cell", getContext());
        $('div[data-toggle="tooltip"]', getContext()).tooltip({
            html: true,
            placement: function (element, args1) {
                return $(args1).data('placement'); // 确定显示位置
            },
            title: function () {
                return $(this).find('.title').html();
            }
        });
        function isBlock(container, target) {
            // 没有容器接受，表示block
            if (container.length == 0) {
                return true;
            }
            var time = container.data('time');
            var therapist = container.data('therapist');
            var targetTime = target.data('time');
            var targetTherapist = target.data('therapist');
            //var room = target.data('room');
            if (time == targetTime && therapist == targetTherapist) {
                return true; // 表示位置没有发生变化
            }
            // 检查当前容器和后面resourceSize个数的容器是否被block,并且排除自己
            var targetResourceLength = target.data('size');
            var targetId = target.data('book-item-id');
            var cell = container;
            var currentContext = $('div.cell[data-therapist=' + therapist + ']', getContext());
            for (var i = 1; i <= targetResourceLength; i++) {
                var block = cell.length == 0 || cell.hasClass('block') || cell.hasClass('time-room-block') || cell.hasClass('therapist-block') || cell.hasClass('therapist-time-block');
                // cell.length 为零表示没有可用的容器装在现在的box
                if (block && targetId != cell.data('book-item-id')) {
                    return true;
                }
                time = time + ${timeMillisUnit}; // 查询下一个容器
                cell = currentContext.filter('div.cell[data-time=' + time + ']');
            }
            return false;
        }

        // 可拖动
        $("div.status-pending,div.status-confirm", getContext()).draggable({
            cursor: 'move',
            opacity: 0.7,
            cursorAt: {top: -1, left: 55},
            revert: function (container) {
                return isBlock($(container), $(this));
            },
            appendTo: "body",
            helper: function () {
                return $(this).clone();
            },
            start: function (event) {
                //var $target = $(event.target);
                //changeCellBlockStatus($target, true);
            },
            stop: function (event) {
                var allBox = $('div.cell', getContext());
                allBox.removeClass('time-room-block').removeClass('therapist-block').removeClass('therapist-time-block');
            }
        });

        divCell.droppable({
            accept: '.book-item-box,.waiting-box',  // 接受draggle的对象
            tolerance: 'pointer',
            drop: function (event, ui) {
                var $container = $(this), $dragItem = ui.draggable;
                if (!isBlock($container, $dragItem)) {
                    // 是否on request并且修改了技师
                    if ($dragItem.data('on-request') == true && ($container.data('therapist') != $dragItem.data('therapist'))) {
                        Dialog.confirm({
                            title: "Confirmed Dialog",
                            message: "Are you sure to change on request therapist?",
                            callback: function (res) {
                                if (res) {
                                    doReloadTherapistView($container, $dragItem);
                                }
                            }
                        });
                    } else {
                        doReloadTherapistView($container, $dragItem);
                    }
                }
            }
        });

        function doReloadTherapistView($container, $dragItem) {
            var data;
            if ($dragItem.hasClass('waiting-box')) {
                data = {
                    appointmentTimeStamp: $container.data('time'),
                    newTherapistId: $container.data('therapist'),
                    bookItemId: $dragItem.data('book-item-id')
                };
            } else {
                data = {
                    appointmentTimeStamp: $container.data('time'),
                    newTherapistId: $container.data('therapist'),
                    oldTherapistId: $dragItem.data('therapist'),
                    bookItemId: $dragItem.data('book-item-id')
                };
            }
            reloadTherapistView(data);
        }

        // cell 提示
        divCell.mouseover(function () {
            $(this).find('#message').removeClass('hide');
        }).mouseout(function () {
            $(this).find('#message').addClass('hide');
        });

        function reloadTherapistView(data) {
            getContext().mask('Loading ... ');
            var scrollTop = $('.calendar-content', getContext()).scrollTop(); // 获取当前的滚动条高度
            data = $.extend({}, data);
            if (!data.shopId) {
                data.shopId = $("#shopId", getContext()).val();
            }
            if (!data.appointmentTimeStamp) {
                data.appointmentTimeStamp = $('#currentTimeStamp', getContext()).data('millis');
            }
            data.rightCalendarHide = $('.calendar-right', getContext()).is(":hidden"); // 记住右侧面板的状态
            var bookTimeTherapistViewMenu = $('#bookTimeTherapistViewMenu', getMenuContext());
            var href = bookTimeTherapistViewMenu.attr('href');
            var index = href.indexOf("?");
            index = (index == -1) ? href.length : index;
            href = href.substr(0, index) + "?shopId=" + data.shopId + "&appointmentTimeStamp=" + data.appointmentTimeStamp;
            bookTimeTherapistViewMenu.attr('href', href);
            $.post('<c:url value="/book/bookTimeTherapistView"/>', data, function (res) {
                // 清除 bookTimeTherapistTimeInterval
                if (typeof bookTimeTherapistTimeInterval === 'number') {
                    clearInterval(bookTimeTherapistTimeInterval);
                }
                getContext().html(res);
                $('.calendar-content', getContext()).scrollTop(scrollTop);  // 设置之前的滚动条高度
            }).always(function () {
                getContext().unmask();
            });
        }

        // 内联日期选择器
        var datePicker = $('#datePicker', getContext()).datePickerBS({
            format: 'yyyy-mm-dd'
        });
        datePicker.datePickerBS('update', '${startDate}');
        datePicker.on('changeDate', function (event) {
            reloadTherapistView({appointmentTimeStamp: event.date.getTime()});
        });

        $('.changeCalendarBtn', getContext()).click(function () {
            reloadTherapistView({appointmentTimeStamp: $(this).data('date')});
        });

        /* $('#shopId', getContext()).click(function () {
            	reloadTherapistView({
                shopId: $(this).val()
            });
        }); */
        $(".shopLink", getContext()).click(function () {
        	var shopId = $(this).data('shop-id');
        	$('#shopId', getContext()).val(shopId);
        	reloadTherapistView({
                shopId: shopId
            });
        });
        function addBook($cell) {
            var param = {
                startAppointmentTime: new Date($cell.data('time')).format('yyyy-MM-dd'),
                therapistId: $cell.data('therapist'),
                shopId: $('#shopId', getContext()).val(),
                forward: 'bookTimeTherapistViewMenu'
            };

            loadFromPage({
                url: '<c:url value="/book/toAdd"/>',
                data: param
            });
        }

        // 快速添加book
        function quickAddBook($cell) {
            var currentTimeStamp = $cell.data('time');
            var param = {
                startTimeStamp: currentTimeStamp,
                therapistId: $cell.data('therapist'),
                shopId: $('#shopId', getContext()).val()
            };
            Dialog.create({
                title: "Quick Add Book",
                url: '<c:url value="/book/toQuickAdd"/>',
                urlData: param,
                callback: function () {
                    reloadTherapistView({appointmentTimeStamp: currentTimeStamp});
                }
            });
        }
     	// double book
        function doubleBook($cell) {
            var currentTimeStamp = $cell.data('time');
            var param = {
                startTimeStamp: currentTimeStamp,
                therapistId: $cell.data('therapist'),
                shopId: $('#shopId', getContext()).val(),
                originalBookItemId:$cell.data('book-item-id'),
                doubleBooking:true
            };
            Dialog.create({
                title: "Add Double Book",
                url: '<c:url value="/book/toQuickAdd"/>',
                urlData: param,
                callback: function () {
                    reloadTherapistView({appointmentTimeStamp: currentTimeStamp});
                }
        	});
    	}
     	
        function addBlock($cell) {
            var therapistId = $cell.data('therapist');
            var currentTimeStamp = $cell.data('time');
            var options = {
                title: "Add Block",
                url: '<c:url value="/book/toBlockTherapistAdd"/>',
                urlData: {
                    startTimeStamp: currentTimeStamp,
                    therapistId: therapistId,
                    shopId: $('#shopId', getContext()).val()
                },
                callback: function () {
                    reloadTherapistView({appointmentTimeStamp: currentTimeStamp});
                }
            };
            Dialog.create(options);
        }

        // book item check out
        function singleCheckOut(bookItemId) {
            var options = {
                width: 1000,
                title: "Check Out",
                url: '<c:url value="/sales/bookItemToCheckout"/>',
                urlData: {
                    bookItemId: bookItemId
                },
                callback: function (selfDlg) {
                    reloadTherapistView({});
                }
            };
            Dialog.create(options);
        }
        
     	// book item check out
        function changeStatusForDoubleBooking(bookItemId,status) {
            var options = {
                width: 1000,
                title: "Change Status For Double Booking",
                url: '<c:url value="/book/changeStatusForDoubleBooking"/>',
                urlData: {
                	bookItemId: bookItemId,
                    status: status
                },
                callback: function (selfDlg) {
                    reloadTherapistView({});
                }
            };
            Dialog.create(options);
        }

        // book check out
        function multiCheckOut(bookId, checkOutStatus) {
            var options = {
                url: '<c:url value="/sales/bookToCheckout"/>',
                data: {
                    bookId: bookId,
                    checkOutStatus: checkOutStatus,
                    forward: 'bookTimeTherapistViewMenu'
                }
            };
            loadFromPage(options);
        }

        // 右键菜单
        $.contextMenu({
            selector: '#bookTimeTherapistView div.cell',
            autoHide: true,
            trigger: 'left',
            callback: function (key, options) {
                switch (key) {
                    case 'quickAddBook' :
                        quickAddBook(options.$trigger);
                        break;
                    case 'add' :
                        addBook(options.$trigger);
                        break;
                    case 'addBlock' :
                        addBlock(options.$trigger);
                        break;
                    default:
                        break;
                }
            },
            items: {
                "quickAddBook": {name: "Add Booking", icon: "add"},
                /* "add": {name: "Add Book", icon: "add"}, */
                "addBlock": {name: 'Block Out', icon: "add"}
            }
        });

        $.contextMenu({
            selector: '#bookTimeTherapistView div.book-item-box',
            autoHide: true,
            trigger: 'left',
            callback: function (key, options) {
                var currentStatus = options.$trigger.data('status');
                var checkResult = false;
                var doubleBooking = options.$trigger.data('double-booking');

                var currentTimeStamp = options.$trigger.data('time');
                var param = {
                    startTimeStamp: currentTimeStamp,
                    therapistId: options.$trigger.data('therapist'),
                    shopId: $('#shopId', getContext()).val(),
                    bookItemId: options.$trigger.data('book-item-id'),
                    roomId: options.$trigger.data('room'),
                    productOptionId: options.$trigger.data('product-option-id'),
                    memberId: options.$trigger.data('member-id'),
                    bookId: options.$trigger.data('book-id')
                };

                switch (key) {
                    case 'confirm' :
                    	if(doubleBooking){
                    		changeStatusForDoubleBooking(options.$trigger.data('book-item-id'),'CONFIRM');
                    	}else{
	                        Dialog.confirm({
	                            title: "Confirmed Dialog",
	                            message: "Sure change to Confirmed status?",
	                            callback: function (res) {
	                                if (res) {
	                                    $.post('<c:url value="/book/updateBookItemStatus"/>?status=CONFIRM', {bookItemId: options.$trigger.data('book-item-id')}, function () {
	                                        reloadTherapistView({});
	                                    });
	                                }
	                            }
	                        });
                    	}
                        break;
                    case 'checkin_service' :
                        
                        if(doubleBooking){
                    		changeStatusForDoubleBooking(options.$trigger.data('book-item-id'),'CHECKIN_SERVICE');
                    	}else{
                    		checkResult = checkAction(currentStatus, 'CHECKIN_SERVICE');
                            if (checkResult.length > 1) {
                                Dialog.alert({
                                    title: "Error",
                                    message: checkResult
                                });
                                break;
                            }
	                        Dialog.confirm({
	                            title: "In Progress Dialog",
	                            message: "Sure change to In Progress status?",
	                            callback: function (res) {
	                                if (res) {
	                                    $.post('<c:url value="/book/updateBookItemStatus"/>?status=CHECKIN_SERVICE', {bookItemId: options.$trigger.data('book-item-id')}, function () {
	                                        reloadTherapistView({});
	                                    });
	                                }
	                            }
	                        });
                    	}
                        break;
                    case 'not_show' :
                    	if(doubleBooking){
                      		changeStatusForDoubleBooking(options.$trigger.data('book-item-id'),'NOT_SHOW');
                      	}else{
	                        checkResult = checkAction(currentStatus, 'NOT_SHOW');
	                        if (checkResult.length > 1) {
	                            Dialog.alert({
	                                title: "Error",
	                                message: checkResult
	                            });
	                            break;
	                        }
	
	                        Dialog.confirm({
	                            title: "No Show Dialog",
	                            message: "Sure change to No Show status?",
	                            callback: function (res) {
	                                if (res) {
	                                    $.post('<c:url value="/book/updateBookItemStatus"/>?status=NOT_SHOW', {bookItemId: options.$trigger.data('book-item-id')}, function () {
	                                        reloadTherapistView({});
	                                    });
	                                }
	                            }
	                        });
                      	}
                        break;
                    case 'cancel' :
                    	if(doubleBooking){
                      		changeStatusForDoubleBooking(options.$trigger.data('book-item-id'),'CANCEL');
                      	}else{
	                        checkResult = checkAction(currentStatus, 'CANCEL');
	                        if (checkResult.length > 1) {
	                            Dialog.alert({
	                                title: "Error",
	                                message: checkResult
	                            });
	                            break;
	                        }
	
	                        Dialog.confirm({
	                            title: "Cancel Dialog",
	                            message: "Sure change to cancel status?",
	                            callback: function (res) {
	                                if (res) {
	                                    $.post('<c:url value="/book/updateBookItemStatus"/>?status=CANCEL', {bookItemId: options.$trigger.data('book-item-id')}, function () {
	                                        reloadTherapistView({});
	                                    });
	                                }
	                            }
	                        });
                      	}
                        break;
                    case 'single_check_out' :
                        checkResult = checkAction(currentStatus, 'COMPLETE');
                        if (checkResult.length > 1) {
                            Dialog.alert({
                                title: "Error",
                                message: checkResult
                            });
                            break;
                        }

                        singleCheckOut(options.$trigger.data('book-item-id'));
                        break;
                    case 'multi_check_out' :
                    	if(doubleBooking){
                      		changeStatusForDoubleBooking(options.$trigger.data('book-item-id'),'COMPLETE');
                      	}else{
	                        checkResult = checkAction(currentStatus, 'COMPLETE');
	                        if (checkResult.length > 1) {
	                            Dialog.alert({
	                                title: "Error",
	                                message: checkResult
	                            });
	                            break;
	                        }
	
	                        multiCheckOut(options.$trigger.data('book-id'));
                      	}
                        break;
                        
                    case 'double_booking' :
                    	 doubleBook(options.$trigger);
                         break;
                    case 'booking_edit' :
                        if (doubleBooking) {
                            changeStatusForDoubleBooking(options.$trigger.data('book-item-id'), 'CONFIRM');
                        } else {
                            Dialog.create({
                                title: "Quick Edit Book",
                                url: '<c:url value="/book/toQuickEdit"/>',
                                urlData: param,
                                callback: function (data) {

                                    reloadTherapistView({appointmentTimeStamp: currentTimeStamp});
                                }
                            });
                        }
                        break;

                    case 'no_show_check_out' :
                        if(doubleBooking){
                            changeStatusForDoubleBooking(options.$trigger.data('book-item-id'),'COMPLETE');
                        }else{

                            multiCheckOut(options.$trigger.data('book-id'), true);
                        }
                        break;
                    default:
                        break;

                }
            },
            items: {
                "confirm": {name: "Confirmed", icon: ""},
                "checkin_service": {name: "In Progress", icon: ""},
                "not_show": {name: "No Show", icon: ""},
                "multi_check_out": {name: "Check Out", icon: ""},
                "cancel": {name: "Cancel", icon: "delete"},
                "booking_edit": {name: "Book Edit", icon: ""},
                "no_show_check_out": {name: "No Show Check Out", icon: ""},
                /* "double_booking": {name: "Double Booking", icon: ""} */
            }
        });

        // 检查当前的状态是否可以转化为目标的状态
        // CONFIRM
        // CHECKIN_SERVICE
        // NOT_SHOW
        // CHECK_OUT
        function checkAction(currentStatus, targetStatus) {

            if (targetStatus == 'COMPLETE') {
                if (currentStatus != 'CHECKIN_SERVICE') {
                    return 'Only Service in progress can check out';
                }
                return true;
            }

            if (targetStatus == 'CHECKIN_SERVICE') {
                if (currentStatus != 'CONFIRM') {
                    return 'Only Confirm status can change to Service in progress';
                }
                return true;
            }

            if (targetStatus == 'NOT_SHOW' || targetStatus == 'CANCEL') {
                if (currentStatus == 'COMPLETE') {
                    return "Complete status can not change to cancel or not show status!";
                }
                return true;
            }

            return true;
        }

        // time box 右键菜单
        $.contextMenu({
            selector: '#bookTimeTherapistView div.time-box',
            autoHide: true,
            trigger: 'left',
            callback: function (key, options) {
                var currentTimeStamp = options.$trigger.data('time');
                var param = {
                    startTimeStamp: currentTimeStamp,
                    id: options.$trigger.data('block-id'),
                    updateType: (options.$trigger.data('repeat-type') == 'NONE') ? 'ONCE' : 'ALL'
                };
                switch (key) {
                    case 'editOnce' :
                        param.updateType = 'ONCE';
                        Dialog.create({
                            title: "Edit Once Block",
                            url: '<c:url value="/book/toBlockTherapistUpdate"/>',
                            urlData: param,
                            callback: function () {
                                reloadTherapistView({appointmentTimeStamp: currentTimeStamp});
                            }
                        });
                        break;
                    case 'editAll' :
                        Dialog.create({
                            title: "Edit Once Block",
                            url: '<c:url value="/book/toBlockTherapistUpdate"/>',
                            urlData: param,
                            callback: function () {
                                reloadTherapistView({appointmentTimeStamp: currentTimeStamp});
                            }
                        });
                        break;
                    case 'cancel' :
                        param.updateType = 'ONCE';
                        Dialog.confirm({
                            title: "Cancel Dialog",
                            message: "Sure to cancel this block?",
                            callback: function (res) {
                                if (res) {
                                    $.post('<c:url value="/book/removeBlock"/>', param, function (json) {
                                        if(json.statusCode == 401) {
                                            Dialog.alert({
                                                title: "Access denied",
                                                message: json.message
                                            });
                                        } else {
                                            reloadTherapistView({appointmentTimeStamp: currentTimeStamp});
                                        }
                                    }, 'json');
                                }
                            }
                        });
                        break;
                    case 'cancelAll' :
                        Dialog.confirm({
                            title: "Cancel All Dialog",
                            message: "Sure to cancel all this block?",
                            callback: function (res) {
                                if (res) {
                                    $.post('<c:url value="/book/removeBlock"/>', param, function (json) {
                                        if(json.statusCode == 401) {
                                            Dialog.alert({
                                                title: "Access denied",
                                                message: json.message
                                            });
                                        } else {
                                            reloadTherapistView({appointmentTimeStamp: currentTimeStamp});
                                        }
                                    }, 'json');
                                }
                            }
                        });
                        break;
                }
            },
            items: {
                "editOnce": {name: "Edit This One"},
                "editAll": {name: "Edit All"},
                "cancel": {name: "Cancel This One", icon: "delete"},
                "cancelAll": {name: "Cancel All", icon: "delete"}
            }
        });

        <c:if test="${not empty error}">
        Dialog.alert({
            title: "Error",
            message: "${error}"
        });
        </c:if>

        // load waiting list
        $('#waitingList', getContext()).load('<c:url value="/book/bookItemWaitingList"/>', {
            startTime: '${startDate}',
            shopId: $("#shopId", getContext()).val()
        }, function () {
            $('.waiting-box', getContext()).draggable({
                cursor: 'move',
                opacity: 0.7,
                cursorAt: {top: -1, left: 55},
                revert: function (container) {
                    return isBlock($(container), $(this));
                },
                appendTo: "body",
                helper: function () {
                    var cloneObj = $(this).clone();
                    cloneObj.css({
                        height: cloneObj.data('children') * Number('${cellHeight}'),
                        width: ${cellWidth},
                        overflow: 'hidden'
                    });
                    return cloneObj;
                },
                start: function (event) {
                },
                stop: function (event) {
                }
            });
        });

        <c:choose>
        <c:when test="${isToday}">
        var bookTimeTherapistTimeInterval;
        var startDateTimeMillis = ${startDateTime.millis};
        var calendarContent = $('.calendar-content', getContext());
        var calendarHeight = calendarContent[0].scrollHeight + 4;
        var heightPerMillisSecond = calendarHeight / ( ${endDateTime.millis} -startDateTimeMillis);
        $('.time-line, .calendar-content-inner', calendarContent).removeClass('hide').width(${therapistList.size() * cellWidth} + ${timeCellWidth});
        $('.calendar-content-inner', calendarContent).height(${rowIndex * cellHeight});

        // 显示时间线
        function changeTimeLine() {
            var position = heightPerMillisSecond * (new Date().getTime() - startDateTimeMillis);
            $('.time-line', calendarContent).css({top: position});
            console.info('position:' + bookTimeTherapistTimeInterval);
            <%--console.log($('.time-line').offset().top/$('.calendar-content-inner').height()*$('.calendar-content').height());--%>
            <%--$('.calendar-content').scrollTop($('.time-line').offset().top/$('.calendar-content-inner').height()*$('.calendar-content').height());--%>
            $('.calendar-content').scrollTop($('.time-line').offset().top-$('.calendar-content').height()+200)
        }

        changeTimeLine();
        if (!bookTimeTherapistTimeInterval) {
            bookTimeTherapistTimeInterval = setInterval(changeTimeLine, 300000);
        }
        </c:when>
        <c:otherwise>
        $('.time-line', $('.calendar-content', getContext())).addClass('hide');
        </c:otherwise>
        </c:choose>
    });
</script>