highPrice = 0.0;

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

function loadDetailedPlan() {
    $.ajax({
        type: "GET",
        url: "/plans/detailedPlan",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "planId": getParam("planId")
        },
        dataType: "json",
        success: function (data) {
            $("#member-plan-introduction").text(data.introduction);
            $("#member-plan-date").text(data.startTime.substr(0, 10));
            $("#member-plan-start").text(data.startTime.substr(11));
            $("#member-plan-end").text(data.endTime.substr(11));

            switch (data.type) {
                case (1):
                    $("#member-plan-type").text("音乐演唱");
                    break;
                case (2):
                    $("#member-plan-type").text("舞蹈表演");
                    break;
                case (3):
                    $("#member-plan-type").text("话剧戏剧");
                    break;
                case (4):
                    $("#member-plan-type").text("体育比赛");
                    break;
                case (5):
                    $("#member-plan-type").text("其他活动");
                    break;
            }


            $("#member-plan-location").text(data.name + ", " + data.location);
            highPrice = data.highPrice;

            for (var i = 0; i < data.seatPriceBeanList.length; i++) {
                $("#member-plan-price").append("<p class=\"item-title\">" + data.seatPriceBeanList[i].seatName + "区(剩余"
                    + data.seatPriceBeanList[i].seatNum + "座) 票价</p>" + data.seatPriceBeanList[i].seatPrice + "元");
            }
        }
    })
}

function changeChooseDiv() {
    if ($("#type-select").val() === "choose") {
        $("#no-choose-div").fadeOut(function () {
            $("#choose-div").fadeIn();
        });

    } else if ($("#type-select").val() === "no-choose") {
        $("#choose-div").fadeOut(function () {
            $("#no-choose-div").fadeIn();
        });
    }
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
                $("#seat-select").append("<option value='" + data.seatPriceBeanList[i].seatName
                    + "-" + data.seatPriceBeanList[i].seatPrice + "'>"
                    + data.seatPriceBeanList[i].seatName + "区</option>");
            }

            changeChooseTotalPrice();
            changeNoChooseTotalPrice();
        }
    })
}

function loadCoupons() {
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
            $("#choose-coupon-select").append("<option value='0'>不使用优惠券</option>");
            $("#no-choose-coupon-select").append("<option value='0'>不使用优惠券</option>");

            if (data !== null && data.length !== 0) {

                for (var i = 0; i < data.length; i++) {
                    var value = data[i].coupon.split("元")[0];
                    $("#choose-coupon-select").append("<option value='" + value + "'>"
                        + data[i].coupon + "</option>");
                    $("#no-choose-coupon-select").append("<option value='" + value + "'>"
                        + data[i].coupon + "</option>");
                }
            }

        }
    })
}

// 计算总价
function changeChooseTotalPrice() {
    var email = sessionStorage.getItem('userId');

    $.ajax({
        type: "GET",
        url: "/user/discount",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "email": email
        },
        dataType: "json",
        success: function (data) {
            var discount = data;
            var seatPrice = $("#seat-select").val().split("-")[1];
            var totalPrice = $("#choose-member-order-num").val() * seatPrice;
            var finalPrice = totalPrice * discount - $("#choose-coupon-select").val();

            $("#choose-total-price").text("原价: " + totalPrice.toFixed(2) + "元");
            $("#choose-final-price").text("优惠后总计: " + finalPrice.toFixed(2) + "元");
        }
    })
}

function changeNoChooseTotalPrice() {

    var email = sessionStorage.getItem('userId');

    $.ajax({
        type: "GET",
        url: "/user/discount",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "email": email
        },
        dataType: "json",
        success: function (data) {
            var discount = data;
            var totalPrice = $("#no-choose-member-order-num").val() * highPrice;
            var finalPrice = totalPrice * discount - $("#no-choose-coupon-select").val();

            $("#no-choose-total-price").text("原价: " + totalPrice.toFixed(2) + "元");
            $("#no-choose-final-price").text("优惠后总计: " + finalPrice.toFixed(2) + "元");
        }
    })
}
