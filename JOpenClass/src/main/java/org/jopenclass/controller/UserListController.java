package org.jopenclass.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.jopenclass.form.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserListController {
    private List<User> userList = new ArrayList<User>();

    @RequestMapping(value="/AddUser",method=RequestMethod.GET)
    public String showForm(){
        return "AddUser";
    }

    @RequestMapping(value="/AddUser",method=RequestMethod.POST)
    public @ResponseBody String addUser(@Valid @ModelAttribute(value="user") User user, BindingResult result ){
        String returnText;
        List<ObjectError> errors = result.getAllErrors();
        for (ObjectError objectError : errors) {
			System.out.println(objectError.getDefaultMessage());
		}
        if(!result.hasErrors()){
            userList.add(user);
            returnText = "User has been added to the list. Total number of users are " + userList.size();
        }else{
            returnText = "Sorry, an error has occur. User has not been added to list.";
        }
        return returnText;
    }

    @RequestMapping(value="/ShowUsers")
    public String showUsers(ModelMap model){
        model.addAttribute("Users", userList);
        return "ShowUsers";
    }
}
