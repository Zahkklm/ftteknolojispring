package com.patika.bookingservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String USER_NOT_FOUND = "kullanıcı bulunamadı.";
    public static final String USER_NOT_FOUND_OR_PASSWORD_IS_WRONG = "bu email ile kayıtlı kullanıcı bulunamadı ve ya şifre yanlış.";
    public static final String USER_ALREADY_DEFINED = "bu email ile zaten kayıtlı bir kullanıcı bulunmaktadır.";
    public static final String USER_EMAIL_CAN_NOT_BE_EMPTY = "email alanı boş olamaz.";
    public static final String ROLE_NOT_FOUND = "role bulunamadı";
    public static final String AUTHENTICATION_FAILED = "Yetkilendirme hatası";
    public static final String CHANGE_BULK_STATUS_FAILED = "Toplu statü değiştirme işlemi başarısız oldu";
}
