$(function(){

    var userName = 'User';

    let initChat = function(){
        loadUsers();
        loadMessages();
    };



    let loadUsers = function (){
        $.get('/api/users', function (response){
            let users = response.users;
            let usersList = $('.users-list');
            for(let i in users){
                let userItem = $('<div class="user-item">'+ users[i].name+'</div>');
                //userItem.text(users[i].name);
                usersList.append(userItem);
            }
        })
    }

    let loadMessages = function (){
        let messagesList = $('.messages-list');
        $.get('/api/messages', function(response){
            let messages = response.messages;
            for (let i in messages){
                let messageItem = $('<div class="message"><b>'+ messages[i].time +"&nbsp;" +messages[i].name+': </b> ' + messages[i].text +'</div>');
                messagesList.append(messageItem);
            }

        });
    }

    let authUser = function (){
        let name = prompt('Enter user name:');
        userName = name;
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
                userName = response.name;
                initChat();
            } else {
                authUser();
            }
        });
    };

    checkAuthStatus();
    $('.send-message').on('click', function (){
       let message = $('.message-text').val();
       let messagesList = $('.messages-list');
       $.post('/api/messages',{'text':message}, function (response){
           if(response.result){
               let messageItem = $('<div class="message"><b>'+ response.time +"&nbsp;" +userName+': </b> ' + message +'</div>');
               messagesList.append(messageItem);
               $('.message-text').val('');
           } else {
               alert('Something is going wrong!');
           };
       });
    });
});