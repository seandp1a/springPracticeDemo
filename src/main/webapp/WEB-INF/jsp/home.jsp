<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<!DOCTYPE html>
	<html>
		<head>
			<script>

				setSessionStorage('UUID', '${logged_in}');
                setSessionStorage('token', '${TOKEN}');

                console.log('${logged_in}');
				// 設定前端 session
				function setSessionStorage(_Name, _Value) {
					var ItemName = _Name.toString();
					window.sessionStorage.removeItem(ItemName);
					window.sessionStorage.setItem(ItemName, _Value);
				}
			</script>
		</head>
		<body>
            <form action="${redirectRefURL}" method="get" name="Login">
        	</form>
		</body>
		<script>
            document.forms.Login.submit();
		</script>
	</html>