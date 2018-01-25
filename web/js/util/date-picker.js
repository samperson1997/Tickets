$(document).ready(function () {

    $(function () {
        $("#date-picker").datepicker({
            minDate: 15,
            hideIfNoPrevNext: true,
            dayNamesMin: ['日', '一', '二', '三', '四', '五', '六'],
            changeMonth: true,
            changeYear: true,
            showOtherMonths: true,
            selectOtherMonths: true,
            // beforeShowDay: $.datepicker.noWeekends,
            yearRange: '2018:2019',
            showButtonPanel: true,
            dateFormat: 'yy-mm-dd',
            onClose: function (selectedDate) {
                // $("#end-filter").datepicker("option", "minDate", selectedDate);
            }
        });

        $("#start-time-input").timepicker({
            stepMinute: 10,
            onClose: function (selectedTime) {
                $("#end-time-input").timepicker("option", "minTime", selectedTime);
            }
        });

        $("#end-time-input").timepicker({
            stepMinute: 10,
            onClose: function (selectedTime) {
                $("#start-time-input").timepicker("option", "maxTime", selectedTime);
            }
        });
    });
});
