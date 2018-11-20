<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
    <style>
    .form-horizontal .control-label{
        padding-top:unset !important;
        text-align:left;

    }
    .backValue{
        text-align:left;
    }
    </style>
<div class="row new-row-width">
    <fieldset>
        <legend><spring:message code="label.treatment.details"/></legend>
        <div class="form-group">
            <label class="col-lg-2 control-label"><spring:message code="label.name"/></label>
            <div class="col-lg-10 backValue">${productVO.name}</div>
        </div><%-- 
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.brand"/></label>
            <div class="col-lg-5">
                ${brand.name}
            </div>
        </div>
        <div class="form-group">
            <label class="col-lg-4 control-label"><spring:message code="label.supplier"/></label>
            <div class="col-lg-5">
                ${supplier.name}
            </div>
        </div> --%>
        <div class="form-group">
            <label class="col-lg-2 control-label"><spring:message code="label.category"/></label>
            <div class="col-lg-10">
                ${category.name}
            </div>
        </div>
    </fieldset>

    <c:if test="${pdkList !=null && pdkList.size() > 0 }">
        <fieldset>
           <legend>Treatment Descriptions</legend>
            <c:forEach items="${pdkList}" var="pdk" varStatus="idx">
                <div class="form-group">
                    <label class="col-lg-4 control-label">${pdk.name}</label>
                    <div class="col-lg-5">
                        <c:if test="${pdk.id eq productVO.pdvalues.get(idx.index).key}">
                            ${productVO.pdvalues.get(idx.index).value}
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </fieldset>
    </c:if>

    <c:if test="${pokList != null && pokList.size()>0}">
        <fieldset>
            <legend>Treatment Options</legend>
            <table class="table table-striped">
                <thead>
                <tr>
                    <c:forEach items="${pokList}" var="pok" varStatus="idx">
                        <th>${pok.name}</th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${productVO.poItemList}" var="poItem">
                    <tr>
                        <c:forEach items="${pokList}" var="pok">
                            <td>
                                <c:forEach items="${poItem.poValues}" var="poValue">
                                    <c:if test="${pok.id eq poValue.key }">
                                        ${poValue.value}
                                    </c:if>
                                </c:forEach>
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </fieldset>
    </c:if>
</div>


