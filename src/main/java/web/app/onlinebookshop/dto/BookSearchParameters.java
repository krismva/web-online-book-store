package web.app.onlinebookshop.dto;

public record BookSearchParameters(String[] title, String[] author,
                                   String[] isbn, String[] price,
                                   String[] description, String[] coverImage) {
}
