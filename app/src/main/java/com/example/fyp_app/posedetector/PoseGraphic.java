
package com.example.fyp_app.posedetector;

import static androidx.camera.core.CameraX.getContext;
import static java.lang.Math.atan2;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.fyp_app.Breath;
import com.example.fyp_app.BreathCompleted;
import com.example.fyp_app.LivePreviewActivity;
import com.example.fyp_app.R;
import com.example.fyp_app.posedetector.YogaPose;
import com.example.fyp_app.posedetector.YogaProgramBeginner;
import com.google.android.gms.common.api.Api;
import com.google.common.primitives.Ints;
import com.google.mlkit.vision.common.PointF3D;
import com.example.fyp_app.EntryChoiceActivity;
import com.example.fyp_app.GraphicOverlay;
import com.example.fyp_app.GraphicOverlay.Graphic;
import com.example.fyp_app.YogaCompletedActivity;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseLandmark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/** Draw the detected pose in preview. */
public class PoseGraphic extends Graphic {

  private static final float DOT_RADIUS = 5.0f;
  private static final float IN_FRAME_LIKELIHOOD_TEXT_SIZE = 30.0f;
  private static final float STROKE_WIDTH = 10.0f;
  private static final float POSE_CLASSIFICATION_TEXT_SIZE = 60.0f;

  private final Pose pose;
  private final boolean showInFrameLikelihood;
  private final boolean visualizeZ;
  private final boolean rescaleZForVisualization;
  private float zMin = Float.MAX_VALUE;
  private float zMax = Float.MIN_VALUE;
  TextToSpeech t1;
  YogaProgramBeginner yogaProgramBeginner;
  private List<YogaPose> yogaArray = new ArrayList<YogaPose>();
  CountDownTimer timer;
  WebView view ;
  Activity activity;
  TextView yogaCount;
  static int yogacount = 600;

  private final List<String> poseClassification;
  private final Paint classificationTextPaint;
  private final Paint leftPaint;
  private final Paint rightPaint;
  private final Paint whitePaint;
  private final Paint testpaint;
  int Left = 0 ;
  int Right = 0 ;
  public static int l = 0;
  static int count = 0 ;

  public PoseGraphic(
          GraphicOverlay overlay,
          Pose pose,
          boolean showInFrameLikelihood,
          boolean visualizeZ,
          boolean rescaleZForVisualization,
          List<String> poseClassification,
          TextToSpeech t1,
          YogaProgramBeginner yogaProgramBeginner,
          WebView view,
          Activity activity) {
    super(overlay);
    this.pose = pose;
    this.showInFrameLikelihood = showInFrameLikelihood;
    this.visualizeZ = visualizeZ;
    this.rescaleZForVisualization = rescaleZForVisualization;
    this.t1 = t1;
    this.yogaProgramBeginner = yogaProgramBeginner;
    this.view = view;
    this.activity = activity;


    this.poseClassification = poseClassification;
    classificationTextPaint = new Paint();
    classificationTextPaint.setColor(Color.WHITE);
    classificationTextPaint.setTextSize(POSE_CLASSIFICATION_TEXT_SIZE);
    classificationTextPaint.setShadowLayer(5.0f, 0f, 0f, Color.BLACK);

    testpaint = new Paint();
    testpaint.setColor(Color.rgb(0, 0, 0));
    testpaint.setAlpha(0);
    whitePaint = new Paint();
    whitePaint.setStrokeWidth(STROKE_WIDTH);
    whitePaint.setColor(Color.WHITE);
    whitePaint.setTextSize(IN_FRAME_LIKELIHOOD_TEXT_SIZE);
    leftPaint = new Paint();
    leftPaint.setStrokeWidth(STROKE_WIDTH);
    leftPaint.setColor(Color.GREEN);
    rightPaint = new Paint();
    rightPaint.setStrokeWidth(STROKE_WIDTH);
    rightPaint.setColor(Color.GREEN);
  }

