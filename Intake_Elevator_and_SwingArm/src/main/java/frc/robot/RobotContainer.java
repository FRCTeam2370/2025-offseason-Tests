// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Commands.RunManipulator;
import frc.robot.Commands.SetIntakePosWithMagic;
import frc.robot.Commands.SetShoulderPos;
import frc.robot.Commands.elevatorSetPos;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.ManipulatorSubsystem;
import frc.robot.Subsystems.ShoulderSubsystem;
import frc.robot.Subsystems.TestingStateHandler;
import pabeles.concurrency.IntOperatorTask.Min;

public class RobotContainer {
  private static final ElevatorSubsystem mElevatorSubsystem = new ElevatorSubsystem();
  private static final IntakeSubsystem mIntakeSubsystem = new IntakeSubsystem();
  private static final ManipulatorSubsystem mManipulatorSubsystem = new ManipulatorSubsystem();
  private static final ShoulderSubsystem mShoulderSubsystem = new ShoulderSubsystem();
  public static final TestingStateHandler mStateHandler = new TestingStateHandler(mElevatorSubsystem, mIntakeSubsystem, mManipulatorSubsystem, mShoulderSubsystem);

  public static CommandXboxController elevatorDriver = new CommandXboxController(0);
  public static CommandXboxController IntakeDriver = new CommandXboxController(1);
  public static CommandXboxController ShoulderDriver = new CommandXboxController(2);
  public static CommandXboxController ManipulatorDriver = new CommandXboxController(3);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    
        elevatorDriver.x().onTrue(new elevatorSetPos(mElevatorSubsystem, 0));//bottom
        elevatorDriver.b().onTrue(new elevatorSetPos(mElevatorSubsystem, 27));//top
        
        IntakeDriver.a().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -78.5));//ground intake
        IntakeDriver.x().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -33));//bottom row
        IntakeDriver.b().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -15));//top row
        IntakeDriver.y().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, 0));//stowed
        IntakeDriver.leftBumper().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, 15));
        IntakeDriver.rightBumper().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, 25));
        
        ShoulderDriver.a().onTrue(new SetShoulderPos(15, mShoulderSubsystem));//descore forward ?
        ShoulderDriver.y().onTrue(new SetShoulderPos(1, mShoulderSubsystem));//stow
        ShoulderDriver.b().onTrue(new SetShoulderPos(59.5, mShoulderSubsystem));//descore reverse

        ManipulatorDriver.rightBumper().whileTrue(new RunManipulator(mManipulatorSubsystem, 0.75));//<--- this is a great speed!!!!
        ManipulatorDriver.leftBumper().whileFalse(new RunManipulator(mManipulatorSubsystem, -0.5));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
