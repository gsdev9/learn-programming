// 整形する関数
function dateFormat(d) {
    let date = new Date(d);
    date.toString();
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    let formattedDate = year + "年" + month + "月" + day + "日";
    return formattedDate;
}

$(function() {

    /**
     * キーワード検索
     */
    $("#submit").on("click", function () {
        const val = $('#input').val();
        const list = $('#ticket_list');
        $.ajax({
           type: "GET",
           url: "/top/search/input",
           data: {input: val}
        }).done(function (data) {
           list.empty();
           let result = "";

           const getData = data["tickets"];

           if(data.length === 0) {
               result = "<p>検索に該当するチケットはありませんでした。</p>";
           } else {
               $.each(getData, function (i, e) {
                   result += '<li id="' + e['ticketId'] + '">'
                           + '<a href="/ticket/' + e['ticketId'] + '">'
                           + '<img src="' + e['thumbnailPath'] + '">'
                           + '<h3>' + e['title'] + '</h3>'
                           + '<p>' + e['body'] + '</p>'
                           + '<p>' + dateFormat(e['date']) + '</p>'
                           + '<p>' + e['price'] + ' 円</p>'
                           + '</a></li>';
               });
           }
           // 検索結果を出力
           list.append(result);
        }).fail(function () {
            list.empty();
            list.append('<p>エラーが発生しました。もう一度入力してください。</p>');
        });
    });

    $('.category').on("click", function () {
        const self = $(this);
        const id = self.attr("id");
        const list = $('#ticket_list');
        $.ajax({
           type: "GET",
           url: "/top/search/category",
           data: {category: id}
        }).done(function (data) {
            list.empty();
            let result = "";

            const getData = data["tickets"];

            if(data.length === 0) {
                result = "<p>検索に該当するチケットはありませんでした。</p>";
            } else {
                $.each(getData, function (i, e) {
                    result += '<li id="' + e['ticketId'] + '">'
                        + '<a href="/ticket/' + e['ticketId'] + '">'
                        + '<img src="' + e['thumbnailPath'] + '">'
                        + '<h3>' + e['title'] + '</h3>'
                        + '<p>' + e['body'] + '</p>'
                        + '<p>' + dateFormat(e['date']) + '</p>'
                        + '<p>' + e['price'] + ' 円</p>'
                        + '</a></li>';
                });
            }
            // 検索結果を出力
            list.append(result);
        }).fail(function () {
            list.empty();
            list.append('<p>エラーが発生しました。もう一度入力してください。</p>');
        });
    });
});