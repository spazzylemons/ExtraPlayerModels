package spazzylemons.extraplayermodels.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spazzylemons.extraplayermodels.client.ClientData;
import spazzylemons.extraplayermodels.client.gui.ConfigScreen;
import spazzylemons.extraplayermodels.client.gui.PreviewPlayerFunctionsKt;

/**
 * Mixin to add a button and player model to the title screen.
 */
@Environment(EnvType.CLIENT)
@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    /**
     * X position to render the player model at.
     */
    private int renderPlayerAtX;

    /**
     * Y position to render the player model at.
     */
    private int renderPlayerAtY;

    // Add a button for Extra Player Models settings on the title screen, because why not?
    @Inject(at = @At("TAIL"), method = "init")
    public void init(CallbackInfo ci) {
        ClientData.INSTANCE.reload();

        AbstractButtonWidget accessibilityButton = null;
        String text = I18n.translate("narrator.button.accessibility");

        for (AbstractButtonWidget button : buttons) {
            if (button instanceof TexturedButtonWidget && button.getMessage().equals(text)) {
                accessibilityButton = button;
                break;
            }
        }

        if (accessibilityButton == null) {
            renderPlayerAtX = width / 2 + 124 + 39;
            renderPlayerAtY = height / 4 + 48 + 72 + 12 - 4;
        } else {
            renderPlayerAtX = accessibilityButton.x + accessibilityButton.getWidth() + 39;
            renderPlayerAtY = accessibilityButton.y - 4;
        }
        addButton(new ButtonWidget(renderPlayerAtX - 35, renderPlayerAtY + 4, 70, 20, I18n.translate("text.extraplayermodels.appearance"), buttonWidget -> {
            if (minecraft != null) {
                minecraft.openScreen(new ConfigScreen(this));
            }
        }));
    }

    // Show the player model on the title screen, Bedrock style
    @Inject(at = @At("TAIL"), method = "render")
    public void render(int mouseX, int mouseY, float delta, CallbackInfo ci) {
        PreviewPlayerFunctionsKt.renderPlayerOnScreen(
                renderPlayerAtX,
                renderPlayerAtY,
                30
        );
    }
}