<%@include file="_header.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<h1>Liste des commandes</h1>

<c:forEach items="${orders}" var="order">


	<c:choose>
		<c:when test="${empty order}">
			<p>aucune commande</p>
		</c:when>

		<c:otherwise>
		Date 
		<fmt:formatDate pattern = "d/M/YY" 
         value = "${order.createdOn}" />
			<p>
				Montant
				<span class="price"> 
				<fmt:formatNumber
						type="number" pattern="##,##"
						value="${order.amount}" /> &euro;
				</span>
			<p>
				Status
				<c:out value="${order.currentStatus}"/>
			</p>
			
		</c:otherwise>
	</c:choose>
</c:forEach>

<%@include file="_footer.jsp" %>