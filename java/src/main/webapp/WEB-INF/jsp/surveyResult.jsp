<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:import url="/WEB-INF/jsp/common/header.jsp" />
<section>
	<ol>
		<c:forEach var="park" items="${winningParks}">
			<li>${park} votes</li>
		</c:forEach>
	</ol>
</section>
<c:import url="/WEB-INF/jsp/common/footer.jsp" />