function loadVenues() {

    var load = $.ajax({
        type: "GET",
        url: "/manager/uncheckedVenues",
        contentType: "application/x-www-form-urlencoded",
        data: {},
        dataType: "json",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                var seat = "| ";
                for (var j = 0; j < data[i].seatList.length; j++) {
                    seat += ( data[i].seatList[j].seatName + " " + data[i].seatList[j].seatNum + "张 | ");
                }


                $("#venue").append("<div class=\"userinfo-div\">\n" +
                    "                <p class=\"item-title\">\n" +
                    "                    场馆识别码, 场馆名称, 场馆地点\n" +
                    "                </p>\n" +
                    "                <p>\n" +
                    "                    " + data[i].id + ", " + data[i].name + ", " + data[i].location +
                    "                </p>\n" +
                    "                <p class=\"item-title\">\n" +
                    "                    座位名称与数量\n" +
                    "                </p>\n" + seat +
                    "                <p>\n" +
                    "                    \n" +
                    "                </p>\n" +
                    "            </div><div class=\"userinfo-div\">    <button class=\"button-green\" onclick=\"checkVenue(" + data[i].id + ", 1)\">审批通过</button>\n" +
                    "                <button class=\"button-red\" onclick=\"checkVenue(" + data[i].id + ", -1)\">审批驳回</button>\n" +
                    "            </div>");
            }
        },
        error: function (request, status) {
            if (status === "timeout") {
                load.abort();
            }
        }
    })
}

function checkVenue(id, result) {

    $.ajax({
        type: "POST",
        url: "/manager/checkVenue",
        data: {
            "id": id,
            "result": result
        },
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        success: function (data) {
            console.log(data);

            if (data.result) {
                window.location.href = "/manager.html";
            }

        }
    });
}