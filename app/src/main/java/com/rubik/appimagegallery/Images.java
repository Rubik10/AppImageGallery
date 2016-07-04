package com.rubik.appimagegallery;

    /**
     * Created by Rubik on 1/7/16.
     */
    public class Images {

        private int idImage;
        private String name;
        private String url;
        boolean selected;


        //Constructor
        public Images (){}

        public Images(String _name, String _url) {
            this.name = _name;
            this.url = _url;
        }

        public Images(int id , String _name, String _url) {
            this.idImage=id;
            this.name = _name;
            this.url = _url;
        }

                    /* GETTERS & SETTERS */

        public int getIdImage() {
            return idImage;
        }

        public void setIdImage(int idImage) {
            this.idImage = idImage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }
    }
