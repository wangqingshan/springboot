<!DOCTYPE html>
<html>
<head>
    <title th:text="${title}"></title>
</head>
<body class="layout">
<div class="wrap">
    <!-- S top -->
    <div th:include="/header/module-header::module-header"></div>
    <!-- S 内容 -->
    <div class="panel-l container clearfix">
        <div class="error">
            <p class="title"><span class="code" th:text="${status}"></span>非常抱歉，没有找到您要查看的页面</p>
            <a href="/" class="btn-back common-button">返回首页
                <img class="logo-back" src="/images/back.png">
            </a>
            <div class="common-hint-word">
                <div th:text="">${path}</div>
                <div th:text="">${error}</div>
                <div th:text="">${timestamp?string("yyyy-MM-dd HH:mm:ss")}</div>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>