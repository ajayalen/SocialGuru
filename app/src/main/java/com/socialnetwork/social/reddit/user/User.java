package com.socialnetwork.social.reddit.user;

import com.google.gson.annotations.SerializedName;

/**
 */
public class User {


    /**
     * is_employee : false
     * has_mail : true
     * name : anand_techahead
     * created : 1.472160095E9
     * hide_from_robots : false
     * is_suspended : false
     * created_utc : 1.472131295E9
     * has_mod_mail : false
     * link_karma : 1
     * in_beta : false
     * comment_karma : 0
     * features : {"adzerk_reporting_2":true,"live_happening_now":true,"adserver_reporting":true,"legacy_search_pref":true,"mobile_web_targeting":true,"orangereds_as_emails":true,"adzerk_do_not_track":true,"sticky_comments":true,"upgrade_cookies":true,"ads_auto_refund":true,"ads_auction":true,"imgur_gif_conversion":true,"expando_events":true,"eu_cookie_policy":true,"force_https":true,"mobile_native_banner":true,"do_not_track":true,"outbound_clicktracking":true,"image_uploads":true,"new_loggedin_cache_policy":true,"https_redirect":true,"screenview_events":true,"pause_ads":true,"give_hsts_grants":true,"new_report_dialog":true,"moat_tracking":true,"subreddit_rules":true,"timeouts":true,"mobile_settings":true,"youtube_scraper":true,"activity_service_write":true,"ads_auto_extend":true,"post_embed":true,"scroll_events":true,"302_to_canonicals":true,"adblock_test":true,"activity_service_read":true}
     * over_18 : false
     * is_gold : false
     * is_mod : false
     * id : 10vqad
     * gold_expiration : null
     * inbox_count : 1
     * has_verified_email : false
     * gold_creddits : 0
     * suspension_expiration_utc : null
     */

    private boolean is_employee;
    private boolean has_mail;
    private String name;
    private double created;
    private boolean hide_from_robots;
    private boolean is_suspended;
    private double created_utc;
    private boolean has_mod_mail;
    private int link_karma;
    private boolean in_beta;
    private int comment_karma;
    /**
     * adzerk_reporting_2 : true
     * live_happening_now : true
     * adserver_reporting : true
     * legacy_search_pref : true
     * mobile_web_targeting : true
     * orangereds_as_emails : true
     * adzerk_do_not_track : true
     * sticky_comments : true
     * upgrade_cookies : true
     * ads_auto_refund : true
     * ads_auction : true
     * imgur_gif_conversion : true
     * expando_events : true
     * eu_cookie_policy : true
     * force_https : true
     * mobile_native_banner : true
     * do_not_track : true
     * outbound_clicktracking : true
     * image_uploads : true
     * new_loggedin_cache_policy : true
     * https_redirect : true
     * screenview_events : true
     * pause_ads : true
     * give_hsts_grants : true
     * new_report_dialog : true
     * moat_tracking : true
     * subreddit_rules : true
     * timeouts : true
     * mobile_settings : true
     * youtube_scraper : true
     * activity_service_write : true
     * ads_auto_extend : true
     * post_embed : true
     * scroll_events : true
     * 302_to_canonicals : true
     * adblock_test : true
     * activity_service_read : true
     */

    private FeaturesBean features;
    private boolean over_18;
    private boolean is_gold;
    private boolean is_mod;
    private String id;
    private Object gold_expiration;
    private int inbox_count;
    private boolean has_verified_email;
    private int gold_creddits;
    private Object suspension_expiration_utc;

    public boolean isIs_employee() {
        return is_employee;
    }

    public void setIs_employee(boolean is_employee) {
        this.is_employee = is_employee;
    }

    public boolean isHas_mail() {
        return has_mail;
    }

