// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Commands.RunIntake;
import frc.robot.Commands.SetIntakePos;
import frc.robot.Commands.SetIntakePosWithMagic;
import frc.robot.Subsystems.IntakeSubsystem;

public class RobotContainer {

  public static final CommandXboxController driver = new CommandXboxController(0);

  public static GenericHID buttonController = new GenericHID(1);

  public static final JoystickButton button = new JoystickButton(buttonController, 1);

  private static IntakeSubsystem mIntakeSubsystem = new IntakeSubsystem();


  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    driver.leftBumper().whileTrue(new RunIntake(0.35, mIntakeSubsystem));
    driver.rightBumper().whileTrue(new RunIntake(-0.35, mIntakeSubsystem));

    // driver.a().onTrue(new SetIntakePos(-33, mIntakeSubsystem));
    // driver.b().onTrue(new SetIntakePos(0, mIntakeSubsystem));
    driver.a().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -78.5));
    driver.x().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -33));
    driver.b().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, -15));
    driver.y().onTrue(new SetIntakePosWithMagic(mIntakeSubsystem, 0));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
