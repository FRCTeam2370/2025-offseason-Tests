// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import java.security.PublicKey;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {
  public static DigitalOutput output1 = new DigitalOutput(0);
  public static DigitalOutput output2 = new DigitalOutput(0);
  public static DigitalOutput output3 = new DigitalOutput(0);
  public static DigitalOutput output4 = new DigitalOutput(0);
  /** Creates a new LEDSubsystem. */
  public LEDSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public static void hasCoralLED() {
    output1.set(false);
    output2.set(false);
    output3.set(false);
    output4.set(false);
  }

  public static void hasAlgaeLED() {
    output1.set(false);
    output2.set(false);
    output3.set(false);
    output4.set(false);
  }

  public static void ableToScoreLED() {
    output1.set(false);
    output2.set(false);
    output3.set(false);
    output4.set(false);
  }

  public static void unableToScoreLED() {
    output1.set(false);
    output2.set(false);
    output3.set(false);
    output4.set(false);
  }

  public static void redClimbLED() {
    output1.set(false);
    output2.set(false);
    output3.set(false);
    output4.set(false);
  }

  public static void blueClimbLED() {
    output1.set(false);
    output2.set(false);
    output3.set(false);
    output4.set(false);
  }

  public static void endgameWarning() {
    output1.set(false);
    output2.set(false);
    output3.set(false);
    output4.set(false);
  }

}
