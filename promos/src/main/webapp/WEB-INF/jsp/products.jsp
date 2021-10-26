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
                    <c:set var="isInCart" value="${cartService.isInCart(1, article.id)}" scope="page" />
					<li <c:if test="${isInCart}">style="border: solid green 2px;"</c:if>>
						<a href="#">
							<span class="price">
							    <fmt:formatNumber var="formattedPrice" type="number" minFractionDigits="2" maxFractionDigits="2" value="${article.price}" />
								<c:out value="${formattedPrice}"/> &euro;
							</span>
                            <img src="<c:out value="${article.img}"/>"/><br/>
							<c:out value="${article.libelle}"/> <br/>
						</a>

						<c:choose>
                        	<c:when test="${isInCart}">
                                <p style="color:green;">Déjà dans le panier</p>
                        	</c:when>
                        	<c:otherwise>
                                <div style="position: absolute; margin-left:60px;">
                                    Quantité <input type="number" min="1" value="1" id= "quantity<c:out value="${article.id}"/>" style="width: 50px; text-align: center;"/>
                                </div>
                                <div>
                                    <span class="glyphicon glyphicon-plus-sign addToCart" data-ref="<c:out value="${article.id}"/>"></span>
                                </div>
                            </c:otherwise>
                        </c:choose>
					</li>
		</c:forEach>
		</ul>
	</c:otherwise>
</c:choose>

<%
jsList.add("cart.js");
%>
<%@include file="_footer.jsp" %>