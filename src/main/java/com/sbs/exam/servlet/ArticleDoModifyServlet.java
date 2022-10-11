package com.sbs.exam.servlet;

import com.sbs.exam.Config;
import com.sbs.exam.exception.SQLErrorException;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/article/doModify")
public class ArticleDoModifyServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html; charset-utf-8");

    String driverName = Config.getDriverClassName();

    try {
      Class.forName(driverName);
    } catch (
        ClassNotFoundException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      resp.getWriter().append("DB 드라이버 클래스 로딩 실패");
      return;
    }

    // DB 연결
    Connection con = null;

    try {
      con = DriverManager.getConnection(Config.getDBUrl(), Config.getDBId(), Config.getDBPw());




    } catch (
        SQLException e) {
      e.printStackTrace();
    } catch ( SQLErrorException e ) {
      e.getOrigin().printStackTrace();
    } finally {
      if (con != null) {
        try {
          con.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
