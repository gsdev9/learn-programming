// 日付に整形する
function dateFormat(d) {
    let date = new Date(d);
    date.toString();
    let year = date.getFullYear();
    let month = date.getMonth() + 1;
    let day = date.getDate();
    if(month.toString().length === 1) { month = "0" + month}
    if(day.toString().length === 1) { day = "0" + day}
    let formattedDate = year + "-" + month + "-" + day;
    return formattedDate;
}

// 時間に整形する
function timeFormat(t) {
    let hour = t[0];
    let time = t[1];
    if(t[0].toString().length === 1) { hour = "0" + t[0]}
    if(t[1].toString().length === 1) { time = "0" + t[1]}
    let formattedTime = hour + ":" + time;
    return formattedTime;
}

$(function() {

    /**
     * キーワード検索
     */
    $("#input").on("keydown", function (e) {
        // エンターキーが押下された時
        if(typeof e.keyCode === "undefined" || e.keyCode === 13) {
            const val = $('#input').val();
            const list = $('#ticket-list');
            if(val !== "") {
                $.ajax({
                    type: "GET",
                    url: "/top/search/input",
                    data: {input: val}
                }).done(function (data) {
                    list.empty();
                    let result = "";

                    const getData = data["tickets"];

                    if (data.length === 0) {
                        result = "<p>検索に該当するチケットはありませんでした。</p>";
                    } else {
                        $.each(getData, function (i, e) {
                            result += '';

                            result += '<li class="container__content__ticket" id="' + e['ticketId'] + '">'
                                + '<a href="/ticket/' + e['ticketId'] + '">'
                                + '<div class="container__content__ticket-thumbnail-area">'
                                + '<img class="container__content__ticket-thumbnail" src="">'
                                + '</div>'
                                + '<div class="container__content__ticket-top">'
                                + '<div class="container__content__ticket-left">'
                                + '<p class="container__content__ticket-avatar-img" style="background-image: url(' + e['user']['thumbnailPath'] + ')">'
                                + '<p class="container__content__ticket-nick-name">' + e['user']['nickName'] + '</p>'
                                + '</div>'
                                + '<div class="container__content__ticket-right">'
                                + '<div class="container__content__ticket-price">' + '¥' + e['price'] + '</div>'
                                + '</div>'
                                + '</div>'
                                + '<div class="container__content__ticket-middle">'
                                + '<h3 class="container__content__ticket-title">' + e['title'] + '</h3>'
                                + '<p class="container__content__ticket-date">'
                                + dateFormat(e['date']) + ' ' + timeFormat(e['startAt']) + '~' + timeFormat(e['endAt'])
                                + '</p>'
                                + '</div>'
                                + '<div class="container__content__ticket-bottom">'
                                + '<ul class="container__content__ticket-label-list">';
                            if(e['ticketLabel']['c'] === true) { result += '<li class="container__content__ticket-label">C</li>' }
                            if(e['ticketLabel']['cPlusPlus'] === true) { result += '<li class="container__content__ticket-label">C++</li>' }
                            if(e['ticketLabel']['cSharp'] === true) { result += '<li class="container__content__ticket-label">C#</li>' }
                            if(e['ticketLabel']['java'] === true) { result += '<li class="container__content__ticket-label">Java</li>' }
                            if(e['ticketLabel']['javaScript'] === true) { result += '<li class="container__content__ticket-label">Javascript</li>' }
                            if(e['ticketLabel']['php'] === true) { result += '<li class="container__content__ticket-label">PHP</li>' }
                            if(e['ticketLabel']['ruby'] === true) { result += '<li class="container__content__ticket-label">Ruby</li>' }
                            if(e['ticketLabel']['python'] === true) { result += '<li class="container__content__ticket-label">Python</li>' }
                            if(e['ticketLabel']['perl'] === true) { result += '<li class="container__content__ticket-label">Perl</li>' }
                            if(e['ticketLabel']['r'] === true) { result += '<li class="container__content__ticket-label">R</li>' }
                            if(e['ticketLabel']['go'] === true) { result += '<li class="container__content__ticket-label">Go</li>' }
                            if(e['ticketLabel']['scala'] === true) { result += '<li class="container__content__ticket-label">Scala</li>' }
                            if(e['ticketLabel']['objectiveC'] === true) { result += '<li class="container__content__ticket-label">Objective-C</li>' }
                            if(e['ticketLabel']['swift'] === true) { result += '<li class="container__content__ticket-label">Swift</li>' }
                            if(e['ticketLabel']['kotlin'] === true) { result += '<li class="container__content__ticket-label">Kotlin</li>' }
                            if(e['ticketLabel']['scratch'] === true) { result += '<li class="container__content__ticket-label">Scratch</li>' }
                            if(e['ticketLabel']['blockly'] === true) { result += '<li class="container__content__ticket-label">Blockly</li>' }
                            if(e['ticketLabel']['sqlLang'] === true) { result += '<li class="container__content__ticket-label">SQL</li>' }
                            result += '</ul>'
                                + '</div>'
                                + '</a>';
                        });
                    }
                    // 検索結果を出力
                    list.append(result);
                }).fail(function () {
                    list.empty();
                    list.append('<p>エラーが発生しました。もう一度入力してください。</p>');
                });
            }
        }
    });

    /**
     * カテゴリー検索
     */
    $('.container__sidebar__nav__category__list').on("click", function () {
        const self = $(this);
        const id = self.attr("id");
        const list = $('#ticket-list');
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
                    result += '<li class="container__content__ticket" id="' + e['ticketId'] + '">'
                        + '<a href="/ticket/' + e['ticketId'] + '">'
                        + '<div class="container__content__ticket-thumbnail-area">'
                        + '<img class="container__content__ticket-thumbnail" src="">'
                        + '</div>'
                        + '<div class="container__content__ticket-top">'
                        + '<div class="container__content__ticket-left">'
                        + '<p class="container__content__ticket-avatar-img" style="background-image: url(' + e['user']['thumbnailPath'] + ')">'
                        + '<p class="container__content__ticket-nick-name">' + e['user']['nickName'] + '</p>'
                        + '</div>'
                        + '<div class="container__content__ticket-right">'
                        + '<div class="container__content__ticket-price">' + '¥' + e['price'] + '</div>'
                        + '</div>'
                        + '</div>'
                        + '<div class="container__content__ticket-middle">'
                        + '<h3 class="container__content__ticket-title">' + e['title'] + '</h3>'
                        + '<p class="container__content__ticket-date">'
                        + dateFormat(e['date']) + ' ' + timeFormat(e['startAt']) + '~' + timeFormat(e['endAt'])
                        + '</p>'
                        + '</div>'
                        + '<div class="container__content__ticket-bottom">'
                        + '<ul class="container__content__ticket-label-list">';
                    if(e['ticketLabel']['c'] === true) { result += '<li class="container__content__ticket-label">C</li>' }
                    if(e['ticketLabel']['cPlusPlus'] === true) { result += '<li class="container__content__ticket-label">C++</li>' }
                    if(e['ticketLabel']['cSharp'] === true) { result += '<li class="container__content__ticket-label">C#</li>' }
                    if(e['ticketLabel']['java'] === true) { result += '<li class="container__content__ticket-label">Java</li>' }
                    if(e['ticketLabel']['javaScript'] === true) { result += '<li class="container__content__ticket-label">Javascript</li>' }
                    if(e['ticketLabel']['php'] === true) { result += '<li class="container__content__ticket-label">PHP</li>' }
                    if(e['ticketLabel']['ruby'] === true) { result += '<li class="container__content__ticket-label">Ruby</li>' }
                    if(e['ticketLabel']['python'] === true) { result += '<li class="container__content__ticket-label">Python</li>' }
                    if(e['ticketLabel']['perl'] === true) { result += '<li class="container__content__ticket-label">Perl</li>' }
                    if(e['ticketLabel']['r'] === true) { result += '<li class="container__content__ticket-label">R</li>' }
                    if(e['ticketLabel']['go'] === true) { result += '<li class="container__content__ticket-label">Go</li>' }
                    if(e['ticketLabel']['scala'] === true) { result += '<li class="container__content__ticket-label">Scala</li>' }
                    if(e['ticketLabel']['objectiveC'] === true) { result += '<li class="container__content__ticket-label">Objective-C</li>' }
                    if(e['ticketLabel']['swift'] === true) { result += '<li class="container__content__ticket-label">Swift</li>' }
                    if(e['ticketLabel']['kotlin'] === true) { result += '<li class="container__content__ticket-label">Kotlin</li>' }
                    if(e['ticketLabel']['scratch'] === true) { result += '<li class="container__content__ticket-label">Scratch</li>' }
                    if(e['ticketLabel']['blockly'] === true) { result += '<li class="container__content__ticket-label">Blockly</li>' }
                    if(e['ticketLabel']['sqlLang'] === true) { result += '<li class="container__content__ticket-label">SQL</li>' }
                    result += '</ul>'
                        + '</div>'
                        + '</a>';
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