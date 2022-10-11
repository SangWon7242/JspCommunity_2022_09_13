<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="게시물 수정"></c:set>
<%@ include file="../part/head.jspf"%>
<h1>게시물 수정</h1>

<form action="doModify" method="POST">
  <input autocomplete="off" type="hidden" name="id" value="${param.id}">

  <div>번호 : ${articleRow.id}</div>
  <div>날짜 : ${articleRow.regDate}</div>
  <div>제목 : <input type="text" name="title" placeholder="제목을 입력해주세요." value="${articleRow.title}"></div>
  <div>내용 : <textarea name="body" placeholder="내용을 입력해주세요.">${articleRow.body}</textarea></div>
  <div>
    <button type="submit">수정</button>
    <a href="list">리스트</a>
  </div>
</form>
<%@ include file="../part/foot.jspf"%>