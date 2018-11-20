<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<%@page import="org.spa.vo.page.Page" %>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<c:if test="${page.totalRecords eq 0 }">
    <div class="empty_document_listing"><spring:message code="lable.page.no.records"/></div>
</c:if>

<c:if test="${page.totalRecords gt 0}">
    <div class="page">
        <span class="totalRecords">${page.totalRecords} </span> <spring:message code="lable.page.records"/>.
        <input type="hidden" id="curQueryString" value='<%=URLDecoder.decode(request.getAttribute("curQueryString").toString(), "utf-8") %>'/>
            <%-- 上一页 --%>
        <c:if test="${page.pageNumber==1 && page.totalPages!=0}">
            <a class="current prev prevNext" href="javascript:;"><i class="glyphicon glyphicon-chevron-left"></i></a>
        </c:if>
        <c:if test="${page.pageNumber!=1 && page.totalPages!=0}">
            <a class="prevactive page-link" href="javascript:;" data-page-no="${page.pageNumber - 1}">
                <i class="glyphicon glyphicon-chevron-left"></i></a>
        </c:if>
        <div>

            <c:choose>
                <c:when test="${page.totalPages gt 7}">
                    <%
                        // 计算显示的页数
                        Page pageObj = (Page) request.getAttribute("page");
                        int pageNumber = pageObj.getPageNumber();
                        int totalPages = pageObj.getTotalPages();

                        List<Integer> pageArray = new ArrayList<>();
                        int startPageNumber = pageNumber - 2;
                        int endPageNumber = pageNumber + 2;
                        if (startPageNumber <= 0) {
                            int step = 0 - startPageNumber + 1;
                            startPageNumber = 1;
                            endPageNumber += step;

                        } else if (endPageNumber >= totalPages) {
                            int step = endPageNumber - totalPages + 1;
                            startPageNumber -= step;
                            endPageNumber = totalPages;
                        }

                        for (int i = startPageNumber; i <= endPageNumber; i++) {
                            pageArray.add(i);
                        }
                        if (!pageArray.contains(1)) {
                            if (!pageArray.contains(2)) {
                                pageArray.add(0, 0);
                            }
                            pageArray.add(0, 1);

                        } else {
                            // 加多一个
                            pageArray.add(pageArray.get(pageArray.size() - 1) + 1);
                        }
                        if (!pageArray.contains(totalPages)) {
                            if (!pageArray.contains(totalPages - 1)) {
                                pageArray.add(0);
                            }
                            pageArray.add(totalPages);
                        }
                        pageContext.setAttribute("pageArray", pageArray);
                    %>
                    <c:forEach var="pageNo" items="${pageArray}">
                        <c:choose>
                            <c:when test="${page.pageNumber eq pageNo}">
                                <a class="current active">${pageNo}</a>
                            </c:when>
                            <c:when test="${pageNo eq 0}">
                                <span class="ellipse">....&nbsp;</span>
                            </c:when>
                            <c:otherwise>
                                <a class="page-link" href="javascript:;" data-page-no="${pageNo}">${pageNo}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <c:forEach var="pageNo" begin="1" end="${page.totalPages}">
                        <c:choose>
                            <c:when test="${page.pageNumber eq pageNo}">
                                <a class="current active">${pageNo}</a>
                            </c:when>
                            <c:otherwise>
                                <a class="page-link" href="javascript:;" data-page-no="${pageNo}">${pageNo}</a>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>

            <%-- 下一页 --%>
        <c:if test="${page.pageNumber==page.totalPages && page.totalPages!=0}">
            <a class="current next prevNext" href="javascript:;"><i class="glyphicon glyphicon-chevron-right"></i></a>
        </c:if>
        <c:if test="${page.pageNumber!=page.totalPages && page.totalPages!=0}">
            <a class="current nextactive page-link" href="javascript:;" data-page-no="${page.pageNumber + 1}">
                <i class="glyphicon glyphicon-chevron-right"></i></a>
        </c:if>
        <input  type="number" value="${page.pageNumber}" name="inNumber" id="inNumber" max="${page.totalPages}" style="height: 24px;width: 50px"><spring:message code="lable.page.page"/>
        <a class="current nextactive" href="javascript:;" id="inNumberBtn" style="width: 50px">OK</a>
    </div>

    <script type="text/javascript">
        $("#inNumberBtn").click(function () {
            var inNumber=$("#inNumber").val();
            var totalPages =${page.totalPages};
            if(inNumber<=totalPages && inNumber!="" &&inNumber>0){
                Dialog.getContext().mask("Loading ... ");
                var curQueryString = $("#curQueryString", Dialog.getContext()).val();
                var obj = JSON.parse(curQueryString);
                obj.pageNumber = inNumber;
                $.ajax({
                    url: '${pageUrl}',
                    type: "post",
                    dataType: "text",
                    data: obj,
                    success: function (response) {
                        $("#pageList", Dialog.getContext()).html(response);
                    }
                }).always(function () {
                    Dialog.getContext().unmask();
                });
            }
        });
        $(function () {
            // 动态计算分页条的宽度和分页显示的内容的高度，超出的滚动显示

            var height;
            // 主页面打开的分页
            if (Dialog.getContext().attr('id') == 'right-content') {
                $('.page', Dialog.getContext()).width(Dialog.getContext().width()); // 计算page的宽度
                var prevElement = $('#pageList', Dialog.getContext()).prev();
                height = $(window).height() - Math.ceil(prevElement.offset().top) - prevElement.outerHeight() - 60;
                $('#pageList', Dialog.getContext()).height(height);
            } else {
                // 这个是对话框打开的分页
                $('.page', Dialog.getContext()).width(Dialog.getContext().width()); // 计算page的宽度
                var modalBody = Dialog.getContext().find('.modal-body');
                height = $(window).height() - modalBody.position().top - 95;
                modalBody.height(height);
                $('#pageList', Dialog.getContext()).height(height - $('#pageList', Dialog.getContext()).position().top - 65);
            }

            $('a.page-link', Dialog.getContext()).click(function () {
            	Dialog.getContext().mask("Loading ... ");
                var curQueryString = $("#curQueryString", Dialog.getContext()).val();
                var obj = JSON.parse(curQueryString);
                obj.pageNumber = $(this).data('page-no');
                $.ajax({
                    url: '${pageUrl}',
                    type: "post",
                    dataType: "text",
                    data: obj,
                    success: function (response) {
                        $("#pageList", Dialog.getContext()).html(response);
                    }
                }).always(function () {
                	Dialog.getContext().unmask();
                });
            });
        });
    </script>
</c:if>
