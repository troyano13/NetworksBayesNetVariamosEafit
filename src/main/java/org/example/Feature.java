package org.example;

public class Feature {


    private String label;
    private String labelParent;
    private String type;
    private String id;
    private String x;
    private String y;
    private String width;
    private String height;
    private String source;
    private String target;


    public Feature(String label, String labelParent, String type, String id, String x, String y, String width, String height, String source, String target) {
        this.label = label;
        this.labelParent = labelParent;
        this.type = type;
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.source = source;
        this.target = target;
    }

    // getters y setters
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLabelParent() {
        return labelParent;
    }

    public void setLabelParent(String labelParent) {
        this.labelParent = labelParent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }


}


