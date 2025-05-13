package merchantAlice.helper;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.android.mods.AssetLoader;
import merchantAlice.MerchantAlice;

import java.util.ArrayList;

public class ImageHelper {
    public static ArrayList<Texture> SP = new ArrayList<>();
    public static ArrayList<Texture> SPDie = new ArrayList<>();
    public static ArrayList<Texture> SPNudity = new ArrayList<>();
    public static ArrayList<Texture> SPEmbarrassment = new ArrayList<>();
    public static ArrayList<Texture> SPTentacle = new ArrayList<>();
    public static ArrayList<Texture> SPSelfComfort = new ArrayList<>();
    public static ArrayList<Texture> Baoyi = new ArrayList<>();
    public static ArrayList<Texture> BaoyiSP = new ArrayList<>();
    public static ArrayList<Texture> Wrap = new ArrayList<>();
    public static ArrayList<Texture> Hit = new ArrayList<>();
    public static ArrayList<Texture> heart = new ArrayList<>();

    public static Texture DesireUI;
    public static Texture NullTopLever;


    static {
        for (int i = 1; i < 5; i++)
            SP.add(AssetLoader.getTexture(MerchantAlice.MOD_ID, "MerchantAliceModResources/img/char/StandingPortrait/" + i + ".png"));

        for (int i = 1; i < 5; i++)
            SPDie.add(AssetLoader.getTexture(MerchantAlice.MOD_ID, "MerchantAliceModResources/img/char/Die/" + i + ".png"));

        for (int i = 1; i < 5; i++)
            SPNudity.add(AssetLoader.getTexture(MerchantAlice.MOD_ID, "MerchantAliceModResources/img/char/StandingPortrait2/" + i + ".png"));

        for (int i = 1; i < 5; i++)
            SPEmbarrassment.add(AssetLoader.getTexture(MerchantAlice.MOD_ID, "MerchantAliceModResources/img/char/StandingPortrait3/" + i + ".png"));

        for (int i = 1; i < 7; i++)
            SPTentacle.add(AssetLoader.getTexture(MerchantAlice.MOD_ID, "MerchantAliceModResources/img/char/Tentacle/" + i + ".png"));

        for (int i = 1; i < 5; i++)
            SPSelfComfort.add(AssetLoader.getTexture(MerchantAlice.MOD_ID, "MerchantAliceModResources/img/char/SelfComfort/" + i + ".png"));

        for (int i = 1; i < 9; i++)
            Baoyi.add(AssetLoader.getTexture(MerchantAlice.MOD_ID, "MerchantAliceModResources/img/aniGif/baoyi/" + i + ".png"));

        for (int i = 1; i < 5; i++)
            BaoyiSP.add(AssetLoader.getTexture(MerchantAlice.MOD_ID, "MerchantAliceModResources/img/aniGif/baoyiSp/" + i + ".png"));

        for (int i = 1; i < 32; i++)
            Wrap.add(AssetLoader.getTexture(MerchantAlice.MOD_ID, "MerchantAliceModResources/img/aniGif/Wrap/" + i + ".png"));

        for (int i = 1; i < 14; i++)
            Hit.add(AssetLoader.getTexture(MerchantAlice.MOD_ID, "MerchantAliceModResources/img/aniGif/hit/" + i + ".png"));

        for (int i = 1; i < 5; i++)
            heart.add(AssetLoader.getTexture(MerchantAlice.MOD_ID, "MerchantAliceModResources/img/vfx/heart" + i + ".png"));

        DesireUI = AssetLoader.getTexture(MerchantAlice.MOD_ID, "MerchantAliceModResources/img/UI/DesireUI.png");
        NullTopLever = AssetLoader.getTexture(MerchantAlice.MOD_ID, "MerchantAliceModResources/img/char/null.png");
    }
}
