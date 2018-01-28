state = "";
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
            $("#final-price").text(data.price.toFixed(2));
            state = data.state;

            var returnPrice = data.price * 0.8;
            if (state === "已开票" || state === "已配票") {
                returnPrice = data.price * 0.5;
            }

            $("#return-price").text(returnPrice.toFixed(2));
        }
    })
}

// 取消支付
function cancelCancelOrder() {
    window.location.href = "javascript: history.go(-1);";
}

// 确认取消
function cancelOrder() {

    var finalPrice = parseFloat($("#final-price").text());
    var returnPrice = parseFloat($("#return-price").text());

    var email = sessionStorage.getItem('userId');

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

                alert("订单之前已取消过! ");
                window.location.href = "javascript: history.go(-1);";

            } else {
                $.ajax({
                    type: "GET",
                    url: "/user/info",
                    contentType: "application/x-www-form-urlencoded",
                    data: {
                        "email": email
                    },
                    dataType: "json",
                    success: function (data) {
                        var pin = data.pin;

                        if ($("#pin").val() !== pin) {
                            $("#pay-tip").text("密码错误。如果您未修改过，钱包密码默认与您初始设置的账户密码相同。");
                        } else {

                            $("#pay-tip").text("");

                            // 钱包钱增加, 会员积分不减少
                            $.ajax({
                                type: "POST",
                                url: "/user/order",
                                contentType: "application/x-www-form-urlencoded",
                                data: {
                                    "email": email,
                                    "deltaAccount": returnPrice,
                                    "deltaScore": 0
                                },
                                dataType: "json",
                                success: function (data0) {
                                    console.log("update user information: " + data0.result);

                                    // 订单状态: isClosed = 1
                                    $.ajax({
                                        type: "POST",
                                        url: "/orders/cancelOrder",
                                        contentType: "application/x-www-form-urlencoded",
                                        data: {
                                            "orderId": getParam("orderId")
                                        },
                                        dataType: "json",
                                        success: function (data) {
                                            console.log("update order state: " + data.result);
                                            alert("订单已取消! ");
                                            window.location.href = "order.html";
                                        }
                                    })

                                }
                            });

                        }
                    }
                });
            }
        }
    });


}