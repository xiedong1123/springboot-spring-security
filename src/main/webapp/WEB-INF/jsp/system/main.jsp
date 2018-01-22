<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>  --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台管理首页</title>
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<meta name="format-detection" content="telephone=no, address=no, email=no">	
<%@include file="/static/common/common.jsp"%>
<script type="text/javascript" src="${ctx}/static/my/system/main/main.js"></script>
</head>
<body style="overflow: hidden;">
<!--head s-->
<header id="layout-head" class="layout-head">
    <div class="admin-logo">
        <a href="JavaScript:;" target="_self"><img src="${ctx}/static/my/mylayui/images/logo-new.png"></a>
    	<p>人才管理平台1.0</p>
    </div>
    <!-- <div class="admin-nav">
        <ul class="layui-nav" lay-filter="">
            <li class="layui-nav-item layui-this"><a href="">首页</a></li>
            <li class="layui-nav-item"><a href="">基础管理</a></li>
            <li class="layui-nav-item"><a href="">监控管理</a></li>
            <li class="layui-nav-item">
                <a href="javascript:;">日常工作</a>
                <dl class="layui-nav-child"> 二级菜单
                    <dd><a href="">移动模块</a></dd>
                    <dd><a href="">后台模版</a></dd>
                    <dd><a href="">电商平台</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="">社区</a></li>
        </ul>
    </div> -->


    <div class="admin-function">
        <a href="javascript:void(0);" onclick="location.reload()" class="glyphicon-ym glyphicon-ym-home tip-btn tip-btn-1" data-toggle="tooltip" data-placement="bottom" data-title="主页"><span></span></a>
        <!-- <a href="javascript:void(0);" class="glyphicon-ym glyphicon-ym-cog tip-btn tip-btn-2" data-toggle="tooltip" data-placement="bottom" data-title="设置"><span></span></a> -->
        <a href="#" onclick="login()" class="glyphicon-ym glyphicon-ym-log-out tip-btn tip-btn-3" data-toggle="tooltip" data-placement="bottom" data-title="退出"><span></span></a>
    </div>

    <div class="userbox">
        <div class="user-icon"><img src="${ctx}/static/my/mylayui/images/hdp.png" width="38" height="38" /></div>
        	<div class="user-name" >&nbsp;${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal.username}</div>
    </div>

	<!-- <div class="searchdbox">
		<div class="layui-form-item searchbox">
			<label class="layui-form-label glyphicon-ym glyphicon-ym-search"></label>
			<div class="layui-input-inline">
			  <input type="text" name="username" lay-verify="required" placeholder="请输入搜索" autocomplete="off" class="layui-input">
			</div>
		</div>
	</div> -->
</header>
<!--head e-->
<!--leftnav s-->

<div class="layui-side layout-nav-left" id="layout-nav-left">
    <div class="layui-side-scroll">
        <div class="column-menu">
            <ul class="layui-nav layui-nav-tree site-demo-nav" lay-filter="test">
                <c:forEach items="${menus}" var="m" varStatus="s">
                    <li
                        class="layui-nav-item  my_layui-nav-item ${s.index == 0 ?'layui-nav-itemed':''}">
                        <a href="javascript:;">
                            <!-- <i class="layui-icon" style="font-size:16px;">&#xe623;</i> -->${m.name}</a>
                        <dl class="layui-nav-child">
                            <c:forEach items="${m.children}" var="cm">
                                <dd>
                                    <a href="${ctx}${cm.url}${cm.id}" onclick="setIframeSrc('${ctx}${cm.url}${cm.id}')" target="myiframe"
                                        nav-n="${m.name},${cm.name},${cm.url},${cm.icon}"><i
                                        class="layui-icon">&#xe617;</i>${cm.name}</a>
                                </dd>
                            </c:forEach>
                        </dl>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>

<!--leftnav e-->
<div class="container">
    <div id="container-maincontnet" class="container-maincontnet">
			<!--路径 s-->
			<!-- <div class="mybreadcrumb" >
			    <span>当前位置：</span>
				<span id="breadcrumb" class="layui-breadcrumb" lay-separator=">" >
				  <a href="javascript:void(0)" onclick="location.reload()">首页</a>
				</span>
				<span class="showTime"> 
					当前时间：<span id="myTime"></span>
					今天：<span id="myDate"></span>
					<img title="查看日期" onClick="WdatePicker({el:'myDate',isShowClear:false,readOnly:true,firstDayOfWeek:1})" src="static/plugin/My97DatePicker/skin/datePicker.gif" style="cursor:pointer" />
				</span>
			</div> -->
	    	<!--路径 e-->
	        <!--内容 s
	         <iframe id="myiframe" name="myiframe" src="${ctx}/welcome" scrolling="yes" frameborder="no" onload="autoHeight();"></iframe>-->
	          <iframe id="myiframe" name="myiframe" src="${ctx}/welcome" scrolling="yes" frameborder="no" onload="autoHeight();"></iframe>
	          <script type="text/javascript">
				//iframe加载之后就重新设置iframe的大小
				autoHeight();
			  </script>
	    	<!--内容 e-->

	</div>
</div>
<script type="text/javascript">
	
 $(function(){
	 
	 //导航的hover效果、二级菜单等功能，需要依赖element模块
	 layui.use(['element','layer'],function(){
		 $('.tip-btn').on("mouseenter",function(){
			 var $title = $(this).attr('data-title');
			 layui.layer.tips($title, $(this), {
	               tips: 3,
	           });
		 })
	  });
	 
 })
 
 /* var newScript = document.createElement('script');
 newScript.type = 'text/javascript';
 newScript.src = 'https://api.thinkpage.cn/v3/weather/now.json?key=61ohmpqeh4vbdbwy&location=chengdu&language=zh-Hans&unit=c';
 $('#myDate').append(newScript); */
 
 
</script> 
</body>
</html>