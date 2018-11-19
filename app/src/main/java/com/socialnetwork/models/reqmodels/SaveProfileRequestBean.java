package com.socialnetwork.models.reqmodels;

import java.util.List;

/**
 * Created by sakshi on 2/9/16.
 */
public class SaveProfileRequestBean {


    /**
     * user_id : 1
     * username : shashank
     * is_private : 1/0
     * first_name : shashank
     * last_name :
     * profile_pic :
     * age : 25
     * gender : M/F
     * about :
     * hobbies :
     * company_name :
     * designation :
     * website : ["website1","website2","website3"]
     */

    private String user_id;
    private String username;
    private String is_private;
    private String first_name;
    private String last_name;
    private String profile_pic;
    private String age;
    private String gender;
    private String about;
    private String hobbies;
    private String company_name;
    private String designation;
    private List<String> website;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIs_private() {
        return is_private;
    }

    public void setIs_private(String is_private) {
        this.is_private = is_private;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public List<String> getWebsite() {
        return website;
    }

    public void setWebsite(List<String> website) {
        this.website = website;
    }
}
