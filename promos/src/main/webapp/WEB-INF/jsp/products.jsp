<%@include file="_header.jsp" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


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
							  <fmt:formatNumber var="formattedPrice" type="number" minFractionDigits="2" maxFractionDigits="2" value="${article.price}" />
								<%-- Display correct price depending on promo --%>
								<c:choose>
									<%-- No promo applicable --%>
									<c:when test="${article.promo == null || !article.promo.isDateValid()}">
										<c:out value="${formattedPrice}"/>
									</c:when>
									<%-- Absolute & pourcentage promos --%>
									<c:when test="${article.promo.promoType.getId() < 3}">
										<%-- Old price --%>
										<span style="text-decoration: line-through">
											<c:out value="${formattedPrice}" />
										</span>
										<%-- Price after promo --%>
										<fmt:formatNumber 
										var="afterPromoPrice" 
										type="number" 
										minFractionDigits="2" 
										maxFractionDigits="2" 
										value="${article.promo.promoType.getId() % 2 == 0 ?
												article.price - article.promo.getX()
												:
												article.price - (article.promo.getX() / 100) * article.price
											}" 
										/>
										<span>
											<c:out value="${afterPromoPrice}"/>
										</span>
									</c:when>
									<%-- Special marketing promo on articles --%>
									<c:when test="${article.promo.promoType.getId() > 4}">
										<fmt:formatNumber 
											var="promoX" 
											type="number" 
											minFractionDigits="0"
											maxFractionDigits="0" 
											value="${article.promo.getX()}" 
										/>
										<fmt:formatNumber 
											var="promoY" 
											type="number" 
											minFractionDigits="0"
											maxFractionDigits="0" 
											value="${article.promo.getY()}" 
										/>
										<c:set var="offer" value="${fn:replace(article.promo.promoType.getName(), 'X', promoX)}" />
										${fn:replace(offer, "Y", promoY)}
										&nbsp; | &nbsp;
										<c:out value="${formattedPrice}"/>
									</c:when>
								</c:choose>
								&euro;
							</span>
							<img width="170" height="170" src="<c:out value="${article.img}"/>"/>
							<br/>
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