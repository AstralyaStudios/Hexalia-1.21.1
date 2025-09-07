package net.astralya.hexalia.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;

public class MoteParticle extends TextureSheetParticle {

    protected MoteParticle(ClientLevel level, double x, double y, double z,
                           double xSpeed, double ySpeed, double zSpeed,
                           float size, SpriteSet spriteSet) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);

        this.friction = 0.96F;
        this.gravity = 0.0F;
        this.lifetime = 40 + this.random.nextInt(20);
        this.quadSize = size * 0.05F;
        this.setAlpha(0.7F);

        float hue = level.random.nextFloat();
        float r = Mth.clamp(0.6F + 0.4F * (float)Math.sin(hue * 6.28F), 0, 1);
        float g = 0.8F + 0.2F * level.random.nextFloat();
        float b = 1.0F;

        this.setColor(r, g, b);
        this.pickSprite(spriteSet);
    }

    @Override
    public void tick() {
        super.tick();

        this.yd += 0.002D;

        if (this.age > this.lifetime - 10) {
            this.alpha -= 0.07F;
        }
    }

    @Override
    public int getLightColor(float partialTick) {
        return 0xF000F0;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level,
                                       double x, double y, double z,
                                       double xSpeed, double ySpeed, double zSpeed) {
            return new MoteParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, 1.0F, this.spriteSet);
        }
    }
}