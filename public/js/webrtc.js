let localStream = null;
let peer = null;
let existingCall = null;
var rt = jsRoutes.controllers.WebSocketController.socket();
var webSocket = null;

open();
$("[data-name='message']").keypress(press);

//****websocket***
//websocketの初期設定
function open() {
    if (webSocket == null) {
        // WebSocket の初期化
        webSocket = new WebSocket(rt.webSocketURL());
        // イベントハンドラの設定
        webSocket.onopen = onOpen;
        webSocket.onmessage = onMessage;
        webSocket.onclose = onClose;
        webSocket.onerror = onError;
    }
}

// 接続イベント
function onOpen(e) {
    chat("接続しました。");
}

// メッセージ受信イベント
function onMessage(e) {
    if (e && e.data) {
        chat(e.data);
    }
}

// エラーイベント
function onError(e) {
    //chat("エラーが発生しました。");
}

// 切断イベント
function onClose(e) {
    chat("切断しました。3秒後に再接続します。(" + e.code + ")");
    webSocket = null;
    setTimeout("open()", 3000);
}

// キー押下時
function press(event) {
    // キーがEnterか判定
    if (event && event.which == 13) {
        // メッセージ取得
        var message = $("[data-name='message']").val();
        // 存在チェック
        if (message && webSocket) {
            // メッセージ送信
            webSocket.send("" + message);
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


//***skyway***
//カメラ映像、音声出力取得
navigator.mediaDevices.getUserMedia({video: true, audio: true})
    .then(function (stream) {
        // Success
        $('#my-video').get(0).srcObject = stream;
        localStream = stream;
    }).catch(function (error) {
    // Error
    console.error('mediaDevice.getUserMedia() error:', error);
    return;
});

//peerオブジェクトの作成
peer = new Peer({
    key: '4efdbe41-8ba0-437c-98e3-7452ea318301',
    //ログレベル：incrementすると多くなる
    debug: 3
});

//peer接続開始
peer.on('open', function () {
    $('#my-id').text(peer.id);
    var json = {'peer': peer.id};
    console.log(json);
    $.ajax(jsRoutes.controllers.ChatController.peerIdSend(peer.id)).done(function (data) {
        alert("ok");
    }).fail(function (XMLHttpRequest, textStatus, errorThrown) {
        alert("error");
    })
});

//peer接続失敗
peer.on('error', function (err) {
    alert(err.message);
});

//peer接続終了
peer.on('disconnected', function () {
});

//発信処理
//相手のPeerID、自分自身のlocalStreamを引数にセットし発信
$('#make-call').submit(function (e) {
    e.preventDefault();
    const call = peer.call($('#callto-id').val(), localStream);
    setupCallEventHandlers(call);
});

//切断処理
$('#end-call').click(function () {
    existingCall.close();
});

//着信処理
peer.on('call', function (call) {
    call.answer(localStream);
    setupCallEventHandlers(call);
});

//着信時のイベントハンドラー
function setupCallEventHandlers(call) {

    //着信あと勝ち
    if (existingCall) {
        existingCall.close();
    }
    ;
    existingCall = call;

    //新規通話接続処理
    call.on('stream', function (stream) {
        addVideo(call, stream);
        setupEndCallUI();
        $('#their-id').text(call.remoteId);
    });

    //既存通話切断処理
    call.on('close', function () {
        removeVideo(call.remoteId);
        setupMakeCallUI();
    });

}

//videoの再生(出力)処理
function addVideo(call, stream) {
    $('#their-video').get(0).srcObject = stream;
}

//videoの削除処理
function removeVideo(peerId) {
    $('#their-video').get(0).srcObject = undefined;
}

//発信ボタンの表示切り替え
function setupMakeCallUI() {
    $('#make-call').show();
    $('#end-call').hide();
}

//終了ボタンの表示切り替え
function setupEndCallUI() {
    $('#make-call').hide();
    $('#end-call').show();
}