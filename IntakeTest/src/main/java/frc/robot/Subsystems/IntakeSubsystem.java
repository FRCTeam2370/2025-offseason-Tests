// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionDutyCycle;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
  public static TalonFX IntakePivot = new TalonFX(6);
  public static TalonFX IntakeRollers = new TalonFX(7);

  public static TalonFXConfiguration pivotConfig = new TalonFXConfiguration();
  public static TalonFXConfiguration rollerConfig = new TalonFXConfiguration();

  public static PositionDutyCycle pivotCycle = new PositionDutyCycle(0);
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {
    configureRollerMotor();
    configurePivotMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("IntakePos", IntakePivot.getPosition().getValueAsDouble());
  }

  public static void setIntakePose(double pose){
    IntakePivot.setControl(pivotCycle.withPosition(pose));
  }

  public static void setPivotVolt(double volt){

  }

  public static void runIntake(double speed){
    IntakeRollers.set(speed);
  }

  public static void configureRollerMotor(){
    IntakeRollers.setNeutralMode(NeutralModeValue.Brake);

    rollerConfig.OpenLoopRamps.DutyCycleOpenLoopRampPeriod = 0.1;
    rollerConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

    IntakeRollers.getConfigurator().apply(rollerConfig);
  }

  public static void configurePivotMotor(){
    IntakePivot.setPosition(0);
    IntakePivot.setNeutralMode(NeutralModeValue.Coast);

    pivotConfig.Slot0.kP = 0.2;
    pivotConfig.Slot0.kI = 0;
    pivotConfig.Slot0.kD = 0;
    //TODO:find the kg here and do it properly: https://v6.docs.ctr-electronics.com/en/latest/docs/api-reference/device-specific/talonfx/closed-loop-requests.html#arm-cosine
    pivotConfig.Slot0.kG = 0;

    IntakePivot.getConfigurator().apply(pivotConfig);
  }
}
