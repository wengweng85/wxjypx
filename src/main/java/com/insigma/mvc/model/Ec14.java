package com.insigma.mvc.model;

import java.util.Date;

public class Ec14 {
    private String eec141;

    private String aec101;

    private String aec107;

    private Date aec108;

    public String getEec141() {
        return eec141;
    }

    public void setEec141(String eec141) {
        this.eec141 = eec141 == null ? null : eec141.trim();
    }

    public String getAec101() {
        return aec101;
    }

    public void setAec101(String aec101) {
        this.aec101 = aec101 == null ? null : aec101.trim();
    }

    public String getAec107() {
        return aec107;
    }

    public void setAec107(String aec107) {
        this.aec107 = aec107 == null ? null : aec107.trim();
    }

    public Date getAec108() {
        return aec108;
    }

    public void setAec108(Date aec108) {
        this.aec108 = aec108;
    }
}