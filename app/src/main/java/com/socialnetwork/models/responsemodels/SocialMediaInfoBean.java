package com.socialnetwork.models.responsemodels;

import java.util.List;

public class SocialMediaInfoBean {


    /**
     * data : {"url":"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/v/t1.0-1/c0.2.50.50/p50x50/995659_414453772001071_161061694_n.jpg?oh=43b51bde61b1e209146b380f0504dd00&oe=582DE60A&__gda__=1479068930_9b6225fef903d671bbed8484b89e2ad0","is_silhouette":false}
     */

    private PictureEntity picture;
    /**
     * picture : {"data":{"url":"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/v/t1.0-1/c0.2.50.50/p50x50/995659_414453772001071_161061694_n.jpg?oh=43b51bde61b1e209146b380f0504dd00&oe=582DE60A&__gda__=1479068930_9b6225fef903d671bbed8484b89e2ad0","is_silhouette":false}}
     * id : 1001931719919937
     * first_name : Rinky
     * friends : {"summary":{"total_count":337},"data":[{"id":"1182642791787955","name":"Md Rahil"},{"id":"1001044726669472","name":"Arun Tyagi"}],"paging":{"cursors":{"after":"QVFIUk9IV0JiOWhyY2NzZAktnZATZAuUmplbTNZARkNtUGVsNXUyZAV95NHpjU2RMTDFiNnFLc3luTzhnazRPU0FQZA09IWUpmUUhmb3FuX2JXM0hZASWtjWmY4ZA2Rn","before":"QVFIUko1ZAVlCYmowUHZA5YjJwb0hUWmktVGFyclJlUzQtaU96T1NSLUlXbWZATTnk1bWRHQWhka255ZA0lQb0tCNjB3ZAzlqbThBTUg0c1ZAVbDBORzZArbnJCNFBn"}}}
     * email : rinks_sweetz@yahoo.in
     * name : Rinky Singh
     * last_name : Singh
     * gender : female
     */

    private String id;
    private String first_name;
    /**
     * summary : {"total_count":337}
     * data : [{"id":"1182642791787955","name":"Md Rahil"},{"id":"1001044726669472","name":"Arun Tyagi"}]
     * paging : {"cursors":{"after":"QVFIUk9IV0JiOWhyY2NzZAktnZATZAuUmplbTNZARkNtUGVsNXUyZAV95NHpjU2RMTDFiNnFLc3luTzhnazRPU0FQZA09IWUpmUUhmb3FuX2JXM0hZASWtjWmY4ZA2Rn","before":"QVFIUko1ZAVlCYmowUHZA5YjJwb0hUWmktVGFyclJlUzQtaU96T1NSLUlXbWZATTnk1bWRHQWhka255ZA0lQb0tCNjB3ZAzlqbThBTUg0c1ZAVbDBORzZArbnJCNFBn"}}
     */

    private FriendsEntity friends;
    private String email;
    private String name;
    private String last_name;
    private String gender;

    public void setPicture(PictureEntity picture) {
        this.picture = picture;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setFriends(FriendsEntity friends) {
        this.friends = friends;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public PictureEntity getPicture() {
        return picture;
    }

    public String getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public FriendsEntity getFriends() {
        return friends;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getGender() {
        return gender;
    }

    public static class PictureEntity {
        /**
         * url : https://fbcdn-profile-a.akamaihd.net/hprofile-ak-frc3/v/t1.0-1/c0.2.50.50/p50x50/995659_414453772001071_161061694_n.jpg?oh=43b51bde61b1e209146b380f0504dd00&oe=582DE60A&__gda__=1479068930_9b6225fef903d671bbed8484b89e2ad0
         * is_silhouette : false
         */

        private DataEntity data;

        public void setData(DataEntity data) {
            this.data = data;
        }

        public DataEntity getData() {
            return data;
        }

        public static class DataEntity {
            private String url;
            private boolean is_silhouette;

            public void setUrl(String url) {
                this.url = url;
            }

            public void setIs_silhouette(boolean is_silhouette) {
                this.is_silhouette = is_silhouette;
            }

            public String getUrl() {
                return url;
            }

            public boolean isIs_silhouette() {
                return is_silhouette;
            }
        }
    }

    public static class FriendsEntity {
        /**
         * total_count : 337
         */

        private SummaryEntity summary;
        /**
         * cursors : {"after":"QVFIUk9IV0JiOWhyY2NzZAktnZATZAuUmplbTNZARkNtUGVsNXUyZAV95NHpjU2RMTDFiNnFLc3luTzhnazRPU0FQZA09IWUpmUUhmb3FuX2JXM0hZASWtjWmY4ZA2Rn","before":"QVFIUko1ZAVlCYmowUHZA5YjJwb0hUWmktVGFyclJlUzQtaU96T1NSLUlXbWZATTnk1bWRHQWhka255ZA0lQb0tCNjB3ZAzlqbThBTUg0c1ZAVbDBORzZArbnJCNFBn"}
         */

        private PagingEntity paging;
        /**
         * id : 1182642791787955
         * name : Md Rahil
         */

        private List<DataEntity> data;

        public void setSummary(SummaryEntity summary) {
            this.summary = summary;
        }

        public void setPaging(PagingEntity paging) {
            this.paging = paging;
        }

        public void setData(List<DataEntity> data) {
            this.data = data;
        }

        public SummaryEntity getSummary() {
            return summary;
        }

        public PagingEntity getPaging() {
            return paging;
        }

        public List<DataEntity> getData() {
            return data;
        }

        public static class SummaryEntity {
            private int total_count;

            public void setTotal_count(int total_count) {
                this.total_count = total_count;
            }

            public int getTotal_count() {
                return total_count;
            }
        }

        public static class PagingEntity {
            /**
             * after : QVFIUk9IV0JiOWhyY2NzZAktnZATZAuUmplbTNZARkNtUGVsNXUyZAV95NHpjU2RMTDFiNnFLc3luTzhnazRPU0FQZA09IWUpmUUhmb3FuX2JXM0hZASWtjWmY4ZA2Rn
             * before : QVFIUko1ZAVlCYmowUHZA5YjJwb0hUWmktVGFyclJlUzQtaU96T1NSLUlXbWZATTnk1bWRHQWhka255ZA0lQb0tCNjB3ZAzlqbThBTUg0c1ZAVbDBORzZArbnJCNFBn
             */

            private CursorsEntity cursors;

            public void setCursors(CursorsEntity cursors) {
                this.cursors = cursors;
            }

            public CursorsEntity getCursors() {
                return cursors;
            }

            public static class CursorsEntity {
                private String after;
                private String before;

                public void setAfter(String after) {
                    this.after = after;
                }

                public void setBefore(String before) {
                    this.before = before;
                }

                public String getAfter() {
                    return after;
                }

                public String getBefore() {
                    return before;
                }
            }
        }

        public static class DataEntity {
            private String id;
            private String name;

            public void setId(String id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }
}
