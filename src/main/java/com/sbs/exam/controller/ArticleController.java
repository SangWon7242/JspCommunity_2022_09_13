package com.sbs.exam.controller;

import com.sbs.exam.dto.Article;
import com.sbs.exam.service.ArticleService;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleController {

  private HttpServletRequest req;
  private HttpServletResponse resp;
  private Connection con;
  private ArticleService articleService;

  public ArticleController(HttpServletRequest req, HttpServletResponse resp, Connection con) {
    this.req = req;
    this.resp = resp;

    articleService = new ArticleService(con);
  }

  public void actionList() throws ServletException, IOException {
    int page = 1;

    if(req.getParameter("page") != null && req.getParameter("page").length() != 0) {
      page = Integer.parseInt(req.getParameter("page"));
    }

    int totalPage = articleService.getForPrintListTotalPage();
    List<Article> articles = articleService.getForPrintArticles(page);

    req.setAttribute("articles", articles);
    req.setAttribute("page", page);
    req.setAttribute("totalPage", totalPage);
    req.getRequestDispatcher("/jsp/article/list.jsp").forward(req, resp);
  }
}
