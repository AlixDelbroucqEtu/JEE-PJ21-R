<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<h2 align="center">Panier</h2>
</br>
<c:choose>
	<c:when test="${empty cart.elements}">
		<p>Panier vide</p>
	</c:when>

	<c:otherwise>
		<c:set var="total" value="0" scope="page" />
		<table width="400px">
		<tbody>
		<c:forEach items="${cart.elements}" var="element">
			<tr style="border-top: 1px black solid;">
				<td rowspan="2"><img width="50" height="50" src="<c:out value="${element.article.img}"/>"/></td>
				<td><c:out value="${element.article.libelle}"/></td>
				<td align="right"><span class="glyphicon glyphicon-remove removeFromCart" data-ref="<c:out value="${element.article.id}"/>"></span></td>
			</tr>
			<tr>
				<td>Quantité : <input type="number" min="1" value="<c:out value="${element.quantite}"/>" id= "qty<c:out value="${element.article.id}"/>" onchange="cartQuantityChanged('qty<c:out value="${element.article.id}"/>',<c:out value="${element.article.id}"/>);" style="width: 50px; text-align: center;" /></td>
				<fmt:formatNumber var="formattedPrice" type="number" minFractionDigits="2" maxFractionDigits="2" value="${element.article.price*element.quantite}" />
				<td align="right">
					<c:out value="${formattedPrice}"/>
					<c:choose>
						<%-- Pas de promo --%>
						<c:when test="${element.article.promo == null || !element.article.promo.isDateValid()}">
						</c:when>
						<%-- Absolute & pourcentage promos --%>
						<c:when test="${element.article.promo.promoType.getId() < 3}">
							&nbsp; ➜ &nbsp;
							<%-- Price after promo --%>
							<fmt:formatNumber 
							var="afterPromoPrice" 
							type="number" 
							minFractionDigits="2" 
							maxFractionDigits="2" 
							value="${element.article.promo.promoType.getId() % 2 == 0 ?
									(element.article.price - element.article.promo.getX()) * element.quantite
									:
									(element.article.price - (element.article.promo.getX() / 100) * element.article.price) * element.quantite
								}" 
							/>
							<b>
								<c:out value="${afterPromoPrice}"/> €
							</b>
						</c:when>
						<%-- Special marketing promo on articles --%>
						<c:when test="${element.article.promo.promoType.getId() > 4}">
							&nbsp; ➜ &nbsp;
							<fmt:formatNumber 
								var="afterPromoPrice" 
								type="number" 
								minFractionDigits="2" 
								maxFractionDigits="2" 
								value="${element.getPriceAfterPromo()}" 
							/>
							<b>
								<c:out value="${afterPromoPrice}"/> €
							</b>
							<br/>
							<fmt:formatNumber 
								var="promoX" 
								type="number" 
								minFractionDigits="0"
								maxFractionDigits="0" 
								value="${element.article.promo.getX()}" 
							/>
							<fmt:formatNumber 
								var="promoY" 
								type="number" 
								minFractionDigits="0"
								maxFractionDigits="0" 
								value="${element.article.promo.getY()}" 
							/>
							<c:set var="offer" value="${fn:replace(element.article.promo.promoType.getName(), 'X', promoX)}" />
							${fn:replace(offer, "Y", promoY)}
						</c:when>
					</c:choose>
				</td>
				<%-- <c:set var="total" value="${total + (element.article.price*element.quantite)}" scope="page" /> --%>
				<c:set var="total" value="${total + (element.getPriceAfterPromo())}" scope="page" />
			</tr>
		</c:forEach>
		</tbody>
		</table>
		<fmt:formatNumber var="formattedTotal" type="number" minFractionDigits="2" maxFractionDigits="2" value="${total}" />
		<div align="right" style="margin-top:10px; font-weight: bold;">Total : <c:out value="${formattedTotal}"/> &euro;</div>

	</c:otherwise>
</c:choose>
</br>
<form method="post" action="/cart/1/promo-code">
	<div class="form-group">
		<label for='code'>Code promo</label>
		<input type="text" name="code" class="form-control" path="code" />
	</div>
	<div class="form-group">
		<input type="submit" value="Activer la promo" class="btn btn-info" />
	</div>
</form>
<hr>
<a class="btn btn-success" href="cart/1/validate">Commander</a>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script>
function cartQuantityChanged(textbox,id) {
	var textInput = document.getElementById(textbox).value;
	if (textInput==null || textInput<=0 || textInput=="") {
		document.getElementById(textbox).value = 1;
		textInput=1;
	}

	$.ajax({
		method: 'POST',
		url: "cart/1/update.json",
		dataType: "json",
		contentType: 'application/json',
		data: JSON.stringify( {id: id, qty: textInput} )
	}).done(function(data){
		$.ajax({
			url: "cart/1.html"
		}).done(function(data){
			JSON.stringify( $('#cartInHeader').html(data) )
		});
	});

}

$(".removeFromCart").click( function(e) {
	var ref = $(this).data("ref");

	$.ajax({
		method: 'POST',
		url: "cart/1/remove.json",
		dataType: "json",
		contentType: 'application/json',
		data: JSON.stringify( {id: ref, qty: 0} )
	}).done(function(data){
		$.ajax({
			url: "cart/1.html"
		}).done(function(data){
			JSON.stringify( $('#cartInHeader').html(data) )
		});
		location.reload(true);
	});
});
</script>