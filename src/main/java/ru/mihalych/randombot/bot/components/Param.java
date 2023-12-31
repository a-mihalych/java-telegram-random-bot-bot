package ru.mihalych.randombot.bot.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Param {

    private final long chatId;
    private final int messageId;
    private final String txt;
}
