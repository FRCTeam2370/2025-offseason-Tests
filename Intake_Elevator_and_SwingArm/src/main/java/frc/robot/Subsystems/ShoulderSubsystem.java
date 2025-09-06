// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShoulderSubsystem extends SubsystemBase {
  public static final TalonFX shoulderMotor = new TalonFX(Constants.ShoulderConstants.ShoulderID);
  private static TalonFXConfiguration shoulderConfig = new TalonFXConfiguration();

  private static PositionDutyCycle shoulderPosCycle = new PositionDutyCycle(0);
  private static MotionMagicDutyCycle shoulderMagicCycle = new MotionMagicDutyCycle(0);
  /** Creates a new ShoulderSubsystem. */
  public ShoulderSubsystem() {
    configShoulder();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Should Pos Rotations", shoulderMotor.getPosition().getValueAsDouble());
  }

  public static void runShoulder(double speed){
    shoulderMotor.set(speed);
  }

  public static void setShoulderPos(double pos){
    shoulderMotor.setControl(shoulderPosCycle.withPosition(pos));
  }

  public static void setShoulderWithMagic(double pos){
    shoulderMotor.setControl(shoulderMagicCycle.withPosition(pos));
  }

  public static double getShoulderPos(){
    return shoulderMotor.getPosition().getValueAsDouble();
  }

  private static void configShoulder(){
    shoulderMotor.setPosition(0);
    shoulderConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

    shoulderConfig.Slot0.kP = 0.25;
    shoulderConfig.Slot0.kI = 0;
    shoulderConfig.Slot0.kD = 0.01;

    shoulderConfig.Slot0.kS = 0.0; // Add 0.25 V output to overcome static friction
    shoulderConfig.Slot0.kV = 0.002; //For this value do 12.0 Volts / max rpm
    shoulderConfig.Slot0.kA = 0.0; // An acceleration of 1 rps/s requires 0.01 V output

    shoulderConfig.MotionMagic.MotionMagicCruiseVelocity = 160; // Target cruise velocity of 40 rps
    shoulderConfig.MotionMagic.MotionMagicAcceleration = 170;// double your cruise velocity

    shoulderMotor.getConfigurator().apply(shoulderConfig);
    shoulderMotor.setNeutralMode(NeutralModeValue.Brake);
  }
}
