package org.example.Entities;

public class Order {
    String Article;
    double price;

    public Order(String article, double price) {
        Article = article;
        this.price = price;
    }

    public String getArticle() {
        return Article;
    }

    public void setArticle(String article) {
        Article = article;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "Article='" + Article + '\'' +
                ", price=" + price +
                '}';
    }
}
