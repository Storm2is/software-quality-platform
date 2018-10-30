/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    $('.loginBtn').on('click', function () {
        var username = $(this).attr("value");
        var password = $(this).attr("value");
        var data = {
            username: username,
            password: password
        }
        $.post('/login', data).done(function (data) {
            window.location.href = "/code/upload"
        });
        ;
    });
});