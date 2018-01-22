/** ****************************初始化************************ */
var isSubmited = false;// 标识:是否已提交（防止重复提交）
$(function() {
	$.validator.addMethod("required", function(value, element, param) {
		if (!value || value.trim() == '') {
			return false;
		} else {
			return true;
		}
	}, '必填！');

	$('#systemDictionaryForm').validate({
		ignore : [],// 验证隐藏(如:select)

		rules : {
			name : {
				required : true,
			}
		},
		messages : {
			name : {
				required : "必填,不能为空",
			}
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

});
/** ****************************具体方法************************ */

//表单提交
function submitHandler(form) {
	layer_index = parent.layer.msg('加载中', {
		icon : 16,
		shade : 0.01,
		time : 0
	// 不自动关闭
	});
	isSubmited = true;// 是否已提交（true）
	var jsonParms = $(form).serializeJson();
	var url = "/systemDictionary/edit";
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
