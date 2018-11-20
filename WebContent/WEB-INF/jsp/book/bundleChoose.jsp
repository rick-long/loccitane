<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<style type="text/css">
    #productOptionBody td {
        border: 1px solid #ccc;
    }
</style>
<c:url var="url" value="/book/toAdd"/>
<%--<c:url var="url" value="/book/chooseBundle"/>--%>
<form  id="defaultForm" enctype="multipart/form-data" method="post" class="form-horizontal" data-form-token="${TokenUtil.get(pageContext)}" 
	action='${url}' style="overflow-y: scroll;overflow-x: hidden; background-color: rgba(255,255,255,0.9);max-height:500px;" >
<div id="defaultBundle">
    <input type="hidden" id="shopId" name="shopId" value="${shopId}"/>
    <div class="form-group">
        <div class="col-lg-12">
          <spring:message code="label.book.bundle.available"/>
        </div>
    </div>
        <div class="row new-row-width">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th width="20%"><spring:message code="label.bundle.name"/> </th>
                    <th width="40%"><spring:message code="label.bundle.group"/> 1 </th>
                    <th width="40%"><spring:message code="label.bundle.group"/> 2 </th>
                </tr>
                </thead>
                <tbody id="bundleBody">
                    <tr>
                        <c:if test="${empty bundleList}">
                            <td id="bundleGroups" style="background: white;">
                                <div class="empty_document_listing" ><spring:message code="lable.page.no.records"/></div>
                            </td>
                        </c:if>
                        <c:if test="${not empty bundleList}">
                            <td id="bundleGroups">
                                    <select class="selectpicker form-control bundleSelection" id="bundleId">
                                        <c:forEach items="${bundleList}" var="bundle" varStatus="idx">
                                            <option value="${bundle.id }">${bundle.name }[${bundle.bundleAmount }]</option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" value="${startTime }" id="startTime" name="startTime">
                            </td>
                        </c:if>
                    </tr>
                </tbody>
            </table>
        </div>
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-primary dialogSubmitBtn" id="dlgSubmit" >
                    <spring:message code="label.button.submit"/>
                </button>
            </div>
        </div>
    </div>
</div>
</form>

