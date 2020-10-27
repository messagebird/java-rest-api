package com.messagebird.objects;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * Created by faizan on 09/12/15.
 */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum Language {

    NL_NL("nl-nl"),
    DE_DE("de-de"),
    EN_GB("en-gb"),
    EN_US("en-us"),
    ES_ES("es-es"),
    FR_FR("fr-fr"),
    RU_RU("ru_ru"),
    ZH_CN("zh-cn"),
    EN_AU("en-au"),
    ES_MX("es-mx"),
    ES_US("es-us"),
    FR_CA("fr-ca"),
    IS_IS("is-is"),
    IT_IT("it-it"),
    JA_JP("ja-jp"),
    KO_KR("ko-kr"),
    PL_PL("pl-pl"),
    PT_BR("pt-br"),
    RO_RO("ro-ro");

    @Getter
    private String code;

    @Override
    public String toString(){
        return code;
    }
}
