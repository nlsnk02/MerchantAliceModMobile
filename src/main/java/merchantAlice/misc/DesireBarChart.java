package merchantAlice.misc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.megacrit.cardcrawl.core.Settings;

import static merchantAlice.misc.DesireUI.HeatStanceCriticalValue;

public class DesireBarChart {

    // 柱状图数据
    private static final float barWidth = 16;
    private static final float barMaxHight = 300 * Settings.scale;
    private static final float waveAmplitude = 2 * Settings.scale;
    private static float waveFrequency = 0.1f;
    private static final float waveSpeed = 4;
    private static float time = 0;
    private static float aniTime = 0;
    private static float barHeight = 0;

    public static void update() {
        if (aniTime > 0) {
            aniTime -= Gdx.graphics.getDeltaTime();
            waveFrequency = 0.4F;
        } else {
            aniTime = 0;
            waveFrequency = 0.1f;
        }

        time += Gdx.graphics.getDeltaTime() * waveSpeed;
        if (time > 1000) time -= 1000;

        float newHeight = DesireUI.inst.desireAmount / 50F * barMaxHight;//数字为显示的最高层数
        if (newHeight > barMaxHight) newHeight = barMaxHight;
        if (newHeight != barHeight) {
            aniTime = 1F;
            barHeight = newHeight;
        }
    }

    public static void render(ShapeRenderer renderer) {
        float x = DesireUI.inst.current_x - 6 * Settings.scale; // 每个柱状图的 x 坐标
        float y = DesireUI.inst.current_y - 130 * Settings.scale; // 柱状图的底部 y 坐标

        if (DesireUI.inst.desireAmount > 0) {
            renderer.setColor(DesireUI.Transparency);
            renderer.rect(x, y, barWidth, barHeight);
        }

        if (DesireUI.inst.desireAmount >= HeatStanceCriticalValue)
            drawWaveTop(renderer, x, y + barHeight, barWidth, waveAmplitude, waveFrequency, time);

    }

    private static void drawWaveTop(ShapeRenderer renderer, float x, float y, float width, float amplitude, float frequency, float time) {
        int segments = 50; // 波浪分段数，越大越平滑
        float segmentWidth = width / segments; // 每段的宽度

        renderer.setColor(DesireUI.Transparency);

        float prevX = x;
        float prevY = y + amplitude * (float) Math.sin(frequency * x + time) + amplitude;

        // 逐段绘制波浪区域
        for (int i = 1; i <= segments; i++) {
            float currentX = x + i * segmentWidth;
            float currentY = y + amplitude * (float) Math.sin(frequency * currentX + time) + amplitude;

            renderer.triangle(prevX, y, prevX, prevY, currentX, currentY);
            renderer.triangle(prevX, y, currentX, currentY, currentX, y);

            prevX = currentX;
            prevY = currentY;
        }
    }


}
