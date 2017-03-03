<%@page language="java" contentType="text/html; charset=utf-8" %>
<html>
<head>
    <title>移动端钉钉企业接入</title>
    <script src="https://g.alicdn.com/ilw/ding/0.9.2/scripts/dingtalk.js"></script>
    <script src="./jquery-3.1.0.js"></script>
</head>
<script type="text/javascript">
    Window.authcode = "";

    $(document).ready(function () {
        var url = window.location.href;
        console.log("url", url);
        var corpId = "dingf57f3beb11fe1a4735c2f4657eb6378f";  // 企业的corpId
        var signature = "";
        var nonceStr = "";
        var timeStamp = "";
        var agentId = "";

        $.post(
            'get_js_config',
            {
                "url": url,
                "corpId": corpId
            },
            function (result) {
                console.log("result", result);
                signature = result.signature;
                nonceStr = result.nonceStr;
                timeStamp = result.timeStamp;
                agentId = result.agentId;
                corpId = result.corpId;

                dd.config({
                    agentId: agentId,
                    corpId: corpId,
                    timeStamp: timeStamp,
                    nonceStr: nonceStr,
                    signature: signature,
                    jsApiList: [
                        'runtime.info',
                        'biz.contact.choose',
                        'device.notification.confirm',
                        'device.notification.alert',
                        'device.notification.prompt',
                        'biz.ding.post',
                        'biz.util.openLink'] //必填，需要使用的jsapi列表
                });

                dd.ready(function () {
                        console.log('dd.ready rocks!')

                        dd.runtime.info({
                            onSuccess: function (info) {
                                console.log('runtime info: ' + JSON.stringify(info));
//                                alert(JSON.stringify(info));
                            },
                            onFail: function (err) {
                                console.log('fail: ' + JSON.stringify(err));
//                                alert(JSON.stringify(err));
                            }
                        });

                        dd.runtime.permission.requestAuthCode({
                            corpId: corpId, //企业id
                            onSuccess: function (info) {
                                console.log('authcode' + info.code);
//                                alert('authcode = '+info.code);
                                Window.authcode = info.code;
                            },
                            onFail: function (err) {
                                console.log('requestAuthCode fail: ' + JSON.stringify(err));
//                                alert(JSON.stringify(err));
                            }
                        });
                    }
                );
            })
    });

    function auth() {
        $("#code").empty();
        var html = "<span>" + Window.authcode + "</span>";
//        alert(html);
        $("#code").append(html);
    }

    function getUserInfo() {
        var code = Window.authcode;
        $.get(
            'get_userInfo',
            {
                "code": code
            },
            function (result) {
                console.log(result);
                $("#user").empty();
                var html = "<ul>";
                html += "<li>"+result.userId+"</li>";
                html += "<li>"+result.isSys+"</li>";
                html += "<li>"+result.deviceId+"</li>";
                html += "<li>"+result.sysLevel+"</li>";
                html += "</ul>"
                $("#user").append(html);
            }
        );
    }

</script>

<style type="text/css">
    .btn {
        height: 80px;
        width: 200px;
        font-size: 24px;
        font-family: "Arial Black";
    }

    .content {
        font-family: "Arial Black";
        font-size: 48px;
    }
</style>

<body>
<h2>Hello 钉钉!</h2>
</body>
    <div>
        <button class="btn" onclick="auth()">显示免登授权码</button>
        <div id="code" class="content">
    </div>
    <div>
        <button class="btn" onclick="getUserInfo()">通过CODE换取身份</button>
        <div id="user" class="content"></div>
    </div>
</div>
</html>
