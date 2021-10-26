<%@ page import="fr.eservices.promos.model.PromoType" %>
<%@ page import="java.util.List" %>
<%@ page import="fr.eservices.promos.model.Article" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@include file="_header.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script
        src="https://code.jquery.com/jquery-3.6.0.min.js"
        integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
        crossorigin="anonymous"></script>


        <c:if test="${not empty param.erreur}">
            <div class="panel panel-danger">
                <div class="panel-heading">
                <c:if test="${param.erreur == 'date'}">
                    Les dates spécifiées sont incorrectes.
                </c:if>
                <c:if test="${param.erreur == 'article'}">
                    Aucun article sélectionné.
                </c:if>
                <c:if test="${param.erreur == 'xint'}">
                    La valeur de X doit être entière pour le type de promotion sélectionné.
                </c:if>
                </div>
            </div>
        </c:if>


<div class="panel panel-success">
    <div class="panel-heading">
        <h3 class="panel-title">Ajouter une promo</h3>
    </div>
    <div class="panel-body">
        <form:form action="promo" method="post" modelAttribute="Promo">
            <div class="form-group">
                <label>Type de promo</label>
                <form:select path="promoType" class="form-control">
                <%
                    String lastType = "";
                %>
                    <c:forEach items="${promoTypes}" var="promoType">
                        <c:set var="promoTypeType" value="${promoType.type}"/>
                        <%
                        String type = (String)pageContext.getAttribute("promoTypeType");
                        if(type != lastType){
                            if(lastType != "") {
                                 %>
                                </optgroup>
                                <%
                            }
                            %>
                            <optgroup label="<c:out value = "${promoType.type}"/>">
                            <%
                        }
                        lastType = type;
                        %>
                        <form:option value="${promoType.id}">${promoType.name}</form:option>
                    </c:forEach>
                    </optgroup>
                </form:select>
            </div>
            <div class="form-group">
                <label for='start'>Date de début</label>
                <form:input type="date" class="form-control" path="start"></form:input>
            </div>
            <div class="form-group">
                <label for='end'>Date de fin</label>
                <form:input type="date" class="form-control" path="end"></form:input>
            </div>
            <div id="adaptativeFields">
                <div class="form-group">
                    <label for='inputArticles'>Article(s) concerné(s)</label>
                    <select name="category" id="category">
                        <option selected value="0">Toutes les catégories</option>
                        <c:forEach items="${categories}" var="category">
                            <option value="${category.id}">${category.name}</option>
                        </c:forEach>
                    </select>
                    <input onKeyUp='searchArticles()' id="inputArticles" class="form-control" type="text" maxlength="30" placeholder="Chercher un article..."/>
                </div>
                <div id="selectedArticles">

                </div>
                <div class="form-group">
                    <label for='x'>Pourcentage</label>
                    <form:input type='number' min='0' max='100' step='.01' class="form-control" path="x"></form:input>
                </div>

            </div>
            <input type="submit" value="Ajouter la promo" class="btn btn-default">
        </form:form>
    </div>
</div>

<br>

<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">Liste des promos</h3>
    </div>
    <div class="panel-body">
        <c:forEach items="${promos}" var="promo">
            <div id='promo${promo.id}' class="panel panel-warning">
                <div class="panel-heading" style="display:flex;justify-content: space-between;align-items: baseline;">
                    <h3 class="panel-title">Promo #${promo.id}</h3><button onclick="removePromo(${promo.id})" class="float-right btn btn-danger">Supprimer</button>
                </div>
                <div class="panel-body">
                    <ul>
                        <li>Type : ${promo.promoType.type}</li>
                        <li>Nom : ${promo.promoType.name}</li>
                        <li>X : ${promo.x}</li>
                        <li>Y : ${promo.y}</li>
                        <li>Debut : ${promo.start}</li>
                        <li>Fin : ${promo.end}</li>
                        <li>Nombre de clients max : ${promo.customerLimit}</li>
                        <li>Code : ${promo.code}</li>
                        <li>Appliqué sur le panier : ${promo.onCart}</li>
                    </ul>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<script>

        const selectedArticles = new Map();

        function searchArticles(){
            $("#selectedArticles").empty();
            console.log($("#category").children("option:selected").val());
            $.ajax({
                url: 'match',
                method: 'POST',
                async: false,
                dataType: "json",
                contentType: 'application/json',
                data: JSON.stringify( {input: $("#inputArticles").val(), idcat: $("#category").children("option:selected").val()} ),
                success: function (data) {
                    for(let i=0; i<data.length; i++){
                        const color = selectedArticles.has(data[i].id) ? '#DFF0D8' : '#F8F8F8';
                        $("#selectedArticles").append("<div style='background-color: "+ color +"' onclick='selectArticle("+data[i].id+")' id='articleInput"+data[i].id+"' class='articleInput'><div class='articleInputLib'>"+data[i].libelle+"</div><div class='articleInputMarque'>"+data[i].marque+"</div><div class='articleInputPrix'>"+data[i].price+"€</div></div>");
                    }
                }
            });
        }

        $("#promoType").change(function () {
            $("#adaptativeFields").empty();
            $.ajax({
                url: 'adaptForm',
                method: 'POST',
                async: false,
                data: $("#promoType").val(),
                success: function (data) {
                    $("#adaptativeFields").append(data);
                }
            });
        });

        function selectArticle(id) {
            $.ajax({
                url: 'selectArticle',
                method: 'POST',
                async: false,
                data: id.toString(),
                success: function (data) {
                    if(data==0){
                        $("#articleInput"+id).css('background-color','#F8F8F8');
                        selectedArticles.delete(id);
                    }else{
                        $("#articleInput"+id).css('background-color','#DFF0D8');
                        selectedArticles.set(id, true);
                    }
                }
            });
        };

        function removePromo(id) {
            $.ajax({
                url: 'removePromo',
                method: 'POST',
                async: false,
                data: id.toString(),
                success: function (data) {
                    $("#promo"+id).remove();
                }
            });
        }

</script>

<%@include file="_footer.jsp" %>