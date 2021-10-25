<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<h2 align="center">Panier</h2>
</br>
<c:choose>
	<c:when test="${empty cart.elements}">
		<p>Panier vide</p>
	</c:when>

	<c:otherwise>
		<c:set var="total" value="0" scope="page" />
		<table width="300">
		<c:forEach items="${cart.elements}" var="element">
			<tr>
				<td rowspan="2"><img width="50" height="50" src="<c:out value="${element.article.img}"/>"/></td>
				<td><c:out value="${element.article.name}"/></td>
				<td align="right"><span class="glyphicon glyphicon-remove removeFromCart" style="color:red" data-ref="<c:out value="${element.article.id}"/>"></span></td>
			</tr>
			<tr>
				<td>Quantité : <input type="number" min="1" value="<c:out value="${element.quantite}"/>" id= "qty<c:out value="${element.article.id}"/>" onchange="cartQuantityChanged('qty<c:out value="${element.article.id}"/>',<c:out value="${element.article.id}"/>);" style="width: 50px; text-align: center;" /></td>
				<fmt:formatNumber var="formattedPrice" type="number" minFractionDigits="2" maxFractionDigits="2" value="${element.article.price*element.quantite/100}" />
				<td align="right"><c:out value="${formattedPrice}"/> &euro;</td> 
				<c:set var="total" value="${total + (element.article.price*element.quantite)}" scope="page" />
			</tr>
		</c:forEach>
		</table>
		<fmt:formatNumber var="formattedPrice" type="number" minFractionDigits="2" maxFractionDigits="2" value="${total/100}" />
		<div align="right" style="margin-top:10px; font-weight: bold;">Total : <c:out value="${formattedPrice}"/> &euro;</div>
		
	</c:otherwise>
</c:choose>
</br>
<a class="btn btn-primary" href="cart/1/validate.html">Commander</a>
	
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
		data:{ ref }
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


