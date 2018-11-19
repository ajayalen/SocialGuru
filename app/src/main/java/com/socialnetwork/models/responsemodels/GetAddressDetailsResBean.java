package com.socialnetwork.models.responsemodels;

import java.util.List;

/**
 * Created by sakshi on 6/9/16.
 */
public class GetAddressDetailsResBean {

    /**
     * Success : true
     * Message : Address detailed information
     * Result : {"user_id":"9","address":[{"address_id":"12","address_tag":"dsd","address":"sds"}]}
     * Status : 200
     */

    private boolean Success;
    private String Message;
    /**
     * user_id : 9
     * address : [{"address_id":"12","address_tag":"dsd","address":"sds"}]
     */

    private ResultBean Result;
    private int Status;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public ResultBean getResult() {
        return Result;
    }

    public void setResult(ResultBean Result) {
        this.Result = Result;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public static class ResultBean {
        private String user_id;
        /**
         * address_id : 12
         * address_tag : dsd
         * address : sds
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
            private String address_id;
            private String address_tag;
            private String address;

            public String getAddress_id() {
                return address_id;
            }

            public void setAddress_id(String address_id) {
                this.address_id = address_id;
            }

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
}
