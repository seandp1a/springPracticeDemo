package com.example.demo.service;

import com.example.demo.pojo.AuthData;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class LoginService {
    public String redirect(Model model, AuthData authData){

        // 驗證非本次說明重點，故省略.
        System.out.println(authData);
        // 要喂進JSP之參數(可自行定義)
        // ex. model.addAttribute("name", "John") -> ${name} = "John"

        model.addAttribute("UUID", "123");

        return "home";
        // return "/WEB-INF/jsp/memberSetGet.jsp"; // 第 2 步驟已將 JSP 路徑前後綴詞處理掉
    }
}
