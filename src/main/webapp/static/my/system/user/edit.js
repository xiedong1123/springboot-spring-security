/** ****************************初始化************************ */
var isSubmited = false;// 标识:是否已提交（防止重复提交）
$(function() {
	
	layui.use('form', function() {
		var form = layui.form

	});
	$.validator.addMethod("username", function(value, element, param) {
		var reg = /^[a-zA-Z\d]\w{3,9}[a-zA-Z\d]$/;
		if (value.trim() == '') {
			return false;
		} else {
			return reg.test(value.trim());
		}
	}, '可以字母（不区分大小写）数字、下划线，不能以下划线开头和结尾,并且大于5位小于12位!');
	$.validator.addMethod("mobile", function(value, element, param) {
		var reg = /^1[34578]\d{9}$/;
		if (value.trim() == '') {
			return true;
		} else {
			return reg.test(value.trim());
		}
	}, '手机号码格式不正确！');
	$.validator.addMethod("required", function(value, element, param) {
		if (!value || value.trim() == '') {
			return false;
		} else {
			return true;
		}
	}, '必填！');

	$('#userEditform').validate({
		ignore : [],// 验证隐藏(如:select)

		rules : {
			username : {
				required : true,
				username : true,
				remote : {
					url : rootPath + '/user/getAccount', // 后台处理程序
					type : "post", // 数据发送方式
					dataType : "json", // 接受数据格式
					data : { // 要传递的数据
						username : function() {
							return $("#username").val();
						},
						id : function() {
							return $("#id").val();
						},
					}
				}
			},
			mobile : {
				mobile : true,
				maxlength : 11
			},
			name : {
				required : true,
			},
			roleIds : {
				required : true,
			},
		},
		messages : {
			username : {
				required : "必填",
				remote : "必填,账户已经存在"
			},
			roleIds : {
				required : "请选择角色",
			},
			name : {
				required : "必填,用户真实姓名",
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

var exists = false;// 用户名是否已经存在
function submitHandler(form) {
	layer_index = parent.layer.msg('加载中', {
		icon : 16,
		shade : 0.01,
		time : 0
	// 不自动关闭
	});
	isSubmited = true;// 是否已提交（true）
	
	
	var jsonParms = $(form).serializeJson();
	
	if (Array.isArray(jsonParms.roleId)) {
		jsonParms.roleIds = jsonParms.roleId.join(',').toString();
	} else {
		jsonParms.roleIds = jsonParms.roleId;
	}
	
	var url = "/user/edit" ;
	$.post(rootPath + url, jsonParms, function(data) {
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



/**关闭*/
function toCancel(){
	parent.layer.close(parent.layer_form);
}