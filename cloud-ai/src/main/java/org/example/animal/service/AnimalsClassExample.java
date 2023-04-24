package org.example.animal.service;


import ai.djl.ModelException;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.translate.TranslateException;
import org.example.animal.utils.AnimalsClassification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class AnimalsClassExample {

    private static final Logger logger = LoggerFactory.getLogger(AnimalsClassExample.class);

    private AnimalsClassExample() {}

    public static void main(String[] args) throws IOException, ModelException, TranslateException {
//    Path imageFile = Paths.get("src/test/resources/tiger.jpeg");
//    Image image = ImageFactory.getInstance().fromFile(imageFile);
      Image image = ImageFactory.getInstance().fromUrl("https://img0.baidu.com/it/u=1858651154,4144960213&fm=253&fmt=auto&app=120&f=JPEG?w=1340&h=800");
      // 进行图片识别 返回的是预测结果
      Classifications classifications = AnimalsClassification.predict(image);
      // best 最可能是什么
      Classifications.Classification bestItem = classifications.best();
      System.out.println(bestItem.getClassName() + " : " + bestItem.getProbability());
      logger.debug("{}", classifications);
    }
}



