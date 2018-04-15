// dateTimePickerが機能しないため使っていない

(function($) {

    'use strict';

    /*
     * 日時入力
     */
    $('.form-control.datetime').datetimepicker({
       locale: 'ja',
       format: 'YYYY/MM/DD HH:mm:ss'
    });

    /*
     * 日付入力
     */
    $('.form-control.date').datetimepicker({
       locale: 'ja',
       format: 'YYYY/MM/DD'
    });

    /*
     * 時刻入力
     */
    $('.form-control.time').datetimepicker({
        locale: 'ja',
        format: 'HH:mm:ss'
    });

});