<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.LetterDto" %>

<%
    LetterDto letter = (LetterDto) request.getAttribute("letter");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>メッセージの編集</title>
<link href="https://fonts.googleapis.com/css2?family=Lato:wght@400;700&display=swap" rel="stylesheet">
<style>
    body {
        font-family: 'Lato', sans-serif;
        background-color: #f4f4f9;
        color: #333;
        margin: 0;
        padding: 20px;
    }
    .container {
        max-width: 800px;
        margin: 0 auto;
    }
    .card {
        background-color: #fff;
        padding: 25px;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        margin-bottom: 20px;
    }
    .card h1 {
        margin-top: 0;
        border-bottom: 2px solid #eee;
        padding-bottom: 10px;
        font-size: 1.5em;
        color: #0056b3;
    }
    textarea, input[type="file"] {
        width: 100%;
        padding: 10px;
        border: 1px solid #ccc;
        border-radius: 4px;
        margin-top: 5px;
        box-sizing: border-box;
    }
    textarea {
        resize: vertical;
        min-height: 120px;
    }
    .btn {
        display: inline-block;
        background-color: #007bff;
        color: #fff;
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 1em;
        text-decoration: none;
        text-align: center;
        margin-right: 10px;
    }
    .btn:hover {
        background-color: #0056b3;
    }
    .btn.secondary {
        background-color: #6c757d;
    }
    .btn.secondary:hover {
        background-color: #5a6268;
    }
    .image-preview {
        margin-top: 10px;
    }
    .image-preview img {
        max-width: 200px;
        max-height: 200px;
        border-radius: 4px;
        border: 1px solid #ddd;
    }
    .delete-check {
        margin: 10px 0;
    }
</style>
</head>
<body>

<div class="container">
    <div class="card">
        <h1>メッセージを編集する</h1>

        <% if (letter != null) { %>
            <form action="UpdateLetter" method="post" enctype="multipart/form-data">
                <input type="hidden" name="LETTER_ID" value="<%= letter.getLetterId() %>">

                <p>メッセージ：<br>
                    <textarea name="MESSAGE" rows="10"><%= escape(letter.getMessage()) %></textarea>
                </p>

                <% if (letter.getImageFilename() != null && !letter.getImageFilename().isEmpty()) { %>
                    <div class="image-preview">
                        <p>現在の画像：</p>
                        <img src="uploads/<%= letter.getImageFilename() %>" alt="現在の画像">
                        <div class="delete-check">
                            <input type="checkbox" name="DELETE_IMAGE" value="true" id="delete_image">
                            <label for="delete_image">この画像を削除する</label>
                        </div>
                    </div>
                <% } %>

                <p>新しい画像をアップロード：<br>
                    <input type="file" name="NEW_IMAGE" accept="image/*">
                </p>

                <div>
                    <input type="submit" value="更新する" class="btn">
                    <a href="LetterMain" class="btn secondary">キャンセル</a>
                </div>
            </form>
        <% } else { %>
            <p style="color: red;">編集対象のメッセージが見つからないか、編集する権限がありません。</p>
            <a href="LetterMain" class="btn secondary">戻る</a>
        <% } %>
    </div>
</div>

</body>
</html>

<%!
    private String escape(String str) {
        if (str == null) return "";
        return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll("＞", "&gt;").replaceAll("\"", "&quot;").replaceAll("'", "&#39;");
    }
%>