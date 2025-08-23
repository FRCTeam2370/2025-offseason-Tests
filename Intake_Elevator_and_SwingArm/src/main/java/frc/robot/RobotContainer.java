// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.time.chrono.MinguoChronology;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Commands.IntakeAlgae;
import frc.robot.Commands.IntakeCoral;
import frc.robot.Commands.MoveElevatorIncrimentally;
import frc.robot.Commands.MoveMechanism;
import frc.robot.Commands.MechanismCommands.SetMechanismToPose;
import frc.robot.Commands.MechanismCommands.StowMechanism;
import frc.robot.Commands.RunIntake;
import frc.robot.Commands.RunManipulator;
import frc.robot.Commands.SetIntakePosWithMagic;
import frc.robot.Commands.SetShoulderPos;
import frc.robot.Commands.elevatorSetPos;
import frc.robot.Commands.Drive.ResetGyro;
import frc.robot.Commands.Drive.TeleopSwerve;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.ManipulatorSubsystem;
import frc.robot.Subsystems.ShoulderSubsystem;
import frc.robot.Subsystems.SwerveSubsystem;
import frc.robot.Subsystems.TestingStateHandler;

public class RobotContainer {
  private static final ElevatorSubsystem mElevatorSubsystem = new ElevatorSubsystem();
  private static final IntakeSubsystem mIntakeSubsystem = new IntakeSubsystem();
  private static final ManipulatorSubsystem mManipulatorSubsystem = new ManipulatorSubsystem();
  private static final ShoulderSubsystem mShoulderSubsystem = new ShoulderSubsystem();
  public static final TestingStateHandler mStateHandler = new TestingStateHandler(mElevatorSubsystem, mIntakeSubsystem, mManipulatorSubsystem, mShoulderSubsystem);
  private static final SwerveSubsystem mSwerve = new SwerveSubsystem();

  public static CommandXboxController elevatorDriver = new CommandXboxController(0);
  public static CommandXboxController IntakeDriver = new CommandXboxController(1);
  public static CommandXboxController ShoulderDriver = new CommandXboxController(2);
  public static CommandXboxController ManipulatorDriver = new CommandXboxController(3);
  public static CommandXboxController driver = new CommandXboxController(4);

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
        mSwerve.setDefaultCommand(new TeleopSwerve(mSwerve, ()-> driver.getRawAxis(0), ()-> -driver.getRawAxis(1), ()-> driver.getRawAxis(4), ()-> false));
        driver.start().onTrue(new ResetGyro(mSwerve));

        ShoulderDriver.x().onTrue(new elevatorSetPos(mElevatorSubsystem, 0));//bottom
        ShoulderDriver.leftBumper().onTrue(new elevatorSetPos(mElevatorSubsystem, 41.13));//top
        
        IntakeDriver.a().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -87));//ground intake
        IntakeDriver.x().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -33));//bottom row
        IntakeDriver.b().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -15));//top row
        IntakeDriver.y().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, 0));//stowed
        IntakeDriver.leftBumper().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, 10));//climb
        IntakeDriver.rightBumper().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -40));//higher climb
        IntakeDriver.leftTrigger().toggleOnTrue(new IntakeCoral(0.5, mIntakeSubsystem));
        IntakeDriver.rightTrigger().whileTrue(new RunIntake(-0.2, mIntakeSubsystem));
        
        ShoulderDriver.a().onTrue(new SetShoulderPos(32, mShoulderSubsystem));//descore forward ?
        ShoulderDriver.y().onTrue(new SetShoulderPos(1, mShoulderSubsystem));//stow
        ShoulderDriver.b().onTrue(new SetShoulderPos(59.5, mShoulderSubsystem));//descore reverse

        ManipulatorDriver.rightBumper().whileTrue(new RunManipulator(mManipulatorSubsystem, 0.75));//<--- this is a great speed!!!!
        ManipulatorDriver.leftBumper().whileTrue(new RunManipulator(mManipulatorSubsystem, -0.75));

        // ManipulatorDriver.a().onTrue(new SetMechanismToPose(10, 19, mElevatorSubsystem, mShoulderSubsystem, mIntakeSubsystem));
        // ManipulatorDriver.x().onTrue(new SetMechanismToPose(41.13, 32, mElevatorSubsystem, mShoulderSubsystem, mIntakeSubsystem));
        // ManipulatorDriver.b().onTrue(new StowMechanism(mElevatorSubsystem, mShoulderSubsystem, mIntakeSubsystem));
        ManipulatorDriver.rightTrigger().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, 0));//stowed
        ManipulatorDriver.leftTrigger().onTrue(new elevatorSetPos(mElevatorSubsystem, 1));
        ManipulatorDriver.a().onTrue(new elevatorSetPos(mElevatorSubsystem, 5));
        ManipulatorDriver.b().onTrue(new elevatorSetPos(mElevatorSubsystem, 10));
        ManipulatorDriver.x().onTrue(new elevatorSetPos(mElevatorSubsystem,15));
        ManipulatorDriver.y().onTrue(new elevatorSetPos(mElevatorSubsystem, 24));

        //Old Setpoint Commands

        //mElevatorSubsystem.setDefaultCommand(new MoveElevatorIncrimentally(mElevatorSubsystem));
        //driver.b().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, 0));
        // driver.x().onTrue(new MoveMechanism(25, 15, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
        // driver.a().onTrue(new MoveMechanism(0, 22, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
        // driver.b().onTrue(new MoveMechanism(0, 1, false, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
        // driver.y().onTrue(new MoveMechanism(41, 32, false, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
        // driver.rightBumper().toggleOnTrue(new IntakeAlgae(0.75, mManipulatorSubsystem));//<--- this is a great speed!!!!
        // driver.leftBumper().whileTrue(new RunManipulator(mManipulatorSubsystem, -0.75));

        //New Setpoint Commands 

        driver.b().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -25));
        driver.a().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, 11));
        driver.x().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -89));
        driver.povUp().onTrue(new MoveMechanism(0, 22, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
        driver.povDown().onTrue(new MoveMechanism(0, 17, false, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
        driver.povLeft().onTrue(new MoveMechanism(0, 1, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
        driver.povRight().onTrue(new MoveMechanism(25, 15, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
        driver.rightTrigger().toggleOnTrue(new IntakeCoral(.4, mIntakeSubsystem));
        driver.leftTrigger().whileTrue(new RunIntake(-.5, mIntakeSubsystem));
        driver.rightBumper().toggleOnTrue(new IntakeAlgae(.4, mManipulatorSubsystem));
        driver.leftBumper().onTrue(new RunManipulator(mManipulatorSubsystem, -.5));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}