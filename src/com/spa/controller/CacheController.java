package com.spa.controller;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("cache")
public class CacheController extends BaseController {


    @RequestMapping("toClearView")
    public String testMultiMenu(Model model) {
        model.addAttribute("cacheNames", getCacheNameList());
        return "cache/clear";
    }

    @RequestMapping("clear")
    public String clear(Model model, String[] cacheNames) {
        CacheManager cacheManager = CacheManager.getInstance();
        List<String> cacheNameList = getCacheNameList();
        for (String name : cacheNameList) {
            Cache cache = cacheManager.getCache(name);
            if (cache != null) {
                cache.removeAll();
            }
        }

        model.addAttribute("success", "Clear cache successfully.");
        model.addAttribute("cacheNames", cacheNameList);
        return "cache/clear";
    }


    private List<String> getCacheNameList() {
        List<String> cacheList = new ArrayList<>();
        CacheManager cacheManager = CacheManager.getInstance();
        for (String name : cacheManager.getCacheNames()) {
            if (name.startsWith("org.spa.model")) {
                cacheList.add(name);
            }
        }
        cacheList.sort(String::compareTo);
        return cacheList;
    }

}
