/******************************初始化*************************/
$(function() {
	//初始化树菜单
	tree_init();
});


/** 树菜单 ***/
function tree_init(){
	var setting = {
			check: {
				enable: true,
			},
			data : {
				simpleData : {
					enable : false,
					idKey : "id",
					pIdKey : "parentId",
					rootPId : ""
				},
				key: {
					url: "xUrl"
				}
			},
			callback: {
				onAsyncSuccess:onAsyncSuccess,
				beforeCheck: function(treeId, treeNode) {
				    return false;
				}
			},
			async: {
				enable: true,
				url: rootPath + '/resource/getTreeList',
			},
			view : {
				dblClickExpand : true,
				showIcon: false
			},
		};
		//初始化树形列表
	var zTree = $.fn.zTree.init($("#permissionTree"), setting);
	
}
function onAsyncSuccess(treeId, treeNode) {
	 var zTree = $.fn.zTree.getZTreeObj("permissionTree");
	   zTree.expandAll(true); 
	   if(zTree){
		   var nodes = zTree.getSelectedNodes();
			for (var i=0, l=nodes.length; i < l; i++) {
				zTree.setChkDisabled(nodes[i], true);
			}
		   if (permissions && permissions != '') {
				permissions = $.parseJSON(permissions);
				for (var i = 0; i < permissions.length; i++) {
					var nodes = zTree.getNodeByParam('id', permissions[i].id);
					zTree.checkNode(nodes, true, false);
				}
			}
		}
	   
}