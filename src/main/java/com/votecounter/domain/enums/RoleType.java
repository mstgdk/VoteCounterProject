package com.votecounter.domain.enums;

public enum RoleType {

    ROLE_ADMIN("Administrator");// ön tarafa ROLE_CUSTOMER ismiile gitmesini istemiyorum. Güvenlik sebebiyle

    // client tarafına  Administrator olarak gitmesini istedik.
    private String name;

    private RoleType(String name) { // ROLE_ADMIN in name lerini setledik
        this.name=name;
    }


    public String getName(){
        return name;
    }
}
