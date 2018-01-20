count = 1;

function addInputBox() {
    count++;
    $("#venue-seat").append("<div class=\"userinfo-div\" id=\"seat-div" + count + "\">\n" +
        "\n" +
        "                    <p class=\"item-title\">\n" +
        "                        座位名称\n" +
        "                    </p>\n" +
        "                    <input type=\"text\" name=\"seat-name\" class=\"form-seatname form-control input-box\"\n" +
        "                           id=\"inputSeatName" + count + "\">\n" +
        "                    <p class=\"item-title\">\n" +
        "                        座位数量\n" +
        "                    </p>\n" +
        "                    <input type=\"text\" name=\"seat-num\" class=\"form-seatnum form-control input-box\"\n" +
        "                           id=\"inputSeatNum" + count + "\">\n" +
        "                    <button class=\"button-red\" onclick=\"deleteInputBox()\" id=\"delete-button" + count + "\">删除</button>\n" +
        "                </div>");
}

function deleteInputBox() {
    var current = event.target.id.substr(13);
    $("#seat-div" + current).remove();
}

function updateSeatInfo_ajax(venueName, seatName, seatNum) {

    $.ajax({
        type: "GET",
        url: "/venue/updateSeat",
        data: {
            "name": venueName,
            "seatName": seatName,
            "seatNum": seatNum
        },
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        success: function (data) {
            console.log(data);
        }
    });
}

function loadSeats() {
    var id = sessionStorage.getItem('venueId');
    $.ajax({
        type: "GET",
        url: "/venue/seat",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "id": id
        },
        dataType: "json",
        success: function (data) {
            if (data !== null && data.length !== 0) {
                $("#inputSeatName1").attr("value", data[0].seatName);
                $("#inputSeatNum1").attr("value", data[0].seatNum);

                if (data.length > 1) {
                    for (var i = 1; i < data.length; i++) {
                        var num = i + 1;
                        $("#venue-seat").append("<div class=\"userinfo-div\" id=\"seat-div" + num + "\">\n" +
                            "\n" +
                            "                    <p class=\"item-title\">\n" +
                            "                        座位名称\n" +
                            "                    </p>\n" +
                            "                    <input type=\"text\" name=\"seat-name\" class=\"form-seatname form-control input-box\"\n" +
                            "                           id=\"inputSeatName" + num + "\">\n" +
                            "                    <p class=\"item-title\">\n" +
                            "                        座位数量\n" +
                            "                    </p>\n" +
                            "                    <input type=\"text\" name=\"seat-num\" class=\"form-seatnum form-control input-box\"\n" +
                            "                           id=\"inputSeatNum" + num + "\">\n" +
                            "                    <button class=\"button-red\" onclick=\"deleteInputBox()\" id=\"delete-button" + num + "\">删除</button>\n" +
                            "                </div>");
                        $("#inputSeatName" + num).attr("value", data[i].seatName);
                        $("#inputSeatNum" + num).attr("value", data[i].seatNum);
                    }
                }
                count = data.length;
            }
        }
    })
}