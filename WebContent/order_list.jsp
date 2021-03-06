<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="shortcut icon" href="img/web_icon.ico" type="image/x-icon" />
<title>订单展示</title>
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
</style>
</head>

<body>

	<script type="text/javascript">
		function payOrder(URL, PARAMS) {
			var temp = document.createElement("form");
			temp.action = URL;
			temp.method = "post";
			temp.style.display = "none";
			for ( var x in PARAMS) {
				var opt = document.createElement("textarea");
				opt.name = x;
				opt.value = PARAMS[x];
				temp.appendChild(opt);
			}
			document.body.appendChild(temp);
			temp.submit();
			//return temp; 
		}
	</script>

	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<div style="margin: 0 auto; margin-top: 10px; width: 950px;">
				<strong>我的订单</strong>
				<c:if test="${fn:length(orderList)}==0">
					当前用户没有订单
				</c:if>
				<table class="table table-bordered">
					<c:forEach items="${orderList }" var="order">
						<tbody>
							<tr class="success">
								<th colspan="3">订单编号:${order.oid }</th>
								<th colspan="2">状态: <c:if test="${order.state==0}">
									未付款&nbsp;&nbsp;&nbsp;&nbsp;
									<a href="javascript:;"
											onclick="payOrder(
									'${pageContext.request.contextPath}/alipay.trade.page.pay.jsp',
									{'WIDout_trade_no':'${order.oid}', 'WIDtotal_amount':'${order.total}', 'WIDsubject':'User Order'}
									)">前往付款</a>
									</c:if> <c:if test="${order.state!=0}">
									已付款
								</c:if>
								</th>
							</tr>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
							</tr>

							<c:forEach items="${order.orderItems }" var="item">
								<tr class="active">
									<td width="60" width="40%"><img
										src="${pageContext.request.contextPath }/${item.product.pimage }"
										width="70" height="60"></td>
									<td width="30%"><a
										href="${pageContext.request.contextPath}/productInfo?
										pid=${item.product.pid}&cid=${item.product.cid}">
											${item.product.pname }</a></td>
									<td width="20%">￥${item.product.shop_price }</td>
									<td width="10%">${item.count }</td>
									<td width="15%"><span class="subtotal">￥${item.subtotal }</span></td>
								</tr>
							</c:forEach>

							<tr>
								<th colspan="5" style="text-align: right">
									商品总计：${order.total}</th>
							</tr>
						</tbody>
					</c:forEach>

				</table>
			</div>
		</div>

	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>

</html>