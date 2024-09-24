package com.example.helloboard.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Navigation {

    @SerializedName("code")
    @Expose
    private Integer code;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("currentDateTime")
    @Expose
    private String currentDateTime;
    @SerializedName("route")
    @Expose
    private Route route;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(String currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public static class Route {

        @SerializedName("traoptimal")
        @Expose
        private List<Traoptimal> traoptimal = null;

        public List<Traoptimal> getTraoptimal() {
            return traoptimal;
        }

        public void setTraoptimal(List<Traoptimal> traoptimal) {
            this.traoptimal = traoptimal;
        }

        public static class Traoptimal {

            @SerializedName("summary")
            @Expose
            private Summary summary;
            @SerializedName("path")
            @Expose
            private List<List<Double>> path = null;
            @SerializedName("section")
            @Expose
            private List<Section> section = null;
            @SerializedName("guide")
            @Expose
            private List<Guide> guide = null;

            public Summary getSummary() {
                return summary;
            }

            public void setSummary(Summary summary) {
                this.summary = summary;
            }

            public List<List<Double>> getPath() {
                return path;
            }

            public void setPath(List<List<Double>> path) {
                this.path = path;
            }

            public List<Section> getSection() {
                return section;
            }

            public void setSection(List<Section> section) {
                this.section = section;
            }

            public List<Guide> getGuide() {
                return guide;
            }

            public void setGuide(List<Guide> guide) {
                this.guide = guide;
            }

            public static class Summary {

                @SerializedName("start")
                @Expose
                private Start start;
                @SerializedName("goal")
                @Expose
                private Goal goal;
                @SerializedName("distance")
                @Expose
                private Integer distance;
                @SerializedName("duration")
                @Expose
                private Integer duration;
                @SerializedName("departureTime")
                @Expose
                private String departureTime;
                @SerializedName("bbox")
                @Expose
                private List<List<Double>> bbox = null;
                @SerializedName("tollFare")
                @Expose
                private Integer tollFare;
                @SerializedName("taxiFare")
                @Expose
                private Integer taxiFare;
                @SerializedName("fuelPrice")
                @Expose
                private Integer fuelPrice;

                public Start getStart() {
                    return start;
                }

                public void setStart(Start start) {
                    this.start = start;
                }

                public Goal getGoal() {
                    return goal;
                }

                public void setGoal(Goal goal) {
                    this.goal = goal;
                }

                public Integer getDistance() {
                    return distance;
                }

                public void setDistance(Integer distance) {
                    this.distance = distance;
                }

                public Integer getDuration() {
                    return duration;
                }

                public void setDuration(Integer duration) {
                    this.duration = duration;
                }

                public String getDepartureTime() {
                    return departureTime;
                }

                public void setDepartureTime(String departureTime) {
                    this.departureTime = departureTime;
                }

                public List<List<Double>> getBbox() {
                    return bbox;
                }

                public void setBbox(List<List<Double>> bbox) {
                    this.bbox = bbox;
                }

                public Integer getTollFare() {
                    return tollFare;
                }

                public void setTollFare(Integer tollFare) {
                    this.tollFare = tollFare;
                }

                public Integer getTaxiFare() {
                    return taxiFare;
                }

                public void setTaxiFare(Integer taxiFare) {
                    this.taxiFare = taxiFare;
                }

                public Integer getFuelPrice() {
                    return fuelPrice;
                }

                public void setFuelPrice(Integer fuelPrice) {
                    this.fuelPrice = fuelPrice;
                }

                public static class Start {

                    @SerializedName("location")
                    @Expose
                    private List<Double> location = null;

                    public List<Double> getLocation() {
                        return location;
                    }

                    public void setLocation(List<Double> location) {
                        this.location = location;
                    }

                }

                public static class Goal {

                    @SerializedName("location")
                    @Expose
                    private List<Double> location = null;
                    @SerializedName("dir")
                    @Expose
                    private Integer dir;

                    public List<Double> getLocation() {
                        return location;
                    }

                    public void setLocation(List<Double> location) {
                        this.location = location;
                    }

                    public Integer getDir() {
                        return dir;
                    }

                    public void setDir(Integer dir) {
                        this.dir = dir;
                    }

                }
            }

            public static class Guide {

                @SerializedName("pointIndex")
                @Expose
                private Integer pointIndex;
                @SerializedName("type")
                @Expose
                private Integer type;
                @SerializedName("instructions")
                @Expose
                private String instructions;
                @SerializedName("distance")
                @Expose
                private Integer distance;
                @SerializedName("duration")
                @Expose
                private Integer duration;

                public Integer getPointIndex() {
                    return pointIndex;
                }

                public void setPointIndex(Integer pointIndex) {
                    this.pointIndex = pointIndex;
                }

                public Integer getType() {
                    return type;
                }

                public void setType(Integer type) {
                    this.type = type;
                }

                public String getInstructions() {
                    return instructions;
                }

                public void setInstructions(String instructions) {
                    this.instructions = instructions;
                }

                public Integer getDistance() {
                    return distance;
                }

                public void setDistance(Integer distance) {
                    this.distance = distance;
                }

                public Integer getDuration() {
                    return duration;
                }

                public void setDuration(Integer duration) {
                    this.duration = duration;
                }

            }

            public static class Section {

                @SerializedName("pointIndex")
                @Expose
                private Integer pointIndex;
                @SerializedName("pointCount")
                @Expose
                private Integer pointCount;
                @SerializedName("distance")
                @Expose
                private Integer distance;
                @SerializedName("name")
                @Expose
                private String name;
                @SerializedName("congestion")
                @Expose
                private Integer congestion;
                @SerializedName("speed")
                @Expose
                private Integer speed;

                public Integer getPointIndex() {
                    return pointIndex;
                }

                public void setPointIndex(Integer pointIndex) {
                    this.pointIndex = pointIndex;
                }

                public Integer getPointCount() {
                    return pointCount;
                }

                public void setPointCount(Integer pointCount) {
                    this.pointCount = pointCount;
                }

                public Integer getDistance() {
                    return distance;
                }

                public void setDistance(Integer distance) {
                    this.distance = distance;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public Integer getCongestion() {
                    return congestion;
                }

                public void setCongestion(Integer congestion) {
                    this.congestion = congestion;
                }

                public Integer getSpeed() {
                    return speed;
                }

                public void setSpeed(Integer speed) {
                    this.speed = speed;
                }

            }
        }
    }
}
