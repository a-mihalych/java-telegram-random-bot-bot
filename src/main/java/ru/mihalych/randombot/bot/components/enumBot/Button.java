package ru.mihalych.randombot.bot.components.enumBot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Button {

    BUTTON_CUBE(Icon.ICON_CUBE.getKodIcon() + " [1 - 6]"),
    BUTTON_DART(Icon.ICON_DART.getKodIcon() + " [1 - 6]"),
    BUTTON_BOWLING(Icon.ICON_BOWLING.getKodIcon() + " [1 - 6]"),
    BUTTON_SOCCER(Icon.ICON_SOCCER.getKodIcon() + " [1 - 5]"),
    BUTTON_BASKETBALL(Icon.ICON_BASKETBALL.getKodIcon() + " [1 - 5]"),
    BUTTON_SLOT(Icon.ICON_SLOT.getKodIcon() + " [1 - 64]"),
    BUTTON_50_50(Icon.ICON_50_50.getKodIcon() + " да/нет"),
    BUTTON_FRACTIONAL("Дробь: " + Icon.ICON_0.getKodIcon() + " ▬ " + Icon.ICON_1.getKodIcon()),
    BUTTON_INTEGER("Целое: " + Icon.ICON_1234.getKodIcon() + " ▬ " + Icon.ICON_1234.getKodIcon());

    private final String txtButton;

    public static String getTxtCommand(String nameButton) {
        String command = "/null";
        for (Button value : Button.values()) {
            if (value.txtButton.equals(nameButton)) {
                int indexStart = value.name().indexOf("_") + 1;
                command = "/" + value.name().substring(indexStart).toLowerCase();
                break;
            }
        }
        return command;
    }
}
