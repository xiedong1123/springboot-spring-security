var isSubmited = false;//标识:是否已提交（防止重复提交）
$(function() {
	
	$("#toSubmit").on("click", function() {
		if(!isSubmited){
			 submitHandler();
		}
		return false;
	});
	
	$("#toCancel").on("click", function() {
		toCancel();
		return false;
	});
});

function submitHandler() {
	layer_index = parent.layer.msg('加载中', {
		icon: 16
		,shade: 0.01,
		time: 0 //不自动关闭
	});
	isSubmited =  true;//是否已提交（true）
	
	var isEnabled = $("#isEnabled").val();
	var id = $("#id").val();
	
	var url = rootPath + '/user/updateStstus';
		
	$.post(url, {"id":id,"isEnabled":isEnabled}, function(data) {
		if (data.success) {
			parent.my_reload();
			parent.layer.close(parent.layer_form);
			parent.layer.msg("操作成功");
		} else {
			layer.msg("操作失败!");
		}
		isSubmited =  false;//是否已提交（false）
		parent.layer.close(layer_index);
	});
}
/**关闭*/
function toCancel(){
	parent.layer.close(parent.layer_form);
}