    @Override
  public void draw(Canvas canvas) {
      List<PoseLandmark> landmarks = pose.getAllPoseLandmarks();
      if (landmarks.isEmpty()) {
        return;
      }
      if(l == 2){
          Intent intent = new Intent(getApplicationContext(), BreathCompleted.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          getApplicationContext().startActivity(intent);
      }
      // Draw pose classification text.
      float classificationX = POSE_CLASSIFICATION_TEXT_SIZE * 0.5f;
      for (int i = 0; i < poseClassification.size(); i++) {
        float classificationY = (canvas.getHeight() - POSE_CLASSIFICATION_TEXT_SIZE * 1.5f
                * (poseClassification.size() - i));
        canvas.drawText(
                poseClassification.get(i),
                classificationX,
                classificationY,
                classificationTextPaint);
      }

      // Draw all the points
      for (PoseLandmark landmark : landmarks) {
        if (landmark == pose.getPoseLandmark(PoseLandmark.NOSE) ||
                landmark == pose.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER) ||
                landmark == pose.getPoseLandmark(PoseLandmark.LEFT_EYE) ||
                landmark == pose.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER) ||
                landmark == pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER) ||
                landmark == pose.getPoseLandmark(PoseLandmark.RIGHT_EYE) ||
                landmark == pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_OUTER) ||
                landmark == pose.getPoseLandmark(PoseLandmark.LEFT_EAR) ||
                landmark == pose.getPoseLandmark(PoseLandmark.RIGHT_EAR) ||
                landmark == pose.getPoseLandmark(PoseLandmark.LEFT_MOUTH) ||
                landmark == pose.getPoseLandmark(PoseLandmark.RIGHT_MOUTH)
        ) {
          drawPoint(canvas, landmark, testpaint);
        } else {
          drawPoint(canvas, landmark, whitePaint);
        }

        if (visualizeZ && rescaleZForVisualization) {
          zMin = min(zMin, landmark.getPosition3D().getZ());
          zMax = max(zMax, landmark.getPosition3D().getZ());
        }
      }
      drawPoint(canvas, pose.getPoseLandmark(PoseLandmark.NOSE), testpaint);
      drawPoint(canvas, pose.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER), testpaint);
      drawPoint(canvas, pose.getPoseLandmark(PoseLandmark.LEFT_EYE), testpaint);
      drawPoint(canvas, pose.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER), testpaint);
      drawPoint(canvas, pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER), testpaint);

      PoseLandmark nose = pose.getPoseLandmark(PoseLandmark.NOSE);
      PoseLandmark lefyEyeInner = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER);
      PoseLandmark lefyEye = pose.getPoseLandmark(PoseLandmark.LEFT_EYE);
      PoseLandmark leftEyeOuter = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER);
      PoseLandmark rightEyeInner = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER);
      PoseLandmark rightEye = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE);
      PoseLandmark rightEyeOuter = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_OUTER);
      PoseLandmark leftEar = pose.getPoseLandmark(PoseLandmark.LEFT_EAR);
      PoseLandmark rightEar = pose.getPoseLandmark(PoseLandmark.RIGHT_EAR);
      PoseLandmark leftMouth = pose.getPoseLandmark(PoseLandmark.LEFT_MOUTH);
      PoseLandmark rightMouth = pose.getPoseLandmark(PoseLandmark.RIGHT_MOUTH);

      PoseLandmark leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER);
      PoseLandmark rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER);
      PoseLandmark leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW);
      PoseLandmark rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW);
      PoseLandmark leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST);
      PoseLandmark rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST);
      PoseLandmark leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP);
      PoseLandmark rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP);
      PoseLandmark leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE);
      PoseLandmark rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE);
      PoseLandmark leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE);
      PoseLandmark rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE);

      PoseLandmark leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY);
      PoseLandmark rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY);
      PoseLandmark leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX);
      PoseLandmark rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX);
      PoseLandmark leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB);
      PoseLandmark rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB);
      PoseLandmark leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL);
      PoseLandmark rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL);
      PoseLandmark leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX);
      PoseLandmark rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX);

      yogaArray = yogaProgramBeginner.getProgram();
      Log.v("Test " , l + "");
      YogaPose yoga = yogaArray.get(l);
      Log.v("Test 1" , l + "");
      int firstjoint1 = (int)yoga.getBodyPart().get("firstjoint1");
      int secondjoint1 =(int) yoga.getBodyPart().get("secondjoint1");
      int thirdjoint1 = (int)yoga.getBodyPart().get("thirdjoint1");
      int angle1 = (int)yoga.getBodyPart().get("angle1");

      int firstjoint2 = (int)yoga.getBodyPart().get("firstjoint2");
      int secondjoint2 =(int) yoga.getBodyPart().get("secondjoint2");
      int thirdjoint2 = (int)yoga.getBodyPart().get("thirdjoint2");
      int angle2 = (int)yoga.getBodyPart().get("angle2");
      String good = yoga.getSpeech()[0];
      String perfect = yoga.getSpeech()[1];

      Left = getAngle(pose.getPoseLandmark(firstjoint1), pose.getPoseLandmark(secondjoint1), pose.getPoseLandmark(thirdjoint1));
      Right = getAngle(pose.getPoseLandmark(firstjoint2), pose.getPoseLandmark(secondjoint2), pose.getPoseLandmark(thirdjoint2));


      if(l < yogaArray.size()){
          if(Left>=angle1 && angle1+30 >= Left && Right >= angle2 && angle2+30 >= Right){
              drawArcLeft(canvas, pose.getPoseLandmark(firstjoint1), pose.getPoseLandmark(secondjoint1), pose.getPoseLandmark(thirdjoint1), leftPaint, Left);
              drawArcRight(canvas, pose.getPoseLandmark(firstjoint2), pose.getPoseLandmark(secondjoint2), pose.getPoseLandmark(thirdjoint2), rightPaint, Right);
              timer =  new CountDownTimer(1000, 1000) {

                  public void onTick(long millisUntilFinished) {
                      if(Left>=angle1 && angle1+30 >= Left && Right >= angle2 && angle2+30 >= Right) {
                          count ++ ;

                          yogaCount = activity.findViewById(R.id.yogaCount);
                          activity.runOnUiThread(new Runnable(){
                              @Override
                              public void run() {
                                  yogaCount.setText(Integer.toString((int) Math.ceil(yogacount/10)));
                              }
                          });
                          yogacount--;
                          if(!t1.isSpeaking()){
                              t1.speak(perfect, TextToSpeech.QUEUE_FLUSH, null,null);
                              t1.playSilentUtterance(4000,TextToSpeech.QUEUE_ADD,null);
                          }
                          if(count ==600 ){
                              if(l < yogaArray.size()){
                              l++;
                                  activity.runOnUiThread(new Runnable(){
                                      @Override
                                      public void run() {
                                          view.loadUrl("file:///android_asset/yogapose"+l+".jpg");

                                      }
                                  });
                              }
                          }
                          Log.v("Cancel ", count+"");
                      }
                      else{
                          count = 0 ;
                          yogacount=600;
                      }
                  }

                  public void onFinish() {
                      /*if(angle1 <= Left && angle2 <= Right){
                          Log.v("Finish ", "pass");
                          if(l < 1){l++;}
                      }
                      else{
                          cancel();
                      }
                    */
                  }
              }.start();

          }
          else if(Left>=angle1 && angle1+30 >= Left && Right <= angle2){
              leftPaint.setColor(Color.GREEN);
              rightPaint.setColor(Color.RED);
              drawArcLeft(canvas, pose.getPoseLandmark(firstjoint1), pose.getPoseLandmark(secondjoint1), pose.getPoseLandmark(thirdjoint1), leftPaint, Left);
              drawArcRight(canvas, pose.getPoseLandmark(firstjoint2), pose.getPoseLandmark(secondjoint2), pose.getPoseLandmark(thirdjoint2), rightPaint, Right);
              count =0 ;
              yogacount=600;
          }
          else if(angle1>= Left && Right >= angle2 && angle2+30 > Right){
              leftPaint.setColor(Color.RED);
              rightPaint.setColor(Color.GREEN);
              drawArcLeft(canvas, pose.getPoseLandmark(firstjoint1), pose.getPoseLandmark(secondjoint1), pose.getPoseLandmark(thirdjoint1), leftPaint, Left);
              drawArcRight(canvas, pose.getPoseLandmark(firstjoint2), pose.getPoseLandmark(secondjoint2), pose.getPoseLandmark(thirdjoint2), rightPaint, Right);
              count =0 ;
              yogacount=600;
          }
          else {
              leftPaint.setColor(Color.RED);
              rightPaint.setColor(Color.RED);
              drawArcLeft(canvas, pose.getPoseLandmark(firstjoint1), pose.getPoseLandmark(secondjoint1), pose.getPoseLandmark(thirdjoint1), leftPaint, Left);
              drawArcRight(canvas, pose.getPoseLandmark(firstjoint2), pose.getPoseLandmark(secondjoint2), pose.getPoseLandmark(thirdjoint2), rightPaint, Right);
              if(!t1.isSpeaking()){
                  t1.speak(good, TextToSpeech.QUEUE_FLUSH, null,null);
                  t1.playSilentUtterance(4000,TextToSpeech.QUEUE_ADD,null);
              }
              count =0 ;
              yogacount=600;

          }

              Log.v("Angle " , angle1 +""+ angle2 + "");
              Log.v("Left Right " , Left +""+ Right + "");
              Log.v("Test 3" , l +"");
          }





      //new Thread(new Runnable() {
      //@Override
      //public void run() {
      // try {
      //  check(LeftHipAngle);
      //} catch (InterruptedException e) {
      //  e.printStackTrace();
      //}
      //}
      //}).start();
    };

  static int getAngle(PoseLandmark firstPoint, PoseLandmark midPoint, PoseLandmark lastPoint) {
    double result =
            Math.toDegrees(
                    atan2(lastPoint.getPosition().y - midPoint.getPosition().y,
                            lastPoint.getPosition().x - midPoint.getPosition().x)
                            - atan2(firstPoint.getPosition().y - midPoint.getPosition().y,
                            firstPoint.getPosition().x - midPoint.getPosition().x));
    result = Math.round(Math.abs(result)); // Angle should never be negative
    if (result > 180) {
      result = (360 - result); // Always get the acute representation of the angle
    }
    return (int)result;
  }
    void drawPoint(Canvas canvas, PoseLandmark landmark, Paint paint) {
    PointF3D point = landmark.getPosition3D();
    //maybeUpdatePaintColor(paint, canvas, point.getZ());
    canvas.drawCircle(translateX(point.getX()), translateY(point.getY()), DOT_RADIUS, paint);
  }

  void drawLine(Canvas canvas, PoseLandmark startLandmark, PoseLandmark endLandmark, Paint paint) {
    PointF3D start = startLandmark.getPosition3D();
    PointF3D end = endLandmark.getPosition3D();

    // Gets average z for the current body line
    float avgZInImagePixel = (start.getZ() + end.getZ()) / 2;
    //maybeUpdatePaintColor(paint, canvas, avgZInImagePixel);

    canvas.drawLine(
          translateX(start.getX()),
          translateY(start.getY()),
          translateX(end.getX()),
          translateY(end.getY()),
          paint);
  }

   void drawArcLeft(Canvas canvas, PoseLandmark startLandmark, PoseLandmark controlLandmark , PoseLandmark endLandmark, Paint paint, int angle){
     PointF3D start = startLandmark.getPosition3D();
     PointF3D control = controlLandmark.getPosition3D();
     PointF3D end = endLandmark.getPosition3D();
     Path p = new Path();
     p.moveTo(translateX(start.getX()), translateY(start.getY()));
     p.quadTo(translateX(control.getX()) + 200, ((translateY(end.getY()) - translateY(start.getY()))/2) + translateY(start.getY()), translateX(end.getX()), translateY(end.getY()) );
     paint.setStyle(Paint.Style.STROKE);
     paint.setStrokeWidth(5f);
     paint.setStrokeCap(Paint.Cap.ROUND);
     canvas.drawPath(p, paint);
     paint.setAlpha(150);
     paint.setColor(Color.BLACK);
     paint.setStyle(Paint.Style.FILL);
     paint.setTextSize(25);
     canvas.drawText(String.valueOf(angle)+"", translateX(control.getX()) +90, ((translateY(end.getY()) - translateY(start.getY()))/2) + translateY(start.getY()) ,paint);
   }
  void drawArcRight(Canvas canvas, PoseLandmark startLandmark, PoseLandmark controlLandmark , PoseLandmark endLandmark, Paint paint, int angle){
    PointF3D start = startLandmark.getPosition3D();
    PointF3D control = controlLandmark.getPosition3D();
    PointF3D end = endLandmark.getPosition3D();
    Path p = new Path();
    p.moveTo(translateX(start.getX()), translateY(start.getY()));
    p.quadTo(translateX(control.getX()) - 200, ((translateY(end.getY()) - translateY(start.getY()))/2) + translateY(start.getY()), translateX(end.getX()), translateY(end.getY()) );
    paint.setStyle(Paint.Style.STROKE);
    paint.setStrokeWidth(5f);
    paint.setStrokeCap(Paint.Cap.ROUND);
    canvas.drawPath(p, paint);
    paint.setAlpha(150);
    paint.setColor(Color.BLACK);
    paint.setStyle(Paint.Style.FILL);
    paint.setTextSize(25);
    canvas.drawText(String.valueOf(angle)+"", translateX(control.getX()) -  90, ((translateY(end.getY()) - translateY(start.getY()))/2) + translateY(start.getY()) ,paint);
  }




  private void maybeUpdatePaintColor(Paint paint, Canvas canvas, float zInImagePixel) {
    if (!visualizeZ) {
      return;
    }

    // When visualizeZ is true, sets up the paint to different colors based on z values.
    // Gets the range of z value.
    float zLowerBoundInScreenPixel;
    float zUpperBoundInScreenPixel;

    if (rescaleZForVisualization) {
      zLowerBoundInScreenPixel = min(-0.001f, scale(zMin));
      zUpperBoundInScreenPixel = max(0.001f, scale(zMax));
    } else {
      // By default, assume the range of z value in screen pixel is [-canvasWidth, canvasWidth].
      float defaultRangeFactor = 1f;
      zLowerBoundInScreenPixel = -defaultRangeFactor * canvas.getWidth();
      zUpperBoundInScreenPixel = defaultRangeFactor * canvas.getWidth();
    }

    float zInScreenPixel = scale(zInImagePixel);

    if (zInScreenPixel < 0) {
      // Sets up the paint to draw the body line in red if it is in front of the z origin.
      // Maps values within [zLowerBoundInScreenPixel, 0) to [255, 0) and use it to control the
      // color. The larger the value is, the more red it will be.
      int v = (int) (zInScreenPixel / zLowerBoundInScreenPixel * 255);
      v = Ints.constrainToRange(v, 0, 255);
      paint.setARGB(255, 255, 255 - v, 255 - v);
    } else {
      // Sets up the paint to draw the body line in blue if it is behind the z origin.
      // Maps values within [0, zUpperBoundInScreenPixel] to [0, 255] and use it to control the
      // color. The larger the value is, the more blue it will be.
      int v = (int) (zInScreenPixel / zUpperBoundInScreenPixel * 255);
      v = Ints.constrainToRange(v, 0, 255);
      paint.setARGB(255, 255 - v, 255 - v, 255);
    }
  }
}

