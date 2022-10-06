package com.sbs.exam.controller;

import com.sbs.exam.Rq;
import com.sbs.exam.dto.Article;
import com.sbs.exam.service.ArticleService;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.util.List;

public class ArticleController extends Controller {

  private HttpServletRequest req;
  private HttpServletResponse resp;
  private Connection con;
  private ArticleService articleService;

  public ArticleController(HttpServletRequest req, HttpServletResponse resp, Connection con) {
    this.req = req;
    this.resp = resp;

    articleService = new ArticleService(con);
  }

  @Override
  public void performAction(Rq rq) {
    switch (rq.getActionMethodName()) {
      case "list":
        actionList(rq);
        break;
      case "write":
        actionShowWrite(rq);
        break;
      case "doWrite":
        actionDoWrite(rq);
        break;
    }
  }

  private void actionDoWrite(Rq rq) {
    HttpSession session = req.getSession();

    if (session.getAttribute("loginedMemberId") == null) {
      rq.appendBody(String.format("<script> alert('로그인 후 이용해주세요.'); location.replace('/jsp/member/login'); </script>"));
      return;
    }

    String title = req.getParameter("title");
    String body = req.getParameter("body");

    int loginedMemberId = (int) session.getAttribute("loginedMemberId");

    SecSql sql = SecSql.from("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?", title);
    sql.append(", body = ?", body);
    sql.append(", memberId = ?", loginedMemberId);

    int id = DBUtil.insert(con, sql);
    rq.appendBody(String.format("<script> alert('%d번 글이 등록되었습니다.'); location.replace('list'); </script>", id));
  }

  private void actionShowWrite(Rq rq) {
    rq.jsp("article/write");
  }

  public void actionList(Rq rq) {
    int page = 1;

    if (req.getParameter("page") != null && req.getParameter("page").length() != 0) {
      page = Integer.parseInt(req.getParameter("page"));
    }

    int totalPage = articleService.getForPrintListTotalPage();
    List<Article> articles = articleService.getForPrintArticles(page);

    req.setAttribute("articles", articles);
    req.setAttribute("page", page);
    req.setAttribute("totalPage", totalPage);

    rq.jsp("article/list");
  }
}
