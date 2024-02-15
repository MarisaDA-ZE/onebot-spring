package top.kirisamemarisa.onebotspring.utils.images;

import top.kirisamemarisa.onebotspring.utils.images.entity.ImgText;
import top.kirisamemarisa.onebotspring.utils.images.entity.Position;
import org.springframework.util.ObjectUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * @Author Marisa
 * @Description ImageUtils.描述
 * @Date 2024/1/29
 */
public class ImageUtils {

    private static final float LINE_HEIGHT = 1.5f;  // 行高

    public static void main(String[] args) {
        // new ImgText("雾雨魔理沙DAZE", 0.95f, 0.3f, 0, 64, "#000000", false);
        String input = "D:\\MRS_Files\\Image\\Marisa.jpg";
        String output = "C:\\Users\\Administrator\\Desktop\\output\\output.jpg";
        // boolean b = drawTextToImage(textList, input, output);
        List<String> list = List.of(
                "敢惹老子，那你算是捏到软柿子了"
        );
        System.out.println(list);
//         List<ImgText> textList = presetCC(input, "表情包文字");
        List<ImgText> textList = presetCB(input, list);
        if (!ObjectUtils.isEmpty(textList)) {
            boolean b = drawTextImagePx(textList, input, output);
            System.out.println(b);
        }
    }

    /**
     * 中心靠上预设
     *
     * @param input 输入
     * @param texts 文字列表
     * @return 结果
     */
    public static List<ImgText> presetCT(String input, List<String> texts) {
        List<Position> pos = centerPositionTop(input, texts);
        return presetTarget(input, texts, pos);
    }

    /**
     * 中心预设
     *
     * @param input 输入
     * @param text  文字
     * @return 结果
     */
    public static List<ImgText> presetCC(String input, String text) {
        Position pos = centerPosition(input, text);
        return presetTarget(input, Collections.singletonList(text), List.of(pos));
    }

    /**
     * 中心靠下预设
     *
     * @param input 输入
     * @param texts 文字列表
     * @return 结果
     */
    public static List<ImgText> presetCB(String input, List<String> texts) {
        List<Position> pos = centerPositionBottom(input, texts);
        return presetTarget(input, texts, pos);
    }

    /**
     * 创建预设对象
     *
     * @param input   图片
     * @param texts   文字
     * @param posList 位置
     * @return .
     */
    public static List<ImgText> presetTarget(String input, List<String> texts, List<Position> posList) {
        List<ImgText> res = new ArrayList<>();
        int index = 0;
        for (String text : texts) {
            ImgText imgText = new ImgText();
            imgText.setText(text);
            Position pos = posList.get(index);
            imgText.setTop(pos.getTop());
            imgText.setLeft(pos.getLeft());
            imgText.setRotate(0);
            imgText.setFontSize(getFontSize(input, text.length()));
            imgText.setColor("#000000");
            imgText.setVertical(false);
            res.add(imgText);
            index++;
        }
        return res;
    }

