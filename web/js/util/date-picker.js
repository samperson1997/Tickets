$(document).ready(function () {

    $(function () {
        $("#date-picker").datepicker({
            // maxDate: 0,
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
    });
});
