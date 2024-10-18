package com.my.articles.controller;

import com.my.articles.dto.LoginDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ApiTestController {
    @GetMapping("/api")
    public String api_from(){
        return "/test/api";
    }

    @PostMapping("/apiPostTest")
    public ResponseEntity<String> apiPostTest(@RequestBody LoginDTO loginDTO){
        // RequestBody > 정보를 요청하면 json 타입으로 받아서, LoginDTO 타입으로 변경해주는 역할을 해줌
        String info = loginDTO.getUserId() + loginDTO.getPassword();
        return ResponseEntity.status(HttpStatus.OK).body(info);
    }

    @RequestMapping(value = "/getResponse", method = RequestMethod.POST)
    @ResponseBody
    public LoginDTO getResponse(){
        return new LoginDTO("zzzmini", "1234");
    }

    @PostMapping("/apiPostArrayTest")
    @ResponseBody
    public Map<String, String> apiPostArrayTest(@RequestBody List<LoginDTO> dtos){
        Map<String, String> map = new HashMap<>();
//        for(LoginDTO dto : dtos){
//            map.put("userid", dto.getUserId());
//            map.put("password", dto.getPassword());
//        }

        map.put("userid", dtos.get(0).getUserId());
        map.put("password", dtos.get(0).getPassword());
        return map;
    }

    @DeleteMapping("/delete")
    // @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public String delete(){
        return "Delete Mapping";
    }
}
