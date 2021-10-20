<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h2 align="center">Panier</h2>
</br>
<c:choose>
	<c:when test="${empty cart.articles}">
		<p>Panier vide</p>
	</c:when>

	<c:otherwise>
		<table width="300">
		<c:forEach items="${cart.articles}" var="article">
			<tr>
				<td rowspan="2"><img width="50" height="50" src="<c:out value="${article.img}"/>"/></td>
				<td><c:out value="${article.name}"/></td>
			</tr>
			<tr>
				<fmt:formatNumber var="formattedPrice" type="number" minFractionDigits="2" maxFractionDigits="2" value="${article.price/100}" />
				<td align="right"><c:out value="${formattedPrice}"/> &euro;</td>
			</tr>
		</c:forEach>
		</table>
	</c:otherwise>
</c:choose>
</br>
<a class="btn btn-primary" href="cart/1/validate.html">Commander</a>