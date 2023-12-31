package ru.mihalych.randombot.bot.components.enumBot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.mihalych.randombot.bot.components.Param;

import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public enum Command implements Function<Param, Object> {

    COMMAND_START(Icon.ICON_START.getKodIcon() + " старт бота") {
        @Override
        public Object apply(Param param) {
            return Command.getSendMessage(param.getChatId(), param.getMessageId(), param.getTxt());
        }
    },
    COMMAND_HELP(Icon.ICON_HELP.getKodIcon() + " помощь") {
        @Override
        public Object apply(Param param) {
            return Command.getSendMessage(param.getChatId(), param.getMessageId(), param.getTxt());
        }
    },
    COMMAND_CUBE(Icon.ICON_CUBE.getKodIcon() + " бросок кубика [1 - 6]") {
        @Override
        public Object apply(Param param) {
            return Command.getSendDice(param.getChatId(), param.getMessageId(), Icon.ICON_CUBE.getKodIcon());
        }
    },
    COMMAND_DART(Icon.ICON_DART.getKodIcon() + " дартс [1 - 6]") {
        @Override
        public Object apply(Param param) {
            return Command.getSendDice(param.getChatId(), param.getMessageId(), Icon.ICON_DART.getKodIcon());
        }
    },
    COMMAND_BOWLING(Icon.ICON_BOWLING.getKodIcon() + " боулинг [1 - 6]") {
        @Override
        public Object apply(Param param) {
            return Command.getSendDice(param.getChatId(), param.getMessageId(), Icon.ICON_BOWLING.getKodIcon());
        }
    },
    COMMAND_SOCCER(Icon.ICON_SOCCER.getKodIcon() + " футбол [1 - 5]") {
        @Override
        public Object apply(Param param) {
            return Command.getSendDice(param.getChatId(), param.getMessageId(), Icon.ICON_SOCCER.getKodIcon());
        }
    },
    COMMAND_BASKETBALL(Icon.ICON_BASKETBALL.getKodIcon() + " баскетбол [1 - 5]") {
        @Override
        public Object apply(Param param) {
            return Command.getSendDice(param.getChatId(), param.getMessageId(), Icon.ICON_BASKETBALL.getKodIcon());
        }
    },
    COMMAND_SLOT(Icon.ICON_SLOT.getKodIcon() + " слот [1 - 64]") {
        @Override
        public Object apply(Param param) {
            return Command.getSendDice(param.getChatId(), param.getMessageId(), Icon.ICON_SLOT.getKodIcon());
        }
    },
    COMMAND_FRACTIONAL("дробь 0-1") {
        @Override
        public Object apply(Param param) {
            return Command.getSendMessage(param.getChatId(), param.getMessageId(), param.getTxt());
        }
    },
    COMMAND_50_50(Icon.ICON_50_50.getKodIcon() + " да или нет (50/50)") {
        @Override
        public Object apply(Param param) {
            return Command.getSendSticker(param.getChatId(), param.getMessageId(), param.getTxt());
        }
    },
    COMMAND_INTEGER("целое с границами") {
        @Override
        public Object apply(Param param) {
            return Command.getSendMessage(param.getChatId(), param.getMessageId(), param.getTxt());
        }
    };

    private final String txtCommand;

    public static Command getCommand(String txtCommand) {
        for (Command value : values()) {
            if (value.name().equals(txtCommand)) {
                return value;
            }
        }
        return null;
    }

    public static SendDice getSendDice(long chatId, int messageId, String txt) {
        SendDice sendDice = new SendDice();
        sendDice.setEmoji(txt);
        sendDice.setChatId(chatId);
        if (messageId > 0) {
            sendDice.setReplyToMessageId(messageId);
        }
        return sendDice;
    }

    public static SendMessage getSendMessage(long chatId, int messageId, String txt) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(txt);
        sendMessage.setChatId(chatId);
        if (messageId > 0) {
            sendMessage.setReplyToMessageId(messageId);
        }
        return sendMessage;
    }

    public static SendSticker getSendSticker(long chatId, int messageId, String txt) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setSticker(new InputFile(txt));
        sendSticker.setChatId(chatId);
        if (messageId > 0) {
            sendSticker.setReplyToMessageId(messageId);
        }
        return sendSticker;
    }
}
