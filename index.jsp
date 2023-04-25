<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Reusable CAPTCHA Security Engine</title>
</head>
<body>
<h1>Reusable CAPTCHA Security Engine</h1>
<form action="submit" method="post">
    <p>
        <img src="captcha" alt="CAPTCHA"/>
        <br/>
        <label for="captcha">Type the text:</label>
        <input type="text" id="captcha" name="captcha"/>
    </p>
    <p>
        <input type="submit" value="Submit"/>
    </p>
</form>
</body>
</html>
