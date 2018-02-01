function loadOrders() {
    var email = sessionStorage.getItem('userId');

    var load = $.ajax({
        type: "GET",
        url: "/orders/list",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "email": email
        },
        dataType: "json",
        success: function (data) {
            for (var i = 0; i < data.length; i++) {
                var type = "";
                switch (data[i].type) {
                    case(1):
                        type = "音乐演唱";
                        break;
                    case(2):
                        type = "舞蹈表演";
                        break;
                    case(3):
                        type = "话剧戏剧";
                        break;
                    case(4):
                        type = "体育比赛";
                        break;
                    case(5):
                        type = "其他活动";
                        break;
                }

                switch (data[i].state) {
                    case "未支付":
                        if (data[i].isSeatSelected === 1) {
                            $("#unpaid-order").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                                + "</b><br><i class='fa fa-clock-o'></i> "
                                + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                                + "<br><i class='fa fa-bookmark'></i> " + type
                                + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                                + "<br><i class='fa fa-ticket'></i> " + data[i].seatName + "区" + data[i].seatNum + "座"
                                + "<br><i class='fa fa-money'></i> " + data[i].price + "元, <a href='/pay.html?orderId="
                                + data[i].orderId + "'>去支付</a>" + "</p></div>");
                        } else {
                            $("#unpaid-order").append("<div class=\"userinfo-div\"><p><p><b>" + data[i].introduction
                                + "</b><br><i class='fa fa-clock-o'></i> "
                                + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                                + "<br><i class='fa fa-bookmark'></i> " + type
                                + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                                + "<br><i class='fa fa-ticket'></i> " + "未选座" + data[i].seatNum + "座"
                                + "<br><i class='fa fa-money'></i> " + data[i].price + "元, <a href='/pay.html?orderId="
                                + data[i].orderId + "'>去支付</a>" + "</p></div>");
                        }
                        break;

                    case "未开票":
                        $("#unassigned-seat-order").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                            + "</b><br><i class='fa fa-clock-o'></i> "
                            + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                            + "<br><i class='fa fa-bookmark'></i> " + type
                            + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                            + "<br><i class='fa fa-ticket'></i> " + data[i].seatName + "区" + data[i].seatNum + "个位置"
                            + "<br><i class='fa fa-money'></i> " + data[i].price + "元, <a href='/cancel.html?orderId="
                            + data[i].orderId + "'>取消订单</a>" + "</p></div>");
                        break;

                    case "未配票":

                        $("#unassigned-no-seat-order").append("<div class=\"userinfo-div\"><p><p><b>" + data[i].introduction
                            + "</b><br><i class='fa fa-clock-o'></i> "
                            + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                            + "<br><i class='fa fa-bookmark'></i> " + type
                            + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                            + "<br><i class='fa fa-ticket'></i> " + "未选座" + data[i].seatNum + "个位置"
                            + "<br><i class='fa fa-money'></i> " + data[i].price + "元, <a href='/cancel.html?orderId="
                            + data[i].orderId + "'>取消订单</a>" + "</p></div>");

                        break;

                    case "已开票" :
                        $("#unused-order").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                            + "</b><br><i class='fa fa-clock-o'></i> "
                            + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                            + "<br><i class='fa fa-bookmark'></i> " + type
                            + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                            + "<br><i class='fa fa-ticket'></i> " + data[i].seatName + "区" + data[i].seatAssigned + "座, 取票号: " + data[i].orderId
                            + "<br><i class='fa fa-money'></i> " + data[i].realPrice + "元, <a href='/cancel.html?orderId="
                            + data[i].orderId + "'>取消订单</a>" + "</p></div>");

                        break;

                    case "已配票":
                        var returnPrice = data[i].price - data[i].realPrice;
                        $("#unused-order").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                            + "</b><br><i class='fa fa-clock-o'></i> "
                            + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                            + "<br><i class='fa fa-bookmark'></i> " + type
                            + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                            + "<br><i class='fa fa-ticket'></i> " + data[i].seatName + "区" + data[i].seatAssigned + "座, 取票号: " + data[i].orderId
                            + "<br><i class='fa fa-money'></i> " + data[i].realPrice + "元, 预支付" + data[i].price
                            + "元, 已退款" + returnPrice + "元, <a href='/cancel.html?orderId="
                            + data[i].orderId + "'>取消订单</a>" + "</p></div>");

                        break;

                    case "已使用":
                        returnPrice = data[i].price - data[i].realPrice;

                        if (data[i].isSeatSelected === 1) {
                            $("#used-order").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                                + "</b><br><i class='fa fa-clock-o'></i> "
                                + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                                + "<br><i class='fa fa-bookmark'></i> " + type
                                + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                                + "<br><i class='fa fa-ticket'></i> " + data[i].seatName + "区" + data[i].seatAssigned + "座"
                                + "<br><i class='fa fa-money'></i> " + data[i].realPrice + "元</p></div>");
                        } else {
                            $("#used-order").append("<div class=\"userinfo-div\"><p><p><b>" + data[i].introduction
                                + "</b><br><i class='fa fa-clock-o'></i> "
                                + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                                + "<br><i class='fa fa-bookmark'></i> " + type
                                + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                                + "<br><i class='fa fa-ticket'></i> " + data[i].seatName + "区" + data[i].seatAssigned + "座"
                                + "<br><i class='fa fa-money'></i> " + data[i].realPrice + "元, 预支付" + data[i].price
                                + "元, 已退款" + returnPrice + "元</div>");
                        }
                        break;

                    case "已关闭":
                        if (data[i].realPrice === 0) {
                            $("#closed-order").append("<div class=\"userinfo-div\"><p><p><b>" + data[i].introduction
                                + "</b><br><i class='fa fa-clock-o'></i> "
                                + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                                + "<br><i class='fa fa-bookmark'></i> " + type
                                + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location + "</p></div>");
                        } else {
                            $("#closed-order").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                                + "</b><br><i class='fa fa-clock-o'></i> "
                                + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                                + "<br><i class='fa fa-bookmark'></i> " + type
                                + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                                + "<br><i class='fa fa-money'></i> " + data[i].realPrice + "元 (手续费)</p></div>");
                        }
                        break;

                    case "已过期":
                        if (data[i].realPrice !== 0) {
                            $("#out-order").append("<div class=\"userinfo-div\"><p><b>" + data[i].introduction
                                + "</b><br><i class='fa fa-clock-o'></i> "
                                + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                                + "<br><i class='fa fa-bookmark'></i> " + type
                                + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                                + "<br><i class='fa fa-money'></i> " + data[i].realPrice + "元</p></div>");
                        } else {
                            $("#out-order").append("<div class=\"userinfo-div\"><p><p><b>" + data[i].introduction
                                + "</b><br><i class='fa fa-clock-o'></i> "
                                + data[i].startTime.substr(0, 10) + " " + data[i].startTime.substr(11) + "-" + data[i].endTime.substr(11)
                                + "<br><i class='fa fa-bookmark'></i> " + type
                                + "<br><i class='fa fa-map-marker'></i> " + data[i].name + ", " + data[i].location
                                + "<br><i class='fa fa-money'></i> " + data[i].realPrice + "元</p></div>");
                        }
                        break;
                }
            }
        }
    })
}