    public void setHas_mail(boolean has_mail) {
        this.has_mail = has_mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCreated() {
        return created;
    }

    public void setCreated(double created) {
        this.created = created;
    }

    public boolean isHide_from_robots() {
        return hide_from_robots;
    }

    public void setHide_from_robots(boolean hide_from_robots) {
        this.hide_from_robots = hide_from_robots;
    }

    public boolean isIs_suspended() {
        return is_suspended;
    }

    public void setIs_suspended(boolean is_suspended) {
        this.is_suspended = is_suspended;
    }

    public double getCreated_utc() {
        return created_utc;
    }

    public void setCreated_utc(double created_utc) {
        this.created_utc = created_utc;
    }

    public boolean isHas_mod_mail() {
        return has_mod_mail;
    }

    public void setHas_mod_mail(boolean has_mod_mail) {
        this.has_mod_mail = has_mod_mail;
    }

    public int getLink_karma() {
        return link_karma;
    }

    public void setLink_karma(int link_karma) {
        this.link_karma = link_karma;
    }

    public boolean isIn_beta() {
        return in_beta;
    }

    public void setIn_beta(boolean in_beta) {
        this.in_beta = in_beta;
    }

    public int getComment_karma() {
        return comment_karma;
    }

    public void setComment_karma(int comment_karma) {
        this.comment_karma = comment_karma;
    }

    public FeaturesBean getFeatures() {
        return features;
    }

    public void setFeatures(FeaturesBean features) {
        this.features = features;
    }

    public boolean isOver_18() {
        return over_18;
    }

    public void setOver_18(boolean over_18) {
        this.over_18 = over_18;
    }

    public boolean isIs_gold() {
        return is_gold;
    }

    public void setIs_gold(boolean is_gold) {
        this.is_gold = is_gold;
    }

    public boolean isIs_mod() {
        return is_mod;
    }

    public void setIs_mod(boolean is_mod) {
        this.is_mod = is_mod;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getGold_expiration() {
        return gold_expiration;
    }

    public void setGold_expiration(Object gold_expiration) {
        this.gold_expiration = gold_expiration;
    }

    public int getInbox_count() {
        return inbox_count;
    }

    public void setInbox_count(int inbox_count) {
        this.inbox_count = inbox_count;
    }

    public boolean isHas_verified_email() {
        return has_verified_email;
    }

    public void setHas_verified_email(boolean has_verified_email) {
        this.has_verified_email = has_verified_email;
    }

    public int getGold_creddits() {
        return gold_creddits;
    }

    public void setGold_creddits(int gold_creddits) {
        this.gold_creddits = gold_creddits;
    }

    public Object getSuspension_expiration_utc() {
        return suspension_expiration_utc;
    }

    public void setSuspension_expiration_utc(Object suspension_expiration_utc) {
        this.suspension_expiration_utc = suspension_expiration_utc;
    }

    public static class FeaturesBean {
        private boolean adzerk_reporting_2;
        private boolean live_happening_now;
        private boolean adserver_reporting;
        private boolean legacy_search_pref;
        private boolean mobile_web_targeting;
        private boolean orangereds_as_emails;
        private boolean adzerk_do_not_track;
        private boolean sticky_comments;
        private boolean upgrade_cookies;
        private boolean ads_auto_refund;
        private boolean ads_auction;
        private boolean imgur_gif_conversion;
        private boolean expando_events;
        private boolean eu_cookie_policy;
        private boolean force_https;
        private boolean mobile_native_banner;
        private boolean do_not_track;
        private boolean outbound_clicktracking;
        private boolean image_uploads;
        private boolean new_loggedin_cache_policy;
        private boolean https_redirect;
        private boolean screenview_events;
        private boolean pause_ads;
        private boolean give_hsts_grants;
        private boolean new_report_dialog;
        private boolean moat_tracking;
        private boolean subreddit_rules;
        private boolean timeouts;
        private boolean mobile_settings;
        private boolean youtube_scraper;
        private boolean activity_service_write;
        private boolean ads_auto_extend;
        private boolean post_embed;
        private boolean scroll_events;
        @SerializedName("302_to_canonicals")
        private boolean value302_to_canonicals;
        private boolean adblock_test;
        private boolean activity_service_read;

        public boolean isAdzerk_reporting_2() {
            return adzerk_reporting_2;
        }

        public void setAdzerk_reporting_2(boolean adzerk_reporting_2) {
            this.adzerk_reporting_2 = adzerk_reporting_2;
        }

        public boolean isLive_happening_now() {
            return live_happening_now;
        }

        public void setLive_happening_now(boolean live_happening_now) {
            this.live_happening_now = live_happening_now;
        }

        public boolean isAdserver_reporting() {
            return adserver_reporting;
        }

        public void setAdserver_reporting(boolean adserver_reporting) {
            this.adserver_reporting = adserver_reporting;
        }

        public boolean isLegacy_search_pref() {
            return legacy_search_pref;
        }

        public void setLegacy_search_pref(boolean legacy_search_pref) {
            this.legacy_search_pref = legacy_search_pref;
        }

        public boolean isMobile_web_targeting() {
            return mobile_web_targeting;
        }

        public void setMobile_web_targeting(boolean mobile_web_targeting) {
            this.mobile_web_targeting = mobile_web_targeting;
        }

        public boolean isOrangereds_as_emails() {
            return orangereds_as_emails;
        }

        public void setOrangereds_as_emails(boolean orangereds_as_emails) {
            this.orangereds_as_emails = orangereds_as_emails;
        }

        public boolean isAdzerk_do_not_track() {
            return adzerk_do_not_track;
        }

        public void setAdzerk_do_not_track(boolean adzerk_do_not_track) {
            this.adzerk_do_not_track = adzerk_do_not_track;
        }

        public boolean isSticky_comments() {
            return sticky_comments;
        }

        public void setSticky_comments(boolean sticky_comments) {
            this.sticky_comments = sticky_comments;
        }

        public boolean isUpgrade_cookies() {
            return upgrade_cookies;
        }

        public void setUpgrade_cookies(boolean upgrade_cookies) {
            this.upgrade_cookies = upgrade_cookies;
        }

        public boolean isAds_auto_refund() {
            return ads_auto_refund;
        }

        public void setAds_auto_refund(boolean ads_auto_refund) {
            this.ads_auto_refund = ads_auto_refund;
        }

        public boolean isAds_auction() {
            return ads_auction;
        }

        public void setAds_auction(boolean ads_auction) {
            this.ads_auction = ads_auction;
        }

        public boolean isImgur_gif_conversion() {
            return imgur_gif_conversion;
        }

        public void setImgur_gif_conversion(boolean imgur_gif_conversion) {
            this.imgur_gif_conversion = imgur_gif_conversion;
        }

        public boolean isExpando_events() {
            return expando_events;
        }

        public void setExpando_events(boolean expando_events) {
            this.expando_events = expando_events;
        }

        public boolean isEu_cookie_policy() {
            return eu_cookie_policy;
        }

        public void setEu_cookie_policy(boolean eu_cookie_policy) {
            this.eu_cookie_policy = eu_cookie_policy;
        }

        public boolean isForce_https() {
            return force_https;
        }

        public void setForce_https(boolean force_https) {
            this.force_https = force_https;
        }

        public boolean isMobile_native_banner() {
            return mobile_native_banner;
        }

        public void setMobile_native_banner(boolean mobile_native_banner) {
            this.mobile_native_banner = mobile_native_banner;
        }

        public boolean isDo_not_track() {
            return do_not_track;
        }

        public void setDo_not_track(boolean do_not_track) {
            this.do_not_track = do_not_track;
        }

        public boolean isOutbound_clicktracking() {
            return outbound_clicktracking;
        }

        public void setOutbound_clicktracking(boolean outbound_clicktracking) {
            this.outbound_clicktracking = outbound_clicktracking;
        }

        public boolean isImage_uploads() {
            return image_uploads;
        }

        public void setImage_uploads(boolean image_uploads) {
            this.image_uploads = image_uploads;
        }

        public boolean isNew_loggedin_cache_policy() {
            return new_loggedin_cache_policy;
        }

        public void setNew_loggedin_cache_policy(boolean new_loggedin_cache_policy) {
            this.new_loggedin_cache_policy = new_loggedin_cache_policy;
        }

        public boolean isHttps_redirect() {
            return https_redirect;
        }

        public void setHttps_redirect(boolean https_redirect) {
            this.https_redirect = https_redirect;
        }

        public boolean isScreenview_events() {
            return screenview_events;
        }

        public void setScreenview_events(boolean screenview_events) {
            this.screenview_events = screenview_events;
        }

        public boolean isPause_ads() {
            return pause_ads;
        }

        public void setPause_ads(boolean pause_ads) {
            this.pause_ads = pause_ads;
        }

        public boolean isGive_hsts_grants() {
            return give_hsts_grants;
        }

        public void setGive_hsts_grants(boolean give_hsts_grants) {
            this.give_hsts_grants = give_hsts_grants;
        }

        public boolean isNew_report_dialog() {
            return new_report_dialog;
        }

        public void setNew_report_dialog(boolean new_report_dialog) {
            this.new_report_dialog = new_report_dialog;
        }

        public boolean isMoat_tracking() {
            return moat_tracking;
        }

        public void setMoat_tracking(boolean moat_tracking) {
            this.moat_tracking = moat_tracking;
        }

        public boolean isSubreddit_rules() {
            return subreddit_rules;
        }

        public void setSubreddit_rules(boolean subreddit_rules) {
            this.subreddit_rules = subreddit_rules;
        }

        public boolean isTimeouts() {
            return timeouts;
        }

        public void setTimeouts(boolean timeouts) {
            this.timeouts = timeouts;
        }

        public boolean isMobile_settings() {
            return mobile_settings;
        }

        public void setMobile_settings(boolean mobile_settings) {
            this.mobile_settings = mobile_settings;
        }

        public boolean isYoutube_scraper() {
            return youtube_scraper;
        }

        public void setYoutube_scraper(boolean youtube_scraper) {
            this.youtube_scraper = youtube_scraper;
        }

        public boolean isActivity_service_write() {
            return activity_service_write;
        }

        public void setActivity_service_write(boolean activity_service_write) {
            this.activity_service_write = activity_service_write;
        }

        public boolean isAds_auto_extend() {
            return ads_auto_extend;
        }

        public void setAds_auto_extend(boolean ads_auto_extend) {
            this.ads_auto_extend = ads_auto_extend;
        }

        public boolean isPost_embed() {
            return post_embed;
        }

        public void setPost_embed(boolean post_embed) {
            this.post_embed = post_embed;
        }

        public boolean isScroll_events() {
            return scroll_events;
        }

        public void setScroll_events(boolean scroll_events) {
            this.scroll_events = scroll_events;
        }

        public boolean isValue302_to_canonicals() {
            return value302_to_canonicals;
        }

        public void setValue302_to_canonicals(boolean value302_to_canonicals) {
            this.value302_to_canonicals = value302_to_canonicals;
        }

        public boolean isAdblock_test() {
            return adblock_test;
        }

        public void setAdblock_test(boolean adblock_test) {
            this.adblock_test = adblock_test;
        }

        public boolean isActivity_service_read() {
            return activity_service_read;
        }

        public void setActivity_service_read(boolean activity_service_read) {
            this.activity_service_read = activity_service_read;
        }
    }
}
