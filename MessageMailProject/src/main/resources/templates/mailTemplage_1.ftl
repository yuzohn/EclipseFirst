<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${(UserName)!""}</title>
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
    <h2>${(titleName)!""}</h2>
    <table id="customers">
        <tr>
            <th>统计时间</th>
            <th>异常名</th>
            <th>异常次数</th>
        </tr>
<#if listInfo?exists>
    <#list listInfo as item>
        <tr>
            <td>${(item.time)!""}</td>
            <td>${(item.exName)!""}</td>
            <td>${(item.exNum)!""}</td>
        </tr>
    </#list>
</#if>
    </table>
</div>
</body>
</html>
