<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Dijkstra</title>
    <link rel="stylesheet" type="text/css" href="/style/login.css">
    <link rel="stylesheet" type="text/css" href="/style/common.css">
</head>
<body>
    <div class="container">
        <div class="logo">DIJKSTRA</div>
        <form action="/login" method="post" class="form">
            <input type="text" name="loginId" placeholder="아이디를 입력하세요." required>
            <input type="password" name="password" placeholder="비밀번호를 입력하세요." required>
            <button type="submit">로그인</button>
            <div class="signup-link">
                <a href="/register">회원가입 하기</a>
            </div>
        </form>
    </div>
</body>
</html>
