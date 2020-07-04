package com.emercial.controller.viewobject;

public class UserVO {
    private Integer id;
    private String name;
    private Byte gender;
    private String telnumber;
    /*unnecessay information
    private String registerMode;
    private String thirdPartyId;
    private String encryptPassword;
     */

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }


    public String getTelnumber() {
        return telnumber;
    }

    public void setTelnumber(String telnumber) {
        this.telnumber = telnumber;
    }
}
