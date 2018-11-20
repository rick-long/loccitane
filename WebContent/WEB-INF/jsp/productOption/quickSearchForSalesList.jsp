<%@page import="org.spa.model.product.ProductOption"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<table class="table table-striped">
    <thead>
    <tr>
        <th><spring:message code="label.name"/></th>
        <th><spring:message code="label.default.price"/></th>
        <th><spring:message code="label.inventory"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    	<%
	        	Long shopId=(Long)request.getAttribute("shopId");
    	%>
    	<c:forEach items="${page.list}" var="item" varStatus="idx">
        <tr data-id="${item.id}">
        	<td class="hide">
        		<c:choose>
	        		<c:when test="${item.product.category.category.reference eq 'CA-GOODS'}">
	        			<spring:message code="label.gender.UNKNOWN"/>
	        		</c:when>
	        		<c:otherwise>
	        			<input name="itemVOs[${idx.index+1}].startTime" id="itemVOs[${idx.index+1}].startTime" class="time-picker form-control" data-product-option-id="${item.id}" readonly/>
	        		</c:otherwise>
        		</c:choose>
            </td>
            <td>
            	${item.label2}
            	<input type="hidden" name="itemVOs[${idx.index+1}].productOptionId" id="itemVOs[${idx.index+1}].productOptionId" value="${item.id}" data-product-option-id="${item.id}"/>
            </td>
            <%
    			ProductOption po=(ProductOption)pageContext.getAttribute("item");
    			Double finalPrice=po.getFinalPrice(shopId);
    			pageContext.setAttribute("finalPrice", finalPrice);
	        %>
            <td>
            	${finalPrice}
            	<input type="hidden" name="itemVOs[${idx.index+1}].price" id="itemVOs[${idx.index+1}].price" value="${finalPrice}" data-product-option-id="${item.id}"/>
            </td>
            <td class="inventory">
            	<c:choose>
	        		<c:when test="${item.isGoods}">
	        			<%
	        			
	        			Integer qty=po.getInventoryQtyByShop(shopId);
	        			if(qty !=null && qty.longValue()==0){}else{
	        				qty=0;
	        				pageContext.setAttribute("noInventory", "true");
	        			}
	        			%>
	        			<%=qty %>
	        		</c:when>
	        		<c:otherwise>
	        			
	        		</c:otherwise>
        		</c:choose>
            </td>
            <td class="hide">
            	<select class="selectpicker form-control quantity" name="itemVOs[${idx.index+1}].qty" id="itemVOs[${idx.index+1}].qty" data-product-option-id="${item.id}">
		    		<option value="1">1</option><option value="2">2</option><option value="3">3</option>
		    		<option value="4">4</option><option value="5">5</option><option value="6">6</option>
		    		<option value="7">7</option><option value="8">8</option><option value="9">9</option>
		    		<option value="10">10</option>
		        </select>
            </td>
            <c:forEach var="numTherapist"  begin="1" end="${numberOfTherapistUsed}">
		    	 <td class="hide therapist${numTherapist}">
            		<select class="selectpicker form-control" name="itemVOs[${idx.index+1}].therapists[${numTherapist }].staffId" id="itemVOs[${idx.index+1}].therapists[${numTherapist }].staffId" data-product-option-id="${item.id}">
			    		<c:if test="${numTherapist != 1 }">
		            		<option value=""><spring:message code="label.option.select.single"/></option>
		            	</c:if>
			            <c:forEach var="therapistObj" items="${therapistList }">
			            	<option value="${therapistObj.id }">${therapistObj.username }</option>
			            </c:forEach>
			        </select>
			        <input type="hidden" name="itemVOs[${idx.index+1}].therapists[${numTherapist}].key" value="${numTherapist}" data-product-option-id="${item.id}"/>
			        <input type="checkbox" name="itemVOs[${idx.index+1}].therapists[${numTherapist}].requested" value="true" data-product-option-id="${item.id}"/>
            	 </td>
			</c:forEach>
			<td class="hide">
                <input type="text" class="form-control extradiscount" id="itemVOs[${idx.index+1}].extraDiscount" name="itemVOs[${idx.index+1}].extraDiscount" data-product-option-id="${item.id}">
            </td>
            <td>
            	<c:choose>
	        		<c:when test="${item.isGoods && noInventory == 'true'}">
	        			
	        		</c:when>
	        		<c:otherwise>
	        			<button class="btn btn-primary selectPOBtn" data-product-option-id="${item.id}">Select</button>
	        		</c:otherwise>
        		</c:choose>
                <button class="btn btn-warning removePOBtn hide" data-product-option-id="${item.id}">Remove</button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!-- page start -->
<c:url var="pageUrl" value="/po/quickSearchForSalesList" scope="request"/>
<c:import url="/WEB-INF/jsp/common/pagination.jsp"/>
<!-- page end -->