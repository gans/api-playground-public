package com.hotmart.playground.idea;

public final class IdeaRepositoryQueries {

    public static final String IDEA_BASE_QUERY = "SELECT new com.hotmart.playground.idea.IdeaDto" +
            "( " +
            "  i.id," +
            "  i.title," +
            "  i.description," +
            "  i.createAt," +
            "  u.id," +
            "  u.email," +
            "  c.id," +
            "  c.name," +
            "  c.description," +
            "  c.image," +
            "  s.likes," +
            "  s.dislikes," +
            "  s.joins," +
            "  s.comments, " +
            "  (SELECT it " +
            "   FROM IdeaInteraction it " +
            "   WHERE it.idea = i " +
            "     AND it.user = :user " +
            "     AND it.type IN (com.hotmart.playground.social.InteractionType.LIKE, com.hotmart.playground.social.InteractionType.DISLIKE) " +
            "  ), " +
            "  (SELECT it " +
            "   FROM IdeaInteraction it " +
            "   WHERE it.idea = i " +
            "     AND it.user = :user " +
            "     AND it.type = com.hotmart.playground.social.InteractionType.JOIN " +
            "   )" +
            ") " +
            "FROM Idea i " +
            "  JOIN i.user u" +
            "  JOIN i.category c " +
            "  JOIN i.stats s ";

    public static final String IDEA_BASE_QUERY_COUNT = "SELECT COUNT(i) " +
            "FROM Idea i " +
            "  JOIN i.user u" +
            "  JOIN i.category c " +
            "  JOIN i.stats s ";

    public static final String FIND_BY_CATEGORY_WHERE = " WHERE (:category IS NULL OR i.category.id = :category) ";

    public static final String FIND_BY_CATEGORY_QUERY = IDEA_BASE_QUERY +
            FIND_BY_CATEGORY_WHERE;

    public static final String FIND_BY_CATEGORY_COUNT_QUERY = IDEA_BASE_QUERY_COUNT +
            FIND_BY_CATEGORY_WHERE;

    public static final String FIND_NEWEST_IDEAS_QUERY = IDEA_BASE_QUERY +
            "ORDER BY i.createAt DESC";

    public static final String FIND_POPULAR_IDEAS_QUERY = IDEA_BASE_QUERY +
            "ORDER BY s.likes DESC, s.joins DESC, s.dislikes ASC, i.createAt DESC";

    private IdeaRepositoryQueries() {
        throw new IllegalStateException("Utility class");
    }

}