function loadStatistics() {
    var email = sessionStorage.getItem('userId');

    $.ajax({
        type: "GET",
        url: "/orders/memberStatistics",
        contentType: "application/x-www-form-urlencoded",
        data: {
            "email": email
        },
        dataType: "json",
        success: function (data) {
            $("#all-orders").text(data.allOrders);
            $("#closed-orders").text(data.closedOrders);
            $("#cancel-orders").text(data.cancelOrders);
            $("#total-price").text(data.totalPrice);

            // 饼图

            var option = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                legend: {
                    orient: 'horizontal',
                    y: 'bottom',
                    data: ['音乐演唱', '舞蹈表演', '话剧戏剧', '体育比赛', '其他活动']
                },
                series: [
                    {
                        name: '活动类型',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        label: {
                            normal: {
                                show: false,
                                position: 'center'
                            },
                            emphasis: {
                                show: true,
                                textStyle: {
                                    fontSize: '20',
                                    fontWeight: 'bold'
                                }
                            }
                        },
                        labelLine: {
                            normal: {
                                show: false
                            }
                        },
                        data: [
                            {value: data.type1Order, name: '音乐演唱'},
                            {value: data.type2Order, name: '舞蹈表演'},
                            {value: data.type3Order, name: '话剧戏剧'},
                            {value: data.type4Order, name: '体育比赛'},
                            {value: data.type5Order, name: '其他活动'}
                        ]
                    }
                ]
            };

            var memberChart = echarts.init(document.getElementById('member-chart'));
            memberChart.setOption(option);
        }
    })
}