package ru.mihalych.randombot.bot.components.enumBot;

import com.vdurmont.emoji.EmojiParser;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Icon {

    ICON_0(":zero:"),
    ICON_1(":one:"),
    ICON_2(":two:"),
    ICON_3(":three:"),
    ICON_4(":four:"),
    ICON_5(":five:"),
    ICON_6(":six:"),
    ICON_7(":seven:"),
    ICON_8(":eight:"),
    ICON_9(":nine:"),
    ICON_START(":clapper:"),
    ICON_HELP(":sos:"),
    ICON_50_50(":yin_yang:"),
    ICON_CUBE(":game_die:"),
    ICON_DART(":dart:"),
    ICON_BOWLING(":bowling:"),
    ICON_SOCCER(":soccer:"),
    ICON_BASKETBALL(":basketball:"),
    ICON_SLOT(":slot_machine:"),
    ICON_OK(":like:"),
    ICON_NO(":dislike:"),
    ICON_1234(":1234:"),
    ICON_MINUS(":heavy_minus_sign:");

    private final String kodIcon;

    public static String getKodIconForNumber(String number) {
        switch (number) {
            case "0":
                return EmojiParser.parseToUnicode(ICON_0.kodIcon);
            case "1":
                return EmojiParser.parseToUnicode(ICON_1.kodIcon);
            case "2":
                return EmojiParser.parseToUnicode(ICON_2.kodIcon);
            case "3":
                return EmojiParser.parseToUnicode(ICON_3.kodIcon);
            case "4":
                return EmojiParser.parseToUnicode(ICON_4.kodIcon);
            case "5":
                return EmojiParser.parseToUnicode(ICON_5.kodIcon);
            case "6":
                return EmojiParser.parseToUnicode(ICON_6.kodIcon);
            case "7":
                return EmojiParser.parseToUnicode(ICON_7.kodIcon);
            case "8":
                return EmojiParser.parseToUnicode(ICON_8.kodIcon);
            case "9":
                return EmojiParser.parseToUnicode(ICON_9.kodIcon);
//            case "-":
//                return EmojiParser.parseToUnicode(ICON_MINUS.kodIcon);
        }
        return null;
    }

    public String getKodIcon() {
        return EmojiParser.parseToUnicode(kodIcon);
    }
}
