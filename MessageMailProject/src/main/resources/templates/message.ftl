<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>异常消息通知</title>
</head>

<style type="text/css">
    table {
        font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
        width: 100%;
        border-collapse: collapse;
    }
    td, th {
        font-size: 1em;
        border: 1px solid #5B4A42;
        padding: 3px 7px 2px 7px;
    }
    th {
        font-size: 1.1em;
        text-align: center;
        padding-top: 5px;
        padding-bottom: 4px;
        background-color: #24A9E1;
        color: #ffffff;
    }
</style>
<body>
<div>
    <h1>异常消息通知</h1>
    <br><br>
    <h2>一、今日异常统计(${(date)!""})</h2>
    <table class="table table-striped table-sm">
        <tr>
            <th>时间</th>
            <th>系统名称</th>
            <th>日志总行数</th>
            <th>异常数</th>
            <th>数据来源</th>
        </tr>
    <#if listGainExc?exists>
        <#list listGainExc as gain>
            <tr>
                <td>${(gain.time)!""}</td>
                <td>${(gain.sysName)!""}</td>
                <td>${(gain.totalNum)!""}</td>
                <td>${(gain.errorNum)!""}</td>
                <td>${(gain.dataSrc)!""}</td>
            </tr>
        </#list>
    </#if>
    </table>
</div>
<br><br>
<h2>二、今日异常分类(${(date)!""})</h2>
<div>
    <table class="table table-striped table-sm">
        <tr>
            <th>时间</th>
            <th>系统名称</th>
            <th>异常类型</th>
            <th>异常次数</th>
            <th>数据来源</th>
        </tr>
    <#if listInfo?exists>
        <#list listInfo as item>
            <tr>
                <td>${(item.time)!""}</td>
                <td>${(item.sysName)!""}</td>
                <td>${(item.exName)!""}</td>
                <td>${(item.exNum)!""}</td>
                <td>${(item.dataSrc)!""}</td>
            </tr>
        </#list>
    </#if>
    </table>
</div>
<br><br>
<div style="background:#3587f2; padding:12px; line-height:1.8; font-size:14px; color:#fff;">提示：请不要对此邮件直接回复，系统看不懂您的回信^_^。如果您有建议或意见，请发送邮件到zouyu@zsins.com
</div>
</body>
</html>
