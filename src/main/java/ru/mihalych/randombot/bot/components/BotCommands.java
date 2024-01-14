package ru.mihalych.randombot.bot.components;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import ru.mihalych.randombot.bot.components.enumBot.Command;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BotCommands {

    private final static int BEGIN_COMMAND = "COMMAND_".length();
    public final static String HELP_TEXT = "Бот генерирует случайные числа и знает команды:\n" +
            "/start - начать работу (запустить) бота\n" +
            "/help - помощь, описание команд\n" +
            "/cube - кубик, число (1 - 6), одно случайное число\n" +
            "/cube 5 - второй вариант, с количеством случайных чисел\n" +
            "/dart - дротик, число (1 - 6), одно случайное число\n" +
            "/dart 7 - второй вариант, с количеством случайных чисел\n" +
            "/bowling - боулинг, число (1 - 6), одно случайное число\n" +
            "/bowling 2 - второй вариант, с количеством случайных чисел\n" +
            "/soccer - футбол, число (1 - 5), одно случайное число\n" +
            "/soccer 8 - второй вариант, с количеством случайных чисел\n" +
            "/basketball - баскетбол, число (1 - 5), одно случайное число\n" +
            "/basketball 4  - второй вариант, с количеством случайных чисел\n" +
            "/slot - однорукий бандит, число (1 - 64), одно случайное число\n" +
            "/slot 6 - второй вариант, с количеством случайных чисел\n" +
            "/fractional - дробь (0-1). Входит 0, не входит 1, одно число\n" +
            "/fractional 10 - второй вариант, с количеством случайных чисел\n" +
            "/50_50 - да или нет (50 на 50), одно случайное значение\n" +
            "/50_50 3 - второй вариант, с количеством случайных значений\n" +
            "/integer - целое число (0-100). Входят 0 и 100, одно число\n" +
            "/integer 1 10 - вариант с границами, входят 1 и 10, одно число\n" +
            "/integer 1 10 9 - вариант с количеством случайных (девять) чисел\n" +
            "    * если не распознано количество случайных чисел, то будет 1\n" +
            "    ** в '/integer ? 10 9' не распознано первое число, будет 0\n" +
            "    *** в '/integer 1 ? 9' не распознано второе число, будет 100";
    public final static String START_TEXT = "Бот запущен!";

    public static List<BotCommand> getFullListCommands() {
        List<BotCommand> result = new ArrayList<>();
        for (Command value : Command.values()) {
            result.add(new BotCommand("/" + value.name().substring(BEGIN_COMMAND).toLowerCase(),
                                      value.getTxtCommand()));
        }
        return result;
    }
}
