<%@include file="_header.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="panel panel-success">
    <div class="panel-heading">
        <h3 class="panel-title">Ajouter une promo</h3>
    </div>
    <div class="panel-body">
        <form:form action="promo" method="post" modelAttribute="Promo">
            <div class="form-group">
                <label>Type de promo</label>
                <form:select path="promoType" class="form-control">
                    <c:forEach items="${promoTypes}" var="promoType">
                        <form:option value="${promoType.id}">${promoType.type}</form:option>
                    </c:forEach>
                </form:select>
            </div>
            <div class="form-group">
                <label>Valeur X</label>
                <form:input class="form-control" path="x"></form:input>
            </div>
            <div class="form-group">
                <label>Valeur Y</label>
                <form:input class="form-control" path="y"></form:input>
            </div>
            <div class="form-group">
                <label>Date de debut (yyyy-MM-dd)</label>
                <form:input type="date" class="form-control" path="start"></form:input>
            </div>
            <div class="form-group">
                <label>Date de fin (yyyy-MM-dd)</label>
                <form:input type="date" class="form-control" path="end"></form:input>
            </div>
            <div class="form-group">
                <label>Nombre de clients max</label>
                <form:input class="form-control" path="customerLimit"></form:input>
            </div>
            <div class="form-group">
                <label>Code</label>
                <form:input class="form-control" path="code"></form:input>
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
            <div class="panel panel-warning">
                <div class="panel-heading">
                    <h3 class="panel-title">Promo #${promo.id}</h3>
                </div>
                <div class="panel-body">
                    <ul>
                        <li>Type : ${promo.promoType.type}</li>
                        <li>X : ${promo.x}</li>
                        <li>Y : ${promo.y}</li>
                        <li>Debut : ${promo.start}</li>
                        <li>Fin : ${promo.end}</li>
                        <li>Nombre de clients max : ${promo.customerLimit}</li>
                        <li>Code : ${promo.code}</li>
                    </ul>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@include file="_footer.jsp" %>