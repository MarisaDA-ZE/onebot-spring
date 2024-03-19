package top.kirisamemarisa.onebotspring.enums;

/**
 * @Author: MarisaDAZE
 * @Description: 表情符号的枚举
 * @Date: 2024/3/18
 */
public enum Emoji {

    // 喜
    HAPPY_1(".^◡^."),  // 开心
    HAPPY_2(" ヾ(^▽^*)))"),  // 开心
    HAPPY_3("(´･ᴗ･`)"),  // 开心
    HAPPY_4("(๑*◡*๑)"),  // 开心
    HAPPY_5("ଘ(੭ˊᵕˋ)੭ ੈ✩‧₊˚"),  // 开心
    HAPPY_6("✧ (＞ ＜)☆"),  // 开心
    HAPPY_7("( ͡° ͜ʖ ͡°)"),  // 开心
    HAPPY_8(" ʕ ᵔᴥᵔ ʔ"),  // 开心
    HAPPY_9("(✧◡✧)"),  // 开心

    // 怒
    ANGRY_1("(〃＞目＜)"),  // 生气
    ANGRY_2("o(￣ヘ￣o＃)"),  // 生气
    ANGRY_3("(；′⌒`)"),  // 生气
    ANGRY_4("┻━┻︵╰(‵□′)╯︵┻━┻"),  // 生气
    ANGRY_5("￣へ￣"),  // 生气
    ANGRY_6("（○｀ 3′○）"),  // 生气
    ANGRY_7("(ﾟДﾟ*)ﾉ"),  // 生气
    ANGRY_8("(* ￣︿￣)"),  // 生气
    ANGRY_9("(ー`′ー)"),  // 生气

    // 哀
    SORROW_1("（ノへ￣、）"),       // 悲伤
    SORROW_2("( -'`-; )"),       // 悲伤
    SORROW_3("テ_デ"),       // 悲伤
    SORROW_4("(。﹏。)"),       // 悲伤
    SORROW_5("(#｀-_ゝ-)"),       // 悲伤
    SORROW_6("(┬＿┬)↘"),       // 悲伤
    SORROW_7("(ノへ￣、)"),       // 悲伤
    SORROW_8("(ノへ￣、)"),       // 悲伤
    SORROW_9("(＞﹏＜)"),       // 悲伤

    // 惧
    FEAR_1("┌(。Д。)┐"),       // 害怕
    FEAR_2("o((⊙﹏⊙))o."),       // 害怕
    FEAR_3("━((*′д｀)爻(′д｀*))━!!!!"),       // 害怕
    FEAR_4("ヽ(*。>Д<)o゜"),       // 害怕
    FEAR_5("ヽ(￣︿￣　)—C<(/;◇;)/"),       // 害怕
    FEAR_6("||o(*°▽°*)o|Ю"),       // 害怕
    FEAR_7("(*Φ皿Φ*)"),       // 害怕
    FEAR_8("Σ( ° △ °|||)︴"),       // 害怕
    FEAR_9("(☄⊙ω⊙)☄"),       // 害怕

    // 爱
    LOVE_1("◕ฺ‿◕ฺ✿ฺ)"),       // 喜爱
    LOVE_2("つ﹏⊂"),       // 喜爱
    LOVE_3("（づ￣3￣）づ╭❤～"),       // 喜爱
    LOVE_4("(✿◡‿◡)"),       // 喜爱
    LOVE_5("o(*￣3￣)o"),       // 喜爱
    LOVE_6("(っ´Ι`)っ"),       // 喜爱
    LOVE_7("!(*￣(￣　*)"),       // 喜爱
    LOVE_8("（○｀ 3′○）"),       // 喜爱
    LOVE_9("( *￣▽￣)((≧︶≦*)"),       // 喜爱

    // 恶
    DISGUST_1("(　´_ゝ`)"),       // 厌恶
    DISGUST_2("φ(-ω-*)"),       // 厌恶
    DISGUST_3("(ﾟДﾟ*)ﾉ"),       // 厌恶
    DISGUST_4("（ ￣ー￣）"),       // 厌恶
    DISGUST_5("(－∀＝)"),       // 厌恶
    DISGUST_6("→_→"),       // 厌恶
    DISGUST_7("←_←"),       // 厌恶
    DISGUST_8("o(￣ヘ￣o＃)"),       // 厌恶
    DISGUST_9("( >ρ < ”)"),       // 厌恶

    // 欲
    DESIRE_1("Ψ(￣∀￣)Ψ"),       // 欲望
    DESIRE_2("$_$"),       // 欲望
    // DESIRE_3(""),       // 欲望
    // DESIRE_4(""),       // 欲望
    // DESIRE_5(""),       // 欲望
    // DESIRE_6(""),       // 欲望
    // DESIRE_7(""),       // 欲望
    // DESIRE_8(""),       // 欲望
    // DESIRE_9(""),       // 欲望

    // 其它
    OTHER_1("(´•ω•̥`)"),       // 其它


    UNKNOWN_1("（未知的表情）");  // 未知表情

    private final String emoji;

    Emoji(String emoji) {
        this.emoji = emoji;
    }

    /**
     * 获取对应的Emoji
     *
     * @return .
     */
    public String getEmoji() {
        return emoji;
    }

}
