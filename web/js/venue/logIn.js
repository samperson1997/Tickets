window.onload = init;
tip = document.getElementById('log-tip');

function init() {
    tip.innerHTML = "";
}

angular.module("mainapp", [])
    .controller("VenueController", function ($scope) {
        $scope.inputUsername = "";
        $scope.inputPassword = "";
        //登录
        $scope.login = function () {
            if (checkFirst()) {
                tip.innerHTML = "";
                login_ajax($scope.inputUsername, $scope.inputPassword);
            } else {
                tip.innerHTML = "请把信息填写完整";
            }
        };

        function checkFirst() {
            return $scope.inputUsername !== null && $scope.inputUsername !== "" &&
                $scope.inputPassword !== null && $scope.inputPassword !== "";
        }

        function login_ajax(username, password) {
            this.username = username;
            this.password = password;

            $.ajax({
                type: "POST",
                url: "/venue/login",
                data: {
                    "id": this.username,
                    "password": this.password
                },
                contentType: "application/x-www-form-urlencoded",
                dataType: "json",
                success: function (data) {
                    $scope.$apply(function () {
                        if (data.result) {
                            window.location.href = "/venue.html";

                            sessionStorage.setItem("venue_log_state", "true");
                            sessionStorage.setItem("venueId", data.message);
                        } else {
                            $scope.inputPassword = "";
                            tip.innerHTML = data.message;
                        }
                    });
                }
            });
        }
    });
