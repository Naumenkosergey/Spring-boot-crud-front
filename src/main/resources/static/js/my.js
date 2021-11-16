$(document).ready(function () {
    $('.edit-button').on('click', function (event) {

        event.preventDefault();

        $('#user-edit').modal("show").find('.modal-dialog')
            .load($(this).attr('href'), function (response, status, xhr) {
            if (xhr.status === 404) {
                $(location).attr('href', '/main');
            }
            // $('#user-edit .modal-header h3').text('Edit User');
            // let submit = $('#user-edit .modal-footer .submit');
            // submit.text('Edit');
            // submit.addClass('btn-primary');
            // $('#user-edit #method').val("patch");
        });
    });


    $('.delete-button').on('click', function (event) {

        event.preventDefault();

        $('#user-delete').modal("show").find('.modal-dialog')
            .load($(this).attr('href'), function (response, status, xhr) {
            if (xhr.status === 404) {
                $(location).attr('href', '/main');
            }
        });

    });

});