package com.socialnetwork.social.instagram.user;

/**
 */
public class User {

    /**
     * id : 1574083
     * username : snoopdogg
     * full_name : Snoop Dogg
     * profile_picture : http://distillery.s3.amazonaws.com/profiles/profile_1574083_75sq_1295469061.jpg
     * bio : This is my bio
     * website : http://snoopdogg.com
     * counts : {"media":1320,"follows":420,"followed_by":3410}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String username;
        private String full_name;
        private String profile_picture;
        private String bio;
        private String website;
        /**
         * media : 1320
         * follows : 420
         * followed_by : 3410
         */

        private CountsBean counts;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getFull_name() {
            return full_name;
        }

        public void setFull_name(String full_name) {
            this.full_name = full_name;
        }

        public String getProfile_picture() {
            return profile_picture;
        }

        public void setProfile_picture(String profile_picture) {
            this.profile_picture = profile_picture;
        }

        public String getBio() {
            return bio;
        }

        public void setBio(String bio) {
            this.bio = bio;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public CountsBean getCounts() {
            return counts;
        }

        public void setCounts(CountsBean counts) {
            this.counts = counts;
        }

        public static class CountsBean {
            private int media;
            private int follows;
            private int followed_by;

            public int getMedia() {
                return media;
            }

            public void setMedia(int media) {
                this.media = media;
            }

            public int getFollows() {
                return follows;
            }

            public void setFollows(int follows) {
                this.follows = follows;
            }

            public int getFollowed_by() {
                return followed_by;
            }

            public void setFollowed_by(int followed_by) {
                this.followed_by = followed_by;
            }
        }
    }
}
