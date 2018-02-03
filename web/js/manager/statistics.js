function loadStatistics() {

    $.ajax({
        type: "GET",
        url: "/managers/managerStatistics",
        contentType: "application/x-www-form-urlencoded",
        data: {},
        dataType: "json",
        success: function (data) {
            $("#user-num").text(data.userNum);
            $("#venue-num").text(data.venueNum);
            $("#unchecked-venue-num").text(data.uncheckedVenueNum);
            $("#plan-num").text(data.planNum);
            $("#end-plan-num").text(data.endPlanNum);
            $("#all-orders").text(data.allOrders);
            $("#closed-orders").text(data.closedOrders);
            $("#cancel-orders").text(data.cancelOrders);
            $("#total-price").text(data.totalPrice.toFixed(2));
            $("#account").text(data.account.toFixed(2));

            var planOption = {
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
                            {value: data.typePlanNum[0], name: '音乐演唱'},
                            {value: data.typePlanNum[1], name: '舞蹈表演'},
                            {value: data.typePlanNum[2], name: '话剧戏剧'},
                            {value: data.typePlanNum[3], name: '体育比赛'},
                            {value: data.typePlanNum[4], name: '其他活动'}
                        ]
                    }
                ]
            };

            var planChart = echarts.init(document.getElementById('plan-chart'));
            planChart.setOption(planOption);

            var userOption = {
                tooltip: {
                    trigger: 'item',
                    formatter: "{a} <br/>{b}: {c} ({d}%)"
                },
                legend: {
                    orient: 'horizontal',
                    y: 'bottom',
                    data: ["大众会员", "青铜会员", "白银会员", "黄金会员", "鸣钻会员"]
                },
                series: [
                    {
                        name: '会员等级',
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
                            {value: data.levelUserNum[0], name: '大众会员'},
                            {value: data.levelUserNum[1], name: '青铜会员'},
                            {value: data.levelUserNum[2], name: '白银会员'},
                            {value: data.levelUserNum[3], name: '黄金会员'},
                            {value: data.levelUserNum[4], name: '鸣钻会员'}
                        ]
                    }
                ]
            };

            var userChart = echarts.init(document.getElementById('user-chart'));
            userChart.setOption(userOption);
        }
    })
}