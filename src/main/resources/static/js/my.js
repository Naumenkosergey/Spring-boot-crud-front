const USER_TABLE_BODY = $('#all-users-table-body');
const USER_MODAL_FORM = $('#user-modal');
const NEW_USER = $('#form-new-user');

$(document).ready(() => {
    getAllUsers();
});

$('#nav-user_form-link').click(() => {
    initialNewUser();
    addRolesSelectNewUser();
});

$('#user-area-tab').click(() => {
    $('#myTab').find('#user-area-tab').prop('aria-selected', true).addClass('btn-primary').removeClass('btn-light');
    $('#myTab').find('#admin-area-tab').prop('aria-selected', false).addClass('btn-light').removeClass('btn-primary');
});

$('#admin-area-tab').click(() => {
    $('#myTab').find('#user-area-tab').prop('aria-selected', false).addClass('btn-light').removeClass('btn-primary');
    $('#myTab').find('#admin-area-tab').prop('aria-selected', true).addClass('btn-primary').removeClass('btn-light');
});

function getAllUsers() {
    fetch('/api').then(function (response) {
        if (response.ok) {
            response.json().then(users => {
                USER_TABLE_BODY.empty();
                printUserTable(users);
            });
        }
    });
}


function addRolesSelectNewUser() {
    fetch("/api/roles").then(response => {
        NEW_USER.find('#new_roles').empty();
        response.json().then(roles => {
            NEW_USER.find('#roles')
            roles.forEach(role => {
                NEW_USER.find('#new_roles')
                    .append($('<option>')
                        // .prop('selected', user.authorities.filter(r => r.authority === role.authority).length)
                        .val(role.authority).text(role.authority));
            });
        });
    });
}

function addRolesSelect(user) {
    fetch("/api/roles").then(response => {
        USER_MODAL_FORM.find('#roles').empty();
        response.json().then(roles => {
            USER_MODAL_FORM.find('#roles')
            roles.forEach(role => {
                USER_MODAL_FORM.find('#roles')
                    .append($('<option>')
                        .prop('selected', user.authorities.filter(r => r.authority === role.authority).length)
                        .val(role.authority).text(role.authority));
            });
        });
    });
}

function addRolesCheckboxing(user) {
    fetch("/api/roles").then(response => {
        USER_MODAL_FORM.find('#roles-div').empty();
        USER_MODAL_FORM.find('#roles-div').append($('<label>').text("Role")).append($('<br>'));
        response.json().then(roles => {
            roles.forEach(role => {
                USER_MODAL_FORM.find('#roles-div')
                    .append($('<input>')
                        .attr('type', 'checkbox')
                        .attr('value', role.authority)
                        .attr('id', 'roles')
                        .prop('checked', user.authorities.filter(r => r.authority === role.authority).length)
                        .val(role.id).text(role.authority))
                    .append($('<label>').text(role.authority))
                    .append($('<br>'));
            });
        });
    });
}

function printUserTable(users) {
    let counter=0;
    users.forEach(user => {
        USER_TABLE_BODY
            .append($('<tr>').addClass(counter++ % 2 != 0 ? 'table-secondary' : 'table-light').attr('id', `row[${user.id}]`)
                .append($('<td>').text(user.id))
                .append($('<td>').text(user.firstName))
                .append($('<td>').text(user.lastName))
                .append($('<td>').text(user.age))
                .append($('<td>').text(user.email))
                .append($('<td>').text(user.authorities.map(role => role.authority).join("  ")))
                .append($('<td>').append($('<button class="btn btn-info">').click(() => {
                    modalEditOrDeleteForm(user.id);
                }).text('Edit')))
                .append($('<td>').append($('<button class="btn btn-danger">').click(() => {
                    modalEditOrDeleteForm(user.id, false);
                }).text('Delete')))
            );
    });
}


function showModalEditUser(id) {
    USER_MODAL_FORM.find('.modal-title').text('Update user');
    USER_MODAL_FORM.find('#firstName').prop('readonly', false);
    USER_MODAL_FORM.find('#lastName').prop('readonly', false);
    USER_MODAL_FORM.find('#age').prop('readonly', false);
    USER_MODAL_FORM.find('#email').prop('readonly', false);
    USER_MODAL_FORM.find('.password-group').show();
    USER_MODAL_FORM.find('#roles').prop('disabled', false);
    USER_MODAL_FORM.find('.btn-submit').removeClass('btn-danger').addClass('btn-primary').text("Edit");
    USER_MODAL_FORM.find('.btn-submit').click(() => editUser(id));

}

