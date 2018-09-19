<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html; ISO-8859-1;charset=utf-8" language="java" %>
<%@include file="common/tag.jsp" %>

<!DOCTYPE html>
<html>
<head>
    <title>列表详情</title>
    <%@include file="common/head.jsp" %>

</head>
<body>
<div class="container">
    <div class="panel panel-default">
        <div class="panel-heading text-center">
            <h2>秒杀列表</h2>
        </div>
        <div class="panel-body">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>商品名称</th>
                    <th>库存</th>
                    <th>开始时间</th>
                    <th>结束时间</th>
                    <th>创建时间</th>
                    <th>详情页</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${list}" var="sk" varStatus="status">
                    <td>${sk.name}</td>
                    <td>${sk.number}</td>
                    <td>
                        <fmt:formatDate value="${sk.startTime}" pattern="YYYY-mm-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${sk.endTime}" pattern="YYYY-mm-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${sk.createTime}" pattern="YYYY-mm-dd HH:mm:ss"/>
                    </td>
                    <td>
                        <a class="btn btn-info" href="/seckill/${sk.seckillId}/detail" target="_blank"/>
                    </td>
                    <tr></tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>
</div>

</body>
</html>

<!-- 新 Bootstrap 核心 CSS 文件 -->
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

<!-- 可选的Bootstrap主题文件（一般不使用） -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"></script>

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>