    /**
     * 根据图片计算字体大小
     *
     * @param input  图片
     * @param length 字符数量
     * @return 字体大小（所占的像素而非字号）
     */
    private static int getFontSize(String input, int length) {
        try {
            BufferedImage image = ImageIO.read(new File(input));
            float img_h = image.getHeight();      // 图高
            float img_w = image.getWidth();       // 图宽
            float fontSize = img_h * 0.035f;
            for (int i = 0; i < 20; i++) {
                float f = 0.005f * i;
                fontSize = img_w * (0.1f - f);
                if ((fontSize * length) <= img_w) {
                    break;
                }
            }
            return (int) fontSize;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    /**
     * 中心靠上预设(多行)
     *
     * @param input 图片
     * @param texts 文字列表
     * @return .
     */
    public static List<Position> centerPositionTop(String input, List<String> texts) {
        try {
            BufferedImage image = ImageIO.read(new File(input));
            int img_w = image.getWidth();       // 图宽
            List<Position> res = new ArrayList<>();
            int offset = 0;
            for (String text : texts) {
                int fontCount = (text.trim()).length();     // 字数
                int font_w = getFontSize(input, fontCount); // 字宽
                float lineHeight = (font_w * LINE_HEIGHT);  // 行高
                // System.out.println("字宽: " + font_w + ",行高: " + lineHeight + ",偏移: " + offset);
                float start_w = (img_w * 0.5f) - (0.5f * (font_w * fontCount));
                float start_h = ( lineHeight) + (offset);
                offset += lineHeight;
                // System.out.println("高度: " + start_h);
                res.add(new Position(start_h, start_w));
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**
     * 中心预设
     *
     * @param input 图片
     * @param text  文字
     * @return .
     */
    public static Position centerPosition(String input, String text) {
        try {
            BufferedImage image = ImageIO.read(new File(input));
            int img_w = image.getWidth();       // 图宽
            int img_h = image.getHeight();      // 图高
            int font_w = getFontSize(input, text.length());   // 字宽
            int fontCount = (text.trim()).length();// 字数
            float start_w = (img_w * 0.5f) - (0.5f * (font_w * fontCount));
            float start_h = (0.5f * img_h) + (0.5f * font_w);
            return new Position(start_h, start_w);
        } catch (Exception e) {
            e.printStackTrace();
            return new Position(0, 0);
        }
    }

    /**
     * 中心靠下预设(多行)
     *
     * @param input 图片
     * @param texts 文字列表
     * @return .
     */
    public static List<Position> centerPositionBottom(String input, List<String> texts) {
        try {
            BufferedImage image = ImageIO.read(new File(input));
            int img_w = image.getWidth();       // 图宽
            int img_h = image.getHeight();      // 图高
            List<Position> res = new ArrayList<>();
            int offset = 0;
            for (String text : texts) {
                int fontCount = (text.trim()).length();     // 字数
                int font_w = getFontSize(input, fontCount); // 字宽
                float lineHeight = (font_w * LINE_HEIGHT);  // 行高
                // System.out.println("字宽: " + font_w + ",行高: " + lineHeight + ",偏移: " + offset);
                float start_w = (img_w * 0.5f) - (0.5f * (font_w * fontCount));
                float start_h = img_h - (0.5f * lineHeight) - (offset);
                offset += lineHeight;
                // System.out.println("高度: " + start_h);
                res.add(new Position(start_h, start_w));
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

//    /**
//     * 生成图片(按百分比绘制)
//     *
//     * @param textList 文字列表
//     * @param imgUrl   原图
//     * @param output   保存到
//     * @return 是否成功
//     */
//    public static boolean drawTextImageRe(List<ImgText> textList, String imgUrl, String output) {
//        try {
//            BufferedImage image = ImageIO.read(new File(imgUrl));
//            Graphics2D g = image.createGraphics();
//            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//
//            for (ImgText imgText : textList) {
//                Font font = new Font("Microsoft YaHei", Font.PLAIN, imgText.getFontSize());
//                g.setFont(font);
//                g.setColor(Color.decode(imgText.getColor()));
//                g.rotate(Math.toRadians(imgText.getRotate()), image.getWidth() * imgText.getLeft(), image.getHeight() * imgText.getTop());
//                if (imgText.isVertical()) {
//                    String s = imgText.getText();
//                    for (int i = 0; i < s.length(); i++) {
//                        String c = s.charAt(i) + "";
//                        FontMetrics fm = g.getFontMetrics();
//                        int x = (int) (image.getWidth() * imgText.getLeft());
//                        int y = (int) (image.getHeight() * imgText.getTop()) + fm.getAscent() * (i + 1);
//                        g.drawString(c, x, y);
//                    }
//                } else {
//                    g.drawString(imgText.getText(), image.getWidth() * imgText.getLeft(), image.getHeight() * imgText.getTop());
//                }
//            }
//
//            g.dispose();
//            ImageIO.write(image, "jpg", new File(output));
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    /**
     * 按像素值绘制
     *
     * @param textList .
     * @param imgUrl   .
     * @param output   .
     * @return .
     */
    public static boolean drawTextImagePx(List<ImgText> textList, String imgUrl, String output) {
        try {
            BufferedImage image = ImageIO.read(new File(imgUrl));
            Graphics2D g = image.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for (ImgText imgText : textList) {
                Font font = new Font("Microsoft YaHei", Font.PLAIN, imgText.getFontSize());
                g.setFont(font);
                g.setColor(Color.decode(imgText.getColor()));
                g.rotate(Math.toRadians(imgText.getRotate()), image.getWidth() * imgText.getLeft(), image.getHeight() * imgText.getTop());
                g.drawString(imgText.getText(), imgText.getLeft(), imgText.getTop());
            }
            g.dispose();
            ImageIO.write(image, "jpg", new File(output));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
