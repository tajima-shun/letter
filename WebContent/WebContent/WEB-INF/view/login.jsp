<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>手紙交換サービス - ログイン</title>
<link href="https://fonts.googleapis.com/css2?family=Lato:wght@400;700&display=swap" rel="stylesheet">
<style>
    body {
        font-family: 'Lato', sans-serif;
        background-color: #f4f4f9;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
        margin: 0;
    }
    .login-container {
        background-color: #fff;
        padding: 40px;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        width: 100%;
        max-width: 400px;
        text-align: center;
    }
    .login-container h1 {
        color: #0056b3;
        margin-bottom: 10px;
    }
    .login-container p {
        color: #666;
        margin-bottom: 30px;
    }
    .form-group {
        margin-bottom: 20px;
        text-align: left;
    }
    .form-group label {
        display: block;
        margin-bottom: 5px;
        font-weight: bold;
    }
    .form-group input {
        width: 100%;
        padding: 12px;
        border: 1px solid #ccc;
        border-radius: 4px;
        box-sizing: border-box;
    }
    .form-group input:focus {
        border-color: #007bff;
        outline: none;
    }
    .btn {
        width: 100%;
        background-color: #007bff;
        color: #fff;
        padding: 12px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-size: 1.1em;
        font-weight: bold;
    }
    .btn:hover {
        background-color: #0056b3;
    }
</style>
</head>
<body>

<div class="login-container">
    <h1>ようこそ</h1>
    <p>手紙交換サービスへ</p>
    <form action="ExecuteLogin" method="post">
        <div class="form-group">
            <label for="ID_USER_NAME">ユーザー名</label>
            <input type="text" name="USER_NAME" maxlength="255" id="ID_USER_NAME" required>
        </div>
        <div class="form-group">
            <label for="ID_PASSWORD">パスワード</label>
            <input type="password" name="PASSWORD" maxlength="255" id="ID_PASSWORD" required>
        </div>
        <input type="submit" value="ログイン" class="btn">
    </form>
</div>

</body>
</html>