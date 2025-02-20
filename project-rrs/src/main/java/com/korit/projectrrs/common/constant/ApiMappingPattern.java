package com.korit.projectrrs.common.constant;

public class ApiMappingPattern {
    public static final String AUTH = "/api/v1/auth";
    public static final String USER = "/api/v1/users";
    public static final String WALKING_RECORD = "/api/v1/walking-records";
    public static final String PROVIDER = "/api/v1/providers";
    public static final String PROVISION = "/api/v1/provisions";

    public static final String TODO = "/api/v1/todos";
    public static final String CUSTOMER_SUPPORT = "/api/v1/customer-supports";
    public static final String RESERVATION = "/api/v1/reservations";
    public static final String REVIEW = "/api/v1/reviews";

    // 커뮤니티
    public static final String COMMUNITY = "/api/v1/community";
    public static final String COMMUNITY_POST = "/write";
    public static final String COMMUNITY_PUT = "/edit/{communityId}";
    public static final String COMMUNITY_DELETE = "/delete/{communityId}";
    public static final String COMMUNITY_GET_BY_ID = "/{communityId}";

    public static final String COMMUNITY_ATTACHMENT = "/api/v1/community-attachments";
    public static final String ATTACHMENT_BY_COMMUNITY_ID = "/community/{communityId}";
    public static final String ATTACHMENT_BY_ATTACHMENT_ID = "/{attachmentId}";
    public static final String DELETE_ATTACHMENT_BY_COMMUNITY_ID = "/community/{communityId}";

    // 커뮤니티 댓글
    public static final String COMMENT = "/api/v1/community/comment";
    public static final String COMMENT_CREATE = "/{communityId}";
    public static final String COMMENT_PUT = "/{communityId}/{commentId}";
    public static final String COMMENT_DELETE_BY_COMMUNITY_ID = "/{communityId}/{commentId}";
    public static final String COMMENT_GET_BY_COMMUNITY_ID = "/{communityId}";

    public static final String COMMUNITY_LIKE = "/api/v1/community/like";
    public static final String COMMUNITY_LIKE_BY_COMMUNITY_ID = "/{communityId}";
    public static final String ALL_COMMUNITY_LIKE_BY_COMMUNITY_ID = "/{communityId}/likes";

    // 공지사항
    public static final String ANNOUNCEMENT = "/api/v1/announcements";
    public static final String ANNOUNCEMENT_BY_ID = "/{announcementId}";

    // 이벤트
    public static final String EVENT = "/api/v1/events";
    public static final String EVENT_BY_ID = "/{eventId}";

    //사용법
    public static final String USAGE_GUIDE = "/api/v1/usage-guide";
    public static final String USAGE_GUIDE_BY_ID = "/{usageGuideId}";

    // 펫 및 건강 기록
    public static final String PET = "/api/v1/pets";
    public static final String HEALTH_RECORDS = "/api/v1/health-records";
    public static final String HEALTH_RECORD_CREATE = "/{petId}";
    public static final String HEALTH_RECORD_GET_LIST = "/{petId}";

    public static final String HEALTH_RECORD_GET_BY_ID = "/{petId}/{healthRecordId}";
    public static final String HEALTH_RECORD_UPDATE = "/{petId}/{healthRecordId}";
    public static final String HEALTH_RECORD_DELETE = "/{petId}/{healthRecordId}";
    public static final String HEALTH_RECORD_BY_USERID = "/all-records";

    public static final String HEALTH_RECORDS_ATTACHMENT = "api/v1/health-attachments";
    public static final String HEALTH_RECORDS_ATTACHMENT_BY_RECORD_ID = "/health-record/{healthRecordId}";
    public static final String DELETE_ATTACHMENT_BY_ATTACHMENT_ID = "/{attachmentId}";
    public static final String DELETE_ATTACHMENT_BY_HEALTH_RECORD_ID = "/health-record/{healthRecordId}";


    public static final String DUPLICATE_USERNAME_PATH = "/duplicate-username";
    public static final String DUPLICATE_NICKNAME_PATH = "/duplicate-nickname";
    public static final String DUPLICATE_PHONE_PATH = "/duplicate-phone";
    public static final String DUPLICATE_EMAIL_PATH = "/duplicate-email";
}