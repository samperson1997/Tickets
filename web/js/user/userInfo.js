oldPassword = "";
level = 0;
score = 0;
pin = "";
account = 0.0;

function loadUserInfo() {
    var email = sessionStorage.getItem('userId');
    console.log(email);

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
    alert("update!");

    var userBean = {};
    userBean.email = sessionStorage.getItem('userId');
    userBean.name = $("#name").val();
    userBean.password = oldPassword;
    userBean.isMember = 1;
    userBean.level = level;
    userBean.score = score;
    userBean.pin = pin;
    userBean.account = account;

    $.ajax({
        type: "POST",
        url: "/user/edit",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "userBean": userBean
        },
        dataType: "json",
        success: function (data) {
            alert("会员昵称修改成功! ");
        }
    })
}