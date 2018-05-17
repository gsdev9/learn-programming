$(function() {
   'use strict';

   fixSidebar();

    /**
     * トップページのサイドバースクロール時固定
     */
   function fixSidebar() {
       let fix = $('#sidebar'), //固定したいコンテンツ
           main = $('#main'), //固定する要素を収める範囲
           sideTop = fix.offset().top,
           fixTop = fix.offset().top,
           mainTop = main.offset().top,
           w = $(window);

       let adjust = function () {
           fixTop = fix.css('position') === 'static' ? sideTop + fix.position().top : fixTop;
           let fixHeight = fix.outerHeight(true),
               mainHeight = main.outerHeight(),
               winTop = w.scrollTop();

           if (winTop + fixHeight > mainTop + mainHeight) {
               fix.removeClass('fixed');
           } else if (winTop >= fixTop) {
               fix.addClass('fixed');
           } else {
               fix.removeClass('fixed');
           }
       };

       w.on('scroll', adjust);
   }
});