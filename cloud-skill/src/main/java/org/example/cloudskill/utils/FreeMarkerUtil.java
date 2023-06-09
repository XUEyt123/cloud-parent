package org.example.cloudskill.utils;


import freemarker.template.Configuration;
import freemarker.template.Template;
import org.example.cloudcommon.config.SystemConfig;
import org.example.cloudskill.dto.SkillGoodsDetailDto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * FreeMarker工具类
 *
 * @author zed
 * @date 2023/03/22
 */
public class FreeMarkerUtil {

    /**
     * 生成秒杀商品静态页面
     */
    public static void createHtml(SkillGoodsDetailDto dto) {
        Writer out = null;
        // 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        try {
            // 第二步：设置模板文件所在的路径
            configuration.setDirectoryForTemplateLoading(new File(dto.getClass().getClassLoader().getResource("templ").getPath()));
            // 第三步：设置模板文件使用的字符集。一般就是utf-8.
            configuration.setDefaultEncoding("utf-8");
            // 第四步：加载一个模板，创建一个模板对象。
            Template template = configuration.getTemplate("goodsdetail.ftl");
            // 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
            Map dataModel = new HashMap<>();
            //向数据集中添加数据
            dataModel.put("dto", dto);
            // 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
            out = new FileWriter(new File(dto.getClass().getClassLoader().getResource("static").getPath(), SystemConfig.GOODS_DETAIL_PRE + dto.getSgid() + ".html"));
            // 第七步：调用模板对象的process方法输出文件。
            template.process(dataModel, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 第八步：关闭流。
            try {
                if (Objects.nonNull(out)) {
                    out.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
