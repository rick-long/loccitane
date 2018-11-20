<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<script src='<c:url value="/resources/js/base/jquery.form.js"/>'></script>
<style>
    span#quickAddMember {
        width: 125px !important;

    }

    .orderDetails_management {
        padding-right: 23px
    }

    .shopId > .glyphicon-ok {
        right: -6px !important;
    }

    .client > i {
        right: -36px !important;
    }

    .Treatment{
        margin-right:20px;
        display:inline;
    }
</style>
<div class="orderDetails">
    <div class="orderDetails_management">
        <table class="clientDetails">
            <tr>
                <td class="col-lg-5"><spring:message code="label.purchase.date"/>*</td>
                <td>
                    <div class="input-group date form_time">
                        <input id="purchaseDateDisplay" name="purchaseDateDisplay" class="form-control purchaseDate"
                               value='<fmt:formatDate value="${book.appointmentTime}" pattern="yyyy-MM-dd"/>' size="16"
                               readonly>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-time"></span></span>
                    </div>
                </td>
            </tr>
            <tr>
                <td class="col-lg-5"><spring:message code="label.client"/>*</td>
                <td>
                    <div class="input-group dialog" data-url='<c:url value="/member/quicksearchWithEvent"/>'
                         data-title='<spring:message code="label.client.quick.search"/>'>
                        <c:set var="fullNameVal">
                            ${book.user.username}-${book.user.fullName}
                        </c:set>
                        <input type="text" name="fullName" id="fullName" value="${fullNameVal }"
                               class="form-control quick-search" readonly/>
                        <span class="input-group-addon">
	              		<span class="glyphicon glyphicon-search"></span>
	               	</span>
                    </div>
                    <a id="clientView" data-permission="book:clientView" onclick="clientView()"
                       title='<spring:message code="label.button.view"/>' class="btn btn-primary dialog btn-edit"
                       data-reload-btn="a.search-btn" data-width="950"
                       data-title='<spring:message code="label.button.view"/>'  style="display: inline;position:absolute;right:-7px;top:3px">
                        <i class="glyphicon glyphicon-eye-open"></i>
                    </a>
                    <span class="glyphicon glyphicon-pencil walkInGuest"><spring:message
                            code="label.client.walk.in.guest"/></span>

                    <%-- <spring:message code="label.sales.ishotelguest"/> --%> <input type="hidden"
                                                                                       name="isHotelGuestDisplay"
                                                                                       id="isHotelGuestDisplay"
                                                                                       value="false"/>
                </td>
            </tr>
            <tr>
                <td class="col-lg-5"><spring:message code="label.shop"/>*</td>
                <td>
                    <input type="hidden" name="shopIdDisplay" id="shopIdDisplay" class="form-control"
                           value="${book.shop.id }"/>
                    <input type="hidden" name="reference" id="reference" class="form-control"
                           value="${book.reference }"/>
                    <input type="text" class="form-control" value="${book.shop.name }" readonly>
                </td>
            </tr>
            <tr>
                <td class="col-lg-5"><spring:message code="label.remarks"/></td>
                <td>
                    <textarea name="remarksDisplay" id="remarksDisplay" class="form-control">${book.remarks }</textarea>
                    <spring:message code="label.sales.showremarksininvoice"/> <input type="checkbox"
                                                                                     name="showRemarksInInvoiceDisplay"
                                                                                     id="showRemarksInInvoiceDisplay"
                                                                                     value="false"/>
                </td>
            </tr>
            <%--<tr>
                <td class="col-lg-5"><span style="text-decoration:underline"> <b><spring:message
                        code="label.sales.additems"/> </b></span>
                </td>
                <td>
	 			    <span style="text-decoration:underline">
		                <a href='<c:url value="/prepaid/toAdd"/>' class="btn btn-primary dialog" data-draggable="true"
                        data-title='<spring:message code="label.prepaid.add.management"/>'>
			            <i class="glyphicon glyphicon-plus"></i>
			            <spring:message code="label.sales.addprepaid"/>
	                    </a>
	                </span>
                </td>
            </tr>--%>
        </table>


    </div>
    <div class="addItem" id="addItem">
        <ul class="nav nav-tabs" id="myTab">
            <li class="active">
                <a href="#booking"><spring:message code="left.navbar.book"/>
                    <span style="border-radius: 50%; height: 15px; width: 15px; display: inline-block; background: red; ">
						<span style="display: block; color: white; height: 15px; line-height: 15px; text-align: center">${commonBookItems.size()}</span>
					</span>
                </a>
            </li>
            <li><a href="#bundles"><spring:message code="label.bundle"/>
                <span style="border-radius: 50%; height: 15px; width: 15px; display: inline-block; background: red;">
						<span style="display: block; color: white; height: 15px; line-height: 15px; text-align: center">${bundles.size()}</span>
				</span></a></li>
            <li><a href="#goods"><spring:message code="label.sales.goods"/></a></li>
            <li><a href="#tips"><spring:message code="label.CATIPS"/></a></li>
        </ul>
        <div class="tab-content">
            <div id="booking" class="tab-pane fade in active">
                <form id="bookingItemForm" data-form-token="${TokenUtil.get(pageContext)}" method="post"
                      class="form-horizontal">
                    <table id="bookItemstable" class="table table-striped">
                        <tr>
                            <th><spring:message code="label.start.time"/>(<spring:message code="label.unit.mins"/>)</th>
                            <th><spring:message code="label.product"/></th>
                            <th><spring:message code="label.room"/></th>
                            <th><spring:message code="label.therapist"/></th>
                            <th><spring:message code="label.price"/></th>
                            <%-- <th><spring:message code="label.extra.discount" />(<spring:message code="label.unit.HKcurrency"/> )</th> --%>
                            <th><spring:message code="label.adjust.net.value"/> (<spring:message
                                    code="label.unit.HKcurrency"/>)
                            </th>

                            <th><spring:message code="label.sales.suitablepackage"/></th>
                            <th><spring:message code="label.sales.voucherno"/></th>
                            <th><spring:message code="label.status"/></th>
                            <th></th>
                        </tr>
                        <c:forEach var="bookItem" items="${commonBookItems}" varStatus="idx">
                            <tr>
                                <td>
                                    <input type="hidden" name="itemVOs[${idx.index}].idxId"
                                           id="itemVOs[${idx.index}].idxId" value="${idx.index}"/>

                                    <fmt:formatDate value="${bookItem.appointmentTime}"
                                                    pattern="HH:mm"/>[${bookItem.duration}]
                                    <input type="hidden" name="itemVOs[${idx.index}].bookItemId"
                                           id="itemVOs[${idx.index}].bookItemId" value="${bookItem.id}"/>
                                    <input type="hidden" name="itemVOs[${idx.index}].startTime"
                                           id="itemVOs[${idx.index}].startTime"
                                           value='<fmt:formatDate value="${bookItem.appointmentTime}" pattern="HH:mm"/>'/>
                                    <input type="hidden" name="itemVOs[${idx.index}].duration"
                                           id="itemVOs[${idx.index}].duration" value="${bookItem.duration}"/>
                                </td>
                                <td>
                                        ${bookItem.productOption.label3}
                                    <input type="hidden" name="itemVOs[${idx.index}].productOptionId"
                                           id="itemVOs[${idx.index}].productOptionId"
                                           value="${bookItem.productOption.id}"/>
                                    <a data-permission="book:productView"
                                       href='<c:url value="/book/productView?id=${bookItem.id}"/>'
                                       title='<spring:message code="label.button.view"/>'
                                       class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn"
                                       data-width="400" data-title='<spring:message code="label.button.view"/>'
                                       style="display: inline;">
                                        <i class="glyphicon glyphicon-eye-open"></i>
                                    </a>
                                </td>
                                <td>${bookItem.room.name}</td>
                                <td>
                                    <c:forEach items="${bookItem.bookItemTherapists}" var="bit" varStatus="tidx">
                                        ${bit.user.displayName} </br>
                                        <input type="hidden"
                                               name="itemVOs[${idx.index}].therapists[${tidx.index }].staffId"
                                               value="${bit.user.id }"/>
                                        <input type="hidden" name="itemVOs[${idx.index}].therapists[${tidx.index }].key"
                                               value="${tidx.index }"/>
                                        <input type="hidden"
                                               name="itemVOs[${idx.index}].therapists[${tidx.index }].requested"
                                               value="${bit.onRequest }"/>
                                    </c:forEach>
                                </td>
                                <td>
                                        ${bookItem.price}
                                    <input type="hidden" class="price" name="itemVOs[${idx.index}].price"
                                           id="itemVOs[${idx.index}].price" value="${bookItem.price}"/>
                                    <input type="hidden" class="form-control" id="itemVOs[${idx.index}].qty"
                                           name="itemVOs[${idx.index}].qty" value="1">
                                </td>
                                    <%-- <td>
                                      <input type="text" class="form-control extradiscount" id="itemVOs[${idx.index}].extraDiscount" value="" name="itemVOs[${idx.index}].extraDiscount" >
                                  </td> --%>

                                <td>
                                    <input type="text" class="form-control adjustNetValue"
                                           id="itemVOs[${idx.index}].adjustNetValue" value=""
                                           name="itemVOs[${idx.index}].adjustNetValue">
                                </td>
                                <td>
                                    <select class="selectpicker form-control suitabledPackages"
                                            name="itemVOs[${idx.index}].paidByPackageId"
                                            id="itemVOs[${idx.index}].paidByPackageId"
                                            data-product-option-id="${bookItem.productOption.id}"
                                            data-status-id="${bookItem.status}">

                                    </select>
                                </td>
                                <td>
                                    <input type="text" class="form-control"
                                           name="itemVOs[${idx.index}].paidByVoucherRef"
                                           id="itemVOs[${idx.index}].paidByVoucherRef" value="" size="15"/>
                                </td>
                                <td>${bookItem.status}</td>
                                <td>
                                    <c:if test="${bookItem.bookItemWhetherCanBeCheckout }">
                                        <button class="btn btn-primary addBookingItem"
                                                data-book-item-id="${bookItem.id}" data-idx-id="${idx.index}"
                                                data-status-id="${bookItem.status}">ADD ITEM
                                        </button>
                                    </c:if>
                                    <c:if test="${isNoShowStatus && !bookItem.bookItemWhetherCanBeCheckout}">
                                        <button class="btn btn-primary addBookingItem"
                                                data-book-item-id="${bookItem.id}" data-idx-id="${idx.index}"
                                                data-status-id="${bookItem.status}">ADD ITEM
                                        </button>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </form>
            </div>
            <div id="bundles" class="tab-pane fade">
                <form id="bundleItemForm" data-form-token="${TokenUtil.get(pageContext)}" method="post"
                      class="form-horizontal">
                    <table id="bundleItemstable" class="table table-striped">
                        <tr>
                            <th><spring:message code="label.start.time"/>(<spring:message code="label.unit.mins"/>)</th>
                            <th><spring:message code="label.product"/></th>
                            <th><spring:message code="label.room"/></th>
                            <th><spring:message code="label.therapist"/></th>
                            <th><spring:message code="label.price"/></th>
                            <th><spring:message code="label.status"/></th>
                            <th><spring:message code="label.bundle.amount"/></th>
                            <th></th>
                        </tr>
                        <c:forEach var="bundleMap" items="${bundles}" varStatus="keyIdx">
                            <c:set var="key" value="${bundleMap.key}"/>
                            <c:forEach var="bookItem" items="${bundleMap.value}" varStatus="valIdx">
                                <c:set var="idx"
                                       value="${commonBookItems.size() + keyIdx.index +keyIdx.index + valIdx.index}"/>
                                <tr data-bundle-id="${bookItem.bundleId}">
                                    <td>
                                        <input type="hidden" name="itemVOs[${idx}].idxId" id="itemVOs[${idx}].idxId"
                                               value="${idx}"/>

                                        <fmt:formatDate value="${bookItem.appointmentTime}"
                                                        pattern="HH:mm"/>[${bookItem.duration}]
                                        <input type="hidden" name="itemVOs[${idx}].bookItemId"
                                               id="itemVOs[${idx}].bookItemId" value="${bookItem.id}"/>
                                        <input type="hidden" name="itemVOs[${idx}].startTime"
                                               id="itemVOs[${idx}].startTime"
                                               value='<fmt:formatDate value="${bookItem.appointmentTime}" pattern="HH:mm"/>'/>
                                        <input type="hidden" name="itemVOs[${idx}].duration"
                                               id="itemVOs[${idx}].duration" value="${bookItem.duration}"/>
                                        <input type="hidden" name="itemVOs[${idx}].bundleId"
                                               id="itemVOs[${idx}].bundleId" value="${bookItem.bundleId}"/>
                                    </td>
                                    <td>
                                            ${bookItem.productOption.label3}
                                        <input type="hidden" name="itemVOs[${idx}].productOptionId"
                                               id="itemVOs[${idx}].productOptionId"
                                               value="${bookItem.productOption.id}"/>
                                        <a data-permission="book:productView"
                                           href='<c:url value="/book/productView?id=${bookItem.id}"/>'
                                           title='<spring:message code="label.button.view"/>'
                                           class="btn btn-primary dialog btn-edit" data-reload-btn="a.search-btn"
                                           data-width="400" data-title='<spring:message code="label.button.view"/>'
                                           style="display: inline;">
                                            <i class="glyphicon glyphicon-eye-open"></i>
                                        </a>
                                    </td>
                                    <td>${bookItem.room.name}</td>
                                    <td>
                                        <c:forEach items="${bookItem.bookItemTherapists}" var="bit" varStatus="tidx">
                                            ${bit.user.displayName} </br>
                                            <input type="hidden"
                                                   name="itemVOs[${idx}].therapists[${tidx.index }].staffId"
                                                   value="${bit.user.id }"/>
                                            <input type="hidden" name="itemVOs[${idx}].therapists[${tidx.index }].key"
                                                   value="${tidx.index }"/>
                                            <input type="hidden"
                                                   name="itemVOs[${idx}].therapists[${tidx.index }].requested"
                                                   value="${bit.onRequest }"/>
                                        </c:forEach>
                                    </td>
                                    <td>
                                            ${bookItem.price}
                                        <input type="hidden" class="price" name="itemVOs[${idx}].price"
                                               id="itemVOs[${idx}].price" value="${bookItem.price}"/>
                                        <input type="hidden" class="form-control" id="itemVOs[${idx}].qty"
                                               name="itemVOs[${idx}].qty" value="1">
                                    </td>
                                    <td>${bookItem.status}</td>
                                    <td class="">
                                        <c:if test="${valIdx.last }">
                                            <b><font color="red"><spring:message
                                                    code="label.unit.HKcurrency"/>${bundleMap['key']['bundleAmount']}</font></b>
                                        </c:if>
                                    </td>
                                    <td><c:if test="${valIdx.last }">
                                        <button class="btn btn-primary addBundleItem">ADD Bundle</button>
                                    </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </table>
                </form>
            </div>
            <%--<div id="goods" class="tab-pane fade">--%>
                <%--<form id="goodsItemForm" data-form-token="${TokenUtil.get(pageContext)}" method="post"--%>
                      <%--class="form-horizontal">--%>
                    <%--<table class="table table-striped">--%>
                        <%--<tr>--%>
                            <%--<th><spring:message code="left.navbar.product"/></th>--%>
                            <%--<th><spring:message code="label.qty"/></th>--%>
                            <%--<c:forEach var="numTherapist" begin="1" end="${numberOfTherapistUsed}">--%>
                                <%--<th><spring:message code="label.therapist"/> (${numTherapist})</th>--%>
                            <%--</c:forEach>--%>
                            <%--<th><spring:message code="label.adjust.net.value"/>(<spring:message--%>
                                    <%--code="label.unit.HKcurrency"/> )--%>
                            <%--</th>--%>
                            <%--<th><spring:message code="label.sales.suitablepackage"/></th>--%>
                            <%--<th><spring:message code="label.sales.fillvoucher"/></th>--%>
                            <%--<th></th>--%>
                        <%--</tr>--%>
                        <%--<tr>--%>
                            <%--<td>--%>
                                <%--<div class="select-category" data-selectable="option" data-root-id="3"--%>
                                     <%--data-root-id1="7"></div>--%>
                            <%--</td>--%>
                            <%--<td>--%>
                                <%--<select class="selectpicker form-control goodQty"--%>
                                        <%--name="itemVOs[${book.bookItems.size()}].qty">--%>
                                    <%--<option value="1">1</option>--%>
                                    <%--<option value="2">2</option>--%>
                                    <%--<option value="3">3</option>--%>
                                    <%--<option value="4">4</option>--%>
                                    <%--<option value="5">5</option>--%>
                                    <%--<option value="6">6</option>--%>
                                    <%--<option value="7">7</option>--%>
                                    <%--<option value="8">8</option>--%>
                                    <%--<option value="9">9</option>--%>
                                    <%--<option value="10">10</option>--%>
                                <%--</select>--%>
                            <%--</td>--%>
                            <%--<c:forEach var="numTherapist" begin="1" end="${numberOfTherapistUsed}">--%>
                                <%--<td>--%>
                                    <%--<select class="selectpicker form-control suitabledTherapists"--%>
                                            <%--name="itemVOs[${book.bookItems.size()}].therapists[${numTherapist }].staffId"--%>
                                            <%--style="    display: inline!important;--%>
    <%--width: 84%;--%>
    <%--vertical-align: middle;">--%>

                                    <%--</select>--%>
                                    <%--<input type="hidden"--%>
                                           <%--name="itemVOs[${book.bookItems.size()}].therapists[${numTherapist}].requested"--%>
                                           <%--value="false">--%>
                                    <%--<input type="hidden"--%>
                                           <%--name="itemVOs[${book.bookItems.size()}].therapists[${numTherapist}].key"--%>
                                           <%--value="${numTherapist}"/>--%>
                                <%--</td>--%>
                            <%--</c:forEach>--%>
                            <%--<td>--%>
                                <%--<input type="text" class="form-control adjustNetValue"--%>
                                       <%--id="itemVOs[${book.bookItems.size()}].adjustNetValue" value=""--%>
                                       <%--name="itemVOs[${book.bookItems.size()}].adjustNetValue">--%>
                            <%--</td>--%>
                            <%--<td>--%>
                                <%--<select class="selectpicker form-control suitabledPackages"--%>
                                        <%--name="itemVOs[${book.bookItems.size()}].paidByPackageId" id="paidByPackageId">--%>

                                <%--</select>--%>
                            <%--</td>--%>
                            <%--<td>--%>
                                <%--<input type="text" class="form-control"--%>
                                       <%--name="itemVOs[${book.bookItems.size()}].paidByVoucherRef" value="" size="15"/>--%>
                            <%--</td>--%>
                            <%--<td>--%>
                                <%--<button class="btn btn-primary" id="addGoodsItem"><spring:message--%>
                                        <%--code="label.add.item"/></button>--%>
                                <%--<input type="hidden" id="idxId" name="itemVOs[${book.bookItems.size()}].idxId"--%>
                                       <%--value="${book.bookItems.size()}">--%>
                            <%--</td>--%>
                        <%--</tr>--%>
                    <%--</table>--%>
                <%--</form>--%>
            <%--</div>--%>
            <%--<div id="tips" class="tab-pane fade content_in_details">--%>
                <%--<table id="tipsTable" class="TipsDetails">--%>
                    <%--<tr>--%>
                        <%--<td class="col-lg-3"><font color="red">*</font><spring:message code="label.amount"/></td>--%>
                        <%--<td>--%>
                            <%--<input id="tipsAmount" name="tipsAmount" class="form-control" value=""><font--%>
                                <%--color="red"></font>--%>
                        <%--</td>--%>
                        <%--<TD></TD>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<td class="col-lg-3"><spring:message code="label.sales.staff"/></td>--%>
                        <%--<td>--%>
                            <%--<select class="selectpicker form-control suitabledTherapists" id="tipsTherapist"--%>
                                    <%--name="tipsTherapist">--%>

                            <%--</select>--%>
                        <%--</td>--%>
                        <%--<TD></TD>--%>
                    <%--</tr>--%>
                    <%--<tr>--%>
                        <%--<TD></TD>--%>
                        <%--<TD>--%>
                            <%--<button class="btn btn-primary" id="addTipsItem" data-idx-id="0"><spring:message--%>
                                    <%--code="label.add.item"/></button>--%>
                        <%--</TD>--%>
                        <%--<td></td>--%>
                    <%--</tr>--%>
                <%--</table>--%>
            <%--</div>--%>
        <%--</div>--%>
    </div>

    <c:url var="url" value="/sales/checkout"/>
    <form:form modelAttribute="orderVO" id="orderForm"
               data-forward="${not empty orderVO.forward ? orderVO.forward : 'salesListMenu'}"
               data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal" action='${url }'>
        <table class="table table-striped" id="itemCart">
            <thead>
            <tr>
                <th class="col-lg-2"><span style="text-decoration:underline"><spring:message
                        code="label.sales.items"/> </span></th>
                <th class="col-lg-2"><span style="text-decoration:underline"><spring:message
                        code="label.sales.therapistcommi"/> </span></th>
                <th class="col-lg-1"><span style="text-decoration:underline"><spring:message code="label.qty"/> </span>
                </th>
                <th class="col-lg-1"><span style="text-decoration:underline"><spring:message
                        code="label.sales.unitcost"/> </span></th>
                <th class="col-lg-1"><span style="text-decoration:underline"><spring:message
                        code="label.sales.adjustment"/> </span></th>
                <th class="col-lg-2"><span style="text-decoration:underline"><spring:message
                        code="label.sales.prepaidpaidamount"/> </span></th>
                <th class="col-lg-2"><span style="text-decoration:underline"><spring:message
                        code="label.sales.finalamount"/> </span></th>
                <th class="col-lg-1"><span style="text-decoration:underline"></span></th>
                <th></th>
            </tr>
            </thead>
            <tbody>

            </tbody>
        </table>

        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="total_show">
            <tr>
                <td width="75%" class="total_text"><spring:message code="label.sales.totalamount"/> :</td>
                <td width="25%" class="totalamount_td"><spring:message code="label.unit.HKcurrency"/></td>
            </tr>
        </table>

        <div class="payment content_in_details">
            <c:forEach var="numPM" begin="1" end="${numberOfPMUsed}">
                <table border="0" cellspacing="0" cellpadding="0" class="checkout_table">
                    <tr>
                        <td class="col-lg-1"><label class="control-label"><spring:message
                                code="label.sales.pm"/>#${numPM}</label></td>
                        <td>
                            <div class="col-lg-5 margin-left">
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tr>
                                        <td class="col-lg-3">
                                            <form:select class="selectpicker form-control pm_select${numPM}"
                                                         path="paymentMethods[${numPM}].id">
                                                <c:if test="${numPM != 1 }">
                                                    <form:option value=""><spring:message
                                                            code="label.option.select.single"/></form:option>
                                                </c:if>
                                                <c:forEach var="pm" items="${pmList }">
                                                    <form:option value="${pm.id }">${pm.name }</form:option>
                                                </c:forEach>
                                            </form:select>
                                        </td>
                                        <td class="col-lg-2">
                                            <form:input path="paymentMethods[${numPM}].value" value=""
                                                        class="form-control paymentMethods${numPM }Val"/>
                                            <form:hidden path="paymentMethods[${numPM}].key" class="form-control"
                                                         value="${numPM }"/>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </td>
                    </tr>
                </table>
            </c:forEach>
        </div>

        <div class="print_invoice">
            <spring:message code="lable.sales.print.invoice"/>
            <input type="checkbox" name="printInvoiceCheckbox" id="printInvoiceCheckbox" value="false"/>
        </div>

        <div class="modal-footer content_in_details">
            <div class="bootstrap-dialog-footer">
                <div class="bootstrap-dialog-footer-buttons">
                    <input type="hidden" name="shopId" id="shopId" class="form-control" value="${book.shop.id }"/>
                    <input type="hidden" name="memberId" id="memberId" class="form-control" value="${book.user.id }"/>
                    <input type="hidden" name="username" id="username" class="form-control"
                           value="${book.user.username }"/>
                    <input type="hidden" name="purchaseDate" id="purchaseDate" class="form-control"
                           value='<fmt:formatDate value="${book.appointmentTime}" pattern="yyyy-MM-dd"/>'/>

                    <input type="hidden" name="remarks" id="remarks" class="form-control"/>
                    <input type="hidden" name="subTotal" id="subTotal" class="form-control"/>
                    <input type="hidden" name="showRemarksInInvoice" id="showRemarksInInvoice" class="form-control"/>
                    <input type="hidden" name="isHotelGuest" id="isHotelGuest" class="form-control"/>
                    <input type="hidden" name="printInvoice" id="printInvoice" class="form-control" value="false"/>
                    <input type="hidden" name="checkOutStatus" id="checkOutStatus" class="form-control"
                           value="${isNoShowStatus}"/>
                    <button type="button" class="btn btn-default formPageSubmit hide checkoutBtn"
                            data-skip-validate="true" id="submit">
                        <spring:message code="label.sales.checkout"/>
                    </button>
                </div>
            </div>
        </div>
    </form:form>

    <%--<div class="modal-footer content_in_details">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button style="float:left; margin-left: auto" class="btn btn-primary hide alipayBtn" id="alipay"
                        name="alipay" onclick="">
                    <spring:message code="label.sales.alipay"/>
                </button>
                <div class="col-lg-2 hide alipaydiv">
                    <input id="paycode" name="paycode" size="18" maxlength="18" class="form-control"/>
                    <span type="hidden" id="paycodeText" name="paycodeText"></span>
                </div>
            </div>
        </div>
    </div>--%>
