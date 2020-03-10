package com.example.fieldglass;

public class Price {
        private String Service;
        private String Cost;
        private String Per;

        //object class that stores the prices of the work
        public Price(String service, String cost, String per) {
            Service = service;
            Cost = cost;
            Per = per;
        }

        public String getService() {
            return Service;
        }

        public void setService(String service) {
            this.Service = service;
        }

        public String getCost() {
            return Cost;
        }

        public void setCost(String cost) {
            this.Cost = cost;
        }

        public String getPer() {
            return Per;
        }

        public void setPer(String per) {
            this.Per = per;
        }

}
