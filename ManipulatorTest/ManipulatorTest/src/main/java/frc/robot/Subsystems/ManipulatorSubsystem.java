// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ManipulatorSubsystem extends SubsystemBase {
  public static TalonFX manipulatorMotor;
  /** Creates a new ManipulatorSubsystem. */
  public ManipulatorSubsystem() {
    manipulatorMotor = new TalonFX(10);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public static void runManipulator(double speed){
    manipulatorMotor.set(speed);
  }
}
