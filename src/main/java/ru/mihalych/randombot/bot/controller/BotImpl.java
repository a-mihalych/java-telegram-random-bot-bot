package ru.mihalych.randombot.bot.controller;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.mihalych.randombot.bot.components.BotButtons;
import ru.mihalych.randombot.bot.components.BotCommands;
import ru.mihalych.randombot.bot.components.enumBot.Command;
import ru.mihalych.randombot.bot.config.BotConfig;
import ru.mihalych.randombot.bot.service.BotService;

import java.util.List;

@Service
@Slf4j
public class BotImpl extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final BotService botService;

    public BotImpl(BotConfig botConfig, BotService botService) {
        this.botConfig = botConfig;
        this.botService = botService;
        try {
            execute(new SetMyCommands(BotCommands.getFullListCommands(), new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("\n!!! class BotImpl. Конструктор, создание меню: {}", e.getMessage());
        }
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(getChatId());
        sendMessage.setText("Был запущен random-bot!");
        sendMessage.setReplyMarkup(BotButtons.getReplyKeyboardMarkup());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("\n!!! class BotImpl. Конструктор, отправка сообщения о запуске бота: {}", e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    public String getBotName() {
        return botConfig.getBotName();
    }

    private long getChatId() {
        return Long.parseLong(botConfig.getChatId());
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        log.info("\n+++ update: {}", update);
        send(botService.parserUpdate(update, getChatId()));
    }

    private void send(List<Object> sends) {
        for (Object send : sends) {
            try {
                if (send instanceof BotApiMethodMessage) {
                    if (send instanceof SendMessage) {
                        SendMessage sendMessage = (SendMessage) send;
                        if (BotCommands.START_TEXT.equals(sendMessage.getText())) {
                            execute(new SetMyCommands(BotCommands.getFullListCommands(),
                                    new BotCommandScopeDefault(), null));
                            ((SendMessage) send).setReplyMarkup(BotButtons.getReplyKeyboardMarkup());
                        }
                    }
                    Message message = execute((BotApiMethodMessage) send);
                    if (send instanceof SendDice) {
                        botService.pause();
                        execute(Command.getSendMessage(message.getChatId(), 0,
                                botService.parseNumber(String.valueOf(message.getDice().getValue()))));
                    }
                } else {
                    execute((SendSticker) send);
                }
            } catch (TelegramApiException e) {
                log.error("\n!!! class BotImpl. Метод send, send: {}\n!!! {}", send, e.getMessage());
            }
        }
    }
}
