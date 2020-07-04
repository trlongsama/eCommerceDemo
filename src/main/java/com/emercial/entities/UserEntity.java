package com.emercial.entities;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "ecommercial", catalog = "")
public class UserEntity {
    private Integer id;
    private String name;
    private Integer gender;
    private String telnumber;
    private String registerMode;
    private String thirdPartyId;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "gender", nullable = false)
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "telnumber", nullable = false, length = 255)
    public String getTelnumber() {
        return telnumber;
    }

    public void setTelnumber(String telnumber) {
        this.telnumber = telnumber;
    }

    @Basic
    @Column(name = "register_mode", nullable = false, length = 255)
    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode;
    }

    @Basic
    @Column(name = "third_party_id", nullable = false, length = 255)
    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (id != that.id) return false;
        if (gender != that.gender) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (telnumber != null ? !telnumber.equals(that.telnumber) : that.telnumber != null) return false;
        if (registerMode != null ? !registerMode.equals(that.registerMode) : that.registerMode != null) return false;
        if (thirdPartyId != null ? !thirdPartyId.equals(that.thirdPartyId) : that.thirdPartyId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + gender;
        result = 31 * result + (telnumber != null ? telnumber.hashCode() : 0);
        result = 31 * result + (registerMode != null ? registerMode.hashCode() : 0);
        result = 31 * result + (thirdPartyId != null ? thirdPartyId.hashCode() : 0);
        return result;
    }
}
