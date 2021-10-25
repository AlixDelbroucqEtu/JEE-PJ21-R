<%@include file="_header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<h1>Les Articles</h1>

</br>
<c:choose>
	<c:when test="${empty articles}">
		<p>Aucun article</p>
	</c:when>

	<c:otherwise>
		<ul class="articles">
		<c:forEach items="${articles}" var="article">

					<li>

						<a href="#">
							<span class="price">
								<c:out value="${article.price}"/> &euro;
							</span>

							<c:out value="${article.libelle}"/> <br/>
						</a>
						<div style="position: absolute; margin-left:60px;">
							Quantité <input type="number" min="1" value="1" id= "quantity<c:out value="${article.id}"/>" style="width: 50px; text-align: center;"/>
						</div>
						<div>
							<span class="glyphicon glyphicon-plus-sign addToCart" data-ref="<c:out value="${article.id}"/>"></span>
						</div>
					</li>
		</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>

<%
jsList.add("cart.js");
%>
<%@include file="_footer.jsp" %>