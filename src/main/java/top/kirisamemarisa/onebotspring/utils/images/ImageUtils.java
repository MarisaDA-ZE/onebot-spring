package top.kirisamemarisa.onebotspring.utils.images;

import top.kirisamemarisa.onebotspring.utils.images.entity.EmojiText;
import top.kirisamemarisa.onebotspring.utils.images.entity.Position;
import org.springframework.util.ObjectUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * @Author Marisa
 * @Description ImageUtils.描述
 * @Date 2024/1/29
 */
public class ImageUtils {

    private static final int maxLen = 18;  // 一行最多有多少个文字
    private static final float LINE_HEIGHT = 1.5f;  // 行高

    public static void main(String[] args) {
        // new ImgText("雾雨魔理沙DAZE", 0.95f, 0.3f, 0, 64, "#000000", false);
        // String input = "D:\\MRS_Files\\Image\\Marisa.jpg";
        String input = "D:\\MRS_Files\\Image\\background.png";
        String output = "C:\\Users\\Administrator\\Desktop\\output\\output.jpg";
        // boolean b = drawTextToImage(textList, input, output);
        List<String> list = List.of(
                "敢惹老子？那你算是捏到软柿子了。",
                "兄弟兄弟，让我捏捏你的软柿子！",
                "我超，你不要过来啊！"
        );
        System.out.println(list);
        List<EmojiText> textList = parseCenterTop(input, list);
        if (!ObjectUtils.isEmpty(textList)) {
            boolean b = drawTextImage(textList, input, output);
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
    public static List<EmojiText> parseCenterTop(String input, List<String> texts) {
        List<Position> positionList = positionCenterTop(input, texts);
        return parse(input, texts, positionList);
    }

    /**
     * 中心靠上预设
     *
     * @param input 输入
     * @param texts 文字列表
     * @return 结果
     */
    public static List<EmojiText> parseCenterCenter(String input, List<String> texts) {
        List<Position> positionList = positionCenterCenter(input, texts);
        return parse(input, texts, positionList);
    }

    /**
     * 创建预设对象
     *
     * @param input   图片
     * @param texts   文字
     * @param posList 位置
     * @return .
     */
    public static List<EmojiText> parse(String input, List<String> texts, List<Position> posList) {
        List<EmojiText> res = new ArrayList<>();
        int index = 0;
        int img_w = 0;
        try {
            BufferedImage image = ImageIO.read(new File(input));
            img_w = image.getWidth();       // 图宽
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String text : texts) {
            EmojiText imgText = new EmojiText();
            imgText.setText(text);
            Position pos = posList.get(index);
            imgText.setPosition(pos);
            imgText.setRotate(0);
            imgText.setFontSize(getFontSize(img_w));
            imgText.setColor("#000000");
            imgText.setFamily("Microsoft YaHei");
            res.add(imgText);
            index++;
        }
        return res;
    }

    /**
     * 计算文字的大小（单位是像素，而不是字号）
     *
     * @param img_w 图片宽度
     * @return 一个文字所占像素
     */
    private static int getFontSize(float img_w) {
        float res = img_w / maxLen; // 字宽
        return (int) res;
    }

    /**
     * 中心靠上预设
     *
     * @param input 图片
     * @param texts 文字列表
     * @return .
     */
    public static List<Position> positionCenterTop(String input, List<String> texts) {
        List<Position> res = new ArrayList<>();
        int[] imageSize = getImageSize(input);
        int img_w = imageSize[0];       // 图宽

        int font_w = getFontSize(img_w);            // 字宽、字高
        float lineHeight = (font_w * LINE_HEIGHT);  // 行高
        float offset = 0;
        for (String text : texts) {
            int fontCount = (text.trim()).length();     // 字数
            float text_w = font_w * fontCount;          // 行宽
            // System.out.println("字宽: " + font_w + ",行高: " + lineHeight + ",偏移: " + offset);
            float start_w = (img_w * 0.5f) - (text_w * 0.5f);
            float start_h = (lineHeight) + (offset);
            offset += lineHeight;
            // System.out.println("高度: " + start_h);
            res.add(new Position(start_w, start_h, text_w, lineHeight));
        }
        return res;

    }

    /**
     * 中心预设
     *
     * @param input 图片
     * @param texts 文字
     * @return .
     */
    public static List<Position> positionCenterCenter(String input, List<String> texts) {
        List<Position> res = new ArrayList<>();
        int[] imageSize = getImageSize(input);
        int img_w = imageSize[0];       // 图宽
        int img_h = imageSize[1];       // 图高

        int font_w = getFontSize(img_w);            // 字宽、字高
        float lineHeight = (font_w * LINE_HEIGHT);  // 行高
        float offset = (img_h * 0.5f) - (texts.size() * lineHeight * 0.5f);
        for (String text : texts) {
            int fontCount = (text.trim()).length();     // 字数
            float text_w = font_w * fontCount;          // 行宽
            // System.out.println("字宽: " + font_w + ",行高: " + lineHeight + ",偏移: " + offset);
            float start_w = (img_w * 0.5f) - (text_w * 0.5f);
            float start_h = (lineHeight) + (offset);
            offset += lineHeight;
            // System.out.println("高度: " + start_h);
            res.add(new Position(start_w, start_h, text_w, lineHeight));
        }
        return res;
    }

    /**
     * 中心预设
     *
     * @param input 图片
     * @param texts 文字
     * @return .
     */
    public static List<Position> positionCenterBottom(String input, List<String> texts) {
        List<Position> res = new ArrayList<>();
        int[] imageSize = getImageSize(input);
        int img_w = imageSize[0];       // 图宽
        int img_h = imageSize[1];       // 图高

        int font_w = getFontSize(img_w);            // 字宽、字高
        float lineHeight = (font_w * LINE_HEIGHT);  // 行高
        float offset = img_h - (texts.size() * lineHeight);
        for (String text : texts) {
            int fontCount = (text.trim()).length();     // 字数
            float text_w = font_w * fontCount;          // 行宽
            // System.out.println("字宽: " + font_w + ",行高: " + lineHeight + ",偏移: " + offset);
            float start_w = (img_w * 0.5f) - (text_w * 0.5f);
            float start_h = (lineHeight) + (offset);
            offset += lineHeight;
            // System.out.println("高度: " + start_h);
            res.add(new Position(start_w, start_h, text_w, lineHeight));
        }
        return res;

    }


    /**
     * 中心预设
     * TODO: 通过offsetX,Y来偏移文字的位置
     *
     * @param input 图片
     * @param texts 文字
     * @return .
     */
    public static List<Position> getPositions(String input, List<String> texts, float offsetX, float offsetY) {
        List<Position> res = new ArrayList<>();
        int[] imageSize = getImageSize(input);
        int img_w = imageSize[0];       // 图宽
        int img_h = imageSize[1];       // 图高

        int font_w = getFontSize(img_w);            // 字宽、字高
        float lineHeight = (font_w * LINE_HEIGHT);  // 行高
        for (String text : texts) {
            int fontCount = (text.trim()).length();     // 字数
            float text_w = font_w * fontCount;          // 行宽
            // System.out.println("字宽: " + font_w + ",行高: " + lineHeight + ",偏移: " + offset);
            float start_w = (img_w * 0.5f) - (text_w * 0.5f);
            float start_h = offsetY + lineHeight;
            offsetY += lineHeight;
            // System.out.println("高度: " + start_h);
            res.add(new Position(start_w, start_h, text_w, lineHeight));
        }
        return res;

    }

    /**
     * 获取图片的宽高
     *
     * @param input 图片地址
     * @return {宽，高}
     */
    private static int[] getImageSize(String input) {
        try {
            BufferedImage image = ImageIO.read(new File(input));
            int img_w = image.getWidth();       // 图宽
            int img_h = image.getHeight();       // 图宽
            return new int[]{img_w, img_h};
        } catch (Exception e) {
            e.printStackTrace();
            return new int[]{0, 0};
        }
    }

    /**
     * 生成图片(按百分比绘制)
     *
     * @param textList 文字列表
     * @param imgUrl   原图
     * @param output   保存到
     * @return 是否成功
     */
    public static boolean drawTextImageReg(List<EmojiText> textList, String imgUrl, String output) {
        try {
            BufferedImage image = ImageIO.read(new File(imgUrl));
            Graphics2D g = image.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (EmojiText e : textList) {
                int x, y;
                Position position = e.getPosition();
                x = (int) (image.getWidth() * position.getX());
                y = (int) (image.getHeight() * position.getY());
                position.setX(x);
                position.setY(y);
                e.setPosition(position);
            }
            return drawTextImage(textList, imgUrl, output);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 按像素坐标绘制文字到图片
     *
     * @param textList 文字列表
     * @param imgUrl   图片地址
     * @param output   输出到
     * @return .
     */
    public static boolean drawTextImage(List<EmojiText> textList, String imgUrl, String output) {
        try {
            BufferedImage image = ImageIO.read(new File(imgUrl));
            Graphics2D g = image.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (EmojiText e : textList) {
                Font font = new Font(e.getFamily(), Font.PLAIN, e.getFontSize());
                g.setFont(font);
                g.setColor(Color.decode(e.getColor()));
                Position p = e.getPosition();
                int x = (int) p.getX();
                int y = (int) p.getY();
                g.rotate(Math.toRadians(e.getRotate()), image.getWidth() * p.getX(), image.getHeight() * p.getY());
                g.drawString(e.getText(), x, y);
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
