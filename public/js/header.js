$(function () {
    // サブメニューの開閉
    $('#header-avatar').on('click', function() {
        $('#sub-menu').toggle();
    });

    // ボタンのマウスオーバーエフェクト
    $('.btn-effect')
        .on('mouseenter', function(e) {
            let parentOffset = $(this).offset(),
                relX = e.pageX - parentOffset.left,
                relY = e.pageY - parentOffset.top;
            $(this).find('span').css({top:relY, left:relX})
        })
        .on('mouseout', function(e) {
            let parentOffset = $(this).offset(),
                relX = e.pageX - parentOffset.left,
                relY = e.pageY - parentOffset.top;
            $(this).find('span').css({top:relY, left:relX})
        });
});