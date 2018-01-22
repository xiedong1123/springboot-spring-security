/** ****************************初始化************************ */

var isSubmited = false;// 标识:是否已提交（防止重复提交）
$(function() {
	
	layui.use([ 'form', 'layer' ], function() {
		//初始化树
		tree_init();
	});
	
	
	$('#roleAddform').validate({
		ignore : [],// 验证隐藏(如:select)

		rules : {
			key : {
				required : true,
				remote : {
					url : rootPath + '/role/isExistsKey', // 后台处理程序
					type : "post", // 数据发送方式
					dataType : "json", // 接受数据格式
					data : { // 要传递的数据
						key :  function() {
				            return $("#key").val();
				        }
					}
				}
			},
			name : {
				required : true,
				remote : {
					url : rootPath + '/role/isExistsName', // 后台处理程序
					type : "post", // 数据发送方式
					dataType : "json", // 接受数据格式
					data : { // 要传递的数据
						name :  function() {
				            return $("#name").val();
				        }
					}
				}
			},
		},
		messages : {
			name : {
				required : "必填",
				remote : "角色名称已存在"
			},
			key : {
				required : "必填",
				remote : "角色唯一标识已存在"
			},
			 
		},
		highlight : function(element) {
			$(element).closest('.form-group').addClass('has-error');
		},

		success : function(label) {
			label.closest('.form-group').removeClass('has-error');
			label.remove();
		},

		errorPlacement : function(error, element) {
			element.parent('div').append(error);
		},
		submitHandler : function(form) {
			if (!isSubmited) {
				submitHandler(form);
			}
		}
	})
	
	
	$("#toCancel").on("click", function() {
		toCancel();
		return false;
	});
	
	
});
/** ****************************具体方法************************ */


function submitHandler(form) {
	layer_index = parent.layer.msg('加载中', {
		icon : 16,
		shade : 0.01,
		time : 0
	// 不自动关闭
	});
	isSubmited = true;// 是否已提交（true）
	
	var idArr = [];
	var jsonParms = $(form).serializeJson();
	//获取树选中节点
	var zTree =  $.fn.zTree.getZTreeObj("permissionTree");
	var nodes = zTree.getCheckedNodes(true);
	$.each(nodes,function(i,o){
		idArr.push(o.id);
	})
	
	jsonParms.resourceIds = idArr.join(',').toString();
	
	var url = rootPath + "/role/add";
	$.post(url, jsonParms, function(data) {
		if (data.success) {
			parent.my_reload();
			parent.layer.close(parent.layer_form);
			parent.layer.msg("操作成功");
		} else {
			parent.layer.msg("操作失败!");
		}
		isSubmited = false;// 是否已提交（false）
		parent.layer.close(layer_index);
	});
}


// 获取所有的权限ztree
function tree_init() {
	var setting = {
			check: {
				enable: true,
			},
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId",
					rootPId : ""
				},
				key: {
					url: "xUrl"
				}
			},
			callback: {
				onAsyncSuccess:function(treeId, treeNode) {
					 var zTree = $.fn.zTree.getZTreeObj("permissionTree");
					   zTree.expandAll(true); 
					   if(!zTree){
							$('#mytree').html('<div style="font-size: 35px;color: #CCC;text-align: center;padding-top: 47%;">无拥有权限</div>');
						}
					},
			},
			async: {
				enable: true,
				url: rootPath + '/resource/getTreeList',
			},
			view : {
				dblClickExpand : false,
				showIcon: false
			},
		};
		//初始化树形列表
		$.fn.zTree.init($("#permissionTree"), setting);
}


function toCancel(){
	parent.layer.close(parent.layer_form);
}
