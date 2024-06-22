<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Profile - Dijkstra</title>
    <link rel="stylesheet" type="text/css" href="/style/profile.css">
    <link rel="stylesheet" type="text/css" href="/style/common.css">
</head>
<body>
    <c:import url="//WEB-INF//views//header.jsp" />
    <main>
        <div class="profile-header">
            <div class="profile-info">
                <h2><c:out value="${userId}" /></h2>
                <p>팔로워 ${followers.size()} 팔로잉 ${followings.size()}</p>
            </div>


            <c:set var="myId" value="${pageContext.request.userPrincipal.name}" />
            <c:choose>
                <c:when test="${userId.equals(myId)}">

                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${!isFollowing}">
                            <form action="/follow?friendId=${userId}" method="post">
                                <button class="follow-button-active">팔로잉</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <form action="/unfollow?friendId=${userId}" method="post">
                                <button class="follow-button-inactive">언팔로우</button>
                            </form>

                        </c:otherwise>
                    </c:choose>
                </c:otherwise>
            </c:choose>

        </div>
        <c:import url="//WEB-INF//views//card.jsp" />
    </main>
</body>
</html>
