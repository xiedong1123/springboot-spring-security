/*$(function(){
	$(document.documentElement).bind("keydown",function(e){
		if(e.keyCode==116 || e.ctrlKey && e.keyCode==82){//回车键  == 116-F5
			layer.closeAll();
	        var iframe = $("#myiframe").attr("src");
	        $("#myiframe").attr('src',iframe);
	        e.preventDefault(); //组织默认刷新
		}
	}); 
});*/
function setIframeSrc(src){
    $("#myiframe").attr('src',src);
}
//窗口调整事件
window.onresize = function() {autoHeight()};

function autoHeight(){
    var iframe = document.getElementById("myiframe");
    if(iframe.Document){//ie自有属性
    	iframe.style.height = iframe.Document.documentElement.scrollHeight- document.getElementById("layout-head").offsetHeight +"px";
        iframe.style.width = iframe.Document.documentElement.scrollWidth - document.getElementById("layout-nav-left").offsetWidth+"px";
    }else if(iframe.contentDocument){//非ie,firefox,chrome,opera,safari
    	iframe.height = ($("#layout-nav-left").height()-$("#layout-head").height())+"px"; 
        iframe.width = ($("#layout-head").width()-$("#layout-nav-left").width()) +"px";
    }
}
/*编辑*/
function login() {
	layui.use('layer', function(){
		parent.layer_form = layer_form = parent.layer.open({
			title : "是否确认退出",
			type : 2,
			area : [ "550px", "330px" ],
			content : rootPath+'/user/loginOutUI'
		});
	});
}
//--------------------------初始化操作-----------------------
/*var myiframe = null;       //myiframe(显示内容)
$(function(){
	myiframe = $("#myiframe");		
	
    //系统时间显示
	
	//var dateTime = '<span id="div1"></span>';
	//			var dateTime = '<input type="text" title="查看日期" style="width:22px;border:0px;cursor:pointer" id="d243" onfocus="WdatePicker({eCont:null})" class="Wdate" style="cursor:pointer"/>'
	setInterval("$('#myTime').html(new Date().format('hh:mm:ss'));",1000);
	$('#myDate').html(new Date().format('yyyy-MM-dd'));
	//<img onClick="WdatePicker({el:'demospan'})" src="../../My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle" style="cursor:pointer" />
	 //导航点击事件
    $("[nav-n]").each(function () {
		$(this).bind("click",function(){
				var nav = $(this).attr("nav-n");
				var sn = nav.split(",");
				var htmlEl = '<a href="javascript:void(0)" onclick="location.reload()">首页<span class="layui-box">&gt;</span></a>';
					htmlEl+='<a href="#">'+sn[0]+'<span class="layui-box">&gt;</span></a>';
					htmlEl+='<a><cite>'+sn[1]+'</cite></a>';
					$("#breadcrumb").html(htmlEl);
		});
	});
});*/

/*--------------------------------------------点击事件----------------------------------------------*/
