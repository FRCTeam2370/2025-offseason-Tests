// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subsystems;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.RobotContainer;
import frc.robot.Commands.RunManipulator;
import frc.robot.Commands.SetIntakePosWithMagic;
import frc.robot.Commands.SetShoulderPos;
import frc.robot.Commands.elevatorSetPos;
import pabeles.concurrency.IntOperatorTask.Min;

public class TestingStateHandler extends SubsystemBase {
  private SendableChooser<String> states = new SendableChooser<>();
  public String state = "Elevator";

  public Command aCommand = new Command() {};
  public Command bCommand = new Command() {};
  public Command xCommand = new Command() {};
  public Command yCommand = new Command() {};
  public Command rBumperCommand = new Command() {};
  public Command lBumperCommand = new Command() {};

  private ElevatorSubsystem mElevatorSubsystem;
  private IntakeSubsystem mIntakeSubsystem;
  private ManipulatorSubsystem mManipulatorSubsystem;
  private ShoulderSubsystem mShoulderSubsystem;

  /** Creates a new TestingStateHandler. */
  public TestingStateHandler(ElevatorSubsystem mElevatorSubsystem, IntakeSubsystem mIntakeSubsystem, ManipulatorSubsystem mManipulatorSubsystem, ShoulderSubsystem mShoulderSubsystem) {
    this.mElevatorSubsystem = mElevatorSubsystem;
    this.mIntakeSubsystem = mIntakeSubsystem;
    this.mManipulatorSubsystem = mManipulatorSubsystem;
    this.mShoulderSubsystem = mShoulderSubsystem;
    
    states.addOption("Elevator", "Elevator");
    states.addOption("Intake", "Intake");
    states.addOption("Manipulator", "Manipulator");
    states.addOption("SwingArm", "SwingArm");
    states.setDefaultOption("Elevator", "Elevator");
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putData("States Chooser", states);
    state = states.getSelected();
    SmartDashboard.putString("what it thinks is the State", state);
  }
}
