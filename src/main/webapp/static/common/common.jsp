<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<link rel="shortcut icon" href="${ctx}/favicon.ico" />
<script>
var rootPath = "${ctx}";
</script>
<!-- *******************【jQuery】**********************-->
<script type="text/javascript" src="${ctx}/static/my/mylayui/js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="${ctx}/static/plugin/jquery-lazyload/jquery.lazyload.js"></script>
<script type="text/javascript" src="${ctx}/static/common/common.js"></script>
<!-- *******************【layUI\mylayUI】**********************-->
<link type="text/css" href="${ctx}/static/layui/css/layui.css" rel="stylesheet">
<link type="text/css" href="${ctx}/static/my/mylayui/css/style.css?v=12" rel="stylesheet">
<%-- <link type="text/css" href="${ctx}/static/my/css/my.css" rel="stylesheet"> --%>

<link type="text/css" href="${ctx }/static/my/css/popup-style.css?v=32" rel="stylesheet">

<script type="text/javascript" src="${ctx}/static/layui/layui.js"></script>

<script src="${ctx}/static/common/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx}/static/plugin/rsa/jsrsasign-latest-all-min.js"></script>

<script type="text/javascript">

	var buttons = '${resKeys}'
	var json = buttons ? $.parseJSON(buttons) : null;
	

</script>
<!-- <script type="text/javascript">
var layer;
layui.use('layer', function(){
	layer = layui.layer;
})


</script> -->

<!-- 解决谷歌浏览器选择文件点击取消按钮清空file问题 -->
<script >
    var newFiles;
    var newFileValue;
</script>

<!-- *******************【plugin】**********************-->

<!-- layTable -->
<%-- <script type="text/javascript" src="${ctx}/static/plugin/layTable.js"></script> --%>

<!-- ZTree树形插件 -->
<link rel="stylesheet" type="text/css" href="${ctx}/static/plugin/zTree/zTree_v3-master/css/zTreeStyle/zTreeStyle1.css">

<script src="${ctx}/static/plugin/zTree/zTree_v3-master/js/jquery.ztree.all.min.js"></script> 

<!-- my97datePicker时间选择插件 -->
<script src="${ctx}/static/plugin/My97DatePicker/WdatePicker.js"></script> 



<!-- *******************【baidu】**********************-->
<%-- <script type="text/javascript"  src="${ctx}/static/plugin/baidu-ueditor/ueditor.config.js"></script> --%>
<%-- <script type="text/javascript"  src="${ctx}/static/plugin/baidu-ueditor/ueditor.all.js"> </script> --%>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<%-- <script type="text/javascript" src="${ctx}/static/plugin/baidu-ueditor/lang/zh-cn/zh-cn.js"></script> --%>



<!-- 注意， 只需要引用 JS，无需引用任何 CSS ！！！-->
<script type="text/javascript" src="${ctx}/static/plugin/wangEditor-3.0.13/release/wangEditor.min.js"></script>

<!-- 浏览器兼容性处理 -->
<!--[if IE 7]>
	<link rel="stylesheet" href="${ctx}/static/my/mylayui/css/font-awesome-ie7.min.css">
<![endif]-->
<!--[if lt IE 9]>
	<script src="${ctx}/static/my/mylayui/js/html5shiv.js"></script>
	<script src="${ctx}/static/my/mylayui/js/respond.min.js"></script>
<![endif]-->
<!--[if IE 6]>
	<script src="${ctx}/static/my/mylayui/js/IE6_MAXMIX.js"></script>
	<script src="${ctx}/static/my/mylayui/js/IE6_PNG.js"></script>
	<script>
	    DD_belatedPNG.fix('.pngFix');
	</script>
	<script>
		// <![CDATA[
		if((window.navigator.appName.toUpperCase().indexOf("MICROSOFT")>=0)&&(document.execCommand)){
			try{
				document.execCommand("BackgroundImageCache", false, true);
			}catch(e){}
		}
		// ]]>
	</script>
<![endif]-->








