package com.sbs.exam.service;


import com.sbs.exam.dao.ArticleDao;
import com.sbs.exam.dto.Article;
import com.sbs.exam.dto.ResultData;
import com.sbs.exam.util.Util;

import java.sql.Connection;
import java.util.List;

public class ArticleService {
  private ArticleDao articleDao;
  public ArticleService(Connection con) {
    this.articleDao = new ArticleDao(con);
  }

  public int getItemsInAPage() {
    return 5;
  }

  public int getForPrintListTotalPage() {
    int itemInAPage = getItemsInAPage();

    int totalCount = articleDao.getTotalCount();
    int totalPage = (int) Math.ceil((double)totalCount / itemInAPage);
    return totalPage;
  }

  public List<Article> getForPrintArticles(int page) {
    int itemInAPage = getItemsInAPage();
    int limitFrom = (page - 1) * itemInAPage;

    List<Article> articles = articleDao.getArticles(itemInAPage, limitFrom);

    return articles;
  }

  public ResultData write(String title, String body, int loginedMemberId) {
    int id = articleDao.write(title, body, loginedMemberId);

    return ResultData.from("S-1", Util.f("%d번 게시물이 생성되었습니다.", id), "id", id);
  }

  public Article getForPrintArticleById(int id) {
    return articleDao.getForPrintArticleById(id);
  }

  public ResultData delete(int id) {
    articleDao.delete(id);
    return ResultData.from("S-1", Util.f("%d번 게시물이 삭제 되었습니다.", id), "id", id);
  }

  public ResultData modify(int id, String title, String body) {
    articleDao.modify(id, title, body);
    return ResultData.from("S-1", Util.f("%d번 게시물이 수정 되었습니다.", id), "id", id);
  }
}
