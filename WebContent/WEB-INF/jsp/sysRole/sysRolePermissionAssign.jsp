<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<style type="text/css">
    #productOptionBody td {
        border: 1px solid #977777;
    }
    .p-t-0{
        padding-top:0 !important;
    }
</style>
<c:url var="url" value="/sysRole/assign"/>
<form  id="defaultForm" method="post" class="form-horizontal" action='${url}'>
    <input type="hidden" name="id" value="${sysRoleVO.id}">
    <div class="form-group">
        <label class="col-lg-5 control-label p-t-0"><spring:message code="label.role.name"/></label>
        <div class="col-lg-5">
            <div>${sysRoleVO.name}</div>
        </div>
    </div>
    <div class="row new-row-width">
        <table id="sysResourceTreeTable" class="table table-striped">
            <thead>
            <tr>
                <th width="70%"><spring:message code="label.name"/></th>
                <th width="15%"><spring:message code="label.type"/></th>
                <th width="15%"><spring:message code="label.description"/></th>
            </tr>
            </thead>
            <tbody id="productOptionBody">
            <c:forEach items="${sysResourceList}" var="item">
                <tr data-tt-id="${item.id}"
                    <c:if test="${not empty item.sysResource}">data-tt-parent-id='${item.sysResource.id}'</c:if>>
                    <td>
                        <c:if test="${not empty item.sysResource}">
                            <input name="sysResourceIds" class="permissions" type="checkbox" data-perm="${item.permission}" value="${item.id}"/>
                        </c:if>
                            ${item.name}
                    </td>
                    <td><spring:message code="label.type.${item.type}"/></td>
                    <td>${item.description}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="modal-footer">
        <div class="bootstrap-dialog-footer">
            <div class="bootstrap-dialog-footer-buttons">
                <button type="button" class="btn btn-primary dialogSubmitBtn" id="dlgSubmit">
                    <spring:message code="label.button.submit"/>
                </button>
            </div>
        </div>
    </div>
</form>

<script type="text/javascript">
    $(document).ready(function () {
        
        var form = $('form', Dialog.getContext());
        var sysResourceTreeTable = $("#sysResourceTreeTable", Dialog.getContext());
        var tree = sysResourceTreeTable.treetable({expandable: true, clickableNodeNames: false});
        setTimeout(function () {
            tree.treetable('expandNode', 1);
        }, 0);

        // 初始化选中的权限
        var permissionsInputs = $('.permissions', Dialog.getContext());
        <c:forEach items="${sysRoleVO.sysResourceIds}" var="item">
        permissionsInputs.filter('[value=${item}]').prop('checked', true);
        </c:forEach>

        permissionsInputs.change(function () {
            var $this = $(this);
            var trNode = $this.parents('tr');
            var nodeId = trNode.data('tt-id');
            /*var parentId= trNode.data('tt-parent-id');
             $("tr[data-tt-parent-id="+parentId+"]").not(trNode).each(function(){
             var $this = $(this);
             var id = $this.data("tt-id");
             tree.treetable('collapseNode', id);
             });*/
            if (trNode.hasClass('collapsed')) {
                tree.treetable('expandNode', nodeId);
            }
            if (this.checked) {
                // 勾选所有的子节点
                updateCheckBoxStatus(nodeId, true);
            } else {
                updateCheckBoxStatus(nodeId, false);
            }

        });

        /**
         * 递归更新 checkbox 的状态
         * @param parentNodeId
         * @param status
         */
        function updateCheckBoxStatus(parentNodeId, status) {
            sysResourceTreeTable.find(String.format('tr[data-tt-parent-id={0}]', parentNodeId)).each(function () {
                var $this = $(this);
                $this.find('input[class=permissions]').prop('checked', status);
                if ($this.hasClass('branch')) {
                    updateCheckBoxStatus($this.data('tt-id'), status);
                    if ($this.hasClass('collapsed')) {
                        tree.treetable('expandNode', $this.data('tt-id'));
                    }
                }
            });
        }
    });
</script>
 