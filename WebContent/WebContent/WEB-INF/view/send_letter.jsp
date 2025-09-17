<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.UserDto" %>

<%-- リクエストスコープからデータを取得 --%>
<%
    UserDto loginUser = (UserDto) session.getAttribute("LOGIN_INFO");
    List<UserDto> userList = (List<UserDto>) request.getAttribute("userList");
    String theme = (String) request.getAttribute("theme");
    if (theme == null) theme = "sakura"; // Default theme
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>手紙交換サービス - 手紙を送る</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Kaisei+Tokumin&display=swap" rel="stylesheet">
<style>
    :root {
        --radius: 0.5rem;
    }

    /* Theme variables */
    .theme-sakura {
        --background: oklch(0.98 0.01 300);
        --foreground: oklch(0.3 0 0);
        --card: oklch(1 0 0 / 0.8);
        --primary: oklch(0.8 0.1 330);
        --primary-foreground: oklch(1 0 0);
        --border: oklch(0.9 0.02 330);
        --input: oklch(1 0 0);
        --ring: oklch(0.8 0.1 330 / 0.5);
        --sidebar: oklch(1 0 0 / 0.6);
        --sidebar-foreground: oklch(0.3 0 0);
        --sidebar-accent: oklch(0.8 0.1 330);
    }

    .theme-snow {
        --background: oklch(0.97 0.01 240);
        --foreground: oklch(0.3 0 0);
        --card: oklch(1 0 0 / 0.8);
        --primary: oklch(0.75 0.08 240);
        --primary-foreground: oklch(1 0 0);
        --border: oklch(0.9 0.01 240);
        --input: oklch(1 0 0);
        --ring: oklch(0.75 0.08 240 / 0.5);
        --sidebar: oklch(1 0 0 / 0.6);
        --sidebar-foreground: oklch(0.3 0 0);
        --sidebar-accent: oklch(0.75 0.08 240);
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
    .snow-flake { animation: snow-fall linear infinite; font-size: 1.2rem; color: var(--primary); }

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
        background-color: oklch(from var(--primary) l h c / 0.2);
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
    .header .user-info a:hover { color: oklch(from var(--primary) calc(l - 0.1) h c); }

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

    select, input[type="file"] {
        width: 100%;
        padding: 12px;
        border: 1px solid var(--border);
        border-radius: var(--radius);
        margin-top: 5px;
        box-sizing: border-box;
        background-color: var(--input);
        color: var(--foreground);
        transition: border-color 0.3s, box-shadow 0.3s;
    }
    select:focus, input[type="file"]:focus {
      outline: none;
      border-color: var(--primary);
      box-shadow: 0 0 0 3px var(--ring);
    }

    .btn {
        display: inline-block;
        background-color: var(--primary);
        color: var(--primary-foreground);
        padding: 12px 24px;
        border: none;
        border-radius: var(--radius);
        cursor: pointer;
        font-size: 1em;
        font-weight: bold;
        text-decoration: none;
        text-align: center;
        transition: background-color 0.3s;
    }
    .btn:hover { background-color: oklch(from var(--primary) calc(l - 0.1) h c); }

    .letter-form-container {
        background: var(--input);
        border-radius: var(--radius);
        position: relative;
        border: 1px solid var(--border);
        padding: 1rem; /* Add padding to house the textarea */
        background-image: url("data:image/svg+xml,%3Csvg width='60' height='60' viewBox='0 0 60 60' xmlns='http://www.w3.org/2000/svg'%3E%3Cg fill='none' fill-rule='evenodd'%3E%3Cg fill='%239C92AC' fill-opacity='0.05'%3E%3Cpath d='M36 34v-4h-2v4h-4v2h4v4h2v-4h4v-2h-4zm0-30V0h-2v4h-4v2h4v4h2V6h4V4h-4zM6 34v-4H4v4H0v2h4v4h2v-4h4v-2H6zM6 4V0H4v4H0v2h4v4h2V6h4V4H6z'/%3E%3C/g%3E%3C/g%3E%3C/svg%3E");
    }

    .letter-wrapper {
        position: relative;
    }

    /* Corner stamps */
    .letter-wrapper::before, .letter-wrapper::after,
    .letter-form-container::before, .letter-form-container::after {
        content: '';
        position: absolute;
        width: 20px;
        height: 20px;
        border: 1px solid var(--border);
        background-color: var(--background);
    }

    .letter-wrapper::before { top: -10px; left: -10px; border-right: none; border-bottom: none; }
    .letter-wrapper::after { top: -10px; right: -10px; border-left: none; border-bottom: none; }
    .letter-form-container::before { bottom: -10px; left: -10px; border-right: none; border-top: none; }
    .letter-form-container::after { bottom: -10px; right: -10px; border-left: none; border-top: none; }


    .letter-textarea {
        font-size: 1.3em;
        line-height: 1.8;
        background-image: linear-gradient(to bottom, transparent 0%, transparent calc(100% - 1px), var(--border) calc(100% - 1px));
        background-size: 100% 2.32em; /* 1.3em * 1.8 - adjusted */
        background-position-y: 0.4em;
        border: none;
        outline: none;
        resize: vertical;
        width: 100%;
        padding: 20px;
        background-color: oklch(0.99 0.005 90); /* Creamy background */
        min-height: 280px;
        box-sizing: border-box;
        color: var(--foreground);
        box-shadow: inset 0 2px 8px rgba(0,0,0,0.05);
    }

    .letter-textarea::placeholder { color: oklch(0.8 0.01 300); }

</style>
</head>
<body class="theme-<%= theme %>">

<div id="animation-container"></div>

<div class="sidebar">
    <h2>メニュー</h2>
    <ul>
        <li><a href="LetterMain">受信箱</a></li>
        <li><a href="ComposeLetter" class="active">手紙を送る</a></li>
        <li><a href="SentLetters">送信済み</a></li>
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
            <h2>メッセージを送る</h2>
            <form action="PostLetter" method="post" id="letterForm" enctype="multipart/form-data">
                <% if (userList != null && !userList.isEmpty()) { %>
                    <p>宛先：<br>
                        <select name="RECIPIENT_ID">
                            <% for (UserDto user : userList) { %>
                                <option value="<%= user.getUserId() %>"><%= escape(user.getUserName()) %></option>
                            <% } %>
                        </select>
                    </p>
                    <p style="margin-top: 1.5rem;">メッセージ：<br>
                        <div class="letter-form-container">
                            <div class="letter-wrapper">
                                <textarea name="MESSAGE" class="letter-textarea" rows="10" placeholder="ここに手紙を綴ってください..."></textarea>
                            </div>
                        </div>
                    </p>
                    <p style="margin-top: 1.5rem;">画像：<br>
                        <input type="file" name="IMAGE" accept="image/*">
                    </p>
                    <p style="margin-top: 2rem; text-align: right;"><input type="submit" value="送信する" class="btn"></p>
                <% } else { %>
                    <p style="color: #666;">送信できる相手がいません。</p>
                <% } %>
            </form>
        </div>

    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const theme = "<%= theme %>";
        const container = document.getElementById('animation-container');
        const count = 20;

        if (theme === 'sakura') {
            for (let i = 0; i < count; i++) {
                const petal = document.createElement('div');
                petal.className = 'sakura-petal';
                petal.innerHTML = '🌸';
                petal.style.left = `${Math.random() * 100}vw`;
                petal.style.animationDuration = `${5 + Math.random() * 5}s`;
                petal.style.animationDelay = `${Math.random() * 5}s`;
                container.appendChild(petal);
            }
        } else if (theme === 'snow') {
            for (let i = 0; i < count; i++) {
                const flake = document.createElement('div');
                flake.className = 'snow-flake';
                flake.innerHTML = '❄️';
                flake.style.left = `${Math.random() * 100}vw`;
                flake.style.animationDuration = `${6 + Math.random() * 6}s`;
                flake.style.animationDelay = `${Math.random() * 6}s`;
                container.appendChild(flake);
            }
        }
    });
</script>

</body>
</html>

<%!
    private String escape(String str) {
        if (str == null) return "";
        return str.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;").replaceAll("'", "&#39;");
    }
%>