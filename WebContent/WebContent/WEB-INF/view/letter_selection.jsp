<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.UserDto" %>

<%
    UserDto loginUser = (UserDto) session.getAttribute("LOGIN_INFO");
%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>手紙交換サービス - 手紙を選ぶ</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Kaisei+Tokumin&display=swap" rel="stylesheet">
<style>
    :root {
        --background: oklch(0.98 0.01 300);
        --foreground: oklch(0.3 0 0);
        --card: oklch(1 0 0 / 0.8);
        --border: oklch(0.9 0.02 330);
        --primary: oklch(0.8 0.1 330);
        --primary-foreground: oklch(1 0 0);
        --secondary: oklch(0.85 0.02 240);
        --sidebar: oklch(1 0 0 / 0.6);
        --sidebar-foreground: oklch(0.3 0 0);
        --sidebar-accent: oklch(0.8 0.1 330);
        --radius: 0.5rem;
    }

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
        text-align: center;
    }
    .container {
        max-width: 800px;
        margin: 0 auto;
    }

    .container h1 {
        font-size: 2.5rem;
        color: var(--primary);
        margin-bottom: 1rem;
    }

    .container > p {
        font-size: 1.1rem;
        color: oklch(0.5 0.01 300);
        margin-bottom: 3rem;
    }

    .selection-grid {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 2rem;
    }

    .selection-card {
        background-color: var(--card);
        backdrop-filter: blur(10px);
        padding: 2rem;
        border-radius: var(--radius);
        border: 1px solid var(--border);
        box-shadow: 0 8px 32px 0 rgba(0,0,0,0.1);
        text-decoration: none;
        color: var(--foreground);
        transition: transform 0.3s, box-shadow 0.3s;
    }

    .selection-card:hover {
        transform: translateY(-10px);
        box-shadow: 0 12px 40px 0 rgba(0,0,0,0.15);
    }

    .selection-card .icon {
        font-size: 4rem;
        margin-bottom: 1rem;
    }

    .selection-card h2 {
        font-size: 1.8rem;
        margin: 0 0 0.5rem 0;
    }

    .selection-card .description {
        font-size: 0.9rem;
        line-height: 1.5;
    }

    .sakura h2 { color: var(--primary); }
    .snow h2 { color: var(--secondary); }

</style>
</head>
<body>

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
        <h1>手紙の種類を選択</h1>
        <p>お好みの手紙テンプレートをお選びください</p>

        <div class="selection-grid">
            <a href="ComposeLetter?theme=sakura" class="selection-card sakura">
                <div class="icon">🌸</div>
                <h2>桜の手紙</h2>
                <p class="description">桜の花びらが舞い散る美しい手紙テンプレート。 春の訪れや新しい始まりを表現するのに最適です。</p>
            </a>

            <a href="ComposeLetter?theme=snow" class="selection-card snow">
                <div class="icon">❄️</div>
                <h2>雪の手紙</h2>
                <p class="description">雪の結晶が舞う静寂な手紙テンプレート。 冬の思い出や静かな気持ちを伝えるのに最適です。</p>
            </a>
        </div>
    </div>
</div>

</body>
</html>
