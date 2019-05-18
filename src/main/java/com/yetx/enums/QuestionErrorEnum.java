package com.yetx.enums;

public enum QuestionErrorEnum implements ErrorEnum{
    QUESTION_ID_NULL(570,"question_id为null"),
    QUESTION_UID_NULL(571,"question_uerid为Null"),
    QUESTION_KEYWORDS_NULL(572,"question_keyword为null"),
    QUESTION_ID_UID_NOTMATCH(573,"question_id and input userId不匹配"),
    ANSWER_ID_NOT_FOUND(574,"answer_id not found"),
    ANSWER_ID_UID_NOTMATCH(575,"answer_id and uid not matched"),
    ANSWER_ID_NULL(576,"answer_id is null"),
    COMMENT_TYPE_ID_NOTMATCH(577,"comment_type and input id不匹配"),
    COMMENT_FROMUID_NOWUID_NOTMATCH(578,"comment_fromUid and now userId不匹配"),
    COMMENT_TYPE_OOR(579,"comment_type不在许可范围内")
    ;
    private final Integer code;
    private final String msg;
    QuestionErrorEnum(Integer code,String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public String getMsg() {
        return null;
    }
}
