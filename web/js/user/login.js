window.onload = init;

function init() {
    tip = document.getElementById('log-tip');
    tip.innerHTML = "";
}

function emailIsValid() {
    if (!/^[\w\.-_\+]+@[\w-]+(\.\w{2,4})+$/.test($("#log-username").val())) {
        tip.innerHTML = "用户邮箱格式不正确";
    } else {
        tip.innerHTML = "";
    }
}

angular.module("mainapp", [])
    .controller("UserController", function ($scope) {
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
            return $scope.inputUsername != null && $scope.inputUsername != "" &&
                $scope.inputPassword != null && $scope.inputPassword != "";
        }

        function login_ajax(username, password) {
            this.username = username;
            this.password = password;

            $.ajax({
                type: "POST",
                url: "/user/login",
                data: {
                    "email": this.username,
                    "password": this.password
                },
                contentType: "application/x-www-form-urlencoded",
                dataType: "json",
                success: function (data) {
                    $scope.$apply(function () {
                        var str = document.referrer;
                        if (data.msg === "success") {
                            if (str === null || str === "") {
                                // window.location.href = "/index.html";
                            } else {
                                // window.location.href = str;
                            }
                            alert("success!!!!!");
                            sessionStorage.setItem("log_state", "true");
                            sessionStorage.setItem("userId", data.email);
                        } else if (data.msg === "fail") {
                            $scope.inputPassword = "";
                            tip.innerHTML = "用户名不存在或密码错误";
                        }
                    });
                }
            });
        }
    });