function showModalDeleteUser(id) {
    USER_MODAL_FORM.find('.modal-title').text('Delete user');
    USER_MODAL_FORM.find('#firstName').prop('readonly', true);
    USER_MODAL_FORM.find('#lastName').prop('readonly', true);
    USER_MODAL_FORM.find('#age').prop('readonly', true);
    USER_MODAL_FORM.find('#email').prop('readonly', true);
    USER_MODAL_FORM.find('.password-group').hide();
    USER_MODAL_FORM.find('#roles').prop('disabled', true);
    USER_MODAL_FORM.find('.btn-submit').removeClass('btn-primary').addClass('btn-danger').text("Delete");
    USER_MODAL_FORM.find('.btn-submit').click(() => deleteUser(id));
}

function modalEditOrDeleteForm(id, editMode = true) {

    fetch('/api/' + id, {method: 'GET'}).then(function (response) {
        response.json().then(function (user) {
            USER_MODAL_FORM.find('#id').val(id);
            USER_MODAL_FORM.find('#firstName').val(user.firstName);
            USER_MODAL_FORM.find('#lastName').val(user.lastName);
            USER_MODAL_FORM.find('#age').val(user.age);
            USER_MODAL_FORM.find('#email').val(user.email);
            USER_MODAL_FORM.find('#password').val('');
            addRolesSelect(user);
            if (editMode) {
                showModalEditUser(id);
            } else {
                showModalDeleteUser(id);
            }
            USER_MODAL_FORM.modal();
        });
    })
}

function setAuthorities(user) {
    let roles = user.authorities;
    let authorities = roles.map(function (role) {
        return {authority: role};
    });
    return authorities;
}

function initialNewUser() {
    NEW_USER.find('#new_firstName').val(''),
        NEW_USER.find('#new_lastName').val(''),
        NEW_USER.find('#new_age').val(0),
        NEW_USER.find('#new_email').val(''),
        NEW_USER.find('#new_password').val(''),
        NEW_USER.find('#new_roles').val('')
}

function createUser() {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');
    let user = {
        'firstName': NEW_USER.find('#new_firstName').val(),
        'lastName': NEW_USER.find('#new_lastName').val(),
        'age': NEW_USER.find('#new_age').val(),
        'email': NEW_USER.find('#new_email').val(),
        'password': NEW_USER.find('#new_password').val(),
        'authorities': NEW_USER.find('#new_roles').val()
    }

    user.authorities = setAuthorities(user);

    let options = {
        "method": "POST",
        "headers": headers,
        "body": JSON.stringify(user)
    };

    console.log(options.body);

    fetch("/api", options).then(() => {
        initialNewUser();
        getAllUsers();
    });
}

function deleteUser(id) {
    let options = {
        "method": "DELETE"
    }
    fetch(`/api/${id}`, options)
        .then(() => {
            USER_MODAL_FORM.modal('hide');
            USER_MODAL_FORM.find(`#row[${id}]`).remove();
            getAllUsers();
        });
}

function editUser(id) {
    let headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');
    let user = {
        'id': parseInt(USER_MODAL_FORM.find('#id').val()),
        'firstName': USER_MODAL_FORM.find('#firstName').val(),
        'lastName': USER_MODAL_FORM.find('#lastName').val(),
        'age': USER_MODAL_FORM.find('#age').val(),
        'email': USER_MODAL_FORM.find('#email').val(),
        'password': USER_MODAL_FORM.find('#password').val(),
        'authorities': USER_MODAL_FORM.find('#roles').val()
    };

    user.authorities = setAuthorities(user);
    let options = {
        "method": "PUT",
        "headers": headers,
        "body": JSON.stringify(user)
    };

    fetch(`/api/${id}`, options)
        .then(() => {
            USER_MODAL_FORM.modal('hide');
            getAllUsers();
        });
}

