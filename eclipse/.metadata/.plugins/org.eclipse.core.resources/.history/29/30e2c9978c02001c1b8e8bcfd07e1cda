package me.luma.client.management.gui.mainmenu;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;

import me.luma.client.core.Luma;
import me.luma.client.core.registry.impl.ClientLoader;
import me.luma.client.management.gui.mainmenu.buttons.GuiTexturedButton;
import me.luma.client.management.gui.mainmenu.login.GuiAltLogin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.src.CustomPanorama;
import net.minecraft.src.CustomPanoramaProperties;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.ISaveFormat;

public class MainMenu extends GuiScreen implements GuiYesNoCallback
{
    private static final AtomicInteger field_175373_f = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random RANDOM = new Random();

    /** Counts the number of screen updates. */
    private float updateCounter;

    public static String content;
    
    /** The splash message. */
    private String splashText;
    private GuiButton buttonResetDemo;

    /** Timer used to rotate the panorama, increases every tick. */
    private int panoramaTimer;

    /**
     * Texture allocated for the current viewport of the main menu's panorama background.
     */
    private DynamicTexture viewportTexture;
    private boolean field_175375_v = true;

    /**
     * The Object object utilized as a thread lock when performing non thread-safe operations
     */
    private final Object threadLock = new Object();

    /** OpenGL graphics card warning. */
    private String openGLWarning1;

    /** OpenGL graphics card warning. */
    private String openGLWarning2;

    private String openGLWarningLink;
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");

    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[] {new ResourceLocation("luma/mainmenu/background.png"), new ResourceLocation("luma/mainmenu/background.png"), new ResourceLocation("luma/mainmenu/background.png"), new ResourceLocation("luma/mainmenu/background.png"), new ResourceLocation("luma/mainmenu/background.png"), new ResourceLocation("luma/mainmenu/background.png")};
    public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation backgroundTexture;

    private GuiButton realmsButton;
    private static final String __OBFID = "CL_00001154";
    private GuiButton modButton;
    private GuiScreen modUpdateNotification;

    public MainMenu()
    {
        this.openGLWarning2 = field_96138_a;
        BufferedReader bufferedreader = null;
    }

    public void updateScreen()
    {
        ++this.panoramaTimer;
    }

    public boolean doesGuiPauseGame()
    {
        return false;
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
    }

    public void initGui()
    {
    	ClientLoader.loaderInstance.getDiscordRP().update("Idle", "Main Menu");
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);

        int i = this.height / 4 + 48;
        
        this.addButtons(i, 24);


