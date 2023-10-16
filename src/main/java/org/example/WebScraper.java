package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class WebScraper {
    public static ArrayList<String> getNewsForSpecificSymbol(String symbol, int amount){
        try {
            Document document = Jsoup.connect("https://www.marketwatch.com/investing/stock/"+symbol).get();
            Elements news = document.getElementsByTag("mw-tabs");
            Elements articles = news.get(0).getElementsByClass("article__content");
            for (int i = 0; i < 2; i++){
                articles.remove(articles.size()-1);
            }
            ArrayList<String> linksToArticles = new ArrayList<>();
            int i = 0;
            for(Element article : articles){
                linksToArticles.add(article.getElementsByClass("link").get(0).attr("href"));
                if (i > amount-2){
                    break;
                }
                i++;
            }
            return linksToArticles;
        }catch (Exception e){
            return new ArrayList<>(){{add("Cannot get info about this stock");}};
        }
    }
}
