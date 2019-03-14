package com.afinal.android.trafficpal.Activity;

/**
 * Created by DHIREN on 22-02-2018.
 */
public class Detail {


    String addr;
    String aod;
    String bdate;
    Long blacknumber;
    String cname;
    String issue_date;
    String licenceno;
    String sstatus;
    String validity;
    String url;
    Long lastsuspend;
    String sdate;


    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getAod() {
        return aod;
    }

    public void setAod(String aod) {
        this.aod = aod;
    }

    public Long getBlacknumber() {
        return blacknumber;
    }

    public void setBlacknumber(Long blacknumber) {
        this.blacknumber = blacknumber;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }

    public String getLicenceno() {
        return licenceno;
    }

    public void setLicenceno(String licenceno) {
        this.licenceno = licenceno;
    }

    public String getSstatus() {
        return sstatus;
    }

    public void setSstatus(String sstatus) {
        this.sstatus = sstatus;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getUrl() {
        return url;
    }

    public Long getLastsuspend() {
        return lastsuspend;
    }

    public void setLastsuspend(Long lastsuspend) {this.lastsuspend = lastsuspend;}

    public String getSdate() {
        return sdate;
    }

    public void setSdate(String sdate) {
        this.sdate = sdate;
    }
}