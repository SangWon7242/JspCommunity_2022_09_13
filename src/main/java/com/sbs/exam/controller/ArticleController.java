package com.sbs.exam.controller;

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
  public ArticleController(HttpServletRequest req, HttpServletResponse resp, Connection con) {
    this.req = req;
    this.resp = resp;
    this.con = con;
  }

  public void actionList() throws ServletException, IOException {
    int page = 1;

    if(req.getParameter("page") != null && req.getParameter("page").length() != 0) {
      page = Integer.parseInt(req.getParameter("page"));
    }

    int itemInAPage = 10;
    int limitFrom = (page - 1) * itemInAPage;

    SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");

    int totalCount = DBUtil.selectRowIntValue(con, sql);
    int totalPage = (int) Math.ceil((double)totalCount / itemInAPage);

    sql = SecSql.from("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");
    sql.append("LIMIT ?, ?", limitFrom, itemInAPage);

    List<Map<String, Object>> articleRows = DBUtil.selectRows(con, sql);

    req.setAttribute("articleRows", articleRows);
    req.setAttribute("page", page);
    req.setAttribute("totalPage", totalPage);
    req.getRequestDispatcher("../article/list.jsp").forward(req, resp);
  }
}
