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

/**
 * @return {boolean}
 */
function compareDate(d1, d2) {

    return ((new Date(d1.replace(/-/g, "\/"))) >= (new Date(d2.replace(/-/g, "\/"))));
}

function loadVenuePlans() {
    var venueId = sessionStorage.getItem('venueId');

    var load = $.ajax({
        type: "GET",
        url: "/plans/venuePlan",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "venueId": venueId
        },
        dataType: "json",
        success: function (data) {

            for (var i = data.length - 1; i >= 0; i--) {
                var type = "";
                switch (data[i].type) {
                    case(1):
                        type = "音乐演唱";
                        break;
                    case(2):
                        type = "舞蹈表演";
                        break;
                    case(3):
                        type = "话剧戏剧";
                        break;
                    case(4):
                        type = "体育比赛";
                        break;
                    case(5):
                        type = "其他活动";
                        break;
                }

                var currentTime = new Date(+new Date() + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '');
                var currentTimePlusOneHour = new Date(+new Date() + 9 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '');

                var startTime = data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11);
                var endTime = data[i].startTime.substr(0, 10) + " " + data[i].endTime.substr(11);

                // 活动列表
                if (compareDate(currentTime, endTime)) {
                    $("#venue-plans-end").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                        + "</b><br><i class='fa fa-clock-o'></i> " + startTime + "-" + endTime.substr(11)
                        + "<br><i class='fa fa-bookmark'></i> " + type + "</p></div>");
                } else {
                    $("#venue-plans-not-end").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                        + "</b><br><i class='fa fa-clock-o'></i> " + startTime + "-" + endTime.substr(11)
                        + "<br><i class='fa fa-bookmark'></i> " + type + "</p></div>");
                }

                // 活动检票
                if (compareDate(currentTimePlusOneHour, startTime) && compareDate(endTime, currentTime)) {
                    $("#venue-check").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                        + "</b><br><i class='fa fa-clock-o'></i> " + startTime + "-" + endTime.substr(11)
                        + "<br><i class='fa fa-bookmark'></i> " + type
                        + "<br><i class='fa fa-ticket'></i>  <a href='/venue-check.html?planId="
                        + data[i].planId + "'>检票</a>" + "</p></div>"
                    )
                    ;
                }
            }
        }
    });
}

function loadPlanInfo() {
    $.ajax({
        type: "GET",
        url: "/plans/detailedPlan",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "planId": getParam("planId")
        },
        dataType: "json",
        success: function (data) {
            $("#venue-plan-introduction").text(data.introduction);
            $("#venue-plan-date").text(data.startTime.substr(0, 10));
            $("#venue-plan-start").text(data.startTime.substr(11));
            $("#venue-plan-end").text(data.endTime.substr(11));

            switch (data.type) {
                case (1):
                    $("#venue-plan-type").text("音乐演唱");
                    break;
                case (2):
                    $("#venue-plan-type").text("舞蹈表演");
                    break;
                case (3):
                    $("#venue-plan-type").text("话剧戏剧");
                    break;
                case (4):
                    $("#venue-plan-type").text("体育比赛");
                    break;
                case (5):
                    $("#venue-plan-type").text("其他活动");
                    break;
            }

            $("#venue-plan-location").text(data.name + ", " + data.location);

            for (var i = 0; i < data.seatPriceBeanList.length; i++) {
                $("#venue-plan-price").append("<p class=\"item-title\">" + data.seatPriceBeanList[i].seatName + "区(剩余"
                    + data.seatPriceBeanList[i].seatNum + "座) 票价</p>" + data.seatPriceBeanList[i].seatPrice + "元");
            }
        }
    })
}

function loadStatistics() {
    var venueId = sessionStorage.getItem('venueId');

    $.ajax({
        type: "GET",
        url: "/orders/venueStatistics",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "venueId": venueId
        },
        dataType: "json",
        success: function (data) {
            $("#plan-num").text(data.planNum);
            $("#end-plan-num").text(data.endPlanNum);
            $("#all-orders").text(data.allOrders);
            $("#cancel-orders").text(data.cancelOrders);
            $("#total-price").text(data.totalPrice.toFixed(2));
            $("#account").text(data.account.toFixed(2));
        }
    })
}