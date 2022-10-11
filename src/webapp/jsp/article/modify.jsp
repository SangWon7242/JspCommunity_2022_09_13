<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<c:set var="pageTitle" value="게시물 수정"></c:set>
<%@ include file="../part/head.jspf"%>

<script>
  let ArticleModify__submitDone = false;

  function ArticleModify__submit(form) {
    if ( ArticleModify__submitDone ) {
      alert('처리 중입니다.');
      return;
    }

    form.title.value = form.title.value.trim();
    if ( form.title.value == 0 ) {
      alert('제목을 입력해주세요.');
      form.title.focus();

      return;
    }

    form.body.value = form.body.value.trim();
    if ( form.body.value == 0 ) {
      alert('내용을 입력해주세요.');
      form.body.focus();

      return;
    }

    form.submit();
    ArticleModify__submitDone = true;
  }
</script>

<h1>게시물 수정</h1>

<form action="doModify" method="POST" onsubmit="ArticleModify__submit(this); return false;">
  <input autocomplete="off" type="hidden" name="id" value="${param.id}">

  <div>번호 : ${article.id}</div>
  <div>날짜 : ${article.regDate}</div>
  <div>제목 : <input type="text" name="title" placeholder="제목을 입력해주세요." value="${article.title}"></div>
  <div>내용 : <textarea name="body" placeholder="내용을 입력해주세요.">${article.body}</textarea></div>
  <div>
    <button type="submit">수정</button>
    <a href="list">리스트</a>
  </div>
</form>
<%@ include file="../part/foot.jspf"%>