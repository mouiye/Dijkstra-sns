<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up - Dijkstra</title>
    <link rel="stylesheet" type="text/css" href="/style/signup.css">
    <link rel="stylesheet" type="text/css" href="/style/common.css">
</head>
<body>
    <div class="container">
        <div class="logo">DIJKSTRA</div>
        <form action="/register" method="post" class="form">
            <input type="text" name="loginId" placeholder="아이디를 입력하세요." required>
            <input type="password" name="password" placeholder="비밀번호를 입력하세요." required>
            <input type="password" name="confirm_password" placeholder="비밀번호를 다시 입력하세요." required>
            <button type="submit">가입하기</button>
            <div class="signin-link">
                <a href="/login">로그인 하기</a>
            </div>
        </form>
    </div>
</body>
</html>
