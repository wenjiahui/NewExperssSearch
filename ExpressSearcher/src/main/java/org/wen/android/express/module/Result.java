package org.wen.android.express.module;

import java.util.List;

/**
 * Created by wenjiahui on 9/21/13.
 *
 *   "nu":"668535524584",
 *   "message":"ok",
 *   "ischeck":"1",
 *   "com":"shentong",
 *   "updatetime":"2013-09-21 23:46:57",
 *   "status":"200",
 *   "condition":"F00",
 *   "data":{}
 *   "state":"3"
 *
 */
public class Result {
    public String nu;
    public String message;
    public String ischeck;
    public String com;
    public String updatetime;
    public String status;
    public String condition;
    public String state;
    public List<Details> data;

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<Details> getData() {
        return data;
    }

    public void setData(List<Details> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "nu='" + nu + '\'' +
                ", message='" + message + '\'' +
                ", ischeck='" + ischeck + '\'' +
                ", com='" + com + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", status='" + status + '\'' +
                ", condition='" + condition + '\'' +
                ", state='" + state + '\'' +
                ", data=" + data +
                '}';
    }
}
