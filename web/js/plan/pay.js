pin = "";
orderBean = {};

var getParam = function (name) {
    var search = document.location.search;
    var pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
    var matcher = pattern.exec(search);
    var items = null;
    if (null != matcher) {
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

    // 未选座
    var isSeatSelected = 0;
    var seatName = "";
    var seatNum = $("#no-choose-member-order-num").val();
    var price = $("#no-choose-final-price").text().split(" ")[1].split("元")[0];

    // 已选座
    if ($("#type-select").val() === "choose") {
        isSeatSelected = 1;
        seatName = $("#seat-select").val().split("-")[0];
        seatNum = $("#choose-member-order-num").val();
        price = $("#choose-final-price").text().split(" ")[1].split("元")[0];
    }

    var realPrice = price;

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
            window.location.href = "pay.html?orderId=" + data;
        }
    })
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

function payOrder() {
    if ($("#pin").val() !== pin) {
        $("#pay-tip").text("密码错误。如果您未修改过，钱包密码默认与您初始设置的账户密码相同。");
    } else {
        alert("支付成功! ");
        window.location.href = "index.html";
    }
}

function cancelPayOrder() {
    window.location.href = "javascript: history.go(-1);";
}