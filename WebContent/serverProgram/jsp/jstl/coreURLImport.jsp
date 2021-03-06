<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Map, java.util.List, mvc.model.BoardVo" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<%--
import
<c:import url="import 할 주소" [var="변수명"] [scope="영역"] [charEncoding="charset"]>
<c:import url="https://www.daum.net" />
<c:import url="https://www.daum.net" var="daum" />
${daum }
 --%>

coreURL.jsp<br><br>
<c:import url="coreURL_target.jsp">
	<c:param name="userId" value="userId"/>
</c:import>
<br>
<br>
coreURL.jsp <br>

----------------------------------------------------<br>
<c:import varReader="reader" url="/serverProgram/jsp/jstl/coreURL_target.jsp"/>
<%--${reader } <br> --%>
<c:out value="${reader}"/> <br>
----------------------------------------------------<br>

네이버 검색결과 가져오기
<c:import var="inputHiddenVar" url="https://search.naver.com/search.naver" >
    <c:param name="query" value="input hidden"/>
</c:import>
${inputHiddenVar }<br>

<%--google 검색결과를 받아올 수 없다. --%>
<%--
<c:import var="inputHiddenVar" url="https://www.google.co.kr/search">
    <c:param name="q" value="site:okky.kr input hidden"/>
</c:import>
${inputHiddenVar }<br>
--%>

</body>
</html>