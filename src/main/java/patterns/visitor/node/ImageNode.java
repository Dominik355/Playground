package patterns.visitor.node;

import patterns.visitor.visitor.Visitor;

public class ImageNode implements Node {
    String fileName;
    byte[] imageData;
    int height, width;
    ImageCaptionNode caption;

    public ImageNode(String fileName, String caption) {
        load(fileName);
        this.caption = new ImageCaptionNode(caption);
    }

    private void load(String fileName) {
        // dummy for now
        this.fileName = fileName;
        height = width = 64;
    }

    @Override
    public void accept(Visitor v) {
        v.visitImageNode(this);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public ImageCaptionNode getCaption() {
        return caption;
    }

    public void setCaption(ImageCaptionNode caption) {
        this.caption = caption;
    }
}
