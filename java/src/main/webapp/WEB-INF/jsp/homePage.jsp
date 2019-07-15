<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:import url="/WEB-INF/jsp/common/header.jsp" />
<section>
	<ul>
		<c:forEach var="park" items="${allParks}">
			<li class="picOfPark"><c:set var="parkpathsrc"
					value="${fn:toLowerCase(park.parkCode)}" /> <c:url
					var="parkImgSrc" value="/img/parks/${parkpathsrc}.jpg" /> <%-- image path --%>
				<c:url var="parkDetailPath" value="/parkDetail">
					<%-- Link path --%>
					<c:param name="parkCode" value="${park.parkCode}" />
				</c:url> <a href="${parkDetailPath}"><img src="${parkImgSrc}"
					alt="${park.parkName}" /></a></li>
			<li>${park.parkName}</li>
			<li>${park.state}</li>
			<li class="onePark">${park.parkDescription}</li>
		</c:forEach>
	</ul>
</section>
<c:import url="/WEB-INF/jsp/common/footer.jsp" />