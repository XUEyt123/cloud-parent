package org.example.cloud.comment.controller;


import org.example.cloud.comment.utils.BadWordsUtil;
import org.example.cloud.comment.utils.BaiduContentCensor;
import org.example.cloudentity.domain.R;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("comment")
public class ContentController {

    /**
     * 使用百度接口 审核内容
     *
     * @param msg 味精
     * @return {@link R}
     */
    @GetMapping("censor")
    public R censor(String msg) {
        boolean pass = BaiduContentCensor.getInstance().censorText(msg);
        if (pass) {
            return R.ok("审核通过");
        }
        return R.ok("内容审核不通过");
    }

    /**
     * 使用自定义的敏感词审核工具
     *
     * @param msg 味精
     * @return {@link R}
     */
    @GetMapping("censor2")
    public R censor2(String msg) {
        List<String> list = BadWordsUtil.searchBadWords(msg);
        if (CollectionUtils.isEmpty(list)) {
            return R.ok("内容审核通过");
        }
        return R.fail(list);
    }

}
