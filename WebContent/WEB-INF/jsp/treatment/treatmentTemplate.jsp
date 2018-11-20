<%@ page import="java.io.File" %>
<%@ page import="com.spa.controller.product.TreatmentController" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
<html>
<body>

<table class="table table-striped">
    <c:if test="${!empty category.sortedChilden}">
        <c:forEach items="${category.sortedChilden}" var="cs">
            <tr>
                <td>
                    ${cs.name}
                </td>
                <td>
                    <c:if test="${!empty cs.sortedChilden}">
                        <c:forEach items="${cs.sortedChilden}" var="css">
                            <p>
                                    ${css.name}
                            </p>
                        </c:forEach>
                    </c:if>
                </td>
                <td>
                    <c:if test="${!empty cs.sortedProducts}">
                        <c:forEach items="${cs.sortedProducts}" var="csp">

                            <c:forEach items="${csp.sortedProductOptions}" var="cspo">
                                <td>${cspo.label33}
                                    <img src="${paths}${cspo.barcode}.png" height="200" width="200"/>
                                </td>
                            </c:forEach>
                        </c:forEach>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
    </c:if>

</table>

</body>
</html>
