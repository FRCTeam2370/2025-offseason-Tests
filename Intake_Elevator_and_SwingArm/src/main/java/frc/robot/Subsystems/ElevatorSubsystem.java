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

public class ElevatorSubsystem extends SubsystemBase {
  public static TalonFX elevatorMotor = new TalonFX(Constants.ElevatorConstants.elevatorID);

  private static final TalonFXConfiguration elevatorConfig = new TalonFXConfiguration();

  private static PositionDutyCycle elevatorPosCycle = new PositionDutyCycle(0);
  private static MotionMagicDutyCycle elevatorMagicPosCycle = new MotionMagicDutyCycle(0);

  /** Creates a new ElevatorSubsystem. */
  public ElevatorSubsystem() {
    elevatorConfiguration();
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Elevator Pos", elevatorMotor.getPosition().getValueAsDouble());
    // This method will be called once per scheduler run
  }

  public static void setElevatorPos(double pos) {
    elevatorMotor.setControl(elevatorPosCycle.withPosition(pos));
  }

  public static void setElevatorPosButMagical(double pos) {
    elevatorMotor.setControl(elevatorMagicPosCycle.withPosition(pos));
  }

  private static void elevatorConfiguration(){
    elevatorMotor.setNeutralMode(NeutralModeValue.Coast);
    elevatorMotor.setPosition(0);

    elevatorConfig.Slot0.kP = 0.2;
    elevatorConfig.Slot0.kI = 0;
    elevatorConfig.Slot0.kD = 0.01;

    elevatorConfig.Slot0.kS = 0;
    elevatorConfig.Slot0.kV = 0.002;
    elevatorConfig.Slot0.kA = 0;

    elevatorConfig.MotionMagic.MotionMagicCruiseVelocity = 250;
    elevatorConfig.MotionMagic.MotionMagicAcceleration = 350;

    elevatorConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

    elevatorMotor.getConfigurator().apply(elevatorConfig);
  }
}