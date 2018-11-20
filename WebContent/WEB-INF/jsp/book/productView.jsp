<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp"%>
  <div class="container-fluid">
   <div class="row lo-m-t-15">
      <div class="col-md-5  col-xs-12 text-right"><strong><spring:message code="label.category"/> : </strong></div>
      <div class="col-md-7 col-xs-12">${productOption.product.category.fullName}</div>
    </div>
     <div class="row lo-m-t-15">
      <div class="col-md-5 col-xs-12  text-right"><strong><spring:message code="label.name"/> : </strong></div>
      <div class="col-md-7 col-xs-12">${productOption.product.name}</div>
     </div>
      <div class="row lo-m-t-15">
      <div class="col-md-5 col-xs-12 text-right"><strong><spring:message code="label.product.option"/> :</strong> </div>
      <div class="col-md-7 col-xs-12">${productOption.getLabel4()}</div>
     </div>
      <div class="row lo-m-t-15">
      <div class="col-md-5 col-xs-12 text-right"><strong>${shop.name} : </strong></div>
      <div class="col-md-7 col-xs-12">${productOptionSupernumeraryPrice.additionalPrice}</div>
     </div>
  </div>