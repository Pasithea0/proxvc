package fiveavian.proxvc.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.options.components.OptionsComponent;
import net.minecraft.core.lang.I18n;

public class EnumOptionComponent<T extends Enum<T>> implements OptionsComponent {
    private final net.minecraft.client.option.OptionEnum<T> option;
    private Minecraft mc;

    public EnumOptionComponent(net.minecraft.client.option.OptionEnum<T> option) {
        this.option = option;
    }

    @Override
    public void init(Minecraft mc) {
        this.mc = mc;
    }

    @Override
    public int getHeight() {
        return 20;
    }

    @Override
    public void render(int x, int y, int width, int relativeMouseX, int relativeMouseY) {
        // Use "options." prefix for translation key
        String displayKey = "options." + option.name;
        String displayText = I18n.getInstance().translateKey(displayKey);
        // Fallback to option name if translation not found
        if (displayText.equals(displayKey)) {
            displayText = option.name;
        }
        mc.currentScreen.drawString(mc.font, displayText, x + 5, y + 6, 0xFFFFFF);
        
        // Draw current value with translation
        String valueKey = displayKey + "." + option.value.name().toLowerCase();
        String valueText = I18n.getInstance().translateKey(valueKey);
        // Fallback to enum name if translation not found
        if (valueText.equals(valueKey)) {
            valueText = option.value.name();
        }
        int valueWidth = mc.font.getStringWidth(valueText);
        mc.currentScreen.drawString(mc.font, valueText, x + width - valueWidth - 5, y + 6, 0xCCCCCC);
    }

    @Override
    public void onMouseClick(int mouseButton, int x, int y, int width, int relativeMouseX, int relativeMouseY) {
        if (relativeMouseX >= 0 && relativeMouseX < width && relativeMouseY >= 0 && relativeMouseY < 20) {
            T[] values = option.value.getDeclaringClass().getEnumConstants();
            int currentIndex = -1;
            for (int i = 0; i < values.length; i++) {
                if (values[i] == option.value) {
                    currentIndex = i;
                    break;
                }
            }
            if (currentIndex >= 0) {
                int nextIndex = (currentIndex + 1) % values.length;
                option.value = values[nextIndex];
                mc.sndManager.playSound("random.click", net.minecraft.core.sound.SoundCategory.GUI_SOUNDS, 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void onMouseMove(int x, int y, int width, int relativeMouseX, int relativeMouseY) {
    }

    @Override
    public void onMouseRelease(int i, int j, int k, int l, int m, int n) {
    }

    @Override
    public void tick() {
    }

    @Override
    public boolean matchesSearchTerm(String string) {
        return false;
    }

    @Override
    public void onClose() {
    }

    @Override
    public void onKeyPress(int i, char c) {
    }
}
