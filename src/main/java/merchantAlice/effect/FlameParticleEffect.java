package merchantAlice.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FlameParticleEffect extends AbstractGameEffect {
    private TextureAtlas.AtlasRegion img;
    private float x;
    private float y;
    private float vX;
    private float vY;
    private float vY2;
    private float vS;
    private float startingDuration;
    private boolean flipX = MathUtils.randomBoolean();
    private float delayTimer = MathUtils.random(0.15F);

    public FlameParticleEffect(float x, float y) {
        this.setImg();
        this.startingDuration = MathUtils.random(0.4F, 1.0F); // 缩短持续时间范围
        this.duration = this.startingDuration;

        float r = MathUtils.random(-12.0F, 12.0F) * MathUtils.random(-12.0F, 12.0F); // 减小随机偏移
        this.x = x + r * Settings.scale - (float) this.img.packedWidth / 2.0F;
        this.y = y + MathUtils.random(-90.0F, 0.0F) * Settings.scale - (float) this.img.packedHeight / 2.0F; // 减小 y 方向偏移范围

        this.vX = MathUtils.random(-10.0F, 10.0F) * Settings.scale; // 减小横向速度
        r = MathUtils.random(2.0F, 15.0F); // 减小随机值范围
        this.vY = r * r / this.startingDuration * Settings.scale; // 减小竖向初速度
        this.vY2 = MathUtils.random(-50.0F, 50.0F) * Settings.scale; // 减小竖向加速度
        this.vS = MathUtils.random(-0.2F, 0.2F) * Settings.scale; // 减小缩放变化速度
        this.color = new Color(1.0F, 0.7F, 0.7F, 0.0F);
        Color var10000 = this.color;
        var10000.g -= MathUtils.random(0.3F); // 调整颜色变化范围
        var10000 = this.color;
        var10000.b -= this.color.g - MathUtils.random(0.3F);
        this.rotation = MathUtils.random(-5.0F, 5.0F); // 减小旋转幅度
        this.scale = Settings.scale * MathUtils.random(0.1F, 0.6F); // 减小粒子初始缩放
        this.renderBehind = MathUtils.randomBoolean(0.5F);
    }

    public void update() {
        if (this.delayTimer > 0.0F) {
            this.delayTimer -= Gdx.graphics.getDeltaTime();
        } else {
            this.x += this.vX * Gdx.graphics.getDeltaTime();
            this.y += this.vY * Gdx.graphics.getDeltaTime();
            this.vY += this.vY2 * Gdx.graphics.getDeltaTime();
            this.vY *= 59.0F * Gdx.graphics.getDeltaTime();
            this.scale += this.vS * Gdx.graphics.getDeltaTime();
            this.duration -= Gdx.graphics.getDeltaTime();
            if (this.duration < 0.0F) {
                this.isDone = true;
            } else if (this.duration > this.startingDuration / 2.0F) {
                this.color.a = Interpolation.fade.apply(0.0F, 0.5F, (this.startingDuration - this.duration) / (this.startingDuration / 2.0F));
            } else if (this.duration < this.startingDuration / 2.0F) {
                this.color.a = Interpolation.fade.apply(0.0F, 0.5F, this.duration / (this.startingDuration / 2.0F));
            }

        }
    }

    private void setImg() {
        int roll = MathUtils.random(2);
        if (roll == 0) {
            this.img = ImageMaster.FLAME_1;
        } else if (roll == 1) {
            this.img = ImageMaster.FLAME_2;
        } else {
            this.img = ImageMaster.FLAME_3;
        }

    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.setBlendFunction(770, 1);
        if (this.flipX && !this.img.isFlipX()) {
            this.img.flip(true, false);
        } else if (!this.flipX && this.img.isFlipX()) {
            this.img.flip(true, false);
        }

        sb.draw(this.img, this.x, this.y, (float) this.img.packedWidth / 2.0F, (float) this.img.packedHeight / 2.0F, (float) this.img.packedWidth, (float) this.img.packedHeight, this.scale, this.scale, this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}