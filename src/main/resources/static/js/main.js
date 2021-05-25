$(function(){

    let initChat = function(){
        //load messages
        //load users
        alert('yes');
    };

    let authUser = function (){
        let name = prompt('Enter user name:');
        $.post('api/users', {'name': name}, function (response){
            if(response.result){
                initChat();
            } else {
                alert('Something goes wrong!');
            }
        });
    }

    let checkAuthStatus = function (){
        $.get('/api/auth',function (response){
            if(response.result){
                initChat();
            } else {
                authUser();
            }
        });
    };

});