window.onload = init;

var password = "";
var account = 0;

tip = document.getElementById('info-tip');
seatTip = document.getElementById('info-seat-tip');

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
        error: function (request, status, err) {
            if (status === "timeout") {
                load.abort();
            }
        }
    })
}

angular.module("venueapp", [])
    .controller("VenueController", function ($scope) {
        $scope.inputEmail = "";
        $scope.inputLocation = "";

        $scope.venueInfoUpdate = function () {

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
        };

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
                    $scope.$apply(function () {
                        if (data.result) {
                            for (var i = 1; i < 10; i++) {
                                if ($("#inputSeatName" + i) !== null) {
                                    updateSeatInfo_ajax($scope.inputEmail, $("#inputSeatName" + i).val(), $("#inputSeatNum" + i).val());
                                }
                            }

                            $scope.inputEmail = "";
                            $scope.inputLocation = "";
                            alert("请等待信息审批后登录");
                            window.location.href = "/index.html";

                        } else {
                            $scope.inputEmail = "";
                            $scope.inputLocation = "";
                            tip.innerHTML = data.message;
                        }
                    });
                }
            });
        }
    });
