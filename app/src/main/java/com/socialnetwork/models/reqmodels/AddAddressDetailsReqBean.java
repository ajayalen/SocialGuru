package com.socialnetwork.models.reqmodels;

import java.util.List;

/**
 * Created by sakshi on 6/9/16.
 */
public class AddAddressDetailsReqBean {

    /**
     * user_id : 1
     * address : [{"address_tag":"home","address":"A-8 ,GreenBoulevard"},{"address_tag":"home","address":"A-8, GreenBoulevard"}]
     */

    private String user_id;
    /**
     * address_tag : home
     * address : A-8 ,GreenBoulevard
     */

    private List<AddressBean> address;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<AddressBean> getAddress() {
        return address;
    }

    public void setAddress(List<AddressBean> address) {
        this.address = address;
    }

    public static class AddressBean {
        private String address_tag;
        private String address;

        public String getAddress_tag() {
            return address_tag;
        }

        public void setAddress_tag(String address_tag) {
            this.address_tag = address_tag;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
