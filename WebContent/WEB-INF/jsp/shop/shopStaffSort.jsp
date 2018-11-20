<%@page import="com.spa.constant.CommonConstant"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>
<div id="checkAndInverCheck">
<c:url var="url" value="/shop/sort"/>
<form id="defaultForm" data-form-token="${TokenUtil.get(pageContext)}" method="post" class="form-horizontal" action='${url }'>
    <table class="table table-striped table-hover" <%--style="align:center"--%>>
        
        <c:set value="0" var="sum" />
        <c:set value="0" var="listSum" />
        <c:forEach items="${staffHomeShop}" var="shs">
            <c:set value="${sum + 1}" var="sum" />
            <tr>
                <td>
                        ${shs.user.displayName}
                    <input type="hidden" value="${shs.id}" id="id" name="staffSortVOList[${listSum}].id"/>
                </td>

                    <td><input id="sort" class="form-control sort" name="staffSortVOList[${listSum}].sort" size="3" value="${sum}" style="width: 60px;display: none"/></td>
                <td>
                    <a type="button" class="btn btn-info" name="upMove" value='<spring:message code="label.button.move.up"/>'>
                        <i class="glyphicon glyphicon-chevron-up"></i>
                    </a>
                </td>
                <td>
                    <a type="button" class="btn btn-info" name="downMove" value='<spring:message code="label.button.move.down"/>'>
                        <i class="glyphicon glyphicon-chevron-down"></i>
                    </a>
                </td>
            </tr>
            <c:set value="${listSum + 1}" var="listSum" />
        </c:forEach>

    </table>
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-primary dialogSubmitBtn">
                    <spring:message code="label.button.submit"/>
                </button>
                 <input  type="hidden" value="${shopId}" name="shopId"/>
            </div>
        </div>
    </div>
</form>
</div>
<script type="text/javascript">
    //上移
    $("a[name='upMove']").bind("click",function(){
        console.log("test")
        var $this = $(this);
        var curTr = $this.parents("tr");
        var prevTr = $this.parents("tr").prev();
        if(prevTr.length == 0){
            //BootstrapDialog.alert('<spring:message code="label.shop.sort.first.msg"/>');
            return;
        }else{
            prevTr.before(curTr);
            sortNumber();//重新排序
        }
    });
    //下移
    $("a[name='downMove']").bind("click",function(){

        var $this = $(this);
        var curTr = $this.parents("tr");
        var nextTr = $this.parents("tr").next();
        if(nextTr.length == 0){
            //BootstrapDialog.alert('<spring:message code="label.shop.sort.last.msg"/>');
            return;
        }else{
            nextTr.after(curTr);
            sortNumber();//重新排序
        }
    });
    //排序
    $("#sort").bind("change",function(){
        var $this = $(this);
        //获得当前行
        var curTr = $this.parents("tr");
        var cursort = $this.val();
        //当前行同级的所有行
        var siblingsTrs = curTr.siblings();
        if(siblingsTrs.length >0){
            for(var i in siblingsTrs){
                var othersort = $(siblingsTrs[i]).children().find("#sort").val();
                if(parseInt(cursort) <= parseInt(othersort)){
                    $(siblingsTrs[i]).before(curTr);
                    sortNumber();//重新排序
                    break;
                }
            }
        }
    });
    function sortNumber(){
        var allInput = $("#checkAndInverCheck").find(".sort");
        console.log(allInput)
        if(allInput.length != 0){
            for(var i=0;i<allInput.length;i++){
                var tempInput = allInput[i];
                tempInput.value = i + 1;
            }
        }
    }
</script>