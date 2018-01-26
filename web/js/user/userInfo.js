window.onload = init;

oldPassword = "";
level = 0;
score = 0;
pin = "";
account = 0.0;
name = "";

passwordTip = document.getElementById('password-tip');
pinTip = document.getElementById('pin-tip');

function init() {
    pinTip.innerHTML = "";
    passwordTip.innerHTML = "";
}

function loadUserInfo() {
    var email = sessionStorage.getItem('userId');

    var load = $.ajax({
        type: "GET",
        url: "/user/info",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "email": email
        },
        dataType: "json",
        success: function (data) {
            $("#email").html(data.email);
            $("#name").attr("value", data.name);
            $("#level").html(data.level);
            $("#score").html(data.score);
            $("#account").html(data.account);

            oldPassword = data.password;
            level = data.level;
            score = data.score;
            pin = data.pin;
            account = data.account;
            name = data.name;
        },
        error: function (request, status, err) {
            if (status === "timeout") {
                load.abort();
            }
        }
    })
}

function cancelMember() {
    var email = sessionStorage.getItem('userId');
    $.ajax({
        type: "POST",
        url: "/user/cancelMember",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "email": email
        },
        dataType: "json",
        success: function (data) {
            window.location.href = "/index.html";
            alert("会员账号已注销");
        }
    })
}

function convertCoupon(couponId) {
    var email = sessionStorage.getItem('userId');
    $.ajax({
        type: "GET",
        url: "/user/convertCoupon",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "email": email,
            "couponId": couponId
        },
        dataType: "json",
        success: function (data) {
            if (data.result) {
                window.location.href = "/member.html";
                alert("优惠券兑换成功");
            } else {
                alert(data.message);
            }
        }
    })
}

function loadCouponList() {
    var email = sessionStorage.getItem('userId');
    $.ajax({
        type: "GET",
        url: "/user/coupon",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "email": email
        },
        dataType: "json",
        success: function (data) {
            if (data !== null && data.length !== 0) {
                for (var i = 0; i < data.length; i++) {
                    $("#coupon-list").append("<p>" + data[i].coupon + " " + data[i].number + "张</p>")
                }
            } else {
                $("#coupon-list").html("<p>暂无优惠券</p>")
            }
        }
    })
}

function updateInfo() {

    var userBean = {};
    userBean.email = sessionStorage.getItem('userId');
    userBean.name = $("#name").val();
    userBean.password = oldPassword;
    userBean.isMember = 1;
    userBean.level = level;
    userBean.score = score;
    userBean.pin = pin;
    userBean.account = account;

    console.log(userBean);

    $.ajax({
        type: "POST",
        url: "/user/edit",
        contentType: "application/json",
        data: JSON.stringify(userBean),
        dataType: "json",
        success: function (data) {
            alert("会员昵称修改成功! ");
            window.location.href = "/member.html";
        }
    })
}

function passwordIsSame() {
    if ($("#new-password").val() !== $("#new-password-confirm").val()) {
        passwordTip.innerHTML = "两次输入密码不一致";
    } else {
        passwordTip.innerHTML = "";
    }
}

function pinIsSame() {
    if ($("#new-pin").val() !== $("#new-pin-confirm").val()) {
        pinTip.innerHTML = "两次输入密码不一致";
    } else {
        pinTip.innerHTML = "";
    }
}

function userChangePassword() {
    if ($("#old-password").val() !== oldPassword) {
        passwordTip.innerHTML = "原密码错误";
    } else if ($("#old-password").val() === $("#new-password").val()) {
        passwordTip.innerHTML = "新旧密码相同";
    } else {
        passwordTip.innerHTML = "";

        var userBean = {};
        userBean.email = sessionStorage.getItem('userId');
        userBean.name = name;
        userBean.password = $("#new-password").val();
        userBean.isMember = 1;
        userBean.level = level;
        userBean.score = score;
        userBean.pin = pin;
        userBean.account = account;

        console.log(userBean);

        $.ajax({
            type: "POST",
            url: "/user/edit",
            contentType: "application/json",
            data: JSON.stringify(userBean),
            dataType: "json",
            success: function (data) {
                alert("账户密码修改成功, 请重新登录");
                window.location.href = "/login.html";
            }
        })
    }
}

function userChangePin() {
    if ($("#old-pin").val() !== pin) {
        pinTip.innerHTML = "原密码错误";
    } else if ($("#old-pin").val() === $("#new-pin").val()) {
        pinTip.innerHTML = "新旧密码相同";
    } else {
        pinTip.innerHTML = "";

        var userBean = {};
        userBean.email = sessionStorage.getItem('userId');
        userBean.name = name;
        userBean.password = oldPassword;
        userBean.isMember = 1;
        userBean.level = level;
        userBean.score = score;
        userBean.pin = $("#new-pin").val();
        userBean.account = account;

        console.log(userBean);

        $.ajax({
            type: "POST",
            url: "/user/edit",
            contentType: "application/json",
            data: JSON.stringify(userBean),
            dataType: "json",
            success: function (data) {
                alert("钱包密码修改成功, 请重新登录");
                window.location.href = "/login.html";
            }
        })
    }
}