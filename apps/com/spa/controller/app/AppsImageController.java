package com.spa.controller.app;

import com.spa.controller.BaseController;
import org.spa.utils.ImageUtil;
import org.spa.utils.Results;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("apps/image")
public class AppsImageController extends BaseController {

    @RequestMapping("list")
    @ResponseBody  public Results list(HttpServletRequest request) {
        Results results = Results.getInstance();
        List filterName = new ArrayList();
        filterName.add("jpg");
        filterName.add("png");
        return results.setCode(Results.CODE_SUCCESS).addMessage("successMsg", ImageUtil.getFileList(request.getSession().getServletContext().getRealPath("/"),filterName,"/resources/img/appsImg/"));
    }

}
