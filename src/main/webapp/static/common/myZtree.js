/**
 * ztree插件
 */

$(function(){
	 drop =  dropDwon("myZtree",function(dl){
		$('<ul id="myZtreeTree" class="ztree selectZtree" style="margin-top: 0; width: 98.5%;height:240px; overflow-x:hidden;"></ul>').appendTo($(dl));
		var setting = {
				check: {
					enable: false
				},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId",
					rootPId : ""
				}
			},
			view : {
				dblClickExpand : false,
				showIcon: false,
				selectedMulti: false //不需要多选的时候selectedMulti: false等等
			},
			callback : {
				onClick : zTreeOnClick,
 				onAsyncSuccess:function(){//异步加载成功后 展开树
 					var treeObj = $.fn.zTree.getZTreeObj("myZtreeTree");
 					treeObj.expandAll(true);
 				}
			},
			async: {
				enable: true,
				url: rootPath + "/department/treeList",
			},
		};
		$.fn.zTree.init($("#myZtreeTree"),setting).expandAll(true);
		
	});
	//ztree点击事件
	function zTreeOnClick(event, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("myZtreeTree");
		var nodes = zTree.getSelectedNodes();
		nodes.sort(function compare(a, b) {
			return a.id - b.id;
		});
		drop.setInputValue(nodes[0].deptName);
		if ($("#parentId").val()) {
			$("#parentId").val(nodes[0].id);
			$("#parentName").val(nodes[0].deptName);
			$("#parentSearchSn").val(nodes[0].searchSn);
		}else {
			$("#deptId").val(nodes[0].id);
			$("#deptName").val(nodes[0].name);
		}
		drop.close();
	}
})