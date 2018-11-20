<%@ page import="com.spa.constant.CommonConstant" %>
<%@ page import="org.joda.time.DateTime" %>
<%@ page import="org.joda.time.Minutes" %>
<%@ page import="org.spa.model.shop.Room" %>
<%@ page import="org.spa.vo.book.CellVO" %>
<%@ page import="org.spa.vo.book.ViewVO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.spa.utils.WebThreadLocal" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<c:set var="timeCellWidth" value="70"/>
<c:set var="cellWidth" value="150"/>
<c:set var="cellHeight" value="22"/>
<style>
.time-line{background-color: rgb(255, 126, 126);height:3px;}
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
    }
    #shopTab{
    margin-top:22px !important;
    }
	
 
</style>
<h3 class="text-h3-white">
    <spring:message code="label.room.view"/>
</h3>
<%-- shop的开门时间 --%>
<fmt:formatDate var="openTimeString" value="${startDateTime.toDate()}" pattern=" hh:mm a"/>
<div id="bookTimeRoomView" class="calendar">
    <div class="calendar-left">
        <div class="calendar-head">
            <div class="hide-calendar-btn">
                <a id="toggleCalendar" class="btn btn-default" href="javascript:;" role="button">
                    <i class="glyphicon glyphicon-resize-horizontal"></i>
                </a>
            </div>
            <div id="currentTimeStamp" class="timedate" data-millis="${startDateTime.millis}">
                <a class="btn btn-default changeCalendarBtn" data-date='${startDateTime.minusDays(1).millis}' href="javascript:;" role="button">
                    <i class="glyphicon glyphicon-chevron-left"></i>
                </a>
                <span class="calenar_s"><fmt:formatDate value="${startDateTime.toDate()}" pattern="yyyy-MM-dd"/></span>
                <a class="btn btn-default changeCalendarBtn" data-date='${startDateTime.plusDays(1).millis}' href="javascript:;" role="button">
                    <i class="glyphicon glyphicon-chevron-right"></i>
                </a>
            </div>
           <div class="calendar-shop">
                <input type="hidden" id="shopId" value="${viewVO.shopId }">
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
            <div class="time-line"></div>
            <div class="time"></div>
            <div class="header_therapist">		
                <div>time</div>
            <c:forEach items="${roomList}" var="item" varStatus="status">
                <div class="therapist" data-room-id="${item.id}" style="left: ${timeCellWidth + status.index * cellWidth}px;top: 0;">${item.name}&nbsp;(${item.capacity})</div>
            </c:forEach>
            </div>
            <%
                request.setAttribute("isTherapist", WebThreadLocal.getUser().getSysRoles().contains(CommonConstant.STAFF_ROLE_REF_THERAPIST));
                List<Room> roomList = (List<Room>) request.getAttribute("roomList");
                ViewVO viewVO = (ViewVO) request.getAttribute("viewVO");
                DateTime startDateTime = (DateTime) request.getAttribute("startDateTime");
                DateTime endDateTime = (DateTime) request.getAttribute("endDateTime");
                boolean isToday = new DateTime().withTimeAtStartOfDay().isEqual(startDateTime.withTimeAtStartOfDay());
                request.setAttribute("isToday", isToday);
                pageContext.setAttribute("startDate", startDateTime.toString("yyyy-MM-dd"));
                DateTime changeToolTipDirectionTime = startDateTime.plusHours(4); // 前四小时的bookItem, tooltip向下提示
                pageContext.setAttribute("changeToolTipDirectionTime", changeToolTipDirectionTime);
                int timeUnit = CommonConstant.TIME_UNIT;
                pageContext.setAttribute("timeUnit", timeUnit);
                pageContext.setAttribute("timeMillisUnit", timeUnit * 60 * 1000); // 毫秒单位
                int maxRow = Minutes.minutesBetween(startDateTime, endDateTime).getMinutes() / timeUnit;
                int maxCol = roomList.size();
                String cellClass;
                pageContext.setAttribute("maxRow", maxRow);
                DateTime currentTime = new DateTime(startDateTime.getMillis());
                for (int rowIndex = 0; rowIndex < maxRow; rowIndex++, currentTime = currentTime.plusMinutes(timeUnit)) {
                    pageContext.setAttribute("currentTime", currentTime);
                    pageContext.setAttribute("rowIndex", rowIndex);
            %>
            <div class="time" style="left: 0; top: ${cellHeight * (rowIndex + 1)}px;"><fmt:formatDate value="${currentTime.toDate()}" pattern="hh:mm a"/></div>
            <%
                Map<String, CellVO> cellVOMap = (Map<String, CellVO>) request.getAttribute("cellVOMap");
                for (int colIndex = 0; colIndex < maxCol; colIndex++) {
                    cellClass = "";
                    Room room = roomList.get(colIndex);
                    pageContext.setAttribute("room", room);
                    pageContext.setAttribute("colIndex", colIndex);
                    CellVO cellVO = cellVOMap.get(currentTime.getMillis() + "," + room.getId());
                    if(currentTime.getMillis() == 1478512500000L) {
                        System.out.println("cellVO:" + cellVO );
                    }
                    if (cellVO != null) {
                        cellClass = "block";
                    }

                    pageContext.setAttribute("cellVO", cellVO);
                    cellClass += currentTime.getHourOfDay() % 2 == 0 ? " even" : " odd";
                    pageContext.setAttribute("cellClass", cellClass);
            %>
            <c:set var="left" value="${timeCellWidth + colIndex * cellWidth}"/>
            <c:set var="top" value="${cellHeight * (rowIndex + 1)}"/>
            <div class="cell ${cellClass}"
                 data-book-item-id="${cellVO.bookItem.id}"
                 data-block-id="${cellVO.block.id}"
                 data-time="${currentTime.millis}"
                 data-room-id="${room.id}"
                 data-room-name="${room.name}"
                 data-position-left="${left}"
                 data-position-top="${top}"
                 style="left: ${left}px; top: ${top}px;">
                <div class="hide message"><fmt:formatDate value="${currentTime.toDate()}" pattern="hh:mm a"/> / ${room.name}</div>
            </div>
            <c:choose>
                <c:when test="${ cellVO ne null && cellVO.parent eq null && cellVO.bookItem ne null}">
                    <c:set var="currentBookItem" value="${cellVO.bookItem}"/>
                    <c:set var="currentBook" value="${currentBookItem.book}"/>
                    <c:set var="currentMember" value="${currentBook.user}"/>
                    <c:set var="currentRoom" value="${currentBookItem.room}"/>
                    <c:set var="processTime" value="${currentBookItem.productOption.processTime}"/>
                    <c:set var="height" value="${cellHeight * cellVO.size}"/>
                    <div class="book-item-box box status-${currentBookItem.status.toLowerCase()}"
                         data-toggle="tooltip"
                         data-placement="right"
                         data-container="body"
                         data-book-id="${currentBook.id}"
                         data-book-item-id="${currentBookItem.id}"
                         data-book-item="${currentBookItem.id}"
                         data-status="${currentBookItem.status}"
                         data-time="${currentTime.millis}"
                         data-children="${cellVO.children.size()}"
                         data-size="${cellVO.size}"
                         data-therapist-ids="${currentBookItem.therapistIds}"
                         data-on-request="${cellVO.onRequest}"
                         data-therapist-list="${currentBookItem.therapistIds}"
                         data-room-id="${room.id}"
                         data-product-option-id="${currentBookItem.productOption.id}"
                         data-position-left="${left}"
                         data-position-top="${top}"
                         data-height="${height}"
                         style="left: ${left}px; top: ${top}px;height: ${height}px;min-height: ${height}px;">
                        <div>
                            <div>${currentMember.fullName}</div>
                            <div>${currentBookItem.productName}</div>
                            <div>${currentBookItem.therapistNames}</div>
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
                        <div class="hide title">
                            <div><spring:message code="label.client"/>: ${currentMember.fullName}</div>
                            <%-- <div>
                                <spring:message code="label.mobile.phone"/>:
                                <c:choose>
                                    <c:when test="${isTherapist}">
                                        ${currentMember.guest ? currentBook.guest.mobilePhoneHiddenPart : currentMember.mobilePhoneHiddenPart}
                                    </c:when>
                                    <c:otherwise>
                                        ${currentMember.guest ? currentBook.guest.mobilePhone : currentMember.mobilePhone}
                                    </c:otherwise>
                                </c:choose>

                            </div> --%>
                            <div><spring:message code="label.treatment"/>: ${currentBookItem.productName}</div>
                            <div><spring:message code="label.start.time"/>:
                                <fmt:formatDate value="${currentTime.toDate()}" pattern="hh:mm a"/></div>
                            <div><spring:message code="label.end.time"/>:
                                <fmt:formatDate value="${currentBookItem.appointmentEndTime}" pattern="hh:mm a"/></div>
                            <div><spring:message code="label.duration"/>: ${currentBookItem.duration}
                                <c:if test="${processTime gt 0}">(<spring:message code="label.process.time"/>:${processTime})</c:if></div>
                            <div><spring:message code="label.amount"/>: <spring:message code="label.currency.default"/>${currentBookItem.price}</div>
                            <div><spring:message code="label.therapist"/>: ${currentBookItem.therapistNames}</div>
                            <div><spring:message code="label.room"/>: ${currentRoom.name}</div>
                            <div><spring:message code="label.status"/>: <spring:message code="label.book.status.${currentBookItem.status}"/></div>
                            <c:if test="${currentBookItem.bookItems.size() gt 0}">
                               <div>Share Room:
                               <c:forEach items="${currentBookItem.bookItems}" var="shareRoomItem" varStatus="shareStatus">
                                   <c:if test="${not shareStatus.first}"> / </c:if>${shareRoomItem.therapistNames}
                               </c:forEach>
                               </div>
                            </c:if>
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
                         data-room-id="${room.id}"
                         data-position-left="${left}"
                         data-position-top="${top}"
                         data-height="${height}"
                         style="left: ${left}px; top: ${top}px;height: ${height}px;">
                        <div><spring:message code="label.block.type.${cellVO.block.type}"/></br>
                        <spring:message code="label.remarks"/>: ${cellVO.block.remarks}</div>
                    </div>
                </c:when>
            </c:choose>
            <%
                    }
                }
            %>
        </div>
        <div class="legend">
            <ul>
                <li class="status-pending"><spring:message code="label.book.status.PENDING"/></li>
                <li class="status-confirm"><spring:message code="label.book.status.CONFIRM"/></li>
                <li class="status-checkin_service"><spring:message code="label.book.status.CHECKIN_SERVICE"/></li>
                <li class="status-complete"><spring:message code="label.survey.status.COMPLETED"/></li>
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
    $(function () {
        <c:if test="${viewVO.rightCalendarHide}">
        $('.calendar-right', getContext()).hide();
        $('.calendar-left', getContext()).width($(window).width() - 10 - $('.calendar-left', getContext()).offset().left);
        </c:if>

        // 計算calendar的顯示高度
        function calHeightAndWidth() {
            var $window = $(window);
            var calendarContent = $(".calendar-content", getContext());
            var calendarLeft = $('.calendar-left', getContext());
            var calendarRight = $('.calendar-right', getContext());
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
		

        $('div[data-toggle="tooltip"]', getContext()).tooltip({
            html: true,
            placement: function (element, args1) {
                return $(args1).data('placement');  // 确定显示位置
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
            var room = container.data('room-id');
            var targetTime = target.data('time');
            var targetRoom = target.data('room-id');
            if (time == targetTime && room == targetRoom) {
                return true; // 表示位置没有发生变化
            }
            // 检查当前容器和后面resourceSize个数的容器是否被block,并且排除自己
            var targetResourceLength = target.data('children') + 1;
            var targetId = target.data('book-item-id');
            var cell = container;
            var currentContext = $('div.cell[data-room-id=' + room + ']', getContext());
            for (var i = 1; i <= targetResourceLength; i++) {
                var block = cell.length == 0 || cell.hasClass('block') || cell.hasClass('time-block') || cell.hasClass('time-room-block') || cell.hasClass('room-block') || cell.hasClass('therapist-block') || cell.hasClass('therapist-time-block');
                // cell.length 为零表示没有可用的容器装在现在的box
                if (block && targetId != cell.data('book-item-id')) {
                    return true;
                }
                time = time + ${timeMillisUnit}; // 查询下一个容器
                cell = currentContext.filter('div.cell[data-time=' + time + ']');
            }
            return false;
        }

        // 动态更新cell是否block
        function changeCellBlockStatus(target, block) {
            // 同therapist 的时间段block 掉
            var therapistIds = '' + target.data('therapist-ids'), bookItem = target.data('book-item');
            blockSameTherapist(therapistIds, bookItem, block, 'time-block');
        }

        // 把box所占的所有时间都block掉
        function blockSameTime(box, block, blockClass) {
            var cell, time, resourceLength, $item;
            box.each(function (index, item) {
                $item = $(item);
                time = $item.data('time');
                resourceLength = $item.data('children') + 1;
                for (var i = 0; i < resourceLength; i++) {
                    cell = $('div.cell[data-time=' + time + ']', getContext());
                    if (block) {
                        cell.addClass(blockClass);
                    } else {
                        cell.removeClass(blockClass);
                    }
                    time = time + ${timeMillisUnit}; // 查询下一个容器
                }
            });
        }

        // block相同therapist的bookItem时间
        function blockSameTherapist(therapistIds, bookItem, block, blockClass) {
            if (therapistIds) {
                var therapistArray = therapistIds.split(',');
                $.each(therapistArray, function (index, item) {
                    var box = $('div.box[data-therapist-ids*=' + item + '][data-book-item!=' + bookItem + ']', getContext());
                    box.each(function (index, item) {
                        blockSameTime($(item), block, blockClass);
                    });
                });
            }
        }

        // 可拖动
        $("div.status-pending,div.status-confirm", getContext()).draggable({
            cursor: 'move',
            opacity: 0.5,
            cursorAt: {top: -1, left: 55},
            revert: function (container) {
                return isBlock($(container), $(this));
            },
            helper: 'clone',
            start: function (event, ui) {
                var $target = $(event.target);
                // block掉那些无法做这个product的room
                var allBox = $('div.cell', getContext());
                allBox.addClass('room-block');
                $.post('<c:url value="/room/getAvailableRoomList"/>', {
                    productOptionId: $target.data('product-option-id'),
                    shopId: $('#shopId', getContext()).val()
                }, function (res) {
                    if (res) {
                        var filter = [];
                        $.each(res, function (index, item) {
                            filter.push('div.cell[data-room-id=' + item.id + ']');
                        });
                        var div = $(filter.join(','), getContext()).removeClass('room-block');
                        allBox.not(div).addClass('time-block');
                    }
                });

                changeCellBlockStatus($target, true);
            },
            stop: function (event) {
                var allBox = $('div.cell', getContext());
                allBox.removeClass('room-block').removeClass('time-block');
            }
        });

        // cell 提示
        $("div.cell", getContext()).mouseover(function () {
            var $this = $(this);
            var title = $this.data('title');
            if (!title) {
                title = new Date($this.data('time')).format('hh:mm') + ' / ' + $this.data('room-name');
                $this.data('title', title);
            }
            $this.html(title);
        });
        $("div.cell", getContext()).mouseout(function () {
            $(this).html('');
        });

        $("div.cell", getContext()).droppable({
            accept: '.book-item-box,.waiting-box',
            tolerance: 'pointer',
            drop: function (event, ui) {
                var $container = $(this), $dragItem = ui.draggable;
                if (!isBlock($container, $dragItem)) {
                    if ($dragItem.hasClass('waiting-box')) {
                        updateBookItem($container, $dragItem);
                    } else {
                        var data = {
                            appointmentTimeStamp: $container.data('time'),
                            roomId: $container.data('room-id'),
                            bookItemId: $dragItem.data('book-item-id')
                        };
                        reloadRoomView(data);
                    }
                }
            }
        });

        function reloadRoomView(data) {
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

            var bookTimeRoomViewMenu = $('#bookTimeRoomViewMenu', getMenuContext());
            var href = bookTimeRoomViewMenu.attr('href');
            var index = href.indexOf("?");
            index = (index == -1) ? href.length : index;
            href = href.substr(0, index) + "?shopId=" + data.shopId + "&appointmentTimeStamp=" + data.appointmentTimeStamp;
            bookTimeRoomViewMenu.attr('href', href);
            if (typeof bookTimeRoomViewInterval === 'number') {
                clearInterval(bookTimeRoomViewInterval); // 清除定时器
            }

            getContext().load('<c:url value="/book/bookTimeRoomView"/>', data, function () {
                $('.calendar-content', getContext()).scrollTop(scrollTop);  // 设置之前的滚动条高度
            });
        }

        // 内联日期选择器
        var datePicker = $('#datePicker', getContext()).datePickerBS({
            format: 'yyyy-mm-dd'
        });
        datePicker.datePickerBS('update', '<fmt:formatDate value="${startDateTime.toDate()}" pattern="yyyy-MM-dd"/>');
        datePicker.on('changeDate', function (event) {
            reloadRoomView({appointmentTimeStamp: event.date.getTime()});
        });

        $('.changeCalendarBtn', getContext()).click(function () {
            reloadRoomView({appointmentTimeStamp: $(this).data('date')});
        });

        /* $('#shopId', getContext()).change(function () {
            reloadRoomView({
                shopId: $(this).val()
            });
        }); */
        $(".shopLink", getContext()).click(function () {
        	var shopId = $(this).data('shop-id');
        	$('#shopId', getContext()).val(shopId);
        	reloadRoomView({
                shopId: shopId
            });
        });
        function addBook($cell) {
            var currentDate = new Date($cell.data('time'));
            var param = {
                startAppointmentTime: currentDate.format('yyyy-MM-dd'),
                startTimeString : currentDate.format('hh:mm'),
                shopId: $('#shopId', getContext()).val(),
                forward:'bookTimeRoomViewMenu'
            };

            loadFromPage({
                url: '<c:url value="/book/toAdd"/>',
                data:param
            });
        }

        function editBook($cell) {
            var bookId = $cell.data('book-id');
            var param = {
                id: bookId,
                forward:'bookListMenu',
                status: "ROOMEDIT"
            };

            loadFromPage({
                url: '<c:url value="/book/toEdit"/>',
                data:param
            });
        }

        // book item check out
        function singleCheckOut(bookItemId) {
            var options = {
                width: 1000,
                title: "<spring:message code="label.button.check.out"/>",
                url: '<c:url value="/sales/bookItemToCheckout"/>',
                urlData: {
                    bookItemId: bookItemId
                },
                callback: function (selfDlg) {
                    reloadRoomView({});
                }
            };
            Dialog.create(options);
        }

        // book check out
        function multiCheckOut(bookId) {
            var options = {
                url: '<c:url value="/sales/bookToCheckout"/>',
                data: {
                    bookId: bookId,
                    forward: 'bookTimeTherapistViewMenu'
                }
            };
            loadFromPage(options);
        }

        function updateBookItem(container, target) {
            var urlData = {
                bookItemId: target.data('book-item-id'),
                appointmentTimeStamp: container.data('time'),
                roomId: container.data('room-id')
            };
            Dialog.create({
                width: 680,
                title: "<spring:message code="label.button.add"/>",
                url: '<c:url value="/book/toBookItemEdit"/>',
                urlData: urlData,
                callback: function (selfDlg) {
                    var timeStamp = selfDlg.$modal.find('#appointmentTimeStamp').val();
                    reloadRoomView({appointmentTimeStamp: timeStamp});
                }
            });
        }

        function addBlock($cell) {
            var param = {
                startTimeStamp: $cell.data('time'),
                roomId: $cell.data('room-id'),
                shopId: $('#shopId', getContext()).val()
            };
            Dialog.create({
                width: 680,
                title: "<spring:message code="label.book.add.room.block"/>",
                url: '<c:url value="/book/toBlockRoomAdd"/>',
                urlData: param,
                callback: function (selfDlg) {
                    reloadRoomView({appointmentTimeStamp: $cell.data('time')});
                }
            });
        }

        // 右键菜单
        $.contextMenu({
            selector: '#bookTimeRoomView div.cell',
            autoHide: true,
            trigger: 'left',
            callback: function (key, options) {
                switch (key) {
                    case 'roomViewAdd' :
                        addBook(options.$trigger);
                        //options.$trigger.trigger('click');
                        break;
                    case 'addBlock' :
                        addBlock(options.$trigger);
                        break;
                    default:
                        break;
                }
            },
            items: {
                "roomViewAdd": {name: "Add Booking", icon: "add"},
                "addBlock": {name: "Block Out", icon: "add"}
            }
        });

        $.contextMenu({
            selector: '#bookTimeRoomView div.book-item-box',
            autoHide: true,
            trigger: 'left',
            callback: function (key, options) {
                var currentStatus = options.$trigger.data('status');
                var checkResult = false;
                switch (key) {
                    case 'confirm' :
                        Dialog.confirm({
                            title: "<spring:message code="label.book.confirmed.dialog"/>",
                            message: "<spring:message code="label.book.change.confirmed.status"/>",
                            callback: function (res) {
                                if (res) {
                                    $.post('<c:url value="/book/updateBookItemStatus"/>?status=CONFIRM', {bookItemId: options.$trigger.data('book-item-id')}, function () {
                                        reloadRoomView({});
                                    });
                                }
                            }
                        });
                        break;
                    case 'checkin_service' :
                        checkResult = checkAction(currentStatus, 'CHECKIN_SERVICE');
                        if (checkResult.length > 1) {
                            Dialog.alert({
                                title: "<spring:message code="lable.error"/>",
                                message: checkResult
                            });
                            break;
                        }

                        Dialog.confirm({
                            title: "<spring:message code="label.book.progress.dialog"/>",
                            message: "<spring:message code="label.book.change.progress.status"/>",
                            callback: function (res) {
                                if (res) {
                                    $.post('<c:url value="/book/updateBookItemStatus"/>?status=CHECKIN_SERVICE', {bookItemId: options.$trigger.data('book-item-id')}, function () {
                                        reloadRoomView({});
                                    });
                                }
                            }
                        });
                        break;
                    case 'not_show' :
                        checkResult = checkAction(currentStatus, 'NOT_SHOW');
                        if (checkResult.length > 1) {
                            Dialog.alert({
                                title: "<spring:message code="lable.error"/>",
                                message: checkResult
                            });
                            break;
                        }

                        Dialog.confirm({
                            title: "<spring:message code="label.book.no.show.dialog"/>",
                            message: "<spring:message code="label.book.change.Show.status"/>",
                            callback: function (res) {
                                if (res) {
                                    $.post('<c:url value="/book/updateBookItemStatus"/>?status=NOT_SHOW', {bookItemId: options.$trigger.data('book-item-id')}, function () {
                                        reloadRoomView({});
                                    });
                                }
                            }
                        });
                        break;
                    case 'cancel' :
                        checkResult = checkAction(currentStatus, 'CANCEL');
                        if (checkResult.length > 1) {
                            Dialog.alert({
                                title: "<spring:message code="lable.error"/>",
                                message: checkResult
                            });
                            break;
                        }

                        Dialog.confirm({
                            title: "<spring:message code="label.book.cancel.dialog"/>",
                            message: "<spring:message code="label.book.change.cancel.status"/>",
                            callback: function (res) {
                                if (res) {
                                    $.post('<c:url value="/book/updateBookItemStatus"/>?status=CANCEL', {bookItemId: options.$trigger.data('book-item-id')}, function () {
                                        reloadRoomView({});
                                    });
                                }
                            }
                        });
                        break;

                    case 'single_check_out' :
                        checkResult = checkAction(currentStatus, 'COMPLETE');
                        if (checkResult.length > 1) {
                            Dialog.alert({
                                title: "<spring:message code="lable.error"/>",
                                message: checkResult
                            });
                            break;
                        }

                        singleCheckOut(options.$trigger.data('book-item-id'));
                        break;
                    case 'multi_check_out' :
                        checkResult = checkAction(currentStatus, 'COMPLETE');
                        if (checkResult.length > 1) {
                            Dialog.alert({
                                title: "<spring:message code="lable.error"/>",
                                message: checkResult
                            });
                            break;
                        }

                        multiCheckOut(options.$trigger.data('book-id'));
                        break;
                    case 'room_edit' :
                        checkResult = checkAction(currentStatus, 'ROOMEDIT');
                        if (checkResult.length > 1) {
                            Dialog.alert({
                                title: "<spring:message code="lable.error"/>",
                                message: checkResult
                            });
                            break;
                        }

                        Dialog.confirm({
                            title: "<spring:message code="label.book.edit.dialog"/>",
                            message: "<spring:message code="label.book.edit"/>",
                            callback: function (res) {
                                if (res) {
                                    editBook(options.$trigger);
                                }
                            }
                        });
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
                "room_edit": {name: "Eidt", icon: ""}
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
            selector: '#bookTimeRoomView div.time-box',
            autoHide: true,
            trigger: 'left',
            callback: function (key, options) {
                var currentTimeStamp = options.$trigger.data('time');
                var param = {
                    startTimeStamp: currentTimeStamp,
                    id: options.$trigger.data('block-id'),
                    updateType: 'ONCE'
                };
                switch (key) {
                    case 'edit' :
                        Dialog.create({
                            title: "<spring:message code="label.book.edit.block"/>",
                            url: '<c:url value="/book/toBlockRoomUpdate"/>',
                            urlData: param,
                            callback: function () {
                                reloadRoomView({appointmentTimeStamp: currentTimeStamp});
                            }
                        });
                        break;
                    case 'cancel' :
                        Dialog.confirm({
                            title: "<spring:message code="label.book.cancel.dialog"/> ",
                            message: "<spring:message code="label.book.cancel.this.block"/> ",
                            callback: function (res) {
                                if (res) {
                                    $.post('<c:url value="/book/removeBlock"/>', param, function (json) {
                                        if(json.statusCode == 401) {
                                            Dialog.alert({
                                                title: "<spring:message code="label.book.access.denied"/>",
                                                message: json.message
                                            });
                                        } else {
                                            reloadRoomView({appointmentTimeStamp: currentTimeStamp});
                                        }

                                    }, 'json');
                                }
                            }
                        });
                        break;
                }
            },
            items: {
                "edit": {name: "Edit"},
                "cancel": {name: "Cancel", icon: "delete"}
            }
        });


        <c:if test="${not empty error}">
        Dialog.alert({
            title: "<spring:message code="lable.error"/>",
            message: "${error}"
        });
        </c:if>

        // load waiting list
        $('#waitingList', getContext()).load('<c:url value="/book/bookItemWaitingList"/>', {
            startTime: '${startDate}',
            shopId: $('#shopId', getContext()).val()
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
                        overflow:'hidden'
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
        var bookTimeRoomViewInterval;
        var startDateTimeMillis = ${startDateTime.millis};
        var calendarContent = $('.calendar-content', getContext());
        var calendarHeight = calendarContent[0].scrollHeight + 2;
        var heightPerMillisSecond = calendarHeight / ( ${endDateTime.millis} - startDateTimeMillis);
        $('.time-line', calendarContent).removeClass('hide').width(${roomList.size() * cellWidth} + ${timeCellWidth});

        // 显示时间线
        function changeTimeLine() {
            var position = heightPerMillisSecond * (new Date().getTime() - startDateTimeMillis);
            $('.time-line', calendarContent).css({top: position});
        }

        changeTimeLine();
        // 删除之前的
        if(!bookTimeRoomViewInterval) {
            bookTimeRoomViewInterval = setInterval(changeTimeLine, 300000); // 五分钟更新一次
        }
        </c:when>
        <c:otherwise>
        $('.time-line', $('.calendar-content', getContext())).addClass('hide');
        </c:otherwise>
        </c:choose>
    });
</script>