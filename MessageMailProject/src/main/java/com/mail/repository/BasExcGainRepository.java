package com.mail.repository;

import com.mail.model.BasExcGain;
import com.mail.model.BasSysException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by zouyu on 2019-4-4.
 */
public interface BasExcGainRepository extends JpaRepository<BasExcGain,String> {

    @Query(value = "SELECT a.c_send_mail FROM bas_main_send a, BAS_send_admin b where a.c_send_id = b.c_send_id and b.c_sys_typ = ?1 and b.c_send_typ = '01001' and b.c_is_copy = '0'",nativeQuery = true)
    public List<String> getExeSend(String sysCode);//发邮件的列表

    @Query(value = "SELECT a.c_send_mobile FROM bas_main_send a, BAS_send_admin b where a.c_send_id = b.c_send_id and b.c_sys_typ = ?1 and b.c_send_typ = '01002'",nativeQuery = true)
    public List<String> getPhoneSend(String sysCode);//发短信的列表

    @Query(value = "SELECT a.c_send_mail FROM BAS_ATTACH_SEND a, BAS_send_admin b where a.c_send_id = b.c_send_id and b.c_sys_typ = ?1 and b.c_send_typ = '01002' and b.c_is_copy = '1'",nativeQuery = true)
    public List<String> getAttachSend(String sysCode);//发短信的列表

    @Query(value = "select * from BAS_EXC_GAIN where TO_CHAR(t_receive_tm, 'yyyy-MM-dd')=TO_CHAR(SYSDATE, 'yyyy-MM-dd')",nativeQuery = true)
    public List<BasExcGain> getGainMsg();

    @Query(value = "SELECT a.c_send_mail FROM bas_main_send a, BAS_send_admin b where a.c_send_id = b.c_send_id and a.c_sys_typ = '03001'and b.c_send_typ = '01002'and b.c_emerge_typ = 'A'",nativeQuery = true)
    public List getSendMainList();

    @Query(value = "SELECT * FROM BAS_SYS_EXCEPTION where TO_CHAR(t_crt_tm, 'YYYY-MM-DD')=TO_CHAR(SYSDATE, 'YYYY-MM-DD') order by C_DATA_SRC",nativeQuery = true)
    public List<BasSysException> getExcMsg();

}
