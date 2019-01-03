package com.rusanov.game.Shooter.graphics;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Window {
    public Window(int width, int height, String name, boolean isFullscreen) {
        try {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle(name);
            Display.create();
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            Display.destroy();
            System.exit(0);
        }

        setDisplayMode(width, height, isFullscreen);
        createMatrixOrtho();

        GL11.glClearColor(0.1f, 0.8f, 0.8f, 1.0f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void createMatrixOrtho() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 1, -1);
        GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
    }

    private void setDisplayMode(int width, int height, boolean isFullscreen) {
        if ((Display.getDisplayMode().getWidth() == width) &&
                (Display.getDisplayMode().getHeight() == height) &&
                (Display.isFullscreen() == isFullscreen)) {
            return;
        }

        try {
            DisplayMode targetDisplayMode = null;

            if (isFullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;

                for (DisplayMode current : modes) {
                    if ((current.getWidth() == width) && (current.getHeight() == height)) {
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
                            if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }

                        // Checking the compatibility with the monitor
                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
                                (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } else {
                targetDisplayMode = new DisplayMode(width,height);
            }

            if (targetDisplayMode == null) {
                System.out.println("Failed to find value mode: " + width + "x" + height);
                return;
            }

            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(isFullscreen);
        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + isFullscreen + e.getMessage());
        }
    }
}
