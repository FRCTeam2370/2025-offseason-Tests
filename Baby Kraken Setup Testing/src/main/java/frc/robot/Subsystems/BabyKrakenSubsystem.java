// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import java.util.function.BiConsumer;

import com.ctre.phoenix6.configs.MotionMagicConfigs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.MotionMagicDutyCycle;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class BabyKrakenSubsystem extends SubsystemBase {
  public static TalonFX bKraken = new TalonFX(8);
  private static TalonFXConfiguration bKrakenConfig = new TalonFXConfiguration();
  private static MotionMagicConfigs bKrakenMotionMagicConfig = bKrakenConfig.MotionMagic;

  private static PositionDutyCycle bKrakenPosCycle = new PositionDutyCycle(0);
  private static final MotionMagicDutyCycle motionMagicCycle = new MotionMagicDutyCycle(0);
  /** Creates a new BabyKrakenSubsystem. */
  public BabyKrakenSubsystem() {
    configureBabyKraken(false);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Baby Kraken Voltage", bKraken.getStatorCurrent().getValueAsDouble());
    SmartDashboard.putNumber("Baby Kraken Position", bKraken.getPosition().getValueAsDouble());
  }

  public static void runBabyKraken(double speed){
    bKraken.set(speed);
  }

  public static void setBabyKrakenPos(double pos){
    bKraken.setControl(bKrakenPosCycle.withPosition(pos));
  }

  public static void setMotionMagic(double pos){
    bKraken.setControl(motionMagicCycle.withPosition(pos));
  }

  public static void configureBabyKraken(Boolean isAlgae){
    if(isAlgae){
      bKrakenConfig.CurrentLimits.StatorCurrentLimit = 40;
      bKrakenConfig.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 0.2;
    }else{
      bKraken.setPosition(0);

      bKrakenConfig.Slot0.kS = 0.0; // Add 0.25 V output to overcome static friction
      bKrakenConfig.Slot0.kV = 0.002; //For this value do 12.0 Volts / max rpm
      bKrakenConfig.Slot0.kA = 0.0; // An acceleration of 1 rps/s requires 0.01 V output
      bKrakenConfig.Slot0.kP = 0.1;
      bKrakenConfig.Slot0.kI = 0;
      bKrakenConfig.Slot0.kD = 0.0;

      bKrakenMotionMagicConfig.MotionMagicCruiseVelocity = 80; // Target cruise velocity of 80 rps
      bKrakenMotionMagicConfig.MotionMagicAcceleration = 160; 
    }

    bKraken.getConfigurator().apply(bKrakenConfig);
  }
}
