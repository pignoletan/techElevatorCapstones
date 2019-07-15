<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<c:set var="parkpathsrc" value="${fn:toLowerCase(park.parkCode)}"/>
<c:url var="parkImgSrc" value="/img/parks/${parkpathsrc}.jpg"/>
<img src = "${parkImgSrc}"/>
<p>${park.parkName}</p>
<p>${park.state}</p>
<p>${park.acreage} acres</p>
<p>${park.elevationInFeet} feet above sea level</p>
<p>Trails span ${park.milesOfTrail} miles</p>
<p>${park.numOfCampsites} campsites</p>
<p>${park.climate} climate</p>
<p>Founded in ${park.yearFounded}</p>
<p>${park.annualVisitorCount} visitors annually</p>
<p>"${park.inspirationalQuote}"<br/>-${park.quoteSource}</p>
<p>${park.parkDescription}</p>
<p><fmt:formatNumber value="${park.entryFee}" type="currency" /> entry fee</p>
<p>${park.numOfAnimalSpecies} species of animals within ${park.parkName}!</p>

<table>
<tr>
<td><h3>Today</h3></td>
</tr>
<tr>
<c:forEach var="weather" items="${fiveDayForecast}">

	<td>
		<c:url var="weatherImgSrc" value="/img/weather/${weather.imgName}"/>
		<a><img src="${weatherImgSrc}"/></a>
		<c:if test="${isFahrenheit}">
			<p>Low of ${weather.low}</p>
			<p>High of ${weather.high}</p>
		</c:if>
		<c:if test="${isFahrenheit == false}">
			<p>Low of ${weather.lowInCelsius}</p>
			<p>High of ${weather.highInCelsius}</p>
		</c:if>
	</td>

</c:forEach>
</tr>
</table>

<c:if test="${isFahrenheit}">
	<c:url var="celsiusPath" value="/changeTemp">
	<c:param name="parkCode" value="${park.parkCode}"/>
	<c:param name="isFahrenheit" value="false"/>
	</c:url>
	<p>Hate Fahrenheit? <a href="${celsiusPath}">Why not Celsius?</a></p>
</c:if>
<c:if test="${isFahrenheit == false}">
	<c:url var="fahrenheitPath" value="/changeTemp">
	<c:param name="parkCode" value="${park.parkCode}"/>
	<c:param name="isFahrenheit" value="true"/>
	</c:url>
	<p>Mistakenly think that Celsius is bad? <a href ="${fahrenheitPath}">You caaaaaan use Fahrenheit</a></p>
</c:if>

<c:forEach var ="advisory" items="${fiveDayForecast[0].weatherAdvisory}">

	<p>${advisory}</p>

</c:forEach>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />