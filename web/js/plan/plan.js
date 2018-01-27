// 企业发布计划
function postPlan() {

    if (checkVenuePlanFirst()) {
        var id = sessionStorage.getItem('venueId');
        var startTime = $("#date-picker").val() + " " + $("#start-time-input").val();
        var endTime = $("#date-picker").val() + " " + $("#end-time-input").val();
        var type = $("#type-select").val().substr(0, 1);
        var introduction = $("#introduction-input").val();

        var seatList = [];
        for (var i = 1; i <= sessionStorage.getItem("planSeatNum"); i++) {
            var seatBean = {};
            seatBean.seatName = $("#seat-name" + i).text();
            seatBean.seatNum = $("#seat-num" + i).text();
            seatBean.seatPrice = $("#seat-price" + i).val();
            seatList.push(seatBean);
        }

        var planSeatBean = {};
        planSeatBean.venueId = id;
        planSeatBean.startTime = startTime;
        planSeatBean.endTime = endTime;
        planSeatBean.type = type;
        planSeatBean.introduction = introduction;
        planSeatBean.seatPriceBeanList = seatList;

        console.log(planSeatBean);

        $.ajax({
            type: "POST",
            url: "/plans/postPlan",
            contentType: "application/json",
            data: JSON.stringify(planSeatBean),
            dataType: "json",
            success: function (data) {
                alert("活动发布成功! ");
                window.location.href = "venue-plan.html";
            }
        })
    } else {
        $("#venue-plan-tip").text("请将信息填写完整");
    }
}

function checkVenuePlanFirst() {
    var isValid = $("#date-picker").val() !== null && $("#date-picker").val() !== ""
        && $("#start-time-input").val() !== null && $("#start-time-input").val() !== ""
        && $("#end-time-input").val() !== null && $("#end-time-input").val() !== ""
        && $("#introduction-input").val() !== null && $("#introduction-input").val() !== "";

    for (var i = 1; i <= sessionStorage.getItem("planSeatNum"); i++) {
        if ($("#seat-price" + i).val() === null || $("#seat-price" + i).val() === "") {
            isValid = false;
        }
    }

    return isValid;
}

// 用户界面
function loadMusicPlan() {
    $.ajax({
        type: "GET",
        url: "/plans/memberPlan",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "type": 1
        },
        dataType: "json",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#concert").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                    + "</b><br><i class='fa fa-clock-o'></i> "
                    + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                    + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                    + "<br><i class='fa fa-ticket'></i> " + data[i].lowPrice + "元起, <a href='/plan.html?planId="
                    + data[i].planId + "'>立即预定</a>" + "</p></div>");
            }
        }
    })
}

function loadDancePlan() {
    $.ajax({
        type: "GET",
        url: "/plans/memberPlan",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "type": 2
        },
        dataType: "json",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#dance").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                    + "</b><br><i class='fa fa-clock-o'></i> "
                    + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                    + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                    + "<br><i class='fa fa-ticket'></i> " + data[i].lowPrice + "元起, <a href='/plan.html?planId="
                    + data[i].planId + "'>立即预定</a>" + "</p></div>");
            }
        }
    })
}

function loadPlayPlan() {
    $.ajax({
        type: "GET",
        url: "/plans/memberPlan",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "type": 3
        },
        dataType: "json",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#play").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                    + "</b><br><i class='fa fa-clock-o'></i> "
                    + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                    + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                    + "<br><i class='fa fa-ticket'></i> " + data[i].lowPrice + "元起, <a href='/plan.html?planId="
                    + data[i].planId + "'>立即预定</a>" + "</p></div>");
            }
        }
    })
}

function loadGamePlan() {
    $.ajax({
        type: "GET",
        url: "/plans/memberPlan",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "type": 4
        },
        dataType: "json",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#game").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                    + "</b><br><i class='fa fa-clock-o'></i> "
                    + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                    + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                    + "<br><i class='fa fa-ticket'></i> " + data[i].lowPrice + "元起, <a href='/plan.html?planId="
                    + data[i].planId + "'>立即预定</a>" + "</p></div>");
            }
        }
    })
}

function loadOtherPlan() {
    $.ajax({
        type: "GET",
        url: "/plans/memberPlan",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "type": 5
        },
        dataType: "json",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                $("#other").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                    + "</b><br><i class='fa fa-clock-o'></i> "
                    + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                    + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                    + "<br><i class='fa fa-ticket'></i> " + data[i].lowPrice + "元起, <a href='/plan.html?planId="
                    + data[i].planId + "'>立即预定</a>" + "</p></div>");
            }

        }
    })
}