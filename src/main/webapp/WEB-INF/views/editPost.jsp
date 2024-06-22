<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Post - Dijkstra</title>
    <link rel="stylesheet" type="text/css" href="/style/createPost.css">
    <link rel="stylesheet" type="text/css" href="/style/common.css">
</head>
<body>
<c:import url="//WEB-INF//views//header.jsp" />
<main>
    <form action="/posts/update" method="post" enctype="multipart/form-data" class="post-form">
        <input type="hidden" name="id" value="${post.id}" />
        <div class="post-input">
            <label for="content">수정하실 내용을 입력하세요</label>
            <textarea id="content" name="content" placeholder="내용을 입력해주세요." required>${post.content}</textarea>
        </div>
        <div class="upload-container">
            <div class="upload-handler-container">
                <div>
                    <input type="file" id="data" name="data" accept="image/*,video/*" hidden onchange="validateFile();">
                    <label for="data" class="upload-label">
                        <img src="/assets/upload.png" alt="Upload Icon">
                    </label>
                </div>
                <c:if test="${post.dataBase64 != null}">
                    <div id="remove-button-container">
                        <button type="button" class="image-delete-btn" onclick="removeFile()">파일 제거하기</button>
                    </div>
                </c:if>
            </div>
            <div id="file-name">
                <c:if test="${post.dataBase64 != null}">
                    <c:choose>
                        <c:when test="${post.isDataImage}">
                            <img src="data:image/jpeg;base64,${post.dataBase64}" alt="Image" class="img-preview"/>
                        </c:when>
                        <c:otherwise>
                            <video controls class="video-preview">
                                <source src="data:video/mp4;base64,${post.dataBase64}" type="video/mp4">
                            </video>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </div>
        </div>
        <button type="submit" class="submit-button">수정하기</button>
    </form>
</main>
<script>
    const removeButtonContainer = document.getElementById('remove-button-container');
    if (removeButtonContainer) {
        removeButtonContainer.style.display = 'none';
    }

    function validateFile() {
        const input = document.getElementById('data');
        const previewContainer = document.getElementById('file-name');
        const removeButtonContainer = document.getElementById('remove-button-container');
        const maxSize = 10 * 1024 * 1024;
        const validImageTypes = ['image/jpeg', 'image/png', 'image/webp'];
        const validVideoTypes = ['video/mp4', 'video/quicktime'];

        if (input.files.length > 0) {
            const file = input.files[0];

            if (file.size > maxSize) {
                alert('파일 크기가 너무 큽니다. 최대 10MB까지 업로드할 수 있습니다.');
                input.value = '';
                previewContainer.innerHTML = '';
                if (removeButtonContainer) {
                    removeButtonContainer.style.display = 'none';
                }
                return;
            }

            if (validImageTypes.includes(file.type) || validVideoTypes.includes(file.type)) {
                displayFilePreview();
            } else {
                alert('지원되지 않는 파일 형식입니다. 이미지는 JPEG, PNG, WEBP만, 비디오는 MP4, MOV만 업로드할 수 있습니다.');
                input.value = '';
                previewContainer.innerHTML = '';
                if (removeButtonContainer) {
                    removeButtonContainer.style.display = 'none';
                }
            }
        }
    }

    function displayFilePreview() {
        const input = document.getElementById('data');
        const previewContainer = document.getElementById('file-name');
        const removeButtonContainer = document.getElementById('remove-button-container');
        const file = input.files[0];

        const reader = new FileReader();
        reader.onload = function(e) {
            if (file.type.startsWith('image/')) {
                previewContainer.innerHTML = '<img src="' + e.target.result + '" alt="Image" class="img-preview" />';
            } else if (file.type.startsWith('video/')) {
                previewContainer.innerHTML = '<video controls class="video-preview"><source src="' + e.target.result + '" type="' + file.type + '"></video>';
            }
            if (removeButtonContainer) {
                removeButtonContainer.style.display = 'block';
            }
        };
        reader.readAsDataURL(file);
    }

    function removeFile() {
        document.getElementById('data').value = '';
        document.getElementById('file-name').innerHTML = '';
        const removeButtonContainer = document.getElementById('remove-button-container');
        if (removeButtonContainer) {
            removeButtonContainer.style.display = 'none';
        }
    }
</script>
</body>
</html>