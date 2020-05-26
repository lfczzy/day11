package com.ztcx.videoplay.been;

import java.util.List;

public class Classify {
    private String title;
    private List<Reclassify> reclassify;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Reclassify> getReclassify() {
        return reclassify;
    }

    public void setReclassify(List<Reclassify> reclassify) {
        this.reclassify = reclassify;
    }

    public static class Reclassify {
        private String name;
        private int logo;
        private String id;

        public Reclassify(String name, int logo, String id) {
            this.name = name;
            this.logo = logo;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getLogo() {
            return logo;
        }

        public void setLogo(int logo) {
            this.logo = logo;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
