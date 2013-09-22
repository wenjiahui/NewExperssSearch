package org.wen.express.module;

/**
 * Created by wenjiahui on 9/21/13.
 * bean details:
 *
 *     "time":"2013-09-20 12:22:08",
 *     "context":"已签收,签收人是【本人】",
 *     "ftime":"2013-09-20 12:22:08"
 */
public class Details {

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String time;
    public String context;
    public String ftime;

    @Override
    public String toString() {
        return "Details{" +
                "time='" + time + '\'' +
                ", context='" + context + '\'' +
                ", ftime='" + ftime + '\'' +
                '}';
    }

}
