package ru.mihalych.randombot.bot.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.mihalych.randombot.bot.components.enumBot.Command;

@Getter
@RequiredArgsConstructor
public class CommandAndText {

    private final Command command;
    private final String txt;
}
