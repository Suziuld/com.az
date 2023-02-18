<%--
  Created by IntelliJ IDEA.
  User: Sunzhen
  Date: 2022/10/31
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="/user/loginout">退出登录</a>
<table border="1" align="center">
    <tr>
        <td>goodsid</td>
        <td>userid</td>
        <td>goodsname</td>
        <td>goodstype</td>
        <td>goodsrent</td>
        <td>releasetime</td>
        <td>state</td>
        <td>remark</td>
        <td>操作</td>
    </tr>

    <c:forEach items="${goodsinfo}" var="goods">
    <tr>
        <td>${goods.goodsid}</td>
        <td>${goods.userid}</td>
        <td>${goods.goodsname}</td>
        <td>${goods.goodstype}</td>
        <td>${goods.goodsrent}</td>
        <td>${goods.releasetime}</td>
        <td>${goods.state}</td>
        <td>${goods.remark}</td>
        <td>
            <button onclick="delete1(${user.id})">删除</button>
            <button onclick="update(${user.id})">修改</button>
        </td>
    </tr>
    </c:forEach>

</body>
</html>
