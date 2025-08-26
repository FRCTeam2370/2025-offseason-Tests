// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ManipulatorSubsystem extends SubsystemBase {
  public static TalonFX manipulatorMotor;
  public static TalonFXConfiguration mConfig = new TalonFXConfiguration();
  public static boolean hasAlgae = false;
  /** Creates a new ManipulatorSubsystem. */
  public ManipulatorSubsystem() {
    manipulatorMotor = new TalonFX(3);
    configureManipulatorMotor();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if(hasAlgae){
      runManipulator(0.15);
      // if(getManipulatorStatorCurrent() < 27){
      //   hasAlgae = false;
      // }
    }
    SmartDashboard.putBoolean("Has Algae", hasAlgae);
    SmartDashboard.putNumber("Manipulator Statur current", getManipulatorStatorCurrent());
  }

  public static double getManipulatorStatorCurrent(){
    return manipulatorMotor.getStatorCurrent().getValueAsDouble();
  }

  public static void runManipulator(double speed){
    manipulatorMotor.set(speed);
  }

  private static void configureManipulatorMotor(){
    mConfig.CurrentLimits.StatorCurrentLimit = 40;

    manipulatorMotor.getConfigurator().apply(mConfig);
    manipulatorMotor.setNeutralMode(NeutralModeValue.Brake);
  }
}
