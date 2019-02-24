$(function() {
    var loginUrl = '/xysp/frontend/localauthlogincheck';
    var loginCount = 0;

    $('#submit').click(function() {
        var username = $('#username').val();
        var password = $('#psw').val();
        var verifyCodeActual = $('#j_captcha').val();
        var needVerify = false;
        if (loginCount >= 3) {
            if (!verifyCodeActual) {
                $.toast('请输入验证码！');
                return;
            } else {
                needVerify = true;
            }
        }
        $.ajax({
            url : loginUrl,
            async : false,
            cache : false,
            type : "post",
            dataType : 'json',
            data : {
                username : username,
                password : password,
                verifyCodeActual : verifyCodeActual,
                needVerify : needVerify
            },
            success : function(data) {
                if (data.success) {
                    $.toast(data.errMsg);
                    window.location.href = '/xysp/shopadmin/shoplist';
                } else {
                    $.toast(data.errMsg);
                    loginCount++;
                    if (loginCount >= 3) {
                        $('#verifyPart').show();
                    }
                }
            }
        });
    });

    $('#register').click(function() {
        window.location.href = '/xysp/frontend/register';
    });
});