package ru.mihalych.randombot.bot.components.enumBot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendDice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import ru.mihalych.randombot.bot.components.BotCommands;
import ru.mihalych.randombot.bot.components.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

@RequiredArgsConstructor
@Getter
public enum Command implements Function<Param, List<Object>> {

    COMMAND_START(Icon.ICON_START.getKodIcon() + " старт бота") {
        @Override
        public List<Object> apply(Param param) {
            return List.of(Command.getSendMessage(param.getChatId(), param.getMessageId(), Icon.ICON_START.getKodIcon()),
                           Command.getSendMessage(param.getChatId(), 0, BotCommands.START_TEXT));
        }
    },
    COMMAND_HELP(Icon.ICON_HELP.getKodIcon() + " помощь") {
        @Override
        public List<Object> apply(Param param) {
            return List.of(Command.getSendSticker(param.getChatId(), param.getMessageId(), Sticker.STICKER_SOS.getStickerId()),
                           Command.getSendMessage(param.getChatId(), 0, BotCommands.HELP_TEXT));
        }
    },
    COMMAND_CUBE(Icon.ICON_CUBE.getKodIcon() + " бросок кубика [1 - 6]") {
        @Override
        public List<Object> apply(Param param) {
            return Command.getSendDices(param, Icon.ICON_CUBE.getKodIcon());
        }
    },
    COMMAND_DART(Icon.ICON_DART.getKodIcon() + " дартс [1 - 6]") {
        @Override
        public List<Object> apply(Param param) {
            return Command.getSendDices(param, Icon.ICON_DART.getKodIcon());
        }
    },
    COMMAND_BOWLING(Icon.ICON_BOWLING.getKodIcon() + " боулинг [1 - 6]") {
        @Override
        public List<Object> apply(Param param) {
            return Command.getSendDices(param, Icon.ICON_BOWLING.getKodIcon());
        }
    },
    COMMAND_SOCCER(Icon.ICON_SOCCER.getKodIcon() + " футбол [1 - 5]") {
        @Override
        public List<Object> apply(Param param) {
            return Command.getSendDices(param, Icon.ICON_SOCCER.getKodIcon());
        }
    },
    COMMAND_BASKETBALL(Icon.ICON_BASKETBALL.getKodIcon() + " баскетбол [1 - 5]") {
        @Override
        public List<Object> apply(Param param) {
            return Command.getSendDices(param, Icon.ICON_BASKETBALL.getKodIcon());
        }
    },
    COMMAND_SLOT(Icon.ICON_SLOT.getKodIcon() + " слот [1 - 64]") {
        @Override
        public List<Object> apply(Param param) {
            return Command.getSendDices(param, Icon.ICON_SLOT.getKodIcon());
        }
    },
    COMMAND_FRACTIONAL("дробь 0-1") {
        @Override
        public List<Object> apply(Param param) {
            int count = Command.getNumber(param.getTxt(), 1);
            List<Object> result = new ArrayList<>();
            int messageId = param.getMessageId();
            for (int i = 0; i < count; i++) {
                result.add(Command.getSendMessage(param.getChatId(), messageId, Command.getFractional()));
                if (i == 0) {
                    messageId = 0;
                }
            }
            return result;
        }
    },
    COMMAND_50_50(Icon.ICON_50_50.getKodIcon() + " да или нет (50/50)") {
        @Override
        public List<Object> apply(Param param) {
            int count = Command.getNumber(param.getTxt(), 1);
            List<Object> result = new ArrayList<>();
            int messageId = param.getMessageId();
            for (int i = 0; i < count; i++) {
                result.add(Command.getSendSticker(param.getChatId(), messageId, get50x50()));
                if (i == 0) {
                    messageId = 0;
                }
            }
            return result;
        }
    },
    COMMAND_INTEGER("целое с границами") {
        @Override
        public List<Object> apply(Param param) {
            List<Object> result = new ArrayList<>();
            int messageId = param.getMessageId();
            int begin = 0;
            int end = 100;
            int count = 1;
            if (param.getTxt() != null) {
                String[] textInt = param.getTxt().split(" ");
                begin = getNumber(textInt[0], 0);
                if (textInt.length > 1) {
                    end = getNumber(textInt[1], 100);
                    if (textInt.length > 2) {
                        count = getNumber(textInt[2], 1);
                    }
                }
                int max = Math.max(begin, end);
                int min = Math.min(begin, end);
                begin = min;
                end = max;
            }
            result.add(Command.getSendMessage(param.getChatId(), messageId,
                   parseNumber(String.valueOf(begin)) + "   ▬   " + parseNumber(String.valueOf(end))));
            messageId = 0;
            for (int i = 0; i < count; i++) {
                result.add(Command.getSendMessage(param.getChatId(), messageId, getInteger(begin, end)));
            }
            return result;
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

    public static String parseNumber(String txtNumber) {
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

    public static SendMessage getSendMessage(long chatId, int messageId, String txt) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(txt);
        sendMessage.setChatId(chatId);
        if (messageId > 0) {
            sendMessage.setReplyToMessageId(messageId);
        }
        return sendMessage;
    }

    private static SendDice getSendDice(long chatId, int messageId, String txt) {
        SendDice sendDice = new SendDice();
        sendDice.setEmoji(txt);
        sendDice.setChatId(chatId);
        if (messageId > 0) {
            sendDice.setReplyToMessageId(messageId);
        }
        return sendDice;
    }

    private static SendSticker getSendSticker(long chatId, int messageId, String txt) {
        SendSticker sendSticker = new SendSticker();
        sendSticker.setSticker(new InputFile(txt));
        sendSticker.setChatId(chatId);
        if (messageId > 0) {
            sendSticker.setReplyToMessageId(messageId);
        }
        return sendSticker;
    }

    private static int getNumber(String txtNumber, int defaultInt) {
        int result;
        try {
            result = Integer.parseInt(txtNumber);
        } catch (Exception e) {
            result = defaultInt;
        }
        return result;
    }

    private static String getFractional() {
        return parseNumber(String.valueOf(new Random().nextDouble()));
    }

    private static String getInteger(int begin, int end) {
        return parseNumber(String.valueOf(new Random().nextInt(Math.abs(end - begin) + 1) + begin));
    }

    private static String get50x50() {
        Random random = new Random();
        int int50_50 = random.nextInt(2);
        return int50_50 == 0 ? Sticker.STICKER_NO.getStickerId() : Sticker.STICKER_YES.getStickerId();
    }

    private static List<Object> getSendDices(Param param, String kodIcon) {
        int count = Command.getNumber(param.getTxt(), 1);
        List<Object> result = new ArrayList<>();
        int messageId = param.getMessageId();
        for (int i = 0; i < count; i++) {
            result.add(Command.getSendDice(param.getChatId(), messageId, kodIcon));
            if (i == 0) {
                messageId = 0;
            }
        }
        return result;
    }
}
