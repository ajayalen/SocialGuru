package com.socialnetwork.models.responsemodels;

import java.util.List;

/**
 * Created by anand on 14/9/16.
 */
public class DeleteSocialPlatformRes {

    /**
     * Success : true
     * Message : Account deleted successfully
     * Result : []
     * Status : 200
     */

    private boolean Success;
    private String Message;
    private int Status;
    private List<?> Result;

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

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }

    public List<?> getResult() {
        return Result;
    }

    public void setResult(List<?> Result) {
        this.Result = Result;
    }
}
