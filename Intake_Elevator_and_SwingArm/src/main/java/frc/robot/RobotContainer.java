// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.time.chrono.MinguoChronology;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Commands.Climb;
import frc.robot.Commands.Descore;
import frc.robot.Commands.IntakeAlgae;
import frc.robot.Commands.IntakeAlgaeAuto;
import frc.robot.Commands.IntakeAlgaeWithMech;
import frc.robot.Commands.IntakeCoral;
import frc.robot.Commands.IntakeCoralFromGround;
import frc.robot.Commands.MoveElevatorIncrimentally;
import frc.robot.Commands.MoveMechanism;
import frc.robot.Commands.ReadyClimber;
import frc.robot.Commands.MechanismCommands.SetMechanismToPose;
import frc.robot.Commands.MechanismCommands.StowMechanism;
import frc.robot.Lib.Utils.SwervePOILogic;
import frc.robot.Commands.RunIntake;
import frc.robot.Commands.RunManipulator;
import frc.robot.Commands.SetIntakePos;
import frc.robot.Commands.SetIntakePosWithMagic;
import frc.robot.Commands.SetShoulderPos;
import frc.robot.Commands.StowWithAlgaeInBucket;
import frc.robot.Commands.YeetAlgae;
import frc.robot.Commands.elevatorSetPos;
import frc.robot.Commands.Drive.DescoreWithDrive;
import frc.robot.Commands.Drive.ResetGyro;
import frc.robot.Commands.Drive.TeleopSwerve;
import frc.robot.Subsystems.ElevatorSubsystem;
import frc.robot.Subsystems.IntakeSubsystem;
import frc.robot.Subsystems.LEDSubsystem;
import frc.robot.Subsystems.ManipulatorSubsystem;
import frc.robot.Subsystems.PhotonLocalization;
import frc.robot.Subsystems.ShoulderSubsystem;
import frc.robot.Subsystems.SuperStructure;
import frc.robot.Subsystems.SwerveSubsystem;
import frc.robot.Subsystems.TestingStateHandler;
import pabeles.concurrency.IntOperatorTask.Min;

public class RobotContainer {
  private static final ElevatorSubsystem mElevatorSubsystem = new ElevatorSubsystem();
  private static final IntakeSubsystem mIntakeSubsystem = new IntakeSubsystem();
  private static final ManipulatorSubsystem mManipulatorSubsystem = new ManipulatorSubsystem();
  private static final ShoulderSubsystem mShoulderSubsystem = new ShoulderSubsystem();
  public static final TestingStateHandler mStateHandler = new TestingStateHandler(mElevatorSubsystem, mIntakeSubsystem, mManipulatorSubsystem, mShoulderSubsystem);
  private static final SwerveSubsystem mSwerve = new SwerveSubsystem();
  private static final LEDSubsystem mLEDSubsystem = new LEDSubsystem();
  private static final PhotonLocalization mLocalization = new PhotonLocalization(mSwerve);
  private static final SuperStructure mSuperStructure = new SuperStructure(mIntakeSubsystem, mElevatorSubsystem, mManipulatorSubsystem, mShoulderSubsystem);

  public static CommandXboxController driver = new CommandXboxController(0);
  public static CommandXboxController operator = new CommandXboxController(1);
  public static GenericHID dial = new GenericHID(2);

  private final SendableChooser<Command> autoChooser;

  public static enum DescoreState {
    UP,
    LOWER,
    UP_BACKWARD,
    LOWER_BACKWARD
  }

