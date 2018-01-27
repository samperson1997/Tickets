pin = "";
isSeatSelectedGlobal = 0;

var getParam = function (name) {
    var search = document.location.search;
    var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
    var matcher = pattern.exec(search);
    var items = null;
    if (null !== matcher) {
        try {
            items = decodeURIComponent(decodeURIComponent(matcher[1]));
        } catch (e) {
            try {
                items = decodeURIComponent(matcher[1]);
            } catch (e) {
                items = matcher[1];
            }
        }
    }
    return items;
};

// 下单去支付
function placeOrder() {
    // 保存变量
    var orderId = 0;
    var email = sessionStorage.getItem('userId');
    var planId = getParam("planId");

    var state = "未支付";
    var seatAssigned = "";

    var couponId = 0;

    // 未选座
    var isSeatSelected = 0;
    var seatName = "";
    var seatNum = $("#no-choose-member-order-num").val();
    var price = $("#no-choose-final-price").text().split(" ")[1].split("元")[0];

    switch ($("#no-choose-coupon-select").val()) {
        case("1"):
            couponId = 1;
            break;
        case("2"):
            couponId = 2;
            break;
        case("5"):
            couponId = 3;
            break;
        case("10"):
            couponId = 4;
            break;
        case("20"):
            couponId = 5;
            break;
        case("50"):
            couponId = 6;
            break;
        case("100"):
            couponId = 7;
            break;
    }

    var maintainNum = 0;
    var maintainNumLess = false;

    // 已选座


    if ($("#type-select").val() === "choose") {
        isSeatSelected = 1;
        seatName = $("#seat-select").val().split("-")[0];
        maintainNum = $("#seat-select").val().split("-")[2];
        seatNum = $("#choose-member-order-num").val();

        console.log(maintainNum);
        console.log(seatNum);

        price = $("#choose-final-price").text().split(" ")[1].split("元")[0];

        switch ($("#choose-coupon-select").val()) {
            case("1"):
                couponId = 1;
                break;
            case("2"):
                couponId = 2;
                break;
            case("5"):
                couponId = 3;
                break;
            case("10"):
                couponId = 4;
                break;
            case("20"):
                couponId = 5;
                break;
            case("50"):
                couponId = 6;
                break;
            case("100"):
                couponId = 7;
                break;
        }

        isSeatSelectedGlobal = 1;

        if (maintainNum < seatNum) {
            maintainNumLess = true;
        }
    }

    if (isSeatSelected === 1 && maintainNumLess) {
        alert("所选座位类型数量不足, 请重新选择");
    } else {

        var realPrice = price;

        var orderBean = {};

        orderBean.orderId = orderId;
        orderBean.email = email;
        orderBean.planId = planId;
        orderBean.price = price;
        orderBean.realPrice = realPrice;
        orderBean.state = state;
        orderBean.seatAssigned = seatAssigned;
        orderBean.isSeatSelected = isSeatSelected;
        orderBean.seatName = seatName;
        orderBean.seatNum = seatNum;

        $.ajax({
            type: "POST",
            url: "/orders/addOrder",
            contentType: "application/json",
            data: JSON.stringify(orderBean),
            dataType: "text",
            success: function (data) {

                // 优惠券更新
                $.ajax({
                    type: "GET",
                    url: "/user/useCoupon",
                    contentType: "application/x-www-form-urlencoded",
                    data: {
                        "email": email,
                        "couponId": couponId
                    },
                    dataType: "json",
                    success: function (data1) {
                        console.log("update user coupon: " + data1.result);
                        window.location.href = "pay.html?orderId=" + data;
                    }
                });
            }
        })
    }
}

function loadAccount() {

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
            $("#current-account").text(data.account);
            pin = data.pin;
        },
        error: function (request, status, err) {
            if (status === "timeout") {
                load.abort();
            }
        }
    })
}

function loadFinalPrice() {
    var load = $.ajax({
        type: "GET",
        url: "/orders/order",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "orderId": getParam("orderId")
        },
        dataType: "json",
        success: function (data) {
            $("#final-price").text(data.price);
        }
    })
}

// 取消支付
function cancelPayOrder() {

    $.ajax({
        type: "POST",
        url: "/orders/cancelOrder",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "orderId": getParam("orderId")
        },
        dataType: "json",
        success: function (data) {
            window.location.href = "javascript: history.go(-1);";
        }
    })

}

// 确认支付
function payOrder() {

    $.ajax({
        type: "GET",
        url: "/orders/order",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "orderId": getParam("orderId")
        },
        dataType: "json",
        success: function (data1) {
            if (data1.state === "已关闭") {

                alert("由于您未及时支付，订单已经关闭。请重新订票！");
                window.location.href = "javascript: history.go(-1);";

            } else {
                var currentAccount = parseFloat($("#current-account").text());
                var finalPrice = parseFloat($("#final-price").text());

                if ($("#pin").val() !== pin) {
                    $("#pay-tip").text("密码错误。如果您未修改过，钱包密码默认与您初始设置的账户密码相同。");
                } else if (currentAccount < finalPrice) {
                    $("#pay-tip").text("钱包余额不足! ");
                } else {

                    $("#pay-tip").text("");

                    var email = sessionStorage.getItem('userId');

                    // 钱包钱减少, 会员积分增加
                    $.ajax({
                        type: "POST",
                        url: "/user/order",
                        contentType: "application/x-www-form-urlencoded",
                        data: {
                            "email": email,
                            "deltaAccount": -finalPrice,
                            "deltaScore": finalPrice * 100
                        },
                        dataType: "json",
                        success: function (data0) {
                            console.log("update user information: " + data0.result);

                            // 订单状态: isPaid = 1
                            var state = (isSeatSelectedGlobal === 1) ? "未开票" : "未配票";
                            $.ajax({
                                type: "POST",
                                url: "/orders/changeOrder",
                                contentType: "application/x-www-form-urlencoded",
                                data: {
                                    "orderId": getParam("orderId"),
                                    "state": state
                                },
                                dataType: "json",
                                success: function (data) {
                                    console.log("update order state: " + data.result);
                                    alert("支付成功! ");
                                    window.location.href = "index.html";
                                }
                            });

                        }
                    });

                }
            }
        }
    })


}