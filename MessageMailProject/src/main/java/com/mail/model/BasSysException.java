package com.mail.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by zouyu on 2019-3-27.
 */
@Entity
@Table(name = "BAS_SYS_EXCEPTION")
@org.hibernate.annotations.Proxy(lazy = false)
public class BasSysException {
    private String id;
    private String sysTyp;
    private String sysCnm;
    private String exeTyp;
    private int total;
    private Date crtTm;
    private Date updTm;
    private String dataSrc;
    private String logTm;

    @Id
    @Column(name = "c_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name="c_sys_typ")
    public String getSysTyp() {
        return sysTyp;
    }

    public void setSysTyp(String sysTyp) {
        this.sysTyp = sysTyp;
    }
    @Column(name="c_sys_cnm")
    public String getSysCnm() {
        return sysCnm;
    }

    public void setSysCnm(String sysCnm) {
        this.sysCnm = sysCnm;
    }
    @Column(name="c_exc_typ")
    public String getExeTyp() {
        return exeTyp;
    }

    public void setExeTyp(String exeTyp) {
        this.exeTyp = exeTyp;
    }
    @Column(name="n_total")
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    @Column(name="t_crt_tm")
    public Date getCrtTm() {
        return crtTm;
    }

    public void setCrtTm(Date crtTm) {
        this.crtTm = crtTm;
    }
    @Column(name="t_upd_tm")
    public Date getUpdTm() {
        return updTm;
    }

    public void setUpdTm(Date updTm) {
        this.updTm = updTm;
    }

    @Column(name="c_data_src")
    public String getDataSrc() {return dataSrc;}

    public void setDataSrc(String dataSrc) {this.dataSrc = dataSrc;}
    @Column(name="t_log_tm")
    public String getLogTm() {
        return logTm;
    }
    public void setLogTm(String logTm) {
        this.logTm = logTm;
    }
}
