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
            $("#name").html(data.name);
            $("#level").html(data.level);
            $("#score").html(data.score);
            $("#account").html(data.account);
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
