import net.minecraft.block.entity.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.client.render.WorldRenderer;

public class StorageESPModule {

    /**
     * Call this inside your render loop for every BlockEntity in the world.
     */
    public void renderBlockHighlight(MatrixStack matrices, VertexConsumer vertices, BlockEntity entity, double camX, double camY, double camZ) {
        int color = getTargetColor(entity);

        // If color is -1, it's not a storage block we care about
        if (color != -1) {
            BlockPos pos = entity.getPos();
            
            // Create the bounding box and offset it by the camera position to fix "jitter"
            Box box = new Box(pos).offset(-camX, -camY, -camZ);

            // Extract RGB values from the hex/int color
            float r = ((color >> 16) & 0xFF) / 255f;
            float g = ((color >> 8) & 0xFF) / 255f;
            float b = (color & 0xFF) / 255f;
            float a = 1.0f; // Solid lines

            // Draws the wireframe box
            WorldRenderer.drawBox(matrices, vertices, box, r, g, b, a);
        }
    }

    private int getTargetColor(BlockEntity entity) {
        // Trapped Chests -> Red
        if (entity instanceof TrappedChestBlockEntity) {
            return 0xFF0000;
        } 
        // Normal Chests -> Lime Green
        else if (entity instanceof ChestBlockEntity) {
            return 0x32CD32;
        } 
        // Ender Chests -> Light Blue
        else if (entity instanceof EnderChestBlockEntity) {
            return 0xADD8E6;
        } 
        // Shulker Boxes -> Hot Pink
        else if (entity instanceof ShulkerBoxBlockEntity) {
            return 0xFF69B4;
        } 
        // Others (Barrels, Hoppers, Dispensers, etc.) -> White
        else if (entity instanceof BarrelBlockEntity || 
                 entity instanceof HopperBlockEntity || 
                 entity instanceof DispenserBlockEntity) {
            return 0xFFFFFF;
        }

        return -1;
    }
}
