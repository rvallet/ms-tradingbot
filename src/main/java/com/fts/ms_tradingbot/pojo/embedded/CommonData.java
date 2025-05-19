package com.fts.ms_tradingbot.pojo.embedded;

import java.util.Date;

public class CommonData {

    private Date creationDate;

    private Date modificationDate;

    public CommonData() {
        this.creationDate = new Date();
        this.modificationDate = new Date();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

}
