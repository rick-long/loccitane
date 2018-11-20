<%@page import="org.spa.vo.sales.OrderItemVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
	
				<%
					Long shopId=(Long)request.getAttribute("shopId");
					%>
	<c:if test="${itemVOs !=null}">
		<c:forEach items="${itemVOs}" var="item" varStatus="idx">
			<c:if test="${item.productOptionId !=null}">
				<tr data-id="${item.productOptionId}">
					<td>
						<c:if test="${item.startTime !=null}">
							${item.startTime}
							<input type="hidden" name="itemVOs[${idx.index+1}].startTime" id="itemVOs[${idx.index+1}].startTime" value="${item.startTime}" data-product-option-id="${item.productOptionId}"/>
						</c:if>
						<c:if test="${item.startTime ==null }">
							N/A
						</c:if>
						<input type="hidden" name="itemVOs[${idx.index+1}].bookItemId" id="itemVOs[${idx.index+1}].bookItemId" value="${item.bookItemId}" data-product-option-id="${item.productOptionId}"/>
					</td>
					<td>
						${item.productDisplayName}
						<input type="hidden" name="itemVOs[${idx.index+1}].productOptionId" id="itemVOs[${idx.index+1}].productOptionId" value="${item.productOptionId}" data-product-option-id="${item.productOptionId}"/>
					</td>
					<td>
						${item.therapistNames}
						<c:forEach items="${item.therapists}" var="therapist" varStatus="theIdx">
							<c:if test="${therapist.key !=null }">
				    			<div>
							       <input type="hidden" name="itemVOs[${idx.index+1}].therapists[${theIdx.index+1}].key" value="${therapist.key}"/>
							       <input type="hidden" name="itemVOs[${idx.index+1}].therapists[${theIdx.index+1}].staffId" value="${therapist.staffId}"/>
							       <input type="hidden" name="itemVOs[${idx.index+1}].therapists[${theIdx.index+1}].requested" value="${therapist.requested}"/>
								</div>
							</c:if>
						</c:forEach>
					</td>
					<td>
					<%
					OrderItemVO vo=(OrderItemVO) pageContext.getAttribute("item");
					Double finalPrice=vo.getProductOption().getFinalPrice(shopId);
					
					%>
						<%=finalPrice %>
					</td>
					<td>
						${item.qty}
						<input type="hidden" name="itemVOs[${idx.index+1}].qty" id="itemVOs[${idx.index+1}].qty" value="${item.qty}" data-product-option-id="${item.productOptionId}"/>
					</td>
					<td>
						${item.totalDiscount}
						<input type="hidden" name="itemVOs[${idx.index+1}].extraDiscount" id="itemVOs[${idx.index+1}].extraDiscount" value="${item.extraDiscount}" data-product-option-id="${item.productOptionId}"/>
					</td>
					<td>
						${item.amount}
					</td>
					<td>
						<c:if test="${!item.productOption.isGoods && item.suitablePackages !=null }">
							<select class="selectpicker" name="itemVOs[${idx.index+1}].paidByPackageId" id="itemVOs[${idx.index+1}].paidByPackageId" data-product-option-id="${item.productOptionId}">
				            	<option value=""><spring:message code="label.option.select.single"/></option>
					            <c:forEach var="prepaid" items="${item.suitablePackages }">
					            	<option value="${prepaid.id }">${prepaid.name }(${prepaid.remainValue})</option>
					            </c:forEach>
					        </select>
				        </c:if>
					</td>
					<td>
						<c:if test="${!item.productOption.isGoods}">
							<input type="text" name="itemVOs[${idx.index+1}].paidByVoucherRef" id="itemVOs[${idx.index+1}].paidByVoucherRef" value="" data-product-option-id="${item.productOptionId}"/>
						</c:if>
					</td>
					<td><button class="btn btn-warning removeItemBtn" data-product-option-id="${item.productOptionId}">Remove</button></td>
				</tr>
			</c:if>
		</c:forEach>
	</c:if>