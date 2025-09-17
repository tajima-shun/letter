<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.UserDto" %>
<%@ page import="model.LetterDto" %>

<%-- リクエストスコープからデータを取得 --%>
<%
    UserDto loginUser = (UserDto) session.getAttribute("LOGIN_INFO");
    List<LetterDto> letterList = (List<LetterDto>) request.getAttribute("letterList");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>手紙交換サービス - 送信済み</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Kaisei+Tokumin&display=swap" rel="stylesheet">
<style>
    :root {
        --background: oklch(0.98 0.01 300); /* Light cherry blossom pink */
        --foreground: oklch(0.3 0 0); /* Dark gray for text */
        --card: oklch(1 0 0 / 0.8);
        --card-foreground: oklch(0.3 0 0);
        --primary: oklch(0.8 0.1 330);
        --primary-foreground: oklch(1 0 0);
        --secondary: oklch(0.85 0.02 240);
        --secondary-foreground: oklch(1 0 0);
        --border: oklch(0.9 0.02 330);
        --input: oklch(1 0 0);
        --ring: oklch(0.8 0.1 330 / 0.5);
        --sidebar: oklch(1 0 0 / 0.6);
        --sidebar-foreground: oklch(0.3 0 0);
        --sidebar-accent: oklch(0.8 0.1 330);
        --radius: 0.5rem;
    }

    /* Animations */
    @keyframes sakura-fall {
        0% { transform: translateY(-10vh) rotate(0deg); opacity: 1; }
        100% { transform: translateY(110vh) rotate(360deg); opacity: 0; }
    }

    @keyframes snow-fall {
        0% { transform: translateY(-10vh) translateX(0px); opacity: 1; }
        100% { transform: translateY(110vh) translateX(100px); opacity: 0; }
    }

    .sakura-petal, .snow-flake {
        position: absolute;
        pointer-events: none;
        z-index: -1;
    }
    .sakura-petal { animation: sakura-fall linear infinite; font-size: 1.5rem; color: var(--primary); }
    .snow-flake { animation: snow-fall linear infinite; font-size: 1.2rem; color: var(--secondary); }

    /* Base styles */
    body {
        font-family: 'Kaisei Tokumin', serif;
        background-color: var(--background);
        color: var(--foreground);
        margin: 0;
        padding: 0;
        display: flex;
        overflow: hidden;
    }

    #animation-container {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        pointer-events: none;
        z-index: 9999;
    }

    .sidebar {
        width: 240px;
        background-color: var(--sidebar);
        backdrop-filter: blur(10px);
        color: var(--sidebar-foreground);
        padding: 20px;
        height: 100vh;
        border-right: 1px solid var(--border);
        flex-shrink: 0;
    }
    .sidebar h2 {
        margin-top: 0;
        color: var(--primary);
        border-bottom: 1px solid var(--border);
        padding-bottom: 10px;
        text-align: center;
        font-size: 1.2rem;
    }
    .sidebar ul {
        list-style: none;
        padding: 0;
        margin-top: 2rem;
    }
    .sidebar ul li a {
        color: var(--sidebar-foreground);
        text-decoration: none;
        display: block;
        padding: 12px 18px;
        border-radius: var(--radius);
        margin-bottom: 0.5rem;
        transition: background-color 0.3s;
    }
    .sidebar ul li a:hover {
        background-color: oklch(0.9 0.02 330 / 0.5);
    }
    .sidebar ul li a.active {
        background-color: var(--sidebar-accent);
        color: var(--primary-foreground);
        font-weight: bold;
    }

    .main-content {
        flex-grow: 1;
        padding: 40px;
        overflow-y: auto;
        height: 100vh;
    }
    .container {
        max-width: 800px;
        margin: 0 auto;
    }

    .header {
        background-color: transparent;
        padding: 0;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 2rem;
    }
    .header h1 {
        margin: 0;
        font-size: 1.8em;
        color: var(--primary);
    }
    .header .user-info a {
        text-decoration: none;
        color: var(--primary);
        font-weight: bold;
        transition: color 0.3s;
    }
    .header .user-info a:hover { color: oklch(0.7 0.1 330); }

    .card {
        background-color: var(--card);
        backdrop-filter: blur(10px);
        padding: 30px;
        border-radius: var(--radius);
        border: 1px solid var(--border);
        box-shadow: 0 8px 32px 0 rgba(0,0,0,0.1);
        margin-bottom: 20px;
    }
    .card h2 {
        margin-top: 0;
        border-bottom: 1px solid var(--border);
        padding-bottom: 10px;
        color: var(--primary);
    }

    .btn {
        display: inline-block;
        background-color: var(--primary);
        color: var(--primary-foreground);
        padding: 10px 20px;
        border: none;
        border-radius: var(--radius);
        cursor: pointer;
        font-size: 0.9em;
        font-weight: bold;
        text-decoration: none;
        text-align: center;
        transition: background-color 0.3s;
    }
    .btn:hover { background-color: oklch(0.7 0.1 330); }
    .btn.delete {
        background-color: oklch(0.8 0.1 20);
    }
    .btn.delete:hover {
        background-color: oklch(0.7 0.1 20);
    }

    /* New Letter Styles */
    .letter-wrapper {
        position: relative;
    }
    .letter-wrapper::before, .letter-wrapper::after,
    .message-card::before, .message-card::after {
        content: '';
        position: absolute;
        width: 20px;
        height: 20px;
        border: 1px solid var(--border);
        background-color: var(--background);
        z-index: 1;
    }
    .letter-wrapper::before { top: -10px; left: -10px; border-right: none; border-bottom: none; }
    .letter-wrapper::after { top: -10px; right: -10px; border-left: none; border-bottom: none; }
    .message-card::before { bottom: -10px; left: -10px; border-right: none; border-top: none; }
    .message-card::after { bottom: -10px; right: -10px; border-left: none; border-top: none; }

    /* Message list styles */
    .message-card {
        background: var(--input);
        border: 1px solid var(--border);
        padding: 1.5rem;
        border-radius: var(--radius);
        margin-bottom: 2.5rem;
        position: relative;
        background-image: url("data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%239C92AC' fill-opacity='0.05'%3E%3Cpath d='M36 34v-4h-2v4h-4v2h4v4h2v-4h4v-2h-4zm0-30V0h-2v4h-4v2h4v4h2V6h4V4h-4zM6 34v-4H4v4H0v2h4v4h2v-4h4v-2H6zM6 4V0H4v4H0v2h4v4h2V6h4V4H6z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E");
    }

    .message-header {
        padding: 0 1rem 1rem 1rem;
        border-bottom: 1px dashed var(--border);
        margin-bottom: 1rem;
    }
    .message-header strong {
        font-size: 1.1em;
        color: var(--primary);
    }

    .message-body {
        white-space: pre-wrap;
        word-wrap: break-word;
        margin: 0;
        color: var(--card-foreground);
        font-size: 1.2em;
        line-height: 1.8;
        background-image: linear-gradient(to bottom, transparent 0%, transparent calc(100% - 1px), var(--border) calc(100% - 1px));
        background-size: 100% 2.16em; /* 1.2em * 1.8 */
        background-position-y: 0.4em;
        background-color: oklch(0.99 0.005 90);
        padding: 1rem;
        border-radius: var(--radius);
        box-shadow: inset 0 2px 8px rgba(0,0,0,0.05);
        min-height: 100px;
    }
    .message-image {
        max-width: 200px;
        height: auto;
        border-radius: var(--radius);
        margin-top: 1rem;
        border: 1px solid var(--border);
    }
    .message-footer {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-top: 1rem;
        padding: 0 1rem;
        color: oklch(0.6 0.01 300);
        font-size: 0.9em;
    }
    .actions a {
        margin-right: 10px;
        color: var(--primary);
        text-decoration: none;
        font-weight: bold;
    }

