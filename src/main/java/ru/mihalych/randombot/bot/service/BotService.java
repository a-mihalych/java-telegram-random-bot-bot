package ru.mihalych.randombot.bot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.mihalych.randombot.bot.components.*;
import ru.mihalych.randombot.bot.components.enumBot.Button;
import ru.mihalych.randombot.bot.components.enumBot.Command;
import ru.mihalych.randombot.bot.components.enumBot.Icon;
import ru.mihalych.randombot.bot.components.enumBot.Sticker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class BotService {

    private final int PAUSE = 5000;
    private final String SYMBOL_COMMAND = "/";
    private final String TXT_RESPONSE = "Боту не удалось распознать отправленное: ";

    public List<Object> parserUpdate(Update update, long chatId) {
        int messageId = 0;
        String txtResponse = TXT_RESPONSE + update;
        if (update.hasMessage()) {
            Message message = update.getMessage();
            chatId = message.getChatId();
            messageId = message.getMessageId();
            if (message.hasText()) {
                return parserTxt(message.getText(), chatId, messageId);
            }
        }
        return List.of(Command.getSendMessage(chatId, messageId, txtResponse));
    }

    public void pause() {
        try {
            Thread.sleep(PAUSE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String parseNumber(String txtNumber) {
        char[] arrayNumber = txtNumber.toCharArray();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < arrayNumber.length; i++) {
            if ((arrayNumber[i] != '.') && (arrayNumber[i] != '-')) {
                result.append(Icon.getKodIconForNumber(String.valueOf(arrayNumber[i])));
            } else {
                if (arrayNumber[i] == '.') {
                    result.append(" . ");
                } else {
                    result.append("▬");
                }
            }
        }
        return result.toString();
    }

    private List<Object> parserTxt(String txt, long chatId, int messageId) {
        List<Object> result = new ArrayList<>();
        String txtTrim = txt.trim();
        String txtCommand;
        if (txtTrim.startsWith(SYMBOL_COMMAND)) {
            txtCommand = txtTrim;
        } else {
            txtCommand = Button.getTxtCommand(txtTrim);
        }
        CommandAndText commandAndText = parseCommandAndText(txtCommand);
        Command command = commandAndText.getCommand();
        String paramTxt = null;
        if (command != null) {
            switch (command) {
                case COMMAND_50_50 -> paramTxt = get50x50();
                case COMMAND_START -> {
                    result.add(Command.getSendMessage(chatId, messageId, Icon.ICON_START.getKodIcon()));
                    messageId = 0;
                    paramTxt = BotCommands.START_TEXT;
                }
                case COMMAND_HELP -> {
                    result.add(Command.getSendSticker(chatId, messageId, Sticker.STICKER_SOS.getStickerId()));
                    messageId = 0;
                    paramTxt = BotCommands.HELP_TEXT;
                }
                case COMMAND_FRACTIONAL -> paramTxt = getFractional();
                case COMMAND_INTEGER -> {
                    String text = commandAndText.getTxt();
                    int begin = 0;
                    int end = 100;
                    if (text != null) {
                        String[] textInt = text.split(" ");
                        begin = getNumber(textInt[0], 0);
                        if (textInt.length > 1) {
                            end = getNumber(textInt[1], 100);
                        }
                        int max = Math.max(begin, end);
                        int min = Math.min(begin, end);
                        begin = min;
                        end = max;
                    }
                    result.add(Command.getSendMessage(chatId, messageId,
                            parseNumber(String.valueOf(begin)) + "   ▬   " + parseNumber(String.valueOf(end))));
                    messageId = 0;
                    paramTxt = getInteger(begin, end);
                }
            }
            result.add(command.apply(new Param(chatId, messageId, paramTxt)));
        } else {
            result.add(Command.getSendMessage(chatId, messageId, TXT_RESPONSE + txt));
        }
        return result;
    }

    private int getNumber(String txtNumber, int defaultInt) {
        int result;
        try {
            result = Integer.parseInt(txtNumber);
        } catch (Exception e) {
            result = defaultInt;
        }
        return result;
    }

    private CommandAndText parseCommandAndText(String commandTxt) {
        Command command;
        String txt;
        if (commandTxt.contains(" ")) {
            int index = commandTxt.indexOf(" ");
            command = Command.getCommand("COMMAND_" + commandTxt.substring(1, index).toUpperCase()); // .valueOf("COMMAND_" + commandTxt.substring(1, index).toUpperCase());
            txt = commandTxt.substring(index + 1).trim();
        } else {
            command = Command.getCommand("COMMAND_" + commandTxt.substring(1).toUpperCase());  // .valueOf("COMMAND_" + commandTxt.substring(1).toUpperCase());
            txt = null;
        }
        return new CommandAndText(command, txt);
    }

    private String getFractional() {
        return parseNumber(String.valueOf(new Random().nextDouble()));
    }

    private String getInteger(int begin, int end) {
        return parseNumber(String.valueOf(new Random().nextInt(Math.abs(end - begin) + 1) + begin));
    }

    private String get50x50() {
        Random random = new Random();
        int int50_50 = random.nextInt(2);
        return int50_50 == 0 ? Sticker.STICKER_NO.getStickerId() : Sticker.STICKER_YES.getStickerId();
    }
}
