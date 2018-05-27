$(function() {
   'use strict';

   fixSidebar();

   flashMotion();

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

    /**
     * フラッシュの動作
     */
   function flashMotion() {
       const flash =  $('.flash'),
             showItem = {top: "-58px"},
             hideItem = {top: "-158px"};
       flash.animate(showItem, 500);
       flash.delay(2000).animate(hideItem, 500);
   }
});