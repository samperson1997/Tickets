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

function loadPlanSeats() {
    $.ajax({
        type: "GET",
        url: "/plans/detailedPlan",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "planId": getParam("planId")
        },
        dataType: "json",
        success: function (data) {
            for (var i = 0; i < data.seatPriceBeanList.length; i++) {
                if (data.seatPriceBeanList[i].seatNum !== 0) {
                    $("#seat-select").append("<option value='" + data.seatPriceBeanList[i].seatName
                        + "-" + data.seatPriceBeanList[i].seatPrice + "-" + data.seatPriceBeanList[i].seatNum + "'>"
                        + data.seatPriceBeanList[i].seatName + "区</option>");
                    $("#no-member-seat-select").append("<option value='" + data.seatPriceBeanList[i].seatName
                        + "-" + data.seatPriceBeanList[i].seatPrice + "-" + data.seatPriceBeanList[i].seatNum + "'>"
                        + data.seatPriceBeanList[i].seatName + "区</option>");
                }
            }

            changeMemberTotalPrice();
            changeNoMemberTotalPrice();
        }
    })
}

// 计算总价
function changeMemberTotalPrice() {
    var email = $("#buy-member-email").val();

    $.ajax({
        type: "GET",
        url: "/user/discount",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "email": email
        },
        dataType: "json",
        success: function (data) {
            var seatPrice = $("#seat-select").val().split("-")[1];
            var totalPrice = $("#choose-member-order-num").val() * seatPrice;
            var finalPrice = data * totalPrice;

            $("#choose-total-price").text("原价: " + totalPrice.toFixed(2) + "元");
            $("#choose-final-price").text("优惠后总计: " + finalPrice.toFixed(2) + "元");
        }
    })

}

function changeNoMemberTotalPrice() {

    var seatPrice = $("#no-member-seat-select").val().split("-")[1];
    var totalPrice = $("#choose-no-member-order-num").val() * seatPrice;
    $("#no-member-choose-total-price").text(totalPrice.toFixed(2) + "元");

}

// 确认订单
function placeOrder() {
    // 保存变量
    var orderId = 0;
    var email = $("#buy-member-email").val();
    var planId = getParam("planId");

    var state = "已使用";
    var seatAssigned = "";
    var isSeatSelected = 1;
    var seatName = $("#seat-select").val().split("-")[0];
    var seatNum = $("#choose-member-order-num").val();
    var price = $("#choose-final-price").text().split(" ")[1].split("元")[0];

    var maintainNum = $("#seat-select").val().split("-")[2];


    if (parseInt(maintainNum) < parseInt(seatNum)) {
        alert("所选座位类型数量不足, 请重新选择");
    } else {
        var orderBean = {};

        orderBean.orderId = orderId;
        orderBean.email = email;
        orderBean.planId = planId;
        orderBean.price = price;
        orderBean.realPrice = price;
        orderBean.state = state;
        orderBean.seatAssigned = seatAssigned;
        orderBean.isSeatSelected = isSeatSelected;
        orderBean.seatName = seatName;
        orderBean.seatNum = seatNum;

        console.log(orderBean);

        $.ajax({
            type: "POST",
            url: "/orders/addOrderOnScene",
            contentType: "application/json",
            data: JSON.stringify(orderBean),
            dataType: "text",
            success: function (data) {
                alert("座位号为" + seatName + "区 " + data);

                // 会员积分增加
                $.ajax({
                    type: "POST",
                    url: "/user/order",
                    contentType: "application/x-www-form-urlencoded",
                    data: {
                        "email": email,
                        "deltaAccount": 0,
                        "deltaScore": price * 100
                    },
                    dataType: "json",
                    success: function (data0) {
                        console.log("update user information: " + data0.result);
                    }
                });
            }
        })
    }
}

function placeNoMemberOrder() {
    // 保存变量
    var orderId = 0;
    var email = "";
    var planId = getParam("planId");

    var state = "已使用";
    var seatAssigned = "";
    var isSeatSelected = 1;
    var seatName = $("#seat-select").val().split("-")[0];
    var seatNum = $("#choose-member-order-num").val();
    var price = $("#choose-final-price").text().split(" ")[1].split("元")[0];

    var maintainNum = $("#seat-select").val().split("-")[2];


    if (parseInt(maintainNum) < parseInt(seatNum)) {
        alert("所选座位类型数量不足, 请重新选择");
    } else {
        var orderBean = {};

        orderBean.orderId = orderId;
        orderBean.email = email;
        orderBean.planId = planId;
        orderBean.price = price;
        orderBean.realPrice = price;
        orderBean.state = state;
        orderBean.seatAssigned = seatAssigned;
        orderBean.isSeatSelected = isSeatSelected;
        orderBean.seatName = seatName;
        orderBean.seatNum = seatNum;

        $.ajax({
            type: "POST",
            url: "/orders/addOrderOnScene",
            contentType: "application/json",
            data: JSON.stringify(orderBean),
            dataType: "text",
            success: function (data) {
                alert("座位号为" + seatName + "区 " + data);
            }
        })
    }
}
