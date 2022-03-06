package com.example.fyp_app.posedetector;


import java.util.HashMap;


public class YogaPose {
    private String name;
    private String video;
    private String[] Speech ;
    private HashMap BodyPart;

    public YogaPose(String name, String video, HashMap bodyPart, String[] speech){
        this.name = name;
        this.video = video;
        this.BodyPart = bodyPart;
        this.Speech = speech;
    }

    public static HashMap addBodyPart(int leftElbowAngle, int leftShoulderAngle, int leftHipAngle, int leftKneeAngle , int rightElbowAngle, int rightShoulderAngle , int rightHipAngle, int rightKneeAngle ){
        HashMap<String, Integer> BodyPart = new HashMap();

        BodyPart.put("leftElbowAngle", leftElbowAngle);
        BodyPart.put("leftShoulderAngle", leftShoulderAngle);
        BodyPart.put("leftHipAngle", leftHipAngle);
        BodyPart.put("leftKneeAngle", leftKneeAngle);
        BodyPart.put("rightElbowAngle", rightElbowAngle);
        BodyPart.put("rightShoulderAngle", rightShoulderAngle);
        BodyPart.put("rightHipAngle", rightHipAngle);
        BodyPart.put("rightKneeAngle", rightKneeAngle);

        return BodyPart;
    }


    public String getName(){
        return name;
    }
    public String getVideo(){
        return video;
    }
    public HashMap getBodyPart(){
        return BodyPart;
    }
    public String[] getSpeech(){
        return Speech;
    }
}
