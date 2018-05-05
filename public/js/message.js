var POLLLING_INVERVAL_TIME_IN_MILLIS = 10000;


$("[data-name='message']").keypress(press);
polling()

//ポーリング
function polling() {
    if (!document.hidden) { // このページが表示されているときだけリクエストする
        get();
    }
    window.setTimeout(polling, POLLLING_INVERVAL_TIME_IN_MILLIS);
};

// キー押下時
function press(event) {
    // キーがEnterか判定
    if (event && event.which == 13) {
        // メッセージ取得
        //TODO:ユーザー名を動的にする。
        var message = $("[data-name='message']").val();
        // 存在チェック
        if (message) {
            // メッセージ送信
            //TODO:サーバーサイドにチャットの内容を送信してDBに入れる
            send(message);
            // メッセージ初期化
            $("[data-name='message']").val("");
        }
    }
}

// チャットに表示
function chat(message) {
    // 100件まで残す
    var chats = $("[data-name='chat']").find("div");
    while (chats.length >= 100) {
        chats = chats.last().remove();
    }
    // メッセージ表示
    var msgtag = $("<div>").text(message);
    $("[data-name='chat']").prepend(msgtag);
}


function send(message) {

    $.ajax(jsRoutes.controllers.PurchasedTicketController.sendMessage($("#purchasedTicketId").val(), message)).done(function (data) {
        console.log("ajax ok");
        chat(data);
    }).fail(function (XMLHttpRequest, textStatus, errorThrown) {
        console.log("ajax error");
    })
}

function get() {
    $.ajax(jsRoutes.controllers.PurchasedTicketController.getMessage($("#purchasedTicketId").val())).done(function (data) {
        console.log("ajax ok");
        console.log(data);
        let len = data.length;
        for (let i = 0; i < len; i++) {
            chat(data[i].message)
        }
    }).fail(function (XMLHttpRequest, textStatus, errorThrown) {
        console.log("ajax error");
    })
}