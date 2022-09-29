package com.sbs.exam.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/home/main")
public class HomeMainServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    req.setCharacterEncoding("UTF-8");
    resp.setCharacterEncoding("UTF-8");
    resp.setContentType("text/html; charset-utf-8");

    HttpSession session = req.getSession();

    boolean isLogined = false;
    int loginedMemberId = -1;

    if ( session.getAttribute("loginedMemberId") != null) {
      loginedMemberId = (int) session.getAttribute("loginedMemberId");
      isLogined = true;
    }

    req.setAttribute("isLogined", isLogined);
    req.setAttribute("loginedMemberId", loginedMemberId);

    req.getRequestDispatcher("../home/main.jsp").forward(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
