package com.seriouslylocal.app.controller;

import java.util.Optional;

import com.seriouslylocal.app.entity.PrefectureInfo;
import com.seriouslylocal.app.service.PrefectureInfoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/prefecture")
public class PrefectureController {
    
    @Autowired
    private PrefectureInfoService service;

    @GetMapping("/{pref}")
    public String get(@PathVariable("pref") String pref, Model model) {
        Optional<PrefectureInfo> op = service.get(pref);
        if (op.isPresent()) {
            model.addAttribute("prefectureInfo", op.get());
            return "prefecture";
        } else {
            return "forward:/404.html";
        }
    }

}
