package com.example.fyp_app.posedetector;

import com.example.fyp_app.posedetector.YogaPose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YogaProgramBeginner {
    List<YogaPose> YogaProgram = new ArrayList<YogaPose>();

    public YogaProgramBeginner(){
        addYoga(new YogaPose("Namaskarasana","video",YogaPose.addBodyPart(35,17,170,170 ,35,17,170,170),new String[]{"good", "perfect"}));
        addYoga(new YogaPose("Right Vrikshasana","video",YogaPose.addBodyPart(140,140,160,165 ,140 ,140,140,17),new String[]{"good", "perfect"}));
        addYoga(new YogaPose("Left Vrikshasana","video",YogaPose.addBodyPart(150,140,120,17,150,140,160,165 ),new String[]{"good", "perfect"}));
        addYoga(new YogaPose("Left Virabhadrasana","video",YogaPose.addBodyPart(140,140,90,80,140,140,120,140 ),new String[]{"good", "perfect"}));
        //addYoga(new YogaPose("Right Virabhadrasana","video",YogaPose.addBodyPart(15,11,23,160,26,24,27,120 ),new String[]{"good", "perfect"}));
        addYoga(new YogaPose("Ashva Sanchalan Asana","video",YogaPose.addBodyPart(150,20,40,40,150,20,140,120 ),new String[]{"good", "perfect"}));
        addYoga(new YogaPose("Adho Mukha Svanasana","video",YogaPose.addBodyPart(140,140,60,160 ,140,140,70,160),new String[]{"good", "perfect"}));

    }
    public void addYoga(YogaPose yogaPose){
        YogaProgram.add(yogaPose);
    }

    public List<YogaPose> getProgram(){
        return YogaProgram;
    }
}
