<div style="background:#f2f2f2;">
    <div style="background:url(http://192.168.22.208/group1/M00/00/00/wKgW0FkjpfqAJAPnAAAlhmNDKpE469_big.png) center top #f2f2f2 no-repeat; height:191px; text-align:left; position:relative; border-radius:8px 8px 0 0;">
        <div style="padding:26px 0 0; color:#fff; font-size:42px; font-weight:100; text-align:center;">${title}</div>

        <div style="margin:5px 0 0; color:#fff; font-size:20px; font-weight:100; text-align:center;">${date}</div>

        <div style="margin:15px 0 30px; padding:0 10px; color:#fff; font-size:16px; font-weight:100; line-height:1.4; text-align:center;">${welcome}</div>
    </div>
</div>

<div style="margin:10px 0 0; padding:0 10px; font-size:18px; color:#363c4c;">
<pre>


     <table class="table table-striped table-sm">
                    <thead>
                    <tr>
                        <th>时间</th>
                        <th>异常类型</th>
                        <th>异常次数</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#--<tr th:each="undr:${ExInfo}">-->
                        <td th:text="${ExInfo.time}"></td>
                        <td th:text="${ExInfo.exName}"></td>
                        <td th:text="${ExInfo.exNum}"></td>
                    <#--</tr>-->
                    </tbody>
                </table>


<#--<p>-->

<#--张先生：<br/>-->
         <#--您于2013年9月1日在我公司单担任 总经理 职务，<br/>-->
         <#--根据公司有关规定及您的工作绩效和表现，您不适合本公司此职位，<br/>-->
         <#--故决定自2017年5月23日起，本公司解除与您的聘雇劳动关系，<br/>-->
         <#--请在收到通知书二日内在公司办公室办理相关离职手续。<br/>-->
         <#--非常感谢您在本公司的辛勤工作！同时祝愿您在未来有更好的发展！<br/>-->
<#--</p>-->
    <#---->

</pre>
</div>

<div style="background:#fff; position:relative; margin:10px 20px 0; padding:0 0 60px; border-bottom:1px solid #f1f1f1; border-radius:3px;">

    <div style="padding:40px 0 0; font-size:34px;">${notice}</div>
    <div style="margin:10px 0 0; padding:0 10px; font-size:18px; color:#363c4c;">变是永远<span style="font-size:40px; color:#f44336; font-style:italic;">不变</span> 的</div>
    <div style="margin:10px 0 0; padding:0 10px; font-size:18px; color:#363c4c;">没有教不好的学生，只有不会教的老师！</div>
</div>
