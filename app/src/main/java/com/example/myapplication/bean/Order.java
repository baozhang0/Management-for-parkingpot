package com.example.myapplication.bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Order {
    private String uindex;
    private String oindex;
    private String pindex;
    private int start,end;
    public static String dateSourceStr="2010-1-1 00";
    public static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH");
    private int status;
    private String startString,endString;
    private String licence;

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getOindex() {
        return oindex;
    }

    public void setOindex(String oindex) {
        this.oindex = oindex;
    }

    public String getUindex() {
        return uindex;
    }

    public void setUindex(String uindex) {
        this.uindex = uindex;
    }

    public String getPindex() {
        return pindex;
    }

    public void setPindex(String pindex) {
        this.pindex = pindex;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStartString() {
        return startString;
    }

    public void setStartString(String startString) {
        this.startString = startString;
    }

    public String getEndString() {
        return endString;
    }

    public void setEndString(String endString) {
        this.endString = endString;
    }

    public Order(String oindex, String uindex, String pindex, int start, int end, int status, String licence){
        this.oindex=oindex;
        this.uindex=uindex;
        this.pindex=pindex;
        this.start=start;
        this.end=end;
        this.status=status;
        this.licence=licence;
        try{
            Date dateSource=(Date)format.parse(dateSourceStr);
            Calendar calstart=Calendar.getInstance();
            Calendar calend=Calendar.getInstance();
            calstart.setTime(dateSource);
            calend.setTime(dateSource);
            calstart.add(Calendar.HOUR_OF_DAY,start);
            calend.add(Calendar.HOUR_OF_DAY,end);
            startString=format.format(calstart.getTime());
            endString=format.format(calend.getTime());
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    public Order(String oindex,int status){
        this.oindex=oindex;
        this.status=status;
    }
    public Order(){

    }

}
