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

planId = getParam("planId");

function checkOrder() {
    var orderId = $("#check-member-order").val();
    var venueId = sessionStorage.getItem("venueId");

    $.ajax({
        type: "GET",
        url: "/orders/checkValidation",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "orderId": orderId,
            "venueId": venueId
        },
        dataType: "json",
        success: function (data2) {
            if (data2.result) {

                $.ajax({
                    type: "GET",
                    url: "/orders/order",
                    contentType: "application/x-www-form-urlencoded",
                    data: {
                        "orderId": orderId
                    },
                    dataType: "json",
                    success: function (data1) {
                        // 会员积分增加
                        $.ajax({
                            type: "POST",
                            url: "/user/order",
                            contentType: "application/x-www-form-urlencoded",
                            data: {
                                "email": data1.email,
                                "deltaAccount": 0,
                                "deltaScore": data1.realPrice * 100
                            },
                            dataType: "json",
                            success: function (data0) {
                                console.log("update user information: " + data0.result);

                                // 订单状态: isUsed = 1
                                $.ajax({
                                    type: "POST",
                                    url: "/orders/changeOrder",
                                    contentType: "application/x-www-form-urlencoded",
                                    data: {
                                        "orderId": orderId,
                                        "state": "已使用"
                                    },
                                    dataType: "json",
                                    success: function (data) {
                                        console.log("update order state: " + data.result);
                                        alert("取票成功! ");
                                        window.location.href = "venue-check.html?planId=" + planId;
                                    }
                                });

                            }
                        });
                    }
                })

            } else {
                $("#check-member-tip").text(data2.message);
            }
        }
    })
}