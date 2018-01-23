window.onload = init;
tip = document.getElementById('reg-tip');
seatTip = document.getElementById('reg-seat-tip');

function init() {
    tip.innerHTML = "";
    seatTip.innerHTML = "";
}

function passwordIsSame() {
    if ($("#reg-password").val() !== $("#reg-password2").val()) {
        tip.innerHTML = "两次输入密码不一致";
    } else {
        tip.innerHTML = "";
    }
}

angular.module("mainapp", [])
    .controller("VenueController", function ($scope) {
        $scope.inputEmail = "";
        $scope.inputPassword = "";
        $scope.inputPassword2 = "";
        $scope.inputLocation = "";

        //注册
        $scope.venueRegister = function () {
            if (checkFirst() && checkSeatFirst()) {
                register_ajax($scope.inputEmail, $scope.inputPassword, $scope.inputLocation);
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
                $scope.inputPassword !== null && $scope.inputPassword !== "" &&
                $scope.inputPassword2 !== null && $scope.inputPassword2 !== "" &&
                $scope.inputLocation !== null && $scope.inputLocation !== "";
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

        function register_ajax(email, password, locations) {
            this.email = email;
            this.password = password;
            this.locations = locations;

            $.ajax({
                type: "POST",
                url: "/venue/register",
                data: {
                    "id": this.email,
                    "password": this.password,
                    "location": this.locations
                },
                contentType: "application/x-www-form-urlencoded",
                dataType: "json",
                success: function (data) {

                    $scope.$apply(function () {
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
                            venueSeatBean.name = $scope.inputEmail;
                            venueSeatBean.seatList = seatList;

                            console.log(venueSeatBean);
                            updateSeatInfo_ajax(venueSeatBean);

                            $scope.inputEmail = "";
                            $scope.inputPassword = "";
                            $scope.inputPassword2 = "";
                            $scope.inputLocation = "";
                            alert("欢迎, 注册成功, 您的场馆识别码为" + data.message + ", 请等待信息审批后登录");
                            window.location.href = "/index.html";

                        } else {
                            $scope.inputEmail = "";
                            $scope.inputPassword = "";
                            $scope.inputPassword2 = "";
                            $scope.inputLocation = "";
                            tip.innerHTML = data.message;
                        }
                    });
                }
            });
        }
    });
