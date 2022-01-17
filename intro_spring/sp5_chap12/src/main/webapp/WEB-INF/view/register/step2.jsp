<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%> <!-- spring에서 제공하는 폼 태그 사용-->
<!DOCTYPE html>
<html>
<head>
    <title>회원가입</title>
</head>
<body>
    <h2>회원 정보 입력</h2>
    <form:form action="step3" modelAttribute="registerRequest">
        <p>
            <label>이메일:<br>
            <form:input path = "email" />
            </label>
        </p>
        <p>
            <label>이름:<br>
                <form:input path = "name" />
            </label>
        </p>
        <p>
            <label>비밀번:<br>
                <form:input path = "password" />
            </label>
        </p>
        <p>
            <label>비밀번호호 확:<br>
                <form:input path = "confirmPassword" />
            </label>
        </p>
        <input type="submit" value="가입 완료">
    </form:form>
</body>
</html>