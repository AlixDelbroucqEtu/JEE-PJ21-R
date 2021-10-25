<%@include file="_header.jsp" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="panel panel-info">
    <div class="panel-heading">
        <h3 class="panel-title">Customer page</h3>
    </div>
    <div class="panel-body">
        <c:forEach items="${promos}" var="promo">
            <div class="panel panel-warning">
                <div class="panel-heading">
                    <h3 class="panel-title">used Promo  #${promo.id}</h3>
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