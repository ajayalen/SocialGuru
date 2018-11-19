package com.socialnetwork.social.twitter.user;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 */
public class User {


    /**
     * id : 760735512844009472
     * id_str : 760735512844009472
     * name : Anand
     * screen_name : AnandTechahead
     * location :
     * description :
     * url : null
     * entities : {"description":{"urls":[]}}
     * protected : false
     * followers_count : 2
     * friends_count : 0
     * listed_count : 0
     * created_at : Wed Aug 03 07:14:14 +0000 2016
     * favourites_count : 0
     * utc_offset : null
     * time_zone : null
     * geo_enabled : false
     * verified : false
     * statuses_count : 0
     * lang : en
     * contributors_enabled : false
     * is_translator : false
     * is_translation_enabled : false
     * profile_background_color : F5F8FA
     * profile_background_image_url : null
     * profile_background_image_url_https : null
     * profile_background_tile : false
     * profile_image_url : http://abs.twimg.com/sticky/default_profile_images/default_profile_0_normal.png
     * profile_image_url_https : https://abs.twimg.com/sticky/default_profile_images/default_profile_0_normal.png
     * profile_link_color : 2B7BB9
     * profile_sidebar_border_color : C0DEED
     * profile_sidebar_fill_color : DDEEF6
     * profile_text_color : 333333
     * profile_use_background_image : true
     * has_extended_profile : false
     * default_profile : true
     * default_profile_image : true
     * following : false
     * follow_request_sent : false
     * notifications : false
     */

    private String id;
    private String id_str;
    private String name;
    private String screen_name;
    private String location;
    private String description;
    private Object url;
    /**
     * description : {"urls":[]}
     */