</div>

<script type="text/javascript">

    $(document).ready(function () {
        if (${empty bundles}) {
            $('#myTab a:first').tab('show');//初始化显示哪个tab
        } else {
            $('#myTab li:eq(1) a').tab('show');//初始化显示哪个tab
        }

        /*$('#myTab a:first').tab('show');//初始化显示哪个tab*/

        $('#myTab a').click(function (e) {
            e.preventDefault();//阻止a链接的跳转行为
            $(this).tab('show');//显示当前选中的链接及关联的content
        });

        //shop
        $('#shopIdDisplay').change(function () {
            //therapist
            $.each($('.suitabledTherapists'), function (index) {
                var obj = $(this);
                var objName = obj.attr('name');
                if (index == 0 || objName == 'tipsTherapist') {
                    $.post('<c:url value="/staff/staffSelectList"/>', {shopId: $('#shopId').val()}, function (res) {
                        obj.html(res);
                    });
                } else {
                    $.post('<c:url value="/staff/staffSelectList"/>', {
                        shopId: $('#shopId').val(),
                        showAll: false
                    }, function (res) {
                        obj.html(res);
                    });
                }
            });
            //clear cart item
            $('#itemCart tbody').find('tr').each(function () {
                $(this).remove();
            });
        }).trigger('change');

        //walkin guest
        $('.walkInGuest').click(function () {
            $.ajax({
                url: '<c:url value="/member/walkInGuest"/>',
                type: "POST",
                dataType: "json",
                success: function (data) {
                    if (data.errorFields.length > 0) {
                        $.each(data.errorFields, function (index, item) {
                            if (item.fieldName == 'username') {
                                $('#username').attr("value", item.errorMessage);
                                $('#fullName').attr("value", item.errorMessage);
                            }
                            if (item.fieldName == 'id') {
                                $('#memberId').attr("value", item.errorMessage);
                            }
                            getSuitablePackages();
                        });
                    }
                }
            });
        });

        //init product option
        $('.select-category').selectCategory({});

        $('#purchaseDateDisplay').datetimepicker({
            format: 'Y-m-d',
            timepicker: false
        });
        $('.input-group-addon').click(function () {
            var input = $(this).siblings('input').trigger('focus');
        });

        //packages init
        getSuitablePackages();

        //add tips to item
        $('#addTipsItem').on('click', function (event) {
            event.preventDefault();// 禁止a连接的href事件

            clearEorroMsg();
            getContext().mask('Loading ... ');
            var idxVal = $('#addTipsItem').data('idx-id');
            if ($('input[name="tipsAmount"]', $('#tipsTable')).filter(function () {
                return !this.value;
            }).length > 0) {
                Dialog.alert({
                    title: "<spring:message code="lable.error"/>",
                    message: "<spring:message code="label.sales.please.input.amount"/>"
                });
                return;
            }
            $.ajax({
                url: '<c:url value="/sales/addTipsItems?idxId="/>' + idxVal + "&amount=" + $('#tipsAmount').val() + "&staffId=" + $('#tipsTherapist').val(),
                type: "POST",
                dataType: "text",
                success: function (response) {
                    $("#itemCart tbody").append(response);
                    $(".checkoutBtn").removeClass("hide");
                    $(".alipayBtn").removeClass("hide");

                    //clear
                    var idx = parseInt(idxVal);
                    idx++;
                    $('#tipsTable').find('input[name^="tipsItemVOs"]').each(function () {
                        replaceName(this, idx);
                    });
                    $('#idxId', $('#tipsTable')).attr('value', idx);

                    calTotalAmount();
                }
            }).always(function () {
                getContext().unmask();
            });
        });

        //add goods to item
        $('#addGoodsItem').on('click', function (event) {
            event.preventDefault();// 禁止a连接的href事件

            clearEorroMsg();
            getContext().mask('Loading ... ');
            var form = $('#goodsItemForm');

            //check product
            if ($('input[name="productOptionId"]', form).filter(function () {
                return !this.value;
            }).length > 0) {
                Dialog.alert({
                    title: "<spring:message code="lable.error"/>",
                    message: "<spring:message code="label.sales.please.select.product"/>"
                });
                return;
            }
            var qty = parseInt($('.goodQty').val());
            // check inventory
            $.ajax({
                url: '<c:url value="/product/checkInventory?productOptionId="/>' + $('input[name="productOptionId"]', form).val() + "&shopId=" + $('#shopIdDisplay').val(),
                type: "POST",
                dataType: "json",
                success: function (data) {
                    $(".alipayBtn").removeClass("hide");
                    if (data > 0 && (data - qty) >= 0) {
                        addGoodsItem(form);
                    } else {
                        BootstrapDialog.alert('<spring:message code="label.sales.message"/> ' + data + '.');
                    }
                }
            }).always(function () {
                getContext().unmask();
            });

        });
        //add bookings to item
        $.each($('.addBookingItem'), function () {
            $(this).on('click', function (event) {
                event.preventDefault();// 禁止a连接的href事件

                clearEorroMsg();
                getContext().mask('Loading ... ');
                var form = $('#bookingItemForm');
                var addToCartBtn = $(this);
                $.ajax({
                    url: '<c:url value="/sales/addCartItems?idxId="/>' + addToCartBtn.data('idx-id') + "&shopId=" + $('#shopIdDisplay').val() + "&memberId=" + $('#memberId').val() + "&purchaseDate=" + $('.purchaseDate').val(),
                    type: "POST",
                    dataType: "text",
                    data: form.serialize(),
                    success: function (response) {
                        $("#itemCart tbody").append(response);

                        var validS = $('#validSuccess').val();
                        if (validS == 'false') {
                        } else {
                            $(".checkoutBtn").removeClass("hide");
                            $(".alipayBtn").removeClass("hide");

                            addToCartBtn.addClass('hide');
                            calTotalAmount();
                        }
                    }
                }).always(function () {
                    getContext().unmask();
                });
            });
        });

        //add bundle to item
        $.each($('.addBundleItem'), function () {
            $(this).on('click', function (event) {
                event.preventDefault();// 禁止a连接的href事件

                clearEorroMsg();
                getContext().mask('Loading ... ');
                var form = $('#bundleItemForm');
                var addToCartBtn = $(this);
                $.ajax({
                    url: '<c:url value="/sales/addBundleCartItems?shopId="/>' + $('#shopIdDisplay').val() + "&memberId=" + $('#memberId').val() + "&purchaseDate=" + $('.purchaseDate').val(),
                    type: "POST",
                    dataType: "text",
                    data: form.serialize(),
                    success: function (response) {
                        $("#itemCart tbody").append(response);

                        var validS = $('#validSuccess').val();
                        if (validS == 'false') {
                        } else {
                            $(".checkoutBtn").removeClass("hide");
                            $(".alipayBtn").removeClass("hide");

                            addToCartBtn.addClass('hide');
                            calTotalAmount();
                        }
                    }
                }).always(function () {
                    getContext().unmask();
                });
            });
        });
        // cart items remove button
        $('#itemCart').on('click', 'button.removeItemBtn', function () {
            var idxIdRB = $(this).data('idx-id');
            var prepaidPaidAmt = $('#prepaidPaidAmt_' + idxIdRB).val();
            var url;

            var packageId = $('#itemVOs\\[' + idxIdRB + '\\]\\.paidByPackageId').val();
            var voucherRef = $('#itemVOs\\[' + idxIdRB + '\\]\\.paidByVoucherRef').val();
            if (packageId != '') {
                url = '<c:url value="/sales/removeItem?packageId="/>' + packageId + '&prepaidPaidAmt=' + prepaidPaidAmt;
            }
            if (voucherRef != '') {
                url = '<c:url value="/sales/removeItem?voucherRef="/>' + voucherRef + '&prepaidPaidAmt=' + prepaidPaidAmt;
            }

            $.ajax({
                url: url,
                type: "POST",
                dataType: "json",
                success: function (data) {
                    //
                }
            });

            $(this).parents('tr').remove();
            calTotalAmount();
            var idxId = $(this).data("book-item-id");
            $.each($('.addBookingItem'), function () {
                var currentBookItemId = $(this).data('book-item-id');
                if (currentBookItemId != null && idxId == currentBookItemId) {
                    $(this).removeClass('hide');
                }
            });
        });

        $('#itemCart').on('click', 'button.removeBundleItemBtn', function () {
            var bundleId = $(this).parents('tr').data("bundle-id");
            alert(bundleId);
            $('#itemCart').find('tr').each(function (index, item) {
                var $item = $(item);
                var trBundleId = $item.data("bundle-id");
                if (trBundleId == bundleId) {
                    $item.remove();
                }
            });
            calTotalAmount();
            $('#bundleItemstable').find('tr').each(function (index, item) {
                var $item = $(item);
                var trBundleId = $item.data("bundle-id");
                if (trBundleId == bundleId) {
                    $item.find('button.addBundleItem').removeClass('hide');
                }
            });
        });


        $('.alipayBtn').click(function () {
            $(".alipaydiv").removeClass("hide");

        });

        $("#printInvoiceCheckbox").change(function () {
            var flag = $("#printInvoiceCheckbox").is(":checked");
            $('#printInvoice').attr('value', flag);
        });

    });

    function clearEorroMsg() {
        $('#itemCart').find('.error_msg').each(function () {
            this.remove();
        });
    }

    function addGoodsItem(form) {
        getContext().mask('Loading ... ');
        var html = [];
        var idxVal = $('#idxId', form).val();
        html.push(String.format('<input type="hidden" class="productOptionIdClass" name="itemVOs[{0}].productOptionId" value="{1}"/>', idxVal, $('#productOptionId', form).val()));
        $(html.join('')).appendTo(form);
        $.ajax({
            url: '<c:url value="/sales/addCartItems?idxId="/>' + idxVal + "&shopId=" + $('#shopId').val() + "&memberId=" + $('#memberId').val(),
            type: "POST",
            dataType: "text",
            data: form.serialize(),
            success: function (response) {
                $("#itemCart tbody").append(response);
                $(".checkoutBtn").removeClass("hide");
                //clear
                var idx = parseInt(idxVal);
                idx++;
                form.find('input[name^="itemVOs"]', form).each(function () {
                    replaceName(this, idx);
                });
                form.find('select[name^="itemVOs"]', form).each(function () {
                    replaceName(this, idx);
                });
                $('#idxId', form).attr('value', idx);
                $('.productOptionIdClass', form).remove();
                //fill the payment value by auto
                calTotalAmount();
            }
        });
    }

    function replaceName(obj, idx) {
        var inputName = $(obj).attr('name');
        var inpStrs = inputName.split(".");
        var replaceName;
        if (inpStrs.length == 3) {
            replaceName = "itemVOs[" + idx + "]." + inpStrs[1] + "." + inpStrs[2];
        } else {
            replaceName = "itemVOs[" + idx + "]." + inpStrs[1];
        }
        $(obj).attr('name', replaceName);
    }

    function calTotalAmount() {
        var totalAmount = 0;
        $('#itemCart').find('.finalAmount').each(function () {
            var val = $(this).data('val');
            totalAmount = parseFloat(totalAmount) + parseFloat(val);
        });
        $('.paymentMethods1Val').attr('value', totalAmount);
        $('.totalamount_td').html('HK$' + totalAmount);
        $('#subTotal').attr('value', totalAmount);

        if (totalAmount == '0') {
            $(".pm_select1").val(22);//no payment id;
        } else {
            $(".pm_select1").val(18);//cash id
        }
    }

    function getSuitablePackages() {
        $.each($('.suitabledPackages'), function () {
            var obj = $(this);
            $.post('<c:url value="/prepaid/suitabledPackagesSelect"/>', {
                memberId: $('#memberId').val(),
                productOptionId: obj.data('product-option-id')
            }, function (res) {
                obj.html(res);
            });
            obj.on("change", function () {
                var suitabledPackagesVal = obj.val();
                if (suitabledPackagesVal == '') {
                    obj.parent().next().children().removeAttr('readonly');
                } else {
                    obj.parent().next().children().attr('readonly', 'true');
                    obj.parent().next().children().val('');
                }
            });
        });
    }

    function formPageBeforeSubmit() {
        $('#remarks').attr('value', $('#remarksDisplay').val());
        var showRemarksInInvoiceVal = document.getElementById("showRemarksInInvoiceDisplay").checked;
        $('#showRemarksInInvoice').attr('value', showRemarksInInvoiceVal);

        var isHotelGuestVal = document.getElementById("isHotelGuestDisplay").checked;
        $('#isHotelGuest').attr('value', isHotelGuestVal);

    }

    function clientView() {
        $("#clientView").attr("href", '<c:url value="/book/clientView?userId="/>' + $("#memberId").val());
    }

</script>