package com.korit.projectrrs.common;

public class ApiMappingPattern {
    public static final String AUTH = "/api/v1/auth";
    public static final String USER = "/api/v1/users";

    public static final String TODO = "/api/v1/todos";
    public static final String ARTICLE = "/api/v1/articles";
    public static final String CUSTOMER_SUPPORT = "/api/v1/customer-supports";
    public static final String CUSTOMER_SUPPORT_ATTACHMENT = "/api/v1/customer-supports-attachment";
    public static final String RESERVATION = "/api/v1/reservation";
    public static final String REVIEW = "/api/v1/reviews";

    // 커뮤니티
    public static final String COMMUNITY = "/api/v1/users/community";
    public static final String COMMUNITY_PUT = "/{communityId}";
    public static final String COMMUNITY_DELETE = "/{communityId}";
    public static final String COMMUNITY_GET_BY_ID = "/{communityId}";
    public static final String COMMUNITY_LIKE_COUNT = "/like/{communityId}";

    // 공지사항
    public static final String ANNOUNCEMENT = "/api/v1/announcements";
    public static final String ANNOUNCEMENT_BY_ID = "/{announcementId}";

    // 펫 및 건강 기록
    public static final String PET = "/api/v1/users/pet";
    public static final String HEALTH_RECORDS = "/api/v1/users/pet/petHealth";
    public static final String HEALTH_RECORD_CREATE = "/{petId}";
    public static final String HEALTH_RECORD_GET_LIST = "/{petId}";
    public static final String HEALTH_RECORD_GET_BY_ID = "/{petId}/{healthRecordId}";
    public static final String HEALTH_RECORD_UPDATE = "/{petId}/{healthRecordId}";
    public static final String HEALTH_RECORD_DELETE = "/{petId}/{healthRecordId}";

    // 커뮤니티 댓글
    public static final String COMMENT = "/api/v1/users/community/comment";
    public static final String COMMENT_CREATE = "/{communityId}";
    public static final String COMMENT_PUT = "/{communityId}/{commentId}";
    public static final String COMMENT_DELETE_BY_COMMUNITY_ID = "/{communityId}/{commentId}";
    public static final String COMMENT_GET_BY_COMMUNITY_ID = "/{communityId}";

}