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
                for (var i = 0; i < data.length; i++) {
                    var num = i + 1;
                    $("#seat-div").append("<div>\n" +
                        "<p class=\"item-title\" style=\"display: inline-block;\" id=\"seat-name" + num + "\">" +
                        data[i].seatName + "</p><p class=\"item-title\" style=\"display: inline-block;\">\n" +
                        "区(共计</p>" +
                        "<p class=\"item-title\" style=\"display: inline-block;\"id=\"seat-num" + num + "\">" +
                        data[i].seatNum + "</p>\n" +
                        " <p class=\"item-title\" style=\"display: inline-block;\">\n" +
                        "座) 票价</p></div>" +
                        "<input type=\"text\" name=\"seat-price\" class=\"input-box\" id=\"seat-price" + num + "\">");

                }
            }
            sessionStorage.setItem("planSeatNum", data.length);
        }
    })
}