<script type="text/javascript">

    $(document).ready(function () {
    	 $('.bundleSelection',Dialog.getContext()).change(function () {
               var obj=$(this);
               $('#bundleGroups').nextAll().remove();
               $.post('<c:url value="/bundle/bundleSelections"/>',{bundleId:obj.val()},function (res) {
                   $('#bundleGroups').after(res);
               });
         }).trigger('change');
    	
        $('#dlgSubmit', Dialog.getContext()).click(function () {
            var bundleId = $('#bundleId',Dialog.getContext()).val();
            var startTime = $('#startTime',Dialog.getContext()).val();
            console.log(startTime);
            if(startTime == 'undefined') {
            	startTime = '';
            }
            var group1 = $('input[name="group1"]:checked').val();
            var group2 = $('input[name="group2"]:checked').val();
            
			if(typeof(group1) == 'undefined' || typeof(group2) == 'undefined'){
				Dialog.alert({
                    title: "<spring:message code='lable.error'/> ",
                    message: "<spring:message code='label.book.selected.bundle.product.is.required'/> "
                });
				return false;
			}
	        var startAppointmentTime = $('#startAppointmentTime').val();
            var sameTimeToShareRoom = $('#sameTimeToShareRoom', getContext());
            var isSameTimeToShareRoom = 'NO';
            if (sameTimeToShareRoom.is(":checked")) {
            	isSameTimeToShareRoom = 'YES';
            }
            $.post('<c:url value="/book/chooseBundle"/>', 
            		{startAppointmentTime: startAppointmentTime,
            	     bundleGroup1: group1,
            	     bundleGroup2: group2,
            	     bundleId: bundleId,
            	     startTime: startTime,
            	     isSameTimeToShareRoom:isSameTimeToShareRoom},
            		 function (res) {
                $('.close').click();
                var resource = $(res);

                var newBookBundleItem = resource.find('tr').clone(false);
                var trs = $('#bookItems tbody').find("tr:last-child");
                if(typeof(trs.html()) =='undefined'){
		                	$('#bookItems tbody').prepend(newBookBundleItem);
		                }else{
                	trs.after(newBookBundleItem);
                }

                var firstTr = $('#firstBookItem');
                if($('input[name="productOptionId"]', firstTr).filter(function(){return !this.value;}).length > 0) {
                	$(".removeBookItem",firstTr).removeClass('hide');
                }

                newBookBundleItem.find('.time-select').multiMenu();
		            	
		                $('.addBookItem', $('#bundleLastItem')).click(function () {
		                    addBookItem($(this));
		                });

                var firstVal = $('input[name="productOptionId"]', firstTr).val();
                if (!firstVal) {
                    firstTr.remove();
                }
		                newBookBundleItem.find('.startTimeDiv').find('.startTime').change(function(){
		                    updateEndTime($(this).parents('tr'));
		                });
            });
        });
        
        function addBookItem($this) {
            var newBookItem = $('#bookItemTemplate', getContext()).find('tr').clone(false);
            var previousItemTR = $this.parents('tr');
            previousItemTR.after(newBookItem);
            //$this.addClass('hide'); // 隱藏當前的按鈕

            // 註冊treatment下拉框
            newBookItem.find('.select-category').selectCategory({
                callback: function () {
                    var displayName = newBookItem.find('input[name=displayName]');
                    displayName.change(function () {
                        updateEndTime($(this).parents('tr'));
                    });
                }
            });

            newBookItem.find('select.guestAmount').selectpicker({});
            var isSameTimeToShareRoom = $('#sameTimeToShareRoom', getContext());
            if(isSameTimeToShareRoom.is(":checked")){
            	newBookItem.find('input.shareRoom').prop('checked',true);
            }
            newBookItem.find('.time-select').multiMenu();

            newBookItem.find('.addBookItem').click(function () {
                addBookItem($(this));
            });

            newBookItem.find('.removeBookItem').click(function () {
                $(this).parents('tr').remove();
                var addBookItem = $('#bookItems', getContext()).find('.addBookItem');
                if (addBookItem.length == 1) {
                    addBookItem.removeClass('hide');
                }
            }).removeClass('hide');

            var previousEndTime = previousItemTR.find('.endTime').val();
            newBookItem.find('.startTimeDiv').find('.select-id').val(previousEndTime);
            newBookItem.find('.startTimeDiv').find('.startTime').val(previousEndTime).change(function(){
                updateEndTime($(this).parents('tr'));
            });
        }
        
        $('#firstBookItem').find('.startTime').change(function(){
            updateEndTime($(this).parents('tr'));
        });
        
     	// 更新EndTime
        function updateEndTime(row) {
        	var bundleId = row.data('bundle-id');
        	if(bundleId == ''){
        		var startTime = $('#startAppointmentTime').val() + ' ' + row.find('.startTime').val() + ':00';
	            var displayName = row.find('input[name=displayName]');
	            var duration = displayName.data('duration');
	            var processTime = displayName.data('process-time');
	            if (duration && startTime) {
	                processTime = processTime ? processTime : 0;
	                var date = getDateFromFormat(startTime, 'yyyy-MM-dd HH:mm:ss');
	                date.setMinutes(date.getMinutes() + duration + processTime);
	                row.find('.endTime').val(date.format('hh:mm'));
	            }
        	}else {
        		var startTime = $('#startAppointmentTime').val() + ' ' + row.find('.startTime').val() + ':00';
        		var duration = row.find('#duration').val();
                var date = getDateFromFormat(startTime, 'yyyy-MM-dd HH:mm:ss');
                var dateTime =date.getTime();
                var endDateTime = dateTime + (60 * duration * 1000);
                var endDate = new Date(endDateTime);
                row.find('.endTime').val(endDate.format('hh:mm'));
            }
        }
    });

</script>