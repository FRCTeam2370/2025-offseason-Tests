// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import static edu.wpi.first.units.Units.Centimeters;
import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.Percent;
import static edu.wpi.first.units.Units.Second;
import static edu.wpi.first.units.Units.Seconds;

import java.util.Map;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {

  //Sets Addresable LED Port and addresable LED strip length
  private static final AddressableLED feedbackLEDs = new AddressableLED(0);
  private static final AddressableLEDBuffer LEDBuffer = new AddressableLEDBuffer(22);


  //Sets the spacing of our LEDs which are 22 leds per 0.15 meters
  private static final Distance LEDSpacing = Meters.of(0.15/22);

  //Disable Animation does a continuous scrolling rainbow effect
  static Map<Double, Color> maskSteps = Map.of(0.0, Color.kWhite, 0.5, Color.kBlack);
  static LEDPattern disableAnimBase = LEDPattern.rainbow(255, 128);
  static LEDPattern disableAnimMask = LEDPattern.steps(maskSteps).scrollAtRelativeSpeed(Percent.per(Second).of(.25));
  static LEDPattern disableAnim = disableAnimBase.mask(disableAnimMask);
  
    //Blue Climbing Anim does a scrolling gradient animation that takes around 3 seconds to complete of the colros limegreen and blue
    static LEDPattern blueClimbAnimBase = LEDPattern.gradient(LEDPattern.GradientType.kDiscontinuous, Color.kLimeGreen, Color.kBlue);
    static LEDPattern blueClimbAnimMask = blueClimbAnimBase.scrollAtRelativeSpeed(Percent.per(Second).of(.25));
    static LEDPattern blueclimbAnim = blueClimbAnimBase.scrollAtAbsoluteSpeed(Centimeters.per(Second).of(9.89), LEDSpacing);
  
  
    //Red Climbing Anim does a scrolling gradient animation that takes around 3 seconds to complete of the colros limegreen and red
    static LEDPattern redClimbAnimBase = LEDPattern.gradient(LEDPattern.GradientType.kDiscontinuous, Color.kLimeGreen, Color.kRed);
    static LEDPattern redClimbAnimMask = redClimbAnimBase.scrollAtRelativeSpeed(Percent.per(Second).of(.25));
    static LEDPattern redclimbAnim = redClimbAnimBase.scrollAtAbsoluteSpeed(Centimeters.per(Second).of(9.89), LEDSpacing);
  
    //Able to score Anim shows a static green gradient
    static LEDPattern ableToScoreAnim = LEDPattern.gradient(LEDPattern.GradientType.kContinuous, Color.kDarkGreen, Color.kLimeGreen);
  
    //Unable to score Anim shows a static red gradient
    static LEDPattern unableToScoreAnim = LEDPattern.gradient(LEDPattern.GradientType.kContinuous, Color.kDarkRed, Color.kRed);
  
    //Robot has Coral Animation does a breathing animation every second of two different whites
    static LEDPattern hasCoralAnimBase = LEDPattern.gradient(LEDPattern.GradientType.kDiscontinuous, Color.kWhite, Color.kGhostWhite);
    static LEDPattern hasCoralAnim = hasCoralAnimBase.breathe(Seconds.of(1));
  
    //Robot has Algae Animation does a breathing animation every second of a teal and turqoise gradient
    static LEDPattern hasAlgaeAnimBase = LEDPattern.gradient(LEDPattern.GradientType.kDiscontinuous, Color.kTeal, Color.kTurquoise);
    static LEDPattern hasAlgaeAnim = hasAlgaeAnimBase.breathe(Seconds.of(1));
  
    //Endgame Warning Animation Flashes red on/off every .25 seconds
    static LEDPattern endgameWarningAnimBase = LEDPattern.solid(Color.kRed);
    static LEDPattern endgameWarningAnim = endgameWarningAnimBase.blink(Seconds.of(.25));
  
    /** Creates a new LEDSubsystem. */
    public LEDSubsystem() {
      feedbackLEDs.setLength(LEDBuffer.getLength());
      feedbackLEDs.start();
    }
  
    @Override
    public void periodic() {
      // This method will be called once per scheduler run
    }
  
    public static void disableLEDAnim() {
      disableAnim.applyTo(LEDBuffer);
    feedbackLEDs.setData(LEDBuffer);
  }

  public static void blueClimbLEDAnim() {
    blueclimbAnim.applyTo(LEDBuffer);
    feedbackLEDs.setData(LEDBuffer);
  }

  public static void redClimbLEDAnim() {
    redclimbAnim.applyTo(LEDBuffer);
    feedbackLEDs.setData(LEDBuffer);
  }

  public static void ableToScoreLEDAnim() {
    ableToScoreAnim.applyTo(LEDBuffer);
    feedbackLEDs.setData(LEDBuffer);
  }

  public static void unableToScoreLEDAnim() {
    unableToScoreAnim.applyTo(LEDBuffer);
    feedbackLEDs.setData(LEDBuffer);
  }

  public static void hasAlgaeLEDAnim() {
    hasAlgaeAnim.applyTo(LEDBuffer);
    feedbackLEDs.setData(LEDBuffer);
  }

  public static void hasCoralLEDAnim() {
    hasCoralAnim.applyTo(LEDBuffer);
    feedbackLEDs.setData(LEDBuffer);
  }

  public static void endgameWarningLEDAnim () {
    endgameWarningAnim.applyTo(LEDBuffer);
    feedbackLEDs.setData(LEDBuffer);
  }

}
