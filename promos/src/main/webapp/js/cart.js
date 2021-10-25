
$(function() {
	var link  = document.createElement('link');
	link.rel  = 'stylesheet';
	link.type = 'text/css';
	link.href = 'css/cart.css';
	link.media = 'all';
	document.getElementsByTagName('head')[0].appendChild(link);
	
	$(".addToCart").click( function(e) {
		var ref = $(this).data("ref");
		var quantity = document.getElementById("quantity".concat(ref)).value;
		if (quantity<=1) {
			alert(quantity.concat(" article ajouté au panier"));
		} else {
			alert(quantity.concat(" articles ajoutés au panier"));
		}
		$.ajax({
			method: 'POST',
			url: "cart/1/add.json",
			dataType: "json",
			contentType: 'application/json',
			data: JSON.stringify( {id: ref, qty: quantity} )
		}).done(function(data){
			$.ajax({
				url: "cart/1.html"
			}).done(function(data){
				JSON.stringify( $('#cartInHeader').html(data) )
			});
		});
	});
	
	$.ajax({
		url: "cart/1.html"
	}).done(function(data){
		JSON.stringify( $('#cartInHeader').html(data) )
	});
});

$('.formatInteger').each(function(){
  let int = parseFloat($(this).text()).toFixed(2)
  $(this).html(int.toString().replace(".", ","))
})