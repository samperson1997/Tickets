window.onload = init;

var password = "";
var account = 0;

tip = document.getElementById('info-tip');
seatTip = document.getElementById('info-seat-tip');
passwordTip = document.getElementById('venue-password-tip');

function init() {
    tip.innerHTML = "";
    seatTip.innerHTML = "";
}

function loadVenueInfo() {
    var email = sessionStorage.getItem('venueId');

    var load = $.ajax({
        type: "GET",
        url: "/venue/info",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "id": email
        },
        dataType: "json",
        success: function (data) {
            $("#id").html(data.venueId);
            $("#info-venuename").attr("value", data.name);
            $("#info-location").attr("value", data.location);
            $("#account").html(data.account);
            password = data.password;
            account = data.account;
        },
        error: function (request, status) {
            if (status === "timeout") {
                load.abort();
            }
        }
    })
}


function venueInfoUpdate() {

    if (checkInfoFirst() && checkSeatFirst()) {
        updateInfo_ajax(sessionStorage.getItem("venueId"),
            $("#info-venuename").val(), $("#info-location").val(), account, password);
    } else {
        if (!checkInfoFirst()) {
            tip.innerHTML = "请把信息填写完整";
        }
        if (!checkSeatFirst()) {
            seatTip.innerHTML = "请把信息填写完整, 或删除多余文本框";
        }
    }
}

function checkInfoFirst() {
    return $("#info-venuename").val() !== null && $("#info-venuename").val() !== "" &&
        $("#info-location").val() !== null && $("#info-location").val() !== "";
}

function checkSeatFirst() {
    var isValid = true;
    for (var i = 1; i < 10; i++) {
        if ($("#inputSeatName" + i) !== null) {
            if ($("#inputSeatName" + i).val() === null || $("#inputSeatNum" + i).val() === "") {
                isValid = false;
                break;
            }
        }
    }
    return isValid;
}

function updateInfo_ajax(id, name, locations, account, password) {
    $.ajax({
        type: "POST",
        url: "/venue/edit",
        data: {
            "id": id,
            "venueId": id,
            "name": name,
            "location": locations,
            "account": account,
            "password": password
        },
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        success: function (data) {
            console.log(data);

            if (data.result) {

                var seatList = [];
                for (var i = 1; i < 10; i++) {
                    if ($("#inputSeatName" + i).val() !== undefined) {
                        var seatBean = {};
                        seatBean.seatName = $("#inputSeatName" + i).val();
                        seatBean.seatNum = $("#inputSeatNum" + i).val();
                        seatList.push(seatBean);
                    }
                }

                var venueSeatBean = {};
                venueSeatBean.name = $("#info-venuename").val();
                venueSeatBean.seatList = seatList;

                console.log(venueSeatBean);
                updateSeatInfo_ajax(venueSeatBean);

                alert("请等待信息审批后登录");
                window.location.href = "/index.html";

            } else {
                tip.innerHTML = data.message;
            }

        }
    });
}

angular.module("venueapp", [])
    .controller("VenueController", function ($scope) {

        $scope.venueChangePassword = function () {
            if ($("#venue-old-password").val() !== password) {
                passwordTip.innerHTML = "原密码错误";
            } else if ($("#venue-old-password").val() === $("#venue-new-password").val()) {
                passwordTip.innerHTML = "新旧密码相同";
            } else {
                passwordTip.innerHTML = "";
                venueChangePassword_ajax(sessionStorage.getItem("venueId"), $("#info-venuename").val(),
                    $("#info-location").val(), account, $("#venue-new-password").val());
            }
        };

        function venueChangePassword_ajax(id, name, locations, account, password) {

            $.ajax({
                type: "POST",
                url: "/venue/edit",
                data: {
                    "id": id,
                    "venueId": id,
                    "name": name,
                    "location": locations,
                    "account": account,
                    "password": password
                },
                contentType: "application/x-www-form-urlencoded",
                dataType: "json",
                success: function (data) {
                    $scope.$apply(function () {
                        if (data.result) {
                            alert("密码修改成功, 请重新登录");
                            window.location.href = "/venue-login.html";
                        }
                    });
                }
            });
        }
    });

function passwordIsSame() {
    if ($("#venue-new-password").val() !== $("#venue-new-password2").val()) {
        passwordTip.innerHTML = "两次输入密码不一致";
    } else {
        passwordTip.innerHTML = "";
    }
}