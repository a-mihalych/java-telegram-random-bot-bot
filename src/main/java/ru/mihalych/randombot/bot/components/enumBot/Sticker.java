package ru.mihalych.randombot.bot.components.enumBot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Sticker {

    STICKER_SUPER("CAADBQADiQMAAukKyAPZH7wCI2BwFxYE"),
    STICKER_SOS("CAACAgIAAxkBAAIScmV7RV2oU71MJSZNqxGT1pGfLbi-AAJeAAPANk8TD4PNEIK9PE0zBA"),
    STICKER_YES("CAACAgIAAxkBAAIPIGVqTj16DMkWbdXSNxouCMscrL96AAJEAANBtVYMWkioov96ZxQzBA"),
    STICKER_NO("CAACAgIAAxkBAAIPHmVqTXxZZLJBftL5LdTY7QH8NEdKAAJFAANBtVYMHZL0ku-42fwzBA"),
    STICKER_OK("CAACAgIAAxkBAAIPHGVqTNxIx_-98U-u--1A6qNcmj_fAAJWAANBtVYM1ZNlBU-tNFYzBA");

    private final String stickerId;
}
