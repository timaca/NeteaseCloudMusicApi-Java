package com.roomaja.neteasecloudmusicapi.defaultEnum;

public enum AesEncryptEnum {
    CBC("CBC"),
    ECB("ECB");

    private String type;

    private AesEncryptEnum(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
