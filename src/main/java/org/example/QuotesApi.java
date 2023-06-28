package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotesApi {

        private String content;
        private String author;
        private String length;
        private String[] tags;

        public String getAuthor() {
            return author;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public void setTags(String[] tags) {
            this.tags = tags;
        }

        public String[] getTags() {
            return tags;
        }

        public String getContent() {
            return content;
        }

        public String getLength() {
            return length;
        }

        private String getPrintTags(){
            String result ="";
            for (int i =0; i<this.tags.length;i++){
                if (i > 0 && i<=this.tags.length-2 ){
                    result+=",";
                }
                result+= this.tags[i];
            }

            result +=".";
            return result;
        }

        public String toString(){
            return "Author: "+ this.author + "\nContent: "+this.content+"\nTags: " + getPrintTags()+"\n";
        }


    }

