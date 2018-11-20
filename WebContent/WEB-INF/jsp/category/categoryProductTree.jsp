<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/jspInit.jsp" %>

<ul class="ztree categoryProductTree" style="width:400px; overflow:auto;"></ul>

<script type="text/javascript">
    $(function () {
        var zTreeObjId = "zTree" + new Date().getTime(), curStatus = "init",
                curAsyncCount = 0,
                asyncForAll = false,
                goAsync = false;
        var setting = {
            async: {
                enable: true,
                url: '${dataUrl}',
                autoParam: ["id=id", "name=name", "level=level", "type=type"],
                dataFilter: null
            },
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true,
                    idKey: 'id',
                    pIdKey: 'parentCategoryId'
                }
            },
            callback: {
                beforeAsync: beforeAsync,
                onAsyncSuccess: onAsyncSuccess,
                onAsyncError: onAsyncError,
                onCheck: zTreeOnCheck,
                onExpand: zTreeOnExpand
            }
        };

        var categoryProductTree = $('.categoryProductTree');
        if (categoryProductTree.length > 1) {
            console.error("It can not be two or more component with class name[categoryProductTree]");
        }
        categoryProductTree.attr("id", zTreeObjId); // 设置id


        //禁用半选状态下的表单,并把它的子节点提前加载出来
        function zTreeSetChkDisabled(zTree,nodes){
            for(var n= 0; n<nodes.length;n++){
                if(nodes[n].isParent){
                    if(nodes[n].zAsync==false){
                        zTree.reAsyncChildNodes(nodes[n], "refresh",true);
                    }
                   if(nodes[n].getCheckStatus().half){
                        zTree.setChkDisabled(nodes[n], true);
                    }
                }
            }
        }

        //初始化禁用半选状态下的表单
        setTimeout(function () {
            var zTree_obj = $.fn.zTree.getZTreeObj(zTreeObjId);
            var Node_array = zTree_obj.getNodes();
            zTreeSetChkDisabled(zTree_obj,Node_array)
            zTreeSetChkDisabled(zTree_obj,Node_array[0].children)
        }, 1000);

        //展开时禁用半选状态下的表单
        function zTreeOnExpand(event, treeId, treeNode){
            var zTree_obj = $.fn.zTree.getZTreeObj(treeId);
            zTreeSetChkDisabled(zTree_obj,treeNode.children);
        }


        function onAsyncSuccess(event, treeId, treeNode, msg) {
            curAsyncCount--;
            var zTree = $.fn.zTree.getZTreeObj(treeId);
            if (!treeNode) {
                curStatus = "async";
                setTimeout(function () {
                    for(var i=0;i<zTree.getNodes().length;i++){
                         zTree.expandNode(zTree.getNodes()[i], true);
                    }
                }, 50);
            } else if (curStatus == "async") {
                if ((treeNode.checked || treeNode.halfCheck) && treeNode.children.length > 0) {
                    asyncNodes(treeNode.children);
                }
                if (curAsyncCount <= 0) {
                    if (curStatus != "init" && curStatus != "") {
                        asyncForAll = true;
                    }
                    curStatus = "";
                }
            }
        }

        function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown) {
            curAsyncCount--;
            if (curAsyncCount <= 0) {
                curStatus = "";
                if (treeNode != null) asyncForAll = true;
            }
        }

        function beforeAsync() {
            curAsyncCount++;
        }

        function asyncNodes(nodes) {
            if (!nodes) return;
            curStatus = "async";
            var zTree = $.fn.zTree.getZTreeObj(zTreeObjId);
            for (var i = 0, j = nodes.length; i < j; i++) {
                goAsync = true;
                zTree.reAsyncChildNodes(nodes[i], "refresh", true);
            }
        }

        function zTreeOnCheck(event, treeId, treeNode) {
            var form = $('.categoryProductTree').parents('form');
            form.find('input[name=categoryIds]').remove();
            form.find('input[name=productIds]').remove();
            var zTree = $.fn.zTree.getZTreeObj(treeId);
            var checkNotes = zTree.getCheckedNodes(true);

            $.each(checkNotes, function (index, node) {
                var id = node.id;
                if (node.type == 'CATEGORY') {
                    if (node.checked && !node.getCheckStatus().half) {
                        form.append('<input type="hidden" name="categoryIds" value="' + id + '">');
                    }
                } else if (node.type == 'PRODUCT') {
                    if (node.checked) {
                        form.append('<input type="hidden" name="productIds" value="' + id + '">');
                    }
                }
            });
        }

        $.fn.zTree.init(categoryProductTree, setting);
    });
</script>