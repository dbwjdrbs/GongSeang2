package com.example.helloboard.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Address {

    @SerializedName("status")
    @Expose
    private Status status;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public static class Result {

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("code")
        @Expose
        private Code code;
        @SerializedName("region")
        @Expose
        private Region region;
        @SerializedName("land")
        @Expose
        private Land land;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Code getCode() {
            return code;
        }

        public void setCode(Code code) {
            this.code = code;
        }

        public Region getRegion() {
            return region;
        }

        public void setRegion(Region region) {
            this.region = region;
        }

        public Land getLand() {
            return land;
        }

        public void setLand(Land land) {
            this.land = land;
        }

        public static class Code {

            @SerializedName("id")
            @Expose
            private String id;
            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("mappingId")
            @Expose
            private String mappingId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getMappingId() {
                return mappingId;
            }

            public void setMappingId(String mappingId) {
                this.mappingId = mappingId;
            }

        }

        public static class Region {

            @SerializedName("area0")
            @Expose
            private Area0 area0;
            @SerializedName("area1")
            @Expose
            private Area1 area1;
            @SerializedName("area2")
            @Expose
            private Area2 area2;
            @SerializedName("area3")
            @Expose
            private Area3 area3;
            @SerializedName("area4")
            @Expose
            private Area4 area4;

            public Area0 getArea0() {
                return area0;
            }

            public void setArea0(Area0 area0) {
                this.area0 = area0;
            }

            public Area1 getArea1() {
                return area1;
            }

            public void setArea1(Area1 area1) {
                this.area1 = area1;
            }

            public Area2 getArea2() {
                return area2;
            }

            public void setArea2(Area2 area2) {
                this.area2 = area2;
            }

            public Area3 getArea3() {
                return area3;
            }

            public void setArea3(Area3 area3) {
                this.area3 = area3;
            }

            public Area4 getArea4() {
                return area4;
            }

            public void setArea4(Area4 area4) {
                this.area4 = area4;
            }

            public static class Area0 {

                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("coords")
                @Expose
                private Coords coords;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Coords getCoords() {
                    return coords;
                }

                public void setCoords(Coords coords) {
                    this.coords = coords;
                }

                public static class Coords {

                    @SerializedName("center")
                    @Expose
                    private Center center;

                    public Center getCenter() {
                        return center;
                    }

                    public void setCenter(Center center) {
                        this.center = center;
                    }

                    public static class Center {

                        @SerializedName("crs")
                        @Expose
                        private String crs;
                        @SerializedName("x")
                        @Expose
                        private Double x;
                        @SerializedName("y")
                        @Expose
                        private Double y;

                        public String getCrs() {
                            return crs;
                        }

                        public void setCrs(String crs) {
                            this.crs = crs;
                        }

                        public Double getX() {
                            return x;
                        }

                        public void setX(Double x) {
                            this.x = x;
                        }

                        public Double getY() {
                            return y;
                        }

                        public void setY(Double y) {
                            this.y = y;
                        }

                    }
                }
            }

            public static class Area1 {

                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("coords")
                @Expose
                private Coords_ coords;
                @SerializedName("alias")
                @Expose
                private String alias;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Coords_ getCoords() {
                    return coords;
                }

                public void setCoords(Coords_ coords) {
                    this.coords = coords;
                }

                public String getAlias() {
                    return alias;
                }

                public void setAlias(String alias) {
                    this.alias = alias;
                }

                public static class Coords_ {

                    @SerializedName("center")
                    @Expose
                    private Center_ center;

                    public Center_ getCenter() {
                        return center;
                    }

                    public void setCenter(Center_ center) {
                        this.center = center;
                    }

                    public static class Center_ {

                        @SerializedName("crs")
                        @Expose
                        private String crs;
                        @SerializedName("x")
                        @Expose
                        private Double x;
                        @SerializedName("y")
                        @Expose
                        private Double y;

                        public String getCrs() {
                            return crs;
                        }

                        public void setCrs(String crs) {
                            this.crs = crs;
                        }

                        public Double getX() {
                            return x;
                        }

                        public void setX(Double x) {
                            this.x = x;
                        }

                        public Double getY() {
                            return y;
                        }

                        public void setY(Double y) {
                            this.y = y;
                        }

                    }
                }
            }

            public static class Area2 {

                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("coords")
                @Expose
                private Coords__ coords;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Coords__ getCoords() {
                    return coords;
                }

                public void setCoords(Coords__ coords) {
                    this.coords = coords;
                }

                public static class Coords__ {

                    @SerializedName("center")
                    @Expose
                    private Center__ center;

                    public Center__ getCenter() {
                        return center;
                    }

                    public void setCenter(Center__ center) {
                        this.center = center;
                    }

                    public static class Center__ {

                        @SerializedName("crs")
                        @Expose
                        private String crs;
                        @SerializedName("x")
                        @Expose
                        private Double x;
                        @SerializedName("y")
                        @Expose
                        private Double y;

                        public String getCrs() {
                            return crs;
                        }

                        public void setCrs(String crs) {
                            this.crs = crs;
                        }

                        public Double getX() {
                            return x;
                        }

                        public void setX(Double x) {
                            this.x = x;
                        }

                        public Double getY() {
                            return y;
                        }

                        public void setY(Double y) {
                            this.y = y;
                        }

                    }
                }
            }

            public static class Area3 {

                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("coords")
                @Expose
                private Coords___ coords;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Coords___ getCoords() {
                    return coords;
                }

                public void setCoords(Coords___ coords) {
                    this.coords = coords;
                }

                public static class Coords___ {

                    @SerializedName("center")
                    @Expose
                    private Center___ center;

                    public Center___ getCenter() {
                        return center;
                    }

                    public void setCenter(Center___ center) {
                        this.center = center;
                    }

                    public static class Center___ {

                        @SerializedName("crs")
                        @Expose
                        private String crs;
                        @SerializedName("x")
                        @Expose
                        private Double x;
                        @SerializedName("y")
                        @Expose
                        private Double y;

                        public String getCrs() {
                            return crs;
                        }

                        public void setCrs(String crs) {
                            this.crs = crs;
                        }

                        public Double getX() {
                            return x;
                        }

                        public void setX(Double x) {
                            this.x = x;
                        }

                        public Double getY() {
                            return y;
                        }

                        public void setY(Double y) {
                            this.y = y;
                        }

                    }
                }
            }

            public static class Area4 {

                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("coords")
                @Expose
                private Coords____ coords;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Coords____ getCoords() {
                    return coords;
                }

                public void setCoords(Coords____ coords) {
                    this.coords = coords;
                }

                public static class Coords____ {

                    @SerializedName("center")
                    @Expose
                    private Center____ center;

                    public Center____ getCenter() {
                        return center;
                    }

                    public void setCenter(Center____ center) {
                        this.center = center;
                    }

                    public static class Center____ {

                        @SerializedName("crs")
                        @Expose
                        private String crs;
                        @SerializedName("x")
                        @Expose
                        private Double x;
                        @SerializedName("y")
                        @Expose
                        private Double y;

                        public String getCrs() {
                            return crs;
                        }

                        public void setCrs(String crs) {
                            this.crs = crs;
                        }

                        public Double getX() {
                            return x;
                        }

                        public void setX(Double x) {
                            this.x = x;
                        }

                        public Double getY() {
                            return y;
                        }

                        public void setY(Double y) {
                            this.y = y;
                        }

                    }
                }
            }
        }

        public static class Land {

            @SerializedName("type")
            @Expose
            private String type;
            @SerializedName("number1")
            @Expose
            private String number1;
            @SerializedName("number2")
            @Expose
            private String number2;
            @SerializedName("addition0")
            @Expose
            private Addition0 addition0;
            @SerializedName("addition1")
            @Expose
            private Addition1 addition1;
            @SerializedName("addition2")
            @Expose
            private Addition2 addition2;
            @SerializedName("addition3")
            @Expose
            private Addition3 addition3;
            @SerializedName("addition4")
            @Expose
            private Addition4 addition4;
            @SerializedName("coords")
            @Expose
            private Coords_____ coords;
            @SerializedName("name")
            @Expose
            private String name;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getNumber1() {
                return number1;
            }

            public void setNumber1(String number1) {
                this.number1 = number1;
            }

            public String getNumber2() {
                return number2;
            }

            public void setNumber2(String number2) {
                this.number2 = number2;
            }

            public Addition0 getAddition0() {
                return addition0;
            }

            public void setAddition0(Addition0 addition0) {
                this.addition0 = addition0;
            }

            public Addition1 getAddition1() {
                return addition1;
            }

            public void setAddition1(Addition1 addition1) {
                this.addition1 = addition1;
            }

            public Addition2 getAddition2() {
                return addition2;
            }

            public void setAddition2(Addition2 addition2) {
                this.addition2 = addition2;
            }

            public Addition3 getAddition3() {
                return addition3;
            }

            public void setAddition3(Addition3 addition3) {
                this.addition3 = addition3;
            }

            public Addition4 getAddition4() {
                return addition4;
            }

            public void setAddition4(Addition4 addition4) {
                this.addition4 = addition4;
            }

            public Coords_____ getCoords() {
                return coords;
            }

            public void setCoords(Coords_____ coords) {
                this.coords = coords;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public static class Addition0 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("value")
                @Expose
                private String value;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

            }

            public static class Addition1 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("value")
                @Expose
                private String value;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

            }

            public static class Addition2 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("value")
                @Expose
                private String value;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

            }

            public static class Addition3 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("value")
                @Expose
                private String value;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

            }

            public static class Addition4 {

                @SerializedName("type")
                @Expose
                private String type;
                @SerializedName("value")
                @Expose
                private String value;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

            }

            public static class Coords_____ {

                @SerializedName("center")
                @Expose
                private Center_____ center;

                public Center_____ getCenter() {
                    return center;
                }

                public void setCenter(Center_____ center) {
                    this.center = center;
                }

                public static class Center_____ {

                    @SerializedName("crs")
                    @Expose
                    private String crs;
                    @SerializedName("x")
                    @Expose
                    private Double x;
                    @SerializedName("y")
                    @Expose
                    private Double y;

                    public String getCrs() {
                        return crs;
                    }

                    public void setCrs(String crs) {
                        this.crs = crs;
                    }

                    public Double getX() {
                        return x;
                    }

                    public void setX(Double x) {
                        this.x = x;
                    }

                    public Double getY() {
                        return y;
                    }

                    public void setY(Double y) {
                        this.y = y;
                    }

                }
            }
        }
    }

    public static class Status {

        @SerializedName("code")
        @Expose
        private Integer code;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("message")
        @Expose
        private String message;

        public Integer getCode() {
            return code;
        }

        public void setCode(Integer code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
}

