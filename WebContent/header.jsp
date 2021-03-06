<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- 登录 注册 购物车... -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top:20px">
		<ol class="list-inline">
			<c:if test="${empty user }">
				<li><a href="login.jsp">登录</a></li>
				<li><a href="register.jsp">注册</a></li>
			</c:if>
			<c:if test="${!empty user }">
				<li>你好，${user.username}</li>
				<li><a href="${pageContext.request.contextPath }/logout">退出</a></li>
			</c:if>
			
		
			<li><a href="cart.jsp">购物车</a></li>
			<li><a href="${pageContext.request.contextPath }/getOrder">我的订单</a></li>
		</ol>
	</div>
</div>

<!-- 不引入jQuery了，调用header.jsp的页面都有jQuery了 -->
<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="${pageContext.request.contextPath}">首页</a>
			</div>

			<script type="text/javascript">
				$(function(){
					//功能：从/categoryList得到类别，并写到页面中
					var content = "";
					$.post(
						//URL
						"${pageContext.request.contextPath}/categoryList",		
						//[data]
						/* 不需要 */
						//[function]
						function(data){
							//注：得到categoryList数据格式为：[{"cid":"xxx", "cname":"XXX"}, {...}, {...}, ...]
							
							//动态创建<li><a href="#"> XXX </a></li>
							for(var i=0; i<data.length; i++){
								content+="<li><a href='${pageContext.request.contextPath}/productListByCid?cid="+ data[i].cid +"'>"+ data[i].cname +"</a></li>";
							}
							
							//将拼接好的内容放进指定位置：#cateList
							$("#cateList").html(content);
						},
						//[type]
						"json"
					);
				});
			</script>

			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav" id="cateList">
					
					<!-- <li class="active"><a href="product_list.htm">手机数码<span class="sr-only">(current)</span></a></li>
					<li><a href="#">电脑办公</a></li> -->
					
				</ul>
				<form class="navbar-form navbar-right" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="该功能暂未开放">
					</div>
					<button type="submit" class="btn btn-default" title="该功能暂未开放">搜索</button>
				</form>
			</div>
		</div>
	</nav>
</div>