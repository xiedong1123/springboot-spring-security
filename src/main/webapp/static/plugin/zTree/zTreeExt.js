/**
 * 刷新ztree
 */
function loadIframe(iframeId,href){
	 if(iframeId!="" && href!=""){
		 $("#"+iframeId).attr("style","width:100%;height:99%;").attr("src",ctx+href);
		 }
}
	 


function refreshTree(id,name){
	var zTree = $.fn.zTree.getZTreeObj("tree");
	var node = zTree.getNodeByParam("id", id, null);
	node.name=name;			  
	zTree.updateNode(node);
}
/**
 * 给ztree添加一个节点
 */
function addNode(iframeId,parentId,id,name,url){
 	var zTree = $.fn.zTree.getZTreeObj("tree");
	var parentNode = zTree.getNodeByParam("id", parentId, null);
	var click="javascript:loadIframe('"+iframeId+"','"+url+"')";
	
	var newNode = {pId:parentId,id:id,name:name,click:click,open:true};
	
	zTree.addNodes(parentNode, newNode);
}
/**
 * 删除ztree节点
 */
function removeNodes(ids){
	var zTree = $.fn.zTree.getZTreeObj("tree");
	if(ids.indexOf(",")>-1){
		var idsarray = ids.split(",");
		for (var i=0;idsarray.length;i++) {
			if(typeof(idsarray[i])!="undefined"){
				var node = zTree.getNodeByParam("id",$.trim(idsarray[i]+""), null);
			    zTree.removeNode(node);
			}else{
				break;
			}
		}
	}else{
		var node = zTree.getNodeByParam("id", ids, null);
		zTree.removeNode(node);
	}
}

//=================以下js用于部门和角色=========================================
checkSetting = {
		check: {
			enable: true,
			chkboxType: {"Y":"", "N":""}
		},
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
			  enable: true
			}
		},
		callback: {
			beforeClick: beforeClick,
			onCheck: onCheck
		}
};
radioSetting = {
		check: {
			enable: true,
			chkStyle: "radio",
			radioType: "all"
		},
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
			  enable: true
	     	}
		},
		callback: {
			onClick: onClick,
			onCheck: onCheck
		}
};
function beforeClick(treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
}
	
function onClick(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId);
		zTree.checkNode(treeNode, !treeNode.checked, null, true);
		return false;
}

function onCheck(e, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj(treeId),
		nodes = zTree.getCheckedNodes(true);
		if(treeId=='deptTree'){
			var deptName = "",deptId = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				deptName += nodes[i].name + ",";
				deptId += nodes[i].id + ",";
			}
			if (deptName.length > 0 ) deptName = deptName.substring(0, deptName.length-1);
			if (deptId.length > 0 ) deptId = deptId.substring(0, deptId.length-1);
			$("#deptName").val(deptName);
			$("#deptId").val(deptId);
		}else if(treeId=='roleTree'){
			var roleNames = "",roleIds = "";
			for (var i=0, l=nodes.length; i<l; i++) {
				roleNames += nodes[i].name + ",";
				roleIds += nodes[i].id + ",";
			}
			if (roleNames.length > 0 ) roleNames = roleNames.substring(0, roleNames.length-1);
			if (roleIds.length > 0 ) roleIds = roleIds.substring(0, roleIds.length-1);
			$("#roleNames").val(roleNames);
			$("#roleIds").val(roleIds);
		}
}

function showTreeList(treeId) {
	
	if(treeId=='deptTree'){
		$("label[for='deptName']").html("");
		var deptO = $("#deptName");
		var deptOffset = $("#deptName").offset();
		$("#menuContentDeptTree").css({left:deptOffset.left + "px", top:deptOffset.top + deptO.outerHeight() + "px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDownDept);
		
	}else if(treeId=='roleTree'){
		$("label[for='roleNames']").html("");
		var roleO = $("#roleNames");
		var roleOffset = $("#roleNames").offset();
		$("#menuContentRoleTree").css({left:roleOffset.left + "px", top:roleOffset.top + roleO.outerHeight() + "px"}).slideDown("fast");

		$("body").bind("mousedown", onBodyDownRole);
	}
}
function hideMenuRole() {
		$("#menuContentRoleTree").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDownRole);
}
function onBodyDownRole(event) {
	   if (!(event.target.id == "menuBtnRole" || event.target.id == "menuContentRoleTree" || $(event.target).parents("#menuContentRoleTree").length>0)) {
			hideMenuRole();
	   }
}

function hideMenuDept() {
		$("#menuContentDeptTree").fadeOut("fast");
		$("body").unbind("mousedown", onBodyDownDept);
}
function onBodyDownDept(event) {
	   if (!(event.target.id == "menuBtnDept" || event.target.id == "menuContentDeptTree" || $(event.target).parents("#menuContentDeptTree").length>0)) {
			hideMenuDept();
	   }
}
		