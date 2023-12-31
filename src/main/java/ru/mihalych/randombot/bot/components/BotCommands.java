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
    public final static String HELP_TEXT = "Бот генерирует случайные числа и принимает следующие команды:\n" +
            "/start - начать работу (запустить) бота\n" +
            "/help - помощь, описание команд\n" +
            "/cube - кубик, число (1 - 6)\n" +
            "/dart - дротик, число (1 - 6)\n" +
            "/bowling - боулинг, число (1 - 6)\n" +
            "/soccer - футбол, число (1 - 5)\n" +
            "/basketball - баскетбол, число (1 - 5)\n" +
            "/slot - однорукий бандит, число (1 - 64)\n" +
            "/fractional - дробное число, число (0 - 1). Входит 0, не входит 1\n" +
            "/50_50 - Да или нет (50 на 50)\n" +
            "/integer - целое число (0 - 100). Входят 0 и 100\n" +
            "/integer 1 10 - второй вариант этой команды, с параметрами.\n" +
            "    Можно указать через пробел одно или два числа, если одно число, то от него до 100, оба входят.\n" +
            "    Если два числа, то случайное число от первого до второго, оба входят";
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
