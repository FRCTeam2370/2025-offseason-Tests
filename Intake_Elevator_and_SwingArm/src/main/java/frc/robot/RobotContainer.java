// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Commands.IntakeCoral;
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

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
        mSwerve.setDefaultCommand(new TeleopSwerve(mSwerve, ()-> ShoulderDriver.getRawAxis(0), ()-> -ShoulderDriver.getRawAxis(1), ()-> ShoulderDriver.getRawAxis(4), ()-> false));
        ShoulderDriver.start().onTrue(new ResetGyro(mSwerve));

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
        ManipulatorDriver.leftBumper().whileFalse(new RunManipulator(mManipulatorSubsystem, -0.5));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