</style>
</head>
<body>

<div id="animation-container"></div>

<div class="sidebar">
    <h2>メニュー</h2>
    <ul>
        <li><a href="LetterMain">受信箱</a></li>
        <li><a href="ComposeLetter">手紙を送る</a></li>
        <li><a href="SentLetters" class="active">送信済み</a></li>
    </ul>
</div>

<div class="main-content">
    <div class="container">

        <div class="header">
            <h1>手紙交換サービス</h1>
            <div class="user-info">
                <% if (loginUser != null) { %>
                    <span><%= escape(loginUser.getUserName()) %>さん</span>
                    <a href="ExecuteLogout" style="margin-left: 15px;">ログアウト</a>
                <% } %>
            </div>
        </div>

        <div class="card">
            <h2>送信済みボックス</h2>
            <% if (letterList == null || letterList.isEmpty()) { %>
                <p>送信済みのメッセージはありません。</p>
            <% } else { %>
                <% for (LetterDto letter : letterList) { %>
                    <div class="message-card sent">
                        <div class="letter-wrapper">
                            <div class="message-header">
                                <strong>To: <%= escape(letter.getRecipientName()) %></strong>
                            </div>

                            <p class="message-body"><%= escape(letter.getMessage()) %></p>

                            <% if (letter.getImageFilename() != null) { %>
                                <img src="uploads/<%= letter.getImageFilename() %>" alt="添付画像" class="message-image">
                            <% } %>

                            <div class="message-footer">
                                <span><%= letter.getCreatedAt() %></span>
                                <div class="actions" style="display: flex; align-items: center; gap: 10px;">
                                    <a href="EditLetter?letter_id=<%= letter.getLetterId() %>">編集</a>
                                    <form action="DeleteSentLetter" method="post" class="delete-form" style="margin: 0;">
                                        <input type="hidden" name="LETTER_ID" value="<%= letter.getLetterId() %>">
                                        <input type="submit" value="削除" class="btn delete">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                <% } %>
            <% } %>
        </div>

    </div>
</div>

<script>
    // Animation script
    document.addEventListener('DOMContentLoaded', function() {
        const container = document.getElementById('animation-container');
        const petalCount = 20;
        const snowCount = 20;

        for (let i = 0; i < petalCount; i++) {
            const petal = document.createElement('div');
            petal.className = 'sakura-petal';
            petal.innerHTML = '🌸';
            petal.style.left = `${Math.random() * 100}vw`;
            petal.style.animationDuration = `${5 + Math.random() * 5}s`;
            petal.style.animationDelay = `${Math.random() * 5}s`;
            container.appendChild(petal);
        }

        for (let i = 0; i < snowCount; i++) {
            const flake = document.createElement('div');
            flake.className = 'snow-flake';
            flake.innerHTML = '❄️';
            flake.style.left = `${Math.random() * 100}vw`;
            flake.style.animationDuration = `${6 + Math.random() * 6}s`;
            flake.style.animationDelay = `${Math.random() * 6}s`;
            container.appendChild(flake);
        }
    });

    // Delete confirmation script
    document.addEventListener('DOMContentLoaded', function() {
        const deleteForms = document.querySelectorAll('.delete-form');
        deleteForms.forEach(form => {
            form.addEventListener('submit', function(event) {
                const confirmed = confirm('本当にこのメッセージを削除しますか？\nこの操作は取り消せません。');
                if (!confirmed) {
                    event.preventDefault(); // Cancel form submission
                }
            });
        });
    });
</script>

</body>
</html>

<%!
    private String escape(String str) {
        if (str == null) return "";
        return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;".replaceAll("'", "&#39;"));
    }
%>