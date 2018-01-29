function charge() {
    $.ajax({
        type: "GET",
        url: "/managers/charge",
        data: {},
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        success: function (data) {
            if (data.result) {
                alert("场馆支付结算成功!");
                window.location.href = "/manager.html";
            }

        }
    });
}