package com.sbs.exam.servlet;

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

    String url = "jdbc:mysql://127.0.0.1:3306/Jsp_Community?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
    String user = "sbsst";
    String password = "sbs123414";


    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html; charset-utf-8");

    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (
        ClassNotFoundException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      resp.getWriter().append("DB 드라이버 클래스 로딩 실패");
      return;
    }

    // DB 연결
    Connection con = null;

    try {
      con = DriverManager.getConnection(url, user, password);

      int id = Integer.parseInt(req.getParameter("id"));
      String title = req.getParameter("title");
      String body = req.getParameter("body");

      SecSql sql = SecSql.from("UPDATE article");
      sql.append("SET title = ?", title);
      sql.append(", body = ?", body);
      sql.append("WHERE id = ?", id);

      DBUtil.update(con, sql);
      resp.getWriter().append(String.format("<script> alert('%d번 글이 수정되었습니다.'); location.replace('detail?id=%d'); </script>", id, id));


    } catch (
        SQLException e) {
      e.printStackTrace();
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
