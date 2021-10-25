<%@include file="/WEB-INF/views/_header.jsp" %>
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
			
					<li <c:if test="${daoArticle.isInCart(article.id, 1)}">style="border: solid green;"</c:if>>
					
						<a href="#">
							<span class="price">
								<fmt:formatNumber var="formattedPrice" type="number" minFractionDigits="2" maxFractionDigits="2" value="${article.price/100}" />
								<c:out value="${formattedPrice}"/> &euro;
							</span>
							<img src="<c:out value="${article.img}"/>"/><br/>
							<c:out value="${article.name}"/> <br/>
						</a>
						<div style="position: absolute; margin-left:60px;">
							Quantité <input type="number" min="0" value="1" id= "quantity<c:out value="${article.id}"/>" style="width: 50px; text-align: center;"/>
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
<%@include file="/WEB-INF/views/_footer.jsp" %>