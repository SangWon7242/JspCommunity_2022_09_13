<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<head>
  <meta charset="UTF-8">
  <title>게시물 상세페이지</title>
</head>
<body>
    <h1>게시물 상세페이지</h1>

    <%@ include file="../part/topBar.jspf"%>

    <table>
      <div>번호 : ${article.id}</div>
      <div>날짜 : ${article.regDate}</div>
      <div>수정날짜 : ${article.updateDate}</div>
      <div>제목 : ${article.title}</div>
      <div>내용 : ${article.body}</div>

      <div>
        <a href="modify?id=${param.id}">수정</a>
        <a href="doDelete?id=${param.id}">삭제</a>
        <a href="list">리스트</a>
      </div>
    </table>
</body>
</html>