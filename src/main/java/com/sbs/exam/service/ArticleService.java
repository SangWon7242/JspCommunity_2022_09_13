package com.sbs.exam.service;

import com.sbs.exam.dao.ArticleDao;
import com.sbs.exam.dto.Article;

import java.sql.Connection;
import java.util.List;

public class ArticleService {
  private ArticleDao articleDao;
  public ArticleService(Connection con) {
    this.articleDao = new ArticleDao(con);
  }

  public int getItemsInAPage() {
    return 20;
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
}
