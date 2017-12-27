package vn.com.fpt.frt_minventory.Model;

/**
 * Created by ADMIN on 11/17/2017.
 */

public class PropertiesInventoryList {
    String id;
    String time;
    String stt;
    String date;
    String member;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public PropertiesInventoryList(String id, String time, String stt, String date, String member){
     this.id = id;
     this.time = time;
     this.stt = stt;
     this.date = date;
     this.member = member;
    }
}