        this.mc.func_181537_a(false);
    }

    private void addButtons(int p_73969_1_, int p_73969_2_)
    {
    	//this.buttonList.add(new GuiMenuButton(1, width / 2 - 149, height / 2 + 60, ""));
    	this.buttonList.add(new GuiButton(1, width / 2 - 75, height / 2 + -62, "Singleplayer", true));
    	this.buttonList.add(new GuiButton(2, width / 2 - 75, height / 2 + -40, "Multiplayer", true));
    	this.buttonList.add(new GuiButton(3, width / 2 - 75, height / 2 + -18, "Alt Manager", true));
    	this.buttonList.add(new GuiButton(4, width / 2 - 75, height / 2 + 4, "Options", true));
    	this.buttonList.add(new GuiButton(5, width / 2 - 75, height / 2 + 26, "Shutdown", true));
    	//this.buttonList.add(new GuiTexturedButton(1, width / 2 - 149, height / 2 + 60, 100, 100, "luma/mainmenu/icons/single.png"));
    	//this.buttonList.add(new GuiTexturedButton(2, width / 2 - 49, height / 2 + 60, 100, 100, "luma/mainmenu/icons/multi.png"));
    	//this.buttonList.add(new GuiTexturedButton(3, width / 2 - -51, height / 2 + 60, 100, 100, "luma/mainmenu/icons/settings.png"));
    	//this.buttonList.add(new GuiTexturedButton(4, width / 2 - 151, height / 2 + 60, 100, 100, "luma/mainmenu/icons/exit.png"));
    	//this.buttonList.add(new GuiTexturedButton(5, width / 2 - 37, height / 2 + 130, 80, 80, "luma/mainmenu/icons/altmanager.png"));
    	boolean width = true;
    	boolean height = true;
    }

    protected void actionPerformed(GuiButton button) throws IOException
    {
    	if (button.id == 1) {
    		this.mc.displayGuiScreen(new GuiSelectWorld(this));
    	}
    	if (button.id == 2)
        {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
    	
    	if (button.id == 3)
        {
            this.mc.displayGuiScreen(new GuiAltLogin(this));
        }
    	
    	if (button.id == 4)
        {
    		this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
    	
    	if (button.id == 5)
        {
            this.mc.shutdown();
        }

    }


    public void confirmClicked(boolean result, int id)
    {
        if (result && id == 12)
        {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        }
        else if (id == 13)
        {
            if (result)
            {
                try
                {
                    Class oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object)null, new Object[0]);
                    oclass.getMethod("browse", new Class[] {URI.class}).invoke(object, new Object[] {new URI(this.openGLWarningLink)});
                }
                catch (Throwable throwable)
                {
                    logger.error("Couldn\'t open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    private void drawPanorama(int p_73970_1_, int p_73970_2_, float p_73970_3_)
    {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        GlStateManager.matrixMode(5889);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Project.gluPerspective(120.0F, 1.0F, 0.05F, 10.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.disableCull();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        byte b0 = 8;
        int i = 64;
        CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();

        if (custompanoramaproperties != null)
        {
            i = custompanoramaproperties.getBlur1();
        }

        for (int j = 0; j < i; ++j)
        {
            GlStateManager.pushMatrix();
            float f = ((float)(j % b0) / (float)b0 - 0.5F) / 64.0F;
            float f1 = ((float)(j / b0) / (float)b0 - 0.5F) / 64.0F;
            float f2 = 0.0F;
            GlStateManager.translate(f, f1, f2);
            GlStateManager.rotate(MathHelper.sin(((float)this.panoramaTimer + p_73970_3_) / 400.0F) * 25.0F + 20.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-((float)this.panoramaTimer + p_73970_3_) * 0.1F, 0.0F, 1.0F, 0.0F);

            for (int k = 0; k < 6; ++k)
            {
                GlStateManager.pushMatrix();

                if (k == 1)
                {
                    GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 2)
                {
                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 3)
                {
                    GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
                }

                if (k == 4)
                {
                    GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
                }

                if (k == 5)
                {
                    GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
                }

                ResourceLocation[] aresourcelocation = titlePanoramaPaths;

                if (custompanoramaproperties != null)
                {
                    aresourcelocation = custompanoramaproperties.getPanoramaLocations();
                }

                this.mc.getTextureManager().bindTexture(aresourcelocation[k]);
                worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
                int l = 255 / (j + 1);
                float f3 = 0.0F;
                worldrenderer.pos(-1.0D, -1.0D, 1.0D).tex(0.0D, 0.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(1.0D, -1.0D, 1.0D).tex(1.0D, 0.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(1.0D, 1.0D, 1.0D).tex(1.0D, 1.0D).color(255, 255, 255, l).endVertex();
                worldrenderer.pos(-1.0D, 1.0D, 1.0D).tex(0.0D, 1.0D).color(255, 255, 255, l).endVertex();
                tessellator.draw();
                GlStateManager.popMatrix();
            }

            GlStateManager.popMatrix();
            GlStateManager.colorMask(true, true, true, false);
        }

        worldrenderer.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.colorMask(true, true, true, true);
        GlStateManager.matrixMode(5889);
        GlStateManager.popMatrix();
        GlStateManager.matrixMode(5888);
        GlStateManager.popMatrix();
        GlStateManager.depthMask(true);
        GlStateManager.enableCull();
        GlStateManager.enableDepth();
    }

    private void rotateAndBlurSkybox(float p_73968_1_)
    {
        this.mc.getTextureManager().bindTexture(this.backgroundTexture);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D, 0, 0, 0, 0, 0, 256, 256);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.colorMask(true, true, true, false);
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        GlStateManager.disableAlpha();
        byte b0 = 3;
        int i = 3;
        CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();

        if (custompanoramaproperties != null)
        {
            i = custompanoramaproperties.getBlur2();
        }

        for (int j = 0; j < i; ++j)
        {
            float f = 1.0F / (float)(j + 1);
            int k = this.width;
            int l = this.height;
            float f1 = (float)(j - b0 / 2) / 256.0F;
            worldrenderer.pos((double)k, (double)l, (double)this.zLevel).tex((double)(0.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos((double)k, 0.0D, (double)this.zLevel).tex((double)(1.0F + f1), 1.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(1.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
            worldrenderer.pos(0.0D, (double)l, (double)this.zLevel).tex((double)(0.0F + f1), 0.0D).color(1.0F, 1.0F, 1.0F, f).endVertex();
        }

        tessellator.draw();
        GlStateManager.enableAlpha();
        GlStateManager.colorMask(true, true, true, true);
    }
    
    private void renderSkybox(int p_73971_1_, int p_73971_2_, float p_73971_3_)
    {
        this.mc.getFramebuffer().unbindFramebuffer();
        GlStateManager.viewport(0, 0, 256, 256);
        this.drawPanorama(p_73971_1_, p_73971_2_, p_73971_3_);
        this.rotateAndBlurSkybox(p_73971_3_);
        int i = 3;
        CustomPanoramaProperties custompanoramaproperties = CustomPanorama.getCustomPanoramaProperties();

        if (custompanoramaproperties != null)
        {
            i = custompanoramaproperties.getBlur3();
        }

        for (int j = 0; j < i; ++j)
        {
            this.rotateAndBlurSkybox(p_73971_3_);
            this.rotateAndBlurSkybox(p_73971_3_);
        }

        this.mc.getFramebuffer().bindFramebuffer(true);
        GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
        float f2 = this.width > this.height ? 120.0F / (float)this.width : 120.0F / (float)this.height;
        float f = (float)this.height * f2 / 256.0F;
        float f1 = (float)this.width * f2 / 256.0F;
        int k = this.width;
        int l = this.height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        worldrenderer.pos(0.0D, (double)l, (double)this.zLevel).tex((double)(0.5F - f), (double)(0.5F + f1)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos((double)k, (double)l, (double)this.zLevel).tex((double)(0.5F - f), (double)(0.5F - f1)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos((double)k, 0.0D, (double)this.zLevel).tex((double)(0.5F + f), (double)(0.5F - f1)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        worldrenderer.pos(0.0D, 0.0D, (double)this.zLevel).tex((double)(0.5F + f), (double)(0.5F + f1)).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        tessellator.draw();
    }
    
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GlStateManager.disableAlpha();
        //this.renderSkybox(mouseX, mouseY, partialTicks);
        ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        drawImg(new ResourceLocation("luma/mainmenu/birdbg.jpeg"), 0.0, 0.0, width, height);
        //drawImg(new ResourceLocation("luma/mainmenu/lumatext.png"), width / 2 - 125, 90, 240.0, 200.0);
        
        //Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow("User:" + content, (sr.getScaledWidth() - Minecraft.getMinecraft().fontRendererObj.getStringWidth("User:" + content)) / 2, 50, -1);
        
        final String timeString = LocalTime.now().toString().split("\\.")[0];
        ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow(timeString, (sr.getScaledWidth() - ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(timeString)) / 2, 40, -1);
        final String dateString = new Date().toString().substring(0, 10).trim();
        ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow(dateString, (sr.getScaledWidth() - ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(dateString)) / 2, 40 + ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getHeight(dateString) + 5, -1);
        final String authorString = "By ".concat(Luma.clientDev);
        ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow(authorString, (sr.getScaledWidth() - ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(authorString)) / 2, sr.getScaledHeight() - ClientLoader.loaderInstance.loaderInstance.fontManager.getFont("SFL 10").getHeight(authorString) - 10, -1);
        final String clientName = "Luma";
        drawString(3, clientName, this.width / 5.9F - ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getWidth(clientName) / 2, this.height / 10, -1);
        //ClientLoader.loaderInstance.fontManager.getFont("SFL 10").drawStringWithShadow(clientName, (sr.getScaledWidth() - ClientLoader.loaderInstance.fontManager.getFont("SFL 5").getWidth(clientName)) / 2, sr.getScaledHeight() - ClientLoader.loaderInstance.fontManager.getFont("SFL 10").getHeight(clientName + 5) - 100, -1);
        
        super.drawScreen(mouseX, mouseY, partialTicks);

    }
    
    
    public static void drawString(final double scale, final String text, final float xPos, final float yPos, final int color) {
    	GlStateManager.pushMatrix();
    	GlStateManager.scale(scale, scale, scale);
    	Minecraft.getMinecraft().fontRendererObj.drawString(text, xPos, yPos, color);
    	GlStateManager.popMatrix();
    }

    public static void drawImg(ResourceLocation loc, double posX, double posY, double width, double height) {

        GlStateManager.pushMatrix();
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        Minecraft.getMinecraft().getTextureManager().bindTexture(loc);
        float f = 1.0F / (float) width;
        float f1 = 1.0F / (float) height;
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
        worldrenderer.pos(posX, (posY + height), 0.0D).tex(0 * f, (0 + (float) height) * f1).endVertex();
        worldrenderer.pos((posX + width), (posY + height), 0.0D).tex((0 + (float) width) * f, (0 + (float) height) * f1).endVertex();
        worldrenderer.pos((posX + width), posY, 0.0D).tex((0 + (float) width) * f, 0 * f1).endVertex();
        worldrenderer.pos(posX, posY, 0.0D).tex(0 * f, 0 * f1).endVertex();
        tessellator.draw();
        GlStateManager.popMatrix();
    }
    
    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
}
