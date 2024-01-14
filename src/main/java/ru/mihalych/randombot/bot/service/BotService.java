package ru.mihalych.randombot.bot.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.mihalych.randombot.bot.components.*;
import ru.mihalych.randombot.bot.components.enumBot.Button;
import ru.mihalych.randombot.bot.components.enumBot.Command;

import java.util.List;

@Service
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

    private List<Object> parserTxt(String txt, long chatId, int messageId) {
        String txtTrim = txt.trim();
        String txtCommand;
        if (txtTrim.startsWith(SYMBOL_COMMAND)) {
            txtCommand = txtTrim;
        } else {
            txtCommand = Button.getTxtCommand(txtTrim);
        }
        CommandAndText commandAndText = parseCommandAndText(txtCommand);
        Command command = commandAndText.getCommand();
        List<Object> result;
        if (command == null) {
            result = List.of(Command.getSendMessage(chatId, messageId, TXT_RESPONSE + txt));
        } else {
            result = command.apply(new Param(chatId, messageId, commandAndText.getTxt()));
        }
        return result;
    }

    private CommandAndText parseCommandAndText(String commandTxt) {
        Command command;
        String txt;
        if (commandTxt.contains(" ")) {
            int index = commandTxt.indexOf(" ");
            command = Command.getCommand("COMMAND_" + commandTxt.substring(1, index).toUpperCase());
            txt = commandTxt.substring(index + 1).trim();
        } else {
            command = Command.getCommand("COMMAND_" + commandTxt.substring(1).toUpperCase());
            txt = null;
        }
        return new CommandAndText(command, txt);
    }
}
