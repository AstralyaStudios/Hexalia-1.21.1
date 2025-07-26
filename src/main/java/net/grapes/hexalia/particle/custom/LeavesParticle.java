package net.grapes.hexalia.particle.custom;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

public class LeavesParticle extends SimpleAnimatedParticle {
    protected LeavesParticle(ClientLevel level,
                             double x, double y, double z,
                             double velocityX, double velocityY, double velocityZ,
                             SpriteSet sprites) {

    /* Happy Villager also extends SimpleAnimatedParticle and sets a tiny ‘base
       quad-size’ scale of 1 pixel; 0 → use default, so keep 0.01 F like the
       original to match it. */
        super(level, x, y, z, sprites, 0.01F);

        /* --- Match Happy Villager behaviour ---------------------------------- */

        this.hasPhysics = false;           // floats through blocks
        this.friction   = 0.96F;           // gentle horizontal drag
        this.gravity    = 0.0F;            // no falling

    /* If caller supplied zero velocities (typical), give it a random gentle
       outward push like the vanilla particle does. */
        if (velocityX == 0 && velocityY == 0 && velocityZ == 0) {
            this.xd = (random.nextDouble() - 0.5D) * 0.02D;
            this.yd =  random.nextDouble()            * 0.02D;
            this.zd = (random.nextDouble() - 0.5D) * 0.02D;
        } else {
            this.xd = velocityX;
            this.yd = velocityY;
            this.zd = velocityZ;
        }

        /* Scale & lifetime: Happy Villager is small and short-lived. */
        this.quadSize *= (0.2F + random.nextFloat() * 0.4F);  // ≈ 0.7 – 1.3
        this.lifetime  = 14 + random.nextInt(6);               // 14 – 19 ticks

        /* Tint: keep your custom colour or pick any int RGB. */
        this.setColor(15916745);   // pale-green leaves

        this.setSpriteFromAge(sprites);  // animates through 4×4 sheet like Happy Villager
    }

    @Override
    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().move(x, y, z));
        this.setLocationFromBoundingbox();
    }

    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new LeavesParticle(level, x, y, z, velocityX, velocityY, velocityZ, this.spriteSet);
        }
    }
}
