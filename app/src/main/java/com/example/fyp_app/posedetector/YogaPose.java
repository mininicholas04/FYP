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

    public static HashMap addBodyPart(int firstJoint1, int secondJoint1, int thirdJoint1, int angle1 , int firstJoint2, int secondJoint2 , int thirdJoint2, int angle2 ){
        HashMap<String, Integer> BodyPart = new HashMap();

        BodyPart.put("firstjoint1", firstJoint1);
        BodyPart.put("secondjoint1", secondJoint1);
        BodyPart.put("thirdjoint1", thirdJoint1);
        BodyPart.put("angle1", angle1);
        BodyPart.put("firstjoint2", firstJoint2);
        BodyPart.put("secondjoint2", secondJoint2);
        BodyPart.put("thirdjoint2", thirdJoint2);
        BodyPart.put("angle2", angle2);

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
