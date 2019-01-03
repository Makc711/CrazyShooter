package com.rusanov.game.Shooter;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.util.ResourceLoader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.IntBuffer;

public class Input {
    private static boolean isKeyPushedDownCurrent = false;
    private static boolean isMouseButtonPushedDownCurrent = false;

    public static boolean isKeyClicked(int key) {
        boolean isKeyPushedDownLast = isKeyPushedDownCurrent;
        isKeyPushedDownCurrent = Keyboard.isKeyDown(key);
        return !isKeyPushedDownCurrent && isKeyPushedDownLast;
    }

    public static void keyboardRefresh() {
        Keyboard.destroy();
        try {
            Keyboard.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    public static int getEventKeycode() {
        int keycode = Keyboard.KEY_NONE;
        while (Keyboard.next());
        if(Keyboard.getEventKeyState()) {
            keycode = Keyboard.getEventKey();
        }
        return keycode;
    }

    public static boolean isMouseButtonClicked(int button) {
        boolean isKeyPushedDownLast = isMouseButtonPushedDownCurrent;
        isMouseButtonPushedDownCurrent = Mouse.isButtonDown(button);
        return !isMouseButtonPushedDownCurrent && isKeyPushedDownLast;
    }

    public static void setCursor(String imageName, int xHotSpot, int yHotSpot) {
        try {
            try {
                BufferedImage image = ImageIO.read(ResourceLoader.getResourceAsStream(imageName));
                int[] pixels = new int[image.getWidth() * image.getHeight()];
                image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
                IntBuffer imageBuffer = IntBuffer.wrap(pixels);
                Cursor cursor = new Cursor(image.getWidth(), image.getHeight(), xHotSpot, yHotSpot, 1, imageBuffer, null);
                Mouse.setNativeCursor(cursor);
            } catch (RuntimeException | IOException e) {
                System.err.println("Can't read file: " + imageName + ".png");
                e.printStackTrace();
                Mouse.setNativeCursor(null);
            }
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    public static int getMoutheX() {
        return Mouse.getX();
    }

    public static int getMoutheY() {
        return Display.getHeight() - Mouse.getY() - 1;
    }
}
