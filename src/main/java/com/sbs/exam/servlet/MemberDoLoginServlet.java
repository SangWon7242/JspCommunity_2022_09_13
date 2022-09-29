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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

@WebServlet("/member/doLogin")
public class MemberDoLoginServlet extends HttpServlet {
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

      String loginId = req.getParameter("loginId");
      String loginPw = req.getParameter("loginPw");

      SecSql sql = SecSql.from("SELECT *");
      sql.append("FROM member");
      sql.append("WHERE loginId = ?", loginId);

      Map<String, Object> memberRow = DBUtil.selectRow(con, sql);

      if (memberRow.isEmpty()) {
        resp.getWriter().append(String.format("<script> alert('%s (은)는 존재하지 않는 아이디 입니다.'); location.replace('../home/main'); </script>", loginId));

        return;
      }

      if (((String) memberRow.get("loginPw")).equals(loginPw) == false) {
        resp.getWriter().append(String.format("<script> alert('로그인 비번이 일치하지 않습니다.'); history.back(); </script>"));

        return;
      }

      HttpSession session = req.getSession();
      session.setAttribute("loginedMemberId", memberRow.get("id"));

      resp.getWriter().append(String.format("<script> alert('로그인 성공'); location.replace('../home/main'); </script>"));

    } catch (
        SQLException e) {
      e.printStackTrace();
    } catch (SQLErrorException e) {
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
