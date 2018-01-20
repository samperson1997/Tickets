window.onload = init;

var password = "";
var account = 0;

tip = document.getElementById('reg-tip');
seatTip = document.getElementById('reg-seat-tip');

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
            $("#reg-username").attr("value", data.name);
            $("#reg-location").attr("value", data.location);
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

angular.module("mainapp", [])
    .controller("VenueController", function ($scope) {
        $scope.inputEmail = "";
        $scope.inputLocation = "";

        //注册
        $scope.venueInfoUpdate = function () {
            if (checkFirst() && checkSeatFirst()) {
                updateInfo_ajax(sessionStorage.getItem("venueId"), $scope.inputEmail, $("#reg-location").val(), account, password);
            } else {
                if (!checkFirst()) {
                    tip.innerHTML = "请把信息填写完整";
                }
                if (!checkSeatFirst()) {
                    seatTip.innerHTML = "请把信息填写完整, 或删除多余文本框";
                }
            }
        };

        function checkFirst() {
            return $scope.inputEmail !== null && $scope.inputEmail !== "" &&
                $("#reg-location").val() !== null && $("#reg-location").val() !== "";
        }

        function checkSeatFirst() {
            var isValid = true;
            for (var i = 1; i < 10; i++) {
                if ($("#inputSeatName" + i) !== null) {
                    if ($("#inputSeatName" + i).val() === "" || $("#inputSeatNum" + i).val() === "") {
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
