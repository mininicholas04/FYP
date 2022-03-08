package com.example.fyp_app.posedetector;

import com.example.fyp_app.posedetector.YogaPose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class YogaProgramBeginner {
    List<YogaPose> YogaProgram = new ArrayList<YogaPose>();

    public YogaProgramBeginner(){
        addYoga(new YogaPose("Namaskarasana","video",YogaPose.addBodyPart(35,17,170,170 ,35,17,170,170),new String[]{"good", "perfect"}));
        //addYoga(new YogaPose("Right Vrikshasana","video",YogaPose.addBodyPart(170,150,170,165 ,170 ,155,110,10),new String[]{"good", "perfect"}));
        //addYoga(new YogaPose("Left Vrikshasana","video",YogaPose.addBodyPart(23,25,31,30,16,24,28,160 ),new String[]{"good", "perfect"}));
        addYoga(new YogaPose("Left Virabhadrasana","video",YogaPose.addBodyPart(150,160,120,150,160,160,100,90 ),new String[]{"good", "perfect"}));
        //addYoga(new YogaPose("Right Virabhadrasana","video",YogaPose.addBodyPart(15,11,23,160,26,24,27,120 ),new String[]{"good", "perfect"}));
        //addYoga(new YogaPose("Ashva Sanchalan Asana","video",YogaPose.addBodyPart(25,23,28,160,12,24,28,130 ),new String[]{"good", "perfect"}));
        addYoga(new YogaPose("Adho Mukha Svanasana","video",YogaPose.addBodyPart(140,150,70,170 ,140,150,75,165),new String[]{"good", "perfect"}));

    }
    public void addYoga(YogaPose yogaPose){
        YogaProgram.add(yogaPose);
    }

    public List<YogaPose> getProgram(){
        return YogaProgram;
    }
}