  public RobotContainer() {
    NamedCommands.registerCommand("Low Descore Forward", new MoveMechanism(0, 15, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
    NamedCommands.registerCommand("UP Descore Forward", new MoveMechanism(15, 17, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
    NamedCommands.registerCommand("Intake Algae", new IntakeAlgaeAuto(0.75, mManipulatorSubsystem));
    NamedCommands.registerCommand("Score L1", new SetIntakePosWithMagic(mIntakeSubsystem, -20, true));
    NamedCommands.registerCommand("Spit Coral", new RunIntake(-0.25, mIntakeSubsystem));
    NamedCommands.registerCommand("Stop Intake", new RunIntake(0, mIntakeSubsystem));
    NamedCommands.registerCommand("Stow", new MoveMechanism(0, 1, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
    NamedCommands.registerCommand("Yeet", new YeetAlgae(mIntakeSubsystem, mManipulatorSubsystem, mElevatorSubsystem, mShoulderSubsystem));
    NamedCommands.registerCommand("Stow Intake", new SetIntakePosWithMagic(mIntakeSubsystem, 0, true));
    NamedCommands.registerCommand("Stow Mechanism", new MoveMechanism(0, 1, false, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
    NamedCommands.registerCommand("Safe Barge", new MoveMechanism(41, 43, false, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
    NamedCommands.registerCommand("Spit Algae", new RunManipulator(mManipulatorSubsystem, -1));

    autoChooser = AutoBuilder.buildAutoChooser();
    SmartDashboard.putData("Auto Chooser", autoChooser);

    configureBindings();
  }

  private void configureBindings() {
        mSwerve.setDefaultCommand(new TeleopSwerve(mSwerve, ()-> driver.getRawAxis(0), ()-> -driver.getRawAxis(1), ()-> driver.getRawAxis(4), ()-> false));
        driver.start().onTrue(new ResetGyro(mSwerve));

        // ShoulderDriver.x().onTrue(new elevatorSetPos(mElevatorSubsystem, 0));//bottom
        // ShoulderDriver.leftBumper().onTrue(new elevatorSetPos(mElevatorSubsystem, 41.13));//top
        
        // IntakeDriver.a().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -87));//ground intake
        // IntakeDriver.x().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -33));//bottom row
        // IntakeDriver.b().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -15));//top row
        // IntakeDriver.y().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, 0));//stowed
        // IntakeDriver.leftBumper().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, 10));//climb
        // IntakeDriver.rightBumper().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -40));//higher climb
        // IntakeDriver.leftTrigger().toggleOnTrue(new IntakeCoral(0.5, mIntakeSubsystem));
        // IntakeDriver.rightTrigger().whileTrue(new RunIntake(-0.2, mIntakeSubsystem));
        
        // ShoulderDriver.a().onTrue(new SetShoulderPos(32, mShoulderSubsystem));//descore forward ?
        // ShoulderDriver.y().onTrue(new SetShoulderPos(1, mShoulderSubsystem));//stow
        // ShoulderDriver.b().onTrue(new SetShoulderPos(59.5, mShoulderSubsystem));//descore reverse

        // ManipulatorDriver.rightBumper().whileTrue(new RunManipulator(mManipulatorSubsystem, 0.75));//<--- this is a great speed!!!!
        // ManipulatorDriver.leftBumper().whileTrue(new RunManipulator(mManipulatorSubsystem, -0.75));

        // // ManipulatorDriver.a().onTrue(new SetMechanismToPose(10, 19, mElevatorSubsystem, mShoulderSubsystem, mIntakeSubsystem));
        // // ManipulatorDriver.x().onTrue(new SetMechanismToPose(41.13, 32, mElevatorSubsystem, mShoulderSubsystem, mIntakeSubsystem));
        // // ManipulatorDriver.b().onTrue(new StowMechanism(mElevatorSubsystem, mShoulderSubsystem, mIntakeSubsystem));
        // ManipulatorDriver.rightTrigger().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, 0));//stowed
        // ManipulatorDriver.leftTrigger().onTrue(new elevatorSetPos(mElevatorSubsystem, 1));
        // ManipulatorDriver.a().onTrue(new elevatorSetPos(mElevatorSubsystem, 5));
        // ManipulatorDriver.b().onTrue(new elevatorSetPos(mElevatorSubsystem, 10));
        // ManipulatorDriver.x().onTrue(new elevatorSetPos(mElevatorSubsystem,15));
        // ManipulatorDriver.y().onTrue(new elevatorSetPos(mElevatorSubsystem, 24));

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
        //TODO: Add operator controls CLIMBING!!!!!, More autos(like a 3 piece),

        //driver.b().toggleOnTrue(new MoveElevatorIncrimentally(mElevatorSubsystem));
        
        driver.y().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -25, true));
        driver.a().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, 11, false));
        driver.povLeft().onTrue(new RunIntake(0.5, mIntakeSubsystem));
        driver.povDown().onTrue(new MoveMechanism(0, 1, false, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem).andThen(new elevatorSetPos(mElevatorSubsystem, 3)));
        //driver.x().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -89));
        driver.povUp().onTrue(new Descore(mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem).alongWith(new IntakeAlgae(0.75, mManipulatorSubsystem)));//new Descore(mShoulderSubsystem, mIntakeSubsystem, mManipulatorSubsystem, mElevatorSubsystem));//new MoveMechanism(10, 17, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
        //operator.x().onTrue(new MoveMechanism(0, 15, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
        //driver.povDown().onTrue(new Descore(mShoulderSubsystem, mIntakeSubsystem, mManipulatorSubsystem, mElevatorSubsystem));//new MoveMechanism(0, 17, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
        //driver.leftStick().onTrue(new StowMechanism(mElevatorSubsystem, mShoulderSubsystem, mIntakeSubsystem, mManipulatorSubsystem, ()-> SuperStructure.GetStowCommand()));
        driver.leftStick().onTrue(new MoveMechanism(0, 1, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem).alongWith(new RunManipulator(mManipulatorSubsystem, 0)));
        driver.povRight().onTrue(new MoveMechanism(41, 30, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
        driver.b().toggleOnTrue(new DescoreWithDrive(mSwerve, ()-> -driver.getRawAxis(1), ()-> SwervePOILogic.findNearestDescore().getFirst(), ()-> dial.getRawAxis(0)).alongWith(new Descore(mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem)).alongWith(new IntakeAlgae(0.75, mManipulatorSubsystem)));
        //operator.a().whileTrue(mSwerve.PathfindToPose(()-> Constants.BlueSidePoses.CLOSE_DESCORE));

        driver.rightTrigger().toggleOnTrue(new IntakeCoralFromGround(.4, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));
        driver.leftTrigger().whileTrue(new RunIntake(-0.25, mIntakeSubsystem));
        driver.rightBumper().toggleOnTrue(new IntakeAlgae(.75, mManipulatorSubsystem));
        driver.leftBumper().whileTrue(new RunManipulator(mManipulatorSubsystem, -1));

        driver.rightStick().onTrue(new IntakeAlgaeWithMech(0.85, mIntakeSubsystem, mElevatorSubsystem, mShoulderSubsystem));
        driver.x().onTrue(new YeetAlgae(mIntakeSubsystem, mManipulatorSubsystem, mElevatorSubsystem, mShoulderSubsystem));
        //driver.back().onTrue(new StowWithAlgaeInBucket(mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem))
        
        operator.rightBumper().onTrue(new MoveMechanism(41, 43, false, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem));

        operator.a().onTrue(new ReadyClimber(mIntakeSubsystem, mElevatorSubsystem, mShoulderSubsystem));
        operator.b().toggleOnTrue(new Climb(()-> operator.getRawAxis(2), ()-> operator.getRawAxis(3), mIntakeSubsystem));

        /*Manual Descore HIGH*/operator.povUp().onTrue(new MoveMechanism(15, 17, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem).alongWith(new IntakeAlgae(0.75, mManipulatorSubsystem)));
        /*Manual Descore LOW*/operator.povDown().onTrue(new MoveMechanism(0, 15, true, mIntakeSubsystem, mShoulderSubsystem, mElevatorSubsystem).alongWith(new IntakeAlgae(0.75, mManipulatorSubsystem)));
        //operator.x().onTrue(new SetIntakePos(8, mIntakeSubsystem));
  }

  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}