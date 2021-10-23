package com.bramerlabs.engine3D.gui;

import com.bramerlabs.engine3D.math.matrix.Matrix4f;
import com.bramerlabs.engine3D.math.vector.Vector3f;

public class GuiObject {

    public GuiMesh mesh;
    public GuiSelectionBox selectionBox;

    public Vector3f position;
    public Vector3f rotation;
    public Vector3f scale;

    public Matrix4f model;

    private OnClickListener onClickListener;

    public GuiObject(GuiMesh mesh, GuiSelectionBox selectionBox, Vector3f position, Vector3f rotation, Vector3f scale) {
        this.mesh = mesh;
        this.selectionBox = selectionBox;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;

        this.model = Matrix4f.transform(position, rotation, scale);

        this.onClickListener = buttonCode -> false;
    }

    public GuiObject(GuiMesh mesh, GuiSelectionBox selectionBox) {
        this.mesh = mesh;
        this.selectionBox = selectionBox;
        this.position = new Vector3f(1, 1, 1);
        this.rotation = new Vector3f(1, 1, 1);
        this.scale = new Vector3f(1, 1, 1);

        this.model = Matrix4f.transform(position, rotation, scale);

        this.onClickListener = buttonCode -> false;
    }

    public boolean performClick(int buttonCode) {
        return this.onClickListener.onClick(buttonCode);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public OnClickListener removeOnClickListener() {
        OnClickListener listener = this.onClickListener;
        this.onClickListener = buttonCode -> false;
        return listener;
    }

    public boolean inBounds(float mouseX, float mouseY) {
        return this.selectionBox.inBounds(mouseX, mouseY);
    }

    public interface OnClickListener {
        boolean onClick(int buttonCode);
    }

}
