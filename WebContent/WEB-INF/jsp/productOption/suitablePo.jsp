<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<c:if test="${productOptionList !=null && productOptionList.size() >0 }">
    <div>
        <c:forEach var="productOption" items="${productOptionList }">
            <div data-po-id="${productOption.id}">
                <span>${productOption.code}</span>
                <span>${productOption.product.name}</span>
                <span>${productOption.duration}</span>
                <span>${productOption.price}</span>
                <span>${productOption.processTime}</span>
            </div>
        </c:forEach>
    </div>
</c:if>