    private EntitiesBean entities;
    @SerializedName("protected")
    private boolean protectedX;
    private int followers_count;
    private int friends_count;
    private int listed_count;
    private String created_at;
    private int favourites_count;
    private Object utc_offset;
    private Object time_zone;
    private boolean geo_enabled;
    private boolean verified;
    private int statuses_count;
    private String lang;
    private boolean contributors_enabled;
    private boolean is_translator;
    private boolean is_translation_enabled;
    private String profile_background_color;
    private Object profile_background_image_url;
    private Object profile_background_image_url_https;
    private boolean profile_background_tile;
    private String profile_image_url;
    private String profile_image_url_https;
    private String profile_link_color;
    private String profile_sidebar_border_color;
    private String profile_sidebar_fill_color;
    private String profile_text_color;
    private boolean profile_use_background_image;
    private boolean has_extended_profile;
    private boolean default_profile;
    private boolean default_profile_image;
    private boolean following;
    private boolean follow_request_sent;
    private boolean notifications;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_str() {
        return id_str;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

    public EntitiesBean getEntities() {
        return entities;
    }

    public void setEntities(EntitiesBean entities) {
        this.entities = entities;
    }

    public boolean isProtectedX() {
        return protectedX;
    }

    public void setProtectedX(boolean protectedX) {
        this.protectedX = protectedX;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFriends_count() {
        return friends_count;
    }

    public void setFriends_count(int friends_count) {
        this.friends_count = friends_count;
    }

    public int getListed_count() {
        return listed_count;
    }

    public void setListed_count(int listed_count) {
        this.listed_count = listed_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getFavourites_count() {
        return favourites_count;
    }

    public void setFavourites_count(int favourites_count) {
        this.favourites_count = favourites_count;
    }

    public Object getUtc_offset() {
        return utc_offset;
    }

    public void setUtc_offset(Object utc_offset) {
        this.utc_offset = utc_offset;
    }

    public Object getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(Object time_zone) {
        this.time_zone = time_zone;
    }

    public boolean isGeo_enabled() {
        return geo_enabled;
    }

    public void setGeo_enabled(boolean geo_enabled) {
        this.geo_enabled = geo_enabled;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getStatuses_count() {
        return statuses_count;
    }

    public void setStatuses_count(int statuses_count) {
        this.statuses_count = statuses_count;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public boolean isContributors_enabled() {
        return contributors_enabled;
    }

    public void setContributors_enabled(boolean contributors_enabled) {
        this.contributors_enabled = contributors_enabled;
    }

    public boolean isIs_translator() {
        return is_translator;
    }

    public void setIs_translator(boolean is_translator) {
        this.is_translator = is_translator;
    }

    public boolean isIs_translation_enabled() {
        return is_translation_enabled;
    }

    public void setIs_translation_enabled(boolean is_translation_enabled) {
        this.is_translation_enabled = is_translation_enabled;
    }

    public String getProfile_background_color() {
        return profile_background_color;
    }

    public void setProfile_background_color(String profile_background_color) {
        this.profile_background_color = profile_background_color;
    }

    public Object getProfile_background_image_url() {
        return profile_background_image_url;
    }

    public void setProfile_background_image_url(Object profile_background_image_url) {
        this.profile_background_image_url = profile_background_image_url;
    }

    public Object getProfile_background_image_url_https() {
        return profile_background_image_url_https;
    }

    public void setProfile_background_image_url_https(Object profile_background_image_url_https) {
        this.profile_background_image_url_https = profile_background_image_url_https;
    }

    public boolean isProfile_background_tile() {
        return profile_background_tile;
    }

    public void setProfile_background_tile(boolean profile_background_tile) {
        this.profile_background_tile = profile_background_tile;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
    }

    public String getProfile_image_url_https() {
        return profile_image_url_https;
    }

    public void setProfile_image_url_https(String profile_image_url_https) {
        this.profile_image_url_https = profile_image_url_https;
    }

    public String getProfile_link_color() {
        return profile_link_color;
    }

    public void setProfile_link_color(String profile_link_color) {
        this.profile_link_color = profile_link_color;
    }

    public String getProfile_sidebar_border_color() {
        return profile_sidebar_border_color;
    }

    public void setProfile_sidebar_border_color(String profile_sidebar_border_color) {
        this.profile_sidebar_border_color = profile_sidebar_border_color;
    }

    public String getProfile_sidebar_fill_color() {
        return profile_sidebar_fill_color;
    }

    public void setProfile_sidebar_fill_color(String profile_sidebar_fill_color) {
        this.profile_sidebar_fill_color = profile_sidebar_fill_color;
    }

    public String getProfile_text_color() {
        return profile_text_color;
    }

    public void setProfile_text_color(String profile_text_color) {
        this.profile_text_color = profile_text_color;
    }

    public boolean isProfile_use_background_image() {
        return profile_use_background_image;
    }

    public void setProfile_use_background_image(boolean profile_use_background_image) {
        this.profile_use_background_image = profile_use_background_image;
    }

    public boolean isHas_extended_profile() {
        return has_extended_profile;
    }

    public void setHas_extended_profile(boolean has_extended_profile) {
        this.has_extended_profile = has_extended_profile;
    }

    public boolean isDefault_profile() {
        return default_profile;
    }

    public void setDefault_profile(boolean default_profile) {
        this.default_profile = default_profile;
    }

    public boolean isDefault_profile_image() {
        return default_profile_image;
    }

    public void setDefault_profile_image(boolean default_profile_image) {
        this.default_profile_image = default_profile_image;
    }

    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public boolean isFollow_request_sent() {
        return follow_request_sent;
    }

    public void setFollow_request_sent(boolean follow_request_sent) {
        this.follow_request_sent = follow_request_sent;
    }

    public boolean isNotifications() {
        return notifications;
    }

    public void setNotifications(boolean notifications) {
        this.notifications = notifications;
    }

    public static class EntitiesBean {
        private DescriptionBean description;

        public DescriptionBean getDescription() {
            return description;
        }

        public void setDescription(DescriptionBean description) {
            this.description = description;
        }

        public static class DescriptionBean {
            private List<?> urls;

            public List<?> getUrls() {
                return urls;
            }

            public void setUrls(List<?> urls) {
                this.urls = urls;
            }
        }
    }
}
