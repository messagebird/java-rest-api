package com.messagebird.objects;

/**
 * Created by faizan on 09/12/15.
 */
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

    private String code;

    Language(String code) {
       this.code = code;
    }

    public String toString() {
        return this.code;
    }
}
