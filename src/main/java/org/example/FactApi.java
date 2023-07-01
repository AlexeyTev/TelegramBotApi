package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class FactApi {

    private String text;
    private String source;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return
                "The Fact is :'" + text + '\'' +
                ", source : '" + source + '\'';
    }
}
