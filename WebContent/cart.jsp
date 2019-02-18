<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>地猫商城购物车</title>
		<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
		<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="css/style.css" type="text/css" />
		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
			
			font {
				color: #3164af;
				font-size: 18px;
				font-weight: normal;
				padding: 0 10px;
			}
		</style>
		
		<script type="text/javascript">
			function delPro(pid){
				if(confirm("你真的忍心删除吗(T^T)")){
					location.href = "${pageContext.request.contextPath}/delProFromCart?pid="+pid;
				}
			}
			
			function clearCart(){
				if(confirm("你确定要清空购物车[痛哭流涕]？")){
					location.href = "${pageContext.request.contextPath}/clearCart";
				}
			}
			
			function submitOrder(){
				if(${empty user}){
					 var answer = confirm("请先登录");
					 if(answer){
						 location.href = "${pageContext.request.contextPath}/login.jsp";
					 }
				}else{
					$.get(
						"${pageContext.request.contextPath}/verifyUnfinishedOrder",
						{"uid" : "${user.uid }"},
						function(data){
							// 验证当前用户有没有未完成订单
							//if(parseInt(data) == 0){
								location.href = "${pageContext.request.contextPath}/submitOrder";
								// 购物项已提交，清空购物车
								/* $.ajax("${pageContext.request.contextPath}/clearCart"); */
							//}else{
							//	alert("你还有未处理的订单哦~");
							//	location.href = "${pageContext.request.contextPath}/order_list.jsp";
							//}
						},
						"text"
					)
				}
			}
		</script>
		
	</head>

	<body>
		<!-- 引入header.jsp -->
		<jsp:include page="/header.jsp"></jsp:include>

		<c:if test="${!empty cart.cartItems }">

		<div class="container">
			<div class="row">

				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong style="font-size:16px;margin:5px 0;">购物车详情</strong>
					<table class="table table-bordered">
						<tbody>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>
							
							<c:forEach items="${cart.cartItems }" var="entry">
								<tr class="active">
									<td width="60" width="40%">
										<input type="hidden" name="id" value="22">
										<img src="${pageContext.request.contextPath }/${entry.value.product.pimage}" width="70" height="60">
									</td>
									<td width="30%">
										<a target="_blank" href="${pageContext.request.contextPath }/productInfo?pid=${entry.value.product.pid}&cid=${entry.value.product.cid}">
											${entry.value.product.pname}</a>
									</td>
									<td width="20%">
										￥${entry.value.product.shop_price}
									</td>
									<td width="10%">
										${entry.value.buyNum}
									</td>
									<td width="15%">
										<span class="subtotal">￥${entry.value.subtotal}</span>
									</td>
									<td>
										<a href="javascript:;" onclick="delPro('${entry.value.product.pid}')" class="delete">删除</a>
									</td>
								</tr>
							</c:forEach>
							
						</tbody>
					</table>
				</div>
			</div>

			<div style="margin-right:130px;">
				<div style="text-align:right;">
					<em style="color:#ff6600;">
				登录后确认是否享有优惠&nbsp;&nbsp;
			</em> 赠送积分: <em style="color:#ff6600;">596</em>&nbsp; 商品金额: <strong style="color:#ff6600;">￥${cart.total }元</strong>
				</div>
				<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
					<a href="javascript:;" onclick="clearCart()" id="clear" class="clear">清空购物车</a>
					<a href="javascript:;" onclick="submitOrder()">
						<input type="submit" width="100" value="结算" name="submit" border="0" style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
						height:35px;width:100px;color:white;">
					</a>
				</div>
			</div>

		</div>
		
		</c:if>
		<c:if test="${empty cart.cartItems }">
			<div class="cart-empty" style="text-align: center;">
				<img alt="" src="${pageContext.request.contextPath }/images/cart-empty.png" style="margin: 0 auto;" />
				<h2>您的购物车还是空的！</h2>
			</div>
		</c:if>
		
		<!-- 引入footer.jsp -->
		<jsp:include page="/footer.jsp"></jsp:include>

	</body>

</html>