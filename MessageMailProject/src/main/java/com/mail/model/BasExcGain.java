package com.mail.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zouyu on 2019-4-4.
 */
@Entity
@Table(name = "BAS_EXC_GAIN")
@org.hibernate.annotations.Proxy(lazy = false)
public class BasExcGain {
    private String id;
    private String dataSrc;
    private Date receiveTm;
    private int tatalNum;
    private int errorNum;
    private String sysTyp;
    private String sysNme;

    @Id
    @Column(name = "c_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name = "c_data_src")
    public String getDataSrc() {
        return dataSrc;
    }

    public void setDataSrc(String dataSrc) {
        this.dataSrc = dataSrc;
    }
    @Column(name = "t_receive_tm")
    public Date getReceiveTm() {
        return receiveTm;
    }

    public void setReceiveTm(Date receiveTm) {
        this.receiveTm = receiveTm;
    }
    @Column(name = "c_tatal_num")
    public int getTatalNum() {
        return tatalNum;
    }

    public void setTatalNum(int tatalNum) {
        this.tatalNum = tatalNum;
    }
    @Column(name = "c_error_num")
    public int getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(int errorNum) {
        this.errorNum = errorNum;
    }
    @Column(name = "c_sys_typ")
    public String getSysTyp() {
        return sysTyp;
    }

    public void setSysTyp(String sysTyp) {
        this.sysTyp = sysTyp;
    }
    @Column(name = "c_sys_nme")
    public String getSysNme() {
        return sysNme;
    }

    public void setSysNme(String sysNme) {
        this.sysNme = sysNme;
    }
}
