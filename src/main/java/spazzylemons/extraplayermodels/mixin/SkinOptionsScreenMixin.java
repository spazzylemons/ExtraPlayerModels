package spazzylemons.extraplayermodels.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.options.SkinOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import spazzylemons.extraplayermodels.client.gui.ConfigScreen;

/**
 * Mixin to add a button to the skin options.
 */
@Mixin(SkinOptionsScreen.class)
public class SkinOptionsScreenMixin extends Screen {
    protected SkinOptionsScreenMixin(Text title) {
        super(title);
    }

    // Add a button for Extra Player Models settings in the skin options, as it makes sense to put one there
    @Inject(at = @At("TAIL"), method = "init")
    public void init(CallbackInfo ci) {
        this.addButton(new ButtonWidget(this.width / 2 - 100, height - 48, 200, 20, I18n.translate("text.extraplayermodels.appearance.withmodname"), (buttonWidget) -> {
            if (minecraft != null) {
                minecraft.openScreen(new ConfigScreen(this));
            }
        }));
    }
}
