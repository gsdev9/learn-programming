$(function() {

    $('#submit').on('click', function() {
        $.ajax({
            type: 'POST',
            url: '/ticket/create',
            data: {
                date: $('#date').val(),
                startAt: $('#startAt').val(),
                endAt: $('#endAt').val(),
                price: $('#price').val(),
                title: $('#title').val(),
                body: $('#body').val()
            }
        }).done(function(data) {
            console.log(data);
        }).fail(function(err) {
            $('#error').html(err.responseText);
        });
    });

});
