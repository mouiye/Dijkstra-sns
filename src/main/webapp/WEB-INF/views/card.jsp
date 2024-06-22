<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach var="post" items="${posts}">
    <div class="post">
        <div class="post-header-btn-wrap">
            <c:set var="myId" value="${pageContext.request.userPrincipal.name}" />
            <c:if test="${post.userId == myId}">
                <form class="delete-post-form" action="/posts/delete?id=${post.id}" method="post" onsubmit="return confirmDelete('정말 이 게시물을 삭제하시겠습니까?')">
                    <button type="submit" class="delete-btn">삭제</button>
                </form>
            </c:if>
            <c:if test="${post.userId == myId}">
                <a href="/posts/edit/${post.id}">
                    <button type="submit" class="modify-btn">수정</button>
                </a>
            </c:if>
        </div>
        <div class="post-header">
            <a href="/profile/${post.userId}">
                <div class="post-author"><c:out value="${post.userId}" /></div>
            </a>
            <div class="post-timestamp">${post.timeAgo}</div>
        </div>
        <div class="post-content">
            <c:if test="${post.dataBase64 != null}">
                <c:choose>
                    <c:when test="${post.isDataImage}">
                        <img src="data:image/jpeg;base64,${post.dataBase64}" alt="Image"/>
                    </c:when>
                    <c:otherwise>
                        <video controls>
                            <source src="data:video/mp4;base64,${post.dataBase64}" type="video/mp4">
                            Your browser does not support the video tag.
                        </video>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
        <div class="post-details">
            <p><c:out value="${post.content}" /></p>
        </div>

        <div class="post-actions">
            <c:choose>
                <c:when test="${!post.isLikedByMe}">
                    <form action="/posts/like?id=${post.id}" method="post">
                        <button class="button-inactive">♥ ${post.likedBy.size()}</button>
                    </form>
                </c:when>
                <c:otherwise>
                    <form action="/posts/unlike?id=${post.id}" method="post" >
                        <button class="button-active">♥ ${post.likedBy.size()}</button>
                    </form>
                </c:otherwise>
            </c:choose>
            <button type="button" class="post-actions-comment-btn" onclick="toggleComments('${post.id}')">💬 ${post.comments.size()}</button>
        </div>

        <div class="comments" id="comments-${post.id}" style="display: block;">
            <form action="/comments/create" method="post" class="comments-form">
                <input type="hidden" name="postId" value="${post.id}" />
                <input type="text" class="comments-input" name="content" placeholder="댓글을 입력하세요" required />
                <button type="submit" class="comments-btn">댓글 달기</button>
            </form>
            <div class="comment-wrap">
                <c:forEach var="comment" items="${post.comments}">
                    <div class="comment">
                        <div class="comment-header">
                            <p>${comment.userId}: <c:out value="${comment.content}" /></p>
                            <c:set var="myId" value="${pageContext.request.userPrincipal.name}" />
                            <c:if test="${comment.userId == myId}">
                                <form class="delete-comment-form" action="/comments/delete?id=${comment.id}" method="post" onsubmit="return confirmDelete('정말 이 댓글을 삭제하시겠습니까?')">
                                    <button type="submit" class="delete-btn">삭제</button>
                                </form>
                            </c:if>
                        </div>
                        <c:choose>
                            <c:when test="${!comment.isLikedByMe}">
                                <form action="/comments/like?id=${comment.id}" method="post">
                                    <button class="button-inactive">♥ ${comment.likedBy.size()}</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="/comments/unlike?id=${comment.id}" method="post" >
                                    <button class="button-active">♥ ${comment.likedBy.size()}</button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</c:forEach>

<script>
    function confirmDelete(message) {
        return confirm(message);
    }

    function toggleComments(postId) {
        const commentsDiv = document.getElementById('comments-' + postId);
        if (commentsDiv) {
            if (commentsDiv.style.display === 'none' || commentsDiv.style.display === '') {
                commentsDiv.style.display = 'block';
            } else {
                commentsDiv.style.display = 'none';
            }
        }
    }
</script>