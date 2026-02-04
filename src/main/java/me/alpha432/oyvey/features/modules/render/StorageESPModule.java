package me.alpha432.oyvey.features.modules.render;

import me.alpha432.oyvey.event.events.Render3DEvent;
import me.alpha432.oyvey.features.modules.Module;
import me.alpha432.oyvey.util.RenderUtil; // Ensure this path is correct for your port
import net.minecraft.block.entity.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import java.awt.Color;
import net.minecraft.client.render.WorldRenderer;

public class StorageESP extends Module {

    public StorageESP() {
        super("StorageESP", "Highlights containers", Category.RENDER, true, false, false);
    }

    // OyVey Ported uses the @EventHandler or @Subscribe annotation
    // Check your project's other modules to see which one it uses!
    public void onRender3D(Render3DEvent event) {
        if (mc.world == null || mc.player == null) return;

        // In 1.21.11, we iterate through blockEntities directly
        for (BlockEntity entity : mc.world.blockEntities) {
            Color color = getStorageColor(entity);
            
            if (color != null) {
                // Using OyVey's internal RenderUtil to draw the actual box
                // This handles the MatrixStack and Camera offset for you
                RenderUtil.drawBox(entity.getPos(), color);
            }
        }
    }

    private Color getStorageColor(BlockEntity entity) {
        // Red: Trapped Chests
        if (entity instanceof TrappedChestBlockEntity) return Color.RED;
        
        // Lime Green: Normal Chests
        if (entity instanceof ChestBlockEntity) return new Color(50, 205, 50);
        
        // Light Blue: Ender Chests
        if (entity instanceof EnderChestBlockEntity) return new Color(173, 216, 230);
        
        // Hot Pink: Shulker Boxes (All colors)
        if (entity instanceof ShulkerBoxBlockEntity) return new Color(255, 105, 180);
        
        // White: Everything else (Barrels, Hoppers, Dispensers)
        if (entity instanceof BarrelBlockEntity || 
            entity instanceof HopperBlockEntity || 
            entity instanceof DispenserBlockEntity) {
            return Color.WHITE;
        }
        
        return null;
    }
}
