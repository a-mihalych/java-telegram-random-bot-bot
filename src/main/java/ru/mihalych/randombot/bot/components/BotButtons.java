package ru.mihalych.randombot.bot.components;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.mihalych.randombot.bot.components.enumBot.Button;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BotButtons {

    public static ReplyKeyboardMarkup getReplyKeyboardMarkup() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> lines = new ArrayList<>();
        KeyboardRow line1 = new KeyboardRow();
        KeyboardRow line2 = new KeyboardRow();
        KeyboardRow line3 = new KeyboardRow();
        line1.add(Button.BUTTON_CUBE.getTxtButton());
        line1.add(Button.BUTTON_DART.getTxtButton());
        line1.add(Button.BUTTON_BOWLING.getTxtButton());
        line2.add(Button.BUTTON_SOCCER.getTxtButton());
        line2.add(Button.BUTTON_BASKETBALL.getTxtButton());
        line2.add(Button.BUTTON_SLOT.getTxtButton());
        line3.add(Button.BUTTON_FRACTIONAL.getTxtButton());
        line3.add(Button.BUTTON_50_50.getTxtButton());
        line3.add(Button.BUTTON_INTEGER.getTxtButton());
        lines.add(line1);
        lines.add(line2);
        lines.add(line3);
        keyboardMarkup.setKeyboard(lines);
        keyboardMarkup.setSelective(false);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);
        return keyboardMarkup;
    }
}
