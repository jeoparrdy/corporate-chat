package org.jeoparrdy.corporatechat;

import org.jeoparrdy.corporatechat.model.Message;
import org.jeoparrdy.corporatechat.model.User;
import org.jeoparrdy.corporatechat.repos.MessageRepository;
import org.jeoparrdy.corporatechat.repos.UserRepository;
import org.jeoparrdy.corporatechat.response.AddMessageResponse;
import org.jeoparrdy.corporatechat.response.AuthResponse;
import org.jeoparrdy.corporatechat.response.MessageResponse;
import org.jeoparrdy.corporatechat.response.UsersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
public class ChatController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("HH:MM:SS dd.MM.yyyy");

    @GetMapping(path = "/api/auth")
    public AuthResponse auth(){
        AuthResponse response = new AuthResponse();
        String sessionId = getSessionId();
        User user = userRepository.getBySessionId(sessionId);
        response.setResult(user!=null);
        if(user!=null) {
            response.setName(user.getName());
        }
        return response;
    }

    private String getSessionId(){
        return RequestContextHolder.currentRequestAttributes().getSessionId();
    }

    @PostMapping(path="/api/users")
    public HashMap<String, Boolean> addUser(HttpServletRequest request){
        String name = request.getParameter("name");
        String sessionId = getSessionId();
        User user = new User();
        user.setName(name);
        user.setRegTime(new Date());
        user.setSessionId(sessionId);
        userRepository.save(user);
        HashMap<String,Boolean> response = new HashMap<>();
        response.put("result", true);
        return response;
    }
    @GetMapping(path = "/api/users")
    public HashMap<String, List> getUsers(){
        Iterable<User> users = userRepository.findAll();
        ArrayList<UsersResponse> usersList = new ArrayList<>();
        for (User user:users){
            UsersResponse userItem = new UsersResponse();
            userItem.setName(user.getName());
            usersList.add(userItem);
        }
        HashMap<String, List> response = new HashMap<>();
        response.put("users",usersList);
        return response;
    }

    @PostMapping(path="/api/messages")
    public AddMessageResponse addMessage(HttpServletRequest request){
        String text = request.getParameter("text");

        Date time = new Date();
        String sessionId = getSessionId();
        User user = userRepository.getBySessionId(sessionId);
        Message message = new Message();
        message.setTime(time);
        message.setUser(user);
        message.setMessage(text);
        messageRepository.save(message);

        AddMessageResponse response = new AddMessageResponse();
        response.setResult(true);
        response.setTime(formatter.format(time));
        return response;
    }
    @GetMapping(path = "/api/messages")
    public HashMap<String, List> getMessages(){

        ArrayList<MessageResponse> messagesList = new ArrayList<>();
        Iterable<Message> messages= messageRepository.findAll();

        for (Message message: messages){
            MessageResponse messageItem = new MessageResponse();
            messageItem.setName(message.getUser().getName());
            messageItem.setTime(formatter.format(message.getTime()));
            messageItem.setText(message.getMessage());
            messagesList.add(messageItem);
        }
        HashMap<String, List> response = new HashMap<>();
        response.put("messages",messagesList);
        return response;
    }


}
