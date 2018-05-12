let localStream = null;
let peer = null;
let existingCall = null;
let screenStream = new MediaStream();
let yourPeer = null;
let call = null;
let dataConnection = null;
var POLLLING_INVERVAL_TIME_IN_MILLIS = 10000;


$("[data-name='message']").keypress(press);
polling();

// ***skyway***
//***video***
// カメラ映像、音声出力取得
navigator.mediaDevices.getUserMedia({video: true, audio: true})
    .then(function (cameraStream) {
        // Success
        $('#my-video').get(0).srcObject = cameraStream;
        localStream = cameraStream;
        //音声のトラックを取得後、streamに詰める
        cameraStream.getAudioTracks().forEach(track => {
            screenStream.addTrack(track.clone())
        });
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
    $.ajax(jsRoutes.controllers.ChatController.peerIdSend(peer.id, $("#roomId").val())).done(function (data) {
    }).fail(function (XMLHttpRequest, textStatus, errorThrown) {
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
    if (yourPeer == null || $('#callto-id').val() != null) {
        yourPeer = $('#callto-id').val();
    }
    e.preventDefault();
    call = peer.call(yourPeer, localStream);
    setupCallEventHandlers(call);
});

//切断処理
$('#end-call').click(function () {
    existingCall.close();
});

//切断処理
function endcall() {
    existingCall.close();
};

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


var screenshare = ScreenShare.create({debug: true});


// スクリーンシェアを開始

$("#tab").on("click", function () {
    //stream作成
    startScreenShare();
});



function startScreenShare() {
    if (screenshare.isScreenShareAvailable() == true) {

        screenshare.start({
            width: 1200,
            height: 800,
            frameRate: 2,
        })
            .then(function (videoStream) {
                //ビデオのトラック取得後、streamに詰める
                videoStream.getVideoTracks().forEach(track => {
                    screenStream.addTrack(track.clone())
                });
                $('#my-video').get(0).srcObject = screenStream;
                localStream = screenStream

                //通話の再構築
                if (existingCall) {
                    call = null;
                    call = peer.call(yourPeer, localStream);
                    setupCallEventHandlers(call);
                }
            })
            .catch(function (error) {
                // error callback
            });
    }
    else {
        alert('Screen Share cannot be used. Please install the Chrome extension.');
    }
}

// スクリーンシェアを終了
$('#stop-screen').click(function () {
    localStream.stop();
});


//***Chat***
// 発信側
function chatstart() {
    if ($('#callto-id').val() || dataConnection == null) {
        dataConnection = peer.connect($('#callto-id').val());
        dataConnection.on('data', function (data) {
            chat(data);
        });
    }
}

// 着信側
peer.on('connection', connection => {
    dataConnection = connection;
    dataConnection.on('data', function (data) {
        chat(data);
    });
});

// キー押下時
function press(event) {
    // キーがEnterか判定
    if (event && event.which == 13) {
        // メッセージ取得
        //TODO:ユーザー名を動的にする。
        var message = "userName > " + $("[data-name='message']").val();
        // 存在チェック
        if (message) {
            // メッセージ送信
            //TODO:サーバーサイドにチャットの内容を送信してDBに入れる
            dataConnection.send(message);
            chat(message)
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

//ポーリング
function polling() {
    if (!document.hidden) { // このページが表示されているときだけリクエストする
        getPeerId();
    }
    window.setTimeout(polling, POLLLING_INVERVAL_TIME_IN_MILLIS);
};

//相手のpeerId取得
function getPeerId() {
    $.ajax(jsRoutes.controllers.ChatController.peerIdGet($("#roomId").val())).done(function (data) {
        if (data.peerId != $("#callto-id").val()) {
            $("#callto-id").val(data.peerId);
            endcall();
            chatstart();
        }
    }).fail(function (XMLHttpRequest, textStatus, errorThrown) {
    })
}
