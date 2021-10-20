<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h1>Panier</h1>

<c:choose>
	<c:when test="${empty cart.articles}">
		<p>Aucun article</p>
	</c:when>

	<c:otherwise>
		<c:forEach items="${cart.articles}" var="article">
		Item <c:out value="${article.name}" />
			<p>
				Prix
				
				<span class="price"> <%--show price as X,XX --%> <fmt:formatNumber
						type="number" pattern="##,##"
						value="${article.price}" /> &euro;
				</span>
			<p>Panier Vide</p>
		</c:forEach>
	</c:otherwise>
</c:choose>

<a class="btn btn-primary" href="cart/1/validate.html">Commander</a>